package com.example.bookinside;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPasswordActivity extends Fragment {

    final String url_ForgetPassword = "";
    EditText etEmail;
    TextView btnResetPassword;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.forget_password_layout, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = (EditText) Objects.requireNonNull(getView()).findViewById(R.id.et_email_forget_pass);
        btnResetPassword = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_reset_password_button);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = etEmail.getText().toString();

                NavHostFragment.findNavController(ForgetPasswordActivity.this)
                        .navigate(R.id.action_ForgetPassword_to_Login);

//                new ResetPassword().execute(Email);
            }
        });

    }

///////////////////////////////////////////////////////////////////
// reset password request

//    public class ResetPassword extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            String Email = strings[0];
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RequestBody formBody = new FormBody.Builder()
//                    .add("email",Email)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(url_ForgetPassword)
//                    .post(formBody)
//                    .build();
//
//            Response response = null;
//            try {
//                response = okHttpClient.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    String result = response.body().string();
//                    if (result.equalsIgnoreCase("login")) {
//                        NavHostFragment.findNavController(ForgetPasswordActivity.this)
//                                .navigate(R.id.action_forgetPassword_to_Login);
//                    } else {
//                        Toast.makeText(getContext(), //aici ar putea fi si getActivity() ...
//                                " Incorrect email !", Toast.LENGTH_LONG).show();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

}
