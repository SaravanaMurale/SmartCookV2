package com.clj.blesample.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {

    public static Typeface getOctinPrisonFont(Context context){

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/octin prison rg.ttf");

        return typeface;


    }

}
