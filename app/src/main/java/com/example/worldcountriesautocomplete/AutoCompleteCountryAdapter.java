package com.example.worldcountriesautocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCountryAdapter extends ArrayAdapter<CountryItem> {

    private List<CountryItem> countryItemListFull;
    public AutoCompleteCountryAdapter(Context context, List<CountryItem> countryItems) {
        super(context, 0, countryItems);
        countryItemListFull = new ArrayList<>(countryItems);
    }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_autocomplete_row,parent,false);
        }

        TextView name = convertView.findViewById(R.id.country_name);
        ImageView image = convertView.findViewById(R.id.country_flag);

        CountryItem countryItem = getItem(position);

        if (countryItem != null) {
            name.setText(countryItem.getCountryName());
            if (countryItem.getCountryImage() == 0){
                image.setImageResource(R.drawable.ic_flag_black_24dp);
            }else{
            image.setImageResource(countryItem.getCountryImage());
            }
        }
        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CountryItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() ==0) {
                suggestions.addAll(countryItemListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(CountryItem item : countryItemListFull){
                    if(item.getCountryName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CountryItem) resultValue).getCountryName();
        }
    };
}
