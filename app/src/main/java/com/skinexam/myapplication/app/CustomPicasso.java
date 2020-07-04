package com.skinexam.myapplication.app;

import android.util.Base64;

import com.skinexam.myapplication.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by webwerks on 2/6/15.
 */
public class CustomPicasso {
    private static Picasso sPicasso;

    public CustomPicasso() {
    }

//    public static Picasso getImageLoader(final Context context) {
//        if (sPicasso == null) {
//            Picasso.Builder builder = new Picasso.Builder(context);
//            builder.downloader(new CustomOkHttpDownloader(context));
//            sPicasso = builder.build();
//        }
//        return sPicasso;
//    }

    static  String creds = String.format("%s:%s", Constants.Auth_UserName, Constants.Auth_Password);
    static String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);

//   private static class CustomOkHttpDownloader extends OkHttp3Downloader {
//        public CustomOkHttpDownloader(Context context) {
//            super(context);
//        }
//
////        protected HttpURLConnection openConnection(final Uri uri) throws IOException {
////            HttpURLConnection connection;
////            connection = super.openConnection(uri);
////            connection.setRequestProperty("Authorization", auth);
////            return connection;
////        }
//    }
}
