package com.libarab.michaelg.libarab.Fragments;


import android.animation.Animator;
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
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

import static com.mikepenz.iconics.core.R.attr.colorPrimary;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private final String TAG =this.getClass().getSimpleName();
    public MenuFragment() {
        // Required empty public constructor
    }
    TextView txt,txtemail,txtfullname;
    FrameLayout search,trivia,favorites,about,settings,bibilo;
    LinearLayout profile;
    View myview;
    User newUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_menu, container, false);
        getActivity().setTitle(R.string.menu);
        txt = (TextView) myview.findViewById(R.id.tv_search);
        txtfullname = (TextView) myview.findViewById(R.id.tv_fullname) ;
        txtemail = (TextView) myview.findViewById(R.id.textView);
        search = (FrameLayout) myview.findViewById(R.id.frame_search);
        trivia = (FrameLayout) myview.findViewById(R.id.frame_trivia);
        favorites = (FrameLayout) myview.findViewById(R.id.frame_favorites);
        about = (FrameLayout) myview.findViewById(R.id.frame_about);
        settings = (FrameLayout) myview.findViewById(R.id.frame_settings);
        bibilo = (FrameLayout) myview.findViewById(R.id.frame_biblio);
        profile =(LinearLayout) myview.findViewById(R.id.frame_profile);

        newUser= (User) getActivity().getIntent().getSerializableExtra("user");
       // newUser =(User) this.getArguments().getSerializable("user");
        String usertype =newUser.getUserType();

        if (usertype.equals("0")){       //user type is guest
            txtfullname.setText(newUser.getFirstname() + " " +newUser.getLastname());
            txtemail.setVisibility(View.INVISIBLE);

            trivia.setClickable(false);
            bibilo.setClickable(false);
            favorites.setClickable(false);

            trivia.setBackground(getResources().getDrawable(R.drawable.ripplebibliography_guest));
            bibilo.setBackground(getResources().getDrawable(R.drawable.ripplebibliography_guest));
            favorites.setBackground(getResources().getDrawable(R.drawable.ripplebibliography_guest));

            trivia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

            bibilo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

            favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            txtfullname.setText(newUser.getFirstname() + " " +newUser.getLastname());
            txtemail.setText(newUser.getUsername());
            trivia.setClickable(true);
            bibilo.setClickable(false);
            favorites.setClickable(true);
           // favorites.setClickable(true);

           // favorites.setBackground(getResources().getDrawable(R.drawable.ripplebibliography_guest));
            bibilo.setBackground(getResources().getDrawable(R.drawable.ripplebibliography_guest));

            trivia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 23/09/2016 Fix color change
                    //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAbout)));

                    //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                    TriviaFragment triviafragmentestfragment = new TriviaFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    //bundle.putInt("Type", userType);
                    triviafragmentestfragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.fragment_main,
                            triviafragmentestfragment,
                            triviafragmentestfragment.getTag()
                    ).commit();
                }
            });

            bibilo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), R.string.to_be_done, Toast.LENGTH_SHORT).show();
                }
            });

            favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 23/09/2016 Fix color change
                    //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                    FavoriteFragmentnew favoritesfragmentestfragment = new FavoriteFragmentnew();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    //bundle.putInt("Type", userType);
                    favoritesfragmentestfragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.fragment_main,
                            favoritesfragmentestfragment,
                            favoritesfragmentestfragment.getTag()
                    ).commit();
                }
            });


        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/09/2016 Fix color change
                //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                SearchTabHostFragment mainfragmentestfragment = new SearchTabHostFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                //bundle.putInt("Type", userType);
                mainfragmentestfragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragment_main,
                        mainfragmentestfragment,
                        mainfragmentestfragment.getTag()
                ).commit();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/09/2016 Fix color change
                //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                AboutFragment aboutfragment = new AboutFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                //bundle.putInt("Type", userType);
                aboutfragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragment_main,
                        aboutfragment,
                        aboutfragment.getTag()
                ).commit();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/09/2016 Fix color change
                //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                PreferenceFragment preffragment = new PreferenceFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                //bundle.putInt("Type", userType);
                preffragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragment_main,
                         preffragment,
                        preffragment.getTag()
                ).commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/09/2016 Fix color change
                //getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
                ProfileFragment profilefragmentestfragment = new ProfileFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                //bundle.putInt("Type", userType);
                profilefragmentestfragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragment_main,
                        profilefragmentestfragment,
                        profilefragmentestfragment.getTag()
                ).commit();
            }
        });

        myview.post(new Runnable()
        {
            @Override
            public void run()
            {
                // get the center for the clipping circle
                int search_cx = search.getWidth() / 2;
                int search_cy =  search.getHeight() / 2;
                //
                int triv_cx = trivia.getWidth() / 2;
                int triv_cy =  trivia.getHeight() / 2;

                int fav_cx = favorites.getWidth() / 2;
                int fav_cy =  favorites.getHeight() / 2;

                int about_cx = about.getWidth() / 2;
                int about_cy =  about.getHeight() / 2;

                int settings_cx = settings.getWidth() / 2;
                int settings_cy =  settings.getHeight() / 2;

                int bibilo_cx = bibilo.getWidth() / 2;
                int bibilo_cy =  bibilo.getHeight() / 2;


                // get the final radius for the clipping circle

                float finalRadius = (float) Math.hypot(search_cx, search_cy);
                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(search, search_cx, search_cy, 0, finalRadius);
                Animator anim2 =
                        ViewAnimationUtils.createCircularReveal(trivia, triv_cx, triv_cy, 0, finalRadius);
                Animator anim3 =
                        ViewAnimationUtils.createCircularReveal(favorites, fav_cx, fav_cy, 0, finalRadius);
                Animator anim4 =
                        ViewAnimationUtils.createCircularReveal(about, about_cx, about_cy, 0, finalRadius);
                Animator anim5 =
                        ViewAnimationUtils.createCircularReveal(settings, settings_cx, settings_cy, 0, finalRadius);
                Animator anim6 =
                        ViewAnimationUtils.createCircularReveal(bibilo, bibilo_cx, bibilo_cy, 0, finalRadius);


                search.setVisibility(View.VISIBLE);
                trivia.setVisibility(View.VISIBLE);
                favorites.setVisibility(View.VISIBLE);
                about.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                settings.setVisibility(View.VISIBLE);
                //anim.setStartDelay(500);
                anim.start();
                //anim3.setStartDelay(100);
                anim3.start();
                //anim4.setStartDelay(200);
                anim4.start();
                //anim5.setStartDelay(300);
                anim5.start();
                //anim2.setStartDelay(400);
                anim2.start();
                //anim6.setStartDelay(500);
                anim6.start();
            }
        });

        return myview;
    }
    @Override
    public void onResume() {
        super.onResume();

        if(newUser.getUserType().equals("0")) {

        }else{
            if (getView() == null) {
                return;
            }
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }
}
