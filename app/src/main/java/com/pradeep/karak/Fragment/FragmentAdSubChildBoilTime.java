package com.pradeep.karak.Fragment;

import static com.pradeep.karak.Others.ApplicationClass.*;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildBoiltimeBinding;


public class FragmentAdSubChildBoilTime extends Fragment implements BluetoothDataCallback {
    private FragmentAdSubchildBoiltimeBinding mBinding;
    ApplicationClass mAppClass;
    private String[] mainMenuList;
    Context mContext;
    String edt100ml;
    String edt200ml;
    String edt300ml;
    String edt400ml;
    String edt500ml;
    String edt600ml;
    String edt700ml;
    String edt800ml;
    String edt900ml;
    String edt1000ml;
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
        mainMenuList = new String[]{"Karak", "Ginger Karak", "Sulaimani", "Masala Karak",
                "Cardamom Karak","Coffee","Hot Milk","Hot Water"};
        mBinding.autoComplete.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_brown_bar));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_autocomplete, mainMenuList);
        mBinding.autoComplete.setAdapter(arrayAdapter);
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_KARAK_ID));

        mBinding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                     switch (position){
                         case 1:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_GINGERKARAK_ID));
                             break;
                         case 2:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_SULAIMANI_ID));
                             break;
                         case 3:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_MASALAKARAK_ID));
                             break;
                         case 4:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_CARDAMOM_ID));
                             break;
                         case 5:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_COFFEE_ID));
                             break;
                         case 6:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_MILK_ID));
                             break;
                         case 7:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_WATER_ID));
                             break;
                         default:
                             sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + BOIL_TIME_READ_SUB_KARAK_ID));
                             break;
                     }
                    }
                });

        mBinding.presetBoilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mBinding.autoComplete.getText().toString()){
                    case "Ginger Karak":
                        sendData(framePacket(BOIL_TIME_GINGERKARAK_FIRST_CUP, BOIL_TIME_GINGERKARAK_SECOND_CUP,
                                BOIL_TIME_GINGERKARAK_THIRD_CUP, BOIL_TIME_GINGERKARAK_FOURTH_CUP,
                                BOIL_TIME_GINGERKARAK_FIVTH_CUP, BOIL_TIME_GINGERKARAK_SIXTH_CUP,
                                BOIL_TIME_GINGERKARAK_SECOND_CUP,BOIL_TIME_GINGERKARAK_EIGHTH_CUP,
                                BOIL_TIME_GINGERKARAK_NINETH_CUP, BOIL_TIME_GINGERKARAK_TENTH_CUP));
                        break;
                    case "Sulaimani":
                        sendData(framePacket(BOIL_TIME_SULAIMANI_FIRST_CUP, BOIL_TIME_SULAIMANI_SECOND_CUP,
                                BOIL_TIME_SULAIMANI_THIRD_CUP, BOIL_TIME_SULAIMANI_FOURTH_CUP,
                                BOIL_TIME_SULAIMANI_FIVTH_CUP, BOIL_TIME_SULAIMANI_SIXTH_CUP,
                                BOIL_TIME_SULAIMANI_SEVENTH_CUP, BOIL_TIME_SULAIMANI_EIGHTH_CUP,
                                BOIL_TIME_SULAIMANI_NINETH_CUP, BOIL_TIME_SULAIMANI_TENTH_CUP));
                        break;
                    case "Masala Karak":
                        sendData(framePacket(BOIL_TIME_MASALAKARAK_FIRST_CUP, BOIL_TIME_MASALAKARAK_SECOND_CUP,
                                BOIL_TIME_MASALAKARAK_THIRD_CUP, BOIL_TIME_MASALAKARAK_FOURTH_CUP,
                                BOIL_TIME_MASALAKARAK_FIVTH_CUP, BOIL_TIME_MASALAKARAK_SIXTH_CUP,
                                BOIL_TIME_MASALAKARAK_SEVENTH_CUP, BOIL_TIME_MASALAKARAK_EIGHTH_CUP,
                                BOIL_TIME_MASALAKARAK_NINETH_CUP, BOIL_TIME_MASALAKARAK_TENTH_CUP));
                        break;
                    case "Cardamom Karak":
                        sendData(framePacket(BOIL_TIME_CARDAMOM_FIRST_CUP, BOIL_TIME_CARDAMOM_SECOND_CUP,
                                BOIL_TIME_CARDAMOM_THIRD_CUP, BOIL_TIME_CARDAMOM_FOURTH_CUP,
                                BOIL_TIME_CARDAMOM_FIVTH_CUP,  BOIL_TIME_CARDAMOM_SIXTH_CUP,
                                BOIL_TIME_CARDAMOM_SEVENTH_CUP, BOIL_TIME_CARDAMOM_EIGHTH_CUP,
                                BOIL_TIME_CARDAMOM_NINETH_CUP, BOIL_TIME_CARDAMOM_TENTH_CUP));
                        break;
                    case "Coffee":
                        sendData(framePacket(BOIL_TIME_COFFEE_FIRST_CUP, BOIL_TIME_COFFEE_SECOND_CUP,
                                BOIL_TIME_COFFEE_THIRD_CUP, BOIL_TIME_COFFEE_FOURTH_CUP,
                                BOIL_TIME_COFFEE_FIVTH_CUP, BOIL_TIME_COFFEE_SIXTH_CUP,
                                BOIL_TIME_COFFEE_SEVENTH_CUP, BOIL_TIME_COFFEE_EIGHTH_CUP,
                                BOIL_TIME_COFFEE_NINETH_CUP, BOIL_TIME_COFFEE_TENTH_CUP));
                        break;
                    case "Hot Milk":
                        sendData(framePacket(BOIL_TIME_MILK_FIRST_CUP, BOIL_TIME_MILK_SECOND_CUP,
                                BOIL_TIME_MILK_THIRD_CUP, BOIL_TIME_MILK_FOURTH_CUP,
                                BOIL_TIME_MILK_FIVTH_CUP, BOIL_TIME_MILK_SIXTH_CUP,
                                BOIL_TIME_MILK_SEVENTH_CUP, BOIL_TIME_MILK_EIGHTH_CUP,
                                BOIL_TIME_MILK_NINETH_CUP, BOIL_TIME_MILK_TENTH_CUP));
                        break;
                    case "Hot Water":
                        sendData(framePacket(BOIL_TIME_WATER_FIRST_CUP, BOIL_TIME_WATER_SECOND_CUP,
                                BOIL_TIME_WATER_THIRD_CUP, BOIL_TIME_WATER_FOURTH_CUP,
                                BOIL_TIME_WATER_FIVTH_CUP, BOIL_TIME_WATER_SIXTH_CUP,
                                BOIL_TIME_WATER_SEVENTH_CUP, BOIL_TIME_WATER_EIGHTH_CUP,
                                BOIL_TIME_WATER_NINETH_CUP, BOIL_TIME_WATER_TENTH_CUP));
                        break;
                    default:
                        sendData(framePacket(BOIL_TIME_KARAK_FIRST_CUP, BOIL_TIME_KARAK_SECOND_CUP,
                                BOIL_TIME_KARAK_THIRD_CUP, BOIL_TIME_KARAK_FOURTH_CUP,
                                BOIL_TIME_KARAK_FIVTH_CUP, BOIL_TIME_KARAK_SIXTH_CUP,
                                BOIL_TIME_KARAK_SEVENTH_CUP, BOIL_TIME_KARAK_EIGHTH_CUP,
                                BOIL_TIME_KARAK_NINETH_CUP, BOIL_TIME_KARAK_TENTH_CUP));
                        break;
                }
                    }
                });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentAdSubChildBoilTime.this, framedPacket, getContext());
    }

    private String framePacket(String one_cup, String two_cup, String three_cup, String four_cup,
                               String five_cup, String six_cup, String seven_cup, String eight_cup,
                               String nine_cup, String ten_cup) {
        edt100ml = mAppClass.formDigits(4, mBinding.edt100ml.getText().toString()) + ";";
        edt200ml = mAppClass.formDigits(4, mBinding.edt200ml.getText().toString()) + ";";
        edt300ml = mAppClass.formDigits(4, mBinding.edt300ml.getText().toString()) + ";";
        edt400ml = mAppClass.formDigits(4, mBinding.edt400ml.getText().toString()) + ";";
        edt500ml = mAppClass.formDigits(4, mBinding.edt500ml.getText().toString()) + ";";
        edt600ml = mAppClass.formDigits(4, mBinding.edt600ml.getText().toString()) + ";";
        edt700ml = mAppClass.formDigits(4, mBinding.edt700ml.getText().toString()) + ";";
        edt800ml = mAppClass.formDigits(4, mBinding.edt800ml.getText().toString()) + ";";
        edt900ml = mAppClass.formDigits(4, mBinding.edt900ml.getText().toString()) + ";";
        edt1000ml = mAppClass.formDigits(4, mBinding.edt1000ml.getText().toString()) + ";";

        return mAppClass.framePacket(BOIL_TIME_MESSAGE_ID + one_cup +
                edt100ml + two_cup + edt200ml + three_cup +
                edt300ml + four_cup + edt400ml + five_cup +
                edt500ml + six_cup + edt600ml + seven_cup +
                edt700ml + eight_cup + edt800ml + nine_cup +
                edt900ml + ten_cup + edt1000ml);
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] handleData = data.split(";");
        if (handleData[0].substring(5, 7).equals("07")) {
            String[] pbt100ml = handleData[1].split(","),
                    pbt200ml = handleData[2].split(","),
                    pbt300ml = handleData[3].split(","),
                    pbt400ml = handleData[4].split(","),
                    pbt500ml = handleData[5].split(","),
                    pbt600ml = handleData[6].split(","),
                    pbt700ml = handleData[7].split(","),
                    pbt800ml = handleData[8].split(","),
                    pbt900ml = handleData[9].split(","),
                    pbt1000ml = handleData[10].split(",");
            BOIL_TIME_100ML = pbt100ml[1];
            BOIL_TIME_200ML = pbt200ml[1];
            BOIL_TIME_300ML = pbt300ml[1];
            BOIL_TIME_400ML = pbt400ml[1];
            BOIL_TIME_500ML = pbt500ml[1];
            BOIL_TIME_600ML = pbt600ml[1];
            BOIL_TIME_700ML = pbt700ml[1];
            BOIL_TIME_800ML = pbt800ml[1];
            BOIL_TIME_900ML = pbt900ml[1];
            BOIL_TIME_1000ML = pbt1000ml[1];
            mBinding.edt100ml.setText(BOIL_TIME_100ML);
            mBinding.edt200ml.setText(BOIL_TIME_200ML);
            mBinding.edt300ml.setText(BOIL_TIME_300ML);
            mBinding.edt400ml.setText(BOIL_TIME_400ML);
            mBinding.edt500ml.setText(BOIL_TIME_500ML);
            mBinding.edt600ml.setText(BOIL_TIME_600ML);
            mBinding.edt700ml.setText(BOIL_TIME_700ML);
            mBinding.edt800ml.setText(BOIL_TIME_800ML);
            mBinding.edt900ml.setText(BOIL_TIME_900ML);
            mBinding.edt1000ml.setText(BOIL_TIME_1000ML);
        } else if (handleData[0].substring(5, 7).equals("11")) {
            if (handleData[2].equals("ACK")) {
                mAppClass.showSnackBar(getContext(),getString(R.string.UpdateSuccessfully));
            }
        }
        mActivity.dismissProgress();

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }


}