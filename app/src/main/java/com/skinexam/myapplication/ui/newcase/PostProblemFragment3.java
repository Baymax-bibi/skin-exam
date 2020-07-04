package com.skinexam.myapplication.ui.newcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.DashboardActivity;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.CreateCaseResultModel;
import com.skinexam.myapplication.splah.ImageBuffer;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class PostProblemFragment3 extends Fragment implements View.OnClickListener {

    String title, des1, des2, des3, concern, spinage, spinhealth, spinbody, spinused, spinhealth_id, spinbody_id, spinused_id, itchy_id = "0", changeColor_id = "0", bumper_id = "0";
    TextView subscrpitionText;
    TextView submit_btn;

    CreateCaseResultModel createCaseResultModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_post_problem_3, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscrpitionText = (TextView) view.findViewById(R.id.subscriptiontext);
        if(getString("subscriber").equals("1")){
            subscrpitionText.setText("Your image will be saved for future comparisons");
        }
        if (savedInstanceState == null){
                title = ImageBuffer.TITLE;
                des1 = ImageBuffer.EDITDEST1;
                des2 = ImageBuffer.EDITDEST2;
                des3 = ImageBuffer.EDITDEST3;
                concern = ImageBuffer.CONCERN;

                spinage = ImageBuffer.SPINAGE;
                spinhealth = ImageBuffer.SPINHEALTH;
                spinbody = ImageBuffer.SPINBODY;
                spinused = ImageBuffer.SPINUSED;

                spinhealth_id = ImageBuffer.SPINHEALTH_ID;
                spinbody_id = ImageBuffer.SPINBODY_ID;
                spinused_id = ImageBuffer.SPINUSED_ID;

            }

        submit_btn = (TextView) view.findViewById(R.id.caseSubmit);
        submit_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                case R.id.caseSubmit:
                    Methods.showProgress(getContext());
                    Create_case();
                    break;

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
                        Toast.makeText(getContext(), createCaseResultModel.getImage_msg(), Toast.LENGTH_SHORT).show();

                        if (getBoolean("SaveLogin")){
                            Intent toDash = new Intent(getContext(), DashboardActivity.class);
                            startActivity(toDash);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
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
        MySingleton.getInstance(getContext()).addToRequestQueue(srStatus);
    }



    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
    public String getString(String key) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
    public synchronized boolean getBoolean(String key) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        Boolean  selected =  mSharedPreferences.getBoolean(key, false);
        return selected;
    }
}
