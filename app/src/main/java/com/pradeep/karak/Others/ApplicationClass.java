package com.pradeep.karak.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.SerialSocket;
import com.pradeep.karak.R;

// Created on 15 Mar 2021 by Jeeva
public class ApplicationClass extends Application {
    // Packet_keys
    public static String START_PACKET = "PSIPS", CRC = "CRC;",
            END_PACKET = "PSIPE", BevaragePacket, BeverageSubPacketCup, BevarageSubPacketSugar,

    // MessageIDs
    GO_TO_OPERATOR_PAGE_MESSAGE_ID = "07;",
            CUP_COUNT_RESET_MESSAGE_ID = "09;",
            DISPENSING_RATIO_MESSAGE_ID = "10;",
            BOIL_TIME_MESSAGE_ID = "11;",
            SEND_PASSWORD_MESSAGE_ID = "12;",
            SEND_CORRECTION_FACTOR_MESSAGE_ID = "13;",
            SEND_FLOW_RATE_MESSAGE_ID = "14;",
            PRESENT_BOIL_TIME_MESSAGE_ID = "15;",
            FACTORY_RESET_MESSAGE_ID = "16;",
            TOTAL_RESET_MESSAGE_ID = "17;",
            MACHINE_NUMBER_MESSAGE_ID = "18;",


    // 10 -> DispensingRatio
    KARAK_SUB_ID = "01,", KARAK_MILK_SUB_ID = "02,",
            KARAK_WATER_SUB_ID = "03,", MASALA_KARAK_TEA_SUB_ID = "04,", MASALA_KARAK_MASALA_SUB_ID = "05,",
            MASALA_KARAK_MILK_SUB_ID = "06,", MASALA_KARAK_WATER_SUB_ID = "07,", SULAIMANI_TEA_SUB_ID = "08,",
            SULAIMANI_WATER_SUB_ID = "09,", GINGER_KARAK_TEA_SUB_ID = "10,", GINGER_KARAK_GINGER_SUB_ID = "11,",
            GINGER_KARAK_MILK_SUB_ID = "12,", GINGER_KARAK_WATER_SUB_ID = "13,", CARDAMOM_KARAK_TEA_SUB_ID = "14,",
            CARDAMOM_KARAK_CARDAMOM_SUB_ID = "15,", CARDMOM_KARAK_MILK_SUB_ID = "16,", CARDMOM_KARAK_WATER = "17,",
            MILK_SUB_ID = "18,", MILK_WATER_SUB_ID = "19,", HOT_WATER_SUB_ID = "20,",

    // 11 -> BoilTime
    BOIL_TIME_KARAK_SUB_ID = "01,",
            BOIL_TIME_MASALA_KARAK_SUB_ID = "02,", BOIL_TIME_GINGER_SUB_ID = "03,",
            BOIL_TIME_CARDAMOM_SUB_ID = "04,", BOIL_TIME__SULAIMANI_SUB_ID = "05,",
            BOIL_TIME_HOT_MILK_SUB_ID = "06,", BOIL_TIME_HOT_WATER_SUB_ID = "07,",

    // 12 -> SendPassword
    ADMIN_PASSWORD_SUB_ID = "01,", MAINTENANCE_PASSWORD_SUB_ID = "02,", CUP_COUNT_PASSWORD_SUB_ID = "03,",


    // 13 -> SendCorrectionFactor
    KARAK_CORRECTION_FACTOR_SUB_ID = "01,", MASALA_KARAK_CORRECTION_FACTOR_SUB_ID = "02,",
            GINGER_KARAK_CORRECTION_FACTOR_SUB_ID = "03,", CARDAMOM_KARAK_CORRECTION_FACTOR_SUB_ID = "04,",
            MILK_CORRECTION_FACTOR_SUB_ID = "05,", WATER_CORRECTION_FACTOR_SUB_ID = "06,",
            SUGAR_CORRECTION_FACTOR_SUB_ID = "07,",

    // 14 -> SendFlowRate
    KARAK_FLOW_RATE_SUB_ID = "01,", MASALA_KARAK_FLOW_RATE_FACTOR_SUB_ID = "02,",
            GINGER_KARAK_FLOW_RATE_SUB_ID = "03,", CARDAMOM_KARAK_FLOW_RATE_SUB_ID = "04,",
            MILK_FLOW_RATE_SUB_ID = "05,", WATER_FLOW_RATE_SUB_ID = "06,",
            SUGAR_FLOW_RATE_SUB_ID = "07,",

    // 15-> PresentBoilTime
    PRESENT_BOIL_TIME_100ML_SUB_ID = "01,",
            PRESENT_BOIL_TIME_200ML_SUB_ID = "02,", PRESENT_BOIL_TIME_300ML_SUB_ID = "03,",
            PRESENT_BOIL_TIME_400ML_SUB_ID = "04,", PRESENT_BOIL_TIME_500ML_SUB_ID = "05,",
            PRESENT_BOIL_TIME_600ML_SUB_ID = "06,", PRESENT_BOIL_TIME_700ML_SUB_ID = "07,",
            PRESENT_BOIL_TIME_800ML_SUB_ID = "08,", PRESENT_BOIL_TIME_900ML_SUB_ID = "09,",
            PRESENT_BOIL_TIME_1000ML_SUB_ID = "10,",

