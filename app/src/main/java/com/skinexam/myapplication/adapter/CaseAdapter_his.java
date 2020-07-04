package com.skinexam.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.BaseTicket;
import com.skinexam.myapplication.utils.Constants;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CaseAdapter_his extends ArrayAdapter<BaseTicket> {

    List<String> ticketArrayList = null;
    Context mContext;

    public CaseAdapter_his(Context context, List<BaseTicket> objects) {
        super(context, 0, objects);
        mContext=context;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.lv_his,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.lv_his_img);

        String imgURL = Constants.IMG_URL + getItem(position).getImage_1();
        new CaseAdapter_his.DownLoadImageTask(img).execute(imgURL);
        TextView lbl= (TextView) convertView.findViewById(R.id.lv_his_con);
        lbl.setText(getItem(position).getTicketTitle());

        TextView date= (TextView) convertView.findViewById(R.id.lv_his_date);
        date.setText(getItem(position).getDate());

        return convertView;
    }

    static class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView imageView;

        DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}