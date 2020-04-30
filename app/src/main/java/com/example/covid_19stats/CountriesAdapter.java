package com.example.covid_19stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends ArrayAdapter<IndvCountry> {
    private Context context;
    private List<IndvCountry> countriesList;
    private List<IndvCountry> countryList2;

    public CountriesAdapter( Context context, List<IndvCountry> countriesList) {
        super(context, R.layout.country_list,countriesList);
        this.context = context;
        this.countriesList = countriesList;
        this.countryList2 = countriesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list,null,true);
        TextView tvCountry = view.findViewById(R.id.tvCountry);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        tvCountry.setText(countryList2.get(position).getCountry());
        Glide.with(context).load(countriesList.get(position).getFlag()).into(imageView);
        return view;
    }
    @Override
    public int getCount() {
        return countryList2.size();
    }

    @Nullable
    @Override
    public IndvCountry getItem(int position) {
        return countryList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countriesList.size();
                    filterResults.values = countriesList;

                }else{
                    List<IndvCountry> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(IndvCountry itemsModel:countriesList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryList2 = (List<IndvCountry>) results.values;
                Countries.countriesList = (List<IndvCountry>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
