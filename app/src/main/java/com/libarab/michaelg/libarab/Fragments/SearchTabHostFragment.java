
package com.libarab.michaelg.libarab.Fragments;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libarab.michaelg.libarab.R;

public class SearchTabHostFragment extends Fragment {

    private final String TAG =this.getClass().getSimpleName();
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public SearchTabHostFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_tabhost,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(getString(R.string.search_book)),
                SearchBookFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(getString(R.string.search_sheet)),
                SearchSheetFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator(getString(R.string.search_map)),
                SearchMapFragment.class, null);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
       activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSearch)));
    }
}