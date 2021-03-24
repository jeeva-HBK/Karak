package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardCupsBinding;

public class FragmentDashBoardCups extends Fragment {
    FragmentDashboardCupsBinding mBinding;
    ApplicationClass mAppclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_cups, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mBinding.view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppclass.navigateTo(getActivity(), R.id.action_fragmentDashBoardCups_to_fragmentDashBoardSugar);
            }
        });
        mBinding.viewBackCups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppclass.popStackBack(getActivity());
            }
        });
    }
}