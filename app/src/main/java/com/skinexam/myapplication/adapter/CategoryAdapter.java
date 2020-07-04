package com.skinexam.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    DemoHandler handler;
    private List<CategoryModel> category;
    private Context context;

    public CategoryAdapter(Context _context, List<CategoryModel> _category) {
        this.context = _context;
        this.category = _category;
    }

    public void setHandler(DemoHandler handler){
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int position) {
        return category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_dash_all,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.imageView.setImageResource(category.get(position).getImage());
        holder.content.setText(category.get(position).getContent());

        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                handler.view_detail(view, position, category.get(position).getContent());
            }
        });
        holder.content.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                handler.view_detail(view, position, category.get(position).getContent());
            }
        });
        return convertView;
    }

    static class Holder {
        ImageView imageView;
        TextView content;

        public Holder(View view){
            imageView = (ImageView) view.findViewById(R.id.lv_dash_img);
            content = (TextView) view.findViewById(R.id.lv_dash_con);
        }
    }
}
