package com.skinexam.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.skinexam.myapplication.DashboardActivity;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.DemoHandler;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.LoginResponseModel;
import com.skinexam.myapplication.splah.Dash_all;
import com.skinexam.myapplication.splah.Dash_pend;
import com.skinexam.myapplication.splah.Dash_recent;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DashboardFragment extends Fragment implements Dash_recent.OnFragmentInteractionListener, Dash_pend.OnFragmentInteractionListener, Dash_all.OnFragmentInteractionListener, DemoHandler {

    private static final String TAG = "END";
    TextView textView_r, textView_p,textView_a;
    LoginResponseModel loginRespo;

    DashboardActivity dashboardActivity = new DashboardActivity();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TabLayout tabLayout = root.findViewById(R.id.dash_tab);

        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.notification_badge_recent));
        textView_r = (TextView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.text_pend);

        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.notification_badge_pend));
        textView_p = (TextView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.text_pend);

        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.notification_badge_all));
        textView_a = (TextView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.text_pend);

        for (int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.dash_tab);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = root.findViewById(R.id.dash_pager);
        final DashPagerAdapter adapter = new DashPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1){
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.gray));
                } else if (tab.getPosition() == 2){
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.gray));
                }else if (tab.getPosition() == 0){
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.gray));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return root;
    }

    public void onResume(){
        super.onResume();
        Chk();
    }

    private void Chk() {

        if (Methods.isOnline(getActivity())) {
            callDashboardData();
        } else {
            Toast.makeText(getActivity(), R.string.error_network_check, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpData(){
        if(loginRespo.getAllCount()==null){
            textView_a.setText("0");
        }else{
            textView_a.setText(loginRespo.getAllCount()+"");
        }

        if(loginRespo.getRecentCount()==null){
            textView_r.setText("0");
        }else{
            textView_r.setText(loginRespo.getRecentCount()+"");
        }

        if(loginRespo.getPendingCount()==null){
            textView_p.setText("0");
        }else{
            textView_p.setText(loginRespo.getPendingCount()+"");
        }
    }

    private void callDashboardData() {
            StringRequest sr = new StringRequest(Request.Method.POST, Constants.STUDENT_DASHBOARD_, new Response.Listener<String>() {
            public void onResponse(String response) {
                Gson gson = new Gson();
                Methods.closeProgress();

                loginRespo = gson.fromJson(response, LoginResponseModel.class);

                if(getActivity()!=null && isAdded()) {
                    if (loginRespo.getStatus()) {

                        setUpData();

                    } else {
                        Toast.makeText(getActivity(), loginRespo.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getActivity(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
//        Log.e("Header", auth);
        return params;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void view_detail(View view, int position, String content) {

    }
}