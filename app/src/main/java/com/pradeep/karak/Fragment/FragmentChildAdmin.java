package com.pradeep.karak.Fragment;

import android.os.Bundle;
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
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentChildAdminBinding;

public class FragmentChildAdmin extends Fragment {
    private FragmentChildAdminBinding mBinding;
    private String[] mainMenuList;
    BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_admin, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainMenuList = new String[]{"statistics", "Boil Time", "Set Dispense Ratio", "Set Password"};
        mBinding.autoComplete.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.bg_brown_bar));
        getParentFragmentManager().beginTransaction().replace(mBinding.adminFragHost.getId(), new FragmentAdSubChildStatistics(), "TAG_STATISTICS").commit();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_autocomplete, mainMenuList);
        mBinding.autoComplete.setAdapter(arrayAdapter);

        mBinding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        getParentFragmentManager().beginTransaction().replace(mBinding.adminFragHost.getId(), new FragmentAdSubChildStatistics(), "TAG_BOILMODE").commit();
                        break;
                    case 1:
                        getParentFragmentManager().beginTransaction().replace(mBinding.adminFragHost.getId(), new FragmentAdSubChildBoilTime(), "TAG_STATISTICS").commit();
                        break;
                    case 2:
                        getParentFragmentManager().beginTransaction().replace(mBinding.adminFragHost.getId(), new FragmentAdSubChildDispenseRatio(), "TAG_STATISTICS").commit();
                        break;
                    case 3:
                        getParentFragmentManager().beginTransaction().replace(mBinding.adminFragHost.getId(), new FragmentAdSubChildSetPassword(), "TAG_STATISTICS").commit();
                        break;
                }
            }
        });
    }
}
