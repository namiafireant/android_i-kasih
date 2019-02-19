package com.example.i_kasih;

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
        imgBtn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kod here
                goToListUsingJava();
            }
        });
    }
    public void goToListUsingJava(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}