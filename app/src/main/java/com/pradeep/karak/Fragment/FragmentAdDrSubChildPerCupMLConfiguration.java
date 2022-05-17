package com.pradeep.karak.Fragment;

import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_WATER;

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
import com.pradeep.karak.databinding.FragmentAdDrSubchildPercupConfigurationBinding;

public class FragmentAdDrSubChildPerCupMLConfiguration extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildPercupConfigurationBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "DrPerCup";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_percup_configuration, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.edtTxtCupMl.append(DR_CUP_ML);
        new MultiTextWatcher().registerEditText(mBinding.edtTxtCupMl).setCallback(this);
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        if (editText.getId() == R.id.edt_txt_cup_ml) {
            DR_CUP_ML = mAppClass.formDigits(3, mBinding.edtTxtCupMl.getText().toString());
        }
    }
}
