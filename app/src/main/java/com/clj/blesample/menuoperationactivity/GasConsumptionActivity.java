package com.clj.blesample.menuoperationactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clj.blesample.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GasConsumptionActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_consumption);

        /*Intent intent = getIntent();
        String fromDate = intent.getStringExtra("FROMDATE");
        String toDate = intent.getStringExtra("TODATE");
        intent.getStringExtra("BURNER");
        System.out.println("stringDate" + fromDate);*/

        GraphView graph = (GraphView) findViewById(R.id.graphView);
        series = new LineGraphSeries<DataPoint>();


        //long numberOfDays = findDifferenceBetweenDates(fromDate, toDate);
        //int noOfDays = (int) numberOfDays;

        /*Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < noOfDays; i++) {

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(fromDate);
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date d = calendar.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }*/


        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        // int d1= calendar.get(Calendar.DAY_OF_MONTH);


        System.out.println("DayAndMonth" + d1);

        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();


        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        // System.out.println("AllDate" + d1 + " " + d2 + " " + d3 + " " + d4 + " " + d5 + " " + d6 + " " + d7 + " " + d8 + " " + d9 + " " + d10);


        ArrayList<Date> arrayList = new ArrayList<>();

        arrayList.add(d1);
        arrayList.add(d2);
        arrayList.add(d3);
        arrayList.add(d4);

        ArrayList<Integer> value = new ArrayList<>();
        value.add(10);
        value.add(2);
        value.add(8);
        value.add(4);

        for (int i = 0; i < arrayList.size(); i++) {
             series = new LineGraphSeries<>(new DataPoint[]{
                            new DataPoint(arrayList.get(i), value.get(i)),


                    });

        }


        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GasConsumptionActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());

        graph.getViewport().setXAxisBoundsManual(false);


        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().scrollToEnd();


        //set Manual X axis
        /*graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

        //set Manual Y axis
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxX(15);*/


    }

    private long findDifferenceBetweenDates(String fromDate, String toDate) {

        long difference_In_Days = 0;

        try {

            Date fromDateFormet = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
            Date toDateFormet = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);


            System.out.println("StringToDateConversion" + fromDateFormet); // Sat Jan 02 00:00:00 GMT 2010
            System.out.println("StringToDateConversion" + toDateFormet);

            long difference_In_Time = toDateFormet.getTime() - fromDateFormet.getTime();

            difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

            System.out.println("NumberOfDays" + difference_In_Days);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return difference_In_Days;

    }
}