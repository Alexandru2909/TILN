package com.example.bookinside;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindBooksActivity extends AppCompatActivity {

    ArrayList<BookType> books = new ArrayList<>();
    ImageView btnBack;
    TextView sectionBook;
    SearchView mySearchView;
    ListView myList;

    //    ////////////////////////
    private static String SERVER = "http://192.168.8.105:3000";
    RequestQueue queue;
    JSONArray req;

    public  void  GetBooks() {
        //Define the endpoint called by funct
        SERVER += "/get_books";
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
                            books.add(new BookType(jsonObject.getString("titlu") + " - " + jsonObject.getString("autor"), R.drawable.star, R.drawable.patratel, R.drawable.go));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                myList.setAdapter(new MyAdapter(FindBooksActivity.this, R.layout.my_book_list, books));
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("HERE!!!");
        setContentView(R.layout.activity_find_books);

        queue = Volley.newRequestQueue(this);
        btnBack = (ImageView) findViewById(R.id.iv_arrow_from_find_books);
        sectionBook = (TextView) findViewById(R.id.book_find_section_title);
        mySearchView = (SearchView) findViewById(R.id.search_book);
        myList = (ListView) findViewById(R.id.list_with_books);
        req = new JSONArray();

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

//        for (int i = 0; i < book_list.length; i++) {
//            books.add(new BookType(book_list[i], R.drawable.star, R.drawable.patratel, R.drawable.go));
//        }
//
//        myList.setAdapter(new MyAdapter(FindBooksActivity.this, R.layout.my_book_list, books));


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
                JSONObject obj = new JSONObject();
                try{
                    obj.put("title",query);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                req.put(obj);
                GetBooks();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<BookType> results = new ArrayList<>();

                for (BookType x : books) {
                    if (x.bookInfo.contains(newText))
                        results.add(x);
                }
                if (results != null && ((MyAdapter) myList.getAdapter()!=null))
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
