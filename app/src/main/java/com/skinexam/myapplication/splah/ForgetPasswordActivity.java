package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.ForgotPasswordResponseModel;
import com.skinexam.myapplication.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener {

    public EditText edtEmailAddress;
    public TextView password_view;
    Button send_btn;
    String eMail;
    public String login_type = "st";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgetpassword);

        init();
    }

    private void init() {
        edtEmailAddress = (EditText) findViewById(R.id.edtEmailAddress);
        password_view = (TextView) findViewById(R.id.password_view);
//        findViewById(R.id.btnSend).setOnClickListener(this);
        send_btn = (Button) findViewById(R.id.btnSend);
        send_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend:
                Chk_online();
                break;
        }
    }

    private void Chk_online() {

        if (Methods.isOnline(this)) {
            eMail = edtEmailAddress.getText().toString().trim();
            if (eMail.equals("")){
                Toast.makeText(ForgetPasswordActivity.this, "Please input Email", Toast.LENGTH_SHORT).show();
            }else {
                if (Validation.isValidEmail(eMail)){
                    Methods.showProgress(this);
                    sendEmail();
                }else {
                    Toast.makeText(ForgetPasswordActivity.this, "Please input valid Email", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, R.string.error_network_check, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
//        Log.e("SendEmail", "Entered");

        StringRequest sr = new StringRequest(Request.Method.POST, Constants.GETEMAIL_JSON, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Methods.closeProgress();
//                        Log.e("forgotPasswordRespo", response);
                        Gson gson = new Gson();
                        ForgotPasswordResponseModel mailResponse = gson.fromJson(response, ForgotPasswordResponseModel.class);
                        if(mailResponse!=null){
                            Toast.makeText(ForgetPasswordActivity.this, mailResponse.getMsg(), Toast.LENGTH_LONG).show();
//                            password_view.setText(mailResponse.getPassword());
//                            if (mailResponse.getStatus()) {
//                                finish();
//                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Methods.closeProgress();
                Methods.showAlertDialog(getString(R.string.error_network_check), ForgetPasswordActivity.this);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
//                SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
//                String  PARAM_USERNAME_F =  mSharedPreferences.getString(Constants.PARAM_USERNAME, "");
//                String PARAM_PASSWORD_F = mSharedPreferences.getString(Constants.PARAM_PASSWORD, "");

                Map<String, String> params = new HashMap<String, String>();
//                params.put(Constants.LOGIN_TYPE, login_type);
                params.put(Constants.EMAIL, eMail);
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        Log.e("Header", auth);
        return params;
    }
}
