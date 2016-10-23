package com.libarab.michaelg.libarab.Trivia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.libarab.michaelg.libarab.Fragments.Params;
import com.libarab.michaelg.libarab.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddQuestion extends AppCompatActivity {
    EditText question;
    EditText correct;
    EditText uncorrect1;
    EditText uncorrect2;
    EditText uncorrect3;
    ImageButton addQuestionButton;
    String myuserid;
    String thisItemid;
    String thisAuther;
    String thisItemName;

    String creationdate1;
    String webLink;
    String publisher;

    private AddQTask addQueTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_add_question);

        question = (EditText) findViewById(R.id.question_text);
        correct = (EditText) findViewById(R.id.correct_answer);
        uncorrect1 = (EditText) findViewById(R.id.uncorrect_answer1);
        uncorrect2 = (EditText) findViewById(R.id.uncorrect_answer2);
        uncorrect3 = (EditText) findViewById(R.id.uncorrect_answer3);


        String

                question1 = question.getText().toString();
        addQuestionButton = (ImageButton) findViewById(R.id.imageButton);
        //addQuestionButton = (Button) findViewById(R.id.imageButton);
        Intent intent = getIntent();
        /*final String myuserid =(String) intent.getSerializableExtra("userid");
        final String thisItemid =(String) intent.getSerializableExtra("itemid");
        String  thisAuther = (String) intent.getSerializableExtra("auther")
        if(thisAuther == null)
          thisAuther = "unknown";
        final String thisItemName =(String) intent.getSerializableExtra("itemname"); */







        myuserid = intent.getStringExtra("userId");
        thisItemid = intent.getStringExtra("itemId");
        thisAuther = intent.getStringExtra("author");
        thisItemName = intent.getStringExtra("itemName");
        creationdate1 = intent.getStringExtra("creationdate");
        webLink = intent.getStringExtra("webLink");
        publisher = intent.getStringExtra("publisher");

        //Toast.makeText(AddQuestion.this,  thisItemName, Toast.LENGTH_LONG).show();

        addQuestionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText question = (EditText) findViewById(R.id.question_text);
                //String question1 = question.getText().toString();
                //Toast.makeText(AddQuestion.this, question1, Toast.LENGTH_LONG).show();
                attemptAddQuestion(myuserid,thisItemid,thisAuther,thisItemName);
            }
        });



    }


    private void attemptAddQuestion(String userID, String itemId, String auther, String itemName){

        String question1 = question.getText().toString();
        String correctAns=correct.getText().toString();
        String wrongAns1=uncorrect1.getText().toString();
        String wrongAns2=uncorrect2.getText().toString();
        String wrongAns3=uncorrect3.getText().toString();

        String question1_trim;
        String correctAns_trim;
        String wrongAns1_trim;
        String wrongAns2_trim;
        String wrongAns3_trim;

        question1_trim =question1.trim();
        correctAns_trim = correctAns.trim();
        wrongAns1_trim=wrongAns1.trim();
        wrongAns2_trim=wrongAns2.trim();
        wrongAns3_trim=wrongAns3.trim();

        int flag=0;
        /*if(TextUtils.isEmpty(question1)||TextUtils.isEmpty(correctAns)||TextUtils.isEmpty(wrongAns1)||TextUtils.isEmpty(wrongAns2)||TextUtils.isEmpty(wrongAns3)){
            flag=2;
        }*/
        if(question1_trim.length()==0||correctAns_trim.length()==0||wrongAns1_trim.length()==0||wrongAns2_trim.length()==0||wrongAns3_trim.length()==0){
            flag=1;
        }
        if (flag==1)
            Toast.makeText(AddQuestion.this,  "space", Toast.LENGTH_LONG).show();
        if(flag==0) {
            //Toast.makeText(AddQuestion.this, "yes", Toast.LENGTH_LONG).show();
            addQueTask = new AddQTask(question1, correctAns,wrongAns1,wrongAns2,wrongAns3,userID,itemId,auther, itemName);
            addQueTask.execute((Void) null);
            // Intent intent = new Intent(getApplicationContext(), Progress.class);
            //  startActivity(intent);

        }
        // Intent intent = new Intent(getApplicationContext(), Progress.class);
        // startActivity(intent);
    }




    /***************************************************************************/

    public class AddQTask extends AsyncTask<Void, Void, JSONObject> {

        private final String question1;
        private final String correct1;
        private final String uncorrect11;
        private final String uncorrect21;
        private final String uncorrect31;
        private final String userid;
        private final String itemid;
        private final String auther;
        private final String itemname;


        AddQTask(String q, String cor, String wrong1, String wrong2, String wrong3, String user, String item, String auther1, String itemName) {
            question1 = q;
            correct1 = cor;
            uncorrect11 = wrong1;
            uncorrect21 = wrong2;
            uncorrect31 = wrong3;
            userid = user;
            itemid = item;
            auther = auther1;
            itemname = itemName;

        }

        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //change
                final String FORECAST_BASE_URL =
                        Params.getServer() + "gamification/createQues?";

                final String QUESTION = "question";
                final String CORRECT_ANSWER = "answer1";
                final String UNCORRECT_ANSWER1 = "answer2";
                final String UNCORRECT_ANSWER2 = "answer3";
                final String UNCORRECT_ANSWER3 = "answer4";
                final String USER_ID = "username";
                final String ITEM_ID = "itemid";
                final String AUTHER = "author";
                final String ITEM_NAME = "ItemName";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUESTION, question1)
                        .appendQueryParameter(CORRECT_ANSWER, correct1)
                        .appendQueryParameter(UNCORRECT_ANSWER1, uncorrect11)
                        .appendQueryParameter(UNCORRECT_ANSWER2, uncorrect21)
                        .appendQueryParameter(UNCORRECT_ANSWER3, uncorrect31)
                        .appendQueryParameter(USER_ID, userid)
                        .appendQueryParameter(ITEM_ID, itemid)
                        .appendQueryParameter(AUTHER, thisAuther)
                        .appendQueryParameter(ITEM_NAME, itemname)
                        .appendQueryParameter("publisher",creationdate1)
                        .appendQueryParameter("creationDate", webLink)
                        .appendQueryParameter("urlImage", publisher)
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

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject success) {
            addQueTask = null;
            if (success == null)
                Toast.makeText(AddQuestion.this, "null", Toast.LENGTH_LONG).show();
            else {
                String answer = null;
                try {
                    answer = success.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (answer.equals("true")) {  //case the user does'nt exist

                    finish();
                    Toast.makeText(AddQuestion.this, "Thank you!", Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(getApplicationContext(), Progress.class);
                    ///startActivity(intent);

                } else {   // case failed

                    Toast.makeText(AddQuestion.this, "not good", Toast.LENGTH_LONG).show();


                }
            }

        }


        protected void onCancelled() {
            addQueTask = null;

        }
    }
}








