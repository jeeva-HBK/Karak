package com.pradeep.karak.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Adapter.DispenseRatioPagerAdapter;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildDispenseratioBinding;

import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_CARDAMOM_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CARDAMOM_KARAK_TEA_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CARDMOM_KARAK_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.CARDMOM_KARAK_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.CARDMOM_KARAK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.COFFEE_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.DISPENSING_RATIO_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.DISPENSING_READ_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARADMOM_KARAK_CARDMOM;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_CARDAMOM_KARAK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_COFFEE_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_GINGER;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_KARAK_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_GINGER_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_HOT_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_HOT_WATER_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_KARAK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAKA_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAKA_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_MASALA;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_MASALA_KARAK_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_MILK_WATER;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_SUGAR;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_TEA;
import static com.pradeep.karak.Others.ApplicationClass.DR_SULAIMANI_WATER;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_GINGER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_TEA_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GINGER_KARAK_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.GO_TO_OPERATOR_PAGE_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.HOT_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.HOT_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.KARAK_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_MASALA_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_TEA_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MASALA_KARAK_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MILK_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MILK_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.MILK_WATER_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.NO_OF_CUP_ML;
import static com.pradeep.karak.Others.ApplicationClass.SULAIMANI_SUGAR_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.SULAIMANI_TEA_SUB_ID;
import static com.pradeep.karak.Others.ApplicationClass.SULAIMANI_WATER_SUB_ID;

public class FragmentAdSubChildDispenseRatio extends Fragment implements BluetoothDataCallback {
    FragmentAdSubchildDispenseratioBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_ad_subchild_dispenseratio, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        ObservableInt pageCount = new ObservableInt(0);
        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
        mBinding.viewPagerMaintenance.setOffscreenPageLimit(8);
        sendData(mAppClass.framePacket(GO_TO_OPERATOR_PAGE_MESSAGE_ID + DISPENSING_READ_SUB_ID));

        pageCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switch (pageCount.get()) {
                    case 0:
                        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(0);
                        break;
                    case 1:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(1);
                        break;
                    case 2:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(2);
                        break;
                    case 3:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(3);
                        break;
                    case 4:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(4);
                        break;
                    case 5:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(5);
                        break;
                    case 6:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(6);
                        break;
                    case 7:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        mBinding.viewPagerMaintenance.setCurrentItem(7);
                        break;
                    case 8:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.GONE);
                        mBinding.viewPagerMaintenance.setCurrentItem(8);
                        break;
                }
            }
        });

        mBinding.IvAdLeftArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() - 1);
        }));

        mBinding.IvAdRightArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() + 1);

        }));

        mBinding.txtDrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(framePacket());
            }
        });
    }

    private void sendData(String framedPacket) {
        mActivity.showProgress();
        mAppClass.sendData(getActivity(), FragmentAdSubChildDispenseRatio.this, framedPacket, getContext());
    }

    private String framePacket() {
        return
                mAppClass.framePacket(DISPENSING_RATIO_MESSAGE_ID
                        + KARAK_SUB_ID + DR_KARAK + ";" + KARAK_MILK_SUB_ID + DR_KARAK_MILK + ";"
                        + KARAK_WATER_SUB_ID + DR_KARAK_WATER + ";"  + KARAK_SUGAR_SUB_ID + DR_KARAK_SUGAR + ";"
                        + MASALA_KARAK_TEA_SUB_ID + DR_MASALA_KARAK_TEA + ";"  + MASALA_KARAK_MASALA_SUB_ID
                        + DR_MASALA_KARAK_MASALA + ";"  + MASALA_KARAK_MILK_SUB_ID + DR_MASALA_KARAK_MILK + ";"
                        + MASALA_KARAK_WATER_SUB_ID + DR_MASALA_KARAKA_WATER + ";"+ MASALA_KARAK_SUGAR_SUB_ID + DR_MASALA_KARAKA_SUGAR + ";"
                        + SULAIMANI_TEA_SUB_ID + DR_SULAIMANI_TEA + ";" + SULAIMANI_WATER_SUB_ID + DR_SULAIMANI_WATER + ";"
                        + SULAIMANI_SUGAR_SUB_ID + DR_SULAIMANI_SUGAR + ";"
                        + GINGER_KARAK_TEA_SUB_ID + DR_GINGER_KARAK_TEA + ";"  + GINGER_KARAK_GINGER_SUB_ID + DR_GINGER_KARAK_GINGER + ";"
                        + GINGER_KARAK_MILK_SUB_ID + DR_GINGER_KARAK_MILK + ";" + GINGER_KARAK_WATER_SUB_ID + DR_GINGER_WATER + ";"
                        + GINGER_KARAK_SUGAR_SUB_ID + DR_GINGER_SUGAR + ";"
                        + CARDAMOM_KARAK_TEA_SUB_ID + DR_CARDAMOM_KARAK_TEA + ";"  + CARDAMOM_KARAK_CARDAMOM_SUB_ID + DR_CARADMOM_KARAK_CARDMOM + ";"
                        + CARDMOM_KARAK_MILK_SUB_ID + DR_CARDAMOM_KARAK_MILK + ";" + CARDMOM_KARAK_WATER  + DR_CARDAMOM_KARAK_WATER+ ";"
                        + CARDMOM_KARAK_SUGAR + DR_CARDAMOM_KARAK_SUGAR + ";"
                        + MILK_SUB_ID + DR_MILK+ ";"  + MILK_WATER_SUB_ID + DR_MILK_WATER + ";"+ MILK_SUGAR_SUB_ID + DR_MILK_SUGAR + ";"
                        + HOT_WATER_SUB_ID + DR_HOT_WATER+ ";" + HOT_SUGAR_SUB_ID + DR_HOT_WATER_SUGAR+ ";"
                        + COFFEE_SUB_ID + DR_COFFEE + ";" + COFFEE_MILK_SUB_ID + DR_COFFEE_MILK + ";"
                        + COFFEE_WATER_SUB_ID + DR_COFFEE_WATER + ";" + COFFEE_SUGAR_SUB_ID + DR_COFFEE_SUGAR + ";"
                        + NO_OF_CUP_ML + DR_CUP_ML + ";");
    }

    @Override
    public void OnDataReceived(String data) {
        mActivity.dismissProgress();
        handleResponse(data);
    }

    private void handleResponse(String data) {
            String[] handleData = data.split(";");
            if (handleData[0].substring(5, 7).equals("07")) {
                String[] Karak = handleData[1].split(","),
                        KarakMilk = handleData[2].split(","),
                        KarakWater = handleData[3].split(","),
                        KarakSugar = handleData[4].split(","),
                        MasalaKarakTea = handleData[5].split(","),
                        MasalaKarakMasala = handleData[6].split(","),
                        MasalaMilk = handleData[7].split(","),
                        MasalaWater = handleData[8].split(","),
                        MasalaSugar = handleData[9].split(","),
                        SulamaniTea = handleData[10].split(","),
                        SulamaniWater = handleData[11].split(","),
                        SulamaniSugar = handleData[12].split(","),
                        Gingerkarak = handleData[13].split(","),
                        GingerKaraKGinger = handleData[14].split(","),
                        GingerMilk = handleData[15].split(","),
                        GingerWater = handleData[16].split(","),
                        GingerSugar = handleData[17].split(","),
                        CardamomTea = handleData[18].split(","),
                        CardamomKarak = handleData[19].split(","),
                        CardamomMilk = handleData[20].split(","),
                        CardamomWater = handleData[21].split(","),
                        CardamomSugar = handleData[22].split(","),
                        Milk = handleData[23].split(","),
                        MilkWater = handleData[24].split(","),
                        MilkSugar = handleData[25].split(","),
                        HotWater = handleData[26].split(","),
                        HotWaterSugar = handleData[27].split(","),
                        Coffee = handleData[28].split(","),
                        CoffeeMilk = handleData[29].split(","),
                        CoffeeWater = handleData[30].split(","),
                        CoffeeSugar = handleData[31].split(","),
                        NoofcupML = handleData[32].split(",");

                DR_KARAK = Karak[1];
                DR_KARAK_MILK = KarakMilk[1];
                DR_KARAK_WATER = KarakWater[1];
                DR_KARAK_SUGAR = KarakSugar[1];
                DR_MASALA_KARAK_TEA = MasalaKarakTea[1];
                DR_MASALA_KARAK_MASALA = MasalaKarakMasala[1];
                DR_MASALA_KARAK_MILK = MasalaMilk[1];
                DR_MASALA_KARAKA_WATER = MasalaWater[1];
                DR_MASALA_KARAKA_SUGAR = MasalaSugar[1];
                DR_SULAIMANI_TEA = SulamaniTea[1];
                DR_SULAIMANI_WATER = SulamaniWater[1];
                DR_SULAIMANI_SUGAR = SulamaniSugar[1];
                DR_GINGER_KARAK_TEA = Gingerkarak[1];
                DR_GINGER_KARAK_GINGER = GingerKaraKGinger[1];
                DR_GINGER_KARAK_MILK = GingerMilk[1];
                DR_GINGER_WATER = GingerWater[1];
                DR_GINGER_SUGAR = GingerSugar[1];
                DR_CARDAMOM_KARAK_TEA = CardamomTea[1];
                DR_CARADMOM_KARAK_CARDMOM = CardamomKarak[1];
                DR_CARDAMOM_KARAK_MILK = CardamomMilk[1];
                DR_CARDAMOM_KARAK_WATER = CardamomWater[1];
                DR_CARDAMOM_KARAK_SUGAR = CardamomSugar[1];
                DR_MILK = Milk[1];
                DR_MILK_WATER = MilkWater[1];
                DR_MILK_SUGAR = MilkSugar[1];
                DR_HOT_WATER = HotWater[1];
                DR_HOT_WATER_SUGAR = HotWaterSugar[1];
                DR_COFFEE = Coffee[1];
                DR_COFFEE_MILK = CoffeeMilk[1];
                DR_COFFEE_WATER = CoffeeWater[1];
                DR_COFFEE_SUGAR = CoffeeSugar[1];
                DR_CUP_ML = NoofcupML[1];
                mBinding.viewPagerMaintenance.setAdapter(new DispenseRatioPagerAdapter(getActivity()));
                mBinding.viewPagerMaintenance.setUserInputEnabled(false);
            } else if (handleData[0].substring(5, 7).equals("10")) {
                if (handleData[1].equals("ACK")) {
                    mAppClass.showSnackBar(getContext(), getString(R.string.UpdateSuccessfully));
                }
            }
            mActivity.dismissProgress();
        }


    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
