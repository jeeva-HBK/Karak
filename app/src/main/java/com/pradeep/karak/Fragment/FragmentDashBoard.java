package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardBinding;

import static com.pradeep.karak.Others.ApplicationClass.ADMIN_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.KEY_BEVERAGE_SELECTION;
import static com.pradeep.karak.Others.ApplicationClass.MAINTENANCE_PASSWORD;

public class FragmentDashBoard extends Fragment implements View.OnClickListener, BluetoothDataCallback {
    FragmentDashboardBinding mBinding;
    ApplicationClass mAppclass;
    BaseActivity mActivity;
    private boolean dataReceived = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mActivity = (BaseActivity) getActivity();
        mActivity.dismissProgress();
        mBinding.btnOperator.setOnClickListener(view1 -> {
            sendPacket(mAppclass.framePacket("07;01;"));
        });

        mBinding.btnDisConnect.setOnClickListener((view1 -> {
            confirmDisconnect();
        }));

        mBinding.vKarak.setOnClickListener(this);
        mBinding.vGingerKarak.setOnClickListener(this);
        mBinding.vSulaimani.setOnClickListener(this);
        mBinding.vMasalaKarak.setOnClickListener(this);
        mBinding.vCardmonKarak.setOnClickListener(this);
        mBinding.vHotMilk.setOnClickListener(this);
        mBinding.vHotWater.setOnClickListener(this);
    }

    private void sendPacket(String framedPacket) {
        mActivity.showProgress();
        dataReceived = false;
        mAppclass.sendData(getActivity(), FragmentDashBoard.this, framedPacket, getContext());

    }

    private void confirmDisconnect() {
        AlertDialog.Builder disconnectDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_disconnect, null);
        disconnectDialog.setView(dialogView);
        AlertDialog alertDialog = disconnectDialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView txtView = dialogView.findViewById(R.id.dialogPass_dismiss);
        TextView txtView1 = dialogView.findViewById(R.id.dialogPasst_dismiss);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppclass.disconnect();
           //     disconnectBle();
                mActivity.updateNavigationUi(R.navigation.scan);
                alertDialog.dismiss();
            }
        });
        txtView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void checkPassword(String password, Bundle b) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dailog_admin_password, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        EditText editText = dialogView.findViewById(R.id.edt_admin_password);
        TextView textView = dialogView.findViewById(R.id.dialogPass_t);

        textView.setOnClickListener((view1 -> {
            if (editText.getText().toString().equals(password)) {
                mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentConfiguration, b);
                alertDialog.dismiss();
            } else {
                mAppclass.showSnackBar(getContext(), "Password wrong!");
            }
        }));
    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.v_karak:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,01");
                break;

            case R.id.v_gingerKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,02");
                break;

            case R.id.v_sulaimani:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,04");
                break;

            case R.id.v_masalaKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,03");
                break;

            case R.id.v_cardmonKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,05");
                break;

            case R.id.v_hotMilk:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,06");
                break;

            case R.id.v_hotWater:
                b.putString(KEY_BEVERAGE_SELECTION, "02;01,07");
                break;
        }
        mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentDashBoardCups, b);
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        dataReceived = true;
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("07")) {
            mActivity.dismissProgress();
            String[] adminPassword = spiltData[1].split(","), maintenancePassword = spiltData[2].split(","), cupcountPassword = spiltData[3].split(",");

            ADMIN_PASSWORD = adminPassword[1];
            MAINTENANCE_PASSWORD = maintenancePassword[1];
            CUP_COUNT_PASSWORD = cupcountPassword[1];

            Bundle b = new Bundle();
            b.putString("CUPCOUNT", data);
            checkPassword(ADMIN_PASSWORD, b);
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}