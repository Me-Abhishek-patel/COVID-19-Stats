package com.example.covid_19stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    TextView tvCases,tvActive,tvCured,tvDeaths,tvTodaysCases,tvTodaysDeaths;
    PieChart pieChart;
    ProgressBar progressBar ;
    LinearLayout infoList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        tvCases = (TextView) findViewById(R.id.tvCases);
        tvCured = (TextView) findViewById(R.id.tvCured);
        tvActive = (TextView) findViewById(R.id.tvActive);
        tvDeaths = (TextView) findViewById(R.id.tvDeath);
        tvTodaysCases = (TextView) findViewById(R.id.tvTodaysCases);
        tvTodaysDeaths = (TextView) findViewById(R.id.tvTodaysDeaths);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        infoList = (LinearLayout) findViewById(R.id.infolist);



        pieChart = findViewById(R.id.piechart);

        String url  = "https://corona.lmao.ninja/v2/all/";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    tvCases.setText(String.valueOf(response.getString("cases")));
                    tvActive.setText(String.valueOf(response.getInt("active")));
                    tvCured.setText(String.valueOf(response.getInt("recovered")));
                    tvDeaths.setText(String.valueOf(response.getInt("deaths")));
                    tvTodaysCases.setText(String.valueOf(response.getInt("todayCases")));
                    tvTodaysDeaths.setText(String.valueOf(response.getInt("todayDeaths")));


                    progressBar.setVisibility(View.GONE);
                    infoList.setVisibility(View.VISIBLE);


                    pieChart.addPieSlice(new PieModel("Cases",response.getInt("cases"), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Cured",response.getInt("recovered"), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",response.getInt("deaths"), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",response.getInt("active"), Color.parseColor("#29B6F6")));

                    pieChart.startAnimation();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Data:", "this is a fatal error");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("globalerr","Went Wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_global:

                            return true;
                        case R.id.navigation_india:
                            openFragment(IndianStats2.newInstance("", ""));
                            return true;
                        case R.id.navigation_countries:
                            startActivity(new Intent(getApplicationContext(),Countries.class));
                            return true;
                    }
                    return false;
                }
            };

}
