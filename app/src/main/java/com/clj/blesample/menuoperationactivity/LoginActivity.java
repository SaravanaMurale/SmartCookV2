package com.clj.blesample.menuoperationactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clj.blesample.MainActivity;
import com.clj.blesample.R;
import com.clj.blesample.databasemanager.SqliteManager;

import static com.clj.blesample.utils.MathUtil.validatePassword;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUserName, loginPassword;
    private Button btnLogin;

    SqliteManager sqliteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteManager = new SqliteManager(LoginActivity.this);

        initView();
    }

    private void initView() {

        loginUserName = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        btnLogin = (Button) findViewById(R.id.loginBtn);

        loginUserName.addTextChangedListener(new MyTextWatcher(loginUserName));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user from SQLITE

                String userName = sqliteManager.validateLoginUser(loginUserName.getText().toString(), loginPassword.getText().toString());

                if (userName != null) {
                    Toast.makeText(LoginActivity.this, "Received UserName" + userName, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "You have entered wrong username or password" + userName, Toast.LENGTH_LONG).show();
                }


            }
        });

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

            String email = loginUserName.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            btnLogin.setEnabled(validatePassword(email) && validatePassword(password));

            if (btnLogin.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnLogin.setBackground(getDrawable(R.drawable.vessel_on));

                }
            } else if (!btnLogin.isEnabled()) {
                btnLogin.setEnabled(false);

            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}