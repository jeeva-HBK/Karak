package com.pradeep.karak.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.pradeep.karak.Activity.BaseActivity;
import com.pradeep.karak.BLE.BluetoothDataCallback;
import com.pradeep.karak.Others.ApplicationClass;
import com.pradeep.karak.R;
import com.pradeep.karak.databinding.FragmentAdSubchildStatisticsBinding;

import java.util.ArrayList;

import static com.pradeep.karak.Others.ApplicationClass.ADMIN_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_PASSWORD;
import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_RESET_MESSAGE_ID;
import static com.pradeep.karak.Others.ApplicationClass.MAINTENANCE_PASSWORD;

public class FragmentAdSubChildStatistics extends Fragment implements BluetoothDataCallback {
    FragmentAdSubchildStatisticsBinding mBinding;
    ApplicationClass mAppClass;
    BaseActivity mActivity;
    Context context;
    AlertDialog alertDialog;
    String data = "", cupKarak = "0", cupGinger = "0", cupSulaimani = "0", cupMasala = "0", cupCardmom = "0", cupMilk = "0", cupHotWater = "0";
    EditText reset;
    public static final String TAG = "Statistics";

    public FragmentAdSubChildStatistics(String data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_statistics, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // if (getTag().equals("TAG_STATISTICS")) {
        readOperatorData();
        //  } else {
        //    setDataToChart(getChartData(0), getChartbevarage(0), 0);
        //  }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mActivity = (BaseActivity) getActivity();
        context = getContext();
        ObservableInt pageCount = new ObservableInt(0);
        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
        if (!data.equals("") && data != null && !data.equals("emptyData")) {
            handleResponse(data);
        }

        pageCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switch (pageCount.get()) {
                    case 0:
                        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        setDataToChart(getChartData(0), getChartbevarage(0), 0);
                        break;
                    case 1:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        setDataToChart(getChartData(1), getChartbevarage(1), 1);
                        break;
                    case 2:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.GONE);
                        setDataToChart(getChartData(2), getChartbevarage(2), 0);
                        break;
                }
            }
        });

        mBinding.IvAdLeftArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() - 1);
        }));

        mBinding.IvAdRightArrow.setOnClickListener((view1 -> {
            pageCount.set(pageCount.get() + 1);

        }));

        mBinding.statisticsReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void readOperatorData() {
        mActivity.showProgress();
        sendData(mAppClass.framePacket("07;01;"));
    }

    private void sendData(String framedPacket) {
        mAppClass.sendData(getActivity(), FragmentAdSubChildStatistics.this, framedPacket, getContext());
    }

    private void resetPassword() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_reset_cup_count, null);
        reset = dialogView.findViewById(R.id.edt_cup_count_reset);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View Go = dialogView.findViewById(R.id.dialog_cup_count_reset);
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBoxValidation()) {
                    mAppClass.sendData(getActivity(), FragmentAdSubChildStatistics.this, mAppClass.framePacket(CUP_COUNT_RESET_MESSAGE_ID), getContext());
                    readOperatorData();
                }
            }
        });
    }

    boolean dialogBoxValidation() {
        if (reset.getText().toString().isEmpty()) {
            mAppClass.showSnackBar(getContext(), "Please enter the password");
            return false;
        } else if (!reset.getText().toString().equals(CUP_COUNT_PASSWORD)) {
            mAppClass.showSnackBar(getContext(), "Password is wrong");
            return false;
        }
        return true;
    }

    private ArrayList<String> getChartbevarage(int mode) {
        ArrayList<String> values = new ArrayList<>();
        switch (mode) {
            case 0:
                values.add("Karak");
                values.add("Sulaimani");
                break;

            case 1:
                values.add("Cardomon");
                values.add("Ginger");
                values.add("Masala");
                break;

            case 2:
                values.add("Milk");
                values.add("water");
                break;
        }
        return values;
    }

    private ArrayList<BarEntry> getChartData(int mode) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        switch (mode) {
            case 0:
                entries.add(0, new BarEntry(0, Float.parseFloat(cupKarak)));
                entries.add(1, new BarEntry(1, Float.parseFloat(cupSulaimani)));
                break;

            case 1:
                entries.add(0, new BarEntry(0, Float.parseFloat(cupCardmom)));
                entries.add(1, new BarEntry(1, Float.parseFloat(cupGinger)));
                entries.add(2, new BarEntry(2, Float.parseFloat(cupMasala)));
                break;

            case 2:
                entries.add(0, new BarEntry(0, Float.parseFloat(cupMilk)));
                entries.add(1, new BarEntry(1, Float.parseFloat(cupHotWater)));
                break;
        }
        return entries;
    }

    private void setDataToChart(ArrayList yValues, ArrayList<String> xValues, int mode) {
        ArrayList<BarEntry> entries = yValues;
        BarDataSet dataSet = new BarDataSet(entries, "Beverages");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.textColor));
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setValueTextSize(20f);
        data.setValueFormatter(new valueFormatter());
        data.setValueTextColor(getResources().getColor(R.color.textColor));
        data.setBarWidth(0.5f);
        data.setHighlightEnabled(false);

        mBinding.chart1.setData(data);
        mBinding.chart1.setScaleEnabled(false);
        ArrayList<String> beverages = xValues;
        XAxis xAxis = mBinding.chart1.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.textColor));
        xAxis.setGridColor(getResources().getColor(R.color.black));
        xAxis.setLabelCount(entries.size());
        mBinding.chart1.getAxisLeft().setGridColor(getResources().getColor(R.color.black));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        if (mode == 0) {
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value == 0.0) {
                        return beverages.get(0);
                    } else if (value == 1.0) {
                        return beverages.get(1);
                    }
                    return "";
                }
            });
        } else if (mode == 1) {
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value == 0.0) {
                        return beverages.get(0);
                    } else if (value == 1.0) {
                        return beverages.get(1);
                    } else if (value == 2.0) {
                        return beverages.get(2);
                    }
                    return "";
                }
            });
        }
        mBinding.chart1.getDescription().setEnabled(false);
        mBinding.chart1.getLegend().setEnabled(false);
        mBinding.chart1.getAxisLeft().setDrawAxisLine(false);
        mBinding.chart1.getAxisRight().setDrawAxisLine(false);
        mBinding.chart1.getXAxis().setDrawAxisLine(false);
        mBinding.chart1.getAxisLeft().setDrawGridLines(false);
        mBinding.chart1.getXAxis().setDrawGridLines(false);
        mBinding.chart1.getAxisRight().setDrawGridLines(false);
        mBinding.chart1.setExtraOffsets(0, 0, 0, 0);
        mBinding.chart1.invalidate();
    }

    @Override
    public void OnDataReceived(String data) {
        handleResponse(data);
    }

    private void handleResponse(String data) {
            mActivity.dismissProgress();
            String[] spiltData = data.split(";");
            if (spiltData[0].substring(5, 7).equals("09")) {
                if (spiltData[1].equals("ACK")) {
                    alertDialog.dismiss();
                    Toast.makeText(context, "CupCount Reset Success !", Toast.LENGTH_SHORT).show();
                }
            } else if (spiltData[0].substring(5, 7).equals("07")) {
                String[] adminPassword = spiltData[2].split(","), maintenancePassword = spiltData[2].split(","), cupcountPassword = spiltData[3].split(","),
                        ccKarak = spiltData[4].split(","), ccGKarak = spiltData[5].split(","), ccMKarak = spiltData[6].split(","),
                        ccSulaimani = spiltData[7].split(","), ccCKarak = spiltData[8].split(","), ccMilk = spiltData[9].split(","),
                        ccWater = spiltData[10].split(",");

                ADMIN_PASSWORD = adminPassword[1];
                MAINTENANCE_PASSWORD = maintenancePassword[1];
                CUP_COUNT_PASSWORD = cupcountPassword[1];

                cupKarak = ccKarak[1];
                cupGinger = ccGKarak[1];
                cupCardmom = ccCKarak[1];
                cupMasala = ccMKarak[1];
                cupSulaimani = ccSulaimani[1];
                cupMilk = ccMilk[1];
                cupHotWater = ccWater[1];
                setDataToChart(getChartData(0), getChartbevarage(0), 0);

            }

    }

    @Override
    public void OnDataReceivedError(Exception e) {
        e.printStackTrace();
    }

    public class valueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return "" + ((int) value);
        }
    }
}
