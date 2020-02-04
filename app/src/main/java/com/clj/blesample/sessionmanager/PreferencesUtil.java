package com.clj.blesample.sessionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesUtil {

    //Shared Preference Name For Whole App
    private static final String SHARED_PREF_NAME = "pref_name_scsk";
    public static final String KNOB_ANGLE="knob_angle";
    public static final String LEFT_KNOB_ANGLE="left_knob_angle";
    public static final String RIGHT_KNOB_ANGLE="right_knob_angle";
    public static final String NO_VALUE="no_value";
    public static final String BURNER_NAME="burner_name";

    public static final String TOP_BURNER_VESSEL="top_burner_vessel";
    public static final String LEFT_BURNER_VESSEL="left_burner_vessel";
    public static final String RIGHT_BURNER_VESSEL="right_burner_vessel";

    public static final String RECEIVED_STATUS="received_status";


    public static final String WRITE_VALUE="write_value";

    public static void setValueString(Context context, String key, String value) {

        if (context == null) return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static String getValueString(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
        return preferences.getString(key, NO_VALUE);


    }


    public static void setValueSInt(Context context, String key, int value) {

        if (context == null) return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();

    }

    public static int getValueInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
        return preferences.getInt(key, -1);

    }

    public static void remove(Context contextRemoveRewardID,String key){

        SharedPreferences removeRewardID = contextRemoveRewardID.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = removeRewardID.edit();
        editor.remove(key);
        editor.commit();


    }

}
