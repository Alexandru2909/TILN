package com.example.bookinside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import org.w3c.dom.ls.LSOutput;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    final String url_Login = "";
    EditText etUsername, etPassword;
    TextView btnLogin, btnForgetPass, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.et_username);
        btnLogin = (TextView)findViewById(R.id.tv_login_button);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnForgetPass = (TextView) findViewById(R.id.tv_forget_pass_button);
        btnRegister = (TextView) findViewById(R.id.tv_register_here_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = etUsername.getText().toString();
                openDashActivity(Username);
            }
        });

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgetPassActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openRegisterActivity();
            }
        });
    }

    public void openDashActivity(String Username) {
        Intent intent1 = new Intent(getBaseContext(), DashboardActivity.class);
        intent1.putExtra("username", Username);

        startActivity(intent1);
    }
    public void openForgetPassActivity() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
