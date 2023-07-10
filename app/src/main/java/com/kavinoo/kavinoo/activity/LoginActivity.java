package com.kavinoo.kavinoo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.adapter.PlacesListAdapter;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;

import org.aviran.cookiebar2.CookieBar;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    ImageView sendCode;
    AppCompatEditText mobilePhoneEditText;
    ProgressBar progressBar;
    TextView sendCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sendCode=findViewById(R.id.send_code);
        mobilePhoneEditText=findViewById(R.id.mobile_phone_edit_text);
        progressBar =findViewById(R.id.spin_kit);
        sendCodeTextView=findViewById(R.id.send_code_text_view);

        ThreeBounce threeBounce=new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.INVISIBLE);

        showPrivacyPolicyDialog();

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputMobile=mobilePhoneEditText.getText().toString();
                String regexStr = "^[0-9]*$";
                if (inputMobile.startsWith("0") && inputMobile.matches(regexStr) && inputMobile.length()==11){
                    sendCodeTextView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    sendMessage(inputMobile);

                }else{
                    CookieBar.Builder c = CookieBar.build(LoginActivity.this);
                    c.setTitle(" درستی شماره موبایل خودتون رو بررسی بفرمایید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                }
            }
        });

        mobilePhoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mobilePhoneEditText.getText().toString().length()==11){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void sendMessage(final String mobile){

        final StringRequest sendMsgReq = new StringRequest(Request.Method.POST, KavinooLinks.SEND_OTP, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                sendCodeTextView.setVisibility(View.VISIBLE);
                CookieBar.Builder c = CookieBar.build(LoginActivity.this);
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
                params.put("phoneNumber", mobile);
                return params;
            }
        };

        sendMsgReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sendMsgReq, "LOGINREQ");

    }

    public void showPrivacyPolicyDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.privacy_policy_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}