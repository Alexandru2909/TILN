package com.example.bookinside;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import static java.lang.System.*;

public class ViewBooksActivity extends AppCompatActivity {

//    ////////////////////////
    private String SERVER = global.getInstance().getIp() + "/get_user_books";
    JSONArray req;
    RequestQueue queue;
    String title;


    public void getUserBooks(){
//        SERVER += "/get_user_books";
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SERVER, req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        linearLayout.addView(CreateBlankCardView(CreateCardView(jsonObject.getString("titlu") + " - " + jsonObject.getString("autor"))));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                out.println("Volley"+ error.toString());
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
////////////////////
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);

//        SERVER = "http://192.168.8.105:3000";

    }

    public Boolean removedBook = false;

    public void removeBook(){
        SERVER = global.getInstance().getIp() + "/remove_book_from_list";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SERVER, req, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                       if(response.getJSONObject(0).get("response") == "true"){
                           removedBook = true;
                       }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                out.println("Volley"+ error.toString());
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
////////////////////
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);
    }

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

        ///TEST
        queue = Volley.newRequestQueue(this);
        req = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
            int flag = -1;
            obj.put("user", getIntent().getStringExtra("username"));
            switch (getIntent().getStringExtra("title")){
                case "Wishlist":
                    flag = 2;
                    break;
                case "Reading now":
                    flag = 1;
                    break;
                case "Your books":
                    flag = 0;
                    break;
                default:
                    break;
            }
            obj.put("flag", flag);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        req.put(obj);
        getUserBooks();


        btnAdd = (ImageView) findViewById(R.id.iv_add_book);
        linearLayout = (LinearLayout) findViewById(R.id.addbookhere);
        btnBack = (ImageView) findViewById(R.id.iv_arrow_from_view_books);
        sectionBook = (TextView) findViewById(R.id.book_section_title);

        final String name = getIntent().getStringExtra("username");
        title = getIntent().getStringExtra("title");
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

//        String[] books = {"Book 1 - Author 1", "Book 2 - Author 2", "Book 3 - Author 3",
//                "Book 4 - Author 4", "Book 5 - Author 5", "Book 6 - Author 6", "Book 7 - Author7",
//                "Book 8 - Author 8" , "Book 9 - Author 9" , "Book 10 - Author 10"};
//
//        List<CardView> cardViews = new ArrayList<>();
//
//        for (int i = 0; i < books.length; i++) {
//            linearLayout.addView(CreateBlankCardView(CreateCardView(books[i])));
//        }

    }

//    public void ShowPopUp(View v) {
//        myDialog.setContentView(R.layout.custompopup);
//
//        ImageView btnAdd, btnClose;
//        btnAdd = (ImageView) myDialog.findViewById(R.id.iv_add);
//        btnClose = (ImageView) myDialog.findViewById(R.id.iv_cancel);
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText bookTitle, bookAuthor;
//                bookTitle = (EditText) myDialog.findViewById(R.id.et_pop_book_name);
//                bookAuthor = (EditText) myDialog.findViewById(R.id.et_pop_author_name);
//
//                if ((bookTitle.getText().toString().trim().length() > 0)) {
//                    if ((bookAuthor.getText().toString().trim().length() > 0)) {
//
//                        final String newBook = bookTitle.getText().toString() + " - " + bookAuthor.getText().toString();
//
//                        linearLayout.addView(CreateBlankCardView(CreateCardView(newBook)));
//
//                    }
//                }
//                myDialog.dismiss();
//            }
//        });
//
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//
//        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.BLACK));
//        myDialog.show();
//    }

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
        final Context context;
        CardView cardview;
        final TextView textview;
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

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookActivity(textview.getText().toString().split(" - ")[0], textview.getText().toString().split(" - ")[1]);
            }
        });

        cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu bookmenu = new PopupMenu(getBaseContext(), view);
                bookmenu.getMenu().add("Remove from list");
//                switch (getIntent().getStringExtra("title")){
//                    case "Wishlist":
//                        bookmenu.getMenu().add("Move to Your books");
//                        bookmenu.getMenu().add("Move to Reading now");
//                        break;
//                    case "Reading now":
//                        bookmenu.getMenu().add("Move to Wishlist");
//                        bookmenu.getMenu().add("Move to Your books");
//                        break;
//                    case "Your books":
//                        bookmenu.getMenu().add("Move to Wishlist");
//                        bookmenu.getMenu().add("Move to Reading now");
//                        break;
//                    default:
//                        break;
//                }
                bookmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        int flag;
//                        Toast.makeText(getBaseContext(),(menuItem.getTitle()),Toast.LENGTH_SHORT);
//                        switch((String)(menuItem.getTitle())){
//
//                            case "Move to Reading now":
//                                Toast.makeText(getBaseContext(), textview.getText().toString().split(" - ")[0] + "was moved to " + menuItem.getTitle().toString().split(" to ")[1], Toast.LENGTH_SHORT).show();
//                                flag = 1;
//                                break;
//                            case "Move to Wishlist":
//                                Toast.makeText(getBaseContext(), textview.getText().toString().split(" - ")[0] + "was moved to " + menuItem.getTitle().toString().split(" to ")[1], Toast.LENGTH_SHORT).show();
//                                flag = 2;
//                            case "Move to Your books":
//                                Toast.makeText(getBaseContext(), textview.getText().toString().split(" - ")[0] + "was moved to " + menuItem.getTitle().toString().split(" to ")[1], Toast.LENGTH_SHORT).show();
//                                flag = 0;
//                            default:
//                                flag = -1;
//                                Toast.makeText(getBaseContext(), textview.getText().toString().split(" - ")[0] + "was removed from " + getIntent().getStringExtra("title"), Toast.LENGTH_SHORT).show();
//                        }
                        Toast.makeText(getBaseContext(), textview.getText().toString().split(" - ")[0] + "was removed from " + getIntent().getStringExtra("title"), Toast.LENGTH_SHORT).show();
                        req = new JSONArray();
                        JSONObject obj = new JSONObject();
                        try{
                            obj.put("user", getIntent().getStringExtra("username"));
                            obj.put("book", textview.getText());
                            switch(title) {
                                case "Wishlist":
                                    obj.put("list", 0);
                                    break;
                                case "Reading now":
                                    obj.put("list", 1);
                                    break;
                                case "Your books":
                                    obj.put("list", 2);
                                    break;
                                default:
                                    obj.put("list", 2);
                            }
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                        req.put(obj);
                        removeBook();
                        if(removedBook){
                            linearLayout.removeView(textview);
                        }
                        removedBook = false;
                        return true;
                    }
                });
                bookmenu.show();
                return true;
            }
        });

        return cardview;
    }

    public CardView CreateBlankCardView(CardView bookView) {
        Context context;
        CardView cardview;
        TextView textview;
        context = getApplicationContext();

        cardview = new CardView(context);

        layoutparams1 = new RelativeLayout.LayoutParams(
                Resources.getSystem().getDisplayMetrics().widthPixels,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams1);

        cardview.setRadius(0);

        cardview.setContentPadding(5, 10,5,10);

//        cardview.setCardBackgroundColor(Color.parseColor("#00000000"));

        cardview.setCardBackgroundColor(Color.TRANSPARENT);

        cardview.setBackgroundColor(Color.TRANSPARENT);

        cardview.setMaxCardElevation(0);

        cardview.addView(bookView);

        return cardview;
    }

    public void openBookActivity(String title, String author){
        Intent intent1 = new Intent(getBaseContext(), BookActivity.class);
        intent1.putExtra("title", title);
        intent1.putExtra("author", author);

//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
    }
}
