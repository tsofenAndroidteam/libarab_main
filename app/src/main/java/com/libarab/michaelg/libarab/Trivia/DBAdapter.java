package com.libarab.michaelg.libarab.Trivia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Badernation on 29/9/16.
 */
public class DBAdapter extends SQLiteOpenHelper {
    //private StartQ quizList;
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "myDatabase4";

    // Table name
    private static final String TABLE_QUESTION = "question";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESION = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private static final String KEY_OPTD= "optd"; //option d

    private SQLiteDatabase myDatabase4;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        myDatabase4=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESION
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC +" TEXT, "+KEY_OPTD+" TEXT)";

        db.execSQL(sql);
        //quizList=new StartQ("Anya@","Anya@");
        //quizList.execute();
        addQuestions();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

        // Create tables again
        onCreate(db);
    }

    public int rowCount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }

    public List<Question> getAllQuestions() {
        // List<Question> quesList = null;
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        myDatabase4=this.getReadableDatabase();

        Cursor cursor = myDatabase4.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOptionA(cursor.getString(3));
                quest.setOptionB(cursor.getString(4));
                quest.setOptionC(cursor.getString(5));
                quest.setOptionD(cursor.getString(6));

                quesList.add(quest);

            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    private void addQuestions()
    {
        //format is question-option1-option2-option3-option4-answer
        Question q1=new Question("ماهو تاريخ اصدار كتاب عمليه السلام في الشرق الاوسط؟","2002", "2000", "1990", "1980","2002");
        this.addQuestion(q1);

        Question q2=new Question(" ما هو تاريخ معاهده السلام بين جمهوريه مصر العربيه وبين دوله اسرائيل؟","1991", "1985", "2004", "1979","1979");
        this.addQuestion(q2);

        Question q3=new Question("ماذا تسمى معاهده السلام الاسرائيليه الاردنيه عام 1994؟","ااتفاقيه الدوحه ", "اتفاقيه كامب ديفيد", "معاهده وادي عربه", "اتفاقيه فك الاشتباك الاول","معاهده وادي عربه");
        this.addQuestion(q3);

        Question q4=new Question("اين عقدت القمه العربيه عام 2002 ؟","مصر ", "لبنان", "الاردن", "فلسطين","لبنان");
        this.addQuestion(q4);

        Question q5=new Question("من الرئيس الذي منع مؤتمر القاهره لمناهضه الحرب؟","محمود عباس", "حسني مبارك", "ارئيل شارون", "الملك عبدالله الثاني","حسني مبارك");
        this.addQuestion(q5);

    }


    // Adding new question
    public void addQuestion(Question quest) {

        ContentValues values = new ContentValues();
        values.put(KEY_QUESION, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOptionA());
        values.put(KEY_OPTB, quest.getOptionB());
        values.put(KEY_OPTC, quest.getOptionC());
        values.put(KEY_OPTD, quest.getOptionD());

        // Inserting Row
        myDatabase4.insert(TABLE_QUESTION, null, values);
    }

/*

    public class StartQ extends AsyncTask<Void, Void, JSONObject> {

        private final String userName;
        private final String itemId;


        StartQ(String name, String id) {
            userName=name;
            itemId=id;
        }

        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //change
                final String FORECAST_BASE_URL =
                        Params.getServer() +"LibArab/gamification/Startquzi?";

                final String USER_NAME = "userName";
                final String ITEM_ID = "itemId";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(USER_NAME, userName)
                        .appendQueryParameter(ITEM_ID, itemId)
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
        protected void onPostExecute(final JSONObject quizListJson) {
            quizList= null;

            if(quizListJson==null){
                System.out.print("sss");
            }
            //else Toast.makeText(getApplicationContext(),"done", Toast.LENGTH_LONG).show();

            else {


                quizList = null;
                try {
                    JSONArray itemsRelateQ = quizListJson.getJSONArray("questions");
                    for(int i=0;i<itemsRelateQ.length();i++){
                        JSONObject itemQuize = itemsRelateQ.getJSONObject(i);
                        String question = itemQuize.getString("qustion");
                        String answer1 = itemQuize.getString("answer1");
                        String answer2 = itemQuize.getString("answer2");
                        String answer3 = itemQuize.getString("answer3");
                        String answer4 = itemQuize.getString("answer4");
                        String correct = itemQuize.getString("answer1");
                        Question q5=new Question(question,answer1,answer2,answer3,

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //finish();

            }





            //Toast.makeText(DBAdapter.this, quizListJson.toString(), Toast.LENGTH_LONG).show();
           /* if(success==null)
                Toast.makeText(quizList.this, "null", Toast.LENGTH_LONG).show();
            else {
                String answer = null;
                try {
                    answer = success.getString("client reply");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (answer.equals("success")) {  //case the user does'nt exist

                    //finish();
                    //Intent intent = new Intent(getApplicationContext(), Progress.class);
                    //startActivity(intent);

                } else {   // case failed
                    try {
                        Toast.makeText(quizList.this, success.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            quizList = null;

        }
    }*/



}
