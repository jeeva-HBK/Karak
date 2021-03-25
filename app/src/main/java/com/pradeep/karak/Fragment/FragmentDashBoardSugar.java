package com.pradeep.karak.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardSugarBinding;

import static com.pradeep.karak.Others.ApplicationClass.BevaragePacket;
import static com.pradeep.karak.Others.ApplicationClass.BevarageSubPacketSugar;
import static com.pradeep.karak.Others.ApplicationClass.KEY_CUP;

public class FragmentDashBoardSugar extends Fragment implements View.OnClickListener, BluetoothDataCallback {
    FragmentDashboardSugarBinding mBinding;
    ApplicationClass mAppclass;
    private static final String TAG = "FragmentDashBoardSugar";

    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_sugar, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mBinding.view.setOnClickListener(this);

        Bundle b = getArguments();
        BevaragePacket = b.getString(KEY_CUP);
        BevarageSubPacketSugar = "";
        Log.e(TAG, "onViewCreated: " + BevaragePacket);
        mBinding.viewBackSugar.setOnClickListener((view1 -> {
            mAppclass.popStackBack(getActivity());
        }));
        mBinding.txtSugar0.setOnClickListener(this);
        mBinding.txtSugar1.setOnClickListener(this);
        mBinding.txtSugar2.setOnClickListener(this);
        mBinding.txtSugar3.setOnClickListener(this);
        mBinding.txtSugar4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSugar0:
                BevarageSubPacketSugar = ";03,00;";
                break;

            case R.id.txtSugar1:
                BevarageSubPacketSugar = ";03,01;";
                break;

            case R.id.txtSugar2:
                BevarageSubPacketSugar = ";03,02;";
                break;

            case R.id.txtSugar3:
                BevarageSubPacketSugar = ";03,03;";
                break;

            case R.id.txtSugar4:
                BevarageSubPacketSugar = ";03,04;";
                break;
        }
        confirmDispense(BevaragePacket + BevarageSubPacketSugar);
    }

    private void confirmDispense(String finalDispensePacket) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setCancelable(false);
        View dialogView = inflater.inflate(R.layout.dailog_confirm_dispense, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View ok = dialogView.findViewById(R.id.dialogPass_ok);
        View cancel = dialogView.findViewById(R.id.dialogPass_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Log.e(TAG, "onClick: " + finalDispensePacket);
                sendPacket(alertDialog, mAppclass.framePacket(finalDispensePacket));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppclass.navigateTo(getActivity(), R.id.action_fragmentDashBoardSugar_to_dashboard);
                alertDialog.dismiss();
            }
        });
    }

    private void sendPacket(AlertDialog alertDialog, String bevarageSubPacketSugar) {
        mAppclass.sendData(getActivity(), FragmentDashBoardSugar.this, bevarageSubPacketSugar, getContext());
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_dispense, null);
        dialogBuilder2.setView(dialogView2);
        AlertDialog alertDialog2 = dialogBuilder2.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog2.show();
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("02")) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}