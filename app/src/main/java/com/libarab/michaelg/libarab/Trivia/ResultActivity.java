package com.libarab.michaelg.libarab.Trivia;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    // Intent intent = getIntent();
    // User myuser =(User) intent.getSerializableExtra("user");

    private String Id1=null;
    private int Sc1 = 0 ;
    private   FinishTask finTask=null;
    int totalQs;
    String userId;
    User myUser;
    int score=0;

    ArrayList<String> myAnsList=new ArrayList<String>();
    ArrayList<String> questions=new ArrayList<String>();
    ArrayList<String> answers=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_result);

        Intent intent = getIntent();
        myUser =(User) intent.getSerializableExtra("userId");

        //  String userId = myuser.getuserid();


        //get rating bar object
        RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);

        //get text view
        TextView tvAnsweredInfo =(TextView)findViewById(R.id.tvAnsweredInfo);
        TextView t=(TextView)findViewById(R.id.textResult);

        //get score
        Bundle b = getIntent().getExtras();
        score= b.getInt("score");
        totalQs= b.getInt("totalQs");
        userId=myUser.getUsername();
        final int points=score*3;
        myAnsList=b.getStringArrayList("myAnsList");
        questions=b.getStringArrayList("questions");
        answers=b.getStringArrayList("answers");


        //display score
        bar.setRating(score);

        tvAnsweredInfo.setText("You have answered "+score+" of "+totalQs+" questions  correctly!");

        float percentage=(score*100)/totalQs;

        if (percentage>=80 && percentage<=100){
            t.setText("Score is Excellent !");
        }else if(percentage>=70 && percentage<=79){
            t.setText("Score is Best");
        }else if(percentage>=60 && percentage<=69){
            t.setText("Score is Good");
        }else if(percentage>=50 && percentage<=59){
            t.setText("Score is Average!");
        }else if(percentage>=33 && percentage<=49){
            t.setText("Score is  Below Average!");
        }else{
            t.setText("Score is Poor! You need to practice more!");
        }



        Sc1= points ;
        // Id1 = myuser.getuserid();
        Id1 = "shsmichael@gmail.com";
        //Id1 = myuser.getuserid();



        //Toast.makeText(ResultActivity.this, "Score:"+points  , Toast.LENGTH_SHORT).show();

        FinishTask fin= new FinishTask(Id1 , Sc1);
        fin.execute((Void) null);


        Button btnDone=(Button)findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnViewAnswer=(Button)findViewById(R.id.btnViewAnswer);
        btnViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent=new Intent(ResultActivity.this,ViewAnswerActivity.class);
                vIntent.putStringArrayListExtra("myAnsList",myAnsList);
                vIntent.putExtra("userId",myUser);
                Bundle b = new Bundle();
                b.putInt("totalQs", totalQs);
                b.putStringArrayList("myAnsList", myAnsList);
                b.putStringArrayList("questions",questions);
                b.putStringArrayList("answers",answers);
                vIntent.putExtras(b);
                startActivity(vIntent);

            }
        });

        // the button id is : btnScore
   /*     Button btnScore=(Button)findViewById(R.id.btnScore);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/
                Toast.makeText(ResultActivity.this, "score:"+points  , Toast.LENGTH_SHORT).show();

            }
        });*/



   /*    Button bt= (Button) findViewById(R.id.btnScore);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

    }
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



    public class FinishTask extends AsyncTask<Void, Void, JSONObject> {
        private final String UserId;
        private final int Score;

        FinishTask(String User , int score){
            UserId= User ;
            Score = score;
            //Toast.makeText(
            // ResultActivity.this, "DDDDDDDDD"+ Sc1 + Id1  , Toast.LENGTH_SHORT).show();

        }


        @Override
        protected JSONObject doInBackground(Void... params) {
            //   return null;


            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //change
                final String FORECAST_BASE_URL =
                        //  Params.getServer()+
                        "http://52.29.110.203:8080/LibArab/gamification/finishGame?";


                //        Intent intent = getIntent();
                //       User myuser =(User) intent.getSerializableExtra("user");

                //String UserId1 = myuser.getuserid();

                //final String UserId1 = "shsmichael@gmail.com";
                final int score1 = 3;

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("userId", userId)

                        .appendQueryParameter("score", score+"")

                        .build();

                URL url = new URL(builtUri.toString());
                Log.v("URL", builtUri.toString());
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
                Log.d("PROBLEM", serverJsonStr);

            } catch (IOException e) {

                // If the code didn't successfully get the weather data, there's no point in attemping
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

        @Override
        protected void onPostExecute(final JSONObject success) {
            finTask = null;
            if (success == null)
                Toast.makeText(ResultActivity.this, "null", Toast.LENGTH_LONG).show();
            else {
                String answer = null;
                try {
                    answer = success.getString("status");
                    // Toast.makeText(ResultActivity.this, "SUCCESSSS", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (answer.equals("true")) {  //case the user does'nt exist


                    //Toast.makeText(ResultActivity.this, "Thank you!", Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(getApplicationContext(), Progress.class);
                    ///startActivity(intent);

                } else {   // case failed

                    Toast.makeText(ResultActivity.this, "not good", Toast.LENGTH_LONG).show();


                }
            }

        }


        @Override
        protected void onCancelled() {

            finTask = null;

        }
    }




    public void create(View view) {
        Intent intent = new Intent(getApplicationContext(), Create.class);
        startActivity(intent);
    }

 /*   public void finish(View view ) {

        User myuser=new User();
        Toast.makeText(getApplicationContext(), "ssssss:"  , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user",myuser);
        startActivity(intent);
    }*/
}

