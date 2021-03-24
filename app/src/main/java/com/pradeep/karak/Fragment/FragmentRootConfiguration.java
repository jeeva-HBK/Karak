package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.util.Log;
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

import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentRootConfigurationBinding;


public class FragmentRootConfiguration extends Fragment implements BluetoothDataCallback {
    private FragmentRootConfigurationBinding mBinding;
    private ApplicationClass mAppClass;
    private String[] mainMenuList;

    private static final String TAG = "FragmentRootConfig";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_root_configuration, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainMenuList = new String[]{"Admin", "maintenance", "master"};
        mBinding.autoComplete.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_brown_bar));
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_autocomplete, mainMenuList);
        mBinding.autoComplete.setAdapter(arrayAdapter);
        getParentFragmentManager().beginTransaction().add(mBinding.configFragHost.getId(), new FragmentChildAdmin(), "TAG_ADMIN").commit();
        mBinding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildAdmin(), "TAG_ADMIN").commit();
                        break;

                    case 1:
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildMaintenance(), "TAG_MAINTENANCE").commit();
                        break;

                    case 2:
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildMaster(), "TAG_MASTER").commit();
                        break;
                }
            }
        });
        mBinding.IbBackArrow.setOnClickListener((view1 -> {
            mAppClass.sendData(getActivity(), FragmentRootConfiguration.this, mAppClass.framePacket(";08;"), getContext());
        }));
    }

    @Override
    public void OnDataReceived(String data) {
        Log.e(TAG, "OnDataReceived: " + data);
        mAppClass.popStackBack(getActivity());
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
