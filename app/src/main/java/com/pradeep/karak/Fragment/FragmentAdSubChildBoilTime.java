package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.pradeep.karak.databinding.FragmentAdSubchildBoiltimeBinding;

import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_CARDAMOM_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_GINGER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_HOT_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_HOT_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_KARAK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_MASALA_KARAK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME_READ_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.BOIL_TIME__SULAIMANI_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.HOTMILK_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.HOT_WATER_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_BOIL_TIME;
import static com.pradeep.karak.Others.ApplicationClass.SULAIMANI_BOIL_TIME;

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
    BaseActivity mActivity;

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
        mActivity = (BaseActivity) getActivity();
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_ID));

        mBinding.txtBoilTimeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(framePacket());
            }
        });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
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
        if (data.equals("PSIPSTIMEOUT;CRC;PSIPE")){
            mActivity.dismissProgress();
            mAppClass.showSnackBar(getContext(),getString(R.string.Timeout));
        }else {
            String[] handleData = data.split(";");
            if (handleData[0].substring(5, 7).equals("07")) {
                String[] karakBoilTime = handleData[1].split(","),
                        MasalaBoilTime = handleData[2].split(","),
                        GingerBoilTime = handleData[3].split(","),
                        CardamomBoilTime = handleData[4].split(","),
                        SulaimaniBoilTime = handleData[5].split(","),
                        HotMilkBoilTime = handleData[6].split(","),
                        HotWaterBoilTime = handleData[7].split(",");
                KARAK_BOIL_TIME = karakBoilTime[1];
                MASALA_KARAK_BOIL_TIME = MasalaBoilTime[1];
                GINGER_KARAK_BOIL_TIME = GingerBoilTime[1];
                CARDAMOM_KARAK_BOIL_TIME = CardamomBoilTime[1];
                SULAIMANI_BOIL_TIME = SulaimaniBoilTime[1];
                HOTMILK_BOIL_TIME = HotMilkBoilTime[1];
                HOT_WATER_BOIL_TIME = HotWaterBoilTime[1];
                mBinding.edtBoilTimeKarak.setText(KARAK_BOIL_TIME);
                mBinding.edtBoilTimeMasalaKarak.setText(MASALA_KARAK_BOIL_TIME);
                mBinding.edtBoilTimeGingerKarak.setText(GINGER_KARAK_BOIL_TIME);
                mBinding.edtBoilTimeCardmomKarak.setText(CARDAMOM_KARAK_BOIL_TIME);
                mBinding.edtBoilTimeSulamani.setText(SULAIMANI_BOIL_TIME);
                mBinding.edtBoilTimeMilk.setText(HOTMILK_BOIL_TIME);
                mBinding.edtBoilTimeHotWater.setText(HOT_WATER_BOIL_TIME);

            } else if (handleData[0].substring(5, 7).equals("11")) {
                if (handleData[0].equals("ACK")) {
                    mAppClass.showSnackBar(getContext(),getString(R.string.UpdateSuccessfully));
                }// TODO: 31-03-2021 check here
            }
        }
        mActivity.dismissProgress();
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}