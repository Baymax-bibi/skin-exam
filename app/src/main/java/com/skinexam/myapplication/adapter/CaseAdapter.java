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
import com.skinexam.myapplication.utils.Constants;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CaseAdapter extends ArrayAdapter<BaseTicket> {

    List<String> ticketArrayList = null;
    Context mContext;

    public CaseAdapter(Context context, List<BaseTicket> objects) {
        super(context, 0, objects);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.lv_dash_all,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.lv_dash_img);
//        img.setImageResource(R.drawable.no_image);

        String imgURL = Constants.IMG_URL + getItem(position).getImage_1();

        new CaseAdapter.DownLoadImageTask(img).execute(imgURL);

//        byte[] imageBytes = Base64.decode(getItem(position).getImage_1(), Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        img.setImageBitmap(decodedImage);

        TextView lbl= (TextView) convertView.findViewById(R.id.lv_dash_con);
        int j=position;
        j++;
        lbl.setText("  " + getItem(position).getTicketTitle());
        return convertView;
    }

    static class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}