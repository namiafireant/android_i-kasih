package com.example.i_kasih;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ListActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;

    ImageView iv_retrive;

    //we will use this list to display data drug in listview
    List<AttributeDrug> drugList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewDrug);

        drugList = new ArrayList<>();

        //calling the method read data to read existing drug data from the database
        readData();
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
                    refreshHeroList(object.getJSONArray("heroes"));
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
    class HeroAdapter extends ArrayAdapter<AttributeDrug> {

        //our hero list
        List<AttributeDrug> heroList;

        //constructor to get the list
        public HeroAdapter(List<AttributeDrug> heroList) {
            super(ListActivity.this, R.layout.drug_list, heroList);
            this.heroList = heroList;
        }

        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.drug_list, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewDesc = listViewItem.findViewById(R.id.textViewDesc);
            iv_retrive = (ImageView) listViewItem.findViewById(R.id.iv_retrive); /*******  try set data image   *******/

            final AttributeDrug hero = heroList.get(position);

            textViewName.setText(hero.getName());
            textViewDesc.setText(hero.getDdesc());

            //String imagedecode = getResources().getString(R.string.iv_retrive);

            //String imagedecode = getResources().getString(R.string.image4);
            //String imagedecode = hero.getImage();

            iv_retrive.setImageBitmap(decodeFromBase64ToBitmap(hero.getImage())); /*** Buat nga aiman*/
            //iv_retrive.setImageBitmap(decodeFromBase64ToBitmap(imagedecode));
            //iv_retrive.setImageBitmap(hero.getImage()); /*******  try set data image   *******/

            return listViewItem;
        }
    }
    private Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    private void readData() {
        PerformNetworkRequest request = new PerformNetworkRequest(AppConfig.URL_READ_DATA, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refreshHeroList(JSONArray heroes) throws JSONException {
        //clearing previous heroes
        drugList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < heroes.length(); i++) {
            //getting each hero object
            JSONObject obj = heroes.getJSONObject(i);

            //adding the hero to the list
            drugList.add(new AttributeDrug(
                    obj.getInt("id"),
                    obj.getString("dname"),
                    obj.getString("ddesc"),
                    obj.getString("dimage")
            ));
        }

        //creating the adapter and setting it to the listview
        HeroAdapter adapter = new HeroAdapter(drugList);
        listView.setAdapter(adapter);
    }
    //END READ N REFRESH [R] Retrieve Display
}