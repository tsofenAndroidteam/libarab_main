package com.libarab.michaelg.libarab.Trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.libarab.michaelg.libarab.Item.ViewPagerActivity;
import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

public class StartQuiz extends AppCompatActivity {
    //  implements NavigationView.OnNavigationItemSelectedListener {
    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_app_bar_main);
        Intent intent = getIntent();
        final String auther =(String) intent.getSerializableExtra("auther");
        final String item =(String) intent.getSerializableExtra("itemName");
        myUser =(User) intent.getSerializableExtra("userId");
        final String userId=myUser.getUsername();


        /*Intent intent1=new Intent(getApplicationContext(),StartQuiz.class);
        intent1.putExtra("auther",auther);
        intent1.putExtra("itemName",item);*/

        TextView text=(TextView) findViewById(R.id.title);
        text.setText(auther);


        Button btnStart=(Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conceptIntent=new Intent(StartQuiz.this,ConceptActivity.class);
                conceptIntent.putExtra("auther",auther);
                conceptIntent.putExtra("itemName",item);
                conceptIntent.putExtra("userId",myUser);

                startActivity(conceptIntent);

            }
        });

        Button button4=(Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), bookList.get(position).getRecordid(), Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(getApplicationContext(),ViewPagerActivity.class);
                //this record id used by the ViewPagerActivity




                intent1.putExtra("user",myUser);

                //this weblink used by the ViewPagerActivity

                intent1.putExtra("recordId",item);
                intent1.putExtra("userId",myUser.getUsername());
                intent1.putExtra("author",auther);
                intent1.putExtra("title",auther);
                intent1.putExtra("creationdate","null");
                intent1.putExtra("publisher","null");
                //this weblink used by the ViewPagerActivity
                intent1.putExtra("webLink","null");
                intent1.putExtra("type","book");
                intent1.putExtra("source","null");
                intent1.putExtra("usertype","null");
                // Remember that variable (user) is the private variable above that is sent by the search



                startActivity(intent1);

            }
        });

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        User newUser=new User("firstname","lastname","username","gender","bday","usertype",true);
        if (id == R.id.action_name) {
            Intent intentToMain=new Intent(getApplicationContext() , com.libarab.michaelg.libarab.Trivia.triviaListview.class);
            intentToMain.putExtra("userId", myUser);
            startActivity(intentToMain);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }





    }
