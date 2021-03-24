package com.pradeep.karak.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
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
            END_PACKET = "PSIPE/r/n", BevaragePacket, BeverageSubPacketCup, BevarageSubPacketSugar;

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
