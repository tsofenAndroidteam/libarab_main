package com.libarab.michaelg.libarab.ListviewActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.libarab.michaelg.libarab.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;


public class bookAdapter extends ArrayAdapter<Book> {
    private Context context;
    ArrayList<Book> bookList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public bookAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);
        this.context = context;
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
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.title = (TextView) v.findViewById(R.id.tv_title);
            holder.creationdate = (TextView) v.findViewById(R.id.tv_creationdate);
            holder.publisher = (TextView) v.findViewById(R.id.tv_publisher);
            holder.author = (TextView) v.findViewById(R.id.tv_author);
            holder.imageview =(ImageView) v.findViewById(R.id.book_item_image);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //holder.imageview.setImageResource(R.drawable.ic_launcher);
        String mytest ="http://icons.iconarchive.com/icons/icons8/ios7/256/Files-Jpg-icon.png";

        if((bookList.get(position).getType()).equals("book") || (bookList.get(position).getType()).equals("map"))
        {
            if(bookList.get(position).getThumbnail().contains("DeliveryManagerServlet")){// "http://books.google.com/books?bibkeys=ISBN:,OCLC:,LCCN:&jscmd=viewapi&callback=updateGBSCover"){
                holder.imageview.setVisibility(View.VISIBLE);
                Picasso
                        .with(this.context)
                        .load(bookList.get(position).getThumbnail())
                        .fit()
                        .into(holder.imageview);
                String test = bookList.get(position).getThumbnail();
                Log.e("this:", test);

            }else {
                holder.imageview.setVisibility(View.GONE);
            }


        }
        else {
            if(bookList.get(position).getWeblink().contains("DeliveryManagerServlet")) {
                holder.imageview.setVisibility(View.VISIBLE);
                Picasso
                        .with(this.context)
                        .load(bookList.get(position).getWeblink())
                        .fit()
                        .into(holder.imageview);
                String test = bookList.get(position).getWeblink();
                Log.e("this:", test);
            }else{
                holder.imageview.setVisibility(View.GONE);
            }
        }
        //new DownloadImageTask(holder.imageview).execute(bookList.get(position).getThumbnail());
        holder.title.setText(bookList.get(position).getTitle());
        //holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.creationdate.setText( bookList.get(position).getCreationdate());
        holder.publisher.setText(bookList.get(position).getPublisher());
        holder.author.setText(bookList.get(position).getAuthor());
        return v;

    }

    public static class ViewHolder {
        public ImageView imageview;
        public TextView title;
        //public TextView tvDescription;
        public TextView creationdate;
        public TextView publisher;
        public TextView author;
        //public TextView tvSpouse;
        //public TextView tvChildren;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}