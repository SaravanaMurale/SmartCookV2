package com.clj.blesample.menuoperationactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.clj.blesample.R;
import com.clj.blesample.adapter.StatisticsAdapter;
import com.clj.blesample.model.StatisticsDTO;

import java.util.ArrayList;
import java.util.List;

public class StatisticsReportActivity extends AppCompatActivity {

    Button leftButton, centerButton, rightButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_report);

        leftButton = (Button) findViewById(R.id.leftBurnerType);
        centerButton = (Button) findViewById(R.id.centerBurnerType);
        rightButton = (Button) findViewById(R.id.rightBurnerType);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callAllBurnerStatisticsActivity=new Intent(StatisticsReportActivity.this,AllBurnerStatisticsActivity.class);
                callAllBurnerStatisticsActivity.putExtra("BURNERTYPE","LEFT1");
                startActivity(callAllBurnerStatisticsActivity);
            }
        });

        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callAllBurnerStatisticsActivity=new Intent(StatisticsReportActivity.this,AllBurnerStatisticsActivity.class);
                callAllBurnerStatisticsActivity.putExtra("BURNERTYPE","CENTER1");
                startActivity(callAllBurnerStatisticsActivity);

            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callAllBurnerStatisticsActivity=new Intent(StatisticsReportActivity.this,AllBurnerStatisticsActivity.class);
                callAllBurnerStatisticsActivity.putExtra("BURNERTYPE","RIGHT1");
                startActivity(callAllBurnerStatisticsActivity);
            }
        });


    }


}
