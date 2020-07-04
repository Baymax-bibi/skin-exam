package com.skinexam.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;


import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.MyCheckBoxModel;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;

    public static ArrayList<MyCheckBoxModel> modelArrayList;

    public CustomAdapter(Context _context, ArrayList<MyCheckBoxModel> _modelArrayList){
        this.context = _context;
        modelArrayList = _modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
//            holder.tvAnimal = (TextView) convertView.findViewById(R.id.animal);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText("\t "+modelArrayList.get(position).getState());
//        holder.tvAnimal.setText(modelArrayList.get(position).getAnimal());

        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
//                TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer)  holder.checkBox.getTag();
//                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(modelArrayList.get(pos).getSelected()){
                    modelArrayList.get(pos).setSelected(false);
                }else {
                    modelArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        CheckBox checkBox;
//        private TextView tvAnimal;
    }
}
