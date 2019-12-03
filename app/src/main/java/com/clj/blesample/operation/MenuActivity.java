package com.clj.blesample.operation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.clj.blesample.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        Toast.makeText(MenuActivity.this,"Menu Activity Called",Toast.LENGTH_LONG).show();

    }
}
