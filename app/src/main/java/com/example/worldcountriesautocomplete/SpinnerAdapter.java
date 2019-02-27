package com.example.worldcountriesautocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<CountryItem> {
    public SpinnerAdapter(Context context, List countryList) {
        super(context, 0, countryList);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_autocomplete_row,parent,false);
        }
        TextView name = convertView.findViewById(R.id.country_name);
        ImageView image = convertView.findViewById(R.id.country_flag);

        CountryItem currentItem = getItem(position);

        if (currentItem != null) {
            name.setText(currentItem.getCountryName());
            if (currentItem.getCountryImage() == 0){
                image.setImageResource(R.drawable.ic_flag_black_24dp);
            }else{
                image.setImageResource(currentItem.getCountryImage());
            }
        }
        return convertView;
    }
}
