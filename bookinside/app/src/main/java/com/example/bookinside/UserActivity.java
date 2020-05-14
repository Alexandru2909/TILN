package com.example.bookinside;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private String SERVER;
    JSONArray req;
    JSONArray sec_req;
    RequestQueue queue;


    public void getPeople(final View v){
        SERVER = global.getInstance().getIp() +"/get_people";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, SERVER, sec_req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    ArrayList<String> locations = new ArrayList<>();
                    ArrayList<String> books = new ArrayList<>();
                    ArrayList<String> contacts = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        
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

        String name = getIntent().getStringExtra("username");

        username.setText(name);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
