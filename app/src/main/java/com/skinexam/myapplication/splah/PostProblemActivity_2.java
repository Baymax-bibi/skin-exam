package com.skinexam.myapplication.splah;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.CustomAdapter;
import com.skinexam.myapplication.adapter.ViewPagerAdapter;
import com.skinexam.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PostProblemActivity_2 extends AppCompatActivity implements View.OnClickListener {

    String title, des1, des2, des3, concern, spinage, spinhealth, spinbody, spinused, spinhealth_id, spinbody_id, spinused_id, itchy_id = "0", changeColor_id= "0", bumper_id= "0";
    TextView pre_title_con, age_prev, helth_prev, state_prev_content, bodypart_prev_content, prior_prev_content, pre_concern_con;
    private TextView next_btn;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_problem_2);

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                title = null;
            }else {
                title = extras.getString("TITLE");
                des1 = extras.getString("EDITDEST1");
                des2 = extras.getString("EDITDEST2");
                des3 = extras.getString("EDITDEST3");
                concern = extras.getString("CONCERN");

                spinage = extras.getString("SPINAGE");
                spinhealth = extras.getString("SPINHEALTH");
                spinbody = extras.getString("SPINBODY");
                spinused = extras.getString("SPINUSED");

                spinhealth_id = extras.getString("SPINHEALTH_ID");
                spinbody_id = extras.getString("SPINBODY_ID");
                spinused_id = extras.getString("SPINHEALTH_ID");

            }
        }else {
            title = (String) savedInstanceState.getSerializable("TITLE");
        }

        next_btn = (TextView) findViewById(R.id.btnNext_1);
        next_btn.setOnClickListener(this);

        List<String> arrayImagesList = new ArrayList<String>();
        arrayImagesList.add(ImageBuffer.image1);
        arrayImagesList.add(ImageBuffer.image2);
        arrayImagesList.add(ImageBuffer.image3);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, (ArrayList<String>) arrayImagesList);
        viewPager.setAdapter(adapter);


        pre_title_con = (TextView) findViewById(R.id.pre_title_con);
        pre_concern_con = (TextView) findViewById(R.id.pre_concern_con);
        age_prev = (TextView) findViewById(R.id.age_prev);
        helth_prev = (TextView) findViewById(R.id.health_prev);

        bodypart_prev_content = (TextView) findViewById(R.id.bodypart_prev_content) ;
        prior_prev_content = (TextView) findViewById(R.id.prior_prev_content);

        state_prev_content = (TextView) findViewById(R.id.state_prev_content);
        pre_title_con.setText(title);
        pre_concern_con.setText(concern);
        age_prev.setText("Age : " + spinage);
        helth_prev.setText("Health : " + spinhealth);;
        bodypart_prev_content.setText(spinbody);
        prior_prev_content.setText(spinused);

        for (int i = 0; i < CustomAdapter.modelArrayList.size(); i++){
            if (CustomAdapter.modelArrayList.get(i).getSelected()){
                state_prev_content.setText(state_prev_content.getText() + "\n" + CustomAdapter.modelArrayList.get(i).getState());
                String state =   CustomAdapter.modelArrayList.get(i).getState();
                if (state.compareTo("Itchy") == 0){
                    itchy_id = "1";
                }
                if (state.compareTo("I can feel it as a bump") == 0){
                    bumper_id = "1";
                }
                if (state.compareTo("Changing color over time") == 0){
                    changeColor_id = "1";
                }
            }
        }
        Log.e("itchy", itchy_id);
        Log.e("bumper", bumper_id);
        Log.e("changeColor", changeColor_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext_1:
            Intent to_3 = new Intent(PostProblemActivity_2.this, PostProblemActivity_3.class);

            to_3.putExtra("TITLE", title);
            to_3.putExtra("EDITDEST1", des1);
            to_3.putExtra("EDITDEST2", des2);
            to_3.putExtra("EDITDEST3", des3);
            to_3.putExtra("CONCERN", concern);

            to_3.putExtra("SPINAGE", spinage);
            to_3.putExtra("SPINHEALTH", spinhealth);
            to_3.putExtra("SPINHEALTH_ID", spinhealth_id);
            to_3.putExtra("SPINBODY", spinbody);
            to_3.putExtra("SPINBODY_ID", spinbody_id);
            to_3.putExtra("SPINUSED", spinused);
            to_3.putExtra("SPINUSED_ID",spinused_id);
            startActivity(to_3);
            break;
         }
    }

    public String getString(String key) {
        SharedPreferences mSharedPreferences = getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        String  selected =  mSharedPreferences.getString(key, "");
        return selected;
    }
}
