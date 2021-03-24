package com.pradeep.karak.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardSugarBinding;

public class FragmentDashBoardSugar extends Fragment implements View.OnClickListener {
    FragmentDashboardSugarBinding mBinding;
    ApplicationClass mAppclass;

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
        mBinding.viewBackSugar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = this.getLayoutInflater();
                dialogBuilder.setCancelable(false);
                View dialogView = inflater.inflate(R.layout.dailog_confirm_dispense, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                View ok = dialogView.findViewById(R.id.dialogPass_ok);
                View cancel = dialogView.findViewById(R.id.dialogPass_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView2 = inflater.inflate(R.layout.dialog_dispense, null);
                        dialogBuilder2.setView(dialogView2);
                        AlertDialog alertDialog2 = dialogBuilder2.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog2.show();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAppclass.navigateTo(getActivity(), R.id.action_fragmentDashBoardSugar_to_dashboard);
                        alertDialog.dismiss();
                    }
                });

                break;
        }
    }
}