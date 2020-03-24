package com.example.bookinside;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {
    TextView btnLogOut,btnReadBooks,btnReadingBooks,btnWishlist,btnFindBooks,displayUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        displayUsername = (TextView)findViewById(R.id.tv_display_username);
        btnLogOut = (TextView)findViewById(R.id.tv_log_out_button);
        btnReadBooks = (TextView) findViewById(R.id.tv_read_books_button);
        btnReadingBooks = (TextView) findViewById(R.id.tv_reading_books_button);
        btnWishlist = (TextView) findViewById(R.id.tv_wishlist_button);
        btnFindBooks = (TextView) findViewById(R.id.tv_find_books_button);

        final String name = getIntent().getStringExtra("username");

        displayUsername.setText("Welcome " + name);

        btnReadBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Your books";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnReadingBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Reading now";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            String title = "Wishlist";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnFindBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Find a book";
            @Override
            public void onClick(View view) {
                openFindBooksActivity(title,name);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }
    public void openViewBooksActivity(String title,String name) {
        Intent intent = new Intent(this, ViewBooksActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("username", name);
        startActivity(intent);
    }
    public void openFindBooksActivity(String title,String name) {
        Intent intent = new Intent(this, FindBooksActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
