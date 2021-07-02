package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Adapter.BluetoothListAdapter;
import com.pradeep.karak.BLE.BluetoothConnectCallback;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.BluetoothScannerCallback;
import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.DialogBluetoothlistBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentBluetoothList extends Fragment implements BluetoothDataCallback, ItemClickListener {
    private static final String TAG = "DialogBluetoothList";
    DialogBluetoothlistBinding mBinding;
    BaseActivity mActivity;
    ArrayList<String> deviceList;
    ArrayList<Map<String, String>> mDeviceList;
    Context mContext;
    ApplicationClass mAppClass;
    // Ble
    List<BluetoothDevice> scannedDevices;
    BluetoothDevice mBleDevice;
    boolean dataReceived = false;
    boolean isVisible = false;
    SharedPreferences preferences;
    BluetoothListAdapter bluetoothListAdapter;
    AlertDialog dispenseAlert, panAlert;
    ImageView iv;
    TextView tv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_bluetoothlist, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().hide();
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mBinding.btnScan.setAlpha(.5f);
        mBinding.btnScan.setEnabled(false);
        mActivity.showProgress();
        mDeviceList = new ArrayList<>();
        deviceList = new ArrayList<>();
        scannedDevices = new ArrayList<>();
        mBinding.rvBluetoothList.setLayoutManager(new LinearLayoutManager(getContext()));
        startScan();

        mBinding.txtConnect.setOnClickListener((view1 -> {
            if (mBinding.txtConnect.getText().toString().equals("Rescan")) {
                mBinding.txtConnect.setText("Scanning..");
                startScan();
            }
        }));
    }

    private void sendPacket(String packet) {
        mAppClass.sendData(getActivity(), FragmentBluetoothList.this, packet, getContext());
    }

    private void stopScan() {
        mBinding.txtConnect.setText(R.string.scan);
        mBinding.txtConnect.setEnabled(true);
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        helper.stopScan();
    }

    private void startScan() {
        deviceList.clear();
        // mBinding.btnScan.setText(R.string.scanning);
        mBinding.btnScan.setEnabled(false);
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        helper.turnOn();
        helper.disConnect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    helper.scanBLE(new BluetoothScannerCallback() {
                        @Override
                        public void OnScanCompleted(List<BluetoothDevice> devices) {
                            if (devices.size() == 0) {
                                mAppClass.showSnackBar(mContext, "Refreshing..");
                                stopScan();
                                startScan();
                                mBinding.txtConnect.setText("Scanning..");
                            }
                        }

                        @Override
                        public void SearchResult(BluetoothDevice device) {
                            Log.e(TAG, "SearchResult: " + device);
                        }

                        @Override
                        public void OnDeviceFoundUpdate(List<BluetoothDevice> devices) {
                            scannedDevices.clear();
                            scannedDevices.addAll(devices);
                            for (BluetoothDevice device : devices) {
                                String listItem = (device.getName() == null ? getString(R.string.unkown) : device.getName())
                                        + "\n" + (device.getAddress() == null ? getString(R.string.unkown) : device.getAddress());
                                if (!deviceList.contains(listItem)) {
                                    deviceList.add(listItem);
                                    updateRV(deviceList);
                                }
                            }
                            mActivity.dismissProgress();
                            mBinding.txtConnect.setText("Rescan");
                        }
                    });
                } catch (Exception e) {
                    mBinding.txtConnect.setText("Rescan");
                    stopScan();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateRV(ArrayList<String> deviceList) {
        try {
            List<String> list = new ArrayList<>();
            if (!preferences.getString("savedMac", "").equals("")
                    && preferences.getString("savedMac", "") != null) {
                JSONArray arr = new JSONArray(preferences.getString("savedMac", ""));
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    list.add(obj.getString("mac"));
                }
            }
            bluetoothListAdapter = new BluetoothListAdapter(deviceList, list, this);
            mBinding.rvBluetoothList.setAdapter(bluetoothListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        dataReceived = true;
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("01")) {
            if (spiltData[2].equals("ACK")) {
                if (spiltData[1].equals("00")) {
                    mActivity.updateNavigationUi(R.navigation.navigation);
                } else if (spiltData[1].equals("01")) {
                    mAppClass.showSnackBar(getContext(), getString(R.string.DispensingPleaseWait));
                }
            }
            helper.setConnected(true);
        } else if (spiltData[0].substring(5, 7).equals("03")) {
            String[] status = spiltData[1].split(","), boilTime = spiltData[2].split(","), bevarageName = spiltData[3].split(",");
            mActivity.dismissProgress();
            sendPacket(data);
            if (status[1].equals("01")) {
                showPanNotAvailable();
            } else {
                switch (bevarageName[1]) {
                    case "01":
                        if (!isVisible) {
                            showDispenseAlert("Karak", R.drawable.dispense_back);
                        } else {
                            changeDispenseMsg("Karak", R.drawable.dispense_back);
                        }
                        break;
                    case "02":
                        if (!isVisible) {
                            showDispenseAlert("Ginger Karak", R.drawable.bg_app_button);
                        } else {
                            changeDispenseMsg("Ginger Karak", R.drawable.bg_app_button);
                        }
                        break;
                    case "03":
                        if (!isVisible) {
                            showDispenseAlert("Sulaimani", R.drawable.bg_top_curved);
                        } else {
                            changeDispenseMsg("Sulaimani", R.drawable.bg_top_curved);
                        }
                        break;
                    case "04":
                        if (!isVisible) {
                            showDispenseAlert("Masala Karak", R.drawable.ic_bg_box);
                        } else {
                            changeDispenseMsg("Masala Karak", R.drawable.ic_bg_box);
                        }
                        break;
                    case "05":
                        if (!isVisible) {
                            showDispenseAlert("Cardomom Karak", R.drawable.ic_camera);
                        } else {
                            changeDispenseMsg("Cardomom Karak", R.drawable.ic_camera);
                        }
                        break;
                    case "06":
                        if (!isVisible) {
                            showDispenseAlert("Hot milk", R.drawable.ic_heart);
                        } else {
                            changeDispenseMsg("Hot milk", R.drawable.ic_heart);
                        }
                        break;
                    case "07":
                        if (!isVisible) {
                            showDispenseAlert("Hot Water", R.drawable.ic_operator);
                        } else {
                            changeDispenseMsg("Hot Water", R.drawable.ic_operator);
                        }
                        break;
                }

                switch (status[1]) {
                    case "02":
                        mAppClass.showSnackBar(getContext(), "Dispensing Milk & Water");
                        break;
                    case "03":
                        mAppClass.showSnackBar(getContext(), "Preheating");
                        break;
                    case "04":
                        mAppClass.showSnackBar(getContext(), "Dispensing ingredients");
                        break;
                    case "05":
                        mAppClass.showSnackBar(getContext(), "Boiling");
                        break;
                }
            }
        } // Pan Release
        else if (spiltData[0].substring(5, 7).equals("05")) {
            if (spiltData[1].equals("ACK")) {
                mAppClass.showSnackBar(getContext(), getString(R.string.PanReleaseAck));
            }
        }
        // Dispense Completed
        else if (spiltData[0].substring(5, 7).equals("04")) {
            sendPacket(mAppClass.framePacket("04;ACK:"));
            if (dispenseAlert.isShowing()) {
                dispenseAlert.dismiss();
            }
            mAppClass.showSnackBar(getContext(), "Dispense Completed !");
            mActivity.updateNavigationUi(R.navigation.navigation);
        }
        // Cancel Dispense
        else if (spiltData[0].substring(5, 7).equals("06")) {
            if (spiltData[1].equals("ACK")) {
                mAppClass.showSnackBar(getContext(), getString(R.string.DispenseCanceled));
                mActivity.updateNavigationUi(R.navigation.navigation);
                if (panAlert.isShowing()) {
                    panAlert.dismiss();
                }
            }
        }
    }

    private void changeDispenseMsg(String beverageName, int resourceID) {
        tv.setText(beverageName);
        iv.setBackgroundResource(resourceID);
    }

    private void showPanNotAvailable() {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_pan_not_available, null);
        dialogBuilder2.setView(dialogView2);
        dialogBuilder2.setCancelable(false);
        TextView release = dialogView2.findViewById(R.id.txt_Release);
        TextView cancel = dialogView2.findViewById(R.id.txt_cancel);

        release.setOnClickListener((view -> {
            sendPacket(mAppClass.framePacket("05;"));
            panAlert.dismiss();
            mActivity.showProgress();
        }));

        cancel.setOnClickListener(view -> {
            sendPacket(mAppClass.framePacket("06;"));
        });

        panAlert = dialogBuilder2.create();
        panAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        panAlert.show();
    }

    private void showDispenseAlert(String bevarageName, int resourceID) {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_dispense, null);
        dialogBuilder2.setView(dialogView2);
        dialogBuilder2.setCancelable(false);
        iv = dialogView2.findViewById(R.id.dispenseAlertImageView);
        tv = dialogView2.findViewById(R.id.dispenseAlertTextView);
        iv.setBackgroundResource(resourceID);
        tv.setText(bevarageName);

        dispenseAlert = dialogBuilder2.create();
        dispenseAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dispenseAlert.show();
        isVisible = true;
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onSaveClicked(String mac) {
        saveFavorite(mac);
    }

    private void saveFavorite(String data) {
        String[] ble = data.split("\n");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", ble[0]);
            jsonObject.put("mac", ble[1]);
            String prefValue = preferences.getString("savedMac", "");
            if (!prefValue.equals("") && prefValue != null) {
                JSONArray arr = new JSONArray(preferences.getString("savedMac", ""));
                arr.put(jsonObject);
                preferences.edit().putString("savedMac", arr.toString()).apply();
            } else {
                JSONArray arr = new JSONArray();
                arr.put(jsonObject);
                preferences.edit().putString("savedMac", arr.toString()).apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "onSaveClicked: " + preferences.getString("savedMac", ""));
    }

    @Override
    public void onUnSave(String data) {
        String[] mac = data.split("\n");
        try {
            JSONArray jArr = new JSONArray(preferences.getString("savedMac", ""));
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                String mString = obj.getString("mac");
                if (mString.equals(mac[1])) {
                    jArr.remove(i);
                }
            }
            preferences.edit().putString("savedMac", jArr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "onSaveClicked: " + preferences.getString("savedMac", ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void OnItemClick(int pos, String name) {
        preferences.edit().putString("macId", name).apply();
        stopScan();
        mBinding.txtConnect.setText("Connecting");
        mActivity.showProgress();
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        helper.disConnect();
        mBleDevice = scannedDevices.get(pos);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    helper.connectBLE(mContext, mBleDevice, new BluetoothConnectCallback() {
                        @Override
                        public void OnConnectSuccess() {
                            try {
                                mActivity.runOnUiThread(new Runnable() {
                                    int i = 1;

                                    @Override
                                    public void run() {
                                        while (i < 6) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!dataReceived) {
                                                        sendPacket(mAppClass.framePacket("01;"));
                                                    }
                                                }
                                            }, 1000 * i);
                                            i++;
                                        }
                                    }
                                });
                            } catch (
                                    Exception e) {
                                stopScan();
                                Log.e(TAG, "OnConnectSuccess: Catch");
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mActivity.dismissProgress();
                                        mAppClass.showSnackBar(mContext, "Error Occurred");
                                        mBinding.txtConnect.setText("Rescan");
                                    }
                                });
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void OnConnectFailed(Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBinding.txtConnect.setText("Rescan");
                                    stopScan();
                                }
                            });

                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "OnConnectSuccess: Failed");
                                    mActivity.dismissProgress();
                                    mAppClass.showSnackBar(mContext, "Connection Failed");
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stopScan();
                            mBinding.txtConnect.setText("Rescan");
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!dataReceived) {
                    mActivity.dismissProgress();
                    startScan();
                }
            }
        }, 20000);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopScan();
        Log.e(TAG, "onPause: ");
    }

}
