package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.skinexam.myapplication.R;

public class GoogleActivity extends Activity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_google);

        webView = (WebView) findViewById(R.id.webView_url);
        webView.loadUrl("https://skinexam.com/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}

