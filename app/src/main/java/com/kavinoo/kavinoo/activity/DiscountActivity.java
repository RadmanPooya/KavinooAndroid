package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.adapter.PlacesListAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.PlacesListAdapterVip;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    List<PlacesItem> placesItemList = new ArrayList<>();

    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;

    RecyclerView recyclerPlaces;
    RecyclerView.Adapter adapterPlaces;
    RecyclerView.LayoutManager layoutManagerPlaces;
    ShimmerFrameLayout shimmerPlaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        viewPager=findViewById(R.id.view_pager);

        toolbarMain=findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        shimmerPlaceList = findViewById(R.id.shimmer_place_list);
        recyclerPlaces = findViewById(R.id.recycler_places_with_category);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiscountActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiscountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        getDiscountPlaces();
    }

    public void getDiscountPlaces(){
        String url= KavinooLinks.GET_DISCOUNT_PLACES;
        final StringRequest recreationReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                placesItemList = placesResponse.getPlaces();


                adapterPlaces = new PlacesListAdapter(placesItemList, DiscountActivity.this);
                recyclerPlaces.setAdapter(adapterPlaces);
                layoutManagerPlaces = new LinearLayoutManager(DiscountActivity.this);
                recyclerPlaces.setLayoutManager(layoutManagerPlaces);
                recyclerPlaces.setHasFixedSize(true);
                adapterPlaces.notifyDataSetChanged();

                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                shimmerPlaceList.startAnimation(animFadeOut);
                shimmerPlaceList.setVisibility(View.GONE);
                recyclerPlaces.setVisibility(View.VISIBLE);
                recyclerPlaces.startAnimation(animFadeIn);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieBar.Builder c = CookieBar.build(DiscountActivity.this);
                c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        recreationReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(recreationReq, "RECREATIONReq");

    }
}