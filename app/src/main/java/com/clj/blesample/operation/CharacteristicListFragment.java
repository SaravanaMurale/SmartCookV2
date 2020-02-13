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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CharacteristicListFragment extends Fragment {


    TextView knobAngleTop, knobAngleLeft, knobAngleRight;
    Croller burnerTop, burnerLeft, burnerRight;
    ImageView topBurnerVesselImage, leftBurnerVesselImage, rightBurnerVesselImage;
    ImageView topBurnerWhistleImage, leftBurnerWhistleImage, rightBurnerWhistleImage;
    TextView topBurnerWhistleCount, leftBurnerWhistleCount, rightBurnerWhistleCount;

    ImageView menuIcon;

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


    int SIZE_OF_CHARACTERISTIC = 0;

    Typeface octinPrisonFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            callMe(0, null, null);


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

        menuIcon = (ImageView) v.findViewById(R.id.menuIcon);


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

                System.out.println("ProgressValue " + progress);


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


                        System.out.println("SendValue " + userRotation);
                        callMe(1, userRotation, FOURTH_BURNER);
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
                        callMe(1, userRotation, LEFT_BURNER);
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

                if(progress>=0){
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
                        callMe(1, userRotation, RIGHT_BURNER);
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


    private void callMe(int position, String userData, String BURNER) {

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
        if (propList.size() > 0 && position == 1) {
            ((OperationActivity) getActivity()).setCharacteristic(characteristic);
            ((OperationActivity) getActivity()).setCharaProp(propList.get(0));
            //((OperationActivity) getActivity()).changePage(2);
            wrietUserData(userData, BURNER);
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

            if (i == 1 && proceed) {

                topBurReceivedVal[0] = data[i];

                vessel1 = (topBurReceivedVal[0] & 0x80) >> 7;

                angle1 = (topBurReceivedVal[0] & 0x7C) >> 2;

                burner1 = (topBurReceivedVal[0] & 0x03);

                String topBurnerAngleInString = intToString(angle1);
                String topBurnerVessel = intToString(vessel1);
                String burner_Number = intToString(burner1);


                doRotateTopBurner(topBurnerAngleInString, topBurnerVessel, burner_Number);


            }
            if (i == 2 && proceed) {
                leftBurReceivedVal[i - 2] = data[i];

                leftVessel = (leftBurReceivedVal[0] & 0x80) >> 7;

                leftAngle = (leftBurReceivedVal[0] & 0x7C) >> 2;

                leftBurner = (leftBurReceivedVal[0] & 0x03);

                String leftBurnerAngleInString = intToString(leftAngle);
                String leftBurnerVessel = intToString(leftVessel);
                String leftburner_Number = intToString(leftBurner);

                doRatateLeftBurner(leftBurnerAngleInString, leftBurnerVessel, leftburner_Number);


                //System.out.println("SecondBurner " + data[i]);
            }
            if (i == 3 && proceed) {
                rightBurReceivedVal[i - 3] = data[i];

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

        System.out.println("ReceivedValue " + topBurnerAngleInString);
        System.out.println("SharedPreBurnerNumber" + preferenceAngle);


        if (!preferenceAngle.equals(topBurnerAngleInString)) {

            int decimal = Integer.parseInt(topBurnerAngleInString);

            int burnerValue = decimal * 10;
            PreferencesUtil.setValueString(getActivity(), PreferencesUtil.KNOB_ANGLE, topBurnerAngleInString);

            burnerTop.setProgress(burnerValue);

            knobAngleTop.setText("" + burnerValue);


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


    private void wrietUserData(String hex, String bur_ner) {

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


            byte[] ret = new byte[5];
            ret[0] = (byte) ('*');
            ret[1] = (byte) (sendbyte);
            ret[2] = (byte) (secondbyte);
            ret[3] = (byte) (thirdbyte);
            ret[4] = (byte) ('#');


            BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
            BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();


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
