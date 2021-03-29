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
import com.pradeep.karak.databinding.FragmentAdSubchildSetpasswordBinding;

import static com.pradeep.karak.Others.ApplicationClass.ADMIN_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.ADMIN_PASSWORD_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_PASSWORD_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MAINTENANCE_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.MAINTENANCE_PASSWORD_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.SEND_PASSWORD_MESSAGE_ID;


public class FragmentAdSubChildSetPassword extends Fragment implements BluetoothDataCallback {
    private FragmentAdSubchildSetpasswordBinding mBinding;
    public String AdminPasswordParameter;
    public String CupCountResetParameter;
    public String MaintenancePassword;
    Context mContext;
    ApplicationClass mAppClass;
    private static final String TAG = "SendPassword";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_subchild_setpassword, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mAppClass = (ApplicationClass) getActivity().getApplication();

        mBinding.editTextAdminPassword.setText(ADMIN_PASSWORD);
        mBinding.editTextMaintenancePassword.setText(MAINTENANCE_PASSWORD);
        mBinding.editTextCupCountReset.setText(CUP_COUNT_PASSWORD);

        mBinding.viewSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    sendData(framePacket());
                }

            }
        });

    }

    private void sendData(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentAdSubChildSetPassword.this, framedPacket, getContext());
    }

    private boolean validation() {
        if (mBinding.editTextAdminPassword.getText().toString().length() != 4) {
            mBinding.editTextAdminPassword.setError("min 4 digit");
            mBinding.editTextAdminPassword.requestFocus();
            return false;
        } else if (mBinding.editTextMaintenancePassword.getText().toString().length() != 4) {
            mBinding.editTextMaintenancePassword.setError("min 4 digit");
            mBinding.editTextMaintenancePassword.requestFocus();
            return false;
        } else if (mBinding.editTextCupCountReset.getText().toString().length() != 4) {
            mBinding.editTextCupCountReset.setError("min 4 digit");
            mBinding.editTextCupCountReset.requestFocus();
            return false;
        }
        return true;
    }

    private String framePacket() {
        AdminPasswordParameter = mBinding.editTextAdminPassword.getText().toString() + ";";
        MaintenancePassword = mBinding.editTextMaintenancePassword.getText().toString() + ";";
        CupCountResetParameter = mBinding.editTextCupCountReset.getText().toString() + ";";

        return mAppClass.framePacket(SEND_PASSWORD_MESSAGE_ID + ADMIN_PASSWORD_SUB_ID + AdminPasswordParameter
                + MAINTENANCE_PASSWORD_SUB_ID + MaintenancePassword + CUP_COUNT_PASSWORD_SUB_ID + CupCountResetParameter);
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] splitData = data.split(";");
        if (splitData[0].substring(5, 7).equals("12")) {
            if (splitData[1].equals("ACK")) {
                mAppClass.showSnackBar(getContext(),"Updated SuccessFully");
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
