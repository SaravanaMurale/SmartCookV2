package com.clj.blesample;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.clj.blesample.databasemanager.SqliteManager;
import com.sdsmdg.harjot.crollerTest.Croller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DummyActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    float value;

    TextView textView;

    EditText editText;
    Button saveData, loadData;
    public static final String FILE_NAME = "scsk.txt";
    FileOutputStream fos;

    Button moveToFragment, saveInSqlite;

    List<String> burnerList = new ArrayList<>();
    List<String> listInString = new ArrayList<>();
    List<Integer> gasUsageLisst = new ArrayList<>();
    String date1 = "1/11/2020";
    String date2 = "2/11/2020";
    String date3 = "3/11/2020";
    String date4 = "4/11/2020";
    String date5 = "5/11/2020";
    String date6 = "6/11/2020";
    String date7 = "7/11/2020";
    String date8 = "8/11/2020";
    String date9 = "9/11/2020";
    String date10 = "10/11/2020";

    SqliteManager sqliteManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dummy);

        sqliteManager = new SqliteManager(DummyActivity.this);

        gasUsageList();

        usageDateList();

        burnerList();




        /*editText = (EditText) findViewById(R.id.editText);
        saveData = (Button) findViewById(R.id.saveData);
        loadData = (Button) findViewById(R.id.loadData);*/


        moveToFragment = (Button) findViewById(R.id.moveToFragment);
        saveInSqlite = (Button) findViewById(R.id.saveInSqlite);

        saveInSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToSqliteDB();

            }
        });

        moveToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                moveToFragment.setVisibility(View.GONE);

                Fragment fragment = new DummyFragment1();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        /*GradientDrawable bgShape = (GradientDrawable) round.getBackground();
        bgShape.setColor(Color.BLACK);*/


      /*  saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*try {
                    fos=new FileOutputStream(FILE_NAME,true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*//*

                try {
                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    fos.write(editText.getText().toString().getBytes());

                    editText.getText().clear();

                    System.out.println("FileDirectory " + getFilesDir());
                    System.out.println("WrittenSuccessfully");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });


        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fis = null;

                try {
                    fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sbr = new StringBuilder();
                    String txt;

                    while ((txt = br.readLine()) != null) {

                        sbr.append(txt).append("\n");

                    }

                    editText.setText(sbr.toString());
                    Toast.makeText(DummyActivity.this, sbr.toString(), Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
*/

    }

    private void burnerList() {

        burnerList.add("00");
        burnerList.add("01");
        burnerList.add("10");
        burnerList.add("00");
        burnerList.add("01");
        burnerList.add("10");
        burnerList.add("00");
        burnerList.add("01");
        burnerList.add("10");
        burnerList.add("00");

    }

    private void usageDateList() {
        /*Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        System.out.println(sDate1 + "\t" + date1);

        dateList.add();*/

        listInString.add(date1);
        listInString.add(date2);
        listInString.add(date3);
        listInString.add(date4);
        listInString.add(date5);
        listInString.add(date6);
        listInString.add(date7);
        listInString.add(date8);
        listInString.add(date9);
        listInString.add(date10);


    }

    private void gasUsageList() {

        gasUsageLisst.add(10);
        gasUsageLisst.add(2);
        gasUsageLisst.add(8);
        gasUsageLisst.add(6);
        gasUsageLisst.add(4);
        gasUsageLisst.add(8);
        gasUsageLisst.add(1);
        gasUsageLisst.add(5);
        gasUsageLisst.add(7);
        gasUsageLisst.add(2);

    }


    private void saveDataToSqliteDB() {

        for (int i = 0; i < listInString.size(); i++) {

            try {
                Date dateFormet = new SimpleDateFormat("dd/MM/yyyy").parse(listInString.get(i));

                System.out.println("StringToDateFormet" + dateFormet);
                System.out.println("GasUsageValue" + gasUsageLisst.get(i));

                sqliteManager.addGasConsumptionPattern(dateFormet, gasUsageLisst.get(i), burnerList.get(i));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        Toast.makeText(DummyActivity.this, "All Value Inserted", Toast.LENGTH_LONG).show();


    }


}



