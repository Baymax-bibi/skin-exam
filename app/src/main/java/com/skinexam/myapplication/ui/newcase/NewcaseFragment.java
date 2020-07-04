package com.skinexam.myapplication.ui.newcase;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.BodyAdapter;
import com.skinexam.myapplication.adapter.CustomAdapter;
import com.skinexam.myapplication.adapter.HealthAdapter;
import com.skinexam.myapplication.adapter.PrevAdapter;
import com.skinexam.myapplication.app.MySingleton;
import com.skinexam.myapplication.model.AgeResponseModel;
import com.skinexam.myapplication.model.BodyResponseModel;
import com.skinexam.myapplication.model.HealthResponseModel;
import com.skinexam.myapplication.model.MyCheckBoxModel;
import com.skinexam.myapplication.model.PatientBody;
import com.skinexam.myapplication.model.PatientHealth;
import com.skinexam.myapplication.model.PatientPrev;
import com.skinexam.myapplication.model.PatientState;
import com.skinexam.myapplication.model.PatientStateModel;
import com.skinexam.myapplication.model.PrevResponseModel;
import com.skinexam.myapplication.splah.ImageBuffer;
import com.skinexam.myapplication.splah.Methods;
import com.skinexam.myapplication.splah.Validation;
import com.skinexam.myapplication.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewcaseFragment extends Fragment implements View.OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO2 = 2;
    static final int REQUEST_TAKE_PHOTO3 = 3;

    private ArrayList<MyCheckBoxModel> modelArrayList;
    private TextView next_btn;
    private CustomAdapter customAdapter;

    private EditText caseTitle, editDesc, editDesc2, editDesc3, edtTicketDesc;
    private String title, editDes1_s, editDes2_s, editDes3_s, editTicketDesc, spinAge_t="spinAge_t", spinHealth_t="spinHealth_t", spinBody_t="spinBody_t", spinUsed_t;
    private Spinner spinAge, spinHealth, spinBody, spinUsed;
    private ListView lstList;
    String spinBody_id="spinBody_id", spinHealth_id="spinHealth_id", spinUsed_id="spinUsed_id";
    public File filen = null;


    List<PatientState> patientState;
    List<PatientState> selectedState = new ArrayList<PatientState>();

    //    for select image
    private ImageButton btnAdd1, btnAdd2, btnAdd3;
    private ImageView imagPhoto1, imagPhoto2, imagPhoto3;
    private int GALLERY = 1, CAMERA = 0;
    int mtype;

    String [] statelist = {"Itchy", "Changing color over time", "I can feel it as a bump"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_newcase, container, false);

        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageBuffer.CONCERN = "";
        ImageBuffer.EDITDEST1 = "";
        ImageBuffer.EDITDEST2 = "";
        ImageBuffer.EDITDEST3 = "";
        ImageBuffer.image1 = "";
        ImageBuffer.image2 = "";
        ImageBuffer.image3 = "";
        ImageBuffer.SPINAGE = "";
        ImageBuffer.SPINBODY = "";
        ImageBuffer.SPINBODY_ID = "";
        ImageBuffer.SPINHEALTH = "";
        ImageBuffer.SPINHEALTH_ID = "";
        ImageBuffer.SPINUSED = "";
        ImageBuffer.SPINUSED_ID = "";
        ImageBuffer.TITLE = "";


        Methods.showProgress(getContext());


        loadAgeData();
        loadHealthData();
        loadStatusData();
        loadBodyPartData();
        loadPrevtData();


        btnAdd1 = (ImageButton) view.findViewById(R.id.btnAdd1);
        imagPhoto1 = (ImageView) view.findViewById(R.id.imgPhoto1);

        btnAdd2 = (ImageButton) view.findViewById(R.id.btnAdd2);
        imagPhoto2 = (ImageView) view.findViewById(R.id.imgPhoto2);

        btnAdd3 = (ImageButton) view.findViewById(R.id.btnAdd3);
        imagPhoto3 = (ImageView) view.findViewById(R.id.imgPhoto3);

        next_btn = (TextView) view.findViewById(R.id.btnNext_1);
        //toproblem = (TextView) findViewById(R.id.toproblem);

        btnAdd1.setOnClickListener(this);
        btnAdd2.setOnClickListener(this);
        btnAdd3.setOnClickListener(this);
        // toproblem.setOnClickListener(this);

        caseTitle = (EditText) view.findViewById(R.id.case_title);
        editDesc = (EditText) view.findViewById(R.id.edtDesc);
        editDesc2 = (EditText) view.findViewById(R.id.edtDesc2);
        editDesc3 = (EditText) view.findViewById(R.id.edtDesc3);
        edtTicketDesc = (EditText) view.findViewById(R.id.edtTicketDesc);
        lstList = (ListView) view.findViewById(R.id.lstList);

