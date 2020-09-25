package com.clj.blesample.dummyoperations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clj.blesample.R;

public class DummyMainActivity extends AppCompatActivity {

    Button clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_main);

        clickMe=(Button)findViewById(R.id.clickMe);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DummyMainActivity.this,DummyOperationActivity.class);
                startActivity(intent);
            }
        });
    }
}