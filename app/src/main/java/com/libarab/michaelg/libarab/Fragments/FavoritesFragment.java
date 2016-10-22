package com.libarab.michaelg.libarab.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.favorites.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_favorites, container, false);
        getActivity().setTitle(R.string.menu_favorites);
        //Button btn_triv = (Button) myview.findViewById(R.id.btn_trivia);
        Intent i = new Intent(getContext() , MainActivity.class);
        startActivity(i);
        return myview;
    }

}
