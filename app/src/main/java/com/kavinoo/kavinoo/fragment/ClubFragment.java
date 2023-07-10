package com.kavinoo.kavinoo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kavinoo.kavinoo.activity.LoginActivity;
import com.kavinoo.kavinoo.activity.MenuActivity;
import com.kavinoo.kavinoo.activity.ProfileActivity;
import com.kavinoo.kavinoo.activity.RegisterActivity;
import com.kavinoo.kavinoo.activity.ReportActivity;
import com.kavinoo.kavinoo.onlinedata.adapter.ActivityScoreAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.AwardVoucherAdapter;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.activityscore.ActivityScoreResponse;
import com.kavinoo.kavinoo.onlinedata.model.awardvoucher.AwardVoucherResponse;
import com.kavinoo.kavinoo.onlinedata.model.token.TokenResponse;
import com.kavinoo.kavinoo.utils.NullStringToEmptyAdapterFactory;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.Map;


public class ClubFragment extends Fragment implements AwardVoucherAdapter.IAwardSuccess {


    RecyclerView activityScoreRecycler;
    ActivityScoreAdapter activityScoreAdapter;
    LinearLayoutManager layoutManagerActivityScore;

    RecyclerView awardVoucherRecycler;
    AwardVoucherAdapter awardVoucherAdapter;
    LinearLayoutManager layoutManagerAwardVoucher;


    Context myContext;
    UserInfoManager userInfoManager;
    TextView userScore;

    ImageView showReport;

    ConstraintLayout clubDataConstraint;

    ImageView regClub;

    ConstraintLayout clubRegisterConstraint;

    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;

    public ClubFragment() {
        // Required empty public constructor
    }

    public ClubFragment(String title, Context context) {
        this.myContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_club, container, false);

        userInfoManager=new UserInfoManager(getActivity());
        activityScoreRecycler=view.findViewById(R.id.recycler_activity_score);
        awardVoucherRecycler=view.findViewById(R.id.recycler_award_voucher);
        userScore=view.findViewById(R.id.user_score);
        showReport=view.findViewById(R.id.show_report);
        clubDataConstraint=view.findViewById(R.id.club_data_constraint);
        regClub=view.findViewById(R.id.reg_club);
        clubRegisterConstraint=view.findViewById(R.id.club_register_constraint);
        toolbarMain=view.findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if (TokenContainer.getToken().equals("")) {
            clubDataConstraint.setVisibility(View.GONE);
            clubRegisterConstraint.setVisibility(View.VISIBLE);
        } else {
            clubDataConstraint.setVisibility(View.VISIBLE);
            clubRegisterConstraint.setVisibility(View.GONE);
            getToken(userInfoManager.getRefreshToken());
        }*/

        regClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });

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

    }

    public void getActivityScoreList() {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.GET, KavinooLinks.ACTIVITY_SCORE_LIST, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    ActivityScoreResponse activityScoreResponse = gson.fromJson(response, ActivityScoreResponse.class);

                    Log.i("SSCC",activityScoreResponse.toString());

                    activityScoreAdapter = new ActivityScoreAdapter(getActivity().getApplicationContext(), activityScoreResponse.getData());
                    activityScoreRecycler.setAdapter(activityScoreAdapter);
                    layoutManagerActivityScore = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    layoutManagerActivityScore.setReverseLayout(true);
                    activityScoreRecycler.setLayoutManager(layoutManagerActivityScore);
                    activityScoreRecycler.setHasFixedSize(true);

                    getAwardVoucherList();

                }catch (Exception e){

                    Log.i("SSCC",e.getMessage().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                CookieBar.Builder c = CookieBar.build(getActivity());
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


    public void getAwardVoucherList() {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.GET, KavinooLinks.AWARD_VOUCHER_LIST, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    AwardVoucherResponse awardVoucherResponse = gson.fromJson(response, AwardVoucherResponse.class);

                    awardVoucherAdapter = new AwardVoucherAdapter(getActivity().getApplicationContext(), awardVoucherResponse.getData(),userInfoManager.getScore(),getActivity(),ClubFragment.this);
                    awardVoucherRecycler.setAdapter(awardVoucherAdapter);
                    layoutManagerAwardVoucher = new LinearLayoutManager(getActivity().getApplicationContext());
                    layoutManagerAwardVoucher.setReverseLayout(true);
                    awardVoucherRecycler.setLayoutManager(layoutManagerAwardVoucher);
                    awardVoucherRecycler.setHasFixedSize(true);

                }catch (Exception e){

                    Log.i("SSCC",e.getMessage().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                CookieBar.Builder c = CookieBar.build(getActivity());
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


    public void getToken(final String refreshToken) {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.POST, KavinooLinks.GET_TOKEN, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                //set shared prefrences

                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();


                    TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);

                    userInfoManager.saveInfo(tokenResponse.getData().getToken().getToken());
                    userInfoManager.saveScore(String.valueOf(tokenResponse.getData().getUser().getScore()));
                    userInfoManager.saveRefreshToken(tokenResponse.getData().getToken().getRefreshToken());
                    userInfoManager.saveName(tokenResponse.getData().getUser().getName());

                    TokenContainer.updateToken(tokenResponse.getData().getToken().getToken());

                    userScore.setText(tokenResponse.getData().getUser().getScore()+" امتیاز ");
                    getActivityScoreList();
                }catch (Exception e){
                    clubDataConstraint.setVisibility(View.GONE);
                    clubRegisterConstraint.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieBar.Builder c = CookieBar.build(getActivity());
                c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("refresh_token", refreshToken);
                return params;
            }
        };

        sendMsgReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sendMsgReq, "CONFIRMCODEREQ");

    }

    @Override
    public void onResume() {
        super.onResume();
        if (TokenContainer.getToken().equals("")) {
            clubDataConstraint.setVisibility(View.GONE);
            clubRegisterConstraint.setVisibility(View.VISIBLE);
        } else {
            clubDataConstraint.setVisibility(View.VISIBLE);
            clubRegisterConstraint.setVisibility(View.GONE);
            getToken(userInfoManager.getRefreshToken());
        }
    }

    @Override
    public void onSuccessAward(String awardId) {
        getToken(userInfoManager.getRefreshToken());
    }
}