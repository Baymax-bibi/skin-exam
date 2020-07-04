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
import com.skinexam.myapplication.handler.CaseHandler;
import com.skinexam.myapplication.handler.RecentCaseHandler;
import com.skinexam.myapplication.model.DashModal;
import com.skinexam.myapplication.model.RecentDashModel;

import java.util.List;

public class RecentDashAdapter extends BaseAdapter {
    RecentCaseHandler handler;
    private List<RecentDashModel> category;
    private Context context;

    public RecentDashAdapter(Context context, List<RecentDashModel> category) {
        this.context = context;
        this.category = category;
    }

//    public CaseAdapter(Context context, List<DashModal> category) {
//    }

    public void setHandler(RecentCaseHandler handler){
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
        RecentDashHolder holder = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gv_card,null);
            holder = new RecentDashHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecentDashHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(category.get(position).getImage());
        holder.content.setText(category.get(position).getContent());

        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                handler.view_cases(view, position, category.get(position).getContent());
            }
        });
        return convertView;
    }

    private class RecentDashHolder {
        ImageView imageView;
        TextView content;

        public RecentDashHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.case_img);
            content = (TextView) view.findViewById(R.id.case_content);
        }
    }
}
