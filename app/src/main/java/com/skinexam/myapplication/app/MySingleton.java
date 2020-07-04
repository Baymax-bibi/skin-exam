package com.skinexam.myapplication.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by webwerks1 on 13/4/15.
 */
public class MySingleton {

    private static MySingleton instance;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag("App");
        getRequestQueue().add(req);
    }
}
