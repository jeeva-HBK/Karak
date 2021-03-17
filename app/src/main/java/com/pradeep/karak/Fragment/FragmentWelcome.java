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
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentWelcomeBinding;


public class FragmentWelcome extends Fragment {
    private static final String TAG = "MachineListFragment";
    private static String SERIAL_NO = "SerialNo", ADDRESS = "Address", END_POINT = "EndPoint", connectPacket = "1;";
    private FragmentWelcomeBinding mBinding;
    private ApplicationClass mAppClass;
    private BaseActivity mActivity;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_welcome, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
        mContext = getActivity().getApplicationContext();
        mBinding.imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppClass.navigateTo(getActivity(), R.id.action_fragment_machine_list_to_dialogBluetoothList);
            }
        });
    }

   /* private void showBleList() {
        DialogFragment dialog = new DialogFragment();
        DialogBluetoothList dialogBluetoothList = new DialogBluetoothList(dialog);
        dialog.init(dialogBluetoothList, "Bluetooth List", new DialogDismissListener() {
            @Override
            public void OnDismiss() {
                mActivity.updateNavigationUi();
            }
        });
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), "tBluetoothList");
    }*/
}
