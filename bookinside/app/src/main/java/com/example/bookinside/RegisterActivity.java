package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    final String url_Register = "";

    EditText etEmail, etPhone, etUsername, etPassword, etConfirmPassword;
    TextView btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etUsername = (EditText) findViewById(R.id.et_username_register);
        etPassword = (EditText) findViewById(R.id.et_password_register);
        etConfirmPassword = (EditText)findViewById(R.id.et_confirm_pass_register);

        btnRegister = (TextView) findViewById(R.id.tv_register_button);
        btnLogin = (TextView) findViewById(R.id.tv_back_to_login_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = etEmail.getText().toString();
                String Phone = etPhone.getText().toString();
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();
                String ConfirmPassword = etConfirmPassword.getText().toString();

                openLoginActivity();
//              new RegisterUser().execute(Email,Phone,Username,Password, ConfirmPassword);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

    }
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
