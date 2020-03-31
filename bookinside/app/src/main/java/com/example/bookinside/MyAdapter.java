package com.example.bookinside;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {

    //1.create a constructor
    //2.create a viewholder inner class
    //3.override 2 methods ( getView() and getCount() )
    ArrayList<BookType> books;

    public MyAdapter(Context context, int layout, ArrayList<BookType> books) {
        super(context, layout);
        this.books = books;
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
        ViewHolder viewHolder;
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

        return row;
    }

    public class ViewHolder {
        TextView tv_bookInfo;
        ImageView iv_star, iv_patratel, iv_go;
    }
}
