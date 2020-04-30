package com.example.covid_19stats;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class IndianStats extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    TextView tvCases2, tvActive2, tvCured2, tvDeaths2, tvTodaysCases2, tvTodaysDeaths2;
    BarChart mBarChart;
    ProgressBar progressBar ;
    LinearLayout infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_stats);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        tvCases2 = (TextView) findViewById(R.id.tvCases2);
        tvCured2 = (TextView) findViewById(R.id.tvCured2);
        tvActive2 = (TextView) findViewById(R.id.tvActive2);
        tvDeaths2 = (TextView) findViewById(R.id.tvDeath2);
        tvTodaysCases2 = (TextView) findViewById(R.id.tvTodaysCases2);
        tvTodaysDeaths2 = (TextView) findViewById(R.id.tvTodaysDeaths2);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular2);
        infoList = (LinearLayout) findViewById(R.id.infolist2);

        mBarChart = (BarChart) findViewById(R.id.barchart);

        String url  = "https://corona.lmao.ninja/v2/countries/india";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    tvCases2.setText(String.valueOf(response.getString("cases")));
                    tvActive2.setText(String.valueOf(response.getInt("active")));
                    tvCured2.setText(String.valueOf(response.getInt("recovered")));
                    tvDeaths2.setText(String.valueOf(response.getInt("deaths")));
                    tvTodaysCases2.setText(String.valueOf(response.getInt("todayCases")));
                    tvTodaysDeaths2.setText(String.valueOf(response.getInt("todayDeaths")));


                    progressBar.setVisibility(View.GONE);
                    infoList.setVisibility(View.VISIBLE);


                    mBarChart.addBar(new BarModel("Cases",response.getInt("cases"), Color.parseColor("#FFA726")));
                    mBarChart.addBar(new BarModel("Cured",response.getInt("recovered"), Color.parseColor("#66BB6A")));
                    mBarChart.addBar(new BarModel("Deaths",response.getInt("deaths"), Color.parseColor("#EF5350")));
                    mBarChart.addBar(new BarModel("Active",response.getInt("active"), Color.parseColor("#29B6F6")));
                    mBarChart.startAnimation();

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
}
