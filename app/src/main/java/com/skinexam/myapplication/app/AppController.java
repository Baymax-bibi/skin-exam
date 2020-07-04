package com.skinexam.myapplication.app;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Created by webwerks1 on 11/4/15.
 */
public class AppController extends MultiDexApplication {
    private boolean fromAddCase;
    private static AppController mInstance;

    public boolean isFromAddCase() {
        return fromAddCase;
    }

    public void setFromAddCase(boolean fromAddCase) {
        this.fromAddCase = fromAddCase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static final AppController getInstance(Context context) {
        return mInstance;
    }
}
