package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.adapter.PlacesListAdapterVip;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    List<PlacesItem> placesItemList = new ArrayList<>();


    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        viewPager=findViewById(R.id.view_pager);

        toolbarMain=findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        getOfferPlaces();
    }

    public void getOfferPlaces(){
        String url= KavinooLinks.GET_OFFER_PLACES;
        final StringRequest recreationReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                placesItemList = placesResponse.getPlaces();


                PlacesListAdapterVip adapterPlaces = new PlacesListAdapterVip(placesItemList, OfferActivity.this);

                viewPager.setAdapter(adapterPlaces);
                viewPager.setClipToPadding(false);
                viewPager.setClipChildren(false);
                viewPager.setOffscreenPageLimit(5);
                viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                if(placesItemList.size()>=2){
                    viewPager.setCurrentItem(1);
                }

                Log.i("REEC",placesItemList.toString());
                Log.i("REEC"," is heeeeeeeereeeeee");
                CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(1));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r=1-Math.abs(position);
                        page.setScaleY(0.72f+r*0.28f);
                        page.setScaleX(0.72f+r*0.28f);
                    }
                });

                viewPager.setPageTransformer(compositePageTransformer);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieBar.Builder c = CookieBar.build(OfferActivity.this);
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