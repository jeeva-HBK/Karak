package com.pradeep.karak.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
;

// Created on 15 Mar 2021 by Jeeva
public class BaseActivity extends AppCompatActivity {
    ApplicationClass mAppClass;
    static ApplicationClass msAppClass;
    ActivityBaseBinding mBinding;
    static ActivityBaseBinding msBinding;
    NavController mNavController;
    AppBarConfiguration mAppBarConfiguration;
    public static boolean canGoBack;
    static Context msContext;
    static BaseActivity baseActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        msBinding = mBinding;
        baseActivity = this;
        setSupportActionBar(mBinding.toolbar);
        mAppClass = (ApplicationClass) getApplication();
        msAppClass = (ApplicationClass) getApplication();
        msContext = getApplicationContext();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DialogFragment dialog = new DialogFragment();
        FragmentPermissionRequest permissionRequestFragment = new FragmentPermissionRequest(dialog);
        dialog.init(permissionRequestFragment, "Permissions", null);
        dialog.setCancelable(false);
        if (!mAppClass.hasPermissions(this, FragmentPermissionRequest.PERMISSIONS)) {
            dialog.show(getSupportFragmentManager(), null);
        }
    }

    public void updateNavigationUi(int navigation) {
        mNavController.setGraph(navigation);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboard).build();
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void showProgress() {
        mBinding.mainProgressCircular.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        canGoBack = false;
    }

    public void dismissProgress() {
        mBinding.mainProgressCircular.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        canGoBack = true;
    }

    public static void msDismissProgress() {
        msBinding.mainProgressCircular.setVisibility(View.GONE);
        msAppClass.showSnackBar(baseActivity, "Timed out try again !");
        Log.e("TAG", "msDismissProgress: " );
        baseActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        canGoBack = true;
    }

    public static void msDismissProgressUpdateNavigation() {
        msBinding.mainProgressCircular.setVisibility(View.GONE);
        baseActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        canGoBack = true;
        baseActivity.mNavController.setGraph(R.navigation.scan);
        baseActivity.mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboard).build();
        NavigationUI.setupActionBarWithNavController(baseActivity, baseActivity.mNavController, baseActivity.mAppBarConfiguration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    @Override
    public void onBackPressed() {
        if (canGoBack) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestory: ");
    }
}