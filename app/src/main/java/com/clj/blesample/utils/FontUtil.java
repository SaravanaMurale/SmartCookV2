package com.clj.blesample.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.Settings;

public class FontUtil {

    public static Typeface getOctinPrisonFont(Context context){

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/octin prison rg.ttf");

        return typeface;


    }

    public static String deviceID(Context context){

        String ANDROID_MOBILE_ID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return ANDROID_MOBILE_ID;

    }

}
