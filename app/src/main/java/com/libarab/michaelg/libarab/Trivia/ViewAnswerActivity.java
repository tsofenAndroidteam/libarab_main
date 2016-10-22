package com.libarab.michaelg.libarab.Trivia;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.libarab.michaelg.libarab.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAnswerActivity extends AppCompatActivity {

    private ListView lvQsAns;

    int totalQs;

    ArrayList<String> myAnsList = new ArrayList<String>();
    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();

    private List<Question> questionsList;
    private Question currentQuestion;

    User myUser;

    ArrayList<HashMap<String, Object>> originalValues = new ArrayList<HashMap<String, Object>>();
    ;

    HashMap<String, Object> temp = new HashMap<String, Object>();

    public static String KEY_QUES = "questions";
    public static String KEY_CANS = "canswer";
    public static String KEY_YANS = "yanswer";

    private CustomAdapter adapter;

    int numOfQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_viewanswer);

        Intent intent = getIntent();
        myUser = (User) intent.getSerializableExtra("userId");
        //Toast.makeText(ViewAnswerActivity.this, myUser.getUsername(), Toast.LENGTH_LONG).show();


        Bundle b = getIntent().getExtras();
        numOfQ = b.getInt("totalQs");
        myAnsList = b.getStringArrayList("myAnsList");
        questions = b.getStringArrayList("questions");
        answers = b.getStringArrayList("answers");



        /*Intent in = getIntent();
        myAnsList=in.getExtras().getStringArrayList("myAnsList");*/


        lvQsAns = (ListView) findViewById(R.id.lvQsAns);

        //Initialize the database
        final DBAdapter dbAdapter = new DBAdapter(this);
        questionsList = dbAdapter.getAllQuestions();


        for (int i = 0; i < numOfQ; i++) {

            temp = new HashMap<String, Object>();
            temp.put(KEY_QUES, questions.get(i));
            temp.put(KEY_CANS, answers.get(i));
            temp.put(KEY_YANS, myAnsList.get(i));

            // add the row to the ArrayList
            originalValues.add(temp);

        }

        adapter = new CustomAdapter(ViewAnswerActivity.this, R.layout.trivia_lists_row,
                originalValues);
        lvQsAns.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    // define your custom adapter
    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
        LayoutInflater inflater;

        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {
            super(context, textViewResourceId, Strings);
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // class for caching the views in a row
        private class ViewHolder {

            TextView tvQS, tvCans, tvYouans;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.trivia_lists_row, null);
                viewHolder = new ViewHolder();

                viewHolder.tvQS = (TextView) convertView.findViewById(R.id.tvQuestions);

                viewHolder.tvCans = (TextView) convertView
                        .findViewById(R.id.tvCorrectAns);
                viewHolder.tvYouans = (TextView) convertView
                        .findViewById(R.id.tvYourAns);

                // link the cached views to the convertview
                convertView.setTag(viewHolder);

            } else
                viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.tvQS.setText(originalValues.get(position).get(KEY_QUES)
                    .toString());

            viewHolder.tvCans.setText("Correct Ans: " + originalValues.get(position).get(KEY_CANS)
                    .toString());
            viewHolder.tvYouans.setText("Your Ans: " + originalValues.get(position)
                    .get(KEY_YANS).toString());

            // return the view to be displayed
            return convertView;
        }
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