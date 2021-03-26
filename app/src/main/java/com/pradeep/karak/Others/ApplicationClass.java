package com.pradeep.karak.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.R;

// Created on 15 Mar 2021 by Jeeva
public class ApplicationClass extends Application {

    // Packet_keys
    public static String START_PACKET = "PSIPS", CRC = "CRC;",
            END_PACKET = "PSIPE/r/n", BevaragePacket, BeverageSubPacketCup, BevarageSubPacketSugar,

    // MessageIDs
    CUP_COUNT_RESET_MESSAGE_ID = "09;",
            DISPENSING_RATIO_MESSAGE_ID = "10;",
            BOIL_TIME_MESSAGE_ID = "11;",
            SEND_PASSWORD_MESSAGE_ID = "12;",

    PRESENT_BOIL_TIME_MESSAGE_ID = "15;",
            FACTORY_RESET_MESSAGE_ID = "16;",
            TOTAL_RESET_MESSAGE_ID = "17;",

    // SendPassword
    ADMIN_PASSWORD_SUB_ID = "01,", MAINTENANCE_PASSWORD = "02,", CUP_COUNT_PASSWORD = "03,",

    // BoilTime
    BOIL_TIME_KARAK_SUB_ID = "01,",
            BOIL_TIME_MASALA_KARAK_SUB_ID = "02,", BOIL_TIME_GINGER_SUB_ID = "03,",
            BOIL_TIME_CARDAMOM_SUB_ID = "04,", BOIL_TIME__SULAIMANI_SUB_ID = "05,",
            BOIL_TIME_HOT_MILK = "06,", BOIL_TIME_HOT_WATER = "07,",

    // PresentBoilTime
    PRESENT_BOIL_TIME_100ML = "01,",
            PRESENT_BOIL_TIME_200ML = "02,", PRESENT_BOIL_TIME_300ML = "03,",
            PRESENT_BOIL_TIME_400ML = "04,", PRESENT_BOIL_TIME_500ML = "05,",
            PRESENT_BOIL_TIME_600ML = "06,", PRESENT_BOIL_TIME_700ML = "07,",
            PRESENT_BOIL_TIME_800ML = "08,", PRESENT_BOIL_TIME_900ML = "09,",
            PRESENT_BOIL_TIME_1000ML = "10,",

    // DispensingRatio
    KARAK_SUB_ID = "01,", KARAK_MILK_SUB_ID = "02,",
            KARAK_WATER_SUB_ID = "03,", MASALA_KARAK_TEA_SUB_ID = "04,", MASALA_KARAK_MASALA_SUB_ID = "05,",
            MASALA_KARAK_MILK_SUB_ID = "06,", MASALA_KARAK_WATER_SUB_ID = "07,", SULAIMANI_TEA_SUB_ID = "08,",
            SULAIMANI_WATER_SUB_ID = "09,", GINGER_KARAK_TEA_SUB_ID = "10,", GINGER_KARAK_GINGER_SUB_ID = "11,",
            GINGER_KARAK_MILK_SUB_ID = "12,", GINGER_KARAK_WATER_SUB_ID = "13,", CARDAMOM_KARAK_TEA_SUB_ID = "14,",
            CARDAMOM_KARAK_CARDAMOM_SUB_ID = "15,", CARDMOM_KARAK_MILK_SUB_ID = "16,", CARDMOM_KARAK_WATER = "17,",
            MILK_SUB_ID = "18,", MILK_WATER_SUB_ID = "19,";

    // Static_Keys
    public static String KEY_BEVERAGE_SELECTION = "pckBeverageSelection", KEY_CUP = "pckCup";

    @Override
    public void onCreate() {
        super.onCreate();
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

    public String framePacket(String packet) {
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
        TextView tv = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
