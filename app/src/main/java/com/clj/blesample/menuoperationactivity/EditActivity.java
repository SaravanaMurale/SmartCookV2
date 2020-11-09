package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.operation.CharacteristicListFragment;
import com.clj.blesample.operation.OperationActivity;
import com.clj.blesample.sessionmanager.PreferencesUtil;
import com.clj.blesample.utils.FontUtil;

import java.util.ArrayList;
import java.util.logging.Level;

public class EditActivity extends AppCompatActivity {

    Button operateLeftBurner, operateCenterBurner, operateRightBurner, start, cancel;
    TextView operateTimer, operateWhistleCount, operateSub, operateAdd, sim, high, off, minute, whistle, burnerSettingsText;
    int timerInMin = 5;
    int whistleInCount = 2;


    byte[] bytes1, byte2;

    boolean timerFlag, whistleFlag;

    String burner = "";

    int flameMode = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ArrayList<byte[]> byteArrayList = new ArrayList<>();

        Intent intent = getIntent();
        burner = intent.getStringExtra("BURNER");
        byteArrayList = (ArrayList<byte[]>) intent.getSerializableExtra("currentByteArrayList");

       /* int byteSize = intent.getIntExtra("currentByte_size", 0);

        for (int i = 0; i < byteSize; i++) {
            barray.add(intent.getByteArrayExtra("currentByte" + i));
            System.out.println("TotalArraySize" + barray.get(i));
        }

        System.out.println("ArrayListSize" + barray.size());*/

        for (int i = 0; i < byteArrayList.size(); i++) {
            System.out.println("DATA" + byteArrayList.get(i));

            bytes1 = byteArrayList.get(0);
            byte2 = byteArrayList.get(1);

        }

        //setBurner();

        /*System.out.println("ByteAtZero" + bytes1[0]);
        System.out.println("ByteAtOne" + bytes1[1]);
        System.out.println("ByteAtTwo" + bytes1[2]);
        System.out.println("ByteAtThree" + bytes1[3]);

        System.out.println("ByteAtZero" + byte2[0]);
        System.out.println("ByteAtOne" + byte2[1]);
        System.out.println("ByteAtTwo" + byte2[2]);
        System.out.println("ByteAtThree" + byte2[3]);
        System.out.println("ByteAtFour" + byte2[4]);*/


        initView();
        backgroundChangeOperation();

        operateTimer.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
        operateTimer.setTextColor(getResources().getColor(R.color.red));
        operateWhistleCount.setBackground(getResources().getDrawable(R.drawable.rounded_border));

        timerFlag = true;
        whistleFlag = false;
        if (timerInMin > 0) {
            minute.setText("" + timerInMin);
            whistle.setVisibility(View.INVISIBLE);
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timerInMin > 0 && whistleInCount > 0 && flameMode > 0) {
                    System.out.println("ReceivedData" + burner + " " + timerInMin + " " + whistleInCount + " " + flameMode);

                    PreferencesUtil.setValueString(EditActivity.this, PreferencesUtil.BURNER, burner);
                    PreferencesUtil.setValueSInt(EditActivity.this, PreferencesUtil.TIMER_IN_MINUTE, timerInMin);
                    PreferencesUtil.setValueSInt(EditActivity.this, PreferencesUtil.WHISTLE_IN_COUNT, whistleInCount);
                    PreferencesUtil.setValueSInt(EditActivity.this, PreferencesUtil.FLAME_MODE, flameMode);

                    onBackPressed();

                } else {
                    Toast.makeText(EditActivity.this, "Please Select Time or Whistle and Flame Mode", Toast.LENGTH_LONG).show();
                }


                // startBurnerClickListener.onStartClick(burner, timerInMin, whistleInCount, flameMode);

                /*Bundle bundle = new Bundle();
                bundle.putString("edttext", "From Activity");

                CharacteristicListFragment fragobj = new CharacteristicListFragment();
                fragobj.setArguments(bundle);*/


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setBurner() {

        if (burner.equals("00")) {

            burnerSettingsText.setText("Left Burner Settings");

        } else if (burner.equals("01")) {

            burnerSettingsText.setText("Center Burner Settings");

        } else if (burner.equals("10")) {
            burnerSettingsText.setText("Right Burner Settings");
        }
    }


