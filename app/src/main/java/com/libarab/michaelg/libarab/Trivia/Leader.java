package com.libarab.michaelg.libarab.Trivia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Leader extends AppCompatActivity {
    User userId;
    private LeaderBoardTask userRank; //= null;
    //Leaderboard

    TextView rank1;
    TextView rank2;
    TextView rank3;
    TextView rank4;
    TextView rank5;
    TextView rank6;
    TextView rank7;
    TextView rank8;
    TextView rank9;
    TextView rank10;

    TextView name1;
    TextView name2;
    TextView name3;
    TextView name4;
    TextView name5;
    TextView name6;
    TextView name7;
    TextView name8;
    TextView name9;
    TextView name10;

    TextView score1;
    TextView score2;
    TextView score3;
    TextView score4;
    TextView score5;
    TextView score6;
    TextView score7;
    TextView score8;
    TextView score9;
    TextView score10;

    TextView userRank1;
    TextView userName1;
    TextView userScore1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.trivia_activity_leader);


        //android
        // CHANGE
        Intent intent = getIntent();
        userId =(User) intent.getSerializableExtra("userId");
       // userId="anya@";
        //Toast.makeText(getApplicationContext(),userId, Toast.LENGTH_LONG).show();

       // userId = myuser.getuserid();
        userRank=new LeaderBoardTask(userId.getUsername());

        /*rank1=(TextView) findViewById(R.id.rank1);
        rank2=(TextView) findViewById(R.id.rank2);
        rank3=(TextView) findViewById(R.id.rank3);
        rank4=(TextView) findViewById(R.id.rank4);
        rank5=(TextView) findViewById(R.id.rank5);
        rank6=(TextView) findViewById(R.id.rank6);
        rank7=(TextView) findViewById(R.id.rank7);
        rank8=(TextView) findViewById(R.id.rank8);
        rank9=(TextView) findViewById(R.id.rank9);
        rank10=(TextView) findViewById(R.id.rank10);*/

        name1=(TextView) findViewById(R.id.name1);
        name2=(TextView) findViewById(R.id.name2);
        name3=(TextView) findViewById(R.id.name3);
        name4=(TextView) findViewById(R.id.name4);
        name5=(TextView) findViewById(R.id.name5);
        name6=(TextView) findViewById(R.id.name6);
        name7=(TextView) findViewById(R.id.name7);
        name8=(TextView) findViewById(R.id.name8);
        name9=(TextView) findViewById(R.id.name9);
        name10=(TextView) findViewById(R.id.name10);

        score1=(TextView) findViewById(R.id.score1);
        score2=(TextView) findViewById(R.id.score2);
        score3=(TextView) findViewById(R.id.score3);
        score4=(TextView) findViewById(R.id.score4);
        score5=(TextView) findViewById(R.id.score5);
        score6=(TextView) findViewById(R.id.score6);
        score7=(TextView) findViewById(R.id.score7);
        score8=(TextView) findViewById(R.id.score8);
        score9=(TextView) findViewById(R.id.score9);
        score10=(TextView) findViewById(R.id.score10);


        //userRank1=(TextView) findViewById(R.id.userRank);
        userName1=(TextView) findViewById(R.id.userName);
        userScore1=(TextView) findViewById(R.id.userScore);
        userRank.execute((Void) null);



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
            Intent intentToMain=new Intent(getApplicationContext() , com.libarab.michaelg.libarab.MainActivity.class);
            intentToMain.putExtra("user", userId);
            startActivity(intentToMain);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }





    /***************************************************************************/
