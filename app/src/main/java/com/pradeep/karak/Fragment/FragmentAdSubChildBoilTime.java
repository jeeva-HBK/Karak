package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildBoiltimeBinding;

import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_CARDAMOM_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_GINGER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_HOT_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_HOT_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_KARAK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_MASALA_KARAK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME__SULAIMANI_SUB_ID;

public class FragmentAdSubChildBoilTime extends Fragment implements BluetoothDataCallback {
    private FragmentAdSubchildBoiltimeBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    String KarakParameter;
    String MasalaKarakParameter;
    String GingerKarakParameter;
    String CardamomKarakParameter;
    String SulamaimaniParameter;
    String HotMilkParameter;
    String HotWaterParameter;

    private static final String TAG = "BoilTime";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_boiltime, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mBinding.txtBoilTimeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(framePacket());
            }
        });
    }

    private void sendData(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentAdSubChildBoilTime.this, framedPacket, getContext());
    }

    private String framePacket() {

        KarakParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeKarak.getText().toString()) + ";";
        MasalaKarakParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeMasalaKarak.getText().toString()) + ";";
        GingerKarakParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeGingerKarak.getText().toString()) + ";";
        CardamomKarakParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeCardmomKarak.getText().toString()) + ";";
        SulamaimaniParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeSulamani.getText().toString()) + ";";
        HotMilkParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeMilk.getText().toString()) + ";";
        HotWaterParameter = mAppClass.formDigits(4, mBinding.edtBoilTimeHotWater.getText().toString()) + ";";

        return mAppClass.framePacket(BOIL_TIME_MESSAGE_ID + BOIL_TIME_KARAK_SUB_ID + KarakParameter +
                BOIL_TIME_MASALA_KARAK_SUB_ID + MasalaKarakParameter + BOIL_TIME_GINGER_SUB_ID +
                GingerKarakParameter + BOIL_TIME_CARDAMOM_SUB_ID + CardamomKarakParameter +
                BOIL_TIME__SULAIMANI_SUB_ID + SulamaimaniParameter + BOIL_TIME_HOT_MILK_SUB_ID +
                HotMilkParameter + BOIL_TIME_HOT_WATER_SUB_ID + HotWaterParameter);
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}