    private void backgroundChangeOperation() {

        operateTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateTimer.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateTimer.setTextColor(getResources().getColor(R.color.red));
                operateWhistleCount.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                operateWhistleCount.setTextColor(getResources().getColor(R.color.white));
                //minuteOrWhistleCount.setText("5 minute");

                timerFlag = true;
                whistleFlag = false;

                minute.setVisibility(View.VISIBLE);
                whistle.setVisibility(View.INVISIBLE);

                if (timerInMin > 0) {

                    minute.setText("" + timerInMin);
                } else {

                    minute.setText("" + timerInMin);
                }

            }
        });

        operateWhistleCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateWhistleCount.setTextColor(getResources().getColor(R.color.red));
                operateTimer.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                operateTimer.setTextColor(getResources().getColor(R.color.white));
                //minuteOrWhistleCount.setText("1");

                timerFlag = false;
                whistleFlag = true;

                minute.setVisibility(View.INVISIBLE);
                whistle.setVisibility(View.VISIBLE);

                if (whistleInCount > 0) {
                    whistle.setText("" + whistleInCount);
                } else {
                    minute.setText("" + whistleInCount);
                }

            }
        });


        operateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operateAdd.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateAdd.setTextColor(getResources().getColor(R.color.red));
                operateSub.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                operateSub.setTextColor(getResources().getColor(R.color.white));


                operateSub.setEnabled(true);

                if (timerFlag) {

                    timerInMin = timerInMin + 1;
                    minute.setText("" + timerInMin);

                } else if (whistleFlag) {
                    whistleInCount = whistleInCount + 1;
                    whistle.setText("" + whistleInCount);
                }

                //b1TimerClickListener.onB1TimerClick();
            }
        });

        operateSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operateSub.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateSub.setTextColor(getResources().getColor(R.color.red));
                operateAdd.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                operateAdd.setTextColor(getResources().getColor(R.color.white));


                if (timerFlag) {

                    if (timerInMin > 0) {
                        operateSub.setEnabled(true);
                        timerInMin = timerInMin - 1;

                        if (timerInMin > 0) {
                            minute.setText("" + timerInMin);
                        } else if (timerInMin == 0) {
                            whistle.setText("" + timerInMin);

                            if (whistleInCount == 0 && timerInMin == 0) {
                                operateSub.setEnabled(false);
                                operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                            }

                        }
                    } else if (timerInMin == 0) {
                        minute.setText("" + timerInMin);
                        if (whistleInCount == 0 && timerInMin == 0) {
                            operateSub.setEnabled(false);
                            operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                        } else {
                            operateSub.setEnabled(true);
                        }

                    }

                } else if (whistleFlag) {
                    if (whistleInCount > 0) {
                        whistleInCount = whistleInCount - 1;

                        if (whistleInCount > 0) {
                            whistle.setText("" + whistleInCount);
                        } else if (whistleInCount == 0) {
                            whistle.setText("" + whistleInCount);

                            if (timerInMin == 0 && whistleInCount == 0) {
                                operateSub.setEnabled(false);
                                operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                            }

                        }
                    } else if (whistleInCount == 0) {
                        whistle.setText("" + whistleInCount);
                        if (timerInMin == 0 && whistleInCount == 0) {
                            operateSub.setEnabled(false);
                            operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                        } else {
                            operateSub.setEnabled(true);
                        }


                    }
                }

            }
        });

        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flameMode = 1;

                sim.setTextColor(getResources().getColor(R.color.red));
                sim.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                sim.setBackgroundColor(getResources().getColor(R.color.burner_on_green));

                high.setTextColor(getResources().getColor(R.color.white));
                high.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                off.setTextColor(getResources().getColor(R.color.white));
                off.setBackground(getResources().getDrawable(R.drawable.rounded_border));


            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flameMode = 2;

                high.setTextColor(getResources().getColor(R.color.red));
                high.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                high.setBackgroundColor(getResources().getColor(R.color.burner_on_green));

                sim.setTextColor(getResources().getColor(R.color.white));
                sim.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                off.setTextColor(getResources().getColor(R.color.white));
                off.setBackground(getResources().getDrawable(R.drawable.rounded_border));


            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flameMode = 0;

                off.setTextColor(getResources().getColor(R.color.red));
                off.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                off.setBackgroundColor(getResources().getColor(R.color.burner_on_green));

                sim.setTextColor(getResources().getColor(R.color.white));
                sim.setBackground(getResources().getDrawable(R.drawable.rounded_border));
                high.setTextColor(getResources().getColor(R.color.white));
                high.setBackground(getResources().getDrawable(R.drawable.rounded_border));

            }
        });


    }

    private void initView() {
        operateLeftBurner = (Button) findViewById(R.id.leftBurnerOperation);
        operateCenterBurner = (Button) findViewById(R.id.centerBurnerOperation);
        operateRightBurner = (Button) findViewById(R.id.rightBurnerOperation);

        operateTimer = (TextView) findViewById(R.id.timerText);
        operateTimer.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        operateWhistleCount = (TextView) findViewById(R.id.whistleText);
        operateWhistleCount.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        minute = (TextView) findViewById(R.id.minute);
        whistle = (TextView) findViewById(R.id.whistle);

        operateSub = (TextView) findViewById(R.id.sub);
        operateAdd = (TextView) findViewById(R.id.add);

        sim = (TextView) findViewById(R.id.sim);
        sim.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        high = (TextView) findViewById(R.id.high);
        high.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        off = (TextView) findViewById(R.id.off);
        off.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));

        start = (Button) findViewById(R.id.start);
        cancel = (Button) findViewById(R.id.cancel);
        start.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        cancel.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));


        burnerSettingsText = (TextView) findViewById(R.id.burnerSettingsText);

        minute.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));
        whistle.setTypeface(FontUtil.getOctinPrisonFont(EditActivity.this));


    }
}