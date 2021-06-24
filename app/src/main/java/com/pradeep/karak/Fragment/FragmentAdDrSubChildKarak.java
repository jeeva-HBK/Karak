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
import com.pradeep.karak.databinding.FragmentAdDrSubchildKarakBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_WATER;

// Created on 19 Mar 2021 by silambu
public class FragmentAdDrSubChildKarak extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildKarakBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "Drkarak";
    int maxValue = 100;
    int water;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_karak, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mBinding.txtKarakTeaGms.append(DR_KARAK);
        mBinding.txtKarakMilk.append(DR_KARAK_MILK);
        mBinding.txtKarakWater.append(DR_KARAK_WATER);
        new MultiTextWatcher().registerEditText(mBinding.txtKarakTeaGms)
                .registerEditText(mBinding.txtKarakMilk).registerEditText(mBinding.txtKarakWater)
                .setCallback(this);
    }


    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_karak_tea_gms:
                DR_KARAK = mAppClass.formDigits(3, mBinding.txtKarakTeaGms.getText().toString());
                break;
            case R.id.txt_karak_milk:
                DR_KARAK_MILK = mAppClass.formDigits(3, mBinding.txtKarakMilk.getText().toString());
                if (mBinding.txtKarakMilk.getText().toString().length() > 0) {
                    String getValue = mBinding.txtKarakMilk.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtKarakWater.setText(water + "");
                    if (Integer.parseInt(mBinding.txtKarakMilk.getText().toString()) > 100) {
                        DR_KARAK_MILK = "100;";
                        mBinding.txtKarakMilk.setText("100");
                    }
                }


                break;
            case R.id.txt_karak_water:
                DR_KARAK_WATER = mAppClass.formDigits(3, mBinding.txtKarakWater.getText().toString());
                if (mBinding.txtKarakMilk.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtKarakWater.getText().toString()) > 100) {
                        DR_KARAK_WATER = "100;";
                        mBinding.txtKarakWater.setText("100");
                    }
                }

                break;
        }

    }

}
