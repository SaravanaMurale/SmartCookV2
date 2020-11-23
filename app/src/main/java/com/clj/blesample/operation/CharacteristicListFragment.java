package com.clj.blesample.operation;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.menuoperationactivity.MenuActivity;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CharacteristicListFragment extends Fragment {


    private ResultAdapter mResultAdapter;

    //MyCode
    BluetoothGattCharacteristic characteristic;
    List<Integer> propList = new ArrayList<>();
    List<String> propNameList = new ArrayList<>();


    int SIZE_OF_CHARACTERISTIC = 0;


    int currentApiVersion;


    ImageView leftOffImg, leftHighImg, leftSimImg;
    ImageView rightOffImg, rightHighImg, rightSimImg;
    ImageView centerOffImg, centerHighImg, centerSimImg;

    ImageView menuEStop, menuSetTimer, menuSetWhistle;

    ImageView leftTimer, leftVessel, leftWhistle;
    ImageView rightTimer, rightVessel, rightWhistle;
    ImageView centerTimer, centerVessel, centerWhistle;

    Spinner mSpinner, mSpinnerWhistleCount, mSelectBurner;
    ImageView eTimer;


    String left = "00", center = "01", right = "10";

    byte[] homeByte = new byte[12];


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

        initView(v);

        /*String strtext = getArguments().getString("edttext");

        System.out.println("ReceivedTExt" + strtext);*/

        /*EditActivity editActivity=new EditActivity();
        editActivity.EditActivityMethod(this);*/

        setStoveData();


        return v;
    }

    private void setStoveData() {

        homeByte[0] = 1; //active
        homeByte[1] = 1; //vessel
        homeByte[2] = 1; //timer
        homeByte[3] = 0; //whistle

        homeByte[4] = 0;
        homeByte[5] = 0;
        homeByte[6] = 0;
        homeByte[7] = 0;

        homeByte[8] = 1;
        homeByte[9] = 0;
        homeByte[10] = 0;
        homeByte[11] = 0;


    }

    private void blinkImage(ImageView timerIcon) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        timerIcon.startAnimation(animation);
    }

    private void stopImageBlinking(ImageView topTimerIcon) {
        topTimerIcon.clearAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();

        //Calls Notify
        if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

            Toast.makeText(getActivity(), "NotifyCalled", Toast.LENGTH_LONG).show();

            callMe(0, null, 0, 0, 0);

        }


        //Calls Notify
       /* if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {

            callMe(0, null, null, 0);


        }*/


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
        ListView listView_device = (ListView) v.findViewById(R.id.list_service_character);

        leftOffImg = (ImageView) v.findViewById(R.id.leftOff);
        leftHighImg = (ImageView) v.findViewById(R.id.leftHigh);
        leftSimImg = (ImageView) v.findViewById(R.id.leftSim);

        rightOffImg = (ImageView) v.findViewById(R.id.rightOff);
        rightHighImg = (ImageView) v.findViewById(R.id.rightHigh);
        rightSimImg = (ImageView) v.findViewById(R.id.rightSim);

        centerOffImg = (ImageView) v.findViewById(R.id.centerOff);
        centerHighImg = (ImageView) v.findViewById(R.id.centerHigh);
        centerSimImg = (ImageView) v.findViewById(R.id.centerSim);

        menuEStop = (ImageView) v.findViewById(R.id.menuEStop);
        menuSetTimer = (ImageView) v.findViewById(R.id.menuSetTimer);
        menuSetWhistle = (ImageView) v.findViewById(R.id.menuSetWhistle);

        leftTimer = (ImageView) v.findViewById(R.id.leftTimer);
        leftVessel = (ImageView) v.findViewById(R.id.leftStove);
        leftWhistle = (ImageView) v.findViewById(R.id.leftWhistle);

        rightTimer = (ImageView) v.findViewById(R.id.rightTimer);
        rightVessel = (ImageView) v.findViewById(R.id.rightStove);
        rightWhistle = (ImageView) v.findViewById(R.id.rightWhistle);

        centerTimer = (ImageView) v.findViewById(R.id.centerTimer);
        centerVessel = (ImageView) v.findViewById(R.id.centerStove);
        centerWhistle = (ImageView) v.findViewById(R.id.centerWhistle);


        leftOffImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMe(1, "00", 0, 1, 0);

            }
        });

        leftHighImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMe(1, "00", 0, 1, 1);

            }
        });

        leftSimImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "00", 0, 1, 0);
            }
        });

        rightOffImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "01", 0, 1, 0);
            }
        });

        rightHighImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "01", 0, 1, 0);
            }
        });

        rightSimImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "01", 0, 1, 0);
            }
        });

        centerOffImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "10", 0, 1, 0);
            }
        });

        centerHighImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "10", 0, 1, 0);
            }
        });

        centerSimImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(1, "10", 0, 1, 0);
            }
        });

        menuEStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("You want to turn off stove?");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (SIZE_OF_CHARACTERISTIC == 2 && mResultAdapter != null) {
                            callMe(1, "null", 1, 3, 0);
                        }

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();
                    }
                });

                android.support.v7.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        menuSetWhistle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String burners[] = {"Burner", "Center", "Left", "Right"};
                String whistleCount[] = {"Whistle", "0", "1", "2", "3", "4", "5"};

                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
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


            }
        });

        menuSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



       /* menuIcon = (ImageView) v.findViewById(R.id.menuIcon);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });*/


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


    private void callMe(int position, String burner, int timerInMin, int whistleInCount, int flameMode) {

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
            wrietUserData(burner, timerInMin, whistleInCount, flameMode);
        }


    }


    private void wrietUserData(String burner, int timerInMin, int whistleInCount, int flameMode) {


        byte[] startBurner = new byte[9];


        startBurner[0] = (byte) ('*');
        startBurner[1] = (byte) (0xC0);
                /*secondFrame[2] = (byte) (burner);
                secondFrame[3] = (byte) ();
                secondFrame[4] = (byte) ();
                secondFrame[5] = (byte) (leftBurnerTimer);
                secondFrame[6] = (byte) (rightBurnerWhistle);
                secondFrame[7] = (byte) (rightBurnerTimer);*/
        startBurner[8] = (byte) ('#');


        BleDevice bleDevice = ((OperationActivity) getActivity()).getBleDevice();
        BluetoothGattCharacteristic characteristic = ((OperationActivity) getActivity()).getCharacteristic();


        BleManager.getInstance().write(
                bleDevice,
                characteristic.getService().getUuid().toString(),
                characteristic.getUuid().toString(),
                startBurner,
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

//        System.out.println(data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]);

        System.out.println("ReceivedLength " + data.length);


        if (data.length == 7) {
            Toast.makeText(getActivity(), "Length 7 Received", Toast.LENGTH_LONG).show();
        } else if (data.length == 9) {
            Toast.makeText(getActivity(), "Length 9 Received", Toast.LENGTH_LONG).show();
        }

        /*if (data.length == 7) {

            if (data[0] == 42) {


            }

        } else {
            Toast.makeText(getActivity(), "Length is less than 7", Toast.LENGTH_LONG).show();
        }*/


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

    private void callAlertDialog() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        // alert.setTitle("Vessel is not detected");
        alert.setMessage("Please Place Vessel");
        alert.setIcon(R.drawable.preethi_logo);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        alert.show();

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
