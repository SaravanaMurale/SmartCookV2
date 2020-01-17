package com.clj.blesample.menuoperationactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.adapter.StatisticsAdapter;
import com.clj.blesample.databasemanager.SqliteManager;
import com.clj.blesample.model.StatisticsDTO;
import com.clj.blesample.utils.FontUtil;

import java.util.ArrayList;
import java.util.List;

public class AllBurnerStatisticsActivity extends AppCompatActivity {

    RecyclerView stat_report_recyclerView;
    List<StatisticsDTO> statisticsDTOList;
    StatisticsAdapter statisticsAdapter;

    SqliteManager sqliteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_burner_statistics);

        sqliteManager=new SqliteManager(AllBurnerStatisticsActivity.this);


        stat_report_recyclerView=(RecyclerView)findViewById(R.id.stati_report_recyclerview);
        stat_report_recyclerView.setHasFixedSize(true);
        stat_report_recyclerView.setLayoutManager(new LinearLayoutManager(AllBurnerStatisticsActivity.this));

        statisticsDTOList=new ArrayList<>();

        statisticsAdapter=new StatisticsAdapter(AllBurnerStatisticsActivity.this,statisticsDTOList);
        stat_report_recyclerView.setAdapter(statisticsAdapter);

       //addBurnerDetailsManually();



      getStatisticsReportFromSqliteDB();

    }

    private void addBurnerDetailsManually() {

        String cooking_ID="cook2";
        String date="17-01-2020";
        String time="12:10";
        String burner="1";
        String angle="90";
        String duration="10";
        String cookingStatus="2";

       boolean returnVal= sqliteManager.addStatisticsBurnerValue(cooking_ID,date,time,burner,angle,duration,cookingStatus,FontUtil.deviceID(AllBurnerStatisticsActivity.this));

       if(returnVal==true){

           Toast.makeText(AllBurnerStatisticsActivity.this,"Inserted Values",Toast.LENGTH_LONG).show();
       }else {
           Toast.makeText(AllBurnerStatisticsActivity.this,"No Value Inserted",Toast.LENGTH_LONG).show();
       }



    }

    private void getStatisticsReportFromSqliteDB() {

        String burner="1";
        statisticsDTOList=sqliteManager.getBurnerStatisticsReport(burner, FontUtil.deviceID(AllBurnerStatisticsActivity.this));
        statisticsAdapter.dataSetChanged(statisticsDTOList);
        statisticsAdapter.notifyDataSetChanged();

    }
}
