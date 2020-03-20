package com.example.bookinside;

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

public class RegisterActivity extends Fragment {

    private EditText mPswEditText;
    private TextView mToggleTextView;
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

//        mPswEditText = view.findViewWithTag(R.id.et_password_register);
//        mToggleTextView = view.findViewById(R.id.tv_toggle_show_pass);
//
//        mToggleTextView.setVisibility(View.VISIBLE);
        //metoda 1
//        mToggleTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    if(mToggleTextView.getText().toString().equals("Show")){
//                        mToggleTextView.setText("Hide");
//                        mPswEditText.setTransformationMethod(null);
//                    } else {
//                        mToggleTextView.setText("Show");
//                        mPswEditText.setTransformationMethod(new PasswordTransformationMethod());
//                    }
//                }
//
//        });

        //metoda 2
//        mPswEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

//        mPswEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(mPswEditText.getText().length() > 0) {
//                    mToggleTextView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    mToggleTextView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        //metoda 3
//        mToggleTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mToggleTextView.getText() == "SHOW") {
//                    mToggleTextView.setText("HIDE");
//                    mPswEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    mPswEditText.setSelection(mPswEditText.length());
//                }
//                else {
//                    mToggleTextView.setText("SHOW");
//                    mPswEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    mPswEditText.setSelection(mPswEditText.length());
//                }
//            }
//        });

        view.findViewById(R.id.tv_have_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterActivity.this)
                        .navigate(R.id.action_Register_to_Login);
            }
        });

    }
}
