package com.pradeep.karak.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pradeep.karak.Fragment.FragmentMnSubChildItem;
import com.pradeep.karak.Fragment.FragmentMnSubChildStatus;

public class MaintenancePagerAdapter extends FragmentStateAdapter {
    private int Num = 2;

    public MaintenancePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentMnSubChildItem();
            case 1:
                return new FragmentMnSubChildStatus();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return Num;
    }
}

