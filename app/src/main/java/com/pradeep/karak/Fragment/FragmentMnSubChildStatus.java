package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentMnSubchildStatusBinding;

import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE;


public class FragmentMnSubChildStatus extends Fragment implements BluetoothDataCallback {
    FragmentMnSubchildStatusBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_mn_subchild_status, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mAppClass.sendData(getActivity(), FragmentMnSubChildStatus.this,
                mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE), getContext());
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("07")) {
            String[] inductionHeater = spiltData[1].split(",");
            String[] proximitySensor = spiltData[2].split(",");
            if (inductionHeater[1].equals("0")) {
                mBinding.txtStatus1.setText("Normal");
            } else if (inductionHeater[1].equals("1")) {
                mBinding.txtStatus1.setText("Alert");
            }
            if (proximitySensor[1].equals("0")) {
                mBinding.txtStatus2.setText("Normal");
            } else if (proximitySensor[1].equals("1")) {
                mBinding.txtStatus2.setText("Alert");
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
