package com.libarab.michaelg.libarab.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.Trivia.About;
import com.libarab.michaelg.libarab.Trivia.AddQuestion;
import com.libarab.michaelg.libarab.Trivia.Leader;
import com.libarab.michaelg.libarab.Trivia.triviaListview;
import com.libarab.michaelg.libarab.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class TriviaFragment extends Fragment {

    private final String TAG =this.getClass().getSimpleName();

    User myuser;
   // String myuser;

    public TriviaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_trivia, container, false);

        getActivity().setTitle(R.string.menu_trivia);
        myuser= (User) getActivity().getIntent().getSerializableExtra("user");
       // myuser= (User) getActivity().getIntent().getSerializableExtra("user");

        /*Button btn_triv = (Button) myview.findViewById(R.id.btn_trivia);
        Intent i = new Intent(getContext() , com.libarab.michaelg.libarab.Trivia.MainActivity.class);
        startActivity(i);*/
        /*
        btn_triv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , com.libarab.michaelg.libarab.Trivia.MainActivity.class);
                startActivity(i);
            }
        });
        */
        /*FrameLayout button = (FrameLayout) myview.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create(v);
            }
        });*/

        FrameLayout button2 = (FrameLayout) myview.findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(v);
            }
        });

        FrameLayout button3 = (FrameLayout) myview.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leaderboard(v);
            }
        });

        /*myuser = new User();
        myuser.setuserid("dror@d");*/


        ImageButton bt= (ImageButton) myview.findViewById(R.id.imageButton2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutTrivia(v);
            }
        });


        return myview;
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
                    //bundle.putInt("Type", userType);
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
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
    }

    public void aboutTrivia(View view){
        Intent intent= new Intent(view.getContext(), About.class);
        startActivity(intent);
    }

    public void start(View view) {

        //Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        //Intent i = new Intent(v.getContext() ,ListviewActivity.class);

        Intent intent = new Intent(view.getContext(), triviaListview.class);
        intent.putExtra("userId",myuser);
        //intent.putExtra("userId","anya@");
        startActivity(intent);
    }

    public void create(View view) {
        Intent intent = new Intent(view.getContext(), AddQuestion.class);
        startActivity(intent);
    }

    public void leaderboard(View view ) {

        Intent intent = new Intent(view.getContext(), Leader.class);
        intent.putExtra("userId",myuser);
        //intent.putExtra("userId","anya@");
        //intent.putExtra("user",myuser);
        startActivity(intent);
    }
}
