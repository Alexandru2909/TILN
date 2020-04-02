package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class FindBooksActivity extends AppCompatActivity {

    ArrayList<BookType> books = new ArrayList<>();
    ImageView btnBack;
    TextView sectionBook;
    SearchView mySearchView;
    ListView myList;

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

        String[] book_list = {"Book 1 - Author 1", "Book 2 - Author 2", "Book 3 - Author 3",
                "Book 4 - Author 4", "Book 5 - Author 5", "Book 6 - Author 6", "Book 7 - Author 7",
                "Book 8 - Author 8", "Book 9 - Author 9", "Book 10 - Author 10", "Book 11 - Author 11", "Book 12 - Author 12"};

        ArrayList<String> list = new ArrayList<String>();

        //create a custom adapter
        //1.List Item layout
        //2.List Item java class
        //3.Most Important - Custom Adapter extend ArrayAdapter class

        for (int i = 0; i < book_list.length; i++) {
            books.add(new BookType(book_list[i], R.drawable.bluestar, R.drawable.bluesquare, R.drawable.blackarrow));
        }

        myList.setAdapter(new MyAdapter(FindBooksActivity.this, R.layout.my_book_list, books));


//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list){
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view =super.getView(position, convertView, parent);
//
//                final TextView textView=(TextView) view.findViewById(android.R.id.text1);
//
//                textView.setTextColor(Color.WHITE);
//
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        openBookActivity(textView.getText().toString(), textView.getText().toString());
//                    }
//                });
//
//                return view;
//            }
//        };
//
//        myList.setAdapter(adapter);
//
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<BookType> results = new ArrayList<>();

                for (BookType x : books) {
                    if (x.bookInfo.contains(newText))
                        results.add(x);
                }

                ((MyAdapter) myList.getAdapter()).update(results);
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

    public void openBookActivity(String title, String author) {
        Intent intent = new Intent(getBaseContext(), BookActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);

//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
