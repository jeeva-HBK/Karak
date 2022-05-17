package com.pradeep.karak.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.acra.ACRA;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;

import static com.pradeep.karak.Activity.BaseActivity.msDismissProgress;
import static com.pradeep.karak.Activity.BaseActivity.msDismissProgressUpdateNavigation;


@AcraMailSender(mailTo = "silambarasanraxgbc@gmail.com")

@AcraToast(resText = R.string.acra_toast_text,
        length = Toast.LENGTH_LONG)

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
            MACHINE_FLUSH_ID = "22",


    // 10 -> DispensingRatio
    KARAK_SUB_ID = "01,", KARAK_MILK_SUB_ID = "02,", KARAK_WATER_SUB_ID = "03,", KARAK_SUGAR_SUB_ID = "04,",
            MASALA_KARAK_TEA_SUB_ID = "05,", MASALA_KARAK_MASALA_SUB_ID = "06,", MASALA_KARAK_MILK_SUB_ID = "07,",
            MASALA_KARAK_WATER_SUB_ID = "08,", MASALA_KARAK_SUGAR_SUB_ID = "09,",
            SULAIMANI_TEA_SUB_ID = "10,", SULAIMANI_WATER_SUB_ID = "11,", SULAIMANI_SUGAR_SUB_ID = "12,",
            GINGER_KARAK_TEA_SUB_ID = "13,", GINGER_KARAK_GINGER_SUB_ID = "14,",
            GINGER_KARAK_MILK_SUB_ID = "15,", GINGER_KARAK_WATER_SUB_ID = "16,",  GINGER_KARAK_SUGAR_SUB_ID = "17,",
            CARDAMOM_KARAK_TEA_SUB_ID = "18,", CARDAMOM_KARAK_CARDAMOM_SUB_ID = "19,",
            CARDMOM_KARAK_MILK_SUB_ID = "20,", CARDMOM_KARAK_WATER = "21,", CARDMOM_KARAK_SUGAR = "22,",
            MILK_SUB_ID = "23,", MILK_WATER_SUB_ID = "24,", MILK_SUGAR_SUB_ID = "25,",
            HOT_WATER_SUB_ID = "26,", HOT_SUGAR_SUB_ID = "27,",
            COFFEE_SUB_ID = "28,", COFFEE_MILK_SUB_ID = "29,", COFFEE_WATER_SUB_ID = "30,", COFFEE_SUGAR_SUB_ID = "31,",
            NO_OF_CUP_ML = "32",

    // 11 -> BoilTime
            BOIL_TIME_KARAK_FIRST_CUP = "01,", BOIL_TIME_KARAK_SECOND_CUP = "02,",
            BOIL_TIME_KARAK_THIRD_CUP = "03,", BOIL_TIME_KARAK_FOURTH_CUP = "04,",
            BOIL_TIME_KARAK_FIVTH_CUP = "05,", BOIL_TIME_KARAK_SIXTH_CUP = "06,",
            BOIL_TIME_KARAK_SEVENTH_CUP = "07,", BOIL_TIME_KARAK_EIGHTH_CUP = "08,",
            BOIL_TIME_KARAK_NINETH_CUP = "09,", BOIL_TIME_KARAK_TENTH_CUP = "10,",
            BOIL_TIME_GINGERKARAK_FIRST_CUP = "11,", BOIL_TIME_GINGERKARAK_SECOND_CUP = "12,",
            BOIL_TIME_GINGERKARAK_THIRD_CUP = "13,", BOIL_TIME_GINGERKARAK_FOURTH_CUP = "14,",
            BOIL_TIME_GINGERKARAK_FIVTH_CUP = "15,", BOIL_TIME_GINGERKARAK_SIXTH_CUP = "16,",
            BOIL_TIME_GINGERKARAK_SEVENTH_CUP = "17,", BOIL_TIME_GINGERKARAK_EIGHTH_CUP = "18,",
            BOIL_TIME_GINGERKARAK_NINETH_CUP = "19,", BOIL_TIME_GINGERKARAK_TENTH_CUP = "20,",
            BOIL_TIME_MASALAKARAK_FIRST_CUP = "21,", BOIL_TIME_MASALAKARAK_SECOND_CUP = "22,",
            BOIL_TIME_MASALAKARAK_THIRD_CUP = "23,", BOIL_TIME_MASALAKARAK_FOURTH_CUP = "24,",
            BOIL_TIME_MASALAKARAK_FIVTH_CUP = "25,", BOIL_TIME_MASALAKARAK_SIXTH_CUP = "26,",
            BOIL_TIME_MASALAKARAK_SEVENTH_CUP = "27,", BOIL_TIME_MASALAKARAK_EIGHTH_CUP = "28,",
            BOIL_TIME_MASALAKARAK_NINETH_CUP = "29,", BOIL_TIME_MASALAKARAK_TENTH_CUP = "30,",
            BOIL_TIME_SULAIMANI_FIRST_CUP = "31,", BOIL_TIME_SULAIMANI_SECOND_CUP = "32,",
            BOIL_TIME_SULAIMANI_THIRD_CUP = "33,", BOIL_TIME_SULAIMANI_FOURTH_CUP = "34,",
            BOIL_TIME_SULAIMANI_FIVTH_CUP = "35,", BOIL_TIME_SULAIMANI_SIXTH_CUP = "36,",
            BOIL_TIME_SULAIMANI_SEVENTH_CUP = "37,", BOIL_TIME_SULAIMANI_EIGHTH_CUP = "38,",
            BOIL_TIME_SULAIMANI_NINETH_CUP = "39,", BOIL_TIME_SULAIMANI_TENTH_CUP = "40,",
            BOIL_TIME_CARDAMOM_FIRST_CUP = "41,", BOIL_TIME_CARDAMOM_SECOND_CUP = "42,",
            BOIL_TIME_CARDAMOM_THIRD_CUP = "43,", BOIL_TIME_CARDAMOM_FOURTH_CUP = "44,",
            BOIL_TIME_CARDAMOM_FIVTH_CUP = "45,", BOIL_TIME_CARDAMOM_SIXTH_CUP = "46,",
            BOIL_TIME_CARDAMOM_SEVENTH_CUP = "47,", BOIL_TIME_CARDAMOM_EIGHTH_CUP = "48,",
            BOIL_TIME_CARDAMOM_NINETH_CUP = "49,", BOIL_TIME_CARDAMOM_TENTH_CUP = "50,",
            BOIL_TIME_MILK_FIRST_CUP = "51,", BOIL_TIME_MILK_SECOND_CUP = "52,",
            BOIL_TIME_MILK_THIRD_CUP = "53,", BOIL_TIME_MILK_FOURTH_CUP = "54,",
            BOIL_TIME_MILK_FIVTH_CUP = "55,", BOIL_TIME_MILK_SIXTH_CUP = "56,",
            BOIL_TIME_MILK_SEVENTH_CUP = "57,", BOIL_TIME_MILK_EIGHTH_CUP = "58,",
            BOIL_TIME_MILK_NINETH_CUP = "59,", BOIL_TIME_MILK_TENTH_CUP = "60,",
            BOIL_TIME_WATER_FIRST_CUP = "61,", BOIL_TIME_WATER_SECOND_CUP = "62,",
            BOIL_TIME_WATER_THIRD_CUP = "63,", BOIL_TIME_WATER_FOURTH_CUP = "64,",
            BOIL_TIME_WATER_FIVTH_CUP = "65,", BOIL_TIME_WATER_SIXTH_CUP = "66,",
            BOIL_TIME_WATER_SEVENTH_CUP = "67,", BOIL_TIME_WATER_EIGHTH_CUP = "68,",
            BOIL_TIME_WATER_NINETH_CUP = "69,", BOIL_TIME_WATER_TENTH_CUP = "70,",
            BOIL_TIME_COFFEE_FIRST_CUP = "71,", BOIL_TIME_COFFEE_SECOND_CUP = "72,",
            BOIL_TIME_COFFEE_THIRD_CUP = "73,", BOIL_TIME_COFFEE_FOURTH_CUP = "74,",
            BOIL_TIME_COFFEE_FIVTH_CUP = "75,", BOIL_TIME_COFFEE_SIXTH_CUP = "76,",
            BOIL_TIME_COFFEE_SEVENTH_CUP = "77,", BOIL_TIME_COFFEE_EIGHTH_CUP = "78,",
            BOIL_TIME_COFFEE_NINETH_CUP = "79,", BOIL_TIME_COFFEE_TENTH_CUP = "80,",

    // 12 -> SendPassword
    ADMIN_PASSWORD_SUB_ID = "01,", MAINTENANCE_PASSWORD_SUB_ID = "02,", CUP_COUNT_PASSWORD_SUB_ID = "03,",


    // 13 -> SendCorrectionFactor
    KARAK_CORRECTION_FACTOR_SUB_ID = "01,", MASALA_KARAK_CORRECTION_FACTOR_SUB_ID = "02,",
            GINGER_KARAK_CORRECTION_FACTOR_SUB_ID = "03,", CARDAMOM_KARAK_CORRECTION_FACTOR_SUB_ID = "04,",
            MILK_CORRECTION_FACTOR_SUB_ID = "05,", WATER_CORRECTION_FACTOR_SUB_ID = "06,",
            SUGAR_CORRECTION_FACTOR_SUB_ID = "07,", COFFEE_CORRECTION_FACTOR_SUB_ID = "08,",

    // 14 -> SendFlowRate
    KARAK_FLOW_RATE_SUB_ID = "01,", MASALA_KARAK_FLOW_RATE_FACTOR_SUB_ID = "02,",
            GINGER_KARAK_FLOW_RATE_SUB_ID = "03,", CARDAMOM_KARAK_FLOW_RATE_SUB_ID = "04,",
            MILK_FLOW_RATE_SUB_ID = "05,", WATER_FLOW_RATE_SUB_ID = "06,",
            SUGAR_FLOW_RATE_SUB_ID = "07,", COFFEE_FLOW_RATE_SUB_ID = "08,",

    // 15-> PresentBoilTime
    PRESENT_BOIL_TIME_100ML_SUB_ID = "01,",
            PRESENT_BOIL_TIME_200ML_SUB_ID = "02,", PRESENT_BOIL_TIME_300ML_SUB_ID = "03,",
            PRESENT_BOIL_TIME_400ML_SUB_ID = "04,", PRESENT_BOIL_TIME_500ML_SUB_ID = "05,",
            PRESENT_BOIL_TIME_600ML_SUB_ID = "06,", PRESENT_BOIL_TIME_700ML_SUB_ID = "07,",
            PRESENT_BOIL_TIME_800ML_SUB_ID = "08,", PRESENT_BOIL_TIME_900ML_SUB_ID = "09,",
            PRESENT_BOIL_TIME_1000ML_SUB_ID = "10,",

    // Data Read Packet
    DISPENSING_READ_SUB_ID = "012;", BOIL_TIME_READ_SUB_KARAK_ID = "044;",
            BOIL_TIME_READ_SUB_GINGERKARAK_ID = "054;", BOIL_TIME_READ_SUB_MASALAKARAK_ID = "064;",
            BOIL_TIME_READ_SUB_SULAIMANI_ID = "074", BOIL_TIME_READ_SUB_CARDAMOM_ID = "084;",
            BOIL_TIME_READ_SUB_MILK_ID = "094;", BOIL_TIME_READ_SUB_WATER_ID = "104;",
            BOIL_TIME_READ_SUB_COFFEE_ID = "114;",
            CORRECTION_FACTOR_READ_SUB_ID = "124;", FLOW_RATE_READ_SUB_ID = "132;",
            PRESET_BOIL_TIME_READ_SUB_ID = "140;", INDUCTION_HEATER_PROXIMITY_SENSOR_FIRMWARE = "62;";

    // Static_Keys
    public static String KEY_BEVERAGE_SELECTION = "pckBeverageSelection", KEY_CUP = "pckCup";

    // ParameterKeyForDispensingRatio
    public static String DR_KARAK = "", DR_KARAK_MILK = "", DR_KARAK_WATER = "", DR_KARAK_SUGAR = "",
            DR_MASALA_KARAK_TEA = "", DR_MASALA_KARAK_MASALA = "", DR_MASALA_KARAK_MILK = "",
            DR_MASALA_KARAKA_WATER = "", DR_MASALA_KARAKA_SUGAR = "",
            DR_SULAIMANI_TEA = "", DR_SULAIMANI_WATER = "", DR_SULAIMANI_SUGAR = "",
            DR_GINGER_KARAK_TEA = "", DR_GINGER_KARAK_GINGER = "", DR_GINGER_KARAK_MILK = "",
            DR_GINGER_WATER = "", DR_GINGER_SUGAR = "",
            DR_CARDAMOM_KARAK_TEA = "", DR_CARADMOM_KARAK_CARDMOM = "",
            DR_CARDAMOM_KARAK_MILK = "", DR_CARDAMOM_KARAK_WATER = "", DR_CARDAMOM_KARAK_SUGAR = "",
            DR_MILK = "", DR_MILK_WATER = "", DR_MILK_SUGAR = "",
            DR_HOT_WATER = "", DR_HOT_WATER_SUGAR = "",
            DR_COFFEE = "", DR_COFFEE_MILK = "", DR_COFFEE_WATER = "", DR_COFFEE_SUGAR = "",
            DR_CUP_ML = "";

    // ParameterKeyForBoilTime
    public static String BOIL_TIME_100ML = "",
            BOIL_TIME_200ML = "", BOIL_TIME_300ML = "",
            BOIL_TIME_400ML = "",BOIL_TIME_500ML = "",
            BOIL_TIME_600ML = "", BOIL_TIME_700ML = "",
            BOIL_TIME_800ML = "", BOIL_TIME_900ML = "",
            BOIL_TIME_1000ML = "";

    // ParameterKeyForCorrectionFactor
    public static String KARAK_CORRECTION_FACTOR = "", MASALA_KARAK_CORRECTION_FACTOR = "",
            GINGER_KARAK_CORRECTION_FACTOR = "", CARDAMOM_KARAK_CORRECTION_FACTOR = "",
            MILK_CORRECTION_FACTOR = "", WATER_CORRECTION_FACTOR = "",
            SUGAR_CORRECTION_FACTOR = "", COFFEE_CORRECTION_FACTOR = "";

    // ParameterKeyForFlowRate
    public static String KARAK_FLOW_RATE = "", MASALA_KARAK_FLOW_RATE = "",
            GINGER_KARAK_FLOW_RATE = "", CARDAMOM_KARAK_FLOW_RATE = "",
            MILK_FLOW_RATE = "", WATER_FLOW_RATE = "",
            SUGAR_FLOW_RATE = "", COFFEE_FLOW_RATE = "";

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
            helper.sendDataBLE(callback, packetToSend, 20000);
        } catch (Exception e) {
            Toast.makeText(mContext, "No Data for Bluetooth Please reconnect your device ", Toast.LENGTH_SHORT).show();
            msDismissProgressUpdateNavigation();
            e.printStackTrace();
        }
    }


    public void sendDataDispense(Activity activity, BluetoothDataCallback callback, String packetToSend, Context mContext) {
        try {
            BluetoothHelper helper = BluetoothHelper.getInstance(activity);
            helper.sendDataBLEForDispense(callback, packetToSend);
        } catch (Exception e) {
            Toast.makeText(mContext, "No Data for Bluetooth Please reconnect your device", Toast.LENGTH_SHORT).show();
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
        try {
            Navigation.findNavController((Activity) fragAct, R.id.nav_host_fragment).navigate(desID);
        }catch (Exception e){
            Log.e("TAG", "navigateToBundle: "+e );
        }

    }


    public void navigateToBundle(FragmentActivity activity, int fragmentIDinNavigation, Bundle b) {
        try {
            Navigation.findNavController((Activity) activity, R.id.nav_host_fragment).navigate(fragmentIDinNavigation, b);
        } catch (Exception e){
            Log.e("TAG", "navigateToBundle: "+e );
        }

    }

    public void popStackBack(FragmentActivity activity) {
        Navigation.findNavController((Activity) activity, R.id.nav_host_fragment).popBackStack();
    }

    public void disconnect() {
        SerialSocket serialSocket = SerialSocket.getInstance();
        serialSocket.disconnect();
    }

    public String framePacket(String packet) {
          return START_PACKET + packet + UtilMethods.CRCCalc(packet) + ";" + END_PACKET;
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
        tv.setTextColor(Color.BLACK);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.WHITE);
        tv.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }


    @Override
    protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
     //   ACRA.init(this);
    }
}