//        this is need for checkbox listview
        modelArrayList = getModel(false);
        customAdapter = new CustomAdapter(getContext(), modelArrayList);
        lstList.setAdapter(customAdapter);

        spinAge = (Spinner) view.findViewById(R.id.spnrAge);
        spinHealth = (Spinner) view.findViewById(R.id.spnrHealth);
        spinBody = (Spinner) view.findViewById(R.id.spnrbodypart);
        spinUsed = (Spinner) view.findViewById(R.id.spnrused);
        next_btn.setOnClickListener(this);


    }
    public void loadAgeData() {
        StringRequest srAge = new StringRequest(Request.Method.POST, Constants.AGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Methods.closeProgress();
                        Gson gson = new Gson();
                        AgeResponseModel objAge = gson.fromJson(response, AgeResponseModel.class);
                        if(objAge!=null){
                            ArrayList<String> listAge = new ArrayList<String>();
                            listAge.add("Select Age");
                            for (Integer ageStr : objAge.getAge()) {
                                listAge.add(ageStr + "");
                            }
                            spinAge.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, listAge));
                            spinAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {
                                    Object item = adapterView.getItemAtPosition(position);
                                    if(adapterView.equals(spinAge)){
                                        if (position > 0) {
                                            spinAge_t = adapterView.getItemAtPosition(position).toString();
                                        }else{
                                            spinAge_t="";
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srAge);
    }


    public void loadHealthData() {
        final StringRequest srHealth = new StringRequest(Request.Method.POST, Constants.HEALTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Methods.closeProgress();
                        Gson gson = new Gson();

                        List<PatientHealth> patientHealth = new ArrayList<PatientHealth>();

                        if(patientHealth!=null){
                            PatientHealth first = new PatientHealth();
                            first.setId(0);
                            first.setStatus("Health");
                            patientHealth.add(first);
                            HealthResponseModel objHealth = gson.fromJson(response, HealthResponseModel.class);
                            patientHealth.addAll(objHealth.getPatientHealth());

                            spinHealth.setAdapter(new HealthAdapter(getContext(), patientHealth));
                            spinHealth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {
                                    Object item = adapterView.getItemAtPosition(position);
                                    if (position > 0) {
                                        spinHealth_t = ((PatientHealth) adapterView.getItemAtPosition(position)).getStatus();
                                        spinHealth_id = Integer.toString(((PatientHealth) adapterView.getItemAtPosition(position)).getId());
//                sHealth = parent.getItemAtPosition(position).toString();
                                    }else{
                                        spinHealth_t="";
                                        spinHealth_id="";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
//                        }
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srHealth);
    }

    public void loadBodyPartData() {
        final StringRequest srBody = new StringRequest(Request.Method.POST, Constants.BODYPART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Methods.closeProgress();
                        Gson gson = new Gson();

                        List<PatientBody> patientBody = new ArrayList<PatientBody>();

                        if(patientBody!=null){
                            PatientBody first = new PatientBody();
                            first.setId(0);
                            first.setStatus("Select Body Part");
                            patientBody.add(first);
                            BodyResponseModel objBody = gson.fromJson(response, BodyResponseModel.class);
                            patientBody.addAll(objBody.getPatientHealth());

                            spinBody.setAdapter(new BodyAdapter(getContext(), patientBody));
                            spinBody.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {
                                    Object item = adapterView.getItemAtPosition(position);
                                    if(position > 0){
                                        spinBody_t = ((PatientBody) adapterView.getItemAtPosition(position)).getStatus();
                                        spinBody_id = Integer.toString(((PatientBody) adapterView.getItemAtPosition(position)).getId());
                                    }else {
                                        spinBody_t = "";
                                        spinBody_id = "";
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
//                        }
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srBody);
    }

    public void loadPrevtData() {
        final StringRequest srPrev = new StringRequest(Request.Method.POST, Constants.PATIENTPREV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Methods.closeProgress();
                        Gson gson = new Gson();

                        List<PatientPrev> patientPrev = new ArrayList<PatientPrev>();

                        if(patientPrev!=null){
                            PatientPrev first = new PatientPrev();
                            first.setId(0);
                            first.setStatus("Select Previous Used");
                            patientPrev.add(first);
                            PrevResponseModel objPrev = gson.fromJson(response, PrevResponseModel.class);
                            patientPrev.addAll(objPrev.getPatientPrev());

                            spinUsed.setAdapter(new PrevAdapter(getContext(), patientPrev));
                            spinUsed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {
                                    Object item = adapterView.getItemAtPosition(position);
                                    if (position > 0) {
                                        spinUsed_t = ((PatientPrev) adapterView.getItemAtPosition(position)).getStatus();
                                        spinUsed_id = Integer.toString(((PatientPrev) adapterView.getItemAtPosition(position)).getId());
                                    } else {
                                        spinUsed_t = "";
                                        spinUsed_id = "";
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
//                        }
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srPrev);
    }

    public void loadStatusData() {
        final StringRequest srStatus = new StringRequest(Request.Method.POST, Constants.STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Methods.closeProgress();
                        Gson gson = new Gson();
//                        if(getActivity()!=null && isAdded()){
                        patientState = new ArrayList<PatientState>();
                        PatientStateModel objPatientState = gson.fromJson(response, PatientStateModel.class);
                        if(objPatientState!=null){
                            patientState.addAll(objPatientState.getPatientState());
                        }
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), getContext());
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return addHeader();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(srStatus);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }

    private void showPictureDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Picture Option");
        alertDialog.setIcon(getResources().getDrawable(R.drawable.icon_image));
        alertDialog.setPositiveButton("GALLERY",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosePhotoFromGallery();
            }
        });
        alertDialog.setNegativeButton("CAMERA",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePhotoFromCamera();
            }
        });
        alertDialog.show();
    }

    public void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String fname = "Image-"+ Math.random() +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    if (mtype == REQUEST_TAKE_PHOTO){
                        imagPhoto1.setImageBitmap(thumbnail);
                        imagPhoto1.setVisibility(View.VISIBLE);
                        ImageBuffer.image1 = bitmapToBase64(thumbnail);
//                        Intent intent = new Intent(this, DashboardActivity.class);
//                        startActivity(intent);
                        SaveImage(thumbnail);
                    }else if (mtype == REQUEST_TAKE_PHOTO2){
                        imagPhoto2.setImageBitmap(thumbnail);
                        imagPhoto2.setVisibility(View.VISIBLE);
                        ImageBuffer.image2 = bitmapToBase64(thumbnail);
                    }else if (mtype == REQUEST_TAKE_PHOTO3){
                        imagPhoto3.setImageBitmap(thumbnail);
                        imagPhoto3.setVisibility(View.VISIBLE);
                        ImageBuffer.image3 = bitmapToBase64(thumbnail);
                    }
                    //saveImage(thumbnail);
                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK){

                    if (data != null) {
                        Uri contentURI = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                            //String path = saveImage(bitmap);
                            if (mtype == REQUEST_TAKE_PHOTO){
                                imagPhoto1.setImageBitmap(bitmap);
                                imagPhoto1.setVisibility(View.VISIBLE);
                                ImageBuffer.image1 = bitmapToBase64(bitmap);
                            }else if (mtype == REQUEST_TAKE_PHOTO2){
                                imagPhoto2.setImageBitmap(bitmap);
                                imagPhoto2.setVisibility(View.VISIBLE);
                                ImageBuffer.image2 = bitmapToBase64(bitmap);
                            }else if (mtype == REQUEST_TAKE_PHOTO3){
                                imagPhoto3.setImageBitmap(bitmap);
                                imagPhoto3.setVisibility(View.VISIBLE);
                                ImageBuffer.image3 = bitmapToBase64(bitmap);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private ArrayList<MyCheckBoxModel> getModel(boolean isSelect) {
        ArrayList<MyCheckBoxModel> list = new ArrayList<>();
        for (int i = 0; i < statelist.length; i++){

            MyCheckBoxModel model = new MyCheckBoxModel();
            model.setSelected(isSelect);
            model.setState(statelist[i]);
            list.add(model);
        }
        return list;
    }


    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {

                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                            //Toast.makeText(getApplicationContext(), "any permission permanetly denined!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
//                .withErrorListener(new PermissionRequestErrorListener() {
//                    @Override
//                    public void onError(DexterError error) {
//                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
                .onSameThread()
                .check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd1:
                mtype=REQUEST_TAKE_PHOTO;
                requestMultiplePermissions();
                showPictureDialog();
                break;
            case R.id.btnAdd2:
                mtype = REQUEST_TAKE_PHOTO2;

                requestMultiplePermissions();
                showPictureDialog();
                break;
            case R.id.btnAdd3:
                mtype = REQUEST_TAKE_PHOTO3;

                requestMultiplePermissions();
                showPictureDialog();
                break;
//            case R.id.toproblem:
//                Intent intent = new Intent(PostProblemActivity_1.this, DashboardActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btnNext_1:
                //Intent next_intent = new Intent(getContext(), com.skinexam.myapplication.splah.PostProblemActivity_2.class);

                title = caseTitle.getText().toString();
                editDes1_s = editDesc.getText().toString();
                editDes2_s = editDesc2.getText().toString();
                editDes3_s = editDesc3.getText().toString();
                editTicketDesc = edtTicketDesc.getText().toString();

//                next_intent.putExtra("TITLE", title);
//                next_intent.putExtra("EDITDEST1", editDes1_s);
//                next_intent.putExtra("EDITDEST2", editDes2_s);
//                next_intent.putExtra("EDITDEST3", editDes3_s);
//                next_intent.putExtra("CONCERN", editTicketDesc);
//
//                next_intent.putExtra("SPINAGE", spinAge_t);
//                next_intent.putExtra("SPINHEALTH", spinHealth_t);
//                next_intent.putExtra("SPINHEALTH_ID", spinHealth_id);
//                next_intent.putExtra("SPINBODY", spinBody_t);
//                next_intent.putExtra("SPINBODY_ID", spinBody_id);
//                next_intent.putExtra("SPINUSED", spinUsed_t);
//                next_intent.putExtra("SPINUSED_ID",spinUsed_id);

                ImageBuffer.TITLE = title;
                ImageBuffer.EDITDEST1 = editDes1_s;
                ImageBuffer.EDITDEST2 = editDes2_s;
                ImageBuffer.EDITDEST3 = editDes3_s;
                ImageBuffer.CONCERN = editTicketDesc;
                ImageBuffer.SPINAGE = spinAge_t;
                ImageBuffer.SPINHEALTH = spinHealth_t;
                ImageBuffer.SPINHEALTH_ID = spinHealth_id;
                ImageBuffer.SPINBODY = spinBody_t;
                ImageBuffer.SPINBODY_ID = spinBody_id;
                ImageBuffer.SPINUSED = spinUsed_t;
                ImageBuffer.SPINUSED_ID = spinUsed_id;


                if(checkValidation()) {
                    selectedState.clear();
                    if(patientState!=null){
                        for (PatientState pas : patientState) {
                            if (pas.isSelected()) {
                                selectedState.add(pas);
                            }
                        }
                    }
                    //startActivity(next_intent);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    PostProblemFragment2 frag = new PostProblemFragment2();
                    transaction.replace(R.id.nav_host_fragment, frag);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                break;
        }

    }

    private boolean checkValidation() {
        if (!Validation.hasText(caseTitle,"Please provide Case title")){
            caseTitle.requestFocus();
            return false;
        }else{
            if(caseTitle.getText().length()<3){
                Toast.makeText(getContext(),"Please Enter character size for Title greater than 3 ",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (!TextUtils.isEmpty(ImageBuffer.image1)) {
            if (editDesc.getText().length() < 3) {
                editDesc.requestFocus();
                Toast.makeText(getContext(),"Please Enter character size for Image Description greater than 3",Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(getContext(),"Please provide first image for review",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinAge_t == ""){
            Toast.makeText(getContext(),"Please Select your Age",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinHealth_t == ""){
            Toast.makeText(getContext(),"Please Select your Health",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinHealth_id == ""){
            Toast.makeText(getContext(),"Please Select your Health",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinBody_t == ""){
            Toast.makeText(getContext(),"Please Select your Body Part",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinBody_id == ""){
            Toast.makeText(getContext(),"Please Select your Body Part",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}