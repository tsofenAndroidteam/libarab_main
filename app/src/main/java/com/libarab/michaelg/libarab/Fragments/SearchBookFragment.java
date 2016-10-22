package com.libarab.michaelg.libarab.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.libarab.michaelg.libarab.ListviewActivity.ListviewActivity;
import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBookFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    String _SEARCH_URL;
    private boolean str_serchby;
    private Switch searchby;
    private String username;
    private String usertype;
    private EditText title ,
            fromyear ,
            toyear;
    User newUser;
    private Spinner spinner;
    private Button searchbutton ;
    private JSONObject returnedjson;
    private TextView tv_titleorauthor;
    private EditText free_txt;
    //ToDo :  change the String to editText ( waiting for Emeil to add the freetext to the xml


    public SearchBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_search_book, container, false);
        getActivity().setTitle(R.string.menu_search);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        title = (EditText) view.findViewById(R.id.title_edittext);
        fromyear = (EditText) view.findViewById(R.id.fromyear_editText);
        toyear = (EditText) view.findViewById(R.id.toYear_editText);
        searchbutton = (Button) view.findViewById(R.id.searchbtn);
        tv_titleorauthor=(TextView) view.findViewById(R.id.tv_title);
        free_txt = (EditText) view.findViewById(R.id.freetext_edittext);

        //free_txt = "any";

        title.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                doSearch(v);
                return true;
            }
            return false;
            }
        });
        fromyear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    doSearch(v);
                    return true;
                }
                return false;
            }
        });
        toyear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    doSearch(v);
                    return true;
                }
                return false;
            }
        });
        newUser= (User) getActivity().getIntent().getSerializableExtra("user");
        username = newUser.getUsername();
        usertype = newUser.getUserType();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.searcharray,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {


                switch (position){
                    case 0:
                        tv_titleorauthor.setText(R.string.search_title);
                        title.setHint(R.string.search_enter_title);
                        _SEARCH_URL =   Params.server +"search/booktitle?";
                        str_serchby=true;
                        break;
                    case 1:
                        tv_titleorauthor.setText(R.string.search_author);
                        title.setHint(R.string.search_enter_author);
                        _SEARCH_URL = Params.server +"search/bookauthor?";
                        str_serchby=false;
                    default:
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //perform action on click
                doSearch(v);
            }

        });

        return view;
    }

    private void doSearch(View v){
        String txt = title.getText().toString();
        if (txt.isEmpty()) {
            txt = "any";
        }
        String free =  free_txt.getText().toString();
        if (txt.isEmpty()) {
            txt = "any";
        }
        if (free.isEmpty()) {
            free = "any";
        }
        //TODO: @michael change userId not 4 in both cases
        if(str_serchby)
        {
            Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                    .appendQueryParameter("userId",    username)
                    .appendQueryParameter("title", txt)
                    .appendQueryParameter("fromyear", fromyear.getText().toString())
                    .appendQueryParameter("toyear",   toyear.getText().toString())
                    .appendQueryParameter("index", Integer.toString(0))
                    .appendQueryParameter("freeTxt", free)
                    .build();

            Log.v("URLBookFRAG", builtUri.toString());
            Intent i = new Intent(v.getContext() ,ListviewActivity.class);
            i.putExtra("Value1", builtUri.toString());
            i.putExtra("searchurl",_SEARCH_URL);
            i.putExtra("wholeUser", newUser);
            i.putExtra("userid", username.toString());
            i.putExtra("usertype", usertype);
            i.putExtra("txt", txt);
            i.putExtra("fromyear",fromyear.getText().toString());
            i.putExtra("toyear", toyear.getText().toString());
            i.putExtra("index", Integer.toString(0));
            i.putExtra("searchby", "title");
            i.putExtra("searchfor","book");
            i.putExtra("freetxt", free);


            //TODO: @Michael i.putExtra("userId",userId);
            startActivity(i);
        }
        else
        {
            Uri builtUri=  Uri.parse(_SEARCH_URL).buildUpon()
                    .appendQueryParameter("userId",    username)
                    .appendQueryParameter("author", txt)
                    .appendQueryParameter("fromyear", fromyear.getText().toString())
                    .appendQueryParameter("toyear",   toyear.getText().toString())
                    .appendQueryParameter("index", Integer.toString(0))
                    .appendQueryParameter("freeTxt", free)
                    .build();
            Log.v("URLBookFRAG", builtUri.toString());
            Intent i = new Intent(v.getContext() ,ListviewActivity.class);
            i.putExtra("Value1", builtUri.toString());
            i.putExtra("searchurl",_SEARCH_URL);
            i.putExtra("wholeUser", newUser);
            i.putExtra("userid", username.toString());
            i.putExtra("usertype", usertype);
            i.putExtra("txt", txt);
            i.putExtra("fromyear",fromyear.getText().toString());
            i.putExtra("toyear", toyear.getText().toString());
            i.putExtra("index", Integer.toString(0));
            i.putExtra("searchby", "author");
            i.putExtra("searchfor","book");
            i.putExtra("freetxt", free);

            startActivity(i);
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    MenuFragment menufragment = new MenuFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    menufragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.fragment_main,
                            menufragment,
                            menufragment.getTag()
                    ).commit();


                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSearch)));
    }
}
//