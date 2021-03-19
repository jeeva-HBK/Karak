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

import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardBinding;

public class FragmentDashBoard extends Fragment implements View.OnClickListener {
    FragmentDashboardBinding mBinding;
    ApplicationClass mAppclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mBinding.btnOperator.setOnClickListener(view1 -> {
            checkPassword();
        });

        mBinding.karakView.setOnClickListener(this);
    }

    private void checkPassword() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_password, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
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
        switch (view.getId()) {
            case R.id.karakView:
                mAppclass.navigateTo(getActivity(), R.id.action_dashboard_to_fragmentDashBoardCups);
                break;
        }
    }
}