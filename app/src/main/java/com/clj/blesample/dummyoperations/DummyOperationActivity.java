package com.clj.blesample.dummyoperations;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.utils.FontUtil;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

public class DummyOperationActivity extends AppCompatActivity {

    Croller burnerTop1, burnerLeft1, burnerRight1;
    TextView knobAngleTop1, knobAngleLeft1, knobAngleRight1;

    Typeface octprisonFont;
    ImageView whistleSet1,eTimer1,emergencyStop1;
    EditText getTimerText1;

    Spinner mSpinner, mSpinnerWhistleCount, mSelectBurner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_operation);

        burnerTop1=(Croller)findViewById(R.id.burnerTop1);
        burnerLeft1=(Croller)findViewById(R.id.burnerLeft1);
        burnerRight1=(Croller)findViewById(R.id.burnerRight1);

        knobAngleTop1 = (TextView) findViewById(R.id.topBurnerKnobAngle1);
        knobAngleLeft1 = (TextView) findViewById(R.id.leftBurnerKnobAngle1);
        knobAngleRight1 = (TextView) findViewById(R.id.rightBurnerKnobAngle1);

        whistleSet1=(ImageView) findViewById(R.id.whistleSet1);
        eTimer1 = (ImageView) findViewById(R.id.eTimer1);
        emergencyStop1=(ImageView)findViewById(R.id.emergencyStop1);



        octprisonFont = FontUtil.getOctinPrisonFont(DummyOperationActivity.this);

        emergencyStop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DummyOperationActivity.this,"Stove Stopped",Toast.LENGTH_LONG).show();
            }
        });

        whistleSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(DummyOperationActivity.this,"I am Called",Toast.LENGTH_LONG).show();

                String burners[] = {"Burner", "Center", "Left", "Right"};
                String whistleCount[] = {"Whistle", "0", "1", "2", "3", "4", "5"};

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DummyOperationActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Select");

                mSpinner = (Spinner) mView.findViewById(R.id.spinnerData);
                mSpinnerWhistleCount = (Spinner) mView.findViewById(R.id.spinnerWhistleCount);


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DummyOperationActivity.this, android.R.layout.simple_spinner_item,
                        burners);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<String> arrayAdapterWhistle = new ArrayAdapter<String>(DummyOperationActivity.this, android.R.layout.simple_spinner_item,
                        whistleCount);

                arrayAdapterWhistle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                mSpinner.setAdapter(arrayAdapter);
                mSpinnerWhistleCount.setAdapter(arrayAdapterWhistle);


                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mSpinner.getSelectedItem().toString().equals("Burner") && mSpinnerWhistleCount.getSelectedItem().toString().equals("Whistle")) {

                            Toast.makeText(DummyOperationActivity.this, "Please Select Burner and Whistle Count", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(DummyOperationActivity.this, "Whistle count sent to stove", Toast.LENGTH_LONG).show();

                        }


                        }
                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();

            }
        });





        eTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String burners[] = {"Select Burner", "Center", "Left", "Right"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DummyOperationActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_set_timer, null);
                builder.setTitle("Set Timer");

                getTimerText1 = (EditText) mView.findViewById(R.id.setTimer);
                mSelectBurner = (Spinner) mView.findViewById(R.id.selectBurner);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DummyOperationActivity.this, android.R.layout.simple_spinner_item,
                        burners);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mSelectBurner.setAdapter(arrayAdapter);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int timer = 0;

                        String time = getTimerText1.getText().toString();
                        if (time.equals("")) {
                            Toast.makeText(DummyOperationActivity.this, "Please Set Timer", Toast.LENGTH_LONG).show();
                        } else {
                            timer = Integer.parseInt(time);
                        }


                        if (timer > 120) {
                            Toast.makeText(DummyOperationActivity.this, "Please Set Timer Below 120min", Toast.LENGTH_LONG).show();
                        } else if (timer == 0) {
                            Toast.makeText(DummyOperationActivity.this, "Please Set Timer", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(DummyOperationActivity.this, "Burner will turnoff in " + timer + "mins", Toast.LENGTH_LONG).show();

                            String timeVal = String.valueOf(timer);

                            //String burner = burnerFinder(mSelectBurner.getSelectedItem().toString());



                        }


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



        burnerTop1.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                knobAngleTop1.setText(""+progress);
                knobAngleTop1.setTypeface(octprisonFont);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });

        burnerLeft1.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                knobAngleLeft1.setText(""+progress);
                knobAngleLeft1.setTypeface(octprisonFont);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });

        burnerRight1.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                knobAngleRight1.setText(""+progress);
                knobAngleRight1.setTypeface(octprisonFont);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });
    }
}