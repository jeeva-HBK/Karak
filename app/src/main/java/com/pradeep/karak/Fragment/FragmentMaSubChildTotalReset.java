package com.pradeep.karak.Fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentMaSubchildTotalResetBinding;

import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE;
import static com.pradeep.karak.Others.ApplicationClass.TOTAL_RESET_MESSAGE_ID;

public class FragmentMaSubChildTotalReset extends Fragment implements BluetoothDataCallback {

    FragmentMaSubchildTotalResetBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ma_subchild_total_reset, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE));
        mBinding.TotalReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalResetDialog();
            }
        });
    }

    private void TotalResetDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setCancelable(false);
        View dialogView = inflater.inflate(R.layout.dialog_total_reset, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View ok = dialogView.findViewById(R.id.total_reset_ok);
        View cancel = dialogView.findViewById(R.id.total_reset_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(mAppClass.framePacket(TOTAL_RESET_MESSAGE_ID));
                alertDialog.dismiss();

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
        mAppClass.sendData(getActivity(), FragmentMaSubChildTotalReset.this, framedPacket, getContext());
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] handleData = data.split(";");
        if (handleData[0].substring(5, 7).equals("07")) {
            String[] FirmWareVersion = handleData[3].split(",");
            mBinding.txtFimwareAppVersion.setText(FirmWareVersion[1]);
            String version = "";
            try {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!version.equals("")) {
                mBinding.txtMobileAppVersion.setText("v"+version);
            } else {
                mBinding.txtMobileAppVersion.setText("version_Error");
            }
        } else if (handleData[0].substring(5, 7).equals("17")) {
            if (handleData[1].equals("ACK")) {
                mAppClass.showSnackBar(getContext(), "Update successfully");
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
