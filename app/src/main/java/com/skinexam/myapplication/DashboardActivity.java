package com.skinexam.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.ProfileDataModel;
import com.skinexam.myapplication.splah.Dash_all;
import com.skinexam.myapplication.splah.Dash_pend;
import com.skinexam.myapplication.splah.Dash_recent;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.splah.PostProblemActivity_1;
import com.skinexam.myapplication.ui.newcase.NewcaseFragment;
import com.skinexam.myapplication.ui.subscription.SubscriptionFragment;
import com.skinexam.myapplication.ui.subscription.util.IabBroadcastReceiver;
import com.skinexam.myapplication.ui.subscription.util.IabHelper;
import com.skinexam.myapplication.ui.subscription.util.IabResult;
import com.skinexam.myapplication.ui.subscription.util.Inventory;
import com.skinexam.myapplication.ui.subscription.util.Purchase;
import com.skinexam.myapplication.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.skinexam.myapplication.R.id;
import static com.skinexam.myapplication.R.layout;
import static com.skinexam.myapplication.R.string;
import static com.skinexam.myapplication.R.style;
import static com.skinexam.myapplication.ui.subscription.Constants_Subscription.REQUEST_SUBSCRIPTION;
import static com.skinexam.myapplication.ui.subscription.Constants_Subscription.SKU_SKINEXAM_MONTHLY;
import static com.skinexam.myapplication.ui.subscription.Constants_Subscription.base64EncodedPublicKey;


public class DashboardActivity extends AppCompatActivity implements  Dash_recent.OnFragmentInteractionListener, Dash_pend.OnFragmentInteractionListener, Dash_all.OnFragmentInteractionListener, DialogInterface.OnClickListener , IabBroadcastReceiver.IabBroadcastListener {

    static final String TAG = "DashboardActivity";
    int flag;

    private AppBarConfiguration mAppBarConfiguration;

    TextView user_name;
    FloatingActionButton fab;

    ProfileDataModel profileRespo;

    IabHelper mHelper;
    IabBroadcastReceiver mBroadcastReceiver;

    //Does the user have an active subscription to the skinexam monthly plan?
    boolean mSubscribedSkinexam = false;

    //Will the subscription auto-renew?
    boolean mAutoRenewEnabled = false;

    String mSkinexamSku = "";

