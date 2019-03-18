package com.example.i_kasih;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.view.View.GONE;

public class Find extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextImprint;
    Spinner spinnerColor, spinnerShape;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        editTextImprint = (EditText) findViewById(R.id.editTextImprint);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerShape = (Spinner) findViewById(R.id.spinnerShape);

        Button buttonFind = (Button) findViewById(R.id.buttonFind);

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToResult();
                findProcess();
            }
        });
    }
    //mula
    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet --> ok dah unkomen!
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    //tamat
    public void goToResult(){
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }

    public void findProcess(){
        String imprint = editTextImprint.getText().toString().trim();
        String color = spinnerColor.getSelectedItem().toString();
        String shape = spinnerShape.getSelectedItem().toString();

        //validating the inputs
        if (TextUtils.isEmpty(imprint)) {
            editTextImprint.setError("Please enter imprint");
            editTextImprint.requestFocus();
            return;
        }
        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("imprint", imprint);
        params.put("color", color);
        params.put("shape", shape);

        //Calling the create API
        PerformNetworkRequest request = new PerformNetworkRequest(AppConfig.URL_FIND, params, CODE_POST_REQUEST);
        request.execute();
    }
}
