package com.skinexam.myapplication.ui.mycase;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.CaseAdapter;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.BaseTicket;
import com.skinexam.myapplication.model.LoginResponseModel;
import com.skinexam.myapplication.splah.Dash_all;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.ui.ViewCaseFragment;
import com.skinexam.myapplication.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MycaseFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private MycaseViewModel toolsViewModel;

    private DatePicker datePicker;
    private Button btnDone;
    TextView txtFrom, txtTo, lblMessage;
    DatePickerDialog datePickerDialog;
    boolean btnClick=false;

    int offset=0;
    ViewPager casePager;
    ArrayList<BaseTicket> caseList;
    String fromDate="",toDate="";

    private ListView listView_re;
    private ListView listView_pe;

    private LoginResponseModel loginRespo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(MycaseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycase, container, false);

        return root;
    }

    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        txtFrom = (TextView) view.findViewById(R.id.txtFrom);
        txtTo = (TextView) view.findViewById(R.id.txtTo);
        //lblMessage= (TextView) view.findViewById(R.id.lblMessage);
        view.findViewById(R.id.btnSubmit).setOnClickListener(this);
        txtFrom.setOnClickListener(this);
        txtTo.setOnClickListener(this);

        listView_re = (ListView) view.findViewById(R.id.myall_re);
        listView_re.setOnItemClickListener(this);

        listView_pe = (ListView) view.findViewById(R.id.myall_pe);
        listView_pe.setOnItemClickListener(this);
    }

    private void setUpData(){
//        List<BaseTicket> allAnsweredList_re = new ArrayList<BaseTicket>(loginRespo.getAllTickets_re());
//        listView_re.setAdapter(new CaseAdapter(getActivity(), allAnsweredList_re));
//
//        List<BaseTicket> allAnsweredList_pe = new ArrayList<BaseTicket>(loginRespo.getAllTickets_pe());
//        listView_pe.setAdapter(new CaseAdapter(getActivity(), allAnsweredList_pe));
//
//        Dash_all.ListUtils.setDynamicHeight(listView_re);
//        Dash_all.ListUtils.setDynamicHeight(listView_pe);

        if (loginRespo.getRecentCount() > 0){
            List<BaseTicket> allAnsweredList_re = new ArrayList<BaseTicket>(loginRespo.getAllTickets_re());
            listView_re.setAdapter(new CaseAdapter(getActivity(), allAnsweredList_re));
            Dash_all.ListUtils.setDynamicHeight(listView_re);
        }

        if (loginRespo.getPendingCount() > 0){

            List<BaseTicket> allAnsweredList_pe = new ArrayList<BaseTicket>(loginRespo.getAllTickets_pe());
            listView_pe.setAdapter(new CaseAdapter(getActivity(), allAnsweredList_pe));
            Dash_all.ListUtils.setDynamicHeight(listView_pe);
        }
    }

    public void getData(final String fromDate, final String toDate) {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.MYCASE, new Response.Listener<String>() {
        @SuppressLint("LongLogTag")
        public void onResponse(String response) {
            Gson gson = new Gson();
            Methods.closeProgress();

            loginRespo = gson.fromJson(response, LoginResponseModel.class);

            if(getActivity()!=null && isAdded()) {
                if (loginRespo.getStatus()) {

                    SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
//                    mEditor.putString("session_id", loginRespo.getSessionId());
                    mEditor.apply();

                    setUpData();

                } else {
                    Toast.makeText(getActivity(), loginRespo.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Methods.showAlertDialog(getString(R.string.error_network_check), getActivity());
            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    Toast.makeText(getActivity(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }) {
        protected Map<String, String> getParams() {
//            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
//            String PARAM_TOKEN = mSharedPreferences.getString(Constants.TOKEN, "");

            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.TOKEN, getString(Constants.TOKEN));
            params.put(Constants.FROM_DATE, fromDate);
            params.put(Constants.TO_DATE, toDate);
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
        return params;
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtFrom:
                final Calendar c_f = Calendar.getInstance();
                int mYear = c_f.get(Calendar.YEAR); // current year
                int mMonth = c_f.get(Calendar.MONTH); // current month
                int mDay = c_f.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),
                        new DatePickerDialog.OnDateSetListener() {


                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String month = ""+(monthOfYear+1), day = ""+dayOfMonth;
                                if ((monthOfYear+1) < 10){
                                    month = "0"+(monthOfYear+1);
                                }
                                if (dayOfMonth <10){
                                    day = "0"+dayOfMonth;
                                }

                                txtFrom.setText(year + "-"+ month + "-" + day);

//                                txtFrom.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.txtTo:
                final Calendar c_t = Calendar.getInstance();
                int mYear_t = c_t.get(Calendar.YEAR); // current year
                int mMonth_t = c_t.get(Calendar.MONTH); // current month
                int mDay_T = c_t.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String month_t = ""+(monthOfYear+1), day_t = ""+dayOfMonth;
                                if ((monthOfYear+1) < 10){
                                    month_t = "0"+(monthOfYear+1);
                                }
                                if (dayOfMonth <10){
                                    day_t = "0"+dayOfMonth;
                                }
                                txtTo.setText(year+"-"+month_t+"-"+day_t);
                            }

                        }, mYear_t, mMonth_t, mDay_T);
                datePickerDialog.show();
                break;
            case R.id.btnSubmit:
                btnClick=true;

                fromDate = txtFrom.getText().toString().substring(txtFrom.getText().toString().indexOf(":")+1,txtFrom.getText().toString().length());
                toDate = txtTo.getText().toString().substring(txtTo.getText().toString().indexOf(":")+1,txtTo.getText().toString().length());

                int data = 1;

                if ((fromDate.length() <= 1)|| (toDate.length() <= 1)) {
                    Methods.showAlertDialog("Please Select FromDate and ToDate", getActivity());
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(fromDate);
                        Date date2 = sdf.parse(toDate);
                        if (date1.after(date2)) {
                            String tempDate;
                            tempDate = fromDate;
                            fromDate = toDate;
                            toDate = tempDate;
//                            data = 0;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (data == 1) {
                        offset=0;
                        Methods.showProgress(getActivity());
                        getData(fromDate.trim(), toDate.trim());
                        Log.e("from", fromDate);
                        Log.e("to", toDate);
//                        Log.e("Time", fromDate+"--"+toDate);
                    } else
                        Toast.makeText(getActivity(), "Select Proper Date", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ViewCaseFragment frag = new ViewCaseFragment();
        Bundle data = new Bundle();
        data.putString("id", ((BaseTicket)parent.getItemAtPosition(position)).getTicketId());
        frag.setArguments(data);
        transaction.replace(R.id.nav_host_fragment, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private String toTineger_m(int month) {
        return "0"+month;
    }
    private String toTineger_d(int day) {
        return "0"+day;
    }

}