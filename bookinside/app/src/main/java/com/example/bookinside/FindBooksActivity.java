package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FindBooksActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView sectionBook;
    SearchView mySearchView;
    ListView myList;
    LinearLayout linearLayout;
    RelativeLayout.LayoutParams layoutparams;
    RelativeLayout.LayoutParams layoutparams1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);

        btnBack = (ImageView) findViewById(R.id.iv_arrow_from_find_books);
        sectionBook = (TextView) findViewById(R.id.book_find_section_title);
        mySearchView = (SearchView) findViewById(R.id.search_book);
        myList = (ListView) findViewById(R.id.list_with_books);

        final String name = getIntent().getStringExtra("username");
        final String title = getIntent().getStringExtra("title");

        sectionBook.setText(title);

        String[] books = {"Book 1 - Author 1", "Book 2 - Author 2", "Book 3 - Author 3",
                "Book 4 - Author 4", "Book 5 - Author 5", "Book 6 - Author 6", "Book 7 - Author 7",
                "Book 8 - Author 8" , "Book 9 - Author 9" , "Book 10 - Author 10", "Book 11 - Author 11", "Book 12 - Author 12"};

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < books.length; i++) {
//            myList.addView(CreateBlankCardView(CreateCardView(books[i])));
            list.add(books[i]);

        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                final TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBookActivity(textView.getText().toString(), textView.getText().toString());
                    }
                });

                return view;
            }
        };

        myList.setAdapter(adapter);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void openDashActivity(String name) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void openBookActivity(String title, String author){
        Intent intent = new Intent(getBaseContext(), BookActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);

//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
