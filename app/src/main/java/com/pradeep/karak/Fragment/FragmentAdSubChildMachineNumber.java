package com.pradeep.karak.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildMachinenumberBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.pradeep.karak.Others.ApplicationClass.MACHINE_NUMBER_MESSAGE_ID;

public class FragmentAdSubChildMachineNumber extends Fragment implements BluetoothDataCallback {
    FragmentAdSubchildMachinenumberBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    String SerialNo;
    BaseActivity mActivity;
    ItemClickListener listener;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_machinenumber, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mBinding.textViewSerialNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(framePacket());
            }
        });
    }

    private void send(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentAdSubChildMachineNumber.this, framedPacket, getContext());
    }

    private String framePacket() {
        return mAppClass.framePacket(MACHINE_NUMBER_MESSAGE_ID + "KARAK-" + mAppClass.formDigits(5, mBinding.edtSerialNo.getText().toString()) + ";");
    }

    @Override
    public void OnDataReceived(String data) {
        handleDataResponse(data);
    }

    private void handleDataResponse(String data) {
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("18")) {
            if (spiltData[1].equals("ACK")) {
                onUnSave(preferences.getString("macId", ""));
                mAppClass.disconnect();
                mActivity.updateNavigationUi(R.navigation.scan);
                mAppClass.showSnackBar(getContext(), getString(R.string.MachineNumberAddedSuccessfully));

            }
        }
        mActivity.dismissProgress();

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }

    public void onUnSave(String data) {
        String[] mac = data.split("\n");
        try {
            JSONArray jArr = new JSONArray(preferences.getString("savedMac", ""));
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                String mString = obj.getString("mac");
                if (mString.equals(mac[1])) {
                    jArr.remove(i);
                }
            }
            preferences.edit().putString("savedMac", jArr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "onSaveClicked: " + preferences.getString("savedMac", ""));
    }
}
