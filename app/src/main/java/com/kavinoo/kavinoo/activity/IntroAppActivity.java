package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.adapter.IntroViewPagerAdapter;
import com.kavinoo.kavinoo.localdata.model.intro.IntroItem;
import com.kavinoo.kavinoo.onlinedata.adapter.SliderAdapter;
import com.kavinoo.kavinoo.onlinedata.model.token.User;
import com.kavinoo.kavinoo.utils.UserInfoManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class IntroAppActivity extends AppCompatActivity {

    DotsIndicator dotsIndicator;
    ViewPager2 viewPagerIntro;
    IntroViewPagerAdapter introViewPagerAdapter;
    ImageView nextIntro;
    ImageView backIntro;
    int localPosition=0;
    boolean isForward = true;
    UserInfoManager userInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_app);

        dotsIndicator = findViewById(R.id.dots_indicator);
        viewPagerIntro = findViewById(R.id.view_pager_intro);
        nextIntro = findViewById(R.id.next_intro);
        backIntro = findViewById(R.id.back_intro);

        userInfoManager = new UserInfoManager(IntroAppActivity.this);
        userInfoManager.setFirstLogin("yes");

        List<IntroItem> introItemList;
        introItemList = new ArrayList<>();
        IntroItem introItemOne = new IntroItem(R.drawable.introone,"دنبال یه راه آسون برای پیدا کردن مکان های اطرافت هستی ؟","جستجو کن ، مسیریابی کن ، بهترینش رو پیدا کن");
        IntroItem introItemTwo = new IntroItem(R.drawable.introtwo,"می خوای دقیق پیدا کنی ؟","فیلتر کن ، مرتب کن ، جستجو کن");
        IntroItem introItemThree = new IntroItem(R.drawable.introthree,"از گرفتن جوایز و کد های تخفیف لذت می بری ؟","اعلام حضور کن ، نظر بده ، امتیاز بگیر");
        IntroItem introItemFour = new IntroItem(R.drawable.introfour,"صاحب یک کسب و کار هستی ؟","مکانش رو ثبت کن");
        IntroItem introItemFive = new IntroItem(R.drawable.introfive,"رفتی و خوب نبود ؟","دیگران رو راهنمایی کن");

        introItemList.add(introItemOne);
        introItemList.add(introItemTwo);
        introItemList.add(introItemThree);
        introItemList.add(introItemFour);
        introItemList.add(introItemFive);

        introViewPagerAdapter=new IntroViewPagerAdapter(introItemList,viewPagerIntro,IntroAppActivity.this);
        viewPagerIntro.setAdapter(introViewPagerAdapter);
        dotsIndicator.attachTo(viewPagerIntro);

        nextIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(localPosition == introItemList.size()-1){
                    Intent i = new Intent(IntroAppActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                viewPagerIntro.setCurrentItem(viewPagerIntro.getCurrentItem()+1);
            }
        });
        backIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerIntro.setCurrentItem(viewPagerIntro.getCurrentItem()-1);
            }
        });
        Animation aniFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_two);
        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_two);

        viewPagerIntro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                localPosition = position;
                if(position == 0){
                    backIntro.setVisibility(View.INVISIBLE);
                    backIntro.startAnimation(aniFadeOut);
                    isForward = true;
                }else if(position == 1){
                    backIntro.setVisibility(View.VISIBLE);
                    if(isForward){
                        backIntro.startAnimation(aniFadeIn);
                    }else{

                    }
                    isForward = true;
                }else{
                    backIntro.setVisibility(View.VISIBLE);
                    isForward = false;
                }

                if(position == 1){

                }
                if (position == introItemList.size()-1) {
                    nextIntro.setImageResource(R.drawable.startintrobutton);
                    nextIntro.startAnimation(aniFadeIn);
                } else {
                    nextIntro.setImageResource(R.drawable.nextintrobutton);
                }
            }
        });

    }
}