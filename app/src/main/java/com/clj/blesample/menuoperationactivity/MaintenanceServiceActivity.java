package com.clj.blesample.menuoperationactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.adapter.MServiceAdapter;
import com.clj.blesample.model.MaintenaceServiceDTO;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceServiceActivity extends AppCompatActivity {

    RecyclerView mServiceRecyclerView;
    List<MaintenaceServiceDTO> mServiceList;
    MServiceAdapter mServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_service);

        mServiceRecyclerView=(RecyclerView)findViewById(R.id.maintenanceRecyclerView);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(MaintenanceServiceActivity.this));
        mServiceRecyclerView.setHasFixedSize(true);
        
        
        mServiceList=new ArrayList<>();

        mServiceAdapter=new MServiceAdapter(MaintenanceServiceActivity.this,mServiceList);
        
        mServiceRecyclerView.setAdapter(mServiceAdapter);

        
        getMaintenanceServiceDataFromSqLite();

    }

    private void getMaintenanceServiceDataFromSqLite() {






    }
}
