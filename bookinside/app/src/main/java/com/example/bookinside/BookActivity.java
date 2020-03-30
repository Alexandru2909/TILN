package com.example.bookinside;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class BookActivity extends AppCompatActivity {

    RelativeLayout.LayoutParams layoutparams;
    RelativeLayout.LayoutParams layoutparams1;

    Dialog myDialog;

    TextView bookTitle, bookAuthor, bookDescription;
    ImageView bookCover;
    LinearLayout bookLocations;

    ListView personsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        setContentView(R.layout.activity_book_page);

        bookTitle = (TextView) findViewById(R.id.book_title);
        bookAuthor = (TextView) findViewById(R.id.book_author);
        bookCover = (ImageView) findViewById(R.id.book_cover_image);
        bookDescription = (TextView) findViewById(R.id.book_description);
        bookLocations = (LinearLayout) findViewById(R.id.locations_list);

        final String author = getIntent().getStringExtra("author");
        final String title = getIntent().getStringExtra("title");

//        System.out.println(author);

        bookAuthor.setText(author);
        bookTitle.setText(title);


        String[] locations = new String[]{"Strada Jean de la Craiova", "Padurea Hoia Baciu", "Lacul Lebedelor"};


        for (int i = 0; i < locations.length; i++) {
            bookLocations.addView(CreateBlankCardView(CreateCardView(locations[i])));
        }




//        bookLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
//                        .show();
//            }
//        });

    }

    public CardView CreateCardView(String text){
        Context context;
        CardView cardview;
        final TextView textview;
        context = getApplicationContext();

        cardview = new CardView(context);

        layoutparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams);

        cardview.setMaxCardElevation(0);

        textview = new TextView(context);

        textview.setLayoutParams(layoutparams);

        textview.setText(text);

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        textview.setTextColor(Color.WHITE);

        textview.setGravity(Gravity.CENTER);

        cardview.setCardBackgroundColor(Color.TRANSPARENT);

        cardview.setBackgroundColor(Color.TRANSPARENT);

        cardview.addView(textview);

        cardview.setContentPadding(10, 10, 10, 10);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopUp(view);

            }
        });

        return cardview;
    }

    public CardView CreateBlankCardView(CardView bookView) {
        Context context;
        CardView cardview;
        context = getApplicationContext();

        cardview = new CardView(context);

        layoutparams1 = new RelativeLayout.LayoutParams(
                Resources.getSystem().getDisplayMetrics().widthPixels,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams1);

        cardview.setContentPadding(5, 10,5,10);

        cardview.setCardBackgroundColor(Color.TRANSPARENT);

        cardview.setBackgroundColor(Color.TRANSPARENT);

        cardview.setMaxCardElevation(0);

        cardview.addView(bookView);

        return cardview;
    }

    public void ShowPopUp(View v) {
        myDialog.setContentView(R.layout.custompopup);

        ImageView btnAdd, btnClose;
        btnClose = (ImageView) myDialog.findViewById(R.id.iv_cancel);
        personsListView = (ListView) myDialog.findViewById(R.id.people_list_view);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        myDialog.show();

        String[] persons = new String[]{"Ion Ion", "Maria Aioanei", "Gheorghe Popa"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < persons.length; ++i) {
            list.add(persons[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);

        System.out.println("_____________________________");
        System.out.println(personsListView);
        personsListView.setAdapter(adapter);

    }



    private static class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        private StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            try {
                String item = getItem(position);
                return mIdMap.get(item);
            }
            catch (NullPointerException e){
                return 0;
            }
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}