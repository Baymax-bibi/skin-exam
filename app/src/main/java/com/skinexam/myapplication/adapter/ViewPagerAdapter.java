package com.skinexam.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

//    private ArrayList<Integer> IMAGES;
//    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> imageUrls;



    public ViewPagerAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls=imageUrls;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        Picasso.get()
//                .load(new File(imageUrls.get(position)))
//                .fit()
//                .centerCrop()
//                .into(imageView);

        byte[] imageBytes = Base64.decode(imageUrls.get(position), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(decodedImage);

        container.addView(imageView);



        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        return view.equals(object);
        return view == object;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

//    private Bitmap base64ToBitmap(String b64) {
//        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//    }

}
