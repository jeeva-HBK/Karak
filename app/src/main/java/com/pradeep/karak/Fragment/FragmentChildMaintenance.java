package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Adapter.MaintenancePagerAdapter;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentChildMaintenanceBinding;

// Created on 21 Mar 2021 by silambu
public class FragmentChildMaintenance extends Fragment {
    private FragmentChildMaintenanceBinding mBinding;
    public ObservableBoolean oVisible = new ObservableBoolean(true);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_maintenance, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oVisible.set(true);
        mBinding.setAVisible(oVisible.get());

        oVisible.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mBinding.setAVisible(oVisible.get());
            }
        });

        mBinding.IvMnLeftArrow.setOnClickListener((view1 -> {
            mBinding.viewPagerMaintenance.setCurrentItem(0);
            oVisible.set(true);
        }));
        mBinding.IvMnRightArrow.setOnClickListener((view1 -> {
            mBinding.viewPagerMaintenance.setCurrentItem(1);
            oVisible.set(false);
        }));

        mBinding.viewPagerMaintenance.setAdapter(new MaintenancePagerAdapter(getActivity()));
        mBinding.viewPagerMaintenance.setUserInputEnabled(false);

    }


}
