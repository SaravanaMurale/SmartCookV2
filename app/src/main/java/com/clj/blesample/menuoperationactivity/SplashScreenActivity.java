package com.clj.blesample.menuoperationactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.clj.blesample.MainActivity;
import com.clj.blesample.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new SplashDownCountDown(3000, 1000).start();

    }

    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

            // user_id = PreferencesUtil.getValueString(SplashScreen.this, PreferenceUtil.USERID);


            startActivity(intent);
            finish();

        }
    }
}