    public DashboardActivity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dashboard);

        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished");
                if(!result.isSuccess()){
                    complain("Problem setting up in-app billing : " + result);
                    return;
                }
                if(mHelper == null) return;
                mBroadcastReceiver = new IabBroadcastReceiver(DashboardActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                Log.d(TAG, "Setup successful, Querying inventory.");
                try{
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                }catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });

        final Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] options1;
                if(!getString("subscriber").equals("0")){
                    Methods.showProgress(DashboardActivity.this);
                    Intent intent = new Intent(getApplicationContext(), PostProblemActivity_1.class);
                    startActivity(intent);
                    fab.setVisibility(View.GONE);
                }else {
                    options1 = new CharSequence[3];
                    options1[0] = "Subscribe as an individual ";
                    options1[1] = "Pay from my company";
                    options1[2] = "Do not wish to subscribe.";
                    //int titleResId;
                    //titleResId = string.titleresId;

                    TextView custom_title = new TextView(DashboardActivity.this);
                    custom_title.setText(string.titleresId);
                    custom_title.setGravity(Gravity.CENTER_HORIZONTAL);
                    custom_title.setTextSize(16);
                    custom_title.setTextColor(Color.WHITE);

                    ContextThemeWrapper cw = new ContextThemeWrapper( DashboardActivity.this, style.AlertDialogTheme );
                    AlertDialog.Builder builder = new AlertDialog.Builder(cw);
                    builder.setCustomTitle(custom_title)
                            .setSingleChoiceItems(options1, 0, DashboardActivity.this)
                            .setPositiveButton(string.subscription_prompt_continue, DashboardActivity.this);
                    AlertDialog dialog = builder.create();
                    //dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(id.drawer_layout);
        NavigationView navigationView = findViewById(id.nav_view);

        View headView = navigationView.getHeaderView(0);
        user_name = headView.findViewById(id.user_name);
        callProfileData();

        NavController navController = Navigation.findNavController(this, id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                id.nave_dashboard, id.nav_mycase, id.nav_newcase, id.nav_changePwd, id.nav_editProfile, id.nav_logout, id.nav_subScription)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                CharSequence[] options;
                fab.setVisibility(View.VISIBLE);
                if (destination.getId() == id.nav_newcase ){
                    fab.setVisibility(View.GONE);
                    if(!getString("subscriber").equals("0")){
                        Methods.showProgress(DashboardActivity.this);
//                        Intent intent = new Intent(getApplicationContext(), PostProblemActivity_1.class);
//                        startActivity(intent);
                        Fragment subfrag = new NewcaseFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(id.nav_host_fragment, subfrag)
                                .commit();
                    }else {
                        options = new CharSequence[3];
                        options[0] = "Subscribe as an individual ";
                        options[1] = "Pay from my company";
                        options[2] = "Do not wish to subscribe.";

                        TextView custom_title = new TextView(DashboardActivity.this);
                        custom_title.setText(string.titleresId);
                        custom_title.setGravity(Gravity.CENTER_HORIZONTAL);
                        custom_title.setTextSize(16);
                        custom_title.setTextColor(Color.WHITE);

                        ContextThemeWrapper cw = new ContextThemeWrapper( DashboardActivity.this, style.AlertDialogTheme );
                        AlertDialog.Builder builder = new AlertDialog.Builder(cw);
                        builder.setCustomTitle(custom_title)
                                .setSingleChoiceItems(options, flag, DashboardActivity.this)
                                .setPositiveButton(string.subscription_prompt_continue, DashboardActivity.this);
                        AlertDialog dialog = builder.create();
                        //dialog.setCancelable(false);
                        dialog.show();

                    //fab.setVisibility(View.GONE);
                    }
                }
                if (destination.getId() == id.nav_editProfile){
                    fab.setVisibility(View.GONE);
                }
//                if (destination.getId() == id.nav_subScription) {
//                    Intent subs = new Intent(DashboardActivity.this, Subscription.class);
//                    startActivity(subs);
//                }
            }
        });
    }
    //onCreate() function ended

   IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
        Log.d(TAG, "Query inventory finished.");
        if(mHelper == null) return;
        if(result.isFailure()) {
            complain("Failed to query inventory : " + result);
            return;
        }
        Log.d(TAG, "Query inventory was successful.");

        Purchase skinexamMonthly = inventory.getPurchase(SKU_SKINEXAM_MONTHLY);
        if(skinexamMonthly != null && skinexamMonthly.isAutoRenewing()) {
            mSkinexamSku = SKU_SKINEXAM_MONTHLY;
            mAutoRenewEnabled = true;
        }else{
            mSkinexamSku = "";
            mAutoRenewEnabled = false;
        }
        mSubscribedSkinexam = (skinexamMonthly != null && verifyDeveloperPayload(skinexamMonthly));
        Log.d(TAG, "User " + (mSubscribedSkinexam ? "HAS" : "DOES NOT HAVE ") + " Skinexam subscription");
        //Checking Subscription
        if(mSubscribedSkinexam) {
            Log.d(TAG, "Subscribe of Database set 1");
            sendRequest("1");
        }else{
            Log.d(TAG, "Subscribe of Database set 0");
            sendRequest("2");             //Subscription canceled
        }
        Log.d(TAG, "Initial inventory query finished; Subscribe of database set 1");
        }
    };

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    public void onClick(DialogInterface dialog, int id) {
        Log.e("option value", String.valueOf(id));
        if (id == 0 /* First choice item */) {
            Log.e("alert", "option0");
            flag = id;
        } else if (id == 1 /* Second choice item */) {
            Log.e("alert flag", "option1");
            flag = id;
        }else if (id == 2) {
            Log.e("alert flag", "option2");
            flag = id;
        }else if (id == DialogInterface.BUTTON_POSITIVE /* continue button */) {
            Log.e("alert dialog", "ok");
            if(flag == 0){
                Log.e("alert", "flag0");
                Fragment subfrag = new SubscriptionFragment();
                getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_host_fragment, subfrag)
                                            .commit();
            }else if(flag == 1){
                submit_dialogue();
            }else if(flag == 2){
                Log.e("alert", "flag2");
                Fragment subfrag = new NewcaseFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, subfrag)
                        .commit();
                fab.setVisibility(View.GONE);
            }

        }
    }

    private void submit_dialogue() {
        ContextThemeWrapper submit_cw = new ContextThemeWrapper( DashboardActivity.this, style.AlertDialogTheme );

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View layout = inflater.inflate(R.layout.dialog_second, (ViewGroup) findViewById(R.id.root));
        final EditText dialogname = (EditText) layout.findViewById(R.id.et_username);
        final EditText number = (EditText) layout.findViewById(R.id.et_companynumber);

        ContextThemeWrapper cw = new ContextThemeWrapper( DashboardActivity.this, style.AlertDialogTheme );
        final AlertDialog dialog = new AlertDialog.Builder(cw)
        .setTitle("Please input name and company Number (Insurance,Hospital,etc)")
                .setView(layout)
                .setPositiveButton("Submit", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogname.length() < 1 || number.length()<1){
                    Toast.makeText(DashboardActivity.this, "Please fill in fields to continue.", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("dialogu name", dialogname.getText().toString());
                    Log.e("dialogu number", number.getText().toString());
                    StringRequest saveRequest = new StringRequest(Request.Method.POST, Constants.URL + "api_CompanyData.php/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.cancel();
                            Log.e("dialog response", response);
                            String res = "";
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                                res = obj.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (res.equals("success")) {
                                Toast.makeText(DashboardActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                Fragment subfrag = new NewcaseFragment();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.nav_host_fragment, subfrag)
                                        .commit();
                                fab.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(DashboardActivity.this, "Database error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
                            String PARAM_TOKEN = mSharedPreferences.getString(Constants.TOKEN, "");

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("clientsId", getString(Constants.TOKEN));
                            params.put("UserName", dialogname.getText().toString());
                            params.put("CompanyNumber", number.getText().toString());
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return addHeader();
                        }
                    };
                    MySingleton.getInstance(DashboardActivity.this).addToRequestQueue(saveRequest);
                }
            }
        });
    }

    public void callProfileData() {
        StringRequest requestProfile = new StringRequest(Request.Method.POST, Constants.STUDENT_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                profileRespo = gson.fromJson(response, ProfileDataModel.class);
                if(profileRespo!=null){
                    String fname = profileRespo.getfName();
                    String lname = profileRespo.getlName();
                    setprofilename(fname, lname);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
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
        MySingleton.getInstance(this).addToRequestQueue(requestProfile);
    }

    private void setprofilename(String fname, String lname) {
        user_name.setText(fname + "   " + lname);
    }

    public synchronized void clear() {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public synchronized void insertString(String key, String value) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        Log.e("Header", auth);
        return params;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public synchronized void insertBoolean(String key, boolean value) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    // send data for checking subscription to server
    private void sendRequest(final String flag) {
        Log.e("request", flag);
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.URL + "apiMobileSubscription.php/", new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            public void onResponse(String response) {

                String res = "";
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    res = obj.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response", response);
                Methods.closeProgress();
                if(res.equals("success")){
                    insertString("subscriber", "1");  //You have already subscribed in skinexam
                    Log.d(TAG, "You have already subscribed in Skinexam.");
                }else if(res.equals("subscribed")){
                    insertString("subscriber", "1");   //No Subscription
                    Log.d(TAG, "You have already subscribed in Skinexam.");
                }else if(res.equals("nothing")){
                    insertString("subscriber", "0");   //No Subscription
                    Log.d(TAG, "You are not subscribeder.");
                }else if(res.equals("canceled")){
                    insertString("subscriber", "0");   //No Subscription
                    Log.d(TAG, "Your Subscription canceled.");
                }else if(res.equals("error")){
                    Toast.makeText(DashboardActivity.this, "Database Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(DashboardActivity.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("clientsId", getString(Constants.TOKEN));
                params.put("subscription", flag);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        MySingleton.getInstance(DashboardActivity.this).addToRequestQueue(sr);
    }

    void complain(String message) {
        Log.e(TAG, "**** Skinexam Error: " + message);
        Toast.makeText(this, "Skinexam subscription error or Pleaes check google.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void receivedBroadcast() {
                Log.d(TAG, "Received broadcast notification. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
    }

//subscription functions called from SubscriptionFragment
    public void purchaseLunch(){
        String payload = "";
        try {
            Log.d(TAG, "Call subscription when click on button.");
            mHelper.launchPurchaseFlow(DashboardActivity.this, SKU_SKINEXAM_MONTHLY, IabHelper.ITEM_TYPE_SUBS, null, REQUEST_SUBSCRIPTION, mPurchaseFinishedListener, payload);
        }catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(mHelper == null) return;
        if(!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }else{
            Log.d(TAG, "onActivityResult handled by IABUTIL");
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished : " + result + ", purchase : " + purchase);
            if(mHelper == null) return;
            if(result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if(!verifyDeveloperPayload(purchase)){
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }
            Log.d(TAG, "Purchase successful.");
            if(purchase.getSku().equals(SKU_SKINEXAM_MONTHLY)) {
                Log.d(TAG, "Skinexam subscription purchased");
                alert("Thanks for subscribing to Skinexam!");
                mSubscribedSkinexam = true;
                mAutoRenewEnabled = purchase.isAutoRenewing();
                mSkinexamSku = purchase.getSku();
                sendRequest_subscription("1");
            }
        }
    };

    //Called when consumption is complete
    IabHelper.OnConsumeFinishedListener onConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result : "+ result);
            Log.d(TAG, "End consumption flow.");
        }
    };

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
    // send data to server once success in subscription
    private void sendRequest_subscription(final String flag) {
        Log.e("request", flag);
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.URL + "apiMobileSubscription.php/", new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            public void onResponse(String response) {

                String res = "";
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    res = obj.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response", response);
                Methods.closeProgress();
                if(res.equals("success")){
                    insertString("subscriber", "1");
                    Fragment subfrag = new SubscriptionFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, subfrag)
                            .commit();
                }else if(res.equals("error")){
                    Toast.makeText(DashboardActivity.this, "Database Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(DashboardActivity.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("clientsId", getString(Constants.TOKEN));
                params.put("subscription", flag);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        MySingleton.getInstance(this).addToRequestQueue(sr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

}
