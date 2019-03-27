package com.example.i_kasih;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Find extends AppCompatActivity {

    public static final String ColorArray = "EmpName";
    public static final String ShapeArray = "Shape";
    EditText editTextImprint;
    Spinner spinnerColor, spinnerShape;
    ProgressBar progressBar;
    ProgressDialog pDialog;

    public static final String JSON_ARRAY = "result";
    private JSONArray result;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextImprint = (EditText) findViewById(R.id.editTextImprint);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerShape = (Spinner) findViewById(R.id.spinnerShape);

        arrayList = new ArrayList<String>();
        arrayList2 = new ArrayList<String>();

        pDialog = new ProgressDialog(Find.this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Loading...");
        showDialog();

        readData();
        readShape();
    }

    private void readData() {
            StringRequest stringRequest = new StringRequest(AppConfig.URL_SPINNER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                result = j.getJSONArray(JSON_ARRAY);
                                listColor(result);
                            } catch (JSONException e) {
                                //e.printStackTrace();
                                pDialog.setMessage("Error test"+ e.getMessage() );
                                showDialog();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    private void readShape() {
        StringRequest stringRequest = new StringRequest(AppConfig.URL_SPINNER2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(JSON_ARRAY);
                            listShape(result);
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            pDialog.setMessage("Error test"+ e.getMessage() );
                            showDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void listColor(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString(ColorArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinnerColor.setAdapter(new ArrayAdapter<String>(Find.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }

    private void listShape(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList2.add(json.getString(ShapeArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinnerShape.setAdapter(new ArrayAdapter<String>(Find.this, android.R.layout.simple_spinner_dropdown_item, arrayList2));
        hideDialog();
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