package com.pradeep.karak.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentPermissionRequestBinding;


public class FragmentPermissionRequest extends Fragment {


    private com.pradeep.karak.Fragment.DialogFragment parent;
    private FragmentPermissionRequestBinding mbinding;
    private Context mContext;
    private int mRequestCode = 1709;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String[] PERMISSIONS;

    public FragmentPermissionRequest(com.pradeep.karak.Fragment.DialogFragment parent) {
        this.parent = parent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PERMISSIONS = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE
            };
        } else {
            PERMISSIONS = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE
            };
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mbinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_permission_request, container, false);
        return mbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();

        mbinding.btnAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissions(PERMISSIONS, mRequestCode);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == mRequestCode) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    new MaterialAlertDialogBuilder(mContext).setTitle("Info")
                            .setMessage("Grant all permissions to continue using the app").setPositiveButton("OK", null)
                            .show();
                    return;
                }
            }
            parent.dismiss();
        }

    }


}
