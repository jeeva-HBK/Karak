package com.pradeep.karak.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.pradeep.karak.R;

// Created on 15 Mar 2021 by Jeeva
public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
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

}
