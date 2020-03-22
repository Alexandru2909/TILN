package com.example.bookinside;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Fragment {

//    private EditText mPswEditText;
//    private EditText mConfirmPassEditText;
//    private TextView mToggleTextView;
//    private TextView mConfirmPassToggleTextView;

    //////////////////////////////////////////////////////////////
    //register request

    final String url_Register = "";

    EditText etEmail, etPhone, etUsername, etPassword, etConfirmPassword;
    TextView btnLogin, btnRegister;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_layout, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_email);
        etPhone = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_phone);
        etUsername = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_username_register);
        etPassword = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_password_register);
        etConfirmPassword = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_confirm_pass_register);

        btnRegister = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_register_button);
        btnLogin = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_back_to_login_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = etEmail.getText().toString();
                String Phone = etPhone.getText().toString();
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();
                String ConfirmPassword = etConfirmPassword.getText().toString();

                NavHostFragment.findNavController(RegisterActivity.this)
                        .navigate(R.id.action_Register_to_Login);

//                new RegisterUser().execute(Email,Phone,Username,Password, ConfirmPassword);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterActivity.this)
                        .navigate(R.id.action_Register_to_Login);
            }
        });

    }

//        public class RegisterUser extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            String Email = strings[0];
//            String Phone = strings[1];
//            String Username = strings[2];
//            String Password = strings[3];
//            String ConfirmPassword = strings[4];
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RequestBody formBody = new FormBody.Builder()
//                    .add("email", Email)
//                    .add("phone", Phone)
//                    .add("username", Username)
//                    .add("password", Password)
//                    .add("confirmPassword", ConfirmPassword)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(url_Register)
//                    .post(formBody)
//                    .build();
//
//            Response response = null;
//            try {
//                response = okHttpClient.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    String result = Objects.requireNonNull(response.body()).string();
//                    if (result.equalsIgnoreCase("register")) {
//                        NavHostFragment.findNavController(RegisterActivity.this)
//                                .navigate(R.id.action_Register_to_Login);
//                    } else {
//                        Toast.makeText(getContext(), //aici ar putea fi si getActivity() ...
//                                "Incorrect fields ?", Toast.LENGTH_LONG).show();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
