package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildMachinenumberBinding;

import static com.pradeep.karak.Others.ApplicationClass.MACHINE_NUMBER_MESSAGE_ID;

public class FragmentAdSubChildMachineNumber extends Fragment implements BluetoothDataCallback {
    FragmentAdSubchildMachinenumberBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    String SerialNo;
    BaseActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_machinenumber, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mBinding.textViewSerialNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(framePacket());
            }
        });
    }

    private void send(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentAdSubChildMachineNumber.this, framedPacket, getContext());
    }

    private String framePacket() {
        return mAppClass.framePacket(MACHINE_NUMBER_MESSAGE_ID + "KARAK-" + mAppClass.formDigits(5, mBinding.edtSerialNo.getText().toString()) + ";");
    }

    @Override
    public void OnDataReceived(String data) {
        handleDataResponse(data);
    }

    private void handleDataResponse(String data) {
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("18")) {
            if (spiltData[1].equals("ACK")) {
                mAppClass.disconnect();
                mActivity.updateNavigationUi(R.navigation.scan);
                Toast.makeText(mContext, "Machine Number Added Successfully !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
