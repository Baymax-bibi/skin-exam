package com.skinexam.myapplication.splah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.CaseAdapter;
import com.skinexam.myapplication.adapter.CaseAdapter_g;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.handler.CaseHandler;
import com.skinexam.myapplication.model.BaseTicket;
import com.skinexam.myapplication.model.DashModal;
import com.skinexam.myapplication.model.LoginResponseModel;
import com.skinexam.myapplication.ui.ViewCaseFragment;
import com.skinexam.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dash_pend.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dash_pend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dash_pend extends Fragment implements CaseHandler, AdapterView.OnItemClickListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CaseAdapter caseAdapter;
    List<DashModal> category;
    GridView gridView;
    private LoginResponseModel loginRespo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Dash_pend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dash_pend.
     */
    // TODO: Rename and change types and number of parameters
    public static Dash_pend newInstance(String param1, String param2) {
        Dash_pend fragment = new Dash_pend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dash_pend, container, false);
//        category = new ArrayList<DashModal>();
//        category.add(new DashModal(R.drawable.one, "one"));
//        category.add(new DashModal(R.drawable.serch, "two"));
//        category.add(new DashModal(R.drawable.two, "three"));
//        category.add(new DashModal(R.drawable.three, "four"));
//        category.add(new DashModal(R.drawable.four, "five"));

//        caseAdapter = new CaseAdapter(getContext(), category);
//        caseAdapter.setHandler(this);
//        gridView = root.findViewById(R.id.pend_grid);
//        gridView.setAdapter(caseAdapter);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) view.findViewById(R.id.pend_grid);
        gridView.setOnItemClickListener(this);
    }

    public void onResume(){
        super.onResume();
        Chk_online();
    }

    private void Chk_online() {

        if (Methods.isOnline(getActivity())) {
            callPendData();
        } else {
            Toast.makeText(getActivity(), R.string.error_network_check, Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpData(){
        List<BaseTicket> allAnsweredList = new ArrayList<BaseTicket>(loginRespo.getPendingTickets());
        gridView.setAdapter(new CaseAdapter_g(getActivity(), allAnsweredList));
    }

    private void callPendData() {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.STUDENT_DASHBOARD_, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            public void onResponse(String response) {
                Gson gson = new Gson();
                Methods.closeProgress();

                loginRespo = gson.fromJson(response, LoginResponseModel.class);

                if(getActivity()!=null && isAdded()) {
                    if (loginRespo.getStatus()) {

//                        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
//                        mEditor.putString("session_id", loginRespo.getSessionId());
//                        mEditor.apply();

                        if (loginRespo.getPendingCount() > 0){
                            setUpData();
                        }else {
//                            Toast.makeText(getActivity(),"There are " + loginRespo.getRecentCount().toString() + " date.", Toast.LENGTH_SHORT).show();
                        }

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void view_cases(View view, int position, String Content) {
        Toast.makeText(getContext(), "this is dash pend ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
