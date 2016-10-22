package com.libarab.michaelg.libarab.Item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.libarab.michaelg.libarab.R;

public class BookinfoActivity extends AppCompatActivity {

    TextView txtAuthor,
            txtTitle,
            txtPublisher,
            txtCreationdate,
            txtSource,
            txtWebLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);

        Intent intent = getIntent();
        String author = intent.getStringExtra("author");
        //String recordid = intent.getStringExtra("recordId");
        String title = intent.getStringExtra("title");
        String creationdate = intent.getStringExtra("creationdate");
        String publisher = intent.getStringExtra("publisher");
        String source = intent.getStringExtra("source");
        String webLink = intent.getStringExtra("webLink");
        txtAuthor = (TextView) findViewById(R.id.textView_author2);
        txtTitle = (TextView) findViewById(R.id.textView_title2);
        txtPublisher = (TextView) findViewById(R.id.textView_publisher2);
        txtCreationdate = (TextView) findViewById(R.id.textView_creationdate2);
        txtSource = (TextView) findViewById(R.id.textView_source2);
        txtWebLink = (TextView) findViewById(R.id.textView_weblink2);

        // "" then the String because if the value is null we need to show it
        txtTitle.setText("" + title);
        txtAuthor.setText("" + author);
        txtCreationdate.setText("" + creationdate);
        txtPublisher.setText("" + publisher);
        txtSource.setText("" +source);
        txtWebLink.setText("" + webLink);


    }
}
