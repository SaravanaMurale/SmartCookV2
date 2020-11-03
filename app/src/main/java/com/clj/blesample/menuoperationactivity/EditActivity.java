package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clj.blesample.R;
import com.clj.blesample.operation.CharacteristicListFragment;
import com.clj.blesample.operation.OperationActivity;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    Button operateLeftBurner, operateCenterBurner, operateRightBurner, start, cancel;
    TextView operateTimer, operateWhistleCount, operateSub, operateAdd, sim, high, off, minute, whistle;
    int timerInMin = 5;
    int whistleInCount = 2;

    StartBurnerClickListener startBurnerClickListener;

    byte[] bytes1, byte2;

    boolean timerFlag, whistleFlag;

    String burner = "";

    String flameMode = "";

    public interface StartBurnerClickListener {
        public void onStartClick(String burner, int timerInMinute, int whistleInCount, String flameMode);
    }

    public void EditActivityMethod(CharacteristicListFragment context) {
        startBurnerClickListener = (StartBurnerClickListener) context;
    }

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("ReceivedData" + burner + " " + timerInMin + " " + whistleInCount + " " + flameMode);

               // startBurnerClickListener.onStartClick(burner, timerInMin, whistleInCount, flameMode);

                /*Bundle bundle = new Bundle();
                bundle.putString("edttext", "From Activity");

                CharacteristicListFragment fragobj = new CharacteristicListFragment();
                fragobj.setArguments(bundle);*/

                onBackPressed();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

                operateSub.setEnabled(true);

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

                            if (whistleInCount == 0 && timerInMin == 0) {
                                operateSub.setEnabled(false);
                                operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                            }

                        }
                    } else if (timerInMin == 0) {
                        minute.setText("" + timerInMin + "whistle");
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
                            whistle.setText("" + whistleInCount + "whistle");
                        } else if (whistleInCount == 0) {
                            whistle.setText("" + whistleInCount + "whistle");

                            if (timerInMin == 0 && whistleInCount == 0) {
                                operateSub.setEnabled(false);
                                operateSub.setBackgroundColor(getResources().getColor(R.color.white));
                            }

                        }
                    } else if (whistleInCount == 0) {
                        whistle.setText("" + whistleInCount + "whistle");
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

                flameMode = "sim";

                sim.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                high.setBackgroundColor(getResources().getColor(R.color.white));
                off.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flameMode = "sim";

                high.setBackgroundColor(getResources().getColor(R.color.burner_on_green));
                sim.setBackgroundColor(getResources().getColor(R.color.white));
                off.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flameMode = "off";

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

        start = (Button) findViewById(R.id.start);
        cancel = (Button) findViewById(R.id.cancel);


    }
}