package com.example.i_kasih;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class News extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    ProgressBar progressBar;
    ListView listView;
    ImageView imageNews;
    ProgressDialog pDialog;
    List<AttributeNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewNews);
        newsList = new ArrayList<>();
        //calling the method read data to read existing drug data from the database


        pDialog = new ProgressDialog(News.this);
        pDialog.setCancelable(true);
        //pDialog.setMessage("Loading...");
        //showDialog();
        readData();
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
                    //refreshing the data list after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    refreshNewsList(object.getJSONArray("heroes"));
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
    //new inner class

    class NewsAdapter extends ArrayAdapter<AttributeNews> {
        //our hero list
        List<AttributeNews> newsList;

        //constructor to get the list
        public NewsAdapter(List<AttributeNews> newsList) {
            super(News.this, R.layout.news_list, newsList);
            this.newsList = newsList;
        }

        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.news_list, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewNews);
            imageNews = (ImageView) listViewItem.findViewById(R.id.imageNews);

            final AttributeNews news = newsList.get(position);

            textViewName.setText(news.getNewsTitle());
            imageNews.setImageBitmap(decodeFromBase64ToBitmap(news.getNewsImg()));

            return listViewItem;
        }
    }
    private Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    private void readData() {
        try{
        News.PerformNetworkRequest request = new News.PerformNetworkRequest(AppConfig.URL_NEWS, null, CODE_GET_REQUEST);
        request.execute();
        }
        catch(Exception e){
            pDialog.setMessage(e.getMessage() );
            showDialog();
        }
    }
    private void refreshNewsList(JSONArray heroes) throws JSONException {
        //clearing previous heroes
        newsList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < heroes.length(); i++) {
            //getting each hero object
            JSONObject obj = heroes.getJSONObject(i);

            //adding the hero to the list
            newsList.add(new AttributeNews(
                    obj.getString("title"),
                    obj.getString("newimg")
            ));
        }

        //creating the adapter and setting it to the listview
        News.NewsAdapter adapter = new News.NewsAdapter(newsList);
        listView.setAdapter(adapter);
    }
    //END READ N REFRESH [R] Retrieve Display

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
}
