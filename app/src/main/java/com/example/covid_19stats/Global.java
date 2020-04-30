package com.example.covid_19stats;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Global#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Global extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView tvCases,tvActive,tvCured,tvDeaths,tvTodaysCases,tvTodaysDeaths;
    PieChart pieChart;
    ProgressBar progressBar ;
    LinearLayout infoList;

    public Global() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Global.
     */
    // TODO: Rename and change types and number of parameters
    public static Global newInstance(String param1, String param2) {
        Global fragment = new Global();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvCases = (TextView) getActivity().findViewById(R.id.tvCases);
        tvCured = (TextView) getActivity().findViewById(R.id.tvCured);
        tvActive = (TextView) getActivity().findViewById(R.id.tvActive);
        tvDeaths = (TextView) getActivity().findViewById(R.id.tvDeath);
        tvTodaysCases = (TextView) getActivity().findViewById(R.id.tvTodaysCases);
        tvTodaysDeaths = (TextView) getActivity().findViewById(R.id.tvTodaysDeaths);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_circular);
        infoList = (LinearLayout) getActivity().findViewById(R.id.infolist);



        pieChart = getActivity().findViewById(R.id.piechart);

        String url  = "https://corona.lmao.ninja/v2/all/";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    tvCases.setText(String.valueOf(response.getInt("cases")));
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


}
