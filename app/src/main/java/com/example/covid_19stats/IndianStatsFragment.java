package com.example.covid_19stats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.eazegraph.lib.models.BarModel;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link IndianStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndianStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    /*instance variables*/
    BottomNavigationView bottomNavigation;
    TextView tvCases2, tvActive2, tvCured2, tvDeaths2, tvTodaysCases2, tvTodaysDeaths2;
    BarChart mBarChart;
    ProgressBar progressBar ;
    LinearLayout infoList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public IndianStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndianStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndianStatsFragment newInstance(String param1, String param2) {
        IndianStatsFragment fragment = new IndianStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*setting layout to inflat*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_indian_stats, container, false);
    }

    /*initialising variables*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bottomNavigation = getView().findViewById(R.id.bottom_navigation);
        tvCases2 = (TextView) getView().findViewById(R.id.tvCases2);
        tvCured2 = (TextView) getView().findViewById(R.id.tvCured2);
        tvActive2 = (TextView) getView().findViewById(R.id.tvActive2);
        tvDeaths2 = (TextView) getView().findViewById(R.id.tvDeath2);
        tvTodaysCases2 = (TextView) getView().findViewById(R.id.tvTodaysCases2);
        tvTodaysDeaths2 = (TextView) getView().findViewById(R.id.tvTodaysDeaths2);
        progressBar = (ProgressBar) getView().findViewById(R.id.progress_circular2);
        infoList = (LinearLayout) getView().findViewById(R.id.infolist2);
        mBarChart = (BarChart) getView().findViewById(R.id.barchart);

        /*Declaring and initialising url string fot Api url*/
        String url  = "https://corona.lmao.ninja/v2/countries/india";

        /*making request queue to request internet connection and data*/
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    /*Setting Data on views*/

                    tvCases2.setText(String.valueOf(response.getString("cases")));
                    tvActive2.setText(String.valueOf(response.getInt("active")));
                    tvCured2.setText(String.valueOf(response.getInt("recovered")));
                    tvDeaths2.setText(String.valueOf(response.getInt("deaths")));
                    tvTodaysCases2.setText(String.valueOf(response.getInt("todayCases")));
                    tvTodaysDeaths2.setText(String.valueOf(response.getInt("todayDeaths")));

                    /*Hiding progressbar*/
                    progressBar.setVisibility(View.GONE);
                    infoList.setVisibility(View.VISIBLE);

                    /*Setting data on charts*/
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
