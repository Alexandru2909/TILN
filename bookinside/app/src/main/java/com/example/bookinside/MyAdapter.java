package com.example.bookinside;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {

    //1.create a constructor
    //2.create a viewholder inner class
    //3.override 2 methods ( getView() and getCount() )
    ArrayList<BookType> books;

    String username;


    private static String SERVER = "http://192.168.8.105:3000/add_on";
    HashMap<String, String> req = new HashMap<>();
    RequestQueue queue;
    String res;
    SharedPreferences.Editor editor ;


    public void addBook() {
//        SERVER += "/add_on";
        StringRequest postRequest = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res = response;
                System.out.println(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                return req;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }


    public MyAdapter(Context context, int layout, ArrayList<BookType> books, String username) {
        super(context, layout);
        this.books = books;
        this.username = username;
    }

    public void update(ArrayList<BookType> results) {
        books = new ArrayList<>();
        books.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //inflate layout and pass
        //convertView - Null or not null
        View row;
        row = convertView;
        final ViewHolder viewHolder;

        queue = Volley.newRequestQueue(getContext());

        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.my_book_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_bookInfo = row.findViewById(R.id.tv_book_info);
            viewHolder.iv_star = row.findViewById(R.id.star);
            viewHolder.iv_patratel = row.findViewById(R.id.patratel);
            viewHolder.iv_go = row.findViewById(R.id.go);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }


        viewHolder.tv_bookInfo.setText(books.get(position).bookInfo);
        viewHolder.iv_star.setImageResource(books.get(position).starIcon);
        viewHolder.iv_patratel.setImageResource(books.get(position).patratelIcon);
        viewHolder.iv_go.setImageResource(books.get(position).goIcon);


        row.findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Hello there!");
                req.put("list", "0");
                req.put("user", username);
                req.put("title", viewHolder.tv_bookInfo.getText().toString());
                addBook();
            }
        });

        row.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("General Kenobi");
                req.put("list", "1");
                req.put("user", username);
                req.put("title", viewHolder.tv_bookInfo.getText().toString());
                addBook();
            }
        });

        row.findViewById(R.id.patratel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("You are a bold one");
                req.put("list", "2");
                req.put("user", username);
                req.put("title", viewHolder.tv_bookInfo.getText().toString());
                addBook();
            }
        });


        return row;
    }

    public class ViewHolder {
        TextView tv_bookInfo;
        ImageView iv_star, iv_patratel, iv_go;
    }
}
