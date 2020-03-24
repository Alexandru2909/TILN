package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class FindBooksActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView sectionBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);

        btnBack = (ImageView) findViewById(R.id.iv_arrow_from_find_books);
        sectionBook = (TextView) findViewById(R.id.book_find_section_title);

        final String name = getIntent().getStringExtra("username");
        final String title = getIntent().getStringExtra("title");

        sectionBook.setText(title);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashActivity(name);
            }
        });

    }

    public void openDashActivity(String name) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}