    // Data Read Packet
    DISPENSING_READ_SUB_ID = "11;", BOIL_TIME_READ_SUB_ID = "31;",
            CORRECTION_FACTOR_READ_SUB_ID = "38", FLOW_RATE_READ_SUB_ID = "45;",
            PRESET_BOIL_TIME_READ_SUB_ID = "52;", INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE = "62;";

    // Static_Keys
    public static String KEY_BEVERAGE_SELECTION = "pckBeverageSelection", KEY_CUP = "pckCup";

    // ParameterKeyForDispensingRatio
    public static String DR_KARAK = "", DR_KARAK_MILK = "", DR_KARAK_WATER = "",
            DR_MASALA_KARAK_TEA = "", DR_MASALA_KARAK_MASALA = "", DR_MASALA_KARAK_MILK = "",
            DR_MASALA_KARAKA_WATER = "", DR_SULAIMANI_TEA = "", DR_SULAIMANI_WATER = "",
            DR_GINGER_KARAK_TEA = "", DR_GINGER_KARAK_GINGER = "", DR_GINGER_KARAK_MILK = "",
            DR_GINGER_WATER = "", DR_CARDAMOM_KARAK_TEA = "", DR_CARADMOM_KARAK_CARDMOM = "",
            DR_CARDAMOM_KARAK_MILK = "", DR_CARDAMOM_KARAK_WATER = "", DR_MILK = "",
            DR_MILK_WATER = "", DR_HOT_WATER = "";

    // ParameterKeyForBoilTime
    public static String KARAK_BOIL_TIME = "", MASALA_KARAK_BOIL_TIME = "",
            GINGER_KARAK_BOIL_TIME = "", CARDAMOM_KARAK_BOIL_TIME = "",
            SULAIMANI_BOIL_TIME = "", HOTMILK_BOIL_TIME = "", HOT_WATER_BOIL_TIME = "";

    // ParameterKeyForCorrectionFactor
    public static String KARAK_CORRECTION_FACTOR = "", MASALA_KARAK_CORRECTION_FACTOR = "",
            GINGER_KARAK_CORRECTION_FACTOR = "", CARDAMOM_KARAK_CORRECTION_FACTOR = "",
            MILK_CORRECTION_FACTOR = "", WATER_CORRECTION_FACTOR = "",
            SUGAR_CORRECTION_FACTOR = "";

    // ParameterKeyForFlowRate
    public static String KARAK_FLOW_RATE = "", MASALA_KARAK_FLOW_RATE = "",
            GINGER_KARAK_FLOW_RATE = "", CARDAMOM_KARAK_FLOW_RATE = "",
            MILK_FLOW_RATE = "", WATER_FLOW_RATE = "",
            SUGAR_FLOW_RATE = "";

    // 1ParameterKeyForPresentBoilTime
    public static String PRESENT_BOIL_100ML = "",
            PRESENT_BOIL_200ML = "", PRESENT_BOIL_300ML = "",
            PRESENT_BOIL_400ML = "", PRESENT_BOIL_500ML = "",
            PRESENT_BOIL_600ML = "", PRESENT_BOIL_700ML = "",
            PRESENT_BOIL_800ML = "", PRESENT_BOIL_900ML = "",
            PRESENT_BOIL_1000ML = "";

    // Passwords
    public static String ADMIN_PASSWORD = "", MAINTENANCE_PASSWORD = "", CUP_COUNT_PASSWORD = "", MASTER_PASSWORD = "4444";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                BluetoothHelper helper = BluetoothHelper.getInstance();
                if (helper != null) {
                    if (helper.isConnected()) {
                        helper.disConnect();
                    }
                }
            }
        });
    }

    public void sendData(Activity activity, BluetoothDataCallback callback, String packetToSend, Context mContext) {
        try {
            BluetoothHelper helper = BluetoothHelper.getInstance(activity);
            helper.sendDataBLE(callback, packetToSend);
        } catch (Exception e) {
            Toast.makeText(mContext, R.string.errorOccurred, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String formDigits(int digits, String value) {
        String finalDigits = null;
        switch (digits) {
            case 2:
                finalDigits = ("00" + value).substring(value.length());
                break;

            case 3:
                finalDigits = ("000" + value).substring(value.length());
                break;

            case 4:
                finalDigits = ("0000" + value).substring(value.length());
                break;

            case 5:
                finalDigits = ("00000" + value).substring(value.length());
                break;

            case 6:
                finalDigits = ("000000" + value).substring(value.length());
                break;
        }
        return finalDigits;
    }

    public void navigateTo(FragmentActivity fragAct, int desID) {
        Navigation.findNavController((Activity) fragAct, R.id.nav_host_fragment).navigate(desID);
    }

    public void navigateTo(FragmentActivity fragAct, int desID, Bundle bundle) {
        Navigation.findNavController((Activity) fragAct, R.id.nav_host_fragment).navigate(desID, bundle);
    }

    public void popStackBack(FragmentActivity activity) {
        Navigation.findNavController((Activity) activity, R.id.nav_host_fragment).popBackStack();
    }

    public void disconnect() {
        SerialSocket serialSocket = SerialSocket.getInstance();
        serialSocket.disconnect();
    }

    public String framePacket(String packet) {
        //  return START_PACKET + packet + UtilMethods.CRCCalc(packet) + ";" + END_PACKET;
        return START_PACKET + packet + CRC + END_PACKET;
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showSnackBar(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.cord_lay), message, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }

}
