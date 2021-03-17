package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentConfigurationBinding;

public class FragmentRootConfiguration extends Fragment {
    private FragmentConfigurationBinding mBinding;
    private String[] mainMenuList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_configuration, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_autocomplete, mainMenuList);
        mBinding.autoComplete.setAdapter(arrayAdapter);
        getParentFragmentManager().beginTransaction().add(mBinding.configFragHost.getId(), new FragmentChildAdmin(), "TAG_ADMIN").commit();
        mainMenuList = new String[]{"Admin", "maintenance", "master"};
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
    }
}
