package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.skinexam.myapplication.R;

/**
 * Created by webwerks on 11/5/15 for Registration page Term And Condition.
 */
public class PrivacyPolicyActivity extends Activity
{

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        webView = (WebView) findViewById(R.id.container1);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Methods.isOnline(getApplicationContext())) {
            webView.loadUrl("https://blessing.skinexam.com/terms.php");
        } else {
            webView.loadData("Please Check your internet connection!", "text/html", "UTF-8");
        }

    }
}