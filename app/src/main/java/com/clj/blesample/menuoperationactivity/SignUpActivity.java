package com.clj.blesample.menuoperationactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.clj.blesample.R;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText signUpName, signUpEmail, signUpMobile, signUpPassword, signUpAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        /*signUpName.addTextChangedListener(new MyTextWatcher(signUpName));
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpMobile.addTextChangedListener(new MyTextWatcher(signUpMobile));
        signUpPassword.addTextChangedListener(new MyTextWatcher(signUpPassword));
        signUpAddress.addTextChangedListener(new MyTextWatcher(signUpAddress));
*/
    }
}