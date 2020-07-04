package com.skinexam.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.PatientHealth;

import java.util.List;

/**
 * Created by satyawan on 23/4/15.
 */
public class HealthAdapter extends ArrayAdapter<PatientHealth>{
 private Context context;

    public HealthAdapter(Context context, List<PatientHealth> data) {
        super(context, 0, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.simple_spinner_item,null);
        TextView lblValue=(TextView)convertView.findViewById(R.id.text1);
        lblValue.setText(getItem(position).getStatus());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
