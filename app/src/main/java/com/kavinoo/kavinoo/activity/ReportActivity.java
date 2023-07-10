package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.adapter.ActivityScoreAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.ActivityScoreTransactionAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.AwardVoucherAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.AwardVoucherTransactionAdapter;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.activityscoretransaction.ActivityScoreTransactionResponse;
import com.kavinoo.kavinoo.onlinedata.model.awardvoucher.AwardVoucherResponse;
import com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction.AwardVoucherTransactionResponse;
import com.kavinoo.kavinoo.utils.NullStringToEmptyAdapterFactory;
import com.kavinoo.kavinoo.utils.TokenContainer;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {


    CardView cardAward;
    CardView cardActivity;
    TextView mtnActivity;
    TextView mtnAward;
    boolean isActivity=true;

    RecyclerView profileActivityScoreRecycler;
    ActivityScoreTransactionAdapter activityScoreTransactionAdapter;
    LinearLayoutManager layoutManagerActivityScoreTransaction;

    RecyclerView profileAwardVoucherRecycler;
    AwardVoucherTransactionAdapter awardVoucherTransactionAdapter;
    LinearLayoutManager layoutManagerAwardVoucherTransaction;

    ImageView emptyImageReport;

    CardView menuToolbar;
    CardView backToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        cardActivity=findViewById(R.id.card_activity);
        cardAward=findViewById(R.id.card_award);
        mtnActivity=findViewById(R.id.mtn_activity);
        mtnAward=findViewById(R.id.mtn_award);

        menuToolbar=findViewById(R.id.menu_toolbar);
        backToolbar=findViewById(R.id.back_toolbar);

        profileActivityScoreRecycler=findViewById(R.id.profile_activity_score_recycler);
        profileAwardVoucherRecycler=findViewById(R.id.profile_award_voucher_recycler);

        emptyImageReport=findViewById(R.id.empty_image_report);

        getProfileActivityScore();

        mtnAward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isActivity){
                    cardActivity.setVisibility(View.INVISIBLE);
                    cardAward.setVisibility(View.VISIBLE);
                    mtnActivity.setTextColor(Color.parseColor("#D9D9D9"));
                    mtnAward.setTextColor(Color.parseColor("#FFFFFF"));

                    isActivity=false;

                    profileAwardVoucherRecycler.setVisibility(View.VISIBLE);
                    profileActivityScoreRecycler.setVisibility(View.INVISIBLE);

                    getProfileAwardVoucher();
                }
            }
        });

        mtnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isActivity) {
                    cardAward.setVisibility(View.INVISIBLE);
                    cardActivity.setVisibility(View.VISIBLE);
                    mtnAward.setTextColor(Color.parseColor("#D9D9D9"));
                    mtnActivity.setTextColor(Color.parseColor("#FFFFFF"));

                    isActivity=true;

                    profileAwardVoucherRecycler.setVisibility(View.INVISIBLE);
                    profileActivityScoreRecycler.setVisibility(View.VISIBLE);

                    getProfileActivityScore();
                }
            }
        });

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReportActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
        backToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void getProfileAwardVoucher() {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.GET, KavinooLinks.AWARD_VOUCHER_TRANSACTION, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    AwardVoucherTransactionResponse awardVoucherTransactionResponse = gson.fromJson(response, AwardVoucherTransactionResponse.class);

                    if(awardVoucherTransactionResponse.getData().size()==0){
                        emptyImageReport.setVisibility(View.VISIBLE);
                        emptyImageReport.setImageResource(R.drawable.awardvoucherempty);
                    }else{
                        emptyImageReport.setVisibility(View.INVISIBLE);
                        awardVoucherTransactionAdapter = new AwardVoucherTransactionAdapter(ReportActivity.this, awardVoucherTransactionResponse.getData());
                        profileAwardVoucherRecycler.setAdapter(awardVoucherTransactionAdapter);
                        layoutManagerAwardVoucherTransaction = new LinearLayoutManager(ReportActivity.this);
                        profileAwardVoucherRecycler.setLayoutManager(layoutManagerAwardVoucherTransaction);
                        profileAwardVoucherRecycler.setHasFixedSize(true);
                    }


                }catch (Exception e){

                    Log.i("SSCC",e.getMessage().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                CookieBar.Builder c = CookieBar.build(ReportActivity.this);
                c.setTitle("خطا در اتصال به سرور");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + TokenContainer.getToken());
                return header;
            }
        };

        sendMsgReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sendMsgReq, "CONFIRMCODEREQ");

    }

    public void getProfileActivityScore() {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.GET, KavinooLinks.ACTIVITY_SCORE_TRANSACTION, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    ActivityScoreTransactionResponse activityScoreTransactionResponse = gson.fromJson(response, ActivityScoreTransactionResponse.class);

                    if(activityScoreTransactionResponse.getData().size()==0){
                        emptyImageReport.setVisibility(View.VISIBLE);
                        emptyImageReport.setImageResource(R.drawable.activityscoreempty);
                    }else{
                        emptyImageReport.setVisibility(View.INVISIBLE);
                        activityScoreTransactionAdapter = new ActivityScoreTransactionAdapter(ReportActivity.this, activityScoreTransactionResponse.getData());
                        profileActivityScoreRecycler.setAdapter(activityScoreTransactionAdapter);
                        layoutManagerActivityScoreTransaction = new LinearLayoutManager(ReportActivity.this);
                        profileActivityScoreRecycler.setLayoutManager(layoutManagerActivityScoreTransaction);
                        profileActivityScoreRecycler.setHasFixedSize(true);
                    }

                } catch (Exception e) {

                    Log.i("SSCC", e.getMessage().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                CookieBar.Builder c = CookieBar.build(ReportActivity.this);
                c.setTitle("خطا در اتصال به سرور");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + TokenContainer.getToken());
                return header;
            }
        };

        sendMsgReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sendMsgReq, "CONFIRMCODEREQ");

    }

}