package com.kavinoo.kavinoo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class UserInfoManager {
    private SharedPreferences sharedPreferences;

    public UserInfoManager(Context context) {
        sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public void saveInfo(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void setFirstLogin(String firstLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_login", firstLogin);
        editor.apply();
    }

    public void saveName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public void saveRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    public void savePinImage(String pinImage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pin_image", pinImage);
        editor.apply();
    }

    public void saveProvince(String province) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("province", province);
        editor.apply();
    }

    public void saveCounty(String county) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("county", county);
        editor.apply();
    }

    public void saveMobile(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public void saveScore(String score) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("score", score);
        editor.apply();
    }

    public void saveLocationLat(String lat) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lat", lat);
        editor.apply();
    }

    public void saveLocationLon(String lon) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lon", lon);
        editor.apply();
    }

    public void saveAddLocationLat(String lat) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lat_add", lat);
        editor.apply();
    }

    public void saveAddLocationLon(String lon) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lon_add", lon);
        editor.apply();
    }

    public void savePermission(String hasAccess) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_all", hasAccess);
        editor.apply();
    }

    @Nullable
    public String getProvince() {
        return sharedPreferences.getString("province", "");
    }

    @Nullable
    public String getFirstLogin() {
        return sharedPreferences.getString("first_login", "");
    }

    @Nullable
    public String getScore() {
        return sharedPreferences.getString("score", "");
    }

    @Nullable
    public String getCounty() {
        return sharedPreferences.getString("county", "");
    }

    @Nullable
    public String getPinImage() {
        return sharedPreferences.getString("pin_image", "");
    }

    @Nullable
    public String getLat() {
        return sharedPreferences.getString("lat", "");
    }

    @Nullable
    public String getLon() {
        return sharedPreferences.getString("lon", "");
    }

    @Nullable
    public String getAddLat() {
        return sharedPreferences.getString("lat_add", "");
    }

    @Nullable
    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    @Nullable
    public String getAddLon() {
        return sharedPreferences.getString("lon_add", "");
    }

    @Nullable
    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    @Nullable
    public String getRefreshToken() {
        return sharedPreferences.getString("refresh_token", "");
    }

    @Nullable
    public String getMobile() {
        return sharedPreferences.getString("mobile", "");
    }

    @Nullable
    public String getHasPermission() {
        return sharedPreferences.getString("access_all", null);
    }

    @Nullable
    public boolean hasMyPermission() {
        if (sharedPreferences.contains("access_all")) {
            return true;
        } else {
            return false;
        }
    }
}
