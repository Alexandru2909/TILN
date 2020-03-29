package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern USERNAME_PATTERN = Pattern.compile(".{5,}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
//                                                                "(?=.*[0-9])" +
//                                                                "(?=.*[a-z])" +
//                                                                "(?=.*[A-Z])" +
            "(?=.*[a-zA-Z])" +
//                                                                "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");
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
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_pass_register);

        btnRegister = (TextView) findViewById(R.id.tv_register_button);
        btnLogin = (TextView) findViewById(R.id.tv_back_to_login_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() && validatePassword() && validateConfirmPassword()) {
                    String Email = etEmail.getText().toString();
                    String Phone = etPhone.getText().toString();
                    String Username = etUsername.getText().toString();
                    String Password = etPassword.getText().toString();
                    String ConfirmPassword = etConfirmPassword.getText().toString();

                    if (Password.equals(ConfirmPassword)) {
                        openLoginActivity();
//              new RegisterUser().execute(Email,Phone,Username,Password, ConfirmPassword);
                    }

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    private boolean validateEmail() {
        String emailInput = etEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            etEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Invalid Email");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String phoneInput = etPhone.getText().toString().trim();
        if (phoneInput.isEmpty()) {
            etPhone.setError("Field can't be empty");
            return false;
        } else if (!Patterns.PHONE.matcher(phoneInput).matches()) {
            etPhone.setError("Invalid Phone Number");
            return false;
        } else {
            etPhone.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = etUsername.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            etUsername.setError("Field can't be empty");
            return false;
        } else if (!USERNAME_PATTERN.matcher(usernameInput).matches()) {
            etUsername.setError("Username can not contain at least 5 letters");
            return false;
        } else {
            etUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = etPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            etPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPassword.setError("Password is too weak");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = etConfirmPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            etConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etConfirmPassword.setError("Password is too weak");
            return false;
        } else {
            etConfirmPassword.setError(null);
            return true;
        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
