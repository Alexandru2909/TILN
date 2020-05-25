package com.example.bookinside;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class BookActivity extends AppCompatActivity {


    private String SERVER = global.getInstance().getIp() + "/get_book";
    JSONArray req;
    JSONArray sec_req;
    RequestQueue queue;
    RequestQueue queue2;
    JSONArray res;
    String description;
    String photo_url;
    ArrayList<String> locations = new ArrayList<>();

    public void getBookInfo(){

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SERVER, req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    bookDescription.setText(jsonObject.getString("descriere"));
                    for (int i = 0; i < jsonObject.getJSONArray("locatii").length(); i++){
                        bookLocations.addView(CreateBlankCardView(CreateCardView(jsonObject.getJSONArray("locatii").get(i).toString())));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Volley"+ error.toString());
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
////////////////////
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);

        //SERVER = "http://192.168.8.105:3000";

    }

//    ArrayList<String> pers;

    public void getPeople(final View v){
        SERVER = global.getInstance().getIp() +"/get_people";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, SERVER, sec_req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
        SharedPreferences sharedPref = getSharedPreferences("User_settings",0);

                try {
                    ArrayList<String> pers = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(!jsonObject.getString("name").equals(sharedPref.getString("User","user")));
                            pers.add(jsonObject.getString("name"));
                    }

//                    for (int i = 0; i < jsonObject.getJSONArray("people").length(); i++) {
//                    }
                    ShowPopUp(v, pers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley"+ error.toString());
            }
        });

        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

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
        req = new JSONArray();

        final String author = getIntent().getStringExtra("author");
        final String title = getIntent().getStringExtra("title");


        // Test for the db connection and data retrieval
        queue = Volley.newRequestQueue(this);
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", title);
            obj.put("author", author);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        req.put(obj);
        getBookInfo();

//        bookDescription.setText(description);

        bookAuthor.setText(author);
        bookTitle.setText(title);

        // Hardcoded data for location
//        String[] locations = new String[]{"Strada Jean de la Craiova", "Padurea Hoia Baciu", "Lacul Lebedelor"};


//        System.out.println(locations);
//        System.out.println(description);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

//        queue2 = Volley.newRequestQueue(this);

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

    public CardView CreateCardView(final String text){
        Context context;
        final CardView cardview;
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
//                queue2 = Volley.newRequestQueue(getBaseContext());

                sec_req = new JSONArray();

                JSONObject obj = new JSONObject();
                try{
                    obj.put("location", textview.getText().toString());
                    System.out.println(textview.getText().toString());
                    System.out.println("__________________");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                System.out.println(obj);
                sec_req.put(obj);
                getPeople(view);
//                ShowPopUp(view);

            }
        });

        return cardview;
    }

    public void ShowPopUp(View v, ArrayList<String> persons) {
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

//        String[] persons = new String[]{"Ion Ion", "Maria Aioanei", "Gheorghe Popa"};

//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < persons.size(); ++i) {
//            list.add(persons.get(i));
//        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, persons);

//        System.out.println("_____________________________");
//        System.out.println(personsListView);
        personsListView.setAdapter(adapter);
        personsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openUserActivity((String) adapterView.getItemAtPosition(i));
                myDialog.dismiss();
                }
        });
    }

    public void openUserActivity(String name) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("username", name);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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