package com.example.i_kasih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button buttonSearchAgain = (Button) findViewById(R.id.buttonSearchAgain);
        buttonSearchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScan();
            }
        });
    }
    public void goToScan(){
        Intent intent = new Intent(this, Scan.class);
        startActivity(intent);
    }
}
