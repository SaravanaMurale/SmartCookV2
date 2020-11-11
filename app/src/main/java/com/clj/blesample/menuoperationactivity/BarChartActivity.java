package com.clj.blesample.menuoperationactivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.clj.blesample.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BarChartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LineChart lineChart;

    GraphView graphView;


    String[] burners = {"Select Burner", "Center", "Left", "Right"};
    Spinner spinner;

    DatePickerDialog.OnDateSetListener setListenerFromDate, setListenerToDate;
    TextView fromDate, toDate;

    
    LineGraphSeries<DataPoint> series;
    SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        spinner = (Spinner) findViewById(R.id.burnerSpinner);

        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);


        spinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, burners);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        graphView = (GraphView) findViewById(R.id.graphView);
        series = new LineGraphSeries<>(getDataPoint());
        graphView.addSeries(series);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DATE);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(BarChartActivity.this, android.R.style.Theme_Holo_Light_Dialog, setListenerFromDate, year, month, date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(BarChartActivity.this, android.R.style.Theme_Holo_Light_Dialog, setListenerToDate, year, month, date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListenerFromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                fromDate.setText(date);

            }
        };


        setListenerToDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                toDate.setText(date);

            }
        };

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                                                               @Override
                                                               public String formatLabel(double value, boolean isValueX) {

                                                                   if (isValueX) {
                                                                       return sdf.format(new Date((long) value));
                                                                   } else {

                                                                       return super.formatLabel(value, isValueX);
                                                                   }
                                                               }
                                                           }
        );

        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);


        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x = 0 ;
        int numDataPoints = 50;

        for(int i=0;i<numDataPoints;i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 1;
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            xAXES.add(i, String.valueOf(x));
        }

        String[] xaxes = new String[xAXES.size()];
        for(int i=0; i<xAXES.size();i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin,"sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

       // lineChart.setData(new LineData(xaxes,lineDataSets));

        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);

    }

    private DataPoint[] getDataPoint() {

        System.out.println("DateFormet " + new Date());

        DataPoint[] dp = new DataPoint[]{


                new DataPoint(new Date().getDate(), 1),
                new DataPoint(new Date().getDate(), 5),
                new DataPoint(new Date().getDate(), 10),
                new DataPoint(new Date().getDate(), 15),
                new DataPoint(new Date().getDate(), 20),
                new DataPoint(new Date().getDate(), 25),
                new DataPoint(new Date().getDate(), 30),
                new DataPoint(new Date().getDate(), 35),
                new DataPoint(new Date().getDate(), 40),
                new DataPoint(new Date().getDate(), 45)


        };

        return dp;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String burner = parent.getItemAtPosition(position).toString();
        System.out.println("SelectedBurners" + burner);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}