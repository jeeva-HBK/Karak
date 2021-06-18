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
import com.pradeep.karak.databinding.FragmentMaSubchildFlowrateBinding;

import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_FLOW_RATE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.FLOW_RATE_READ_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_FLOW_RATE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_FLOW_RATE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_FLOW_RATE_FACTOR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MILK_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.MILK_FLOW_RATE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.SEND_FLOW_RATE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.SUGAR_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.SUGAR_FLOW_RATE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.WATER_FLOW_RATE;
import static com.pradeep.karak.Others.ApplicationClass.WATER_FLOW_RATE_SUB_ID;

public class FragmentMaSubChildFlowRate extends Fragment implements BluetoothDataCallback {

    FragmentMaSubchildFlowrateBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    String KarakFrParameter;
    String MasalaFrParameter;
    String GingerFrParameter;
    String CardamomFrParameter;
    String MilkFrParameter;
    String WaterFrParameter;
    String SugarFrParameter;
    BaseActivity mActivity;
    int retryCount = 0;
    String lastSentPacket = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ma_subchild_flowrate, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + FLOW_RATE_READ_SUB_ID));
        mBinding.flowRateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(framePacket());
            }
        });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentMaSubChildFlowRate.this, framedPacket, getContext());
        lastSentPacket = framedPacket;

    }

    private String framePacket() {
        KarakFrParameter = mAppClass.formDigits(4, mBinding.flowRateKarak.getText().toString()) + ";";
        MasalaFrParameter = mAppClass.formDigits(4, mBinding.flowRateMasalaKarak.getText().toString()) + ";";
        GingerFrParameter = mAppClass.formDigits(4, mBinding.flowRateGingerKarak.getText().toString()) + ";";
        CardamomFrParameter = mAppClass.formDigits(4, mBinding.flowrateCardmomKarak.getText().toString()) + ";";
        MilkFrParameter = mAppClass.formDigits(4, mBinding.flowRateMilk.getText().toString()) + ";";
        WaterFrParameter = mAppClass.formDigits(4, mBinding.flowRateWater.getText().toString()) + ";";
        SugarFrParameter = mAppClass.formDigits(4, mBinding.flowRateSugar.getText().toString()) + ";";

        return mAppClass.framePacket(SEND_FLOW_RATE_MESSAGE_ID + KARAK_FLOW_RATE_SUB_ID + KarakFrParameter +
                MASALA_KARAK_FLOW_RATE_FACTOR_SUB_ID + MasalaFrParameter + GINGER_KARAK_FLOW_RATE_SUB_ID + GingerFrParameter +
                CARDAMOM_KARAK_FLOW_RATE_SUB_ID + CardamomFrParameter + MILK_FLOW_RATE_SUB_ID + MilkFrParameter +
                WATER_FLOW_RATE_SUB_ID + WaterFrParameter + SUGAR_FLOW_RATE_SUB_ID + SugarFrParameter);
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        if (data.equals("PSIPSTIMEOUT;CRC;PSIPE")) {
            mActivity.dismissProgress();
            mAppClass.showSnackBar(getContext(), getString(R.string.Timeout));
        } else {
            String[] handelData = data.split(";");
            if (handelData[0].substring(5, 7).equals("07")) {
                String[] Karakfr = handelData[1].split(","),
                        MasalaKarakfr = handelData[2].split(","),
                        GingerKarakfr = handelData[3].split(","),
                        CardamomKarakfr = handelData[4].split(","),
                        Milkfr = handelData[5].split(","),
                        Waterfr = handelData[6].split(","),
                        Sugarfr = handelData[7].split(",");
                KARAK_FLOW_RATE = Karakfr[1];
                MASALA_KARAK_FLOW_RATE = MasalaKarakfr[1];
                GINGER_KARAK_FLOW_RATE = GingerKarakfr[1];
                CARDAMOM_KARAK_FLOW_RATE = CardamomKarakfr[1];
                MILK_FLOW_RATE = Milkfr[1];
                WATER_FLOW_RATE = Waterfr[1];
                SUGAR_FLOW_RATE = Sugarfr[1];
                mBinding.flowRateKarak.append(KARAK_FLOW_RATE);
                mBinding.flowRateMasalaKarak.append(MASALA_KARAK_FLOW_RATE);
                mBinding.flowRateGingerKarak.append(GINGER_KARAK_FLOW_RATE);
                mBinding.flowrateCardmomKarak.append(CARDAMOM_KARAK_FLOW_RATE);
                mBinding.flowRateMilk.append(MILK_FLOW_RATE);
                mBinding.flowRateWater.append(WATER_FLOW_RATE);
                mBinding.flowRateSugar.append(SUGAR_FLOW_RATE);
            } else if (handelData[0].substring(5, 7).equals("14")) {
                if (handelData[1].equals("ACK")) {
                    mAppClass.showSnackBar(getContext(), getString(R.string.UpdateSuccessfully));
                }
            }
            mActivity.dismissProgress();
        }

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
        while (retryCount > 5) {
            if (retryCount <= 4) {
                sendData(lastSentPacket);
                retryCount++;
            } else if (retryCount > 4) {
                mAppClass.showSnackBar(getContext(), "something went wrong\ntry reconnect!");
                mAppClass.disconnect();
            }

        }

    }
}
