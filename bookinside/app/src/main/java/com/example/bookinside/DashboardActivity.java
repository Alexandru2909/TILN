package com.example.bookinside;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {
    TextView btnLogOut,btnReadBooks,btnReadingBooks,btnWishlist,btnFindBooks,displayUsername;
    RequestQueue queue;
    JSONArray req;
    PendingIntent pendingIntent;
    Intent emailIntent;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    private String SERVER = global.getInstance().getIp() + "/get_notifs";
    public  void  GetNotifs() {
        //Define the endpoint called by funct

//        START LOADING
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//            return point of POST
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SERVER, req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        System.out.println("hello"+jsonObject.getString("email") + jsonObject.getString("name"));
                        emailIntent.setData(Uri.parse("mailto:"+jsonObject.getString("email")));
                        emailIntent.setType("text/plain");
                        showNotification("MappingBooks","Hey,you might know "+ jsonObject.getString("name")+ " from one of your books.\nTap here to email him!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                CLEAR LOADING
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Volley"+ error.toString());
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        //FOR LOCALHOST
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);
    }

    void showNotification(String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, emailIntent, 0);

         notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        queue = Volley.newRequestQueue(this);
        builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action_email)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        //        builder = new NotificationCompat.Builder(this,"0")
//                .setSmallIcon(R.drawable.ic_action_email)
//                .setContentTitle("My notification")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
        req = new JSONArray();
        JSONObject obj = new JSONObject();
        try{
            SharedPreferences sharedPref = getSharedPreferences("User_settings",0);
            obj.put("user",sharedPref.getString("User","user"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        req.put(obj);
        GetNotifs();
        displayUsername = (TextView)findViewById(R.id.tv_display_username);
        btnLogOut = (TextView)findViewById(R.id.tv_log_out_button);
        btnReadBooks = (TextView) findViewById(R.id.tv_read_books_button);
        btnReadingBooks = (TextView) findViewById(R.id.tv_reading_books_button);
        btnWishlist = (TextView) findViewById(R.id.tv_wishlist_button);
        btnFindBooks = (TextView) findViewById(R.id.tv_find_books_button);

        final String name = getIntent().getStringExtra("username");

        displayUsername.setText("Welcome " + name);

        btnReadBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Your books";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnReadingBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Reading now";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            String title = "Wishlist";
            @Override
            public void onClick(View view) {
                openViewBooksActivity(title,name);
            }
        });

        btnFindBooks.setOnClickListener(new View.OnClickListener() {
            String title = "Find a book";
            @Override
            public void onClick(View view) {
                openFindBooksActivity(title,name);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

    }

    private boolean backClickedTwice ;

    @Override
    public void onBackPressed(){
        if (backClickedTwice){
            super.onBackPressed();
        }
        this.backClickedTwice = true;
        Toast.makeText(this, "You are about to exit the app. Press again to exit.", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backClickedTwice = false;
            }
        }, 2000);
    }

    public void openViewBooksActivity(String title,String name) {
        Intent intent = new Intent(this, ViewBooksActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("username", name);
        startActivity(intent);
    }
    public void openFindBooksActivity(String title,String name) {
        Intent intent = new Intent(this, FindBooksActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
