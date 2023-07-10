package com.kavinoo.kavinoo.interfaces;

import android.graphics.Bitmap;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public interface OnGlideLoaded {

    void onGlideLoaded(List<LatLng> latLngs, List<Bitmap> bitmaps);

}
