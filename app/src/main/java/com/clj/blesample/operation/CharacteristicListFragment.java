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

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CharacteristicListFragment extends Fragment  {

    String toCheck="";

    ImageView knobRound;
    TextView burnerTopOnClick;

    RelativeLayout relativeLayout;

    TextView knobAngleTop,knobAngleLeft,knobAngleRight;
    Croller burnerTop,burnerLeft,burnerRight;
    ImageView topBurnerVesselImage,leftBurnerVesselImage,rightBurnerVesselImage;
    ImageView topBurnerWhistleImage,leftBurnerWhistleImage,rightBurnerWhistleImage;
    TextView topBurnerWhistleCount,leftBurnerWhistleCount,rightBurnerWhistleCount;

    ImageView menuIcon;

    int pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7;
    char c0,c1,c2,c3,c4,c5,c6,c7;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_characteric_list, null);
        initView(v);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Calls Notify
        if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
            callMe(0, null);
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



        burnerTop = (Croller)v.findViewById(R.id.burnerTop);
        burnerLeft=(Croller)v.findViewById(R.id.burnerLeft);
        burnerRight=(Croller)v.findViewById(R.id.burnerRight);

        knobAngleTop=(TextView)v.findViewById(R.id.topBurnerKnobAngle);
        knobAngleLeft=(TextView)v.findViewById(R.id.leftBurnerKnobAngle);
        knobAngleRight=(TextView)v.findViewById(R.id.rightBurnerKnobAngle);

        topBurnerVesselImage=(ImageView)v.findViewById(R.id.top_Burner_Vessel);
        leftBurnerVesselImage=(ImageView)v.findViewById(R.id.leftBurner_Vessel);
        rightBurnerVesselImage=(ImageView)v.findViewById(R.id.rightBurnerVess_);

        topBurnerWhistleImage=(ImageView)v.findViewById(R.id.topBurner_whistle_img);
        leftBurnerWhistleImage=(ImageView)v.findViewById(R.id.leftBurner_Whistle);
        rightBurnerWhistleImage=(ImageView)v.findViewById(R.id.rightBurner_Whistle);

        topBurnerWhistleCount=(TextView)v.findViewById(R.id.topBurner_Whistle_Count);
        leftBurnerWhistleCount=(TextView)v.findViewById(R.id.leftBurner_Whistle_Count);
        rightBurnerWhistleCount=(TextView)v.findViewById(R.id.rightBurner_Whistle_Count);

        menuIcon=(ImageView)v.findViewById(R.id.menuIcon);

        burnerTopOnClick=(TextView)v.findViewById(R.id.burnerTopOnClick);

        knobRound=(ImageView)v.findViewById(R.id.knobRound);

        //relativeLayout=(RelativeLayout)v.findViewById(R.id.relativeLayout);

        /*relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("BurnerTopViewOnClick");

                PreferencesUtil.setValueSInt(getActivity(),PreferencesUtil.WRITE_VALUE,0);



            }
        });
*/




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
                Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/octin prison rg.ttf");

                System.out.println("Check"+progress);


                knobAngleTop.setTypeface(typeface);
                knobAngleTop.setText(String.valueOf(progress));

                String data=String.valueOf(progress);


                String burnerNumber=PreferencesUtil.getValueString(getActivity(),PreferencesUtil.BURNER_NAME);

                String knobRotationAngle=PreferencesUtil.getValueString(getActivity(),PreferencesUtil.KNOB_ANGLE);

                if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

                    System.out.println("Iamcalled");

                    int writeVal=PreferencesUtil.getValueInt(getActivity(),PreferencesUtil.WRITE_VALUE);

                    if(writeVal==1) {


                    }else  {

                        callMe(1, data);

                    }
                }


                if(progress>=0 && progress<=75 ){

                    //croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                }else if(progress>=75 && progress<=130){
                    //croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                }else if(progress>=131 && progress<=180){
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

        Intent menuIntent=new Intent(this.getActivity(),MenuActivity.class);
        startActivity(menuIntent);

    }

    private void callMe(int position, String userData) {

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
            wrietUserData(userData);
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

                        System.out.println("Iamnotifydata" + data);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("DataGettingFromStove " + HexUtil.formatHexString(data));

                                String hexadecimal = HexUtil.formatHexString(data);

                                int decimal = FormatConversion.hexaDecimalToDecimal(hexadecimal);

                                String binary = FormatConversion.decimalToBinary(decimal);

                                System.out.println("IamBinaryData" + binary);

                                if (binary.length() >= 8) {

                                    c7 = binary.charAt(7);
                                    c6 = binary.charAt(6);
                                    c5 = binary.charAt(5);
                                    c4 = binary.charAt(4);
                                    c3 = binary.charAt(3);
                                    c2 = binary.charAt(2);
                                    c1 = binary.charAt(1);
                                    c0 = binary.charAt(0);


                                }


                                pos7=Character.getNumericValue(c7);
                                pos6=Character.getNumericValue(c6);
                                pos5=Character.getNumericValue(c5);
                                pos4=Character.getNumericValue(c4);
                                pos3=Character.getNumericValue(c3);
                                pos2=Character.getNumericValue(c2);
                                pos1=Character.getNumericValue(c1);
                                pos0=Character.getNumericValue(c0);

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


    private void doFindBurnerDetails() {

        String burnerPosition = String.valueOf(pos6) + String.valueOf(pos7);

        if (burnerPosition.equals("00")) {
            System.out.println("Burner No 4");

            doFindKnobAngleForFirstBurner(burnerPosition);

        } else if (burnerPosition.equals("01")) {
            System.out.println("Burner No 1");

            doFindKnobAngleForFirstBurner(burnerPosition);

        } else if (burnerPosition.equals("10")) {
            System.out.println("Burner No 2");
        } else if (burnerPosition.equals("11")) {
            System.out.println("Burner No 3");

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

        String burnerNumber=PreferencesUtil.getValueString(this.getActivity(),PreferencesUtil.BURNER_NAME);
        System.out.println("SharedPreBurnerNumber"+burner_Number);
        String knobRotationAngle=PreferencesUtil.getValueString(this.getActivity(),PreferencesUtil.KNOB_ANGLE);

        String knobAngel = String.valueOf(pos1) + String.valueOf(pos2) + String.valueOf(pos3) + String.valueOf(pos4)
                + String.valueOf(pos5);

        System.out.println("Received Knob angle in binary value " + knobAngel);
        int decimal = Integer.parseInt(knobAngel, 2);

        String knob_val_string=String.valueOf(decimal);

        System.out.println("Knob Angel " + decimal);

        //show_data.setText("" + decimal);

        if(!burnerNumber.equals(burner_Number) || !knobRotationAngle.equals(knob_val_string)){

            PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,0);

            burnerTop.setProgress(decimal);


            knobAngleTop.setText("" + decimal);

            PreferencesUtil.setValueString(this.getActivity(),PreferencesUtil.BURNER_NAME,burner_Number);
            PreferencesUtil.setValueString(this.getActivity(),PreferencesUtil.KNOB_ANGLE,knob_val_string);


        }else if (burnerNumber.equals(burner_Number) && knobRotationAngle.equals(knob_val_string)){

            //If value is 1 means data wont write
            PreferencesUtil.setValueSInt(this.getActivity(),PreferencesUtil.WRITE_VALUE,1);



        }

        

    }


    private void wrietUserData(String hex) {
        //String hexi="f8";

        //Hexadecimal Format
        String finalHex=hex;

        int userEnterBurPos=Integer.parseInt(hex);

        if(userEnterBurPos<=10){
           finalHex="A";

        }else if(userEnterBurPos>10 && userEnterBurPos<=20){
            finalHex="14";
        }else if(userEnterBurPos>20 && userEnterBurPos<=30){
            finalHex="1E";
        }else if(userEnterBurPos>30 && userEnterBurPos<=40){
            finalHex="28";
        }else if(userEnterBurPos>40 && userEnterBurPos<=50){
            finalHex="32";
        }else if(userEnterBurPos>50 && userEnterBurPos<=60){
            finalHex="3C";
        }else if(userEnterBurPos>60 && userEnterBurPos<=70){
            finalHex="46";
        }else if(userEnterBurPos>70 && userEnterBurPos<=80){
            finalHex="50";
        }else if(userEnterBurPos>80 && userEnterBurPos<=90){
            finalHex="5A";
            //hexToByte(finalHex);
        }else if(userEnterBurPos>90 && userEnterBurPos<=100){
            finalHex="64";
        }


        BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
        BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();
        //HexUtil.hexStringToBytes(hex),

        byte[] bbb=FormatConversion.stringToByte(finalHex);

        //System.out.println("BBB "+HexUtil.formatHexString(bbb));


        BleManager.getInstance().write(
                bleDevice,
                characteristic.getService().getUuid().toString(),
                characteristic.getUuid().toString(),
                HexUtil.hexStringToBytes(hex),
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

        byte[] b=new byte[1];
        b=finalHex.getBytes();

        System.out.println("ByteALength"+b.length);

        for (int i=0;i<b.length;i++){
            System.out.println("CheckData"+b[i]);
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
