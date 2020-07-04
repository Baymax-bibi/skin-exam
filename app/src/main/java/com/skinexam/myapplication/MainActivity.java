package com.skinexam.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.skinexam.myapplication.utils.Constants;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showBasicSplash();
    }

    private void showBasicSplash() {
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1500);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        public void handleMessage(Message m){
            if(getBoolean("SaveLogin")){
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, com.skinexam.myapplication.splah.SplashActivity.class);
                startActivity(intent);
            }
            finish();
        }
    };

    public synchronized boolean getBoolean(String key) {
//                return getPreferences().getBoolean(key, false);
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        Boolean  selected =  mSharedPreferences.getBoolean(key, false);
        return selected;
    }
}
