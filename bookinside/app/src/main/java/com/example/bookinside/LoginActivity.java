package com.example.bookinside;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LoginActivity extends Fragment {

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

//            view.findViewById(R.id.tv_login_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    NavHostFragment.findNavController(LoginActivity.this)
//                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                }
//            });
//
//        view.findViewById(R.id.tv_forget_pass_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(LoginActivity.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });

        view.findViewById(R.id.tv_register_here_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.action_Login_to_Register);
            }
        });
    }

}
