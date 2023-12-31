package com.kavinoo.kavinoo.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesResponse;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    ImageView splashLogo;
    ImageView towerImageView;
    ImageView logoLocationImageView;
    ImageView bottomSplashImageView;
    ArrayList<CategoriesItem> categoryModelArrayList;
    ArrayList<CategoriesItem> secCategoryModelArrayList;
    String categoryUrlReq;
    String secCategoryUrlReq;
    UserInfoManager userInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userInfoManager = new UserInfoManager(SplashScreen.this);

        categoryModelArrayList = new ArrayList<>();
        secCategoryModelArrayList = new ArrayList<>();



        splashLogo = findViewById(R.id.splash_logo);
        towerImageView = findViewById(R.id.tower_image_view);
        logoLocationImageView = findViewById(R.id.logo_location_image_view);
        bottomSplashImageView = findViewById(R.id.bottom_splash_image_view);

        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_three);

        splashLogo.startAnimation(aniFade);
        //bottomSplashImageView.startAnimation(aniFade);

        //Glide.with(SplashScreen.this).asGif().load(R.drawable.giflogo).into(splashLogo);

        Animation animZoomLocation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_anim_in);


        logoLocationImageView.startAnimation(animZoomLocation);

        /*towerImageView.setVisibility(View.VISIBLE);
        Animation bottomToTopFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_bottom_to_top);
        towerImageView.startAnimation(bottomToTopFade);*/

        final Runnable[] runnableLogo = {null};

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*logoLocationImageView.setVisibility(View.VISIBLE);
                Animation animZoomLocation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_anim_in);
                logoLocationImageView.startAnimation(animZoomLocation);*/
            }
        }, 1000);

        final Runnable[] runnableFinish = {null};

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(userInfoManager.getFirstLogin().equals("")){
                    Intent i = new Intent(SplashScreen.this, StartIntroActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }
}