//class

    public class LeaderBoardTask extends AsyncTask<Void, Void, JSONObject> {

        private final String userId;

        LeaderBoardTask(String user) {
            userId=user;

        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //we have to change it
                //future change
                //"http://172.20.10.6:8080/LibArab/gamification/leaderBoard?";
                final String FORECAST_BASE_URL ="http://52.29.110.203:8080/LibArab/gamification/leaderBorad?";
                // Params.getServer() + "gamification/leaderBorad?";
                //check format
                final String USER = "userId";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(USER, userId)
                        .build();

                URL url = new URL(builtUri.toString());
                //Log.v("URL", builtUri.toString());
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
                //Log.d("PROBLEM", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
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
            return serverJson;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        protected void onPostExecute(final JSONObject leaders) {
            if(leaders==null){
                Toast.makeText(getApplicationContext(),"null", Toast.LENGTH_LONG).show();
            }
            //else Toast.makeText(getApplicationContext(),"done", Toast.LENGTH_LONG).show();

            else {

              /*  String jsonRank1;
                String jsonRank2;
                String jsonRank3;
                String jsonRank4;
                String jsonRank5;
                String jsonRank6;
                String jsonRank7;
                String jsonRank8;
                String jsonRank9;
                String jsonRank10;*/

                String jsonScore1;
                String jsonScore2;
                String jsonScore3;
                String jsonScore4;
                String jsonScore5;
                String jsonScore6;
                String jsonScore7;
                String jsonScore8;
                String jsonScore9;
                String jsonScore10;

                String jsonName1;
                String jsonName2;
                String jsonName3;
                String jsonName4;
                String jsonName5;
                String jsonName6;
                String jsonName7;
                String jsonName8;
                String jsonName9;
                String jsonName10;

                String jsonUserRank;
                String jsonUserName;
                String jsonUserScore;


                userRank = null;
                try {
                    JSONArray leadersArray = leaders.getJSONArray("Leaders");

                    if(leadersArray.length()>1) {
                        JSONObject perUser1 = leadersArray.getJSONObject(1);
                       // jsonRank1 = perUser1.getString("rank");
                        jsonScore1 = perUser1.getString("score");
                        jsonName1 = perUser1.getString("name");
                        //rank1.setText(jsonRank1);
                        name1.setText(jsonName1);
                        score1.setText(jsonScore1);

                    }
                    //Toast.makeText(getApplicationContext(), jsonName1, Toast.LENGTH_LONG).show();
                    if(leadersArray.length()>2) {
                        JSONObject perUser2 = leadersArray.getJSONObject(2);
                        //jsonRank2 = perUser2.getString("rank");
                        jsonScore2 = perUser2.getString("score");
                        jsonName2 = perUser2.getString("name");
                        //rank2.setText(jsonRank2);
                        name2.setText(jsonName2);
                        score2.setText(jsonScore2);
                    }

                    if(leadersArray.length()>3) {
                        JSONObject perUser3 = leadersArray.getJSONObject(3);
                       // jsonRank3 = perUser3.getString("rank");
                        jsonScore3 = perUser3.getString("score");
                        jsonName3 = perUser3.getString("name");
                       // rank3.setText(jsonRank3);
                        name3.setText(jsonName3);
                        score3.setText(jsonScore3);
                    }

                    if(leadersArray.length()>4) {
                        JSONObject perUser4 = leadersArray.getJSONObject(4);
                       // jsonRank4 = perUser4.getString("rank");
                        jsonScore4 = perUser4.getString("score");
                        jsonName4 = perUser4.getString("name");
                        //rank4.setText(jsonRank4);
                        name4.setText(jsonName4);
                        score4.setText(jsonScore4);
                    }
                    if(leadersArray.length()>5) {
                        JSONObject perUser5 = leadersArray.getJSONObject(5);
                        //jsonRank5 = perUser5.getString("rank");
                        jsonScore5 = perUser5.getString("score");
                        jsonName5 = perUser5.getString("name");
                        //rank5.setText(jsonRank5);
                        name5.setText(jsonName5);
                        score5.setText(jsonScore5);

                    }

                    if(leadersArray.length()>6) {
                         JSONObject perUser6 = leadersArray.getJSONObject(6);
                        //jsonRank6 = perUser6.getString("rank");
                        jsonScore6 = perUser6.getString("score");
                        jsonName6 = perUser6.getString("name");
                        //rank6.setText(jsonRank6);
                        name6.setText(jsonName6);
                        score6.setText(jsonScore6);

                    }
                    if(leadersArray.length()>7) {
                        JSONObject perUser7 = leadersArray.getJSONObject(7);
                        //jsonRank7 = perUser7.getString("rank");
                        jsonScore7 = perUser7.getString("score");
                        jsonName7 = perUser7.getString("name");
                        score7.setText(jsonScore7);
                        name7.setText(jsonName7);
                        //rank7.setText(jsonRank7);
                    }
                    if(leadersArray.length()>8) {
                        JSONObject perUser8 = leadersArray.getJSONObject(8);
                        //jsonRank8 = perUser8.getString("rank");
                        jsonScore8 = perUser8.getString("score");
                        jsonName8 = perUser8.getString("name");
                        //rank8.setText(jsonRank8);
                        name8.setText(jsonName8);
                        score8.setText(jsonScore8);
                    }
                    if(leadersArray.length()>9) {

                        JSONObject perUser9 = leadersArray.getJSONObject(9);
                        //jsonRank9 = perUser9.getString("rank");
                        jsonScore9 = perUser9.getString("score");
                        jsonName9 = perUser9.getString("name");
                       // rank9.setText(jsonRank9);
                        name9.setText(jsonName9);
                        score9.setText(jsonScore9);
                    }
                    if(leadersArray.length()>10) {
                        JSONObject perUser10 = leadersArray.getJSONObject(10);
                        //jsonRank10 = perUser10.getString("rank");
                        jsonScore10 = perUser10.getString("score");
                        jsonName10 = perUser10.getString("name");
                        //rank10.setText(jsonRank10);
                        name10.setText(jsonName10);
                        score10.setText(jsonScore10);
                    }

                    JSONObject perUser0 = leadersArray.getJSONObject(0);
                    if(perUser0!=null) {
                        jsonUserRank = perUser0.getString("rank");
                        jsonUserScore = perUser0.getString("score");
                        jsonUserName = perUser0.getString("name");
                    }

                  /*  userRank1.setText(jsonUserRank);
                    userName1.setText(jsonUserName);
                    userScore1.setText(jsonUserScore);*/


                    //String id = c.getString("id");

                    JSONObject user = leadersArray.getJSONObject(0);
                    String userRank = user.getString("rank");
                    String userScore = user.getString("score");
                    String userName = user.getString("name");
                    //userRank1.setText(userRank);
                    //userName1.setText(userName);
                    userScore1.setText(userScore);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //finish();

            }
            /*
            String answer = null;
            try {
                answer = success.getString("client reply");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (answer.equals("success")) {  //case the user does'nt exist
                //showProgress(false);
                finish();
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);

            } else {   // case failed
                try {
                    Toast.makeText(AddQuestion.this, success.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            */
        }

        @Override
        protected void onCancelled() {

            userRank = null;
           /* showProgress(false);*/
        }
    }






}
