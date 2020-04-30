package com.example.covid_19stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class EachCountry extends AppCompatActivity {
    TextView tvCountry,tvCases,tvRecovered,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths;
    BarChart mBarChart;
    ProgressBar progressBar ;
    LinearLayout infoList;
    private  int positionCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_country);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        tvCountry = findViewById(R.id.countryName);
        tvCases = findViewById(R.id.tvCases3);
        tvRecovered = findViewById(R.id.tvCured3);
        tvActive = findViewById(R.id.tvActive3);
        tvTodayCases = findViewById(R.id.tvTodaysCases3);
        tvTotalDeaths = findViewById(R.id.tvDeath3);
        tvTodayDeaths = findViewById(R.id.tvTodaysDeaths3);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular3);
        infoList = (LinearLayout) findViewById(R.id.infolist3);

        mBarChart = (BarChart) findViewById(R.id.barchart3);


        tvCountry.setText(Countries.countriesList.get(positionCountry).getCountry());
        tvCases.setText(Countries.countriesList.get(positionCountry).getCases());
        tvRecovered.setText(Countries.countriesList.get(positionCountry).getRecovered());
        tvActive.setText(Countries.countriesList.get(positionCountry).getActive());
        tvTodayCases.setText(Countries.countriesList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(Countries.countriesList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(Countries.countriesList.get(positionCountry).getTodayDeaths());

        progressBar.setVisibility(View.GONE);
        infoList.setVisibility(View.VISIBLE);


        mBarChart.addBar(new BarModel("Cases",Integer.parseInt(Countries.countriesList.get(positionCountry).getCases()), Color.parseColor("#FFA726")));
        mBarChart.addBar(new BarModel("Cured",Integer.parseInt(Countries.countriesList.get(positionCountry).getRecovered()), Color.parseColor("#66BB6A")));
        mBarChart.addBar(new BarModel("Deaths",Integer.parseInt(Countries.countriesList.get(positionCountry).getDeaths()), Color.parseColor("#EF5350")));
        mBarChart.addBar(new BarModel("Active",Integer.parseInt(Countries.countriesList.get(positionCountry).getActive()), Color.parseColor("#29B6F6")));
        mBarChart.startAnimation();



    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
