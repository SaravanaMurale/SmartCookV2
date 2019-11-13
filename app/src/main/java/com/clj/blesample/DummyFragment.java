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

    TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dummy, container, false);

         textView=(TextView)view.findViewById(R.id.positionView);

        Croller croller = (Croller)view.findViewById(R.id.crollers);

        croller.setAntiClockwise(true);
        croller.setIndicatorWidth(10);
        croller.setBackCircleColor(Color.parseColor("#EDEDED"));
        croller.setMainCircleColor(Color.WHITE);
        croller.setMin(0);
        croller.setMax(180);
        croller.setLabel("Burner 1");
        croller.setStartOffset(45);
        croller.setLabelSize(15);
        croller.setIsContinuous(true);
        croller.setLabelColor(Color.BLACK);
        croller.setProgressPrimaryColor(Color.parseColor("#0B3C49"));
        croller.setIndicatorColor(Color.parseColor("#0B3C49"));
        croller.setProgressSecondaryColor(Color.parseColor("#EEEEEE"));

        croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

                textView.setText("Position"+progress);

                if(progress>=0 && progress<=50 ){
                    croller.setIndicatorColor(Color.parseColor("#0000ff"));
                    croller.setProgressPrimaryColor(Color.parseColor("#0000ff"));
                }else if(progress>=51 && progress<=100){
                    croller.setIndicatorColor(Color.parseColor("#ffff00"));
                    croller.setProgressPrimaryColor(Color.parseColor("#ffff00"));
                }else if(progress>=101 && progress<=180){
                    croller.setIndicatorColor(Color.parseColor("#ff0000"));
                    croller.setProgressPrimaryColor(Color.parseColor("#ff0000"));
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
