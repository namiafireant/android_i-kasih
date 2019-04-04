package com.example.i_kasih;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Result extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    ProgressBar progressBar;
    ListView listView;
    ProgressDialog pDialog;
    List<AttResult> resultList;
    private  String URLRESULT;
    ImageView img_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewResult);
        resultList = new ArrayList<>();

        Intent intent = getIntent();
        String IMPRINT = intent.getStringExtra(Find.IMPRINT_MESSAGE);
        String COLOR = intent.getStringExtra(Find.COLOR_MESSAGE);
        String SHAPE = intent.getStringExtra(Find.SHAPE_MESSAGE);

        URLRESULT = AppConfig.URL_RESULT+"?IMPRINT="+IMPRINT+"&COLOR="+COLOR+"&SHAPE="+SHAPE;

        /*Button buttonSearchAgain = (Button) findViewById(R.id.buttonSearchAgain);
        buttonSearchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScan();
            }
        });*/

        pDialog = new ProgressDialog(Result.this);
        pDialog.setCancelable(true);
        //pDialog.setMessage("Searching Data...");
        //pDialog.setMessage(URLRESULT);
        //showDialog();
            readData();

    }
    public void goToScan(){
        Intent intent = new Intent(this, Scan.class);
        startActivity(intent);
    }
    //mula
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
                    //refreshing the data list after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    refreshResultList(object.getJSONArray("results"));
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
    class ResultAdapter extends ArrayAdapter<AttResult> {
        //our hero list
        List<AttResult> resultList;

        //constructor to get the list
        public ResultAdapter(List<AttResult> resultList) {
            super(Result.this, R.layout.result_list, resultList);
            this.resultList = resultList;
        }
        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.result_list, null, true);

            //getting the textview for displaying name
            img_result = (ImageView) listViewItem.findViewById(R.id.img_result);
            TextView textView19 = listViewItem.findViewById(R.id.textView19);
            TextView textView20 = listViewItem.findViewById(R.id.textView20);
            TextView textView21 = listViewItem.findViewById(R.id.textView21);
            TextView textView22 = listViewItem.findViewById(R.id.textView22);
            TextView textView23 = listViewItem.findViewById(R.id.textView23);

            final AttResult result = resultList.get(position);

            textView19.setText(result.getName());
            textView20.setText(result.getDdesc());
            textView21.setText(result.getImprint());
            textView22.setText(result.getColor());
            textView23.setText(result.getShape());
            img_result.setImageBitmap(decodeFromBase64ToBitmap(result.getImage()));

            return listViewItem;
        }
    }
    private Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    private void readData() {
        Result.PerformNetworkRequest request = new Result.PerformNetworkRequest(URLRESULT, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refreshResultList(JSONArray results) throws JSONException {
        //clearing previous heroes
        resultList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < results.length(); i++) {
            //getting each hero object
            JSONObject obj = results.getJSONObject(i);

                //adding the hero to the list
                resultList.add(new AttResult(
                        obj.getInt("id"),
                        obj.getString("dimage"),
                        obj.getString("dname"),
                        obj.getString("ddesc"),
                        obj.getString("imprint"),
                        obj.getString("color"),
                        obj.getString("shape")
                ));
        }

        //creating the adapter and setting it to the listview
        ResultAdapter adapter = new ResultAdapter(resultList);
        listView.setAdapter(adapter);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
