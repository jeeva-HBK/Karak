package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
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
    List<Map<String, String>> deviceList;
    Context mContext;
    ApplicationClass mAppClass;

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
        mAppClass = (ApplicationClass)getActivity().getApplication();
        mContext = getContext();
        mBinding.btnScan.setAlpha(.5f);
        mBinding.btnScan.setEnabled(false);
        mActivity.showProgress();
        deviceList = new ArrayList<>();
        mAppClass.framePacket("01;");



        ArrayList<String> listName = new ArrayList<>();
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

        SimpleAdapter listAdapter = new SimpleAdapter(getContext(), data,
                R.layout.item_ble_list,
                new String[]{"Name", "Company"},
                new int[]{R.id.text_main,R.id.text_sub_main});


        mBinding.rvBluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivity.updateNavigationUi();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.btnScan.setAlpha(1.0f);
                mBinding.btnScan.setEnabled(true);
                mActivity.dismissProgress();
                mBinding.rvBluetoothList.setAdapter(listAdapter);
              //  mBinding.btnScan.setText("RESCAN");
                mActivity.dismissProgress();
            }
        }, 2000);

    }

}
