package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clj.blesample.R;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    Button operateLeftBurner, operateCenterBurner, operateRightBurner;
    TextView operateTimer, operateWhistleCount, operateSub, operateAdd, sim, high, off, minute, whistle;
    int timerInMin = 5;
    int whistleInCount = 2;

    B1TimerClickListener b1TimerClickListener;

    byte[] bytes1, byte2;

    boolean timerFlag, whistleFlag;


    interface B1TimerClickListener {
        public void onB1TimerClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ArrayList<byte[]> byteArrayList = new ArrayList<>();

        Intent intent = getIntent();
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
        operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.white));
        timerFlag = true;
        whistleFlag = false;
        if (timerInMin > 0) {
            minute.setText("" + timerInMin + " minute");
            whistle.setVisibility(View.INVISIBLE);
        }


    }


    private void backgroundChangeOperation() {

        operateTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateTimer.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.white));
                //minuteOrWhistleCount.setText("5 minute");

                timerFlag = true;
                whistleFlag = false;

                minute.setVisibility(View.VISIBLE);
                whistle.setVisibility(View.INVISIBLE);

                if (timerInMin > 0) {
                    minute.setText("" + timerInMin + " minute");
                } else {
                    minute.setText("" + timerInMin + " minute");
                }

            }
        });

        operateWhistleCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operateWhistleCount.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateTimer.setBackgroundColor(getResources().getColor(R.color.white));
                //minuteOrWhistleCount.setText("1");

                timerFlag = false;
                whistleFlag = true;

                minute.setVisibility(View.INVISIBLE);
                whistle.setVisibility(View.VISIBLE);

                if (whistleInCount > 0) {
                    whistle.setText("" + whistleInCount + " whistle");
                } else {
                    minute.setText("" + whistleInCount + " whistle");
                }

            }
        });


        operateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operateAdd.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateSub.setBackgroundColor(getResources().getColor(R.color.white));


                if (timerFlag) {

                    timerInMin = timerInMin + 1;
                    minute.setText("" + timerInMin + " minute");

                } else if (whistleFlag) {
                    whistleInCount = whistleInCount + 1;
                    whistle.setText("" + whistleInCount + " whistle");
                }

                //b1TimerClickListener.onB1TimerClick();
            }
        });

        operateSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operateSub.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                operateAdd.setBackgroundColor(getResources().getColor(R.color.white));

                if (timerFlag) {

                    if (timerInMin > 0) {
                        operateSub.setEnabled(true);
                        timerInMin = timerInMin - 1;

                        if (timerInMin > 0) {
                            minute.setText("" + timerInMin + " minute");
                        } else if (timerInMin == 0) {
                            whistle.setText("" + timerInMin + "whistle");
                            operateSub.setEnabled(false);
                            operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    } else if (timerInMin == 0) {
                        minute.setText("" + timerInMin + "whistle");
                        operateSub.setEnabled(false);
                        operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                    }

                } else if (whistleFlag) {
                    if (whistleInCount > 0) {
                        whistleInCount = whistleInCount - 1;

                        if (whistleInCount > 0) {
                            whistle.setText("" + whistleInCount + "whistle");
                        } else if (whistleInCount == 0) {
                            whistle.setText("" + whistleInCount + "whistle");
                            operateSub.setEnabled(false);
                            operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    } else if (whistleInCount == 0) {
                        whistle.setText("" + whistleInCount + "whistle");
                        operateSub.setEnabled(false);
                        operateSub.setBackgroundColor(getResources().getColor(R.color.white));

                    }
                }

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


    }

    private void initView() {
        operateLeftBurner = (Button) findViewById(R.id.leftBurnerOperation);
        operateCenterBurner = (Button) findViewById(R.id.centerBurnerOperation);
        operateRightBurner = (Button) findViewById(R.id.rightBurnerOperation);

        operateTimer = (TextView) findViewById(R.id.timerText);
        operateWhistleCount = (TextView) findViewById(R.id.whistleText);
        minute = (TextView) findViewById(R.id.minute);
        whistle = (TextView) findViewById(R.id.whistle);

        operateSub = (TextView) findViewById(R.id.sub);
        operateAdd = (TextView) findViewById(R.id.add);

        sim = (TextView) findViewById(R.id.sim);
        high = (TextView) findViewById(R.id.high);
        off = (TextView) findViewById(R.id.off);
    }
}