package com.skinexam.myapplication.ui.newcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.adapter.CustomAdapter;
import com.skinexam.myapplication.adapter.ViewPagerAdapter;
import com.skinexam.myapplication.splah.ImageBuffer;

import java.util.ArrayList;
import java.util.List;

public class PostProblemFragment2 extends Fragment implements View.OnClickListener {

    String title, des1, des2, des3, concern, spinage, spinhealth, spinbody, spinused, spinhealth_id, spinbody_id, spinused_id, itchy_id = "0", changeColor_id= "0", bumper_id= "0";
    TextView pre_title_con, age_prev, helth_prev, state_prev_content, bodypart_prev_content, prior_prev_content, pre_concern_con;
    private TextView next_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_post_problem_2, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null){
                title = ImageBuffer.TITLE;
                des1 = ImageBuffer.EDITDEST1;
                des2 = ImageBuffer.EDITDEST2;
                des3 = ImageBuffer.EDITDEST3;
                concern = ImageBuffer.CONCERN;

                spinage = ImageBuffer.SPINAGE;
                spinhealth = ImageBuffer.SPINHEALTH;
                spinbody = ImageBuffer.SPINBODY;
                spinused = ImageBuffer.SPINUSED;

                spinhealth_id = ImageBuffer.SPINHEALTH_ID;
                spinbody_id = ImageBuffer.SPINBODY_ID;
                spinused_id = ImageBuffer.SPINUSED_ID;

            }

        next_btn = (TextView) view.findViewById(R.id.btnNext_1);
        next_btn.setOnClickListener(this);

        List<String> arrayImagesList = new ArrayList<String>();
        arrayImagesList.add(ImageBuffer.image1);
        arrayImagesList.add(ImageBuffer.image2);
        arrayImagesList.add(ImageBuffer.image3);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), (ArrayList<String>) arrayImagesList);
        viewPager.setAdapter(adapter);


        pre_title_con = (TextView) view.findViewById(R.id.pre_title_con);
        pre_concern_con = (TextView) view.findViewById(R.id.pre_concern_con);
        age_prev = (TextView) view.findViewById(R.id.age_prev);
        helth_prev = (TextView) view.findViewById(R.id.health_prev);

        bodypart_prev_content = (TextView) view.findViewById(R.id.bodypart_prev_content) ;
        prior_prev_content = (TextView) view.findViewById(R.id.prior_prev_content);

        state_prev_content = (TextView) view.findViewById(R.id.state_prev_content);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext_1:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                PostProblemFragment3 frag = new PostProblemFragment3();
                transaction.replace(R.id.nav_host_fragment, frag);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
