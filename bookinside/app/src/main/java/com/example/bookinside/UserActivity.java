package com.example.bookinside;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class UserActivity extends AppCompatActivity {
    private String SERVER;
    JSONArray req;
    JSONArray sec_req;
    RequestQueue queue;


    public void getUserInfo(){
        SERVER = global.getInstance().getIp() +"/fetch_user";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, SERVER, req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                System.out.println("________________________________");
                System.out.print(response);

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mail.setText(jsonObject.getString("mail"));
                        for (int j = 0; j < jsonObject.getJSONArray("locations").length(); j++){
                            locations.addView(CreateCardView(jsonObject.getJSONArray("locations").get(j).toString()));
                        }
                        for (int j = 0; j < jsonObject.getJSONArray("books").length(); j++){
                            books.addView(CreateCardViewBook(jsonObject.getJSONArray("books").get(j).toString()));
                        }
                        for (int j = 0; j < jsonObject.getJSONArray("contact").length(); j++){
                            contacts.addView(CreateCardView(jsonObject.getJSONArray("contact").get(j).toString()));
                        }
                    }

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

    TextView username, mail;
    LinearLayout contacts, locations, books;
    ImageView btnBack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        username = (TextView) findViewById(R.id.user_username);
        mail = (TextView) findViewById(R.id.user_mail);
        contacts = (LinearLayout) findViewById(R.id.user_contacts);
        locations = (LinearLayout) findViewById(R.id.user_locations);
        books = (LinearLayout) findViewById(R.id.user_books);
        btnBack = (ImageView) findViewById(R.id.back_from_user_page);

        final String name = getIntent().getStringExtra("username");

        username.setText(name);
        req = new JSONArray();

        queue = Volley.newRequestQueue(this);
        JSONObject obj = new JSONObject();
        try{
            SharedPreferences sharedPref = getSharedPreferences("User_settings",0);
            obj.put("asking",sharedPref.getString("User","user"));

            obj.put("user", name);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        req.put(obj);

        getUserInfo();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public CardView CreateCardViewBook(final String text){
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
                openBookActivity(text.split("-")[0], text.split("-")[1]);
            }
        });

        return cardview;
    }

    public void openBookActivity(String title, String author) {
        Intent intent = new Intent(getBaseContext(), BookActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);

//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

//        cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

        return cardview;
    }

}
