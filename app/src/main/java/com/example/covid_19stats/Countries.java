package com.example.covid_19stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Countries<request> extends AppCompatActivity {

    public static List<IndvCountry> countriesList = new ArrayList<>();
    EditText searchBar;
    ListView listView;
    IndvCountry indvCountry;
    CountriesAdapter countriesAdapter;
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        searchBar = findViewById(R.id.searchBar);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progress_circular4);


        String url = "https://corona.lmao.ninja/v2/countries/";



        StringRequest jRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");


                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                indvCountry = new IndvCountry(todayCases, deaths, todayDeaths, recovered, active, flagUrl, countryName, cases);
                                countriesList.add(indvCountry);


                            }
                            progressBar.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            countriesAdapter = new CountriesAdapter(Countries.this, countriesList);
                            listView.setAdapter(countriesAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Countries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jRequest);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countriesAdapter.getFilter().filter(s);
                countriesAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),EachCountry.class).putExtra("position",position));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
