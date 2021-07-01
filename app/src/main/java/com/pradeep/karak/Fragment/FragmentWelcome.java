package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothAdapter;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Adapter.BluetoothFavListAdapter;
import com.pradeep.karak.BLE.BluetoothConnectCallback;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.BluetoothScannerCallback;
import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentWelcomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class FragmentWelcome extends Fragment implements ItemClickListener, BluetoothDataCallback {
    private static final String TAG = "MachineListFragment";
    private static String SERIAL_NO = "SerialNo", ADDRESS = "Address", END_POINT = "EndPoint", connectPacket = "1;";
    private FragmentWelcomeBinding mBinding;
    private ApplicationClass mAppClass;
    private BaseActivity mActivity;
    private Context mContext;
    SharedPreferences preferences;
    BluetoothFavListAdapter mAdapter;

    AlertDialog dispenseAlert, panAlert, confirmDispenseAlert;
    ImageView iv;
    TextView tv;
    boolean dataReceived = false;
    boolean isVisible = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_welcome, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().show();
        mContext = getActivity().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        bluetoothEnable();
        mBinding.recylerFavList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        loadFav();
        mBinding.imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppClass.navigateTo(getActivity(), R.id.action_fragment_machine_list_to_dialogBluetoothList);
            }
        });
    }

    private void loadFav() {
        try {
            mAdapter = new BluetoothFavListAdapter(new JSONArray(preferences.getString("savedMac", "")), this);
            mBinding.recylerFavList.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bluetoothEnable();
    }

    @Override
    public void OnItemClick(int pos,String name) {
    }

    @Override
    public void onSaveClicked(String mac) {
        mConnect(mac);
    }

    private void mConnect(String mac) {
        mActivity.showProgress();
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        if (helper.isConnected()) {
            helper.disConnect();
        }
        try {
            helper.scanBLE(new BluetoothScannerCallback() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void OnScanCompleted(List<BluetoothDevice> devices) {
                    Log.e(TAG, "OnScanCompleted: " + devices.size());
                    if (devices.size() == 0) {
                        mAppClass.showSnackBar(getContext(), "No Device Found");
                        mActivity.dismissProgress();
                    }
                    for (int i = 0; i < devices.size(); i++) {
                        if (devices.get(i).getAddress().equals(mac)) {
                            BluetoothDevice mDevice = devices.get(i);
                            if (helper.isConnected()) {
                                helper.disConnect();
                            }
                            connectBle(mDevice, helper);
                        }
                    }
                }

                @Override
                public void SearchResult(BluetoothDevice device) {
                    Log.e(TAG, "SearchResult: " + device);
                }

                @Override
                public void OnDeviceFoundUpdate(List<BluetoothDevice> devices) {
                    Log.e(TAG, "SearchResult: " + devices);
                }
            });
        } catch (Exception e) {
            mActivity.dismissProgress();
            Toast.makeText(mContext, "Operation Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void connectBle(BluetoothDevice mDevice, BluetoothHelper helper) {
        Log.e(TAG, "trying to connect");
        try {
            helper.connectBLE(getContext(), mDevice, new BluetoothConnectCallback() {
                @Override
                public void OnConnectSuccess() {
                    helper.stopScan();
                    sendConnectPacket();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!dataReceived) {
                                        mActivity.dismissProgress();
                                        Toast.makeText(mContext, "Timed Out", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, 10000);
                        }
                    });
                }

                @Override
                public void OnConnectFailed(Exception e) {
                    Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            mActivity.dismissProgress();
            e.printStackTrace();
        }
    }

    private void sendConnectPacket() {
        mActivity.runOnUiThread(new Runnable() {
            int i = 0;
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
    }

    private void sendData(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentWelcome.this, framedPacket, getContext());
    }

    @Override
    public void onUnSave(String data) {
        try {
            JSONArray jArr = new JSONArray(preferences.getString("savedMac", ""));
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                String mString = obj.getString("mac");
                if (mString.equals(data)) {
                    jArr.remove(i);
                }
            }
            preferences.edit().putString("savedMac", jArr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadFav();
        Log.e(TAG, "onSaveClicked: " + preferences.getString("savedMac", ""));
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        dataReceived = true;
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("01")) {
            if (spiltData[2].equals("ACK")) {
                if (spiltData[1].equals("00")) {
                    mActivity.dismissProgress();
                    mActivity.updateNavigationUi(R.navigation.navigation);
                } else if (spiltData[1].equals("01")) {
                    mAppClass.showSnackBar(getContext(), "Dispensing Please Wait...");
                }
            }
        } else if (spiltData[0].substring(5, 7).equals("03")) {
            String[] status = spiltData[1].split(","), boilTime = spiltData[2].split(","), bevarageName = spiltData[3].split(",");
            mActivity.dismissProgress();
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
                        switch (bevarageName[1]) {
                            case "01":
                            case "06":
                            case "05":
                            case "03":
                            case "02":
                                mAppClass.showSnackBar(getContext(), "Dispensing Milk & Water");
                                break;
                            case "04":
                            case "07":
                                mAppClass.showSnackBar(getContext(), "Dispensing  Water");
                                break;
                        }
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
            sendPacket(data);
        } // Pan Release
        else if (spiltData[0].substring(5, 7).equals("05")) {
            if (spiltData[1].equals("ACK")) {
                Toast.makeText(getContext(), "Pan Release Ack", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Dispense Canceled !", Toast.LENGTH_SHORT).show();
                mActivity.updateNavigationUi(R.navigation.navigation);
                if (panAlert.isShowing()) {
                    panAlert.dismiss();
                }
            }
        }
    }

    private void sendPacket(String packet) {
        mAppClass.sendDataDispense(getActivity(), FragmentWelcome.this, packet, getContext());
    }

    private void changeDispenseMsg(String beverageName, int resourceID) {
        tv.setText(beverageName);
        iv.setBackgroundResource(resourceID);
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

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }

    void bluetoothEnable() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();
        mBluetoothAdapter.startDiscovery();
    }
}
