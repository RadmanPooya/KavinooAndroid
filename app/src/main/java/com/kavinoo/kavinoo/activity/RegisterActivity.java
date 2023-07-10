package com.kavinoo.kavinoo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.token.TokenResponse;
import com.kavinoo.kavinoo.onlinedata.model.verify.VerifyResponse;
import com.kavinoo.kavinoo.utils.NullStringToEmptyAdapterFactory;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    String mobile;
    TextView mobilePhoneSent;
    CountDownTimer countDownTimer;
    TextView timer;
    CardView sendAgain;
    CardView editPhone;
    TextView sendAgainTextView;

    ProgressBar progressBarLogin;

    EditText regNumber1;
    EditText regNumber2;
    EditText regNumber3;
    EditText regNumber4;

    String inputCode;

    ImageView loginToKavinoo;
    TextView loginToKavinooTextView;
    ProgressBar progressBarSendAgain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RegisterActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            RegisterActivity.this.getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        mobile=getIntent().getStringExtra("mobile");

        mobilePhoneSent=findViewById(R.id.mobile_phone_sent);
        timer=findViewById(R.id.timer);
        sendAgain=findViewById(R.id.send_again);
        editPhone=findViewById(R.id.edit_phone);
        progressBarLogin=findViewById(R.id.spin_kit_login);
        progressBarSendAgain=findViewById(R.id.spin_kit_send_again);
        regNumber1=findViewById(R.id.reg_number1);
        regNumber2=findViewById(R.id.reg_number2);
        regNumber3=findViewById(R.id.reg_number3);
        regNumber4=findViewById(R.id.reg_number4);
        loginToKavinoo=findViewById(R.id.login_to_kavinoo);
        sendAgainTextView=findViewById(R.id.send_again_text_view);
        loginToKavinooTextView=findViewById(R.id.login_to_kavinoo_text_view);

        mobilePhoneSent.setText(mobile);
        countDownTimer=new CountDownTimer(90000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((millisUntilFinished / 60000)+":"+(millisUntilFinished % 60000 / 1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarSendAgain.setVisibility(View.VISIBLE);
                sendAgainTextView.setVisibility(View.INVISIBLE);
                sendMessageAgain(mobile);
            }
        });
        ThreeBounce threeBounce=new ThreeBounce();
        progressBarLogin.setIndeterminateDrawable(threeBounce);
        progressBarLogin.setVisibility(View.INVISIBLE);

        ThreeBounce threeBounce2=new ThreeBounce();
        progressBarSendAgain.setIndeterminateDrawable(threeBounce2);
        progressBarSendAgain.setVisibility(View.INVISIBLE);

        regNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(regNumber1.getText().toString().length()==1){
                    regNumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        regNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(regNumber2.getText().toString().length()==1){
                    regNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        regNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(regNumber3.getText().toString().length()==1){
                    regNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        regNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(regNumber4.getText().toString().length()==1){

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    if(regNumber1.getText().toString().matches("") || regNumber2.getText().toString().matches("") || regNumber3.getText().toString().matches("") || regNumber4.getText().toString().matches("")){

                    }else{
                        inputCode=regNumber1.getText().toString()+regNumber2.getText().toString()+regNumber3.getText().toString()+regNumber4.getText().toString();
                        loginToKavinooTextView.setVisibility(View.INVISIBLE);
                        progressBarLogin.setVisibility(View.VISIBLE);
                        sendConfirm(inputCode,mobile);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginToKavinoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCode=regNumber1.getText().toString()+regNumber2.getText().toString()+regNumber3.getText().toString()+regNumber4.getText().toString();
                loginToKavinooTextView.setVisibility(View.INVISIBLE);
                progressBarLogin.setVisibility(View.VISIBLE);
                sendConfirm(inputCode,mobile);
            }
        });

    }

    public void sendConfirm(final String code, final String mobileNumber) {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.POST, KavinooLinks.VERIFY_OTP, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                VerifyResponse verifyResponse = gson.fromJson(response, VerifyResponse.class);

                getToken(verifyResponse.getData().getRefreshToken());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginToKavinooTextView.setVisibility(View.VISIBLE);
                progressBarLogin.setVisibility(View.INVISIBLE);
                CookieBar.Builder c = CookieBar.build(RegisterActivity.this);
                c.setTitle(" لطفا درستی کد تایید و یا اتصال اینترنت خود را بررسی فرمایید");
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
                params.put("code", code);
                params.put("phoneNumber", mobileNumber);
                return params;
            }
        };

        sendMsgReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sendMsgReq, "CONFIRMCODEREQ");

    }

    public void sendMessageAgain(final String mobile){
        final StringRequest sendMsgReq = new StringRequest(Request.Method.POST, KavinooLinks.SEND_OTP, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                progressBarSendAgain.setVisibility(View.INVISIBLE);
                sendAgainTextView.setVisibility(View.VISIBLE);
                countDownTimer.start();
                CookieBar.Builder c = CookieBar.build(RegisterActivity.this);
                c.setTitle(" کد تایید مجددا ارسال شد");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarSendAgain.setVisibility(View.INVISIBLE);
                sendAgainTextView.setVisibility(View.VISIBLE);
                CookieBar.Builder c = CookieBar.build(RegisterActivity.this);
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
        App.getInstance().addToRequestQueue(sendMsgReq, "LOGINAGAINREQ");

    }

    public void getToken(final String refreshToken) {
        final StringRequest sendMsgReq = new StringRequest(Request.Method.POST, KavinooLinks.GET_TOKEN, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                //set shared prefrences

                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();



                TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);
                Log.i("VVVV",response.toString()+"");



                TokenContainer.updateToken(tokenResponse.getData().getToken().getToken());
                UserInfoManager userInfoManager=new UserInfoManager(RegisterActivity.this);
                userInfoManager.saveInfo(tokenResponse.getData().getToken().getToken());
                userInfoManager.saveScore(String.valueOf(tokenResponse.getData().getUser().getScore()));
                userInfoManager.saveRefreshToken(tokenResponse.getData().getToken().getRefreshToken());
                userInfoManager.saveName(tokenResponse.getData().getUser().getName());
                TokenContainer.updateToken(tokenResponse.getData().getToken().getToken());
                userInfoManager.saveMobile(mobile);
                Intent i = new Intent(RegisterActivity.this, AccountDetailActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieBar.Builder c = CookieBar.build(RegisterActivity.this);
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


}