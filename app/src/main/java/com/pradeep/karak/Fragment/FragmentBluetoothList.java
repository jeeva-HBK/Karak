package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothConnectCallback;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.BluetoothScannerCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.DialogBluetoothlistBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentBluetoothList extends Fragment implements BluetoothDataCallback {
    DialogBluetoothlistBinding mBinding;
    BaseActivity mActivity;
    ArrayList<String> deviceList;
    ArrayList<Map<String, String>> mDeviceList;
    Context mContext;
    ApplicationClass mAppClass;
    private static final String TAG = "DialogBluetoothList";

    // Ble
    ArrayAdapter<String> listAdapter;
    List<BluetoothDevice> scannedDevices;
    BluetoothDevice mBleDevice;

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
        mBinding.btnScan.setAlpha(.5f);
        mBinding.btnScan.setEnabled(false);
        mActivity.showProgress();
        deviceList = new ArrayList<>();
        mDeviceList = new ArrayList<>();

        deviceList = new ArrayList<>();
        scannedDevices = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(mContext, R.layout.item_bluethoot_list, deviceList);
        startScan();

        mBinding.rvBluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBinding.txtConnect.setText("Connecting");
                mActivity.showProgress();
                BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
                helper.disConnect();
                mBleDevice = scannedDevices.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            helper.connectBLE(mContext, mBleDevice, new BluetoothConnectCallback() {
                                @Override
                                public void OnConnectSuccess() {
                                    try {
                                        stopScan();
                                        sendPacket(mAppClass.framePacket("01;"));
                                    } catch (Exception e) {
                                        stopScan();
                                        Log.e(TAG, "OnConnectSuccess: Catch");
                                        mActivity.dismissProgress();
                                        mBinding.txtConnect.setText("Rescan");
                                        mAppClass.showSnackBar(mContext, "Error Occurred");
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void OnConnectFailed(Exception e) {
                                    stopScan();
                                    mBinding.txtConnect.setText("Rescan");
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
                            stopScan();
                            mBinding.txtConnect.setText("Rescan");
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        mBinding.txtConnect.setOnClickListener((view1 -> {
            if (mBinding.txtConnect.getText().toString().equals("Rescan")) {
                mAppClass.showSnackBar(mContext, "Refreshing");
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
        listAdapter.notifyDataSetChanged();
        //mBinding.btnScan.setText(R.string.scanning);
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
                                mAppClass.showSnackBar(mContext, "NoDeviceFound");
                                stopScan();
                                mBinding.txtConnect.setText("Rescan");
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
                                }
                            }
                            listAdapter.notifyDataSetChanged();
                            mBinding.rvBluetoothList.setAdapter(listAdapter);
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

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("01")) {
            if (spiltData[2].equals("ACK")) {
                if (spiltData[1].equals("00")) {
                    mActivity.updateNavigationUi(R.navigation.navigation);
                } else if (spiltData[1].equals("01")) {
                    Toast.makeText(mContext, "Dispensing Please Wait!", Toast.LENGTH_SHORT).show();
                } // PSIPS07;01,0000;02,0000;03,0000;04,00000;05,00000;06,00000;07,00000;08,00000;09,00000;10,00000;11,000;
            }
            helper.setConnected(true);
        } else if (spiltData[0].substring(5, 7).equals("03")) {
            String[] status = spiltData[1].split(","), boilTime = spiltData[2].split(","), bevarageName = spiltData[3].split(",");

            if (status[1].equals("1")) {
                showPanNotAvailable();
            } else {
                switch (bevarageName[1]) {
                    case "1":
                        showDispenseAlert("Karak", R.drawable.dispense_back);
                        break;
                    case "2":
                        showDispenseAlert("Ginger Karak", R.drawable.bg_app_button);
                        break;
                    case "3":
                        showDispenseAlert("Sulaimani", R.drawable.bg_top_curved);
                        break;
                    case "4":
                        showDispenseAlert("Masala Karak", R.drawable.ic_bg_box);
                        break;
                    case "5":
                        showDispenseAlert("Cardomom Karak", R.drawable.ic_camera);
                        break;
                    case "6":
                        showDispenseAlert("Hot milk", R.drawable.ic_heart);
                        break;
                    case "7":
                        showDispenseAlert("Hot Water", R.drawable.ic_operator);
                        break;
                }
            }
        } // Pan Release
        else if (spiltData[0].substring(5, 7).equals("05")) {
            if (spiltData[1].equals("ACK")) {
                Toast.makeText(getContext(), "Pan Release Ack", Toast.LENGTH_SHORT).show();
            }
        }
        // Dispense Completed
        else if (spiltData[0].substring(5, 7).equals("04")) {
            sendPacket(mAppClass.framePacket("04;ACK:"));
        }

        mActivity.dismissProgress();
    }

    private void showPanNotAvailable() {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_pan_not_available, null);
        dialogBuilder2.setView(dialogView2);

        TextView release = dialogView2.findViewById(R.id.txt_Release);

        release.setOnClickListener((view -> {
            sendPacket(mAppClass.framePacket("05;"));
        }));

        AlertDialog alertDialog2 = dialogBuilder2.create();
        alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog2.show();
    }

    private void showDispenseAlert(String bevarageName, int resourceID) {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_dispense, null);
        dialogBuilder2.setView(dialogView2);

        ImageView iv = dialogView2.findViewById(R.id.dispenseAlertImageView);
        TextView tV = dialogView2.findViewById(R.id.dispenseAlertTextView);
        iv.setBackgroundResource(resourceID);
        tV.setText(bevarageName);

        AlertDialog alertDialog2 = dialogBuilder2.create();
        alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog2.show();
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
