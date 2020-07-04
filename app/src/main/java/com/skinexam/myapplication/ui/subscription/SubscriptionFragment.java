package com.skinexam.myapplication.ui.subscription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skinexam.myapplication.DashboardActivity;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.splah.Methods;
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

import static android.content.Context.MODE_PRIVATE;
import static com.skinexam.myapplication.ui.subscription.Constants_Subscription.SKU_SKINEXAM_MONTHLY;
import static com.skinexam.myapplication.ui.subscription.Constants_Subscription.base64EncodedPublicKey;

public class SubscriptionFragment extends Fragment {

//    static final String TAG = "SubscriptionFragment";
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View root = null;
//        return root;
//    }
//
//    public void onViewCreated(View view, Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        Intent subscription = new Intent(getActivity(), Subscription.class);
//        getActivity().finish();
//        startActivity(subscription);
//    }

    Fragment currentFragment;

    private Button subscribe;
    private RelativeLayout subscribe_main;

    String flag;
    // Debug tag, for logging
    static final String TAG = "SubscriptionFragment";

    IabHelper iabHelper;
    IabBroadcastReceiver mBroadcastReceiver;

//    private boolean isInAppSupported = false;
//    private boolean isSubscriptionDone = false;
//    private String mBase64EncodedPublicKey;

    //Does the user have an active subscription to the skinexam monthly plan?
    boolean mSubscribedSkinexam = false;

    //Will the subscription auto-renew?
    boolean mAutoRenewEnabled = false;

    String mSkinexamSku = "";

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel = ViewModelProviders.of(this).get(SubscriptionViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        View root = inflater.inflate(R.layout.fragment_subscription, container, false);
        return root;
    }
//
    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Creating IAB helper");
        iabHelper = new IabHelper(getContext(), base64EncodedPublicKey);
        iabHelper.enableDebugLogging(true);
        Log.d(TAG, "Starting setup");
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished");
                if(!result.isSuccess()){
                    complain("Problem setting up in-app billing : " + result);
                    return;
                }
                if(iabHelper == null) return;
                mBroadcastReceiver = new IabBroadcastReceiver(new IabBroadcastReceiver.IabBroadcastListener() {
                    @Override
                    public void receivedBroadcast() {
                        Log.d(TAG, "Received broadcast notification. Querying inventory.");
                        try {
                            iabHelper.queryInventoryAsync(mGotInventoryListener);
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            complain("Error querying inventory. Another async operation in progress.");
                        }
                    }
                });
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                getActivity().registerReceiver(mBroadcastReceiver, broadcastFilter);

                Log.d(TAG, "Setup successful, Querying inventory.");
                try{
                    iabHelper.queryInventoryAsync(mGotInventoryListener);
                }catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
//        iabHelper = new IabHelper(getContext(), mBase64EncodedPublicKey);
//        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//            @Override
//            public void onIabSetupFinished(IabResult result) {
//                if (!result.isSuccess()) {
//                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
//                }else{
//                    isInAppSupported = true;
//                    //Query if Subscription already buy
//                    iabHelper.queryInventoryAsync(true, mGotInventoryListener);
//                }
//            }
//        });
        subscribe_main = view.findViewById(R.id.screen_main);
        subscribe = (Button)view.findViewById(R.id.subscribe);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //iabHelper.launchSubscriptionPurchaseFlow(getActivity(), SKU_SUBSCRIPTION, REQUEST_SUBSCRIPTION, mPurchasedFinishedListener);
                String payload = "";
                setWaitScreen(true);
//                try {
                    DashboardActivity act = (DashboardActivity) getActivity();
                    act.purchaseLunch();
//                    Log.d(TAG, "Call subscription when click on button.");
//                    iabHelper.launchPurchaseFlow(getActivity(), SKU_SKINEXAM_MONTHLY, IabHelper.ITEM_TYPE_SUBS, null, REQUEST_SUBSCRIPTION, mPurchaseFinishedListener, payload);
//                    getActivity().finish();
//                }catch (IabHelper.IabAsyncInProgressException e) {
//                    complain("Error launching purchase flow. Another async operation in progress");
//                    setWaitScreen(false);
//                }
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if(iabHelper == null) return;
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
                Log.d(TAG, "Subscribe of Databse set 1");
                sendRequest("1");
            }else{
                Log.d(TAG, "Subscribe of Databse set 0");
                sendRequest("2");
            }
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; Subscribe of database set 1");
        }
    };

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (iabHelper != null) {
            iabHelper.disposeWhenFinished();
            iabHelper = null;
        }
    }

    void complain(String message) {
        Log.e(TAG, "**** Delaroy Error: " + message);
        alert("Error: " + message);
    }

    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {
        subscribe_main.setVisibility(set ? View.GONE : View.VISIBLE);
    }
//
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
//
    // send data to server
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
                    subscribe.setTextColor(Color.parseColor("#F89106"));
                    subscribe.setText("Subscribed");
                    subscribe.setBackgroundResource(R.drawable.shadow);
                    subscribe.setEnabled(false);
                } else if(res.equals("subscribed")){
                    subscribe.setTextColor(Color.parseColor("#F89106"));
                    subscribe.setText("Subscribed");
                    subscribe.setBackgroundResource(R.drawable.shadow);
                    subscribe.setEnabled(false);
                }else if(res.equals("nothing")){
                    subscribe.setText("Subscribe");
                    subscribe.setEnabled(true);
                }else if(res.equals("canceled")){
                    subscribe.setText("Subscribe");
                    subscribe.setTextColor(Color.parseColor("#FFFFFF"));
                    subscribe.setBackgroundResource(R.drawable.mybutton);
                    subscribe.setEnabled(true);
                }else if(res.equals("error")){
                    Toast.makeText(getContext(), "Database Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
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
        MySingleton.getInstance(getContext()).addToRequestQueue(sr);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
    public synchronized String getString(String key) {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
}

