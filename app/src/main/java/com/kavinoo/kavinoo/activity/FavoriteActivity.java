package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.adapter.PlacesListAdapterFavorite;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;
import com.kavinoo.kavinoo.localdata.viewmodel.PlaceDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    List<PlaceDetails> placesItemList = new ArrayList<>();

    PlaceDetailsViewModel placeDetailsViewModel;

    Context myContext;

    ImageView backFavorite;

    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        viewPager = findViewById(R.id.view_pager);
        backFavorite=findViewById(R.id.back_favorite);
        toolbarMain=findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        final Animation aniZoomIn = AnimationUtils.loadAnimation(FavoriteActivity.this, R.anim.zoom_in_two);
        final Animation aniZoomOut = AnimationUtils.loadAnimation(FavoriteActivity.this, R.anim.zoom_out_two);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FavoriteActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FavoriteActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        getFavoritePlaces();
        
        
    }


    public void getFavoritePlaces() {

        placeDetailsViewModel= new ViewModelProviders().of(this).get(PlaceDetailsViewModel.class);

        placeDetailsViewModel.allFavorite().observe(this, new Observer<List<PlaceDetails>>() {
            @Override
            public void onChanged(List<PlaceDetails> placeDetails) {
                placesItemList=placeDetails;

                PlacesListAdapterFavorite adapterPlaces = new PlacesListAdapterFavorite(placesItemList, FavoriteActivity.this);

                viewPager.setAdapter(adapterPlaces);
                viewPager.setClipToPadding(false);
                viewPager.setClipChildren(false);
                viewPager.setOffscreenPageLimit(5);
                viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                if(placesItemList.size()==0){

                }else{
                    backFavorite.setImageResource(R.drawable.backfavoriteplace);
                }
                if (placesItemList.size() > 2) {
                    viewPager.setCurrentItem(1);
                }else {
                    viewPager.setCurrentItem(0);
                }

                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(1));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.72f + r * 0.28f);
                        page.setScaleX(0.72f + r * 0.28f);
                    }
                });

                viewPager.setPageTransformer(compositePageTransformer);
            }
        });

    }

}