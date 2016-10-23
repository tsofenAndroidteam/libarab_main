package com.libarab.michaelg.libarab.Trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.libarab.michaelg.libarab.R;

import java.util.ArrayList;

/**
 * Created by Pcp on 02/10/2016.
 */

public class ListviewQAdapter extends ArrayAdapter<ItemsQ> {
    ArrayList<ItemsQ> bookList;
    LayoutInflater vi;
    int Resource;
    com.libarab.michaelg.libarab.Trivia.ListviewQAdapter.ViewHolder holder;

    public ListviewQAdapter(Context context, int resource, ArrayList<ItemsQ> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        bookList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new com.libarab.michaelg.libarab.Trivia.ListviewQAdapter.ViewHolder();
            v = vi.inflate(Resource, null);
            holder.title = (TextView) v.findViewById(R.id.textView2);
            holder.author = (TextView) v.findViewById(R.id.tv_title);

            holder.creationdate = (TextView) v.findViewById(R.id.tv_creationdate);
            holder.publisher = (TextView) v.findViewById(R.id.tv_publisher);
            holder.author = (TextView) v.findViewById(R.id.tv_author);
            holder.imageview =(ImageView) v.findViewById(R.id.book_item_image);
            v.setTag(holder);
        } else {
            holder = (com.libarab.michaelg.libarab.Trivia.ListviewQAdapter.ViewHolder) v.getTag();
        }
        holder.title.setText(bookList.get(position).getAuthor());
        //holder.author.setText(bookList.get(position).getAuthor());

        holder.creationdate.setText( bookList.get(position).getcreationDate());
        holder.publisher.setText(bookList.get(position).getpublisher());
        holder.author.setText(bookList.get(position).getAuthor());
        return v;

    }

    static class ViewHolder {
        public TextView title;
        //public TextView tvDescription;
        public TextView author;

        public TextView creationdate;
        public TextView publisher;
        public ImageView imageview;


    }

}
