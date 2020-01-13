package com.clj.blesample.menuoperationactivity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.adapter.MServiceAdapter;
import com.clj.blesample.databasemanager.SqliteManager;
import com.clj.blesample.model.MaintenaceServiceDTO;
import com.clj.blesample.utils.FontUtil;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceServiceActivity extends AppCompatActivity {


    RecyclerView mServiceRecyclerView;
    List<MaintenaceServiceDTO> mServiceList;
    MServiceAdapter mServiceAdapter;

    SqliteManager sqliteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_service);

        mServiceRecyclerView = (RecyclerView) findViewById(R.id.maintenanceRecyclerView);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(MaintenanceServiceActivity.this));
        mServiceRecyclerView.setHasFixedSize(true);

        sqliteManager = new SqliteManager(MaintenanceServiceActivity.this);


        mServiceList = new ArrayList<>();

        mServiceAdapter = new MServiceAdapter(MaintenanceServiceActivity.this, mServiceList);

        mServiceRecyclerView.setAdapter(mServiceAdapter);

        /*insertMaintenanceServiceData();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        getAllMaintenanceServiceDataFromSqLite();

    }

    private void insertMaintenanceServiceData() {

        //addMaintenanceServiceData(String m_issueID,String issueFixedDate,String personName,String issue,String deviceID)

        sqliteManager.addMaintenanceServiceData("ISSU1","13-01-2020","Murali","Low Flame",FontUtil.deviceID(this));

    }

    private void getAllMaintenanceServiceDataFromSqLite() {


        mServiceList = sqliteManager.getAllM_ServiceData(FontUtil.deviceID(MaintenanceServiceActivity.this));

        mServiceAdapter.dataSetChanged(mServiceList);

        mServiceAdapter.notifyDataSetChanged();


    }
}
