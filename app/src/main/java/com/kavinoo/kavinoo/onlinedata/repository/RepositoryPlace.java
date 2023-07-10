package com.kavinoo.kavinoo.onlinedata.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;

public class RepositoryPlace {
    Context context;

    public RepositoryPlace(Context context) {
        this.context = context;
    }

    public LiveData<PlacesResponse> getPlacesListLData(String url){

        final MutableLiveData<PlacesResponse> mutableLiveData=new MutableLiveData<>();

        final StringRequest placeReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                mutableLiveData.setValue(placesResponse);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AAAA", error.getMessage());
            }
        });
        placeReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(placeReq, "PLACESREQ");
        return mutableLiveData;
    }
}
