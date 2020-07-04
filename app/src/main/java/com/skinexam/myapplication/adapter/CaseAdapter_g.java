package com.skinexam.myapplication.adapter;

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
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CaseAdapter_g extends ArrayAdapter<BaseTicket> {

    Context mContext;

    public CaseAdapter_g(Context context, List<BaseTicket> objects) {
        super(context, 0, objects);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Methods.closeProgress();
        convertView= LayoutInflater.from(mContext).inflate(R.layout.gv_card,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.case_img);
        String imgURL = Constants.IMG_URL + getItem(position).getImage_1();

        new DownLoadImageTask(img).execute(imgURL);

        TextView lbl= (TextView) convertView.findViewById(R.id.case_content);
        lbl.setText("  " + getItem(position).getTicketTitle());
        return convertView;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}