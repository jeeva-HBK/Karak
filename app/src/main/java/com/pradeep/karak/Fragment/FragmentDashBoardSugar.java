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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_dispense, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();
                break;
        }
    }
}