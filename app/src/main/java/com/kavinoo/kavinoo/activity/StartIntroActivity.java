package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.utils.UserInfoManager;

public class StartIntroActivity extends AppCompatActivity {

    ImageView yesIntro;
    ImageView noIntro;
    UserInfoManager userInfoManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_intro);

        yesIntro = findViewById(R.id.yes_intro);
        noIntro = findViewById(R.id.no_intro);

        userInfoManager = new UserInfoManager(StartIntroActivity.this);

        yesIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoManager.setFirstLogin("yes");
                Intent i = new Intent(StartIntroActivity.this, IntroAppActivity.class);
                startActivity(i);
                finish();
            }
        });

        noIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoManager.setFirstLogin("no");
                Intent i = new Intent(StartIntroActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}