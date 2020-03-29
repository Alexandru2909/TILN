package com.example.bookinside;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookActivity extends AppCompatActivity {

    TextView bookTitle, bookAuthor, bookDescription;
    ImageView bookCover;
    ListView bookLocations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        bookTitle = (TextView) findViewById(R.id.book_title);
        bookAuthor = (TextView) findViewById(R.id.book_author);
        bookCover = (ImageView) findViewById(R.id.book_cover_image);
        bookDescription = (TextView) findViewById(R.id.book_description);
        bookLocations = (ListView) findViewById(R.id.locations_list);

        final String author = getIntent().getStringExtra("author");
        final String title = getIntent().getStringExtra("title");

//        System.out.println(author);

        bookAuthor.setText(author);
        bookTitle.setText(title);

    }
}
