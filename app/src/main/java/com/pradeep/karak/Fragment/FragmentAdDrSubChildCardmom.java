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
import com.pradeep.karak.databinding.FragmentAdDrSubchildCardmomBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARADMOM_KARAK_CARDMOM;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAKA_WATER;

// Created on 18 Mar 2021 by silambu
public class FragmentAdDrSubChildCardmom extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildCardmomBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "cardmom";
    int maxValue = 100;
    int water;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_cardmom, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtTeaGms.append(DR_CARDAMOM_KARAK_TEA);
        mBinding.txtTeaCardmom.append(DR_CARADMOM_KARAK_CARDMOM);
        mBinding.txtGmsMilk.append(DR_CARDAMOM_KARAK_MILK);
        mBinding.txtGmsWater.append(DR_CARDAMOM_KARAK_WATER);
        mBinding.txtGmsSugar.append(DR_CARDAMOM_KARAK_SUGAR);
        new MultiTextWatcher().registerEditText(mBinding.txtTeaGms).registerEditText(mBinding.txtTeaCardmom)
                .registerEditText(mBinding.txtGmsMilk).registerEditText(mBinding.txtGmsWater)
                .registerEditText(mBinding.txtGmsSugar).setCallback(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.txtServingPercu.setText(DR_CUP_ML+"ml "+getString(R.string.no_ml_percup));
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_tea_gms:
                DR_CARDAMOM_KARAK_TEA = mAppClass.formDigits(3,mBinding.txtTeaGms.getText().toString()) ;
                break;
            case R.id.txt_tea_cardmom:
                DR_CARADMOM_KARAK_CARDMOM =mAppClass.formDigits(3,mBinding.txtTeaCardmom.getText().toString()) ;
                break;
            case R.id.txt_gms_milk:
                DR_CARDAMOM_KARAK_MILK =mAppClass.formDigits(3,mBinding.txtGmsMilk.getText().toString()) ;
                if (mBinding.txtGmsMilk.getText().toString().length() > 0) {
                    String getValue = mBinding.txtGmsMilk.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtGmsWater.setText(water + "");
                    if (Integer.parseInt(mBinding.txtGmsMilk.getText().toString()) > 100) {
                        DR_CARDAMOM_KARAK_MILK = "100;";
                        mBinding.txtGmsMilk.setText("100");
                    }
                }
                break;
            case R.id.txt_gms_water:
                DR_CARDAMOM_KARAK_WATER =mAppClass.formDigits(3,mBinding.txtGmsWater.getText().toString()) ;
                if (mBinding.txtGmsWater.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtGmsWater.getText().toString()) > 100) {
                        DR_CARDAMOM_KARAK_WATER = "100;";
                        mBinding.txtGmsWater.setText("100");
                    }
                }
                break;
            case R.id.txt_gms_sugar:
                DR_CARDAMOM_KARAK_SUGAR = mAppClass.formDigits(3,mBinding.txtGmsSugar.getText().toString()) ;
                break;
        }

    }


}
