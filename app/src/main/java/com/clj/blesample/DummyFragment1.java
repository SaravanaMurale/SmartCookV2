package com.clj.blesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clj.blesample.menuoperationactivity.MenuActivity;


public class DummyFragment1 extends Fragment {


    ImageView leftBurner,leftBurnerVessel,leftBurnerTimer,leftBurnerWhistle,leftBurnerVesselEmpty;
    Button leftBurnerSetting,leftBurnerEdit;

    ImageView centerBurner,centerBurnerVessel,centerBurnerTimer,centerBurnerWhistle,centerBurnerVesselEmpty;
    Button centerBurnerSetting,centerBurnerEdit;

    ImageView rightBurner,rightBurnerVessel,rightBurnerTimer,rightBurnerWhistle,rightBurnerVesselEmpty;
    Button rightBurnerSetting,rightBurnerEdit;

    LinearLayout leftBurnerWidgetBlock,centerBurnerWidgetBlock,rightBurnerWidgetBlock;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        initView(view);


        return view;
    }

    private void initView(View view) {

        leftBurner=(ImageView)view.findViewById(R.id.leftBurner);
        leftBurnerVessel=(ImageView)view.findViewById(R.id.leftBurnerVessel);
        leftBurnerTimer=(ImageView)view.findViewById(R.id.leftBurnerTimer);
        leftBurnerWhistle=(ImageView)view.findViewById(R.id.leftBurnerWhistle);
        leftBurnerVesselEmpty=(ImageView)view.findViewById(R.id.leftBurnerVesselEmpty);

        leftBurnerSetting=(Button)view.findViewById(R.id.leftBurnerSetting);
        leftBurnerEdit=(Button)view.findViewById(R.id.leftBurnerEdit);


        centerBurner=(ImageView)view.findViewById(R.id.centerBurner);
        centerBurnerVessel=(ImageView)view.findViewById(R.id.centerBurnerVessel);
        centerBurnerTimer=(ImageView)view.findViewById(R.id.centerBurnerTimer);
        centerBurnerWhistle=(ImageView)view.findViewById(R.id.centerBurnerWhistle);
        centerBurnerVesselEmpty=(ImageView)view.findViewById(R.id.centerBurnerVesselEmpty);

        centerBurnerSetting=(Button)view.findViewById(R.id.centerBurnerSetting);
        centerBurnerEdit=(Button)view.findViewById(R.id.centerBurnerEdit);

        rightBurner=(ImageView)view.findViewById(R.id.rightBurner);
        rightBurnerVessel=(ImageView)view.findViewById(R.id.rightBurnerVessel);
        rightBurnerTimer=(ImageView)view.findViewById(R.id.rightBurnerTimer);
        rightBurnerWhistle=(ImageView)view.findViewById(R.id.rightBurnerWhistle);
        rightBurnerVesselEmpty=(ImageView)view.findViewById(R.id.rightBurnerVesselEmpty);

        rightBurnerSetting=(Button)view.findViewById(R.id.rightBurnerSetting);
        rightBurnerEdit=(Button)view.findViewById(R.id.rightBurnerEdit);

        leftBurnerWidgetBlock=(LinearLayout)view.findViewById(R.id.leftBurnerWidgetsBlock);
        centerBurnerWidgetBlock=(LinearLayout)view.findViewById(R.id.centerBurnerWidgetsBlock);
        rightBurnerWidgetBlock=(LinearLayout)view.findViewById(R.id.rightBurnerWidgetsBlock);




    }

    private void launchMenuActivity() {

        Intent intent = new Intent(this.getActivity(), MenuActivity.class);
        startActivity(intent);

    }

    private void callDummyFragment2() {

        /*Fragment fragment = new DummyFragment2();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/

    }


}
