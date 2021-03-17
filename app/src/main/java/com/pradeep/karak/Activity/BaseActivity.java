package com.pradeep.karak.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.pradeep.karak.Fragment.DialogFragment;
import com.pradeep.karak.Fragment.FragmentPermissionRequest;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.ActivityBaseBinding;

// Created on 15 Mar 2021 by Jeeva
public class BaseActivity extends AppCompatActivity {
    ApplicationClass mAppClass;
    ActivityBaseBinding mBinding;
    NavController mNavController;
    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        setSupportActionBar(mBinding.toolbar);
        mAppClass = (ApplicationClass) getApplication();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DialogFragment dialog = new DialogFragment();
        FragmentPermissionRequest permissionRequestFragment = new FragmentPermissionRequest(dialog);
        dialog.init(permissionRequestFragment, "Permissions", null);
        dialog.setCancelable(false);
        if (!mAppClass.hasPermissions(this, FragmentPermissionRequest.PERMISSIONS)) {
            dialog.show(getSupportFragmentManager(), null);
        }
    }

    public void updateNavigationUi() {
        mNavController.setGraph(R.navigation.navigation);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboard).build();
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}