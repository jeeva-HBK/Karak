package com.pradeep.karak.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.AxisBase;
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

import static com.pradeep.karak.Others.ApplicationClass.CUP_COUNT_RESET_MESSAGE_ID;

public class FragmentAdSubChildStatistics extends Fragment implements BluetoothDataCallback {
    FragmentAdSubchildStatisticsBinding mBinding;
    ApplicationClass mAppClass;
    BaseActivity mActivity;
    Context context;
    AlertDialog alertDialog;

    public static final String TAG = "Statistics";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_subchild_statistics, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        context = getContext();
        ObservableInt pageCount = new ObservableInt(0);
        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
        setDataToChart(getChartData(0), getChartbevarage(0));
        Log.e("TAG", "onPropertyChanged: " + pageCount.get());
        pageCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.e("TAG", "onPropertyChanged: " + pageCount.get());
                switch (pageCount.get()) {
                    case 0:
                        mBinding.IvAdLeftArrow.setVisibility(View.GONE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        setDataToChart(getChartData(0), getChartbevarage(0));
                        break;
                    case 1:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.VISIBLE);
                        setDataToChart(getChartData(1), getChartbevarage(1));
                        break;
                    case 2:
                        mBinding.IvAdLeftArrow.setVisibility(View.VISIBLE);
                        mBinding.IvAdRightArrow.setVisibility(View.GONE);
                        setDataToChart(getChartData(2), getChartbevarage(2));
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

    private void resetPassword() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_reset_cup_count, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View Go = dialogView.findViewById(R.id.dialog_cup_count_reset);
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.sendData(getActivity(), FragmentAdSubChildStatistics.this, mAppClass.framePacket(CUP_COUNT_RESET_MESSAGE_ID), getContext());
            }
        });


    }

    private ArrayList<String> getChartbevarage(int mode) {
        ArrayList<String> values = new ArrayList<>();
        switch (mode) {
            case 0:
                values.add("Karak");
                values.add("Sulaimani");
                break;

            case 1:
                values.add("Cardomon Karak");
                values.add("Ginger Karak");
                values.add("Masala Karak");
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
                entries.add(new BarEntry(0, 100));
                entries.add(new BarEntry(1, 200));
                break;

            case 1:
                entries.add(new BarEntry(0, 300));
                entries.add(new BarEntry(1, 400));
                entries.add(new BarEntry(1, 500));
                break;

            case 2:
                entries.add(new BarEntry(0, 600));
                entries.add(new BarEntry(1, 700));
                break;
        }
        return entries;
    }

    private void setDataToChart(ArrayList<BarEntry> entry, ArrayList<String> beverage) {
        ArrayList<BarEntry> entries = entry;
        BarDataSet dataSet = new BarDataSet(entries, "Beverages");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.textColor));
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setValueTextSize(20f);
        data.setValueFormatter(new valueFormatter());
        data.setValueTextColor(getResources().getColor(R.color.textColor));
        data.setBarWidth(0.5f);

        mBinding.chart1.setData(data);
        mBinding.chart1.setScaleEnabled(false);
        ArrayList<String> beverages = beverage;
        XAxis xAxis = mBinding.chart1.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.textColor));
        xAxis.setGridColor(getResources().getColor(R.color.black));
        xAxis.setLabelCount(1);
        mBinding.chart1.getAxisLeft().setGridColor(getResources().getColor(R.color.black));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return beverages.get((int) value);
            }
        });
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
        alertDialog.dismiss();
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
