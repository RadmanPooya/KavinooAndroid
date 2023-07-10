package com.kavinoo.kavinoo.utils;

import android.content.Context;
import android.util.Log;

import com.kavinoo.kavinoo.BuildConfig;

import java.io.IOException;
import java.io.InputStream;

public class AppUtils {
    public static void logi(String msg){
        if (BuildConfig.DEBUG){
            Log.i("APP_DEBUG", msg);
        }
    }
    public static void logi2(String msg){
        if (BuildConfig.DEBUG){
            Log.i("APP_TEST", msg);
        }
    }



    public static String loadJSONFromAsset(Context context, String assetsFileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(assetsFileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
