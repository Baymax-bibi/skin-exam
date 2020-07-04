package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.RegisterResponseModel;
import com.skinexam.myapplication.utils.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserRegister extends Activity implements View.OnClickListener {

    TextView signIn;
    EditText fName, lName, edtEmail, edtPass, edtConfPass;
    Button btnRegist;
    CheckBox chkTerm;
    String Email, Pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_reg);

        signIn = findViewById(R.id.SignIn_tv);
        signIn.setOnClickListener(this);

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        edtEmail = findViewById(R.id.edtEmail_reg);
        edtPass = findViewById(R.id.edtPass_reg);
        edtConfPass = findViewById(R.id.edt_cnf_pass);
        btnRegist = findViewById(R.id.btnRegist);

        chkTerm = (CheckBox) findViewById(R.id.chkTerm);
        btnRegist.setOnClickListener(this);

        SpannableString spannableString = new SpannableString("I agree to Terms and Conditions");
        spannableString.setSpan(new UnderlineSpan(), 11, 31, 0);
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(UserRegister.this, PrivacyPolicyActivity.class));
            }
        };

        spannableString.setSpan(clickSpan, 11, 31, 0);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.ColorPrimaryDark)), 11, 31, 0);
        chkTerm.setText(spannableString);
        chkTerm.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void Chk_online() {

        Email = edtEmail.getText().toString().trim();
        Pass = edtPass.getText().toString().trim();

        if (Methods.isOnline(UserRegister.this)) {
            if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Pass)) {
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show();
            } else {
                if (Validation.isValidEmail(Email)) {
                    Methods.showProgress(UserRegister.this);
                    registeration();
                } else {
                    Toast.makeText(this, "Please enter valid EmailId", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please check Internet connection ", Toast.LENGTH_SHORT).show();
        }
    }

    public void registeration() {
        Methods.showProgress(this);
        StringRequest srReg = new StringRequest(Request.Method.POST, Constants.STUDENT_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Methods.closeProgress();
                        Log.e("registration response",response);

                        Gson gson = new Gson();
                        RegisterResponseModel objRes = gson.fromJson(response, RegisterResponseModel.class);

                        if(objRes!=null){
                            if (objRes.getStatus()) {
                                Toast.makeText(UserRegister.this, objRes.getMsg(), Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(UserRegister.this,objRes.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), UserRegister.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.FIRST_NAME, fName.getText().toString());
                params.put(Constants.LAST_NAME, lName.getText().toString());
                params.put(Constants.ADDRESS, " ");
                params.put(Constants.CITY_NAME, " ");
                params.put(Constants.STATE, " ");
                params.put(Constants.ZIP_CODE, " ");
                params.put(Constants.COUNTRY_ID, " ");
                params.put(Constants.TEL_NO, " ");
                params.put(Constants.MOB_NO, " ");
                params.put(Constants.EMAIL, edtEmail.getText().toString());
                params.put(Constants.PARAM_PASSWORD, edtPass.getText().toString());
                params.put(Constants.CON_PASSWORD, edtConfPass.getText().toString());
                params.put(Constants.SUBS, "");
                return checkParams(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(srReg);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SignIn_tv:
                Intent signintent = new Intent(UserRegister.this, SignInActivity.class);
                startActivity(signintent);
                break;
            case R.id.btnRegist:
                if (edtPass.getText().toString().equals(edtConfPass.getText().toString())){
                    Chk_online();
                }else {
                    Toast.makeText(UserRegister.this, "Password and confirm password Doesn't match", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
