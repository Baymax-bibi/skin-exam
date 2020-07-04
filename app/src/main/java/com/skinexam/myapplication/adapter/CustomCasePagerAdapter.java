package com.skinexam.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by webwerks on 8/5/15.
 */
public class CustomCasePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> ticketArrayList = null;

    public CustomCasePagerAdapter(Context context, List<String> ticketArrayList) {
        this.ticketArrayList = ticketArrayList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return ticketArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ( object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.activity_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

//        Picasso picasso=new CustomPicasso().getImageLoader(mContext);
//        Picasso.get()
//                .load(Constants.IMG_URL + ticketArrayList.get(position))
//                .fit()
//                .centerCrop()
//                .into(imageView);
////        Picasso.with(mContext).load(Constants.IMG_URL + ticketArrayList.get(position)).into(imageView);
//        Log.e("imgagelocation", Constants.IMG_URL + ticketArrayList.get(position));
//
//        byte[] imageBytes = Base64.decode(ticketArrayList.get(position), Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        imageView.setImageBitmap(decodedImage);

        if (getString("subscriber").equals("1")){

        }

        Picasso.get()
                .load(Constants.IMG_URL + ticketArrayList.get(position))
                .fit()
                .centerCrop()
                .into(imageView);
//        Picasso.with(mContext).load(Constants.IMG_URL + ticketArrayList.get(position)).into(imageView);
//        Log.e("imgagelocation", Constants.IMG_URL + ticketArrayList.get(position));


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public synchronized String getString(String key) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
}