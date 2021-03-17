package com.pradeep.karak.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pradeep.karak.Fragment.FragmentMaSubChildFault;
import com.pradeep.karak.Fragment.FragmentMaSubChildItem;
import com.pradeep.karak.Fragment.FragmentMaSubChildStatus;

public class MaintenancePagerAdapter extends FragmentStateAdapter {
    private int Num = 3;

    public MaintenancePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
                return new FragmentMaSubChildItem();
            case 1:
                return new FragmentMaSubChildFault();
            case 2:
                return new FragmentMaSubChildStatus();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return Num;
    }
}

