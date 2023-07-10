package com.kavinoo.kavinoo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.Map;

public class AccountDetailActivity extends AppCompatActivity {


    ImageView sabtName;
    AppCompatEditText nameFamilyEditText;

    ProgressBar progressBar;

    TextView sabtTextView;

    UserInfoManager userInfoManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        userInfoManager=new UserInfoManager(AccountDetailActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AccountDetailActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            AccountDetailActivity.this.getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        sabtName=findViewById(R.id.sabt_name);
        nameFamilyEditText=findViewById(R.id.name_family_edit_text);
        progressBar =findViewById(R.id.spin_kit);
        sabtTextView=findViewById(R.id.sabt_text_view);

        ThreeBounce threeBounce=new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.INVISIBLE);

        sabtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!nameFamilyEditText.getText().toString().matches("")){
                    sabtTextView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    sabtNameMethod(nameFamilyEditText.getText().toString());

                }else{
                    CookieBar.Builder c = CookieBar.build(AccountDetailActivity.this);
                    c.setTitle("لطفا نام و نام خانوادگی را وارد نمایید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                }
            }
        });




    }


    public void sabtNameMethod(final String nameFamily){

        final StringRequest updateAccountReq = new StringRequest(Request.Method.POST, KavinooLinks.UPDATE_ACCOUNT, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                userInfoManager.saveName(nameFamily);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                sabtTextView.setVisibility(View.VISIBLE);
                CookieBar.Builder c = CookieBar.build(AccountDetailActivity.this);
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
                params.put("name", nameFamily);
                params.put("family", "");
                params.put("gender", "men");
                params.put("email", "");
                params.put("username", "");
                params.put("password","");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + userInfoManager.getToken());
                return header;
            }
        };

        updateAccountReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(updateAccountReq, "LOGINREQ");

    }


}