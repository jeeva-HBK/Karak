package com.pradeep.karak.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pradeep.karak.Fragment.FragmentAdDrSubChildCardmom;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildGinger;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildKarak;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildMasala;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildMilk;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildSulaimani;
import com.pradeep.karak.Fragment.FragmentAdDrSubChildWater;

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
                return new FragmentAdDrSubChildKarak();
            case 1:
                return new FragmentAdDrSubChildGinger();
            case 2:
                return new FragmentAdDrSubChildSulaimani();
            case 3:
                return new FragmentAdDrSubChildMasala();
            case 4:
                return new FragmentAdDrSubChildCardmom();
            case 5:
                return new FragmentAdDrSubChildMilk();
            case 6:
                return new FragmentAdDrSubChildWater();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return Num;
    }
}

