package com.skinexam.myapplication.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.skinexam.myapplication.ui.ImageDecoding;
import com.skinexam.myapplication.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Satyawan on 2/4/15.
 */
public class BaseActivity extends AppCompatActivity {


	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}

    private static AlertDialog.Builder builder = null;

    public static void Alert(String msg, Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setTitle("Alert !")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();

    }

    public Map<String, String> addHeader(){
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        Log.e("Header", auth);
        return params;
    }

    public String encodeImgTOBase64(String path,String extension){
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Bitmap bm = ImageDecoding.decodeBitmapFromFile(path,metrics.widthPixels, 300);
//        Bitmap bm = ImageDecoding.decodeBitmapFromFile(path,600, 407);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")){
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }else if(extension.equalsIgnoreCase("png")){
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


}
