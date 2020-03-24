package com.example.bookinside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import org.w3c.dom.ls.LSOutput;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    final String url_Login = "";
    EditText etUsername, etPassword;
    TextView btnLogin, btnForgetPass, btnRegister;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin = (TextView)findViewById(R.id.tv_login_button);
        btnForgetPass = (TextView) findViewById(R.id.tv_forget_pass_button);
        btnRegister = (TextView) findViewById(R.id.tv_register_here_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUsername() && validatePassword()) {
                    String Username = etUsername.getText().toString();
                    openDashActivity(Username);
                }
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

    private boolean validateUsername(){
        String usernameInput = etUsername.getText().toString().trim();
        if(usernameInput.isEmpty()){
            etUsername.setError("Field can't be empty");
            return false;
        } else if(!USERNAME_PATTERN.matcher(usernameInput).matches()){
            etUsername.setError("Username can not contain at least 5 letters");
            return false;
        }else{
            etUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = etPassword.getText().toString().trim();
        if(passwordInput.isEmpty()){
            etPassword.setError("Field can't be empty");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            etPassword.setError("Password is too weak");
            return false;
        }else{
            etPassword.setError(null);
            return true;
        }
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
