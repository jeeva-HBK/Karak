package com.pradeep.karak.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pradeep.karak.Fragment.FragmentSubChildAdDMasala;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrCardmom;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrGinger;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrKarak;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrMilk;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrSulaimani;
import com.pradeep.karak.Fragment.FragmentSubChildAdDrWater;

public class DispenseRatioPagerAdapter extends FragmentStateAdapter {
    private int Num = 7;

    public DispenseRatioPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
                return new FragmentSubChildAdDrCardmom();
            case 1:
                return new FragmentSubChildAdDrGinger();
            case 2:
                return new FragmentSubChildAdDMasala();
            case 3:
                return new FragmentSubChildAdDrMilk();
            case 4:
                return new FragmentSubChildAdDrSulaimani();
            case 5:
                return new FragmentSubChildAdDrKarak();
            case 6:
                return new FragmentSubChildAdDrWater();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return Num;
    }
}

