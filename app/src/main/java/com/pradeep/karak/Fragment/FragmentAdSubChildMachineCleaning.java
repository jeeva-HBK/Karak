package com.pradeep.karak.Fragment;

import static com.pradeep.karak.Others.ApplicationClass.MACHINE_FLUSH_ID;

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
import com.pradeep.karak.databinding.FragmentAdSubchildFlushBinding;

public class FragmentAdSubChildMachineCleaning extends Fragment implements BluetoothDataCallback {

    FragmentAdSubchildFlushBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    BaseActivity mActivity;
    int flushType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_flush, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mBinding.btnFlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flushType > 0) {
                    sendData(framePacket());
                } else {
                    Toast.makeText(getContext(), "Select any beverage to flush", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.imgCoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushType = 1;
                mBinding.imgCoff.setBackgroundResource(R.drawable.ic_selected_bev);
                mBinding.imgMilk.setBackgroundResource(0);
            }
        });
        mBinding.imgMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushType = 2;
                mBinding.imgMilk.setBackgroundResource(R.drawable.ic_selected_bev);
                mBinding.imgCoff.setBackgroundResource(0);
            }
        });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentAdSubChildMachineCleaning.this, framedPacket, getContext());
    }

    private String framePacket() {
        return mAppClass.framePacket(MACHINE_FLUSH_ID + ";" + flushType + ";");
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] splitData = data.split(";");
        /*if (splitData[0].substring(5, 7).equals("21")) {
            if (splitData[1].equals("ACK")) {
                mAppClass.showSnackBar(getContext(), getString(R.string.UpdateSuccessfully));
            }
        }*/
        if (splitData[0].substring(5, 7).equals("22")) {
            mActivity.dismissProgress();
            if (splitData[2].equals("ACK")) {
                mAppClass.showSnackBar(getContext(), splitData[1].equals("1") ? "Coffee flush completed" :
                        "Tea flush completed");
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {

    }
}
