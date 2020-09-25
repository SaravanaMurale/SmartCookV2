package com.clj.blesample.operation;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clj.blesample.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = (BarChart) findViewById(R.id.barChart);

        getEntries();

        barDataSet = new BarDataSet(barEntries, "Date");
        barData = new BarData(barDataSet);

        barChart.setData(barData);

        barDataSet.setColor(getColor(R.color.red));
        barDataSet.setValueTextColor(getColor(R.color.black));
        barDataSet.setValueTextSize(16f);
    }

    private void getEntries() {

        barEntries = new ArrayList();

        barEntries.add(new BarEntry(1, 2));
        barEntries.add(new BarEntry(2, 3));
        barEntries.add(new BarEntry(3, 4));
        barEntries.add(new BarEntry(4, 5));
        barEntries.add(new BarEntry(5, 6));
        barEntries.add(new BarEntry(6, 7));
        barEntries.add(new BarEntry(7, 8));
        barEntries.add(new BarEntry(8, 9));
        barEntries.add(new BarEntry(9, 10));
        barEntries.add(new BarEntry(10, 11));

        Date date=new Date();

        //barEntries.add(new Entry(new Long(date.getDate()).floatValue(), new Double(date.getValueY()).floatValue()));

    }
}