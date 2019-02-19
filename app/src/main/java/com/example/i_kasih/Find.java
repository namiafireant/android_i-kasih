package com.example.i_kasih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Find extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Button buttonFind = (Button) findViewById(R.id.buttonFind);
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToResult();
            }
        });
    }
    public void goToResult(){
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }
}
