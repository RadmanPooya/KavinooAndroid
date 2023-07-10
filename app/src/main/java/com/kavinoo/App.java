package com.kavinoo;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import ir.map.sdk_map.Mapir;

public class App extends Application {
    private static App base;
    public static Context context;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Mapir.getInstance(this, "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3In0.eyJhdWQiOiIxNjY2NCIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3IiwiaWF0IjoxNjQyMTIyNjk2LCJuYmYiOjE2NDIxMjI2OTYsImV4cCI6MTY0NDYyODI5Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.S6IMCplH_EasvLFTuSYXtMAIK1W1HOlAUYeYciNen1Q-JXmJs-326pziiw1m7RdWKv4naaaPGTiZk2ty0py5PlyHu6eEHKb8sM2ewKbqMcKjpL48O6WDRHw4ESZ3VjppvvSazHnPmxDOc5FtdjDcWKI4YJwXx1cArF8w4-FJNBjYf9YwTBeYlDWS7gS7SDXwl_XIUZK983ytr2gyY_T074d3Z4cqWtwkCQA3LddxxOy_b50BzP0v0aRTvd2nrF5p33K8syqOCDpSC_TrxdDxw2tEJQ4Hqi0LrZcwPaga6qskQyyboEMmBVJe2_npLMI7OacQd5NcedPMnAsXOGZyhw");
        context=getApplicationContext();
        TokenContainer.updateToken(new UserInfoManager(this).getToken());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    public static synchronized App getInstance() {
        if (base == null) {
            base = new App();
        }
        return base;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}

