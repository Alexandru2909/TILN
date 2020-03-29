package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewBooksActivity extends AppCompatActivity {

//    ////////////////////////
private static final String SERVER = "http://192.168.1.2:8081/";

    private TextView tvServerResponse;
    public class HttpGetRequest extends AsyncTask<Void, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(Void... params){
            String result;
            String inputLine;

            try {
                // connect to the server
                URL myUrl = new URL(SERVER);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();

                // get the string from the input stream
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();

            } catch(IOException e) {
                e.printStackTrace();
                result = "error";
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            tvServerResponse.setText(result);
        }
    }
//    ////////////////////////

    RelativeLayout.LayoutParams layoutparams;
    RelativeLayout.LayoutParams layoutparams1;

    Dialog myDialog;

    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    ListView listView;

    ImageView btnBack, btnAdd;
    TextView sectionBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        myDialog = new Dialog(this);

        btnAdd = (ImageView) findViewById(R.id.iv_add_book);
        linearLayout = (LinearLayout) findViewById(R.id.addbookhere);
        btnBack = (ImageView) findViewById(R.id.iv_arrow_from_view_books);
        sectionBook = (TextView) findViewById(R.id.book_section_title);

        final String name = getIntent().getStringExtra("username");
        final String title = getIntent().getStringExtra("title");

        //Sample test code
        tvServerResponse = findViewById(R.id.book_section_title);
        HttpGetRequest request = new HttpGetRequest();
        request.execute();
        //End of sample test code
        sectionBook.setText(title);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openDashActivity(name);
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindBookActivity();
            }
        });

        String[] books = {"Book 1 - Author 1", "Book 2 - Author 2", "Book 3 - Author 3",
                "Book 4 - Author 4", "Book 5 - Author 5", "Book 6 - Author 6", "Book 7 - Author7",
                "Book 8 - Author 8" , "Book 9 - Author 9" , "Book 10 - Author 10"};

        List<CardView> cardViews = new ArrayList<>();

        for (int i = 0; i < books.length; i++) {
            linearLayout.addView(CreateCardView(books[i]));
            linearLayout.addView(CreateBlankCardView());
        }

    }

    public void ShowPopUp(View v) {
        myDialog.setContentView(R.layout.custompopup);

        ImageView btnAdd, btnClose;
        btnAdd = (ImageView) myDialog.findViewById(R.id.iv_add);
        btnClose = (ImageView) myDialog.findViewById(R.id.iv_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText bookTitle, bookAuthor;
                bookTitle = (EditText) myDialog.findViewById(R.id.et_pop_book_name);
                bookAuthor = (EditText) myDialog.findViewById(R.id.et_pop_author_name);

                if ((bookTitle.getText().toString().trim().length() > 0)) {
                    if ((bookAuthor.getText().toString().trim().length() > 0)) {

                        final String newBook = bookTitle.getText().toString() + " - " + bookAuthor.getText().toString();

                        linearLayout.addView(CreateCardView(newBook));
                        linearLayout.addView(CreateBlankCardView());

                    }
                }
                myDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        myDialog.show();
    }

    public void openDashActivity(String name) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void openFindBookActivity() {
        Intent intent = new Intent(this, FindBooksActivity.class);
        startActivity(intent);
    }

    public CardView CreateCardView(String bookDetails) {
        Context context;
        CardView cardview;
        TextView textview;
        context = getApplicationContext();

        cardview = new CardView(context);

        layoutparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams);

        cardview.setRadius(55); //55 - curbat

        cardview.setPadding(10, 35, 10, 50);

        cardview.setCardBackgroundColor(Color.parseColor("#E9B7B5F4"));

        cardview.setMaxCardElevation(0);

        textview = new TextView(context);

        textview.setLayoutParams(layoutparams);

        textview.setText(bookDetails);

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        textview.setTextColor(Color.BLACK);

        textview.setPadding(55, 35, 25, 15);

        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

        cardview.setContentPadding(10, 35, 10, 40);

        return cardview;
    }

    public CardView CreateBlankCardView() {
        Context context;
        CardView cardview;
        TextView textview;
        context = getApplicationContext();

        cardview = new CardView(context);

        layoutparams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                40
        );

        cardview.setLayoutParams(layoutparams1);

        cardview.setRadius(0);

//        cardview.setPadding(55, 35, 25, 15);

        cardview.setCardBackgroundColor(Color.parseColor("#00000000"));

        cardview.setMaxCardElevation(50);

        return cardview;
    }
}
