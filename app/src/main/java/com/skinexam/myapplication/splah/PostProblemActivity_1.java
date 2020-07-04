package com.skinexam.myapplication.splah;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.skinexam.myapplication.DashboardActivity;
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
import com.skinexam.myapplication.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostProblemActivity_1 extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_problem_1);

        Methods.showProgress(PostProblemActivity_1.this);


        loadAgeData();
        loadHealthData();
        loadStatusData();
        loadBodyPartData();
        loadPrevtData();


        btnAdd1 = (ImageButton) findViewById(R.id.btnAdd1);
        imagPhoto1 = (ImageView) findViewById(R.id.imgPhoto1);

        btnAdd2 = (ImageButton) findViewById(R.id.btnAdd2);
        imagPhoto2 = (ImageView) findViewById(R.id.imgPhoto2);

        btnAdd3 = (ImageButton) findViewById(R.id.btnAdd3);
        imagPhoto3 = (ImageView) findViewById(R.id.imgPhoto3);

        next_btn = (TextView) findViewById(R.id.btnNext_1);
        //toproblem = (TextView) findViewById(R.id.toproblem);

        btnAdd1.setOnClickListener(this);
        btnAdd2.setOnClickListener(this);
        btnAdd3.setOnClickListener(this);
       // toproblem.setOnClickListener(this);

        caseTitle = (EditText) findViewById(R.id.case_title);
        editDesc = (EditText) findViewById(R.id.edtDesc);
        editDesc2 = (EditText) findViewById(R.id.edtDesc2);
        editDesc3 = (EditText) findViewById(R.id.edtDesc3);
        edtTicketDesc = (EditText) findViewById(R.id.edtTicketDesc);
        lstList = (ListView) findViewById(R.id.lstList);

//        this is need for checkbox listview
        modelArrayList = getModel(false);
        customAdapter = new CustomAdapter(PostProblemActivity_1.this, modelArrayList);
        lstList.setAdapter(customAdapter);

        spinAge = (Spinner) findViewById(R.id.spnrAge);
        spinHealth = (Spinner) findViewById(R.id.spnrHealth);
        spinBody = (Spinner) findViewById(R.id.spnrbodypart);
        spinUsed = (Spinner) findViewById(R.id.spnrused);
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
                            spinAge.setAdapter(new ArrayAdapter<String>(PostProblemActivity_1.this, R.layout.simple_spinner_item, listAge));
                            spinAge.setOnItemSelectedListener(PostProblemActivity_1.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_1.this);
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
        MySingleton.getInstance(this).addToRequestQueue(srAge);
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

                            spinHealth.setAdapter(new HealthAdapter(PostProblemActivity_1.this, patientHealth));
                            spinHealth.setOnItemSelectedListener(PostProblemActivity_1.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_1.this);
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
        MySingleton.getInstance(this).addToRequestQueue(srHealth);
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

                            spinBody.setAdapter(new BodyAdapter(PostProblemActivity_1.this, patientBody));
                            spinBody.setOnItemSelectedListener(PostProblemActivity_1.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_1.this);
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
        MySingleton.getInstance(this).addToRequestQueue(srBody);
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

                            spinUsed.setAdapter(new PrevAdapter(PostProblemActivity_1.this, patientPrev));
                            spinUsed.setOnItemSelectedListener(PostProblemActivity_1.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if(getActivity()!=null && isAdded()){
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_1.this);
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
        MySingleton.getInstance(this).addToRequestQueue(srPrev);
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
                        Methods.showAlertDialog(getString(R.string.error_network_check), PostProblemActivity_1.this);
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
        MySingleton.getInstance(this).addToRequestQueue(srStatus);
    }

    private Map<String, String> addHeader() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s",Constants.Auth_UserName, Constants.Auth_Password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }

    private void showPictureDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    if (mtype == REQUEST_TAKE_PHOTO){
                        imagPhoto1.setImageBitmap(thumbnail);
                        imagPhoto1.setVisibility(View.VISIBLE);
                        ImageBuffer.image1 = bitmapToBase64(thumbnail);
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
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
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
                            Toast.makeText(PostProblemActivity_1.this, "Failed!", Toast.LENGTH_SHORT).show();
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

//    public String saveImage(Bitmap myBitmap) {
////        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
////        File wallpaperDirectory = new File(
////                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
////        if (!wallpaperDirectory.exists()) {
////            wallpaperDirectory.mkdirs();
////        }
////
////        try {
////            File f = new File(wallpaperDirectory, Calendar.getInstance()
////                    .getTimeInMillis() + ".jpg");
////            f.createNewFile();
////            FileOutputStream fo = new FileOutputStream(f);
////            fo.write(bytes.toByteArray());
//////            MediaScannerConnection.scanFile(this,
//////                    new String[]{f.getPath()},
//////                    new String[]{"image/jpeg"}, null);
////            fo.flush();
////            fo.close();
////
////            if (mtype == REQUEST_TAKE_PHOTO){
////                btn1 = f.getAbsolutePath();
////            }else if (mtype == REQUEST_TAKE_PHOTO2){
////                btn2 = f.getAbsolutePath();
////            }else if (mtype == REQUEST_TAKE_PHOTO3){
////                btn3 = f.getAbsolutePath();
////            }
////
////
////            return f.getAbsolutePath();
////        } catch (IOException e1) {
////            e1.printStackTrace();
////        }
//        return "";
//    }

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
        Dexter.withActivity(this)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PostProblemActivity_1.this);
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
        Uri uri = Uri.fromParts("package", getPackageName(), null);
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
                Intent next_intent = new Intent(com.skinexam.myapplication.splah.PostProblemActivity_1.this, com.skinexam.myapplication.splah.PostProblemActivity_2.class);

                title = caseTitle.getText().toString();
                editDes1_s = editDesc.getText().toString();
                editDes2_s = editDesc2.getText().toString();
                editDes3_s = editDesc3.getText().toString();
                editTicketDesc = edtTicketDesc.getText().toString();

                next_intent.putExtra("TITLE", title);
                next_intent.putExtra("EDITDEST1", editDes1_s);
                next_intent.putExtra("EDITDEST2", editDes2_s);
                next_intent.putExtra("EDITDEST3", editDes3_s);
                next_intent.putExtra("CONCERN", editTicketDesc);

                next_intent.putExtra("SPINAGE", spinAge_t);
                next_intent.putExtra("SPINHEALTH", spinHealth_t);
                next_intent.putExtra("SPINHEALTH_ID", spinHealth_id);
                next_intent.putExtra("SPINBODY", spinBody_t);
                next_intent.putExtra("SPINBODY_ID", spinBody_id);
                next_intent.putExtra("SPINUSED", spinUsed_t);
                next_intent.putExtra("SPINUSED_ID",spinUsed_id);
