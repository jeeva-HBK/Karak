package com.pradeep.karak.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentRootConfigurationBinding;

import static com.pradeep.karak.Others.ApplicationClass.MAINTENANCE_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.MASTER_PASSWORD;


public class FragmentRootConfiguration extends Fragment implements BluetoothDataCallback {
    private static final String TAG = "FragmentRootConfig";
    FragmentRootConfigurationBinding mBinding;
    ApplicationClass mAppClass;
    String[] mainMenuList;
    BaseActivity mActivity;
    Context mcontext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_root_configuration, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainMenuList = new String[]{"Admin", "maintenance", "master"};
        mBinding.autoComplete.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_brown_bar));
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
        mcontext = getContext();
        // Bundle b = getArguments();
        // String data = b.getString("CUPCOUNT");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_autocomplete, mainMenuList);
        mBinding.autoComplete.setAdapter(arrayAdapter);
        getParentFragmentManager().beginTransaction().add(mBinding.configFragHost.getId(), new FragmentChildAdmin(getArguments().getString("CUPCOUNT")), "INIT_ADMIN").commit();
        mBinding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildAdmin("emptyData"), "TAG_ADMIN").commit();
                        break;

                    case 1:
                        checkPassword(MAINTENANCE_PASSWORD, 0);
                        break;

                    case 2:
                        checkPassword(MASTER_PASSWORD, 1);
                        break;
                }
            }
        });
        mBinding.IbBackArrow.setOnClickListener((view1 -> {
            mActivity.showProgress();
            mAppClass.sendData(getActivity(), FragmentRootConfiguration.this, mAppClass.framePacket("08;"), getContext());
        }));
    }

    private void checkPassword(String password, int mode) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mcontext);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_password, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        EditText editText = dialogView.findViewById(R.id.edt_password);
        View view = dialogView.findViewById(R.id.dialogPass_submit);
        TextView title = dialogView.findViewById(R.id.txt_maintance_access);
        if (mode == 1) {
            title.setText("Master Access");
        }
        try {
            view.setOnClickListener((view1 -> {
                if (editText.getText().toString().equals(password)) {
                    if (mode == 0) {
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildMaintenance(), "TAG_MAINTENANCE").commit();
                    } else if (mode == 1) {
                        getParentFragmentManager().beginTransaction().replace(mBinding.configFragHost.getId(), new FragmentChildMaster(), "TAG_MASTER").commit();
                    }
                    alertDialog.dismiss();
                } else {
                    editText.setError("Password Wrong !");

                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "checkPassword:" + e);
        }
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        String[] spiltData = data.split(";");
        if (spiltData[0].startsWith("08", 5)) {
            if (spiltData[1].equals("ACK")) {
                mAppClass.popStackBack(getActivity());
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
