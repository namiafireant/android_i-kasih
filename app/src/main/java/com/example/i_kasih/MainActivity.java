package com.example.i_kasih;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Go_to_list
        ImageButton imgBtn_list = (ImageButton) findViewById(R.id.imgBtn_list);
        ImageButton imgBtn_news = (ImageButton) findViewById(R.id.imgBtn_news);
        ImageButton imgBtn_find = (ImageButton) findViewById(R.id.imgBtn_find);
        ImageButton imgBtn_scan = (ImageButton) findViewById(R.id.imgBtn_scan);

        imgBtn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToListUsingJava();
            }
        });
        imgBtn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNews();
            }
        });
        imgBtn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFind();
            }
        });
        imgBtn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScan();
            }
        });
    }
    public void goToListUsingJava(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void goToNews(){
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }
    public void goToFind(){
        Intent intent = new Intent(this, Find.class);
        startActivity(intent);
    }
    public void goToScan(){
        Intent intent = new Intent(this, Scan.class);
        startActivity(intent);
    }
}