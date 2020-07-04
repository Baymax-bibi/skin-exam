package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.DashboardActivity;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.LoginResponseModel;
import com.skinexam.myapplication.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends Activity implements View.OnClickListener {
    TextView com_tv, txtForgotPass_tv, txtReg;
    Button login_btn;
    public String Email, Pass;
    EditText edtEmail, edtPass;
    private CheckBox chkRememberPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signin);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);

        com_tv = (TextView) findViewById(R.id.visit_com);
        com_tv.setOnClickListener(this);

        login_btn = (Button) findViewById(R.id.btnLogin);
        login_btn.setOnClickListener(this);

        chkRememberPassword = (CheckBox) findViewById(R.id.chkRememberPassword);

        txtForgotPass_tv = (TextView) findViewById(R.id.txtForgotPass);
        txtForgotPass_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetintent = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetintent);
            }
        });

        txtReg = (TextView) findViewById(R.id.txtRegister);
        txtReg.setOnClickListener(this);

        if (getBoolean("SaveLogin")) {
            edtEmail.setText(getString(Constants.EMAIL));
            edtPass.setText(getString(Constants.PARAM_PASSWORD));
            chkRememberPassword.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                ChkLogin();
                break;
            case R.id.txtRegister:
                Intent userReg = new Intent(this, UserRegister.class);
                startActivity(userReg);
                break;
            case R.id.visit_com:
                Intent intent = new Intent(this, GoogleActivity.class);
                startActivity(intent);
        }
    }

    private void ChkLogin() {
        Email = edtEmail.getText().toString().trim();
        Pass = edtPass.getText().toString().trim();

        if (Methods.isOnline(SignInActivity.this)) {
            if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Pass)) {
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show();
            } else {
                if (Validation.isValidEmail(Email)) {
                    Methods.showProgress(SignInActivity.this);
                    callLoginService();
                } else {
                    Toast.makeText(this, "Please enter valid EmailId", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please check Internet connection ", Toast.LENGTH_SHORT).show();
        }
    }

    public void callLoginService() {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.STUDENT_LOGIN, new Response.Listener<String>() {
            public void onResponse(String response) {
                Gson gson = new Gson();
                Methods.closeProgress();

                LoginResponseModel loginRespo = gson.fromJson(response, LoginResponseModel.class);

                if(loginRespo!=null) {
                    if (loginRespo.getStatus()) {
                        if (chkRememberPassword.isChecked()) {

                            insertBoolean("SaveLogin", true);
                            insertString(Constants.EMAIL, Email);
                            insertString(Constants.PARAM_PASSWORD, Pass);

                        } else {
                            clear();
                        }

                        insertString(Constants.TOKEN, loginRespo.getToken());
                        finish();
                        startActivity(new Intent(SignInActivity.this, DashboardActivity.class));

                    } else {
                        Toast.makeText(SignInActivity.this, loginRespo.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Methods.showAlertDialog(getString(R.string.error_network_check), SignInActivity.this);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, edtEmail.getText().toString().trim());
                params.put(Constants.PARAM_PASSWORD, edtPass.getText().toString().trim());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }



    public synchronized void insertString(String key, String value) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public synchronized void insertBoolean(String key, boolean value) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public synchronized void clear() {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public synchronized String getString(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }

    public synchronized boolean getBoolean(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        Boolean  selected =  mSharedPreferences.getBoolean(key, false);
        return selected;
    }

    public void onBackPressed() {
        //finish();
        //System.exit(0);
        moveTaskToBack(true);

    }
}
