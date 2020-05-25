package com.example.bookinside;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private String SERVER = global.getInstance().getIp() + "/login";
    HashMap<String, String> req = new HashMap<>();
    RequestQueue queue;
    String res;
    SharedPreferences.Editor editor ;
    private FusedLocationProviderClient fusedLocationClient;

    public void LogIn() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res = response;
                System.out.println(response);
                if (response.equals("True")){
                    editor.putString("User", etUsername.getText().toString());
                    editor.putString("Password", etPassword.getText().toString());
                    editor.commit();
                    openDashActivity();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    res = "False";
                    error.printStackTrace();
                }
            }){
            @Override
            protected Map<String, String> getParams() {
                return req;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }


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

        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SharedPreferences sharedPref = getSharedPreferences("User_settings", 0);
        editor = sharedPref.edit();


        btnLogin = (TextView)findViewById(R.id.tv_login_button);
        btnForgetPass = (TextView) findViewById(R.id.tv_forget_pass_button);
        btnRegister = (TextView) findViewById(R.id.tv_register_here_button);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            req.put("longitude",String.valueOf(location.getLongitude()));
                            req.put("latitude",String.valueOf(location.getLatitude()));
                        }
                        else{
                            System.out.println("can't get locations..");
                        }
                    }
                });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUsername() && validatePassword()) {
                    checkUser();
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

    private boolean backClickedTwice;

    @Override
    public void onBackPressed(){
        if (backClickedTwice){
            super.onBackPressed();
        }
        this.backClickedTwice = true;
        Toast.makeText(this, "You are about to exit the app. Press again to exit.", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backClickedTwice = false;
            }
        }, 2000);
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

    public void checkUser() {
        queue = Volley.newRequestQueue(this);
        req.put("user", etUsername.getText().toString());
        req.put("password", etPassword.getText().toString());
        LogIn();
//        System.out.println(res);
    }
    public void openDashActivity(){
        Intent intent1 = new Intent(getBaseContext(), DashboardActivity.class);
        SharedPreferences sharedPref = getSharedPreferences("User_settings", 0);
        intent1.putExtra("username", sharedPref.getString("User","user"));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
