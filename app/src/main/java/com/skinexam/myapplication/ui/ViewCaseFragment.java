package com.skinexam.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.CustomCasePagerAdapter;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.TicketModel;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class ViewCaseFragment extends Fragment {

    private TextView txtTitle, txtSummery, txtAge, txtHealth, txtState;
    private WebView txtDocReply;
    private ViewPager recentViewpager;
    private List<String> arrayImagesList;
    private static int currentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_case, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtSummery = (TextView) view.findViewById(R.id.txtSummery);
        recentViewpager = (ViewPager) view.findViewById(R.id.recentViewpager);
        txtAge = (TextView) view.findViewById(R.id.txtAge);
        txtHealth = (TextView) view.findViewById(R.id.txtHealth);
        txtState = (TextView) view.findViewById(R.id.txtState);
        txtDocReply = (WebView) view.findViewById(R.id.txtDocReply);
        ticketResponse(view);
        Methods.showProgress(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void ticketResponse(final View view) {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.TICKET_JSON,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Methods.closeProgress();
                            if(getActivity()!=null && isAdded()){
                                TicketModel ticketModel = gson.fromJson(response, TicketModel.class);
                                    if (ticketModel.getStatus()){
                                        txtTitle.setText("Title : " + ticketModel.getTitle());
                                    }

                                if(TextUtils.isEmpty(ticketModel.getSummary())){
                                    txtSummery.setText("Summary : Not Available");
                                }else{
                                    txtSummery.setText("Summary : " + Html.fromHtml(ticketModel.getSummary()));
                                }

                                if(TextUtils.isEmpty(ticketModel.getAge())){
                                    txtAge.setText("Not Available");
                                }else{
                                    txtAge.setText("Age: " + ticketModel.getAge());
                                }

                                if(TextUtils.isEmpty(ticketModel.getHealth_status())){
                                    txtHealth.setText("Health : Not Available");
                                }else{
                                    txtHealth.setText("Health: " + ticketModel.getHealth_status());
                                }

                                txtDocReply.loadDataWithBaseURL(null, ticketModel.getTreatment(), "text/html", "utf-8", null);
                                

                                String state_itchy = ticketModel.getItchy();
                                String state_color = ticketModel.getChanging_color();
                                String state_bump = ticketModel.getFeels_like_bump();
                                if (state_itchy == null){
                                    state_itchy = "";
                                }
                                if (state_color == null){
                                    state_color = "";
                                }
                                if (state_bump == null){
                                    state_bump = "";
                                }
                                txtState.setText("State : " + state_itchy + state_color + state_bump );

                                List<String> list = new ArrayList<String>();
                                list.add(ticketModel.getImage1());
                                list.add(ticketModel.getImage2());
                                list.add(ticketModel.getImage3());
                                recentViewpager.setAdapter(new CustomCasePagerAdapter(getActivity(), list));
                                recentViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    /**
                                     * This method will be invoked when a new page becomes selected. Animation is not
                                     * necessarily complete.
                                     *
                                     * @param position Position index of the new selected page.
                                     */
                                    @Override
                                    public void onPageSelected(int position) {
                                        currentPage = position;
                                    }

                                    /**
                                     * Called when the scroll state changes. Useful for discovering when the user
                                     * begins dragging, when the pager is automatically settling to the current page,
                                     * or when it is fully stopped/idle.
                                     *
                                     * @param state The new scroll state.
                                     * @see ViewPager#SCROLL_STATE_IDLE
                                     * @see ViewPager#SCROLL_STATE_DRAGGING
                                     * @see ViewPager#SCROLL_STATE_SETTLING
                                     */
                                    @Override
                                    public void onPageScrollStateChanged(int state) {
//                                        if (state == ViewPager.SCROLL_STATE_IDLE) {
//                                            int pageCount = arrayImagesList.size();
//                                            if (currentPage == 0) {
//
//                                            }
//                                            else if (currentPage == pageCount - 1) {}
//                                        }
                                    }
                                });
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(getActivity()!=null && isAdded()){
                    Toast.makeText(getActivity(), "Some Problem Occured", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TICKET_ID, getArguments().getString("id"));
                params.put("clientsId", getString(Constants.TOKEN));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return addHeader();
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);
    }
    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        Log.e("Header", auth);
        return params;
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
}