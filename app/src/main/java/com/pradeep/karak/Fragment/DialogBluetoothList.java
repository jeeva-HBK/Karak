package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.BluetoothScannerCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.DialogBluetoothlistBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogBluetoothList extends Fragment {
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
        mAppClass.framePacket("01;");

        deviceList = new ArrayList<>();
        scannedDevices = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_single_choice, deviceList);
        startScan();
        /* ArrayList<String> listName = new ArrayList<>();
        listName.add("HMSoft");
        listName.add("RealMe");
        listName.add("Boat");
        listName.add("Sony");
        listName.add("OnePlus");
        listName.add("Pixel");

        ArrayList<String> listMac = new ArrayList<>();
        listMac.add("E4:14:C8:B6:K8:11");
        listMac.add("D5:10:T2:B6:J4:10");
        listMac.add("H6:31:H5:B6:O5:08");
        listMac.add("O7:22:T9:B6:P2:99");
        listMac.add("L8:88:Y2:B6:F8:23");
        listMac.add("P9:24:H4:B6:G0:16");
        deviceList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for (int i = 0; i < listName.size(); i++) {
            Map<String, String> datum = new HashMap<String, String>();
            datum.put("Name", listName.get(i));
            datum.put("Company", listMac.get(i));
            data.add(datum);
        }


        */


        mBinding.rvBluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivity.updateNavigationUi();
            }
        });

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
                            //  mBinding.btnScan.setText(R.string.scan);
                            //  mBinding.btnScan.setEnabled(true);
                            if (devices.size() == 0) {
                                Toast.makeText(mContext, "NoDeviceFound", Toast.LENGTH_SHORT).show();
                            }
                            //  mBinding.btnScan.performClick();
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
                                    Map<String, String> datum = new HashMap<String, String>();
                                    String[] mArr = listItem.split("\n");
                                    datum.put(mArr[0], mArr[1]);
                                    mDeviceList.add(datum);
                                    deviceList.add(listItem);
                                }
                            }
                            listAdapter.notifyDataSetChanged();
                            mBinding.rvBluetoothList.setAdapter(listAdapter);
                            mActivity.dismissProgress();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
