package com.pradeep.karak.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
          /*  Bundle b = new Bundle();
            b.putString("CUPCOUNT", "myData");
            checkPassword("0000", b);*/
        });
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
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!dataReceived) {
                            Toast.makeText(getContext(), "Timed Out!", Toast.LENGTH_SHORT).show();
                            mActivity.dismissProgress();
                        }
                    }
                }, 10000);
            }
        });
    }

    private void checkPassword(String password, Bundle b) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_password, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        EditText editText = dialogView.findViewById(R.id.edt_password);
        View view = dialogView.findViewById(R.id.dialogPass_submit);

        view.setOnClickListener((view1 -> {
            if (editText.getText().toString().equals(password)) {
                mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentConfiguration, b);
                alertDialog.dismiss();
            } else {
                Toast.makeText(getContext(),
                        "Password wrong!", Toast.LENGTH_SHORT).show();
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
        ///  data = "PSIPS07;01,0000;02,0000;03,0000;04,12345;05,54321;06,98765;07,56789;08,55555;09,77777;10,16164;CRC;PSIPE";
        dataReceived = true;
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("07")) {
            mActivity.dismissProgress();
            String[] adminPassword = spiltData[2].split(","), maintenancePassword = spiltData[2].split(","), cupcountPassword = spiltData[3].split(",");

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