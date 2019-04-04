package com.example.i_kasih;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    public static final String IMPRINT_MESSAGE = "No IMPRINT Input";
    public static final String COLOR_MESSAGE = "No COLOR Input";
    public static final String SHAPE_MESSAGE = "No SHAPE Input";

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

        readColor();
        readShape();

        Button buttonFind = (Button) findViewById(R.id.buttonFind);
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToResult();
                findProcess();
            }
        });

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
        //HashMap<String, String> params = new HashMap<>();
        //params.put("imprint", imprint);
        //params.put("color", color);
        //params.put("shape", shape);

        Intent intent = new Intent(this, Result.class);
        intent.putExtra(IMPRINT_MESSAGE, imprint);
        intent.putExtra(COLOR_MESSAGE, color);
        intent.putExtra(SHAPE_MESSAGE, shape);
        startActivity(intent);
    }

    private void readColor() {
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