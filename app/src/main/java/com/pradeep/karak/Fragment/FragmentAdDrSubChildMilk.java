package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Callbacks.TextWatcherWithInstance;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.Others.MultiTextWatcher;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdDrSubchildMilkBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK_WATER;

// Created on 19 Mar 2021 by silambu
public class FragmentAdDrSubChildMilk extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildMilkBinding mBinding;
    public static final String TAG = "DrMilk";
    ApplicationClass mAppClass;
    Context mContext;
    int maxValue = 100;
    int water;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_milk, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtMilkTeaGms.append(DR_MILK);
        mBinding.txtMilkWater.append(DR_MILK_WATER);
        new MultiTextWatcher().registerEditText(mBinding.txtMilkTeaGms).registerEditText(mBinding.txtMilkWater)
                .setCallback(this);
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_milk_tea_gms:
                DR_MILK = mAppClass.formDigits(3, mBinding.txtMilkTeaGms.getText().toString());
                if (mBinding.txtMilkTeaGms.getText().toString().length() > 0) {
                    String getValue = mBinding.txtMilkTeaGms.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtMilkWater.setText(water+"");
                    if (Integer.parseInt(mBinding.txtMilkTeaGms.getText().toString()) > 100) {
                        DR_MILK = "100;";
                        mBinding.txtMilkTeaGms.setText("100");
                    }
                }
                break;
            case R.id.txt_milk_water:
                DR_MILK_WATER = mAppClass.formDigits(3, mBinding.txtMilkWater.getText().toString()) ;
                if (mBinding.txtMilkWater.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtMilkWater.getText().toString()) > 100) {
                        DR_MILK_WATER = "100;";
                        mBinding.txtMilkWater.setText("100");
                    }
                }
                break;

        }
    }

}
