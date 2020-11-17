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

    byte[] burnerValue = new byte[12];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        initView(view);

        setValueToBurner();


        return view;
    }

    private void setValueToBurner() {

        //Left
        burnerValue[0] = 1; //active
        burnerValue[1] = 1; //vessel
        burnerValue[2] = 1; //timer
        burnerValue[3] = 1; //whistle

        //Center
        burnerValue[4] = 0;
        burnerValue[5] = 0;
        burnerValue[6] = 0;
        burnerValue[7] = 0;

        //Right
        burnerValue[8] = 1;
        burnerValue[9] = 0;
        burnerValue[10] = 0;
        burnerValue[11] = 0;

        if(burnerValue[0]==1 && burnerValue[1]==1 ){
            //leftBurner.setBackground(getResources().getDrawable(R.drawable.burner_with_vessel));
            leftBurner.setImageDrawable(getResources().getDrawable(R.drawable.burner_with_vessel));
        }



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
