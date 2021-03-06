package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clj.blesample.R;
import com.clj.blesample.databasemanager.SqliteManager;

import static com.clj.blesample.utils.MathUtil.validateAddress;
import static com.clj.blesample.utils.MathUtil.validateEmail;
import static com.clj.blesample.utils.MathUtil.validateMobile;
import static com.clj.blesample.utils.MathUtil.validateName;
import static com.clj.blesample.utils.MathUtil.validatePassword;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText signUpName, signUpEmail, signUpMobile, signUpPassword, signUpAddress;

    SqliteManager sqliteManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();

        sqliteManager = new SqliteManager(SignUpActivity.this);

        signUpName.addTextChangedListener(new MyTextWatcher(signUpName));
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpMobile.addTextChangedListener(new MyTextWatcher(signUpMobile));
        signUpPassword.addTextChangedListener(new MyTextWatcher(signUpPassword));
        signUpAddress.addTextChangedListener(new MyTextWatcher(signUpAddress));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data in SqliteDatabase

                boolean status = sqliteManager.addUser(signUpName.getText().toString(), signUpEmail.getText().toString(), signUpMobile.getText().toString(), signUpPassword.getText().toString(), signUpAddress.getText().toString());
                if (status) {

                    signUpName.getText().clear();
                    signUpEmail.getText().clear();
                    signUpMobile.getText().clear();
                    signUpPassword.getText().clear();
                    signUpAddress.getText().clear();

                    Toast.makeText(SignUpActivity.this, "User Added Successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(SignUpActivity.this, "User Is Not Added Successfully", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void initView() {

        signUpName = (EditText) findViewById(R.id.signup_name);
        signUpEmail = (EditText) findViewById(R.id.signup_email);
        signUpMobile = (EditText) findViewById(R.id.signup_mobile);
        signUpPassword = (EditText) findViewById(R.id.signup_password);
        signUpAddress = (EditText) findViewById(R.id.signup_address);

        btnSignUp = (Button) findViewById(R.id.btnSignup);
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String name = signUpName.getText().toString().trim();
            String email = signUpEmail.getText().toString().trim();
            String mobileNum = signUpMobile.getText().toString().trim();
            String password = signUpPassword.getText().toString().trim();
            String address = signUpAddress.getText().toString().trim();

            btnSignUp.setEnabled(validateName(name) && validateEmail(email) && validateMobile(mobileNum) && validatePassword(password) && validateAddress(address));

            if (btnSignUp.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    btnSignUp.setBackground(getResources().getDrawable(R.drawable.card_view_border));

                }
            } else if (!btnSignUp.isEnabled()) {
                btnSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_border));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    }
}