package com.pradeep.karak.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Adapter.BluetoothFavListAdapter;
import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentWelcomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentWelcome extends Fragment implements ItemClickListener {
    private static final String TAG = "MachineListFragment";
    private static String SERIAL_NO = "SerialNo", ADDRESS = "Address", END_POINT = "EndPoint", connectPacket = "1;";
    private FragmentWelcomeBinding mBinding;
    private ApplicationClass mAppClass;
    private BaseActivity mActivity;
    private Context mContext;
    SharedPreferences preferences;
    BluetoothFavListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_welcome, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().show();
        mContext = getActivity().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mBinding.recylerFavList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        loadFav();
        mBinding.imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppClass.navigateTo(getActivity(), R.id.action_fragment_machine_list_to_dialogBluetoothList);
            }
        });
    }

    private void loadFav() {
        try {
            mAdapter = new BluetoothFavListAdapter(new JSONArray(preferences.getString("savedMac", "")), this);
            mBinding.recylerFavList.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnItemClick(int pos) {

    }

    @Override
    public void onSaveClicked(String mac) {

    }

    @Override
    public void onUnSave(String data) {
        try {
            JSONArray jArr = new JSONArray(preferences.getString("savedMac", ""));
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                String mString = obj.getString("mac");
                if (mString.equals(data)) {
                    jArr.remove(i);
                }
            }
            preferences.edit().putString("savedMac", jArr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadFav();
        Log.e(TAG, "onSaveClicked: " + preferences.getString("savedMac", ""));
    }



   /* private void showBleList() {
        DialogFragment dialog = new DialogFragment();
        DialogBluetoothList dialogBluetoothList = new DialogBluetoothList(dialog);
        dialog.init(dialogBluetoothList, "Bluetooth List", new DialogDismissListener() {
            @Override
            public void OnDismiss() {
                mActivity.updateNavigationUi();
            }
        });
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), "tBluetoothList");
    }*/
}
