package com.clj.blesample;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.text.DecimalFormat;

public class DummyActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    float value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        DummyFragment dummyFragment=new DummyFragment();

        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction().add(R.id.fragmentContainer,dummyFragment,null);
        fragmentTransaction.commit();


    }
}
