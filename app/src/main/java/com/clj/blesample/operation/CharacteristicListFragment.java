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
                //Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/octin prison rg.ttf");

                System.out.println("Check" + progress);

                String FOURTH_BURNER = "00";


                knobAngleTop.setTypeface(octinPrisonFont);
                int pro = progress;
                knobAngleTop.setText(String.valueOf(pro));

                String data = FormatConversion.integerToString(progress);

                String burnerNumber = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.BURNER_NAME);

                String knobRotationAngle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

                    String knobRotation_Angle = PreferencesUtil.getValueString(getActivity(), PreferencesUtil.KNOB_ANGLE);

                    if (!knobRotation_Angle.equals(data)) {


                        callMe(1, data, FOURTH_BURNER);
                        PreferencesUtil.setValueString(getActivity(), PreferencesUtil.KNOB_ANGLE, data);


                    } else {

                    }


                }


                if (progress >= 0 && progress <= 75) {

                    //croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {
                    //croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {
                    //croller.setIndicatorColor(Color.parseColor("#d32f2f"));
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
                knobAngleLeft.setText(String.valueOf(progress));


                if (progress >= 0 && progress <= 75) {

                    //croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {
                    //croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {
                    //croller.setIndicatorColor(Color.parseColor("#d32f2f"));
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

                knobAngleRight.setTypeface(octinPrisonFont);
                knobAngleRight.setText(String.valueOf(progress));

                if (progress >= 0 && progress <= 75) {

                    //croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                } else if (progress >= 75 && progress <= 130) {
                    //croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                } else if (progress >= 131 && progress <= 180) {
                    //croller.setIndicatorColor(Color.parseColor("#d32f2f"));
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

    private void callMenuItems() {

        Intent menuIntent = new Intent(this.getActivity(), MenuActivity.class);

        startActivity(menuIntent);

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

        final char char0, char1, char2, char3, char4, char5, char6, char7;

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


                        System.out.println("Iamnotifydata" + data);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                splitEachBurnerDataFromReceivedByte(data);


                                //int burnervalue=data&0x03;


                                //System.out.println("DataGettingFromStove " + HexUtil.formatHexString(data));

                                String hexadecimal = HexUtil.formatHexString(data);

                                int decimal = FormatConversion.hexaDecimalToDecimal(hexadecimal);

                                String binary = FormatConversion.decimalToBinary(decimal);

                                System.out.println("IamBinaryData" + binary);


                                //dofindBurner_Angle_Vessel_Details(binary);


                                System.out.println("ReceivedBinary" + binary);
                                System.out.println("TotalLength" + binary.length());

                                if (binary.length() >= 8) {

                                    c7 = binary.charAt(7);
                                    c6 = binary.charAt(6);
                                    c5 = binary.charAt(5);
                                    c4 = binary.charAt(4);
                                    c3 = binary.charAt(3);
                                    c2 = binary.charAt(2);
                                    c1 = binary.charAt(1);
                                    c0 = binary.charAt(0);

                                } else {

                                    System.out.println("Received Data From Stove Is Less Then 8 Character");

                                }


                                /*pos7 = Character.getNumericValue(c7);
                                pos6 = Character.getNumericValue(c6);
                                pos5 = Character.getNumericValue(c5);
                                pos4 = Character.getNumericValue(c4);
                                pos3 = Character.getNumericValue(c3);
                                pos2 = Character.getNumericValue(c2);
                                pos1 = Character.getNumericValue(c1);
                                pos0 = Character.getNumericValue(c0);*/

                                pos_7 = Character.toString(c7);
                                pos_6 = Character.toString(c6);
                                pos_5 = Character.toString(c5);
                                pos_4 = Character.toString(c4);
                                pos_3 = Character.toString(c3);
                                pos_2 = Character.toString(c2);
                                pos_1 = Character.toString(c1);
                                pos_0 = Character.toString(c0);

                                doFindBurnerDetails();

                                doFindVesselDetails();

                                //doFindKnobAngle();


                                //show_data.setText(HexUtil.formatHexString(characteristic.getValue()));


                                //int in=data;


                                //Converting hex to string
                                /*addText(txt, HexUtil.formatHexString(characteristic.getValue(),
                                        true));
                                */                        /*notifyText(txt, HexUtil.formatHexString(characteristic.getValue(),
                                                                true));

*/
                                // System.out.println("NOTIFY" + characteristic.getValue());
                            }
                        });
                    }
                });

    }

    private void splitEachBurnerDataFromReceivedByte(byte[] data) {

        byte[] topBurReceivedVal = new byte[1];
        byte[] leftBurReceivedVal = new byte[1];
        byte[] rightBurReceivedVal = new byte[1];

        for (int i = 0; i < data.length; i++) {


            int k = data[i];

            if (i == 0) {
                topBurReceivedVal[i] = data[i];
            }
            if (i == 1) {
                leftBurReceivedVal[i - 1] = data[i];
            }
            if (i == 2) {
                rightBurReceivedVal[i - 2] = data[i];
            }


        }

        String topBurnerHexValue = HexUtil.formatHexString(topBurReceivedVal);
        String leftBurnerHexValue = HexUtil.formatHexString(leftBurReceivedVal);
        String rightBurnerHexValue = HexUtil.formatHexString(rightBurReceivedVal);

        int topBurnerdecimalValue = FormatConversion.hexaDecimalToDecimal(topBurnerHexValue);
        String topBurnerBinaryValue = FormatConversion.decimalToBinary(topBurnerdecimalValue);

        int leftBurnerdecimalValue = FormatConversion.hexaDecimalToDecimal(leftBurnerHexValue);
        String leftBurnerBinaryValue = FormatConversion.decimalToBinary(leftBurnerdecimalValue);

        int rightBurnerdecimalValue = FormatConversion.hexaDecimalToDecimal(rightBurnerHexValue);
        String rightBurnerBinaryValue = FormatConversion.decimalToBinary(rightBurnerdecimalValue);


        doSplitTopBurner(topBurnerBinaryValue);
        doSplitLeftBurner(leftBurnerBinaryValue);
        doSplitRightBurner(rightBurnerBinaryValue);

    }


    private void doSplitTopBurner(String topBurnerBinaryValue) {

        char topChar0 = '\u0000', topChar1 = '\u0000', topChar2 = '\u0000', topChar3 = '\u0000', topChar4 = '\u0000', topChar5 = '\u0000', topChar6 = '\u0000', topChar7 = '\u0000';
        String topString0, topString1, topString2, topString3, topString4, topString5, topString6, topString7;
        String topKnobAngleValue;
        String topBurnerAngleInString;
        String topBurnerVessel;

        if (topBurnerBinaryValue.length() == 8) {

            topChar7 = topBurnerBinaryValue.charAt(7);
            topChar6 = topBurnerBinaryValue.charAt(6);
            topChar5 = topBurnerBinaryValue.charAt(5);
            topChar4 = topBurnerBinaryValue.charAt(4);
            topChar3 = topBurnerBinaryValue.charAt(3);
            topChar2 = topBurnerBinaryValue.charAt(2);
            topChar1 = topBurnerBinaryValue.charAt(1);
            topChar0 = topBurnerBinaryValue.charAt(0);

        } else {

            System.out.println("Received Data From Stove Is Less Then 8 Character");

        }


        topString7 = Character.toString(topChar7);
        topString6 = Character.toString(topChar6);
        topString5 = Character.toString(topChar5);
        topString4 = Character.toString(topChar4);
        topString3 = Character.toString(topChar3);
        topString2 = Character.toString(topChar2);
        topString1 = Character.toString(topChar1);
        topString0 = Character.toString(topChar0);


        topKnobAngleValue = topString1 + topString2 + topString3 + topString4 + topString5;

        int topBurnerAngleInDecimal = Integer.parseInt(topKnobAngleValue, 2);
        topBurnerAngleInString = String.valueOf(topBurnerAngleInDecimal);
        topBurnerVessel = topString0;


        //doRotateTopBurner(topBurnerAngleInString, topBurnerVessel);


    }


    private void doSplitRightBurner(String rightBurnerBinaryValue) {

        char rightChar0 = '\u0000', rightChar1 = '\u0000', rightChar2 = '\u0000', rightChar3 = '\u0000', rightChar4 = '\u0000', rightChar5 = '\u0000', rightChar6 = '\u0000', rightChar7 = '\u0000';
        String rightString0, rightString1, rightString2, rightString3, rightString4, rightString5, rightString6, rightString7;
        String rightKnobAngleValue;
        String rightBurnerAngleInString;
        String rightBurnerVessel;

        if (rightBurnerBinaryValue.length() == 8) {

            rightChar7 = rightBurnerBinaryValue.charAt(7);
            rightChar6 = rightBurnerBinaryValue.charAt(6);
            rightChar5 = rightBurnerBinaryValue.charAt(5);
            rightChar4 = rightBurnerBinaryValue.charAt(4);
            rightChar3 = rightBurnerBinaryValue.charAt(3);
            rightChar2 = rightBurnerBinaryValue.charAt(2);
            rightChar1 = rightBurnerBinaryValue.charAt(1);
            rightChar0 = rightBurnerBinaryValue.charAt(0);

        } else {

            System.out.println("Received Data From Stove Is Less Then 8 Character");

        }

        rightString7 = Character.toString(rightChar7);
        rightString6 = Character.toString(rightChar6);
        rightString5 = Character.toString(rightChar5);
        rightString4 = Character.toString(rightChar4);
        rightString3 = Character.toString(rightChar3);
        rightString2 = Character.toString(rightChar2);
        rightString1 = Character.toString(rightChar1);
        rightString0 = Character.toString(rightChar0);

        rightKnobAngleValue = rightString1 + rightString2 + rightString3 + rightString4 + rightString5;

        int rightBurnerAngleInDecimal = Integer.parseInt(rightKnobAngleValue, 2);
        rightBurnerAngleInString = String.valueOf(rightBurnerAngleInDecimal);
        rightBurnerVessel = rightString0;

        //doRotateRightBurner(rightBurnerAngleInString, rightBurnerVessel);


    }

    private void doSplitLeftBurner(String leftBurnerBinaryValue) {

        char leftChar0 = '\u0000', leftChar1 = '\u0000', leftChar2 = '\u0000', leftChar3 = '\u0000', leftChar4 = '\u0000', leftChar5 = '\u0000', leftChar6 = '\u0000', leftChar7 = '\u0000';
        String leftString0, leftString1, leftString2, leftString3, leftString4, leftString5, leftString6, leftString7;
        String leftKnobAngleValue;
        String leftBurnerAngleInString;
        String leftBurnerVessel;

        if (leftBurnerBinaryValue.length() == 8) {

            leftChar7 = leftBurnerBinaryValue.charAt(7);
            leftChar6 = leftBurnerBinaryValue.charAt(6);
            leftChar5 = leftBurnerBinaryValue.charAt(5);
            leftChar4 = leftBurnerBinaryValue.charAt(4);
            leftChar3 = leftBurnerBinaryValue.charAt(3);
            leftChar2 = leftBurnerBinaryValue.charAt(2);
            leftChar1 = leftBurnerBinaryValue.charAt(1);
            leftChar0 = leftBurnerBinaryValue.charAt(0);

        } else {

            System.out.println("Received Data From Stove Is Less Then 8 Character");

        }

        leftString7 = Character.toString(leftChar7);
        leftString6 = Character.toString(leftChar6);
        leftString5 = Character.toString(leftChar5);
        leftString4 = Character.toString(leftChar4);
        leftString3 = Character.toString(leftChar3);
        leftString2 = Character.toString(leftChar2);
        leftString1 = Character.toString(leftChar1);
        leftString0 = Character.toString(leftChar0);

        leftKnobAngleValue = leftString1 + leftString2 + leftString3 + leftString4 + leftString5;

        int leftBurnerAngleInDecimal = Integer.parseInt(leftKnobAngleValue, 2);
        leftBurnerAngleInString = String.valueOf(leftBurnerAngleInDecimal);
        leftBurnerVessel = leftString0;


        //doRotateLeftBurner(leftBurnerAngleInString, leftBurnerVessel);


    }



   /* private void dofindBurner_Angle_Vessel_Details(String binary) {

        char

        if (binary.length() >= 8) {

           char c7 = binary.charAt(7);
            c6 = binary.charAt(6);
            c5 = binary.charAt(5);
            c4 = binary.charAt(4);
            c3 = binary.charAt(3);
            c2 = binary.charAt(2);
            c1 = binary.charAt(1);
            c0 = binary.charAt(0);

        } else {

            System.out.println("Received Data From Stove Is Less Then 8 Character");

        }




    }*/


    private void doFindBurnerDetails() {

        String burnerPosition = pos_6 + pos_7;

        //String burnerPosition = String.valueOf(pos6) + String.valueOf(pos7);

        if (burnerPosition.equals("00")) {
            System.out.println("BurnerNo4");
            String FOURTH_BURDER = "44";

            doFindKnobAngleForFirstBurner(burnerPosition);

        } else if (burnerPosition.equals("01")) {
            System.out.println("Burner No 1");

            // doFindKnobAngleForFirstBurner(burnerPosition);

        } else if (burnerPosition.equals("10")) {
            System.out.println("Burner No 2");
        } else if (burnerPosition.equals("11")) {
            System.out.println("Burner No 3");
            //doFindKnobAngleForFirstBurner(burnerPosition);

        }


    }


    private void doFindVesselDetails() {

        if (String.valueOf(pos0).equals("0")) {
            System.out.println("Vessel Not Detected " + pos0);
        } else if (String.valueOf(pos0).equals("1")) {
            System.out.println("Vessel Detected " + pos0);
        }
    }


    private void doFindKnobAngleForFirstBurner(String burner_Number) {

        String burnerNumber = PreferencesUtil.getValueString(this.getActivity(), PreferencesUtil.BURNER_NAME);
        System.out.println("SharedPreBurnerNumber" + burner_Number);
        String knobRotationAngle = PreferencesUtil.getValueString(this.getActivity(), PreferencesUtil.KNOB_ANGLE);

        /*String knobAngel = String.valueOf(pos1) + String.valueOf(pos2) + String.valueOf(pos3) + String.valueOf(pos4)
                + String.valueOf(pos5);*/

        String knobAngel = pos_1 + pos_2 + pos_3 + pos_4 + pos_5;

        System.out.println("Received Knob angle in binary value " + knobAngel);
        int decimal = Integer.parseInt(knobAngel, 2);

        String knob_val_string = String.valueOf(decimal);

        System.out.println("KnobAngel " + decimal);

        //show_data.setText("" + decimal);

        if (!burnerNumber.equals(burner_Number) || !knobRotationAngle.equals(knob_val_string)) {

            //PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,0);

            burnerTop.setProgress(decimal * 10);


            knobAngleTop.setText("" + decimal * 10);

            PreferencesUtil.setValueString(this.getActivity(), PreferencesUtil.BURNER_NAME, burner_Number);
            PreferencesUtil.setValueString(this.getActivity(), PreferencesUtil.KNOB_ANGLE, knob_val_string);


        } else if (burnerNumber.equals(burner_Number) && knobRotationAngle.equals(knob_val_string)) {

            //If value is 1 means data wont write
            // PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,1);


        }


    }


    private void wrietUserData(String hex, String bur_ner) {
        //String hexi="f8";

        int angel_ = Integer.parseInt(hex) / 10;
        int burner_ = Integer.parseInt(bur_ner);
        int vessel = 1;
        int sendbyte = 0;
        sendbyte = ((angel_ << 2) | (burner_) | (vessel << 7));

        //byte c=(byte)sendbyte;

        byte[] ret = new byte[1];
        ret[0] = (byte) (sendbyte);

        // byte[] b=;


        // String wireteBinaryData=vessel+Integer.toBinaryString(angel_)+bur_ner;

        /*String wireteBinaryData="00011000";
        System.out.println("NewBinary"+wireteBinaryData);

       String writeHexFormat= FormatConversion.convertBinaryToHexadecimal(wireteBinaryData);

        byte[] a=FormatConversion.hexStringToByteArray(writeHexFormat);*/

        /*String finalHexa_Decimal = FormatConversion.decimalToHexaDecimalConversion(hex, bur_ner);

        System.out.println("FinalVAlue" + finalHexa_Decimal);*/


        BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
        BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();
        //HexUtil.hexStringToBytes(hex),

        //byte[] bbb=FormatConversion.stringToByte(finalHexa_Decimal);

        //System.out.println("BBB "+HexUtil.formatHexString(bbb));


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
                                /*addText(txt, "write success, current: " + current
                                        + " total: " + total
                                        + " justWrite: " + HexUtil.formatHexString(justWrite, true));*/

                                //user_entered_data.setText("User Entered Data Is" + HexUtil.formatHexString(justWrite));

                                System.out.println("WrittenDataIs" + HexUtil.formatHexString(justWrite));

                                //t
                                //System.out.println(HexUtil.encodeHex(justWrite));
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


    }

    private void hexToByte(String finalHex) {

        byte[] b = new byte[1];
        b = finalHex.getBytes();

        System.out.println("ByteALength" + b.length);

        for (int i = 0; i < b.length; i++) {
            System.out.println("CheckData" + b[i]);
        }


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


}
