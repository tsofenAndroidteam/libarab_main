package com.libarab.michaelg.libarab.ListviewActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.libarab.michaelg.libarab.Item.ViewPagerActivity;
import com.libarab.michaelg.libarab.MainActivity;
import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//import com.example.misho.login.Item.ViewPagerActivity;

public class ListviewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG =this.getClass().getSimpleName();
    int index;
    private ArrayList<Book> bookList;
    private String myURL="any";
    private String _SEARCH_URL;
    private bookAdapter adapter;
    private String user;
    private String usertype;
    private String searchfor;
    private String fromyear;
    private String toyear;
    private String free_txt;
    private String txt;
    User newUser;
    private TextView resultstitle;
    private int totalhits;
    private String searchby;
    private FloatingActionButton nxt, prev;
    private ListView listview;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)


    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewactivity);
        listview = (ListView)findViewById(R.id.list);
        resultstitle = (TextView) findViewById(R.id.textView11);
        prev= (FloatingActionButton) findViewById(R.id.fab1);
        nxt= (FloatingActionButton) findViewById(R.id.fab);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>" + getResources().getString(R.string.listView_title) + "</font>"));

        newUser= (User) getIntent().getSerializableExtra("wholeUser");
        bookList = new ArrayList<Book>();
        Bundle extras = getIntent().getExtras();
         if(extras != null) {
             user = extras.getString("userid");
             usertype = extras.getString("usertype");
             myURL = extras.getString("Value1");
             fromyear=extras.getString("fromyear");
             toyear=extras.getString("toyear");
             txt=extras.getString("txt");
             _SEARCH_URL=extras.getString("searchurl");
             index=extras.getInt("index");
             searchby= extras.getString("searchby");
             searchfor=extras.getString("searchfor");
             free_txt = extras.getString("freetxt");
         }

        Log.v(TAG + "querey to sever :",myURL);
        new JSONAsyncTask().execute(myURL);
        adapter = new bookAdapter(getApplicationContext(), R.layout.row2, bookList);
        listview.setAdapter(adapter);
        resultstitle.setText("["+ Integer.toString(index)+"-"+ Integer.toString(index+24)+"]");

        if(index==0)
            prev.hide();
        nxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //perform action on click
                index=index+24;
                if(index>0)
                    prev.show();
                if(index>totalhits)
                    nxt.hide();

                Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId",    user)
                        // .appendQueryParameter("title",    title.getText().toString())
                        .appendQueryParameter(searchby,    txt)
                        .appendQueryParameter("fromyear", fromyear)
                        .appendQueryParameter("toyear",   toyear)
                        .appendQueryParameter("index", Integer.toString(index) )
                        .appendQueryParameter("freeTxt", free_txt)
                        .build();

                Log.v(TAG, builtUri.toString());
                // this is a intent to the same activity diffrent pages
                Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                //this is the link
                i.putExtra("Value1", builtUri.toString());
                i.putExtra("searchurl",_SEARCH_URL);
                i.putExtra("userid",user);
                i.putExtra("usertype", usertype);
                i.putExtra("txt", txt);
                i.putExtra("wholeUser",newUser);
                i.putExtra("fromyear", fromyear);
                i.putExtra("toyear", toyear);
                i.putExtra("index", index);
                i.putExtra("searchby", searchby);
                i.putExtra("type",searchfor);
                i.putExtra("searchfor", searchfor);
                i.putExtra("freetxt", free_txt);

                startActivity(i);
            }

        });
        //prev Button ClickListener

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                index=index-24;
                if(index==0)
                    prev.hide();
                Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId",    user)
                        // .appendQueryParameter("title",    title.getText().toString())
                        .appendQueryParameter(searchby,    txt)
                        .appendQueryParameter("fromyear", fromyear)
                        .appendQueryParameter("toyear",   toyear)
                        .appendQueryParameter("index", Integer.toString(index) )
                        .appendQueryParameter("freeTxt", free_txt)
                        .build();

                Log.v(TAG, builtUri.toString());
                Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                i.putExtra("Value1", builtUri.toString());
                i.putExtra("searchurl",_SEARCH_URL);
                i.putExtra("userid",user);
                i.putExtra("usertype", usertype);
                i.putExtra("txt", txt);
                i.putExtra("wholeUser",newUser);
                i.putExtra("fromyear", fromyear);
                i.putExtra("toyear", toyear);
                i.putExtra("index", index);
                i.putExtra("searchby", searchby);
                i.putExtra("type",searchfor);
                i.putExtra("searchfor", searchfor);
                i.putExtra("freetxt", free_txt);
                startActivity(i);

            }

        });

        // when user chosses the book

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Intent intent1=new Intent(getApplicationContext(),ViewPagerActivity.class);
                //this record id used by the ViewPagerActivity
                intent1.putExtra("recordId",bookList.get(position).getRecordid());
                intent1.putExtra("userId",user);
                intent1.putExtra("usertype", usertype);
                intent1.putExtra("user",newUser);
                intent1.putExtra("type",searchfor);
                //this weblink used by the ViewPagerActivity
                intent1.putExtra("webLink",bookList.get(position).getWeblink());
                // Remember that variable (user) is the private variable above that is sent by the search

                startActivity(intent1);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_name) {
            Intent intentToMain=new Intent(getApplicationContext() ,MainActivity.class);
            intentToMain.putExtra("user", newUser);
            startActivity(intentToMain);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


            @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    class JSONAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListviewActivity.this);
            dialog.setMessage(getResources().getString(R.string.l_wait));
            dialog.setTitle(R.string.connecting_server);
            dialog.show();
            dialog.setCancelable(false);
        }


        @Override
        protected String doInBackground(String... urls) {
            String total_hits = "";
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);
                    if (!jsono.has("result")) {
                        System.out.print(jsono.getString("result"));
                    }
                    String res = jsono.getString("result");
                    Log.v("results", res);
                    if (res.equals("false")) {
                        total_hits = "0";
                    }
                    total_hits = jsono.getString("totalHits");
                    Log.v("totalHits", (total_hits));


                    JSONArray jarray = jsono.getJSONArray("docs");

                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        Book currentbook = new Book();
                        currentbook.setRecordid(object.getString("recordId"));
                        String author = new String(object.getString("author").getBytes("ISO-8859-1"), "UTF-8");
                        author = author.replace("?","");
                        //currentbook.setTitle(new String(object.getString("title").getBytes("ISO-8859-1"), "UTF-8"));
                        String title = new String(object.getString("title").getBytes("ISO-8859-1"), "UTF-8");
                        title = title.replace("?","");
                        currentbook.setTitle(title);
                        currentbook.setCreationdate(new String(object.getString("creationdate").getBytes("ISO-8859-1"), "UTF-8"));
                        currentbook.setPublisher(new String(object.getString("publisher").getBytes("ISO-8859-1"), "UTF-8"));
                        currentbook.setAuthor(author);
                        currentbook.setType(searchfor);
                        //if there is a libweblink that means thats a sheet
                        //else is a book
                        if(object.has("libWebLink"))
                        {
                            currentbook.setWeblink(object.getString("page"));
                            currentbook.setThumbnail(object.getString("libWebLink"));
                        }
                        else
                        {
                            currentbook.setWeblink(object.getString("webLink"));
                            currentbook.setThumbnail(object.getString("thumbnail"));

                        }

                        currentbook.setSource(object.getString("source"));

                        //this solves the no results issue
                        if (!total_hits.equals("0"))
                            bookList.add(currentbook);


                    }
                    return total_hits;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return total_hits;
        }

        protected void onPostExecute(String total ) {
            dialog.cancel();
            totalhits= Integer.parseInt(total);
            adapter.notifyDataSetChanged();
            if (total == null) {
                Toast.makeText(getApplicationContext(), "Sorry , Unable to fetch data from server", Toast.LENGTH_LONG).show();
                finish();
            }
            if (total.equals("0")) {
                Toast.makeText(getApplicationContext(), R.string.no_results_found, Toast.LENGTH_LONG).show();
                finish();

            }
            if (totalhits <= 24) {
                nxt.hide();
            }
            if (index >= totalhits) {
                nxt.hide();
            }
        }
    }
}