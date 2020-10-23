package com.clj.blesample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;


public class DummyFragment1 extends Fragment {


    Button leftBurner, leftBurnerSettings, leftBurnerEdit, centerBurner, centerBurnerSettings, centerBurnerEdit, rightBurner, rightBurnerSettings, rightBurnerEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        leftBurner = (Button) view.findViewById(R.id.leftBurner);
        leftBurnerSettings = (Button) view.findViewById(R.id.leftBurnerSettings);
        leftBurnerEdit = (Button) view.findViewById(R.id.leftBurnerEdit);

        centerBurner = (Button) view.findViewById(R.id.centerBurner);
        centerBurnerSettings = (Button) view.findViewById(R.id.centerBurnerSettings);
        centerBurnerEdit = (Button) view.findViewById(R.id.centerBurnerEdit);

        rightBurner = (Button) view.findViewById(R.id.rightBurner);
        rightBurnerSettings = (Button) view.findViewById(R.id.rightBurnerSettings);
        rightBurnerEdit = (Button) view.findViewById(R.id.rightBurnerEdit);

        leftBurnerSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDummyFragment2();
            }
        });

        centerBurnerSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDummyFragment2();
            }
        });

        rightBurnerSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDummyFragment2();
            }
        });


        return view;
    }

    private void callDummyFragment2() {

        Fragment fragment = new DummyFragment2();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
