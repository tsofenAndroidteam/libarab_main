package com.libarab.michaelg.libarab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class ImageAdapter extends BaseAdapter
{
    private Context context;
    private final String[] mobileValues;

    public ImageAdapter(Context context, String[] mobileValues)
    {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View gridView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
        {
            gridView = inflater.inflate(R.layout.gridviewadapter, null);
        }
        else
        {
            gridView = convertView;
        }

        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_name);
        textView.setText(mobileValues[position]);

        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

        String mobile = mobileValues[position];

        if (mobile.equals("Windows"))
        {
            Picasso.with(context).load("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4069965").into(imageView);
            //new DownloadImageTask(imageView).execute("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4069965");
        }
        else if (mobile.equals("iOS"))
        {
            Picasso.with(context).load("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4070181").into(imageView);
            //new DownloadImageTask(idmageView).execute("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4070181");
        }
        else if (mobile.equals("Blackberry"))
        {
            Picasso.with(context).load("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4069455").into(imageView);
            //new DownloadImageTask(imageView).execute("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4069455");
        }
        else
        {
            Picasso.with(context).load("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4070202").into(imageView);
            //new DownloadImageTask(imageView).execute("http://rosetta.nli.org.il/delivery/DeliveryManagerServlet?dps_func=thumbnail&dps_pid=IE4070202");
        }

        return gridView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String imageLink = urls[0];
            Bitmap mIcon11 = null;
            try
            {
                InputStream in = new java.net.URL(imageLink).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getCount()
    {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

}