package com.skinexam.myapplication.splah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skinexam.myapplication.R;


public class SplashActivity extends Activity {

    //Button postProblem_btn ;
    TextView signIn_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

       // postProblem_btn = (Button) findViewById(R.id.post_problem_btn);
        signIn_tv = (TextView) findViewById(R.id.SignIn_tv);

//        postProblem_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent post_intent = new Intent(com.skinexam.myapplication.splah.SplashActivity.this, PostProblemActivity_1.class);
//                startActivity(post_intent);
//            }
//        });

        signIn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_intent = new Intent(com.skinexam.myapplication.splah.SplashActivity.this, com.skinexam.myapplication.splah.SignInActivity.class);
                startActivity(sign_intent);
            }
        });
    }
}
