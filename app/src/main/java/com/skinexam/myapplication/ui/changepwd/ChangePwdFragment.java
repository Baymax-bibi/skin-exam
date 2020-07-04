package com.skinexam.myapplication.ui.changepwd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.ChangePassResponseModel;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;
import com.skinexam.myapplication.utils.Validation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChangePwdFragment extends Fragment implements View.OnClickListener {

    private ChangePwdViewModel shareViewModel;

    public EditText edtOldPass, edtNewPass, edtConfirmPass;
    public String userId, sessionId;
    //String old_pass, new_pass, confirm_pass;

    Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ChangePwdViewModel.class);
        View root = inflater.inflate(R.layout.fragment_changepwd, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtOldPass = (EditText) view.findViewById(R.id.edtOldPass);
        edtNewPass = (EditText) view.findViewById(R.id.edtNewPass);
        edtConfirmPass = (EditText) view.findViewById(R.id.edtConfirmPass);
        view.findViewById(R.id.btnSubmit_pass).setOnClickListener(this);
        userId = getString("user_id");
        sessionId = getString("session_id");
    }

    public void changePassword() {
        StringRequest srChangPass = new StringRequest(Request.Method.POST, Constants.CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Methods.closeProgress();
                        Gson gson = new Gson();
                        ChangePassResponseModel objRes = gson.fromJson(response, ChangePassResponseModel.class);

                        if(objRes!=null){
                            Toast.makeText(getActivity(),objRes.getMsg(),Toast.LENGTH_SHORT).show();
                            if (objRes.getStatus()) {
                                insertString(Constants.PARAM_PASSWORD, edtNewPass.getText().toString());
                                clear();
//                                startActivity(new Intent(getActivity(), ChangePwdFragment.class));
                                getActivity().onBackPressed();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.closeProgress();
                        Methods.showAlertDialog(getString(R.string.error_network_check), mContext);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
                String PARAM_TOKEN = mSharedPreferences.getString(Constants.TOKEN, "");

                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, PARAM_TOKEN);
                params.put(Constants.OLD_PASSWORD, edtOldPass.getText().toString());
                params.put(Constants.NEW_PASSWORD, edtNewPass.getText().toString());
                //params.put(Constants.CONFIRM_PASSWORD, edtConfirmPass.getText().toString());
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(srChangPass);
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtOldPass,"Please enter old password")) return ret = false;
        if (!Validation.hasText(edtNewPass,"Please enter new password")) return ret = false;
        if (!Validation.hasText(edtConfirmPass,"Please enter confirm password")) return ret = false;
        return ret;
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        Log.e("Header", auth);
        return params;
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }

    public synchronized void insertString(String key, String value) {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void clear() {
        edtOldPass.setText((""));
        edtNewPass.setText("");
        edtConfirmPass.setText("");
    }

    @Override
    public void onClick(View view) {
//        old_pass = edtOldPass.getText().toString();
//        new_pass = edtNewPass.getText().toString();
//        confirm_pass = edtConfirmPass.getText().toString();
        if (checkValidation()) {
            if (!edtNewPass.getText().toString().equals(edtConfirmPass.getText().toString())) {
                Toast.makeText(getActivity(), "Password and confirm password are not match", Toast.LENGTH_LONG).show();
                edtNewPass.setText("");
                edtConfirmPass.setText("");
            } else {
                Methods.showProgress(getActivity());
                changePassword();
            }
        }
    }
}