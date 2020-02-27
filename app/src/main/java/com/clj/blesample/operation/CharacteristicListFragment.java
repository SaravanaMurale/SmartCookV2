package com.clj.blesample.operation;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blesample.MainActivity;
import com.clj.blesample.R;
import com.clj.blesample.sessionmanager.PreferencesUtil;
import com.clj.blesample.utils.FontUtil;
import com.clj.blesample.utils.FormatConversion;
import com.clj.blesample.utils.OnBackPressed;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CharacteristicListFragment extends Fragment {

    int tempVal = 0;


    TextView knobAngleTop, knobAngleLeft, knobAngleRight;
    Croller burnerTop, burnerLeft, burnerRight;
    ImageView topBurnerVesselImage, leftBurnerVesselImage, rightBurnerVesselImage;
    ImageView topBurnerWhistleImage, leftBurnerWhistleImage, rightBurnerWhistleImage;
    TextView topBurnerWhistleCount, leftBurnerWhistleCount, rightBurnerWhistleCount;

    ImageView menuIcon;
    ImageView whistleSet;

    ImageView leftTimerIcon, rightTimerIcon, topTimerIcon;
    TextView topTimerCount, rightTimerCount, leftTimerCount;

    int pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7;
    String pos_0, pos_1, pos_2, pos_3, pos_4, pos_5, pos_6, pos_7;
    char c0, c1, c2, c3, c4, c5, c6, c7;

    private ResultAdapter mResultAdapter;

    //MyCode
    BluetoothGattCharacteristic characteristic;
    List<Integer> propList = new ArrayList<>();
    List<String> propNameList = new ArrayList<>();

    Button notifyBtn;
    Button writeBtn;

    TextView show_data;
    EditText edit_data;
    Button send_btn;
    TextView user_entered_data;

    EditText getTimerText;


    int SIZE_OF_CHARACTERISTIC = 0;

    Spinner mSpinner, mSpinnerWhistleCount, mSelectBurner;
    ImageView eTimer;

    Typeface octinPrisonFont;
    int currentApiVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Hides status bar
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getActivity().getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

        //end of status bar
        View v = inflater.inflate(R.layout.fragment_characteric_list, null);

        getFont();

        initView(v);
        return v;
    }

    private void getFont() {

        octinPrisonFont = FontUtil.getOctinPrisonFont(getActivity());
    }


    @Override
    public void onResume() {
        super.onResume();

        String knobRotation_Angle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);
        System.out.println("OnResumeValue " + knobRotation_Angle);

        //Calls Notify
        if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

            callMe(0, null, null, 0);


        }


        //Notify
        //Have to call without user clicking
        /*notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
                    callMe(0,null);
                }

            }
        });*/
        //Write
        //Should be called after user clicked
        /*writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
                    //callMe(1);
                }

            }
        });*/


    }


    private void initView(View v) {
        mResultAdapter = new ResultAdapter(getActivity());
        ListView listView_device = (ListView) v.findViewById(R.id.list_service);

        notifyBtn = (Button) v.findViewById(R.id.notify);
        writeBtn = (Button) v.findViewById(R.id.write);

        show_data = (TextView) v.findViewById(R.id.show_received_data);
        edit_data = (EditText) v.findViewById(R.id.edit_data);
        send_btn = (Button) v.findViewById(R.id.send_btn);
        user_entered_data = (TextView) v.findViewById(R.id.user_entered_data);


        burnerTop = (Croller) v.findViewById(R.id.burnerTop);
        burnerLeft = (Croller) v.findViewById(R.id.burnerLeft);
        burnerRight = (Croller) v.findViewById(R.id.burnerRight);

        knobAngleTop = (TextView) v.findViewById(R.id.topBurnerKnobAngle);
        knobAngleLeft = (TextView) v.findViewById(R.id.leftBurnerKnobAngle);
        knobAngleRight = (TextView) v.findViewById(R.id.rightBurnerKnobAngle);

        topBurnerVesselImage = (ImageView) v.findViewById(R.id.top_Burner_Vessel);
        leftBurnerVesselImage = (ImageView) v.findViewById(R.id.leftBurner_Vessel);
        rightBurnerVesselImage = (ImageView) v.findViewById(R.id.rightBurnerVess_);

        topBurnerWhistleImage = (ImageView) v.findViewById(R.id.topBurner_whistle_img);
        leftBurnerWhistleImage = (ImageView) v.findViewById(R.id.leftBurner_Whistle);
        rightBurnerWhistleImage = (ImageView) v.findViewById(R.id.rightBurner_Whistle);

        topBurnerWhistleCount = (TextView) v.findViewById(R.id.topBurner_Whistle_Count);
        leftBurnerWhistleCount = (TextView) v.findViewById(R.id.leftBurner_Whistle_Count);
        rightBurnerWhistleCount = (TextView) v.findViewById(R.id.rightBurner_Whistle_Count);

        topTimerIcon = (ImageView) v.findViewById(R.id.topTimerIcon);
        leftTimerIcon = (ImageView) v.findViewById(R.id.LeftTimerIcon);
        rightTimerIcon = (ImageView) v.findViewById(R.id.rightBurnerTimerIcon);

        topTimerCount = (TextView) v.findViewById(R.id.topBurnerShowTimer);
        leftTimerCount = (TextView) v.findViewById(R.id.leftBurnerTimer);
        rightTimerCount = (TextView) v.findViewById(R.id.rightBurnerTimer);


        menuIcon = (ImageView) v.findViewById(R.id.menuIcon);

        whistleSet = (ImageView) v.findViewById(R.id.whistleSet);

        eTimer = (ImageView) v.findViewById(R.id.eTimer);


        eTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String burners[] = {"Select Burner", "Center", "Left", "Right"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_set_timer, null);
                builder.setTitle("Set Timer");

                getTimerText = (EditText) mView.findViewById(R.id.setTimer);
                mSelectBurner = (Spinner) mView.findViewById(R.id.selectBurner);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                        burners);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mSelectBurner.setAdapter(arrayAdapter);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int timer = 0;

                        String time = getTimerText.getText().toString();
                        if (time.equals("")) {
                            Toast.makeText(getActivity(), "Please Set Timer", Toast.LENGTH_LONG).show();
                        } else {
                            timer = Integer.parseInt(time);
                        }


                        if (timer > 120) {
                            Toast.makeText(getActivity(), "Please Set Timer Below 120min", Toast.LENGTH_LONG).show();
                        } else if (timer == 0) {
                            Toast.makeText(getActivity(), "Please Set Timer", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getActivity(), "Burner will turnoff in" + timer + "mins", Toast.LENGTH_LONG).show();

                            String timeVal = String.valueOf(timer);

                            String burner = burnerFinder(mSelectBurner.getSelectedItem().toString());

                            if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
                                callMe(1, burner, timeVal, 2);
                            }
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

        //To check write Data
        /*send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = edit_data.getText().toString();

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
                    callMe(1, data);
                }

            }
        });*/

        whistleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String burners[] = {"Burner", "Center", "Left", "Right"};
                String whistleCount[] = {"Whistle", "1", "2", "3", "4", "5"};

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Select");

                mSpinner = (Spinner) mView.findViewById(R.id.spinnerData);
                mSpinnerWhistleCount = (Spinner) mView.findViewById(R.id.spinnerWhistleCount);


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                        burners);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<String> arrayAdapterWhistle = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                        whistleCount);

                arrayAdapterWhistle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                mSpinner.setAdapter(arrayAdapter);
                mSpinnerWhistleCount.setAdapter(arrayAdapterWhistle);


                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mSpinner.getSelectedItem().toString().equals("Burner") && mSpinnerWhistleCount.getSelectedItem().toString().equals("Whistle")) {

                            Toast.makeText(getActivity(), "Please Select Burner and Whistle Count", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getActivity(), "Selected", Toast.LENGTH_LONG).show();


                            if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

                                String burner = burnerFinder(mSpinner.getSelectedItem().toString());

                                if (burner.equals("01")) {

                                    topBurnerWhistleImage.setVisibility(View.VISIBLE);
                                    topBurnerWhistleCount.setVisibility(View.VISIBLE);
                                    topBurnerWhistleCount.setText("0");

                                } else if (burner.equals("10")) {

                                    leftBurnerWhistleImage.setVisibility(View.VISIBLE);
                                    leftBurnerWhistleCount.setVisibility(View.VISIBLE);
                                    leftBurnerWhistleCount.setText("0");


                                } else if (burner.equals("11")) {

                                    rightBurnerWhistleImage.setVisibility(View.VISIBLE);
                                    rightBurnerWhistleCount.setVisibility(View.VISIBLE);
                                    rightBurnerWhistleCount.setText("0");
                                }


                                callMe(1, burner, mSpinnerWhistleCount.getSelectedItem().toString(), 1);


                            }


                        }


                        dialog.dismiss();


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


        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMenuItems();

            }
        });


        burnerTop.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                String FOURTH_BURNER = "01";

                System.out.println("ProgressValueRight " + progress);


                int pro = FormatConversion.dataToRanceConversion(progress);
                String proString = String.valueOf(pro);

                System.out.println("ReturnedProgressValue " + pro);

                knobAngleTop.setTypeface(octinPrisonFont);


                String userRotation = FormatConversion.integerToString(pro * 10);

                System.out.println("BurnerValue " + userRotation);


                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

                    String knobRotation_Angle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);

                    System.out.println("BurnerSharedPreferenceValueBefore " + knobRotation_Angle);

                    if (!knobRotation_Angle.equals(proString)) {


                        knobAngleTop.setText(String.valueOf(userRotation));


                        int writeFromBurner = 1;

                        System.out.println("SendValueRight " + userRotation);

                        callMe(1, userRotation, FOURTH_BURNER, 0);
                        PreferencesUtil.setValueString(getActivity(), PreferencesUtil.KNOB_ANGLE, proString);
                        System.out.println("BurnerSharedPreferenceValue " + knobRotation_Angle);


                    } else {

                    }


                }


                if (progress >= 0 && progress <= 75) {


                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {

                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {

                    croller.setProgressPrimaryColor(Color.parseColor("#d32f2f"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });


        burnerLeft.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                knobAngleLeft.setTypeface(octinPrisonFont);


                String LEFT_BURNER = "10";

                int pro = FormatConversion.dataToRanceConversion(progress);
                String proString = String.valueOf(pro);

                String userRotation = FormatConversion.integerToString(pro * 10);

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {


                    String knobRotation_Angle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE);

                    if (!knobRotation_Angle.equals(proString)) {

                        knobAngleLeft.setText(String.valueOf(userRotation));
                        int writeFromBurner = 1;
                        callMe(1, userRotation, LEFT_BURNER, 0);
                        PreferencesUtil.setValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE, proString);

                    } else {

                    }

                }


                if (progress >= 0 && progress <= 75) {


                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {

                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {

                    croller.setProgressPrimaryColor(Color.parseColor("#d32f2f"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });

        burnerRight.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                if (progress >= 0) {
                    return;
                }

                knobAngleRight.setTypeface(octinPrisonFont);

                String RIGHT_BURNER = "11";

                int pro = FormatConversion.dataToRanceConversion(progress);
                String proString = String.valueOf(pro);

                String userRotation = FormatConversion.integerToString(pro * 10);

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

                    String knobRotation_Angle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE);

                    if (!knobRotation_Angle.equals(proString)) {

                        knobAngleRight.setText(String.valueOf(userRotation));
                        int writeFromBurner = 1;
                        callMe(1, userRotation, RIGHT_BURNER, 0);
                        PreferencesUtil.setValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE, proString);

                    } else {


                    }

                }


                if (progress >= 0 && progress <= 75) {


                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {

                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {

                    croller.setProgressPrimaryColor(Color.parseColor("#d32f2f"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });


        listView_device.setAdapter(mResultAdapter);
        listView_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*final BluetoothGattCharacteristic characteristic = mResultAdapter.getItem(position);
                final List<Integer> propList = new ArrayList<>();
                List<String> propNameList = new ArrayList<>();
                int charaProp = characteristic.getProperties();
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    propList.add(CharacteristicOperationFragment.PROPERTY_READ);
                    propNameList.add("Read");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                    propList.add(CharacteristicOperationFragment.PROPERTY_WRITE);
                    propNameList.add("Write");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                    propList.add(CharacteristicOperationFragment.PROPERTY_WRITE_NO_RESPONSE);
                    propNameList.add("Write No Response");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    propList.add(CharacteristicOperationFragment.PROPERTY_NOTIFY);
                    propNameList.add("Notify");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
                    propList.add(CharacteristicOperationFragment.PROPERTY_INDICATE);
                    propNameList.add("Indicate");
                }

                if (propList.size() > 1) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getActivity().getString(R.string.select_operation_type))
                            .setItems(propNameList.toArray(new String[propNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((OperationActivity) getActivity()).setCharacteristic(characteristic);
                                    ((OperationActivity) getActivity()).setCharaProp(propList.get(which));
                                    ((OperationActivity) getActivity()).changePage(2);
                                }
                            })
                            .show();
                } else if (propList.size() > 0) {
                    ((OperationActivity) getActivity()).setCharacteristic(characteristic);
                    ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
                    ((OperationActivity) getActivity()).changePage(2);
                }*/

                //callMe(position);


            }
        });
    }

    private String burnerFinder(String toString) {
        String burner = "";

        if (toString.equals("Right")) {
            burner = "01";
        } else if (toString.equals("Left")) {
            burner = "10";
        } else if (toString.equals("Center")) {
            burner = "11";
        }

        return burner;

    }


    private void callMe(int position, String userData, String BURNER, int secondFrameStatus) {

        //Position 0 -->Notify
        //Position 1 -->Write

        final BluetoothGattCharacteristic characteristic = mResultAdapter.getItem(position);
        final List<Integer> propList = new ArrayList<>();
        List<String> propNameList = new ArrayList<>();
        int charaProp = characteristic.getProperties();
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
            propList.add(CharacteristicOperationFragment.PROPERTY_WRITE);
            propNameList.add("Write");
        }
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            propList.add(CharacteristicOperationFragment.PROPERTY_NOTIFY);
            propNameList.add("Notify");
        }

       /* if (propList.size() > 0 ) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            ((OperationActivity) getActivity()).changePage(2);

        }*/

        if (propList.size() > 0 && position == 0) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            //((OperationActivity) getActivity()).changePage(2);

            //Notify
            gettingStoveData();


        }
        if (propList.size() > 0 && position == 1 && secondFrameStatus == 0) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            //((OperationActivity) getActivity()).changePage(2);
            wrietUserData(userData, BURNER, 0);
        }

        //Whistle Count Set
        if (propList.size() > 0 && position == 1 && secondFrameStatus == 1) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            //((OperationActivity) getActivity()).changePage(2);
            wrietUserData(userData, BURNER, secondFrameStatus);
        }

        //Timer Count Set
        if (propList.size() > 0 && position == 1 && secondFrameStatus == 2) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            //((OperationActivity) getActivity()).changePage(2);
            wrietUserData(userData, BURNER, secondFrameStatus);
        }


    }

    private void gettingStoveData() {

        BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
        final BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();

        BleManager.getInstance().notify(
                bleDevice,
                characteristic.getService().getUuid().toString(),
                characteristic.getUuid().toString(),
                new BleNotifyCallback() {

                    @Override
                    public void onNotifySuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //addText(txt, "notify success");
                            }
                        });
                    }

                    @Override
                    public void onNotifyFailure(final BleException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //addText(txt, exception.toString());
                                System.out.println("IamException" + exception);
                            }
                        });
                    }

                    @Override
                    public void onCharacteristicChanged(final byte[] data) {


                        // System.out.println("Iamnotifydata" + data);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                splitEachBurnerDataFromReceivedByte(data);


                            }
                        });
                    }
                });

    }

    private void splitEachBurnerDataFromReceivedByte(byte[] data) {

        if (data.length == 6) {


            byte[] topBurReceivedVal = new byte[1];
            byte[] leftBurReceivedVal = new byte[1];
            byte[] rightBurReceivedVal = new byte[1];
            int vessel1 = 0;
            int burner1 = 0;
            int angle1 = 0;
            int leftVessel = 0, leftBurner = 0, leftAngle = 0;
            int rightVessel = 0, rightBurner = 0, rightAngle = 0;
            boolean proceed = false;


            for (int i = 0; i < data.length; i++) {

                if (i == 0) {

                    int k = data[i];

                    if (k == 42) {
                        System.out.println("*Data" + k);
                        proceed = true;
                    } else {

                        proceed = false;
                    }


                }


                if (i == 2 && proceed) {

                    topBurReceivedVal[0] = data[i];

                    vessel1 = (topBurReceivedVal[0] & 0x80) >> 7;

                    angle1 = (topBurReceivedVal[0] & 0x7C) >> 2;

                    burner1 = (topBurReceivedVal[0] & 0x03);

                    String topBurnerAngleInString = intToString(angle1);
                    String topBurnerVessel = intToString(vessel1);
                    String burner_Number = intToString(burner1);


                    doRotateTopBurner(topBurnerAngleInString, topBurnerVessel, burner_Number);


                }
                if (i == 3 && proceed) {
                    leftBurReceivedVal[i - i] = data[i];

                    leftVessel = (leftBurReceivedVal[0] & 0x80) >> 7;

                    leftAngle = (leftBurReceivedVal[0] & 0x7C) >> 2;

                    leftBurner = (leftBurReceivedVal[0] & 0x03);

                    String leftBurnerAngleInString = intToString(leftAngle);
                    String leftBurnerVessel = intToString(leftVessel);
                    String leftburner_Number = intToString(leftBurner);

                    doRatateLeftBurner(leftBurnerAngleInString, leftBurnerVessel, leftburner_Number);


                    //System.out.println("SecondBurner " + data[i]);
                }
                if (i == 4 && proceed) {
                    rightBurReceivedVal[i - i] = data[i];

                    rightVessel = (rightBurReceivedVal[0] & 0x80) >> 7;

                    rightAngle = (rightBurReceivedVal[0] & 0x7C) >> 2;

                    rightBurner = (rightBurReceivedVal[0] & 0x03);

                    String rightBurnerAngleInString = intToString(rightAngle);
                    String rightBurnerVessel = intToString(rightVessel);
                    String rightBurner_number = intToString(rightBurner);

                    doRotateRightBurner(rightBurnerAngleInString, rightBurnerVessel, rightBurner_number);


                }

            }

            PreferencesUtil.setValueString(getActivity(), PreferencesUtil.RECEIVED_STATUS, "true");
        } else if (data.length == 9) {

            boolean whistleTimerFlag = false;

            for (int i = 0; i < data.length; i++) {

                System.out.println("SecondFrameData " + data[i]);

                if (i == 0) {

                    int k = data[i];

                    if (k == 42) {
                        System.out.println("*Data" + k);
                        whistleTimerFlag = true;
                    } else {

                        whistleTimerFlag = false;
                    }

                }
                if (i == 1 && whistleTimerFlag) {

                }
                if (i == 2 && whistleTimerFlag) {
                    int topBurnerWhistle = data[i];

                    if (topBurnerWhistle > 0) {

                        topBurnerWhistleImage.setVisibility(View.VISIBLE);
                        topBurnerWhistleCount.setVisibility(View.VISIBLE);
                        topBurnerWhistleCount.setText("" + topBurnerWhistle);
                    }else if(topBurnerWhistle==0){
                        topBurnerWhistleImage.setVisibility(View.INVISIBLE);
                        topBurnerWhistleCount.setVisibility(View.INVISIBLE);
                        topBurnerWhistleCount.setText("");
                    }
                }
                if (i == 3 && whistleTimerFlag) {

                    int topBurnerTimer = data[i];

                    if (topBurnerTimer > 0) {

                        int minutes = (int) TimeUnit.SECONDS.toMinutes(topBurnerTimer);

                        topTimerIcon.setVisibility(View.VISIBLE);
                        topTimerCount.setText("" + minutes);


                    } else {
                        topTimerIcon.setVisibility(View.INVISIBLE);
                        topTimerCount.setVisibility(View.INVISIBLE);
                    }
                }
                if (i == 4 && whistleTimerFlag) {
                    int leftBurnerWhistle = data[i];

                    if (leftBurnerWhistle > 0) {

                        leftBurnerWhistleImage.setVisibility(View.VISIBLE);
                        leftBurnerWhistleCount.setVisibility(View.VISIBLE);
                        leftBurnerWhistleCount.setText("" + leftBurnerWhistle);
                    }else if(leftBurnerWhistle==0){
                        leftBurnerWhistleImage.setVisibility(View.INVISIBLE);
                        leftBurnerWhistleCount.setVisibility(View.INVISIBLE);
                        leftBurnerWhistleCount.setText("");
                    }

                }
                if (i == 5 && whistleTimerFlag) {
                    int leftBurnerTimer = data[i];

                    if (leftBurnerTimer > 0) {
                        int minutes = (int) TimeUnit.SECONDS.toMinutes(leftBurnerTimer);

                        leftTimerIcon.setVisibility(View.VISIBLE);
                        leftTimerCount.setText("" + minutes);


                    } else {

                        leftTimerIcon.setVisibility(View.INVISIBLE);
                        leftTimerCount.setVisibility(View.INVISIBLE);
                    }

                }
                if (i == 6 && whistleTimerFlag) {
                    int rightBurnerWhistle = data[i];

                    if (rightBurnerWhistle > 0) {

                        rightBurnerWhistleImage.setVisibility(View.VISIBLE);
                        rightBurnerWhistleCount.setVisibility(View.VISIBLE);
                        rightBurnerWhistleCount.setText("" + rightBurnerWhistle);
                    }else if(rightBurnerWhistle==0){
                        rightBurnerWhistleImage.setVisibility(View.INVISIBLE);
                        rightBurnerWhistleCount.setVisibility(View.INVISIBLE);
                        rightBurnerWhistleCount.setText("");
                    }
                }
                if (i == 7 && whistleTimerFlag) {
                    int rightBurnerTimer = data[i];

                    if (rightBurnerTimer > 0) {
                        int minutes = (int) TimeUnit.SECONDS.toMinutes(rightBurnerTimer);

                        rightTimerIcon.setVisibility(View.VISIBLE);
                        rightTimerCount.setVisibility(View.VISIBLE);
                        rightTimerCount.setText("" + minutes);
                    } else {
                        rightTimerIcon.setVisibility(View.INVISIBLE);
                        rightTimerCount.setVisibility(View.INVISIBLE);
                    }
                }
                if (i == 8 && whistleTimerFlag) {

                }

            }

            //Whistle and timer goes here

        }

    }


    private void doRotateTopBurner(String topBurnerAngleInString, String topBurnerVessel, String burner_Number) {


        String preferenceAngle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);

        System.out.println("VesselDetection " + vesselDetection(topBurnerVessel));

        if (vesselDetection(topBurnerVessel)) {

            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.TOP_BURNER_VESSEL, 1);
            topBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel_orange));
            topBurnerVesselImage.setVisibility(View.VISIBLE);

        } else {
            topBurnerVesselImage.setVisibility(View.INVISIBLE);
            // topBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel));
            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.TOP_BURNER_VESSEL, 0);
        }

        System.out.println("BleReceivedValue " + topBurnerAngleInString);
        System.out.println("SharedPreBurnerNumber" + preferenceAngle);


        if (!preferenceAngle.equals(topBurnerAngleInString)) {

            int decimal = Integer.parseInt(topBurnerAngleInString);

            int burnerValue = decimal * 10;
            PreferencesUtil.setValueString(getActivity(), PreferencesUtil.KNOB_ANGLE, topBurnerAngleInString);

            burnerTop.setProgress(burnerValue);

            knobAngleLeft.setText("" + burnerValue);

            /*if (burnerValue == 19) {
                knobAngleTop.setText("" + 0);
            } else {
                knobAngleTop.setText("" + burnerValue);
            }*/


        } else if (preferenceAngle.equals(topBurnerAngleInString)) {

            //If value is 1 means data wont write
            // PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,1);


        }


    }


    private void doRatateLeftBurner(String leftBurnerAngleInString, String leftBurnerVessel, String leftburner_number) {

        String preferenceAngle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE);

        if (vesselDetection(leftBurnerVessel)) {

            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.LEFT_BURNER_VESSEL, 1);
            leftBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel_orange));
            leftBurnerVesselImage.setVisibility(View.VISIBLE);
        } else {
            leftBurnerVesselImage.setVisibility(View.INVISIBLE);
            //leftBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel));
            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.LEFT_BURNER_VESSEL, 0);
        }

        if (!preferenceAngle.equals(leftBurnerAngleInString)) {

            int decimal = Integer.parseInt(leftBurnerAngleInString);
            int burnerValue = decimal * 10;
            PreferencesUtil.setValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE, leftBurnerAngleInString);

            burnerLeft.setProgress(burnerValue);

            knobAngleLeft.setText("" + burnerValue);

        } else if (preferenceAngle.equals(leftBurnerAngleInString)) {


        }

    }


    private void doRotateRightBurner(String rightBurnerAngleInString, String rightBurnerVessel, String rightBurner_number) {

        String preferenceAngle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE);

        if (vesselDetection(rightBurnerVessel)) {

            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.RIGHT_BURNER_VESSEL, 1);
            rightBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel_orange));
            rightBurnerVesselImage.setVisibility(View.VISIBLE);
        } else {
            rightBurnerVesselImage.setVisibility(View.INVISIBLE);
            //rightBurnerVesselImage.setImageDrawable(getResources().getDrawable(R.drawable.vessel));
            PreferencesUtil.setValueSInt(getActivity(), PreferencesUtil.RIGHT_BURNER_VESSEL, 0);
        }


        if (!preferenceAngle.equals(rightBurnerAngleInString)) {

            int decimal = Integer.parseInt(rightBurnerAngleInString);
            int burnerValue = decimal * 10;
            PreferencesUtil.setValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE, rightBurnerAngleInString);

            burnerRight.setProgress(burnerValue);

            knobAngleRight.setText("" + burnerValue);

        } else if (preferenceAngle.equals(rightBurnerAngleInString)) {

            //If value is 1 means data wont write
            // PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,1);


        }

    }


    private void wrietUserData(String hex, String bur_ner, int secondFrameStatus) {

        //int status=PreferencesUtil.getValueInt(getActivity(),PreferencesUtil.ZERO_WRITE_STATUS);

        if (hex.equals("0") && tempVal == 0) {
            //Toast.makeText(getActivity(),"Data wont write",Toast.LENGTH_LONG).show();
            //PreferencesUtil.setValueSInt(getActivity(),PreferencesUtil.ZERO_WRITE_STATUS,1);
        } else {

            if (secondFrameStatus == 1) {

                int topBurnerWhistle = 0, leftBurnerWhistle = 0, rightBurnerWhistle = 0;
                int topBurnerTimer = 0, leftBurnerTimer = 0, rightBurnerTimer = 0;

                byte[] secondFrame = new byte[9];
                //hex burner
                //bur_ner whistle count

                if (hex.equals("01")) {

                    topBurnerWhistle = FormatConversion.stringToInt(bur_ner);

                } else if (hex.equals("10")) {

                    leftBurnerWhistle = FormatConversion.stringToInt(bur_ner);

                } else if (hex.equals("11")) {

                    rightBurnerWhistle = FormatConversion.stringToInt(bur_ner);

                }


                secondFrame[0] = (byte) ('*');
                secondFrame[1] = (byte) (0xC0);
                secondFrame[2] = (byte) (topBurnerWhistle);
                secondFrame[3] = (byte) (topBurnerTimer);
                secondFrame[4] = (byte) (leftBurnerWhistle);
                secondFrame[5] = (byte) (leftBurnerTimer);
                secondFrame[6] = (byte) (rightBurnerWhistle);
                secondFrame[7] = (byte) (rightBurnerTimer);
                secondFrame[8] = (byte) ('#');


                Toast.makeText(getActivity(), "New Data Write", Toast.LENGTH_LONG).show();

                BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
                BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();


                BleManager.getInstance().write(
                        bleDevice,
                        characteristic.getService().getUuid().toString(),
                        characteristic.getUuid().toString(),
                        secondFrame,
                        new BleWriteCallback() {

                            //Converting byte to String and displaying to user
                            @Override
                            public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }

                            @Override
                            public void onWriteFailure(final BleException exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Exception" + exception.toString());
                                    }
                                });
                            }
                        });


            } else if (secondFrameStatus == 2) {

                int topBurnerTimer = 0, leftBurnerTimer = 0, rightBurnerTimer = 0;
                int topBurnerWhistle = 0, leftBurnerWhistle = 0, rightBurnerWhistle = 0;

                byte[] secondFrameWithTimer = new byte[9];


                if (hex.equals("01")) {

                    topBurnerTimer = FormatConversion.stringToInt(bur_ner);

                } else if (hex.equals("10")) {

                    leftBurnerTimer = FormatConversion.stringToInt(bur_ner);

                } else if (hex.equals("11")) {

                    rightBurnerTimer = FormatConversion.stringToInt(bur_ner);

                }

                secondFrameWithTimer[0] = (byte) ('*');
                secondFrameWithTimer[1] = (byte) (0xC0);
                secondFrameWithTimer[2] = (byte) (topBurnerWhistle);
                secondFrameWithTimer[3] = (byte) (topBurnerTimer);
                secondFrameWithTimer[4] = (byte) (leftBurnerWhistle);
                secondFrameWithTimer[5] = (byte) (leftBurnerTimer);
                secondFrameWithTimer[6] = (byte) (rightBurnerWhistle);
                secondFrameWithTimer[7] = (byte) (rightBurnerTimer);
                secondFrameWithTimer[8] = (byte) ('#');

                Toast.makeText(getActivity(), "Timer Data Sent", Toast.LENGTH_LONG).show();

                BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
                BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();


                BleManager.getInstance().write(
                        bleDevice,
                        characteristic.getService().getUuid().toString(),
                        characteristic.getUuid().toString(),
                        secondFrameWithTimer,
                        new BleWriteCallback() {

                            //Converting byte to String and displaying to user
                            @Override
                            public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }

                            @Override
                            public void onWriteFailure(final BleException exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Exception" + exception.toString());
                                    }
                                });
                            }
                        });

            } else {


                tempVal = 1;
                //Toast.makeText(getActivity(),"Data Writing",Toast.LENGTH_LONG).show();
                String recevied_status = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.RECEIVED_STATUS);
                System.out.println("ConnectedStatus " + recevied_status);
                if (recevied_status.equals("true")) {


                    int topBurnereAngle = 0;
                    int leftBurnerAngle = 0;
                    int rightBurnerAngle = 0;

                    if (bur_ner.equals("01")) {

                        topBurnereAngle = Integer.parseInt(hex) / 10;
                        String leftBurnerAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE);
                        leftBurnerAngle = Integer.parseInt(leftBurnerAngleString);
                        String rightBurnerAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE);
                        rightBurnerAngle = Integer.parseInt(rightBurnerAngleString);


                    } else if (bur_ner.equals("10")) {
                        leftBurnerAngle = Integer.parseInt(hex) / 10;
                        String topBurnereAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);
                        topBurnereAngle = Integer.parseInt(topBurnereAngleString);
                        String rightBurnerAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.RIGHT_KNOB_ANGLE);
                        rightBurnerAngle = Integer.parseInt(rightBurnerAngleString);


                    } else if (bur_ner.equals("11")) {
                        rightBurnerAngle = Integer.parseInt(hex) / 10;
                        String topBurnereAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);
                        topBurnereAngle = Integer.parseInt(topBurnereAngleString);
                        String leftBurnerAngleString = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.LEFT_KNOB_ANGLE);
                        leftBurnerAngle = Integer.parseInt(leftBurnerAngleString);
                    }


                    int angel_top = topBurnereAngle;
                    int angel_left = leftBurnerAngle;
                    int angel_right = rightBurnerAngle;


                    int vessel = PreferencesUtil.getValueInt(getActivity(), PreferencesUtil.TOP_BURNER_VESSEL);
                    int vesselLeft = PreferencesUtil.getValueInt(getActivity(), PreferencesUtil.LEFT_BURNER_VESSEL);
                    int vesselRight = PreferencesUtil.getValueInt(getActivity(), PreferencesUtil.RIGHT_BURNER_VESSEL);
                    int sendbyte = 0, secondbyte = 0, thirdbyte = 0;


                    sendbyte = ((angel_top << 2) | (0x01) | (vessel << 7));
                    secondbyte = ((angel_left << 2) | (0x02) | (vesselLeft << 7));
                    thirdbyte = ((angel_right << 2) | (0x03) | (vesselRight << 7));


                    byte[] ret = new byte[6];
                    ret[0] = (byte) ('*');
                    ret[1] = (byte) (0XD0);
                    ret[2] = (byte) (sendbyte);
                    ret[3] = (byte) (secondbyte);
                    ret[4] = (byte) (thirdbyte);
                    ret[5] = (byte) ('#');


                    BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
                    BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();


                    System.out.println("BleSentValue" + angel_top);


                    BleManager.getInstance().write(
                            bleDevice,
                            characteristic.getService().getUuid().toString(),
                            characteristic.getUuid().toString(),
                            ret,
                            new BleWriteCallback() {

                                //Converting byte to String and displaying to user
                                @Override
                                public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                        }
                                    });
                                }

                                @Override
                                public void onWriteFailure(final BleException exception) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("Exception" + exception.toString());
                                        }
                                    });
                                }
                            });


                } else {
                    System.out.println("Not recevied data yet");
                }

            }

        }
    }


    private boolean vesselDetection(String topBurnerVessel) {

        if (topBurnerVessel.equals("1")) {
            return true;
        }

        return false;

    }


    private void runOnUiThread(Runnable runnable) {
        if (isAdded() && getActivity() != null)
            getActivity().runOnUiThread(runnable);
    }


    public void showData() {
        BluetoothGattService service = ((OperationActivity) getActivity()).getBluetoothGattService();
        System.out.println("CharacteristicListFragment" + service.getCharacteristics());
        mResultAdapter.clear();
        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
            mResultAdapter.addResult(characteristic);
        }
        mResultAdapter.notifyDataSetChanged();
    }

    private class ResultAdapter extends BaseAdapter {

        private Context context;
        private List<BluetoothGattCharacteristic> characteristicList;

        ResultAdapter(Context context) {
            this.context = context;
            characteristicList = new ArrayList<>();
        }

        void addResult(BluetoothGattCharacteristic characteristic) {
            characteristicList.add(characteristic);
        }

        void clear() {
            characteristicList.clear();
        }

        @Override
        public int getCount() {
            SIZE_OF_CHARACTERISTIC = characteristicList.size();
            return characteristicList.size();
        }

        @Override
        public BluetoothGattCharacteristic getItem(int position) {
            if (position > characteristicList.size())
                return null;
            return characteristicList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(context, R.layout.adapter_service, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
                holder.txt_uuid = (TextView) convertView.findViewById(R.id.txt_uuid);
                holder.txt_type = (TextView) convertView.findViewById(R.id.txt_type);
                holder.img_next = (ImageView) convertView.findViewById(R.id.img_next);
            }

            BluetoothGattCharacteristic characteristic = characteristicList.get(position);
            String uuid = characteristic.getUuid().toString();

            holder.txt_title.setText(String.valueOf(getActivity().getString(R.string.characteristic) + "（" + position + ")"));
            holder.txt_uuid.setText(uuid);

            StringBuilder property = new StringBuilder();
            int charaProp = characteristic.getProperties();
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                property.append("Read");
                property.append(" , ");
            }
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                property.append("Write");
                property.append(" , ");
            }
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                property.append("Write No Response");
                property.append(" , ");
            }
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                property.append("Notify");
                property.append(" , ");
            }
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
                property.append("Indicate");
                property.append(" , ");
            }
            if (property.length() > 1) {
                property.delete(property.length() - 2, property.length() - 1);
            }
            if (property.length() > 0) {
                holder.txt_type.setText(String.valueOf(getActivity().getString(R.string.characteristic) + "( " + property.toString() + ")"));
                holder.img_next.setVisibility(View.VISIBLE);
            } else {
                holder.img_next.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        class ViewHolder {
            TextView txt_title;
            TextView txt_uuid;
            TextView txt_type;
            ImageView img_next;
        }


    }

    private String intToString(int data) {
        String val = String.valueOf(data);

        return val;

    }


    private void callMenuItems() {

        Intent menuIntent = new Intent(this.getActivity(), MenuActivity.class);

        startActivity(menuIntent);

    }


}
