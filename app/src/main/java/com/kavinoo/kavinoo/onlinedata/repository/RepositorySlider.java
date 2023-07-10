package com.kavinoo.kavinoo.onlinedata.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.onlinedata.model.slider.SlidesResponse;

public class RepositorySlider {

    Context context;

    public RepositorySlider(Context context) {
        this.context = context;
    }



    public LiveData<SlidesResponse> getSliderListLData(String url) {


        final MutableLiveData<SlidesResponse> mutableLiveData=new MutableLiveData<>();

        final StringRequest sliderDetailsReq=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                SlidesResponse slidesResponse = gson.fromJson(response, SlidesResponse.class);

                mutableLiveData.setValue(slidesResponse);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        sliderDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sliderDetailsReq, "SLIDERREQ");
        return mutableLiveData;
    }
}
