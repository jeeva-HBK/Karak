package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.pradeep.karak.databinding.FragmentAdDrSubchildMasalaBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAKA_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_MASALA;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_TEA;

// Created on 19 Mar 2021 by silambu
public class FragmentAdDrSubChildMasala extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildMasalaBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "DrMasala";
    int maxValue = 100;
    int water;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_masala, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtMasalaTeaGms.append(DR_MASALA_KARAK_TEA);
        mBinding.txtMasalaMasala.append(DR_MASALA_KARAK_MASALA);
        mBinding.txtMasalaGmsMilk.append(DR_MASALA_KARAK_MILK);
        mBinding.txtMasalaGmsWater.append(DR_MASALA_KARAKA_WATER);
        new MultiTextWatcher().registerEditText(mBinding.txtMasalaTeaGms)
                .registerEditText(mBinding.txtMasalaMasala).registerEditText(mBinding.txtMasalaGmsMilk)
                .registerEditText(mBinding.txtMasalaGmsWater).setCallback(this);
    }


    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_masala_tea_gms:
                DR_MASALA_KARAK_TEA = mAppClass.formDigits(3, mBinding.txtMasalaTeaGms.getText().toString());
                break;
            case R.id.txt_masala_masala:
                DR_MASALA_KARAK_MASALA = mAppClass.formDigits(3, mBinding.txtMasalaMasala.getText().toString());
                break;
            case R.id.txt_masala_gms_milk:
                DR_MASALA_KARAK_MILK = mAppClass.formDigits(3, mBinding.txtMasalaGmsMilk.getText().toString());
                if (mBinding.txtMasalaGmsMilk.getText().toString().length() > 0) {
                    String getValue = mBinding.txtMasalaGmsMilk.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtMasalaGmsWater.setText(water + "");
                    if (Integer.parseInt(mBinding.txtMasalaGmsMilk.getText().toString()) > 100) {
                        DR_MASALA_KARAK_MILK = "100;";
                        mBinding.txtMasalaGmsMilk.setText("100");
                    }
                }
                break;
            case R.id.txt_masala_gms_water:
                DR_MASALA_KARAKA_WATER = mAppClass.formDigits(3, mBinding.txtMasalaGmsWater.getText().toString()) ;

                break;
        }

    }

}