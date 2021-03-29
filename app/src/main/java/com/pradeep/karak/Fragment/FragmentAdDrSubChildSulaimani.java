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
import com.pradeep.karak.databinding.FragmentAdDrSubchildSulaimaniBinding;

import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_WATER;

public class FragmentAdDrSubChildSulaimani extends Fragment implements TextWatcherWithInstance {
    FragmentAdDrSubchildSulaimaniBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    public static final String TAG = "DrSulaimani";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_dr_subchild_sulaimani, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtSulaimaniTeaGms.setText(DR_SULAIMANI_TEA);
        mBinding.txtSWater.setText(DR_SULAIMANI_WATER);
        new MultiTextWatcher().registerEditText(mBinding.txtSulaimaniTeaGms)
                .registerEditText(mBinding.txtSWater).setCallback(this);
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        switch (editText.getId()) {
            case R.id.txt_sulaimani_tea_gms:
                DR_SULAIMANI_TEA = mAppClass.formDigits(3, mBinding.txtSulaimaniTeaGms.getText().toString()) + ";";
                break;
            case R.id.txt_s_water:
                DR_SULAIMANI_WATER = mAppClass.formDigits(3, mBinding.txtSWater.getText().toString()) + ";";
                if (mBinding.txtSWater.getText().toString().length() > 0) {
                    if (Integer.parseInt(mBinding.txtSWater.getText().toString()) > 100) {
                        DR_SULAIMANI_WATER = "100;";
                        mBinding.txtSWater.setText("100");
                    }
                }
                break;
        }

    }


}
