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

  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }*/

   /* private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       /* getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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


}



