package com.pradeep.karak.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardBinding;

import static com.pradeep.karak.Others.ApplicationClass.KEY_BEVERAGE_SELECTION;

public class FragmentDashBoard extends Fragment implements View.OnClickListener {
    FragmentDashboardBinding mBinding;
    ApplicationClass mAppclass;
    BaseActivity mActivity;

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
            checkPassword();
        });
        mBinding.vKarak.setOnClickListener(this);
        mBinding.vGingerKarak.setOnClickListener(this);
        mBinding.vSulaimani.setOnClickListener(this);
        mBinding.vMasalaKarak.setOnClickListener(this);
        mBinding.vCardmonKarak.setOnClickListener(this);
        mBinding.vHotMilk.setOnClickListener(this);
        mBinding.vHotWater.setOnClickListener(this);
    }

    private void checkPassword() {
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
            if (editText.getText().toString().equals("0000")) {
                mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentConfiguration);
                alertDialog.dismiss();
            }
        }));
    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.v_karak:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,01");
                break;

            case R.id.v_gingerKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,02");
                break;

            case R.id.v_sulaimani:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,04");
                break;

            case R.id.v_masalaKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,03");
                break;

            case R.id.v_cardmonKarak:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,05");
                break;

            case R.id.v_hotMilk:
                b.putString(KEY_BEVERAGE_SELECTION, "02,01,06");
                break;

            case R.id.v_hotWater:
                b.putString(KEY_BEVERAGE_SELECTION , "02,01,07");
                break;
        }
        mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentDashBoardCups, b);
    }
}