package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
               onBackPressed();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail()) {
                    String Email = etEmail.getText().toString();
                    openLoginActivity();
                    //        new ResetPassword().execute(Email);
                }
            }
        });
    }

    private boolean validateEmail(){
        String emailInput = etEmail.getText().toString().trim();
        if(emailInput.isEmpty()){
            etEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            etEmail.setError("Invalid Email");
            return false;
        }else{
            etEmail.setError(null);
            return true;
        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
