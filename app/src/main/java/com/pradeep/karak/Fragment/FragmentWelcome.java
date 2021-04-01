package com.pradeep.karak.Fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.Adapter.BluetoothFavListAdapter;
import com.pradeep.karak.BLE.BluetoothConnectCallback;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.BLE.BluetoothHelper;
import com.pradeep.karak.BLE.BluetoothScannerCallback;
import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentWelcomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.pradeep.karak.BLE.SerialSocket.single_instance;


public class FragmentWelcome extends Fragment implements ItemClickListener, BluetoothDataCallback {
    private static final String TAG = "MachineListFragment";
    private static String SERIAL_NO = "SerialNo", ADDRESS = "Address", END_POINT = "EndPoint", connectPacket = "1;";
    private FragmentWelcomeBinding mBinding;
    private ApplicationClass mAppClass;
    private BaseActivity mActivity;
    private Context mContext;
    SharedPreferences preferences;
    BluetoothFavListAdapter mAdapter;

    boolean dataReceived = false;

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

    private void connect(String macAddress) {
        mActivity.showProgress();
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        if (helper.isConnected()) {
            helper.disConnect();
        }
        try {
            helper.scanBLE(new BluetoothScannerCallback() {
                @Override
                public void OnScanCompleted(List<BluetoothDevice> devices) {
                    for (BluetoothDevice device : devices) {
                        if (device.getAddress().equals(macAddress)) {
                            return;
                        }
                    }
                    mActivity.dismissProgress();
                    new MaterialAlertDialogBuilder(mContext).setTitle("Connect failed").setMessage(Html.fromHtml("Possible reasons for connection failure" + "<br/>" +
                            "• The Machine is powered off." + "<br/>" +
                            "• The Machine is connected to some other mobile phone." + "<br/>" +
                            "• The Machine is not in the specified range.")).setPositiveButton("OK", null).show();
                }

                @Override
                public void SearchResult(BluetoothDevice device) {

                }

                @Override
                public void OnDeviceFoundUpdate(List<BluetoothDevice> devices) {
                    try {
                        BluetoothDevice mDevice = null;

                        for (int i = 0; i < devices.size(); i++) {
                            if (devices.get(i).getAddress().equals(macAddress)) {
                                mDevice = devices.get(i);
                            }
                        }
                        helper.connectBLE(mContext, mDevice, new BluetoothConnectCallback() {
                            @Override
                            public void OnConnectSuccess() {
                                try {
                                    Log.e(TAG, "OnConnectSuccess");
                                    int i = 0;
                                    while (i < 5) {
                                        dataReceived = false;
                                        helper.sendDataBLE(FragmentWelcome.this, mAppClass.framePacket("01;"));
                                        i++;
                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!dataReceived) {
                                                mActivity.dismissProgress();
                                                Toast.makeText(mContext, "Timed Out", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, 5000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void OnConnectFailed(Exception e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        single_instance = null;
                                        Toast.makeText(mContext, "Unable to connect to machine", Toast.LENGTH_SHORT).show();
                                        mActivity.dismissProgress();
                                    }
                                });
                            }
                        });
                    } catch (Exception e) {
                        mActivity.dismissProgress();
                        Toast.makeText(mContext, "Error occurred", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, BluetoothHelper.SearchType.ByMAC, macAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveClicked(String mac) {
        mConnect(mac);
    }

    private void mConnect(String mac) {
        mActivity.showProgress();
        BluetoothHelper helper = BluetoothHelper.getInstance(getActivity());
        if (helper.isConnected()) {
            helper.disConnect();
        }
        try {
            helper.scanBLE(new BluetoothScannerCallback() {
                @Override
                public void OnScanCompleted(List<BluetoothDevice> devices) {
                    for (int i = 0; i < devices.size(); i++) {
                        if (devices.get(i).getAddress().equals(mac)) {
                            BluetoothDevice mDevice = devices.get(i);
                            connectBle(mDevice, helper);
                        }
                    }
                }

                @Override
                public void SearchResult(BluetoothDevice device) {
                }

                @Override
                public void OnDeviceFoundUpdate(List<BluetoothDevice> devices) {
                }
            });
        } catch (Exception e) {
            mActivity.dismissProgress();
            Toast.makeText(mContext, "Operation Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void connectBle(BluetoothDevice mDevice, BluetoothHelper helper) {
        try {
            helper.connectBLE(getContext(), mDevice, new BluetoothConnectCallback() {
                @Override
                public void OnConnectSuccess() {
                    int i = 0;
                    while (i < 5) {
                        dataReceived = false;
                        sendData(mAppClass.framePacket("01;"));
                        i++;
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!dataReceived) {
                                        mActivity.dismissProgress();
                                        Toast.makeText(mContext, "Timed Out", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, 5000);
                        }
                    });
                }

                @Override
                public void OnConnectFailed(Exception e) {
                    Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            mActivity.dismissProgress();
            Toast.makeText(mContext, "Operation Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void sendData(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentWelcome.this, framedPacket, getContext());
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

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
        dataReceived = true;
        String[] spiltData = data.split(";");
        if (spiltData[0].substring(5, 7).equals("01")) {
            if (spiltData[2].equals("ACK")) {
                if (spiltData[1].equals("00")) {
                    mActivity.dismissProgress();
                    mActivity.updateNavigationUi(R.navigation.navigation);
                }
            }
        }
    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }
}
