package com.clj.blesample.menuoperationactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.clj.blesample.R;
import com.clj.blesample.adapter.StatisticsAdapter;
import com.clj.blesample.model.StatisticsDTO;

import java.util.ArrayList;
import java.util.List;

public class StatisticsReportActivity extends AppCompatActivity {

    RecyclerView stat_report_recyclerView;
    List<StatisticsDTO> statisticsDTOList;
    StatisticsAdapter statisticsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_report);

        stat_report_recyclerView=(RecyclerView)findViewById(R.id.stati_report_recyclerview);
        stat_report_recyclerView.setHasFixedSize(true);
        stat_report_recyclerView.setLayoutManager(new LinearLayoutManager(StatisticsReportActivity.this));

        statisticsDTOList=new ArrayList<>();

        statisticsAdapter=new StatisticsAdapter(StatisticsReportActivity.this,statisticsDTOList);
        stat_report_recyclerView.setAdapter(statisticsAdapter);

        getStatisticsReportFromSqliteDB();

    }

    private void getStatisticsReportFromSqliteDB() {



    }
}
