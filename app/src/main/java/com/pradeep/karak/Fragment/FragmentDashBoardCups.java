package com.pradeep.karak.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentDashboardCupsBinding;

import static com.pradeep.karak.Others.ApplicationClass.BevaragePacket;
import static com.pradeep.karak.Others.ApplicationClass.BeverageSubPacketCup;
import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.KEY_BEVERAGE_SELECTION;
import static com.pradeep.karak.Others.ApplicationClass.KEY_CUP;

public class FragmentDashBoardCups extends Fragment implements View.OnClickListener {
    FragmentDashboardCupsBinding mBinding;
    ApplicationClass mAppclass;
    private static final String TAG = "FragmentDashBoardCups";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_cups, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppclass = (ApplicationClass) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Bundle b = getArguments();
        BevaragePacket = b.getString(KEY_BEVERAGE_SELECTION);
        BeverageSubPacketCup = "";
        Log.e(TAG, "onViewCreated: " + BevaragePacket);
        if(DR_CUP_ML.isEmpty()) {
            mBinding.textView10.setVisibility(View.GONE);
        } else {
            mBinding.textView10.setVisibility(View.VISIBLE);
            mBinding.textView10.setText(DR_CUP_ML+"ml Serving per Cup");
        }
        mBinding.viewBackCups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppclass.popStackBack(getActivity());
            }
        });
        mBinding.txtCup1.setOnClickListener(this);
        mBinding.txtCup2.setOnClickListener(this);
        mBinding.txtCup3.setOnClickListener(this);
        mBinding.txtCup4.setOnClickListener(this);
        mBinding.txtCup5.setOnClickListener(this);
        mBinding.txtCup6.setOnClickListener(this);
        mBinding.txtCup7.setOnClickListener(this);
        mBinding.txtCup8.setOnClickListener(this);
        mBinding.txtCup9.setOnClickListener(this);
        mBinding.txtCup10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtCup1:
                BeverageSubPacketCup = ";02,01";
                break;

            case R.id.txtCup2:
                BeverageSubPacketCup = ";02,02";
                break;

            case R.id.txtCup3:
                BeverageSubPacketCup = ";02,03";
                break;

            case R.id.txtCup4:
                BeverageSubPacketCup = ";02,04";
                break;

            case R.id.txtCup5:
                BeverageSubPacketCup = ";02,05";
                break;

            case R.id.txtCup6:
                BeverageSubPacketCup = ";02,06";
                break;

            case R.id.txtCup7:
                BeverageSubPacketCup = ";02,07";
                break;

            case R.id.txtCup8:
                BeverageSubPacketCup = ";02,08";
                break;

            case R.id.txtCup9:
                BeverageSubPacketCup = ";02,09";
                break;

            case R.id.txtCup10:
                BeverageSubPacketCup = ";02,10";
                break;
        }
        Bundle b = new Bundle();
        b.putString(KEY_CUP, BevaragePacket + BeverageSubPacketCup);
        mAppclass.navigateToBundle(getActivity(), R.id.action_fragmentDashBoardCups_to_fragmentDashBoardSugar, b);
    }
}