package com.pradeep.karak.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentMaSubchildFactoryResetBinding;

import static com.pradeep.karak.Others.ApplicationClass.FACTORY_RESET_MESSAGE_ID;

public class FragmentMaSubChildFactoryReset extends Fragment implements BluetoothDataCallback {

    FragmentMaSubchildFactoryResetBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    AlertDialog alertDialog;
    BaseActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ma_subchild_factory_reset, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mBinding.viewFactoryReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factoryResetDialog();
            }
        });
    }

    private void factoryResetDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setCancelable(false);
        View dialogView = inflater.inflate(R.layout.dailog_factory_reset, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View ok = dialogView.findViewById(R.id.factory_reset_ok);
        View cancel = dialogView.findViewById(R.id.factory_reset_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(mAppClass.framePacket(FACTORY_RESET_MESSAGE_ID));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentMaSubChildFactoryReset.this, framedPacket, getContext());
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
            String[] splitData = data.split(";");
            if (splitData[0].substring(5, 7).equals("16"))
                if (splitData[1].equals("ACK")) { alertDialog.dismiss();
                mAppClass.showSnackBar(getContext(),"Factory Reset Successfully");
                }
            mActivity.dismissProgress();

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}