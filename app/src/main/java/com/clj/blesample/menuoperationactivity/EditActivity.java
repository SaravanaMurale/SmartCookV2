package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clj.blesample.R;

public class EditActivity extends AppCompatActivity {

    Button operateLeftBurner, operateCenterBurner, operateRightBurner;
    TextView operateTimer, operateWhistleCount, operateSub, operateAdd, sim, high, off, minuteOrWhistleCount;
    int timer = 0;
    int whistleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();
        backgroundChangeOperation();


    }


    private void backgroundChangeOperation() {

        operateTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateTimer.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.white));
                minuteOrWhistleCount.setText("5 minute");
            }
        });

        operateWhistleCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateTimer.setBackgroundColor(getResources().getColor(R.color.white));
                minuteOrWhistleCount.setText("1");
            }
        });

        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sim.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                high.setBackgroundColor(getResources().getColor(R.color.white));
                off.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                high.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                sim.setBackgroundColor(getResources().getColor(R.color.white));
                off.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                off.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                sim.setBackgroundColor(getResources().getColor(R.color.white));
                high.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        operateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operateAdd.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateSub.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        operateSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operateSub.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateAdd.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });


    }

    private void initView() {
        operateLeftBurner = (Button) findViewById(R.id.leftBurnerOperation);
        operateCenterBurner = (Button) findViewById(R.id.centerBurnerOperation);
        operateRightBurner = (Button) findViewById(R.id.rightBurnerOperation);

        operateTimer = (TextView) findViewById(R.id.timerText);
        operateWhistleCount = (TextView) findViewById(R.id.whistleText);
        minuteOrWhistleCount = (TextView) findViewById(R.id.minuteOrWhistleCount);

        operateSub = (TextView) findViewById(R.id.sub);
        operateAdd = (TextView) findViewById(R.id.add);

        sim = (TextView) findViewById(R.id.sim);
        high = (TextView) findViewById(R.id.high);
        off = (TextView) findViewById(R.id.off);
    }
}