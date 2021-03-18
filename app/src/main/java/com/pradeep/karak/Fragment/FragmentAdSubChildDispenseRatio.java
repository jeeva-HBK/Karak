package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Adapter.DispenseRatioPagerAdapter;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildDispenseratioBinding;

public class FragmentAdSubChildDispenseRatio extends Fragment {
    FragmentAdSubchildDispenseratioBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_subchild_dispenseratio, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObservableInt pageCount = new ObservableInt(0);
        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);

        pageCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switch (pageCount.get()) {
                    case 0:
                        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(0);
                        break;
                    case 1:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(1);
                        break;
                    case 2:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(2);
                        break;
                    case 3:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(3);
                        break;
                    case 4:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(4);
                        break;
                    case 5:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(5);
                        break;
                    case 6:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.GONE);
                        mBinding.viewPagerMaintenance.setCurrentItem(6);
                        break;
                }
            }
        });

        mBinding.IvAdLeftArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() - 1);
        }));

        mBinding.IvAdRightArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() + 1);

        }));

        mBinding.viewPagerMaintenance.setAdapter(new DispenseRatioPagerAdapter(getActivity()));
        mBinding.viewPagerMaintenance.setUserInputEnabled(false);
    }
}
