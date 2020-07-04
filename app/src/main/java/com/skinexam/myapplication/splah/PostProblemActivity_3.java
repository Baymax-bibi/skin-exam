package com.skinexam.myapplication.splah;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.DashboardActivity;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.BaseActivity;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.CreateCaseResultModel;
import com.skinexam.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostProblemActivity_3  extends AppCompatActivity implements View.OnClickListener {

    String title, des1, des2, des3, concern, spinage, spinhealth, spinbody, spinused, spinhealth_id, spinbody_id, spinused_id, itchy_id = "0", changeColor_id = "0", bumper_id = "0";
    TextView subscrpitionText;
    TextView submit_btn;

    BaseActivity baseActivity;

    ArrayList<String> images = new ArrayList<String>();

    CreateCaseResultModel createCaseResultModel;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_problem_3);

        subscrpitionText = (TextView) findViewById(R.id.subscriptiontext);
        if(getString("subscriber").equals("1")){
            subscrpitionText.setText("Your image will be saved for future comparisons");
        }

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                title = null;
            }else {
                title = extras.getString("TITLE");
                des1 = extras.getString("EDITDEST1");
                des2 = extras.getString("EDITDEST2");
                des3 = extras.getString("EDITDEST3");
                concern = extras.getString("CONCERN");

                spinage = extras.getString("SPINAGE");
                spinhealth = extras.getString("SPINHEALTH");
                spinbody = extras.getString("SPINBODY");
                spinused = extras.getString("SPINUSED");

                spinhealth_id = extras.getString("SPINHEALTH_ID");
                spinbody_id = extras.getString("SPINBODY_ID");
                spinused_id = extras.getString("SPINHEALTH_ID");

                baseActivity = new BaseActivity();

            }
        }else {
            title = (String) savedInstanceState.getSerializable("TITLE");
        }

        submit_btn = (TextView) findViewById(R.id.caseSubmit);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.caseSubmit:
                Methods.showProgress(PostProblemActivity_3.this);
                Create_case();
        }
    }

    public void Create_case() {
        final StringRequest srStatus = new StringRequest(Request.Method.POST, Constants.CREATECASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Methods.closeProgress();
                        Gson gson = new Gson();
                        createCaseResultModel = gson.fromJson(response, CreateCaseResultModel.class);
                        Log.e("postPro_2", createCaseResultModel.getImage_msg());

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(PostProblemActivity_3.this, createCaseResultModel.getImage_msg(), Toast.LENGTH_SHORT).show();
                        ImageBuffer.image1 = "";
                        ImageBuffer.image2 = "";
                        ImageBuffer.image3 = "";

                        if (getBoolean("SaveLogin")){
                            Intent toDash = new Intent(PostProblemActivity_3.this, DashboardActivity.class);
                            startActivity(toDash);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_3.this);
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, getString(Constants.TOKEN));
                params.put(Constants.TITLE, title);
                params.put(Constants.SUMMERY, "");
                params.put(Constants.AGE_DATA, spinage);
                params.put(Constants.HEALTH_STATUS_ID, spinhealth_id);
                params.put(Constants.USERBODYPART, spinbody_id);
                params.put(Constants.PRIVTREAT, spinused);
                params.put(Constants.TICKET_DESCRIPTION, concern);
                params.put(Constants.DES_1, des1);
                params.put(Constants.DES_2, des2);
                params.put(Constants.DES_3, des3);
                params.put(Constants.ITCHY, itchy_id);
                params.put(Constants.CHANGECOLOR, changeColor_id);
                params.put(Constants.FEELBUMP, bumper_id);
                if (!TextUtils.isEmpty(ImageBuffer.image1)){
                    params.put(Constants.IMAGE1, ImageBuffer.image1);

                }
                if (!TextUtils.isEmpty(ImageBuffer.image2)){
                    params.put(Constants.IMAGE2, ImageBuffer.image2);
                }
                if (!TextUtils.isEmpty(ImageBuffer.image3)){
                    params.put(Constants.IMAGE3, ImageBuffer.image3);
                }
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return addHeader();
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(srStatus);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
    public String getString(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
    public synchronized boolean getBoolean(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        Boolean  selected =  mSharedPreferences.getBoolean(key, false);
        return selected;
    }
}