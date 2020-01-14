package com.clj.blesample;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DummyActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    float value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

       /* DummyFragment dummyFragment=new DummyFragment();

        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction().add(R.id.fragmentContainer,dummyFragment,null);
        fragmentTransaction.commit();
*/


        /*Calendar c = Calendar.getInstance();
        System.out.println("TimeAlone => "+c.getTime());
*/


        /*SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        System.out.println("FormattedDate");
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();*/


        Date time=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = sdf.format(time);
        System.out.println("TimeAlone "+currentDateTimeString);


        Date date=new Date();
        SimpleDateFormat sDate=new SimpleDateFormat("dd-MM-yyyy");
        String formattedDAte = sDate.format(date);
        System.out.println("DateAlone "+formattedDAte);





    }
}
