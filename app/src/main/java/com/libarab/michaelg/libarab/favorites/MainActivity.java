package com.libarab.michaelg.libarab.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.favorites.Fragments.BookGridFragment;
import com.libarab.michaelg.libarab.favorites.view.ViewPagerIndicator;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import android.support.design.widget.FloatingActionButton;
//import me.icxd.bookshelve.view.ViewPagerIndicator;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    // ViewPager
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("Books", "Sheets");

    // Fragment
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_activity_main);


        // Top ToolBar
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Lower right corner of the floating menu
        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true);


        // Floating the bottom right button - sweep the
        final com.github.clans.fab.FloatingActionButton fabBtnScanner = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_scanner);
        fabBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
               // Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                //startActivity(intent);
            }
        });


        // Floating the bottom right button - Add
        com.github.clans.fab.FloatingActionButton fabBtnAdd = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_add);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
               // Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                //startActivity(intent);
            }
        });

        // Menu button in the upper left corner
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.fav_navigation_drawer_open, R.string.fav_navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.fav_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.fav_view_pager);

        // ViewPagerIndicator
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.fav_indicator);
        viewPagerIndicator.setTabItemTitles(titles);

        // Fragment
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL));
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE));

        // PagerAdapter
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };

        // Menu settings data adapter
        viewPager.setAdapter(pagerAdapter);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fav_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_scanner) {
         //   Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
          //  startActivity(intent);
        } else if (id == R.id.nav_add) {
           // Intent intent = new Intent(MainActivity.this, SearchActivity.class);
           // intent.putExtra("search_type", SearchActivity.SEARCH_NET);
           // startActivity(intent);
        } else if (id == R.id.nav_about) {
            //Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            //startActivity(intent);
        } else if (id == R.id.nav_search) {
            //Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            //intent.putExtra("search_type", SearchActivity.SEARCH_LOCAL);
           // startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fav_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void refreshUI(){

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
