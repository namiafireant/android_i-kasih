package com.example.i_kasih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        Button buttonScanCam = (Button) findViewById(R.id.buttonScanCam);
        buttonScanCam.setOnClickListener(new View.OnClickListener() {
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
