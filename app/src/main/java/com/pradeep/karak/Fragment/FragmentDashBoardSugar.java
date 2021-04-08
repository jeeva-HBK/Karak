package com.pradeep.karak.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.pradeep.karak.databinding.FragmentDashboardSugarBinding;

import static com.pradeep.karak.Others.ApplicationClass.BevaragePacket;
import static com.pradeep.karak.Others.ApplicationClass.BevarageSubPacketSugar;
import static com.pradeep.karak.Others.ApplicationClass.KEY_CUP;

public class FragmentDashBoardSugar extends Fragment implements View.OnClickListener, BluetoothDataCallback {
    FragmentDashboardSugarBinding mBinding;
    ApplicationClass mAppclass;
    BaseActivity mActivity;
    private static final String TAG = "FragmentDashBoardSugar";

    AlertDialog dispenseAlert, panAlert, confirmDispenseAlert;
    ImageView iv;
    TextView tv;
    boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_sugar, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
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
        confirmDispenseAlert = dialogBuilder.create();
        confirmDispenseAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDispenseAlert.show();
        TextView ok = dialogView.findViewById(R.id.txt_ok);
        TextView cancel = dialogView.findViewById(R.id.txt_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDispenseAlert.dismiss();
                Log.e(TAG, "onClick: " + finalDispensePacket);
                sendPacket(mAppclass.framePacket(finalDispensePacket));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppclass.navigateTo(getActivity(), R.id.action_fragmentDashBoardSugar_to_dashboard);
                confirmDispenseAlert.dismiss();
            }
        });
    }

    private void sendPacket(String packet) {
        mAppclass.sendData(getActivity(), FragmentDashBoardSugar.this, packet, getContext());
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        if (data.equals("PSIPSTIMEOUT;CRC;PSIPE")) {
            mActivity.dismissProgress();
            mAppclass.showSnackBar(getContext(), "Response Error");
        } else {
            mActivity.dismissProgress();
            String[] spiltData = data.split(";");
            if (spiltData[0].substring(5, 7).equals("02")) {
                if (spiltData[1].equals("ACK")) {
                    mActivity.showProgress();
                }
            } // Dispensing Status
            else if (spiltData[0].substring(5, 7).equals("03")) {
                String[] status = spiltData[1].split(","), boilTime = spiltData[2].split(","), bevarageName = spiltData[3].split(",");
                mActivity.dismissProgress();

                if (status[1].equals("01")) {
                    showPanNotAvailable();
                } else {
                    switch (bevarageName[1]) {
                        case "01":
                           if (!isVisible) {
                                showDispenseAlert("Karak", R.drawable.karak);
                            } else {
                               changeDispenseMsg("Karak", R.drawable.karak);
                            }
                            break;
                        case "02":
                            if (!isVisible) {
                                showDispenseAlert("Ginger Karak", R.drawable.karakginger);
                            } else {
                                changeDispenseMsg("Ginger Karak", R.drawable.karakginger);
                            }
                            break;
                        case "03":
                            if (!isVisible) {
                                showDispenseAlert("Sulaimani", R.drawable.sulaimani);
                            } else {
                                changeDispenseMsg("Sulaimani", R.drawable.sulaimani);
                            }
                            break;
                        case "04":
                            if (!isVisible) {
                                showDispenseAlert("Masala Karak", R.drawable.karakmasaka);
                            } else {
                                changeDispenseMsg("Masala Karak", R.drawable.karakmasaka);
                            }
                            break;
                        case "05":
                            if (!isVisible) {
                                showDispenseAlert("Cardomom Karak", R.drawable.karakcardmom);
                            } else {
                                changeDispenseMsg("Cardomom Karak", R.drawable.karakcardmom);
                            }
                            break;
                        case "06":
                            if (!isVisible) {
                                showDispenseAlert("Hot milk", R.drawable.hotmilk);
                            } else {
                                changeDispenseMsg("Hot milk", R.drawable.hotmilk);
                            }
                            break;
                        case "07":
                            if (!isVisible) {
                                showDispenseAlert("Hot Water", R.drawable.water);
                            } else {
                                changeDispenseMsg("Hot Water", R.drawable.water);
                            }
                            break;
                    }

                    switch (status[1]) {
                        case "02":
                            mAppclass.showSnackBar(getContext(), "Dispensing Milk & Water");
                            break;
                        case "03":
                            mAppclass.showSnackBar(getContext(), "Preheating");
                            break;
                        case "04":
                            mAppclass.showSnackBar(getContext(), "Dispensing ingredients");
                            break;
                        case "05":
                            mAppclass.showSnackBar(getContext(), "Boiling");
                            break;
                    }
                }
                sendPacket(data);
            } // Pan Release
            else if (spiltData[0].substring(5, 7).equals("05")) {
                if (spiltData[1].equals("ACK")) {
                    mActivity.showProgress();
                }
            }
            // Dispense Completed
            else if (spiltData[0].substring(5, 7).equals("04")) {
                sendPacket(mAppclass.framePacket("04;ACK:"));
                if (dispenseAlert.isShowing()) {
                    dispenseAlert.dismiss();
                }
                mAppclass.showSnackBar(getContext(), "Dispense Completed !");
                mActivity.updateNavigationUi(R.navigation.navigation);
            }
            // Cancel Dispense
            else if (spiltData[0].substring(5, 7).equals("06")) {
                if (spiltData[1].equals("ACK")) {
                    mAppclass.showSnackBar(getContext(),getString(R.string.DispenseCanceled));
                    mActivity.updateNavigationUi(R.navigation.navigation);
                    if (panAlert.isShowing()) {
                        panAlert.dismiss();
                    }
                }
            }
        }
    }

    private void showPanNotAvailable() {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_pan_not_available, null);
        dialogBuilder2.setView(dialogView2);
        dialogBuilder2.setCancelable(false);
        TextView release = dialogView2.findViewById(R.id.txt_Release);
        TextView cancel = dialogView2.findViewById(R.id.txt_cancel);

        release.setOnClickListener((view -> {
            sendPacket(mAppclass.framePacket("05;"));
            panAlert.dismiss();
            mActivity.showProgress();
        }));

        cancel.setOnClickListener(view -> {
            sendPacket(mAppclass.framePacket("06;"));
        });

        panAlert = dialogBuilder2.create();
        panAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        panAlert.show();
    }

    private void showDispenseAlert(String bevarageName, int resourceID) {
        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView2 = inflater.inflate(R.layout.dialog_dispense, null);
        dialogBuilder2.setView(dialogView2);
        dialogBuilder2.setCancelable(false);
        iv = dialogView2.findViewById(R.id.dispenseAlertImageView);
        tv = dialogView2.findViewById(R.id.dispenseAlertTextView);
        iv.setBackgroundResource(resourceID);
        tv.setText(bevarageName);

        dispenseAlert = dialogBuilder2.create();
        dispenseAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dispenseAlert.show();
        isVisible = true;
    }

    private void changeDispenseMsg(String beverageName, int resourceID) {
        tv.setText(beverageName);
        iv.setBackgroundResource(resourceID);
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}

