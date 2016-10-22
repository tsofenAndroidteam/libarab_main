package com.libarab.michaelg.libarab.favorites.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;
import com.libarab.michaelg.libarab.favorites.BookInfoActivity;
import com.libarab.michaelg.libarab.favorites.adapter.BookGridAdapter;
import com.libarab.michaelg.libarab.favorites.bean.Book;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

//import org.litepal.crud.DataSupport;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import me.icxd.bookshelve.activity.BookInfoActivity;


public class
BookGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVORITE = 2;

    private static final String ARG_TYPE = "type";
    private int type = TYPE_ALL; // Data display (all, favorites)

    private GridView gridView; // Grid List
    private BookGridAdapter bookGridAdapter; // Data Adapter
    private int gridPosition = -1; // position of the selected item
    private ArrayList<Book> bookList;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                //fetchData();
                 bookGridAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static BookGridFragment newInstance(int type) {
        BookGridFragment fragment = new BookGridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          bookList = new ArrayList<Book>();
       /* if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
        }
        */
        type =2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_fragment_book_grid, container, false);

        // gridView
        gridView = (GridView) view.findViewById(R.id.fav_gridView);

        // ItemClickListener
        gridView.setOnItemClickListener(this);

        // ContextMenu - 'Delete' Function
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridPosition = position;
                Log.i("HB", "onItemLongClick:gridPosition: " + gridPosition);
                return false;
            }
        });
        registerForContextMenu(gridView);

        // EmptyView
        View emptyView = view.findViewById(R.id.fav_empty);
        ImageView ivIcon = (ImageView) emptyView.findViewById(R.id.fav_iv_icon);
        TextView tvText = (TextView) emptyView.findViewById(R.id.fav_tv_text);
        if (type == TYPE_FAVORITE) {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_favorite).colorRes(R.color.grid_empty_icon).sizeDp(40));
            tvText.setText("No Favorites Books");
        } else {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).colorRes(R.color.grid_empty_icon).sizeDp(48));
            tvText.setText("No Favorites Sheets");
        }
        gridView.setEmptyView(emptyView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bookGridAdapter = new BookGridAdapter(getContext());
        fetchData();
        //bookGridAdapter.notifyDataSetChanged();
        gridView.setAdapter(bookGridAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), BookInfoActivity.class);
           User myuser= (User) getActivity().getIntent().getSerializableExtra("user");
           intent.putExtra("book",  (Book)bookGridAdapter.getItem(position));
           intent.putExtra("user", myuser);
           //intent.putExtra("recordId",)
       // intent.putExtra("id", (int) bookGridAdapter.getItemId(position));

          startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "delete selected");
        menu.add(1, 2, 1, "Remove all");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO: 03/10/2016 restore

        Log.i("HB", "onContextItemSelected:adapter.getCount(): " + bookGridAdapter.getCount());
        Log.i("HB", "onContextItemSelected:gridPosition: " + gridPosition);
        if (item.getItemId() == 1 && gridPosition != -1) {
            // delete selected
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("This book sure to delete it")
                    .setContentText("Unable to recover deleted after。")
                    .setConfirmText("Yes")
                    .setCancelText("cancel")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // Refresh Data
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: 12/10/2016  fetchData();
                                    // TODO: 12/10/2016  bookGridAdapter.notifyDataSetChanged();
                                    User myuser= (User) getActivity().getIntent().getSerializableExtra("user");
                                    String _REMOVE_FAV_URL_="http://52.29.110.203:8080/LibArab/favorites/removeFromFavorites?";
                                    Uri builtUri =  Uri.parse(_REMOVE_FAV_URL_).buildUpon()
                                            .appendQueryParameter("userId",    myuser.getUsername())
                                            // .appendQueryParameter("title",    title.getText().toString())
                                            .appendQueryParameter("bibId",    "0")
                                            .appendQueryParameter("itemId",((Book)bookGridAdapter.getItem(gridPosition)).getBookid() )
                                            .appendQueryParameter("pagenum", ((Book)bookGridAdapter.getItem(gridPosition)).getPagenum())
                                            .build();
                                    //Log.v(TAG + "REMOVEFAVURL", builtUri.toString());
                                    FavoritesTask fav = new FavoritesTask(builtUri.toString());
                                    fav.execute((Void) null);
                                    fetchData();
                                }
                            }, 800);

                            sDialog
                                    .setTitleText("successfully deleted")
                                    .setContentText("The book has been successfully removed。")
                                    .setConfirmText("Done")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else if (item.getItemId() == 2) {
            // Remove all
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Sure to delete all Books")
                    .setContentText("Unable to recover deleted after.")
                    .setConfirmText("Yes")
                    .setCancelText("cancel")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // Refresh Data
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: 12/10/2016  DataSupport.deleteAll(Book.class);
                                    // TODO: 12/10/2016  fetchData();
                                    // TODO: 12/10/2016  bookGridAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                            sDialog
                                    .setTitleText("successfully deleted")
                                    .setContentText("All books have been successfully removed。")
                                    .setConfirmText("determine")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else {
            return super.onContextItemSelected(item);
        }
        return true;

    }

    public void fetchData() {

        //here is the link : http://www.mocky.io/v2/57eeaccc260000c61d1111cd
        //json you should be getting back  : {"result":"true","dataarray":[{"recordID":"book1","favName":"fav 41","pageNumber":"1","description":"No Description","type":"Book","pageLink":"http://pngimg.com/upload/book_PNG2111.png"},{"recordID":"book2","favName":"fav 41","pageNumber":"1","description":"No Description","type":"Book","pageLink":"http://pngimg.com/upload/book_PNG2111.png"},{"recordID":"book3","favName":"fav 41","pageNumber":"1","description":"No Description","type":"Book","pageLink":"http://pngimg.com/upload/book_PNG2111.png"}]}
        //need to implement somehow async task to fetch data and build data into list<Book>
        //try completing the async fentch first :)
        /*Log.i("HB", type + "GridFragment.fetchData");
        if (type == TYPE_FAVORITE) {
            bookGridAdapter.setData(DataSupport.where("favourite = ?", "1").order("id desc").find(Book.class));
        } else {
            bookGridAdapter.setData(DataSupport.order("id desc").find(Book.class));
        }*/

            getFavoritesTask getfavtask = new getFavoritesTask();
            getfavtask.execute((Void) null);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("HB", type + "GridFragment.onResume");
        //fetchData();
        bookGridAdapter.notifyDataSetChanged();
    }

    public class getFavoritesTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;
            try {
                User user= (User) getActivity().getIntent().getSerializableExtra("user");

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

                    Book lastBook = null;
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
                    Book currentbook = new Book();

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

                bookGridAdapter.setData(bookList);
                bookGridAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
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

}
