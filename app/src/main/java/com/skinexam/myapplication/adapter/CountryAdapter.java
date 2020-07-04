package com.skinexam.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.Country;

import java.util.List;

/**
 * Created by webwerks1 on 15/4/15.
 */
public class CountryAdapter extends ArrayAdapter<Country> {
    private Context context;

    public CountryAdapter(Context context, List<Country> data) {
        super(context, 0, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.simple_spinner_item, parent, false);
        }
        TextView CountryName = (TextView) row.findViewById(R.id.text1);
        CountryName.setText(getItem(position).getName());
        return row;
    }
}
