package com.example.bookinside;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private boolean backClickedTwice ;

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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
