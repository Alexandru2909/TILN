package com.example.bookinside;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class BookActivity extends AppCompatActivity {


    private static String SERVER = "http://192.168.8.105:3000/";
    HashMap<String,String> req = new HashMap<String,String>();
    RequestQueue queue;
    JSONArray res;
    String description;
    String photo_url;
    ArrayList<String> locations = new ArrayList<>();

    public void getBookInfo(){
        SERVER += "get_book";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    description = jsonObject.getString("descriere");
                    System.out.println(description);

                    for (int i = 0; i < jsonObject.getJSONArray("locatii").length(); i++){
                        locations.add(jsonObject.getJSONArray("locatii").get(i).toString());
                    }
                    System.out.println(locations);

                }
                catch (JSONException e){
                    System.out.println("_____________");
                    e.printStackTrace();
                    description = "There was something wrong";
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
                //do something

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        description = "Error communicating with server";
                        System.out.println("___________________No can do");
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                return req;
            }
        };

        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
        SERVER = "http://192.168.8.105:3000/";

    }


    RelativeLayout.LayoutParams layoutparams;
    RelativeLayout.LayoutParams layoutparams1;

    Dialog myDialog;

    TextView bookTitle, bookAuthor, bookDescription;
    ImageView bookCover;
    LinearLayout bookLocations;
    ImageView backBtn;

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
        backBtn = (ImageView) findViewById(R.id.back_from_book_page);


        final String author = getIntent().getStringExtra("author");
        final String title = getIntent().getStringExtra("title");


        // Test for the db connection and data retrieval
        queue = Volley.newRequestQueue(this);
        req.put("title", "Ion");
        req.put("author", "Liviu Rebreanu");
        getBookInfo();

        bookDescription.setText(description);

        bookAuthor.setText(author);
        bookTitle.setText(title);

        // Hardcoded data for location
//        String[] locations = new String[]{"Strada Jean de la Craiova", "Padurea Hoia Baciu", "Lacul Lebedelor"};

        System.out.println("------------------------------");
        for (int i = 0; i < locations.size(); i++) {
            bookLocations.addView(CreateBlankCardView(CreateCardView(locations.get(i))));
        }
        System.out.println(locations);
        System.out.println(description);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

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