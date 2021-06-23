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
import com.pradeep.karak.databinding.FragmentMaSubchildPresetBoilTimeBinding;

import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_1000ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_100ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_200ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_300ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_400ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_500ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_600ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_700ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_800ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_900ML;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_1000ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_100ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_200ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_300ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_400ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_500ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_600ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_700ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_800ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_900ML_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESENT_BOIL_TIME_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.PRESET_BOIL_TIME_READ_SUB_ID;

public class FragmentMaSubChildPresetBoilTime extends Fragment implements BluetoothDataCallback {
    private static final String TAG = "PresentBoilTime";
    FragmentMaSubchildPresetBoilTimeBinding mBinding;
    ApplicationClass mAppClass;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ma_subchild_preset_boil_time, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + PRESET_BOIL_TIME_READ_SUB_ID));
        mBinding.presetBoilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(framePacket());
            }
        });


    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentMaSubChildPresetBoilTime.this, framedPacket, getContext());
    }

    private String framePacket() {
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

        return mAppClass.framePacket(PRESENT_BOIL_TIME_MESSAGE_ID + PRESENT_BOIL_TIME_100ML_SUB_ID +
                edt100ml + PRESENT_BOIL_TIME_200ML_SUB_ID + edt200ml + PRESENT_BOIL_TIME_300ML_SUB_ID +
                edt300ml + PRESENT_BOIL_TIME_400ML_SUB_ID + edt400ml + PRESENT_BOIL_TIME_500ML_SUB_ID +
                edt500ml + PRESENT_BOIL_TIME_600ML_SUB_ID + edt600ml + PRESENT_BOIL_TIME_700ML_SUB_ID +
                edt700ml + PRESENT_BOIL_TIME_800ML_SUB_ID + edt800ml + PRESENT_BOIL_TIME_900ML_SUB_ID +
                edt900ml + PRESENT_BOIL_TIME_1000ML_SUB_ID + edt1000ml);
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
                PRESENT_BOIL_100ML = pbt100ml[1];
                PRESENT_BOIL_200ML = pbt200ml[1];
                PRESENT_BOIL_300ML = pbt300ml[1];
                PRESENT_BOIL_400ML = pbt400ml[1];
                PRESENT_BOIL_500ML = pbt500ml[1];
                PRESENT_BOIL_600ML = pbt600ml[1];
                PRESENT_BOIL_700ML = pbt700ml[1];
                PRESENT_BOIL_800ML = pbt800ml[1];
                PRESENT_BOIL_900ML = pbt900ml[1];
                PRESENT_BOIL_1000ML = pbt1000ml[1];
                mBinding.edt100ml.append(PRESENT_BOIL_100ML);
                mBinding.edt200ml.append(PRESENT_BOIL_200ML);
                mBinding.edt300ml.append(PRESENT_BOIL_300ML);
                mBinding.edt400ml.append(PRESENT_BOIL_400ML);
                mBinding.edt500ml.append(PRESENT_BOIL_500ML);
                mBinding.edt600ml.append(PRESENT_BOIL_600ML);
                mBinding.edt700ml.append(PRESENT_BOIL_700ML);
                mBinding.edt800ml.append(PRESENT_BOIL_800ML);
                mBinding.edt900ml.append(PRESENT_BOIL_900ML);
                mBinding.edt1000ml.append(PRESENT_BOIL_1000ML);
            } else if (handleData[0].substring(5, 7).equals("15")) {
                if (handleData[1].equals("ACK")) {
                    mAppClass.showSnackBar(getContext(),getString(R.string.UpdateSuccessfully));
                }
            }
            mActivity.dismissProgress();


    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }

   /* private boolean validation() {
        if (mBinding.edt100ml.getText().toString().equals("")) {
            mBinding.edt100ml.setError("enter min one");
            mBinding.edt100ml.requestFocus();
            return false;
        } else if (mBinding.edt200ml.getText().toString().equals("")) {
            mBinding.edt200ml.setError("enter min one");
            mBinding.edt200ml.requestFocus();
            return false;
        } else if (mBinding.edt300ml.getText().toString().equals("")) {
            mBinding.edt200ml.setError("enter min one");
            mBinding.edt300ml.requestFocus();
            return false;
        } else if (mBinding.edt400ml.getText().toString().equals("")) {
            mBinding.edt400ml.setError("enter min one");
            mBinding.edt400ml.requestFocus();
            return false;
        } else if (mBinding.edt500ml.getText().toString().equals("")) {
            mBinding.edt500ml.setError("enter min one");
            mBinding.edt500ml.requestFocus();
            return false;
        } else if (mBinding.edt600ml.getText().toString().equals("")) {
            mBinding.edt600ml.setError("enter min one");
            mBinding.edt600ml.requestFocus();
            return false;
        } else if (mBinding.edt700ml.getText().toString().equals("")) {
            mBinding.edt700ml.setError("enter min one");
            mBinding.edt700ml.requestFocus();
            return false;
        } else if (mBinding.edt800ml.getText().toString().equals("")) {
            mBinding.edt800ml.setError("enter min one");
            mBinding.edt800ml.requestFocus();
            return false;
        } else if (mBinding.edt900ml.getText().toString().equals("")) {
            mBinding.edt900ml.setError("enter min one");
            mBinding.edt900ml.requestFocus();
            return false;
        } else if (mBinding.edt1000ml.getText().toString().equals("")) {
            mBinding.edt1000ml.setError("enter min one");
            mBinding.edt1000ml.requestFocus();
            return false;
        }

        return true;
    }*/
}
