package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.pradeep.karak.databinding.FragmentAdDrSubchildGingerBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_GINGER;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_WATER;

// Created on 18 Mar 2021 by silambu
public class FragmentAdDrSubChildGinger extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildGingerBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "DrGinger";
    int maxValue = 100;
    int water;


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_ginger, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtGingerTeaGms.append(DR_GINGER_KARAK_TEA);
        mBinding.txtGingerGinger.append(DR_GINGER_KARAK_GINGER);
        mBinding.txtGingerGmsMilk.append(DR_GINGER_KARAK_MILK);
        mBinding.txtGingerGms.append(DR_GINGER_WATER);
        mBinding.txtSugarGms.append(DR_GINGER_SUGAR);
        mBinding.txtServingPercu.setText(DR_CUP_ML+"ml "+getString(R.string.no_ml_percup));
        new MultiTextWatcher().registerEditText(mBinding.txtGingerTeaGms).registerEditText(mBinding.txtGingerGinger)
                .registerEditText(mBinding.txtGingerGmsMilk).registerEditText(mBinding.txtGingerGms)
                .registerEditText(mBinding.txtSugarGms).setCallback(this);
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_ginger_tea_gms:
                DR_GINGER_KARAK_TEA = mAppClass.formDigits(3, mBinding.txtGingerTeaGms.getText().toString());
                break;
            case R.id.txt_ginger_ginger:
                DR_GINGER_KARAK_GINGER = mAppClass.formDigits(3, mBinding.txtGingerGinger.getText().toString());
                break;
            case R.id.txt_ginger_gms_milk:
                DR_GINGER_KARAK_MILK = mAppClass.formDigits(3, mBinding.txtGingerGmsMilk.getText().toString());
                if (mBinding.txtGingerGmsMilk.getText().toString().length() > 0) {
                    String getValue = mBinding.txtGingerGmsMilk.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtGingerGms.setText(water + "");
                    if (Integer.parseInt(mBinding.txtGingerGmsMilk.getText().toString()) > 100) {
                        DR_GINGER_KARAK_MILK = "100;";
                        mBinding.txtGingerGmsMilk.setText("100");
                    }
                }
                break;
            case R.id.txt_ginger_gms:
                DR_GINGER_WATER = mAppClass.formDigits(3, mBinding.txtGingerGms.getText().toString()) ;
                if (mBinding.txtGingerGms.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtGingerGms.getText().toString()) > 100) {
                        DR_GINGER_WATER = "100;";
                        mBinding.txtGingerGms.setText("100");
                    }
                }
                break;
            case R.id.txt_sugar_gms:
                DR_GINGER_SUGAR = mAppClass.formDigits(3, mBinding.txtSugarGms.getText().toString());
                break;
        }

    }

}
