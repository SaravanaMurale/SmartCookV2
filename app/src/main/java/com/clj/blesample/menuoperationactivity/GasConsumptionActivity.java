package com.clj.blesample.menuoperationactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clj.blesample.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class GasConsumptionActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_consumption);

        int y,x;
        x=0;
        y=0;

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d7 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d8 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d9 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date d10 = calendar.getTime();

        GraphView graph=(GraphView) findViewById(R.id.graphView);
        series=new LineGraphSeries<DataPoint>();


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 10),
                new DataPoint(d2, 2),
                new DataPoint(d3, 8),
                new DataPoint(d4, 1),
                new DataPoint(d5, 9),
                new DataPoint(d6, 3),
                new DataPoint(d7, 10),
                new DataPoint(d8, 2),
                new DataPoint(d9, 7),
                new DataPoint(d10, 5)

        });




        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GasConsumptionActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(10); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        //set Manual X axis
        /*graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

        //set Manual Y axis
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxX(15);*/

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);

    }
}