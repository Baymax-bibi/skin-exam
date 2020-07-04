package com.skinexam.myapplication.ui.editprofile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.ProfileDataModel;
import com.skinexam.myapplication.model.RegisterResponseModel;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.utils.Constants;
import com.skinexam.myapplication.utils.Validation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class EditprofileFragment extends Fragment implements View.OnClickListener{
    EditText edtFirstName, edtLastName, edtAddress, edtCity, edtState, edtZipCode, edtHomeTel, edtCellPhone, edtEmail;
    AutoCompleteTextView aet_Country;
    Button btnSave;
    ProfileDataModel profileRespo;
    String[] countries = {"Afghanistan", "Albania", "Albania", "Albania", "Albania", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin","Bhutan",
            "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Ivory Coast", "Cape Verde",
            "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo (Congo-Brazzaville)",
            "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia (Czech Republic)", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
            "Ecuador", "Egypt", "The Savior", "Equatorial Guinea", "Eritrea", "Estonia", "Swatiland (fmr. \"Swaziland\")","Ethiopia","Fiji","Finland",
            "France","Gabon","Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau",
            "Guyana","Haiti","Holy See","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya"
            ,"Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta"
            ,"Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar (formerly Burma)","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua"
            ,"Niger","Nigeria","North Korea","North Macedonia","Norway","Oman","Pakistan","Palau","Palestine State","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania"
            ,"Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia"
            ,"South Africa","South Korea","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria","Tajikistan","Tanzania","Thailand","Timor-Leste"
            ,"Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","U.S","Uruguay","Uzbekistan"
            ,"vanuatu","Venezuela","Venezuela","Yemen","Zambia","Zimbabwe"
    };

    public EditprofileViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        sendViewModel = ViewModelProviders.of(this).get(EditprofileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_editprofile, container, false);
        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtCity = view.findViewById(R.id.edtCity);
        edtState = view.findViewById(R.id.edtState);
        edtZipCode = view.findViewById(R.id.edtZipCode);
        //edtHomeTel = view.findViewById(R.id.edtHomeTel);
        edtCellPhone = view.findViewById(R.id.edtCellPhone);
        edtEmail = view.findViewById(R.id.edtEmail);

        aet_Country = view.findViewById(R.id.aet_Country);
        aet_Country.setText(getString("Country_name"));
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countries);
        aet_Country.setAdapter(adapter);
        aet_Country.setThreshold(1);



        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        Chk_online();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                if (checkValidation()) {
                    Methods.showProgress(getContext());
                    submitForm();
                } else {
                    Toast.makeText(getContext(), "Please enter all details", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isValidEmail(edtEmail.getText().toString())) ret = false;
        if (!Validation.isPhoneNumber(edtCellPhone, false)) ret = false;
        return ret;
    }
    public void submitForm() {
        StringRequest srReg = new StringRequest(Request.Method.POST, Constants.PROFILE_EDIT,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Methods.closeProgress();
                Gson gson = new Gson();
                RegisterResponseModel objRes = gson.fromJson(response, RegisterResponseModel.class);
                if(objRes!=null){
                    if(objRes.getStatus()) {
                        Methods.closeProgress();
                        Toast.makeText(getContext(), objRes.getMsg(), Toast.LENGTH_LONG).show();
                        insertString("Country_name", aet_Country.getText().toString());
                    }else{
                        Methods.closeProgress();
                        Toast.makeText(getContext(), objRes.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, getString(Constants.TOKEN));
                params.put(Constants.FIRST_NAME, edtFirstName.getText().toString());
                params.put(Constants.LAST_NAME, edtLastName.getText().toString());
                params.put(Constants.ADDRESS, edtAddress.getText().toString());
                params.put(Constants.CITY_NAME, edtCity.getText().toString());
                params.put(Constants.STATE, edtState.getText().toString());
                params.put(Constants.ZIP_CODE, edtZipCode.getText().toString());
                params.put(Constants.COUNTRY_ID, "20");
                params.put(Constants.MOB_NO, edtCellPhone.getText().toString());
                //arams.put(Constants.TEL_NO, edtHomeTel.getText().toString());
                params.put(Constants.EMAIL, edtEmail.getText().toString());
                return checkParams(params);
            }
            @Override
            public Map<String, String> getHeaders() {
                return addHeader();
            }
            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs =  it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srReg);
    }
    public String getString(String key) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
    private void Chk_online() {

        if (Methods.isOnline(getContext())) {
            callProfileData();
        } else {
            Toast.makeText(getContext(), R.string.error_network_check, Toast.LENGTH_SHORT).show();
        }
    }
    public void callProfileData() {
        StringRequest requestProfile = new StringRequest(Request.Method.POST, Constants.STUDENT_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                profileRespo = gson.fromJson(response, ProfileDataModel.class);
                if(profileRespo!=null){
                    setProfileData(profileRespo);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
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
        MySingleton.getInstance(getContext()).addToRequestQueue(requestProfile);
    }

    private void setProfileData(ProfileDataModel profileRespo) {
        edtFirstName.setText(profileRespo.getfName());
        edtLastName.setText(profileRespo.getlName());
        edtAddress.setText(profileRespo.getAddress());
        edtCity.setText(profileRespo.getCity());
        edtState.setText(profileRespo.getState());
        edtZipCode.setText(profileRespo.getZipCode());
        //edtHomeTel.setText(profileRespo.getHomeTelNo());
        edtCellPhone.setText(profileRespo.getMobileNo());
        edtEmail.setText(profileRespo.getEmail());
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }

    public synchronized void insertString(String key, String value) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }
}