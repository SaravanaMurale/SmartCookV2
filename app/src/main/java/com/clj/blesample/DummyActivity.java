package com.clj.blesample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.sdsmdg.harjot.crollerTest.Croller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DummyActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    float value;

    TextView textView;

    EditText editText;
    Button saveData, loadData;
    public static final String FILE_NAME = "scsk.txt";
    FileOutputStream fos;

    Button moveToFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        /*editText = (EditText) findViewById(R.id.editText);
        saveData = (Button) findViewById(R.id.saveData);
        loadData = (Button) findViewById(R.id.loadData);*/


        moveToFragment = (Button) findViewById(R.id.moveToFragment);

        moveToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                moveToFragment.setVisibility(View.GONE);

                Fragment fragment = new DummyFragment1();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
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


}



