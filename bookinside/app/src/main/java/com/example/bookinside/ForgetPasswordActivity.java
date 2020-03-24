package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {
    final String url_ForgetPassword = "";
    EditText etEmail;
    TextView btnResetPassword;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etEmail = (EditText) findViewById(R.id.et_email_forget_pass);
        btnResetPassword = (TextView) findViewById(R.id.tv_reset_password_button);
        btnBack = (ImageView) findViewById(R.id.iv_arrow);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openLoginActivity();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = etEmail.getText().toString();
                openLoginActivity();
//              new ResetPassword().execute(Email);
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
