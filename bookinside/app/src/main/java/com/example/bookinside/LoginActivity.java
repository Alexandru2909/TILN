package com.example.bookinside;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Fragment {

    final String url_Login = "";
    EditText etUsername, etPassword;
    TextView btnLogin, btnForgetPass, btnRegister;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_username);
        etPassword = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_password);
        btnLogin = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_login_button);
        btnForgetPass = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_forget_pass_button);
        btnRegister = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_register_here_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();

                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.action_Login_to_Dashboard);

//                new LoginUser().execute(Username,Password);
            }
        });

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.action_Login_to_forgetPassword);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.action_Login_to_Register);
            }
        });
    }

///////////////////////////////////////////////////////////////////
// login request

//    public class LoginUser extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            String Username = strings[0];
//            String Password = strings[1];
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RequestBody formBody = new FormBody.Builder()
//                    .add("username", Username)
//                    .add("user_password", Password)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(url_Login)
//                    .post(formBody)
//                    .build();
//
//            Response response = null;
//            try {
//                response = okHttpClient.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    String result = response.body().string();
//                    if (result.equalsIgnoreCase("login")) {
//                        NavHostFragment.findNavController(LoginActivity.this)
//                                .navigate(R.id.action_Login_to_Dashboard);
//                    } else {
//                        Toast.makeText(getContext(), //aici ar putea fi si getActivity() ...
//                                " Incorrect username or password !", Toast.LENGTH_LONG).show();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
    //}

}
