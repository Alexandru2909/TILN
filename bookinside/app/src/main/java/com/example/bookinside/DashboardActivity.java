package com.example.bookinside;

import android.annotation.SuppressLint;
import android.content.Intent;
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


public class DashboardActivity extends Fragment {

    TextView btnLogOut,btnReadBooks,btnReadingBooks,btnWishlist,btnFindBooks,displayUsername;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
//        Bundle bundle = getArguments();
//
//        assert bundle != null;
//        String username = bundle.getString("username");
//        displayUsername = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_display_username);
//        displayUsername.setText(username);

        return inflater.inflate(R.layout.dashboard_layout, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogOut = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_log_out_button);
        btnReadBooks = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_read_books_button);
        btnReadingBooks = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_reading_books_button);
        btnWishlist = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_wishlist_button);
        btnFindBooks = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.tv_find_books_button);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DashboardActivity.this)
                        .navigate(R.id.action_Dashboard_to_Login);
            }
        });

        btnReadBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DashboardActivity.this)
                        .navigate(R.id.action_Dashboard_to_ViewBooks);
            }
        });

        btnReadingBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DashboardActivity.this)
                        .navigate(R.id.action_Dashboard_to_ViewBooks);
            }
        });

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DashboardActivity.this)
                        .navigate(R.id.action_Dashboard_to_ViewBooks);
            }
        });

        btnFindBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DashboardActivity.this)
                        .navigate(R.id.action_Dashboard_to_FindBooks);
            }
        });

    }


}
