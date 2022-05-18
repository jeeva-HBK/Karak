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

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentMaSubchildCorrectionFactorBinding;

import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CORRECTION_FACTOR_READ_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MILK_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.MILK_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.SEND_CORRECTION_FACTOR_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.SUGAR_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.SUGAR_CORRECTION_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.WATER_CORRECTION_FACTOR;
import static com.pradeep.karak.Others.ApplicationClass.WATER_CORRECTION_FACTOR_SUB_ID;

public class FragmentMaSubChildCorrectionFactor extends Fragment implements BluetoothDataCallback {
    FragmentMaSubchildCorrectionFactorBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    String KarakParameter, MasalaKarakParameter, GingerKarakParameter, CardamomKarakParameter,
            MilkParameter, WaterParameter, SugarParamter, CoffeeParameter;
    BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ma_subchild_correction_factor, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        send(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + CORRECTION_FACTOR_READ_SUB_ID));
        mBinding.tvCorrectionFactorSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(framePacket());
            }
        });

    }

    private void send(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentMaSubChildCorrectionFactor.this, framedPacket, getContext());
    }

    private String framePacket() {
        KarakParameter = mAppClass.formDigits(4, mBinding.correctionFactorKarak.getText().toString()) + ";";
        MasalaKarakParameter = mAppClass.formDigits(4, mBinding.correctionMasalaKarak.getText().toString()) + ";";
        GingerKarakParameter = mAppClass.formDigits(4, mBinding.correctionGingerKarak.getText().toString()) + ";";
        CardamomKarakParameter = mAppClass.formDigits(4, mBinding.correctionFactorCardmomKarak.getText().toString()) + ";";
        MilkParameter = mAppClass.formDigits(4, mBinding.correctionMilk.getText().toString()) + ";";
        WaterParameter = mAppClass.formDigits(4, mBinding.correctionWater.getText().toString()) + ";";
        SugarParamter = mAppClass.formDigits(4, mBinding.correctionSugar.getText().toString()) + ";";
        CoffeeParameter = mAppClass.formDigits(4,mBinding.correctionCoffee.getText().toString()) + ";";
        return mAppClass.framePacket(SEND_CORRECTION_FACTOR_MESSAGE_ID + KARAK_CORRECTION_FACTOR_SUB_ID + KarakParameter +
                MASALA_KARAK_CORRECTION_FACTOR_SUB_ID + MasalaKarakParameter + GINGER_KARAK_CORRECTION_FACTOR_SUB_ID +
                GingerKarakParameter + CARDAMOM_KARAK_CORRECTION_FACTOR_SUB_ID + CardamomKarakParameter + MILK_CORRECTION_FACTOR_SUB_ID +
                MilkParameter + WATER_CORRECTION_FACTOR_SUB_ID + WaterParameter + SUGAR_CORRECTION_FACTOR_SUB_ID +
                SugarParamter + COFFEE_CORRECTION_FACTOR_SUB_ID + CoffeeParameter);

    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
            String[] handleData = data.split(";");
            if (handleData[0].substring(5, 7).equals("07")) {
                String[] KarakCf = handleData[1].split(","),
                        MasalaKarakCf = handleData[2].split(","),
                        GingerKarakcf = handleData[3].split(","),
                        CardamomKarakCf = handleData[4].split(","),
                        MilkCf = handleData[5].split(","),
                        WaterCf = handleData[6].split(","),
                        Sugarcf = handleData[7].split(","),
                        Coffeecf = handleData[8].split(",");
                KARAK_CORRECTION_FACTOR = KarakCf[1];
                MASALA_KARAK_CORRECTION_FACTOR = MasalaKarakCf[1];
                GINGER_KARAK_CORRECTION_FACTOR = GingerKarakcf[1];
                CARDAMOM_KARAK_CORRECTION_FACTOR = CardamomKarakCf[1];
                MILK_CORRECTION_FACTOR = MilkCf[1];
                WATER_CORRECTION_FACTOR = WaterCf[1];
                SUGAR_CORRECTION_FACTOR = Sugarcf[1];
                COFFEE_CORRECTION_FACTOR = Coffeecf[1];
                mBinding.correctionFactorKarak.append(KARAK_CORRECTION_FACTOR);
                mBinding.correctionMasalaKarak.append(MASALA_KARAK_CORRECTION_FACTOR);
                mBinding.correctionGingerKarak.append(GINGER_KARAK_CORRECTION_FACTOR);
                mBinding.correctionFactorCardmomKarak.append(CARDAMOM_KARAK_CORRECTION_FACTOR);
                mBinding.correctionMilk.append(MILK_CORRECTION_FACTOR);
                mBinding.correctionWater.append(WATER_CORRECTION_FACTOR);
                mBinding.correctionSugar.append(SUGAR_CORRECTION_FACTOR);
                mBinding.correctionCoffee.append(COFFEE_CORRECTION_FACTOR);
            } else if (handleData[0].substring(5, 7).equals("13")) {
                if (handleData[1].equals("ACK")) {
                   mAppClass.showSnackBar(getContext(),getString(R.string.UpdateSuccessfully));
                }
            }
            mActivity.dismissProgress();

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        mActivity.dismissProgress();
        mAppClass.showSnackBar(getContext(),"Operation Failed");
        e.printStackTrace();
    }
}
