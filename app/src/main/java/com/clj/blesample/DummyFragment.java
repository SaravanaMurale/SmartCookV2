package com.clj.blesample;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;


public class DummyFragment extends Fragment {

    TextView textView,leftBurner,rightBurner;
    Croller crollerTop,crollerLeft,crollerRight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dummy, container, false);

         textView=(TextView)view.findViewById(R.id.positionViewTopBurner);
         leftBurner=(TextView)view.findViewById(R.id.positionViewLeftBurner);
         rightBurner=(TextView)view.findViewById(R.id.positionViewRightBurner);

        crollerTop = (Croller)view.findViewById(R.id.crollers);
        crollerLeft=(Croller)view.findViewById(R.id.crollersLeft);
        crollerRight=(Croller)view.findViewById(R.id.crollersRight);

        //crollerTop.setAntiClockwise(true);
        //crollerTop.setIndicatorWidth(10);
        //crollerTop.setBackCircleColor(Color.parseColor("#EDEDED"));
        //crollerTop.setMainCircleColor(Color.WHITE);
        //crollerTop.setMin(0);
        //crollerTop.setMax(180);
        //crollerTop.setLabel("Burner 1");
        //crollerTop.setStartOffset(45);
        //crollerTop.setLabelSize(15);
        //crollerTop.setIsContinuous(true);
        //crollerTop.setLabelColor(Color.BLACK);
        //crollerTop.setProgressPrimaryColor(Color.parseColor("#0B3C49"));
        //crollerTop.setIndicatorColor(Color.parseColor("#0B3C49"));
        //crollerTop.setProgressSecondaryColor(Color.parseColor("#EEEEEE"));


        crollerTop.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                textView.setText(String.valueOf(progress));

                if(progress>=0 && progress<=75 ){
                    croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                }else if(progress>=75 && progress<=130){
                    croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                }else if(progress>=131 && progress<=180){
                    croller.setIndicatorColor(Color.parseColor("#de7300"));
                    croller.setProgressPrimaryColor(Color.parseColor("#de7300"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });

        crollerLeft.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                leftBurner.setText(String.valueOf(progress));

                if(progress>=0 && progress<=75 ){
                    croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                }else if(progress>=75 && progress<=130){
                    croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                }else if(progress>=131 && progress<=180){
                    croller.setIndicatorColor(Color.parseColor("#de7300"));
                    croller.setProgressPrimaryColor(Color.parseColor("#de7300"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });

        crollerRight.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                rightBurner.setText(String.valueOf(progress));

                if(progress>=0 && progress<=75 ){
                    croller.setIndicatorColor(Color.parseColor("#76c9f5"));
                    croller.setProgressPrimaryColor(Color.parseColor("#76c9f5"));
                }else if(progress>=75 && progress<=130){
                    croller.setIndicatorColor(Color.parseColor("#f3e701"));
                    croller.setProgressPrimaryColor(Color.parseColor("#f3e701"));
                }else if(progress>=131 && progress<=180){
                    croller.setIndicatorColor(Color.parseColor("#de7300"));
                    croller.setProgressPrimaryColor(Color.parseColor("#de7300"));
                }

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });






        return view;
    }


}
