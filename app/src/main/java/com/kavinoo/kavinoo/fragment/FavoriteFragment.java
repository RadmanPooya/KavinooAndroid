package com.kavinoo.kavinoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.MenuActivity;
import com.kavinoo.kavinoo.activity.ProfileActivity;
import com.kavinoo.kavinoo.localdata.adapter.PlacesListAdapterFavorite;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;
import com.kavinoo.kavinoo.localdata.viewmodel.PlaceDetailsViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    ViewPager2 viewPager;
    List<PlaceDetails> placesItemList = new ArrayList<>();

    PlaceDetailsViewModel placeDetailsViewModel;

    Context myContext;

    ImageView backFavorite;

    ImageView kavinooProfile;
    ImageView menuToolbar;
    ConstraintLayout toolbarMain;


    public FavoriteFragment() {
    }

    public FavoriteFragment(String title) {
        // Required empty public constructor
    }

    public FavoriteFragment(String title, Context context) {
        this.myContext=context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.view_pager);
        backFavorite=view.findViewById(R.id.back_favorite);
        toolbarMain=view.findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        final Animation aniZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in_two);
        final Animation aniZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out_two);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
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

                        PlacesListAdapterFavorite adapterPlaces = new PlacesListAdapterFavorite(placesItemList, myContext);

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