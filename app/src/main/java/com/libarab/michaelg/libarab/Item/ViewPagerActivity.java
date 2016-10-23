package com.libarab.michaelg.libarab.Item;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libarab.michaelg.libarab.Fragments.Params;
import com.libarab.michaelg.libarab.Item.zoomable.ZoomableDraweeView;
import com.libarab.michaelg.libarab.ListviewActivity.Book;
import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.Trivia.AddQuestion;
import com.libarab.michaelg.libarab.User;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ViewPagerActivity extends AppCompatActivity {

    private final String TAG =this.getClass().getSimpleName();

    private final String _ADD_FAV_URL_="http://52.29.110.203:8080/LibArab/favorites/addToFavorites?";
    private final String _REMOVE_FAV_URL_="http://52.29.110.203:8080/LibArab/favorites/removeFromFavorites?";
    private final String GUEST_USER="0";
    GalleryAdapter myadapter;
    List<String> pagesStr = new ArrayList<String>();
    private ArrayList<Integer> favoritePages;
    private ArrayList<com.libarab.michaelg.libarab.favorites.bean.Book> bookList;

    private String ID = "NNL_ALEPH003157499";
    private String userId= "100";
    private String weblink;
    private String usertype;
    private ViewItemTask mAuthTask = null;

    private ImageView addbutton;
    MaterialFavoriteButton lovebutton;
    boolean isJump = false;
    ViewPager vpGallery;
    EditText etchange;
    TextView tvGoto;
    int isNoPages =0;
    private Book book;
    private String type;
    ZoomableDraweeView view2;
    String stringnumber;


    public void bookinfo(View v){

        Intent bookinfoactivity = new Intent(this,BookinfoActivity.class);
        bookinfoactivity.putExtra("creationdate" ,book.getCreationdate());
        bookinfoactivity.putExtra("title"        ,book.getTitle());
        bookinfoactivity.putExtra("author"       ,book.getAuthor());
        bookinfoactivity.putExtra("webLink"      ,book.getWeblink());
        bookinfoactivity.putExtra("publisher"    ,book.getPublisher());
        bookinfoactivity.putExtra("source"       ,book.getSource());
        startActivity(bookinfoactivity);
    }

    public void changepage(View v){

         stringnumber = etchange.getText().toString();

        if (!(stringnumber.matches(""))) {
            vpGallery.setCurrentItem(Integer.parseInt(stringnumber) - 1);
            if(Integer.parseInt(stringnumber)>pagesStr.size()){
                tvGoto.setText(pagesStr.size() + "/" + pagesStr.size());
            }
            else {
                tvGoto.setText(stringnumber + "/" + pagesStr.size());
            }
            isJump = true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        setContentView(R.layout.activity_view_pager);
        //set love button
        bookList = new ArrayList<com.libarab.michaelg.libarab.favorites.bean.Book>();
        favoritePages = new ArrayList<Integer>();
        //set Love button
        lovebutton = (MaterialFavoriteButton) findViewById(R.id.lovebutton);
        //set addQuestion button
        addbutton = (ImageView) findViewById(R.id.add_question_button);
        //set gallery
        vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
        //set Goto text on botton
        tvGoto=(TextView) findViewById(R.id.tv_goto);
        tvGoto.setTextSize(20);
        //get Extras from sent Intent
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ID  = extras.getString("recordId");
            userId=extras.getString("userId");
            usertype = extras.getString("usertype");
            type= extras.getString("type");
            weblink      = extras.getString("webLink");
        }
        //GUEST USER

        if(usertype.equals(GUEST_USER)){
            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewPagerActivity.this, R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

            lovebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewPagerActivity.this, R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

        }
        //REGULAR USER

        else {
            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addQuestion(v);
                }
            });

            lovebutton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                               // Toast.makeText(getApplicationContext(),"Added Page: " +(vpGallery.getCurrentItem()+1),Toast.LENGTH_LONG).show();
                                if(book.getTitle().length()>30){
                                    String Short=book.getTitle();
                                    Short=Short.substring(0,30);
                                }

                                Uri builtUri =  Uri.parse(_ADD_FAV_URL_).buildUpon()
                                        .appendQueryParameter("username",    userId)
                                        //////////////////THIS WAS COMMENTED I DIDNT TOUCH IT (EMIL)
                                        // .appendQueryParameter("title",    title.getText().toString())
                                        .appendQueryParameter("bibId",    "0")
                                        .appendQueryParameter("itemId", ID)
                                        .appendQueryParameter("type", type)
                                        .appendQueryParameter("pagelink", pagesStr.get(vpGallery.getCurrentItem())+"")
                                        .appendQueryParameter("pagenum", (vpGallery.getCurrentItem()+1)+"")
                                        .appendQueryParameter("desc", "No Description").build();

                                        ///////////// THIS IS WHAT I COMMENTED (EMIL)
                                        if(book.getTitle().length()>30) {
                                            builtUri.buildUpon().appendQueryParameter("title", book.getTitle().substring(0, 30));
                                        }else{
                                            builtUri.buildUpon().appendQueryParameter("title", book.getTitle()).build();
                                        }
                                        builtUri.buildUpon()
                                        .appendQueryParameter("author", book.getAuthor())
                                        .appendQueryParameter("publisher", book.getPublisher())
                                        .appendQueryParameter("creationDate", book.getCreationdate())


                                        ///////////// THIS WAS COMMENTED... I DIDNT TOUCH THEM (EMIL)
                                        //.appendQueryParameter("source", book.getSource())
                                        //.appendQueryParameter("other", book.getOther())
                                        .build();
                                Log.v(TAG + "ADDFAVURL", builtUri.toString());
                                if(!(favoritePages.contains(vpGallery.getCurrentItem()+1)))
                                favoritePages.add(vpGallery.getCurrentItem()+1);
                                Collections.sort(favoritePages);
                                FavoritesTask fav = new FavoritesTask(builtUri.toString());
                                fav.execute((Void) null);

                            } else {

                                //Toast.makeText(getApplicationContext(),"Deleted Page "+ ((vpGallery.getCurrentItem())+1) ,Toast.LENGTH_LONG).show();
                                Uri builtUri =  Uri.parse(_REMOVE_FAV_URL_).buildUpon()
                                        .appendQueryParameter("userId",    userId)
                                        // .appendQueryParameter("title",    title.getText().toString())
                                        .appendQueryParameter("bibId",    "0")
                                        .appendQueryParameter("itemId", ID)
                                        .appendQueryParameter("pagenum", (vpGallery.getCurrentItem()+1)+"")
                                        .build();
                                Log.v(TAG + "REMOVEFAVURL", builtUri.toString());
                                int my =vpGallery.getCurrentItem()+1;
                                if( favoritePages.contains(vpGallery.getCurrentItem()+1) )
                                favoritePages.remove(Integer.valueOf(vpGallery.getCurrentItem()+1));
                                Collections.sort(favoritePages);
                                FavoritesTask fav = new FavoritesTask(builtUri.toString());
                                fav.execute((Void) null);
                            }
                        }
                    });
        }

        // Go To Funcuality
        tvGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewPagerActivity.this);
                alertDialog.setTitle(R.string.jump_to_page);
                alertDialog.setMessage(R.string.enter_page);

                etchange = new EditText(ViewPagerActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                etchange.setInputType(InputType.TYPE_CLASS_NUMBER);
                etchange.setLayoutParams(lp);
                alertDialog.setView(etchange);
                alertDialog.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changepage(v);
                    }
                });


                alertDialog.show();
            }
        });
        // TODO: 13/10/2016 Review Code again ( Michael ) 
        //SHEET
        if(type.equals("sheet")){

            //add sheet page linke
            pagesStr.add(weblink);
           // vpGallery.setVisibility(View.GONE);
           // TextView textView9=(TextView) findViewById(R.id.textView13);
            //textView9.setVisibility(View.GONE);
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
           // Picasso.with(getApplicationContext()).load(weblink).into(imageView);
           // imageView.setVisibility(View.VISIBLE);
          //  textView1.setText( 1+"/"+1);
            view2= new ZoomableDraweeView(imageView.getContext());
            view2.setController(
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(weblink))
                            .build());
            GenericDraweeHierarchy hierarchy =
                    new GenericDraweeHierarchyBuilder(getApplicationContext().getResources())
                            .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                            .setProgressBarImage(new ProgressBarDrawable())
                            .build();
            view2.setHierarchy(hierarchy);

            book = new Book();

            mAuthTask = new ViewItemTask(ID, userId);
            mAuthTask.execute((Void) null);
            getfavorites();

            return;
        }
        //BOOK OR MAP
        else {
            book = new Book();
            mAuthTask = new ViewItemTask(ID, userId);
            mAuthTask.execute((Void) null);
            getfavorites();

        }

    }

    private void getfavorites() {
        getFavoritesTask getfavtask = new getFavoritesTask();
        getfavtask.execute((Void) null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }

    public void addQuestion(View view) {

        Intent addq = new Intent(this, AddQuestion.class);
        addq.putExtra("userId", userId);
        addq.putExtra("itemId", ID);
        addq.putExtra("author", book.getAuthor());
        addq.putExtra("itemName", book.getTitle());

        addq.putExtra("creationdate" ,book.getCreationdate());
        addq.putExtra("webLink"      ,book.getWeblink());
        addq.putExtra("publisher"    ,book.getPublisher());
        startActivity(addq);

    }
    /**************************************************************************************
        This is a AsyncTASK for the Data fetching of the Book/MAP *************************
     *************************************************************************************
    */

    public class ViewItemTask extends AsyncTask<Void, Void, JSONObject> {

        private final String bookId;
        private final String userId;


        ViewItemTask(String bookId, String userId) {
            this.bookId = bookId;
            this.userId=userId;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;

            try {
                final String ID_PARAM = "recordId";
                final String USER_PARAM ="userId";
                final String SERVER_BASE_URL = Params.getServer() + "search/bookquery?";
                Uri builtUri = Uri.parse(SERVER_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter(ID_PARAM, bookId)
                        .appendQueryParameter(USER_PARAM,userId)
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v(TAG + "Type: " + type, builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                serverJsonStr = buffer.toString();
                Log.e("PROBLEM", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(final JSONObject success) {
            JSONArray pages=null;
            JSONObject fetchedbook =null;
            JSONArray pages3 = null;
            String creationdate,thumbnail,author,subject,webLink,publisher,source,title;
            try {

                //Handle Data for the Fetched Book

                fetchedbook =success.getJSONObject("book");
                book.setCreationdate(fetchedbook.getString("creationdate"));
                book.setThumbnail(fetchedbook.getString("thumbnail"));
                book.setAuthor(fetchedbook.getString("author"));
                //book.setSubject(fetchedbook.getString("subject"));
                book.setWeblink(fetchedbook.getString("webLink"));
                book.setPublisher(fetchedbook.getString("publisher"));
                book.setSource(fetchedbook.getString("source"));
                book.setTitle(fetchedbook.getString("title"));

                //Handle Array of Pages of Fetched Book

                pages = success.getJSONArray("pages");

                String first = "http://iiif.nli.org.il/IIIF/";
                String last = "/full/full/0/default.jpg";
                String tmp = "";

                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setVisibility(View.GONE);

                for (int i = 1; i < pages.length()-1; i++) {
                    pagesStr.add(first +pages.getString(i) + last);
                    Log.e("ItemsQ pages",tmp);
                }
                if(type.equals("book")) {
                    if ((pagesStr.size() == 0) || (pagesStr.size() == 1)) {
                        TextView textView9 = (TextView) findViewById(R.id.textView13);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                        viewPager.setVisibility(View.GONE);
                        textView9.setText(R.string.no_pages_error);
                        textView9.setTextSize(30);
                        textView9.setTextColor(Color.WHITE);
                        textView9.setVisibility(View.VISIBLE);


                        isNoPages = 1;
                    }
                }
                if(isNoPages ==0){

                    TextView textView9=(TextView) findViewById(R.id.textView13);
                    textView9.setVisibility(textView9.GONE);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                    viewPager.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
            myadapter=new GalleryAdapter(pagesStr);
            vpGallery.setAdapter(myadapter);
            tvGoto.setText(1 + "/" +pagesStr.size());
            vpGallery.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {

                    tvGoto.setText(position+1 + "/" +pagesStr.size() );
                    Boolean starstatus=false;
                   if( favoritePages.contains(position+1)==true)
                       starstatus=true;
                    lovebutton.setFavorite(starstatus);
                }
            });
        }


        protected void onCancelled() {
            mAuthTask = null;
            //   showProgress(false);
        }

    }

    /*****************************************************************************************************************************************
        This is a AsyncTASK for the Favorites*************************************************************************************************
    */

    public class FavoritesTask extends AsyncTask<Void, Void, JSONObject> {

        private String _SERVER_COMMAND;

        public FavoritesTask(String servercomman) {
            _SERVER_COMMAND = servercomman;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;
            try {
                Uri builtUri = Uri.parse(_SERVER_COMMAND).buildUpon().build();
                URL url = new URL(builtUri.toString());
                Log.v("FavoritesURL:", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null; // Nothing to do.
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0)
                    return null;
                serverJsonStr = buffer.toString();
                Log.d("getFavoritesjson:", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject object) {

        }
    }

    class GalleryAdapter extends PagerAdapter {

        List<String> items;

        public GalleryAdapter(List<String> pages){
            items=pages;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {





               ZoomableDraweeView view = new ZoomableDraweeView(container.getContext());
               view.setController(
                       Fresco.newDraweeControllerBuilder()
                               .setUri(Uri.parse(items.get(position)))
                               .build());

               GenericDraweeHierarchy hierarchy =
                       new GenericDraweeHierarchyBuilder(container.getResources())
                               .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                               .setProgressBarImage(new ProgressBarDrawable())
                               .build();

               view.setHierarchy(hierarchy);

               container.addView(view,
                       ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }



    public class getFavoritesTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;
            try {
                User user= (User) getIntent().getSerializableExtra("user");

                final String SERVER_GETFAVORITES = "http://52.29.110.203:8080/LibArab/favorites/getFavorites?";

                //final String SERVER_BASE_URL = "http://www.mocky.io/v2/57f0a2d70f0000f60901353f";
                Uri builtUri = Uri.parse(SERVER_GETFAVORITES).buildUpon()
                        .appendQueryParameter("userId",    user.getUsername())
                        .appendQueryParameter("type",    "book")
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v("getFavoritesURL:", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null; // Nothing to do.
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0)
                    return null;
                serverJsonStr = buffer.toString();
                Log.d("getFavoritesjson:", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject object) {
            try {
                JSONArray jarray = null;
                String answer = null;

                if (object == null) {
                    // something to do???
                    return;
                }

                bookList.clear();
                jarray = object.getJSONArray("favorites");

                for (int i = 0; i < jarray.length(); i++) {

                    com.libarab.michaelg.libarab.favorites.bean.Book lastBook = null;
                    String lastId = null;
                    String currentId = null;
                    String pageNumber = null;
                    int pageNumber2 = 0;
                    int lastId2 = 0;
                    int currentId2 = 1;

                    if(i != 0){
                        lastBook = bookList.get(bookList.size() - 1);
                    }

                    JSONObject bookobj = null;
                    bookobj = jarray.getJSONObject(i);
                    com.libarab.michaelg.libarab.favorites.bean.Book currentbook = new com.libarab.michaelg.libarab.favorites.bean.Book();

                    currentbook.setId(i);
                    currentbook.setBookid(bookobj.getString("bookID"));
                    currentbook.setTitle(bookobj.getString("title"));
                    currentbook.setImage(bookobj.getString("pageLink"));
                    currentbook.setDescription(bookobj.getString("description"));

                    //needed for viewer
                    currentbook.setAuthor(bookobj.getString("author"));
                    currentbook.setPublisher(bookobj.getString("publisher"));
                    currentbook.setCreationDate(bookobj.getString("creationDate"));
                    currentbook.setPagenum(bookobj.getString("pageNumber"));
                    pageNumber = bookobj.getString("pageNumber");
                    pageNumber2 = Integer.parseInt(pageNumber);
                    currentbook.getPageList().add(pageNumber2);
                    if(lastBook != null){
                        lastId = lastBook.getBookid();
                        currentId =currentbook.getBookid();
                        String[] separate = lastId.split("H");
                        String[] separate1 = currentId.split("H");
                        lastId2 = Integer.parseInt(separate[1]);
                        currentId2 = Integer.parseInt(separate1[1]);
                    }
                    if (lastId2 == currentId2){
                        lastId2 = 0;
                        currentId2 = 1;
                        //lastBook.getPageList().add(pageNumber2);
                        //bookList.remove(bookList.size() - 1);
                        bookList.get(bookList.size() - 1).getPageList().add(pageNumber2);
                    }else{
                        bookList.add(currentbook);
                    }



                }
                for(int i = 0 ; i < bookList.size() ; i++){
                    if(bookList.get(i).getBookid().equals(ID)){
                        favoritePages.clear();
                        favoritePages = bookList.get(i).getPageList();
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}


