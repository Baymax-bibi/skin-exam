package com.skinexam.myapplication.splah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.skinexam.myapplication.adapter.CaseAdapter_his;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.BaseTicket;
import com.skinexam.myapplication.model.LoginResponseModel;
import com.skinexam.myapplication.ui.ViewCaseFragment_his;
import com.skinexam.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistroryActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, View.OnClickListener {

    private LoginResponseModel loginRespo;
    private ListView allcase_history;
    boolean doublePressedBackToExit = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);


        allcase_history = (ListView) findViewById(R.id.allcase_history);
        allcase_history.setOnItemClickListener(this);



        Chk_online();
    }

    private void Chk_online() {
        if (Methods.isOnline(HistroryActivity.this) ){
            callPendData();
        } else {
            Toast.makeText(HistroryActivity.this, R.string.error_network_check, Toast.LENGTH_SHORT).show();
        }
    }
    private void setUpData(){
        Methods.closeProgress();
        List<BaseTicket> allAnsweredList = new ArrayList<BaseTicket>(loginRespo.getPendingTickets());
        allcase_history.setAdapter(new CaseAdapter_his(HistroryActivity.this, allAnsweredList));

    }

    private void callPendData() {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.HISTORY, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            public void onResponse(String response) {
                Gson gson = new Gson();

                loginRespo = gson.fromJson(response, LoginResponseModel.class);
//                Log.e("history response", response);

                    if (loginRespo.getStatus()) {

                        Methods.showProgress(HistroryActivity.this);
                        setUpData();

                    } else {
                        Toast.makeText(HistroryActivity.this, loginRespo.getMsg(), Toast.LENGTH_LONG).show();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Methods.showAlertDialog(getString(R.string.error_network_check), HistroryActivity.this);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(HistroryActivity.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                SharedPreferences mSharedPreferences = HistroryActivity.this.getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
                String PARAM_TOKEN = mSharedPreferences.getString(Constants.TOKEN, "");

                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, PARAM_TOKEN);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        MySingleton.getInstance(HistroryActivity.this).addToRequestQueue(sr);
    }
    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        view.setBackgroundColor(getColor(R.color.colorPrimary));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ViewCaseFragment_his frag = new ViewCaseFragment_his();
        Bundle data = new Bundle();
        data.putString("id", ((BaseTicket)parent.getItemAtPosition(position)).getTicketId());
        frag.setArguments(data);
        transaction.replace(R.id.history_act, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void onBackPressed() {

        // Check if NavigationDrawer is Opened
        if (doublePressedBackToExit) {
            Intent intent = new Intent(HistroryActivity.this, DashboardActivity.class);
            startActivity(intent);

        }
        else {
            this.doublePressedBackToExit = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            // Delay of 2 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Set doublePressedBackToExit false after 2 seconds
                    doublePressedBackToExit = false;
                }
            }, 2000);
        }
    }
}