//                next_intent.putExtra("image_1", btn1);
//                next_intent.putExtra("image_2", btn2);
//                next_intent.putExtra("image_3", btn3);
//                Log.e("spinage_t", spinAge_t);
//                Log.e("spinHealth_t", spinHealth_t);
//                Log.e("spinHealth_id", spinHealth_id);
//                Log.e("spinBody_t", spinBody_t);
//                Log.e("spinBody_id", spinBody_id);

                if(checkValidation()) {
//                    if (!TextUtils.isEmpty(btn1)) {
//                        imageString.add(imgPhotoUrl1);
//                    }
//                    if (!TextUtils.isEmpty(imgPhotoUrl2)) {
//                        imageString.add(imgPhotoUrl2);
//                    }
//                    if (!TextUtils.isEmpty(imgPhotoUrl3)) {
//                        imageString.add(imgPhotoUrl3);
//                    }
                    selectedState.clear();
                    if(patientState!=null){
                        for (PatientState pas : patientState) {
                            if (pas.isSelected()) {
                                selectedState.add(pas);
                            }
                        }
                    }
//                    loadData();
                    startActivity(next_intent);
                    //finish();
                }

                break;
        }

    }

//    private void loadData() {
//
//        addCaseModel.setImgArrayList(imageString);
//
//        addCaseModel.setTitle(caseTitle.getText().toString());
//        // ((CaseDetailActivity) getActivity()).getAddCaseModel().setSummery(edtSummary.getText().toString());
//        addCaseModel.setImage1(imgPhotoUrl1);
//        addCaseModel.setDescription1(editDesc.getText().toString());
//        addCaseModel.setImage2(imgPhotoUrl2);
//        addCaseModel.setDescription2(editDesc2.getText().toString());
//        addCaseModel.setImage3(imgPhotoUrl3);
//        addCaseModel.setDescription3(editDesc3.getText().toString());
//        addCaseModel.setImg_extension1(imgExt);
//        addCaseModel.setImg_extension2(imgExt2);
//        addCaseModel.setImg_extension3(imgExt3);
//        addCaseModel.setAge(spinAge_t);
//        addCaseModel.setPatientHealth(selectedHealth);
//        addCaseModel.setSummery(edtTicketDesc.getText().toString());
//        addCaseModel.setPatientStates(selectedState);
//
//    }

    private boolean checkValidation() {
        if (!Validation.hasText(caseTitle,"Please provide Case title")){
            caseTitle.requestFocus();
            return false;
        }else{
            if(caseTitle.getText().length()<3){
                Toast.makeText(PostProblemActivity_1.this,"Please Enter character size for Title greater than 3 ",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (!TextUtils.isEmpty(ImageBuffer.image1)) {
            if (editDesc.getText().length() < 3) {
                editDesc.requestFocus();
                Toast.makeText(PostProblemActivity_1.this,"Please Enter character size for Image Description greater than 3",Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(PostProblemActivity_1.this,"Please provide first image for review",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinAge_t == ""){
            Toast.makeText(PostProblemActivity_1.this,"Please Select your Age",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinHealth_t == ""){
            Toast.makeText(PostProblemActivity_1.this,"Please Select your Health",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinHealth_id == ""){
            Toast.makeText(PostProblemActivity_1.this,"Please Select your Health",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinBody_t == ""){
            Toast.makeText(PostProblemActivity_1.this,"Please Select your Body Part",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spinBody_id == ""){
            Toast.makeText(PostProblemActivity_1.this,"Please Select your Body Part",Toast.LENGTH_LONG).show();
            return false;
        }

//        if (!TextUtils.isEmpty(imgPhotoUrl2)) {
//            if (editDesc2.getText().length() < 8) {
//                editDesc2.requestFocus();
//                Toast.makeText(PostProblemActivity_1.this,"Please Enter character size for Image Description greater than 8",Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//
//        if (!TextUtils.isEmpty(imgPhotoUrl3)) {
//            if (editDesc3.getText().length() < 8) {
//                editDesc2.requestFocus();
//                Toast.makeText(PostProblemActivity_1.this,"Please Enter character size for Image Description greater than 8",Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//
//        if(!TextUtils.isEmpty(edtTicketDesc.getText().toString())) {
//            if (edtTicketDesc.getText().length() < 8) {
//                edtTicketDesc.requestFocus();
//                Toast.makeText(PostProblemActivity_1.this, "Please Enter character size for Image Ticket Descritption greater than 8",Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
        return true;
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(spinAge)){
            if (position > 0) {
                spinAge_t = parent.getItemAtPosition(position).toString();
            }else{
                spinAge_t="";
            }
        }else if(parent.equals(spinHealth)){
            if (position > 0) {
                spinHealth_t = ((PatientHealth) parent.getItemAtPosition(position)).getStatus();
                spinHealth_id = Integer.toString(((PatientHealth) parent.getItemAtPosition(position)).getId());
//                sHealth = parent.getItemAtPosition(position).toString();
            }else{
                spinHealth_t="";
                spinHealth_id="";
            }
        }else if(parent.equals(spinBody)){
            if(position > 0){
                spinBody_t = ((PatientBody) parent.getItemAtPosition(position)).getStatus();
                spinBody_id = Integer.toString(((PatientBody) parent.getItemAtPosition(position)).getId());
            }else {
                spinBody_t = "";
                spinBody_id = "";
            }
        }else if(parent.equals((spinUsed))){
            if (position > 0) {
                spinUsed_t = ((PatientPrev) parent.getItemAtPosition(position)).getStatus();
                spinUsed_id = Integer.toString(((PatientPrev) parent.getItemAtPosition(position)).getId());
            } else {
                spinUsed_t = "";
                spinUsed_id = "";
            }
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onBackPressed() {

        // Check if NavigationDrawer is Opened
//        if (doublePressedBackToExit) {
            Intent intent = new Intent(PostProblemActivity_1.this, DashboardActivity.class);
            startActivity(intent);
            finish();
//
//        }
//        else {
//            this.doublePressedBackToExit = true;
//            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
//
//            // Delay of 2 seconds
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    // Set doublePressedBackToExit false after 2 seconds
//                    doublePressedBackToExit = false;
//                }
//            }, 2000);
//        }
    }
}
