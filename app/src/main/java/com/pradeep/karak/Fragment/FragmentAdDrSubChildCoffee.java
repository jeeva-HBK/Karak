package com.pradeep.karak.Fragment;

import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_WATER;

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
import com.pradeep.karak.databinding.FragmentAdDrSubchildCoffeeBinding;

public class FragmentAdDrSubChildCoffee extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildCoffeeBinding mBinding;
    ApplicationClass mAppClass;
    public static final String TAG = "DrCoffee";
    int maxValue = 100;
    int water;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_coffee, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mBinding.txtCoffeeTeaGms.append(DR_COFFEE);
        mBinding.txtCoffeeMilk.append(DR_COFFEE_MILK);
        mBinding.txtCoffeeWater.append(DR_COFFEE_WATER);
        mBinding.txtCoffeeSugar.append(DR_COFFEE_SUGAR);
        mBinding.txtServingPercu.setText(DR_CUP_ML+"ml "+getString(R.string.no_ml_percup));
        new MultiTextWatcher().registerEditText(mBinding.txtCoffeeTeaGms)
                .registerEditText(mBinding.txtCoffeeMilk).registerEditText(mBinding.txtCoffeeWater)
                .registerEditText(mBinding.txtCoffeeSugar).setCallback(this);
    }


    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_coffee_tea_gms:
                DR_COFFEE = mAppClass.formDigits(3, mBinding.txtCoffeeTeaGms.getText().toString());
                break;
            case R.id.txt_coffee_milk:
                DR_COFFEE_MILK = mAppClass.formDigits(3, mBinding.txtCoffeeMilk.getText().toString());
                if (mBinding.txtCoffeeMilk.getText().toString().length() > 0) {
                    String getValue = mBinding.txtCoffeeMilk.getText().toString();
                    water = maxValue - Integer.parseInt(getValue);
                    mBinding.txtCoffeeWater.setText(water + "");
                    if (Integer.parseInt(mBinding.txtCoffeeMilk.getText().toString()) > 100) {
                        DR_COFFEE_MILK = "100;";
                        mBinding.txtCoffeeMilk.setText("100");
                    }
                }

                break;
            case R.id.txt_coffee_water:
                DR_COFFEE_WATER = mAppClass.formDigits(3, mBinding.txtCoffeeWater.getText().toString());
                if (mBinding.txtCoffeeMilk.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtCoffeeWater.getText().toString()) > 100) {
                        DR_COFFEE_WATER = "100;";
                        mBinding.txtCoffeeWater.setText("100");
                    }
                }

                break;
            case R.id.txt_coffee_sugar:
                DR_COFFEE_SUGAR = mAppClass.formDigits(3, mBinding.txtCoffeeSugar.getText().toString());
                break;
        }

    }

}
