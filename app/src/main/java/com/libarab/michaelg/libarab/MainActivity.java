package com.libarab.michaelg.libarab;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.libarab.michaelg.libarab.Fragments.AboutFragment;
import com.libarab.michaelg.libarab.Fragments.FavoriteFragmentnew;
import com.libarab.michaelg.libarab.Fragments.MenuFragment;
import com.libarab.michaelg.libarab.Fragments.PreferenceFragment;
import com.libarab.michaelg.libarab.Fragments.SearchTabHostFragment;
import com.libarab.michaelg.libarab.Fragments.TriviaFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    int userType;
    User myuser;
    View item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        myuser = (User) intent.getSerializableExtra("user");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView) header.findViewById(R.id.textView_first_header);
        TextView textView1 = (TextView) header.findViewById(R.id.textView_second_header);
        textView.setText(myuser.getFirstname() + " " + myuser.getLastname());
        textView1.setText(myuser.getUsername());
        //item = findViewById(R.id.nav_trivia);

        if(myuser.getUserType().equals("0")){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_trivia).setVisible(false);
            nav_Menu.findItem(R.id.nav_bibilo).setVisible(false);
            nav_Menu.findItem(R.id.nav_favorites).setVisible(false);
        }else{
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_trivia).setVisible(true);
            nav_Menu.findItem(R.id.nav_bibilo).setVisible(true);
            nav_Menu.findItem(R.id.nav_favorites).setVisible(true);
        }
        //set main frag
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        MenuFragment menufragment = new MenuFragment();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        //bundle.put("Type", userType);
        bundle.putSerializable("user",myuser);
        menufragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.fragment_main,
                menufragment,
                menufragment.getTag()
        ).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //set settings frag
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSettings)));
            PreferenceFragment settingsfragment = new PreferenceFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    settingsfragment,
                    settingsfragment.getTag()
            ).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            MenuFragment menufragment = new MenuFragment();
            FragmentManager manager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putInt("Type", userType);
            menufragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragment_main,
                    menufragment,
                    menufragment.getTag()
            ).commit();


        } else if (id == R.id.nav_search) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSearch)));
            SearchTabHostFragment mainfragmenttest = new SearchTabHostFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    mainfragmenttest,
                    mainfragmenttest.getTag()
            ).commit();

        } else if (id == R.id.nav_favorites) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorFav)));
            FavoriteFragmentnew favoritesfragment = new FavoriteFragmentnew();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    favoritesfragment,
                    favoritesfragment.getTag()
            ).commit();

        } else if (id == R.id.nav_trivia) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
            TriviaFragment triviafragment = new TriviaFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    triviafragment,
                    triviafragment.getTag()
            ).commit();
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSettings)));
            PreferenceFragment settingsfragment = new PreferenceFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    settingsfragment,
                    settingsfragment.getTag()
            ).commit();
        } else if (id == R.id.nav_about) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAbout)));
            AboutFragment aboutfragment = new AboutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    aboutfragment,
                    aboutfragment.getTag()
            ).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void refreshUI(){

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}