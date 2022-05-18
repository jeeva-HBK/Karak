package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
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
import com.pradeep.karak.databinding.FragmentAdDrSubchildWaterBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_HOT_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_HOT_WATER_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK_WATER;

// Created on 19 Mar 2021 by silambu
public class FragmentAdDrSubChildWater extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildWaterBinding mBinding;
    public static final String TAG = "DrWater";
    ApplicationClass mAppClass;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_water, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtWaterWaterGms.append(DR_HOT_WATER);
        mBinding.txtWaterSugarGms.append(DR_HOT_WATER_SUGAR);
        new MultiTextWatcher().registerEditText(mBinding.txtWaterWaterGms)
                .registerEditText(mBinding.txtWaterSugarGms).setCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.txtServingPercu.setText(DR_CUP_ML+"ml "+getString(R.string.no_ml_percup));
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_water_water_gms:
                DR_HOT_WATER = mAppClass.formDigits(3, mBinding.txtWaterWaterGms.getText().toString()) ;
                if (mBinding.txtWaterWaterGms.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtWaterWaterGms.getText().toString()) > 100) {
                        DR_HOT_WATER = "100;";
                        mBinding.txtWaterWaterGms.setText("100");
                    }
                }
                break;
            case R.id.txt_water_sugar_gms:
                DR_HOT_WATER_SUGAR = mAppClass.formDigits(3, mBinding.txtWaterSugarGms.getText().toString()) ;
                break;
        }
    }


}
