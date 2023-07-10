package com.kavinoo.kavinoo.activity;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.getaddressresponse.GetAddressResponse;
import com.kavinoo.kavinoo.onlinedata.model.searchaddress.SearchAddressResponse;
import com.kavinoo.kavinoo.onlinedata.model.searchaddresssend.SendSearchAddress;
import com.kavinoo.kavinoo.utils.MapKey;
import com.kavinoo.kavinoo.utils.UserInfoManager;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.squareup.picasso.Picasso;

import org.aviran.cookiebar2.CookieBar;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.map.sdk_map.MapirStyle;

public class SelectLocationActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteSearchAddress;
    SearchAddressResponse searchAddressResponse;


    CardView myLocationCardView;

    MapboxMap map;
    Style mapStyle;
    MapView mapView;

    LatLng lastKnowLatLng = null;

    ImageView sabtloc;

    int count=0;

    UserInfoManager userInfoManager;

    String addLat;
    String addLon;

    String pinImage;

    LocationManager mLocationManager;
    Location myLocation;

    // This is an object to manage location update
    private LocationEngine locationEngine;

    // These are two number to handle interval of location update and its delay
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 100000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 1;

    // Declare an object with CustomLocationCallBack class type
    private MyLocationCallback callback = new MyLocationCallback(this);

    // This is a custom Class to manage last known location
    private static class MyLocationCallback implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<SelectLocationActivity> activityWeakReference;

        MyLocationCallback(SelectLocationActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            SelectLocationActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null)
                    return;


                activity.lastKnowLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                //Toast.makeText(activity, activity.lastKnowLatLng.getLatitude() + ", " + activity.lastKnowLatLng.getLongitude(), Toast.LENGTH_SHORT).show();
                activity.zoomToSpecificLocation(new LatLng(activity.lastKnowLatLng.getLatitude(), activity.lastKnowLatLng.getLongitude()));
                // Pass the new location to the Mapir SDK's LocationComponent
                if (activity.map != null && result.getLastLocation() != null)
                    activity.map.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            SelectLocationActivity activity = activityWeakReference.get();

            if (activity != null) {

            }
            //Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Call this method to enable location update and see it in map
    @SuppressLint("MissingPermission")
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.RED)
                    .build();

            // Get an instance of the component
            LocationComponent locationComponent = map.getLocationComponent();
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, mapStyle)
                            .useDefaultLocationEngine(false) // This line is necessary
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();

            // Add the location icon click listener
            locationComponent.addOnLocationClickListener(new OnLocationClickListener() {
                @Override
                public void onLocationComponentClick() {
                }
            });
        } else {
            PermissionsManager permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted)
                        enableLocationComponent();
                    else {

                    }
                    //Toast.makeText(SelectLocationActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            });
            permissionsManager.requestLocationPermissions(this);
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }


    ImageView sabtLocation;


    ImageView imageMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        userInfoManager=new UserInfoManager(SelectLocationActivity.this);

        pinImage=userInfoManager.getPinImage();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SelectLocationActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            SelectLocationActivity.this.getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        checkPermission();

        myLocationCardView=findViewById(R.id.my_location_card_view);
        myLocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zoomToSpecificLocation(new LatLng(lastKnowLatLng.getLatitude(), lastKnowLatLng.getLongitude()));
                enableLocationComponent();
            }
        });
        autoCompleteSearchAddress=findViewById(R.id.auto_complete_search_address);
        autoCompleteSearchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    searchInPlaces(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteSearchAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) SelectLocationActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SelectLocationActivity.this.getCurrentFocus().getWindowToken(), 0);
                LatLng point=new LatLng();
                point.setLatitude(searchAddressResponse.getValue().get(position).getGeom().getCoordinates().get(1));
                point.setLongitude(searchAddressResponse.getValue().get(position).getGeom().getCoordinates().get(0));
                zoomToSpecificLocation(point);

            }
        });

        sabtLocation=findViewById(R.id.sabt_location);

        sabtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count==0){
                    CookieBar.Builder c = CookieBar.build(SelectLocationActivity.this);
                    c.setTitle(" لطفا با کلیک برروی نقشه ، محل مورد نظر خود را انتخاب نمایید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }else{
                    userInfoManager.saveAddLocationLat(addLat);
                    userInfoManager.saveAddLocationLon(addLon);
                    finish();
                }


            }
        });

        imageMarker=findViewById(R.id.image_marker);

        Picasso.with(SelectLocationActivity.this).load(pinImage).into(imageMarker);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                map = mapboxMap;
                map.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        //moveCameraWithoutOption();

                        enableLocationComponent();
                    }
                });

                map.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        LatLng latLngcamera=new LatLng();
                        latLngcamera.setLatitude(map.getCameraPosition().target.getLatitude());
                        latLngcamera.setLongitude(map.getCameraPosition().target.getLongitude());
                        Log.i("CAMPO",latLngcamera.toString());


                        addLat=String.valueOf(latLngcamera.getLatitude());
                        addLon=String.valueOf(latLngcamera.getLongitude());

                        /*Bitmap icon = BitmapFactory.decodeResource(SelectAddressActivity.this.getResources(), R.drawable.pinimage);

                        addSymbolToMap(latLngcamera, icon);*/

                        getCity(addLat,addLon);

                        count=1;

                    }
                });

                /*map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull final LatLng point) {
                        if(count==0){

                            //Toast.makeText(SelectLocationActivity.this, "Click\npoint: " + point.getLatitude() + "," + point.getLongitude(), Toast.LENGTH_SHORT).show();

                            addLat=String.valueOf(point.getLatitude());
                            addLon=String.valueOf(point.getLongitude());

                            Picasso.with(SelectLocationActivity.this).load(pinImage).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    Bitmap bitmapPin = Bitmap.createBitmap(bitmap);
                                    addSymbolToMap(point, bitmapPin);
                                    count=1;
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }

                            });
                        }

                        return false;
                    }
                });*/

            }
        });

    }


    private void addSymbolToMap(LatLng samplePoint, Bitmap bitmap) {
        mapStyle.addImage("sample_image_id", bitmap);
        // create symbol manager object
        SymbolManager sampleSymbolManager = new SymbolManager(mapView, map, mapStyle);
        sampleSymbolManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public void onAnnotationClick(Symbol symbol) {
                //Toast.makeText(SelectLocationActivity.this, "This is CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {
                //Toast.makeText(SelectLocationActivity.this, "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
        // set non-data-driven properties, such as:
        sampleSymbolManager.setIconAllowOverlap(true);
        sampleSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
        // Add symbol at specified lat/lon
        SymbolOptions sampleSymbolOptions = new SymbolOptions();
        sampleSymbolOptions.withLatLng(samplePoint);
        sampleSymbolOptions.withIconImage("sample_image_id");
        int valueInPixels = (int) getResources().getDimension(R.dimen._100sdp);

        float textTabSize= (float) (valueInPixels/540f);

        sampleSymbolOptions.withIconSize(textTabSize);
        // save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }



    private void moveCameraWithoutOption() {
        LatLng samplePoint = new LatLng(35.732521, 51.422575);
        map.easeCamera(CameraUpdateFactory.newLatLng(samplePoint));
    }

    private void zoomToSpecificLocation(LatLng samplePoint) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, 14));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLoc();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            getLoc();

        } else {

        }
    }

    public void getLoc() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        if (myLocation == null) {
            myLocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        } else {


        }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
                2, mLocationListener);

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLocation = location;
            userInfoManager.saveLocationLat(String.valueOf(myLocation.getLatitude()));
            userInfoManager.saveLocationLon(String.valueOf(myLocation.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }

        @Override
        public void onProviderEnabled(String provider) {
            getLoc();


        }

        @Override
        public void onProviderDisabled(String provider) {
            showLocationOn();
        }
    };


    public void showLocationOn() {
        final Dialog dialog = new Dialog(SelectLocationActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.location_on_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView powerOnLocation;
        ImageView cancelLocation;

        powerOnLocation = dialog.findViewById(R.id.power_on_location);
        cancelLocation = dialog.findViewById(R.id.cancel_on_location);

        powerOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });
        cancelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    String[] addresses;

    public void searchInPlaces(final String word){
        final StringRequest searchAddress = new StringRequest(Request.Method.POST, KavinooLinks.SEARCH_ADDRESS, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                searchAddressResponse = gson.fromJson(response,SearchAddressResponse.class);


                addresses=new String[searchAddressResponse.getValue().size()];
                for(int i=0;i<searchAddressResponse.getValue().size();i++){
                    addresses[i]=searchAddressResponse.getValue().get(i).getAddress();
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SelectLocationActivity.this, R.layout.list_item_search,R.id.text_view_address,addresses);
                autoCompleteSearchAddress.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SelectLocationActivity.this, "خطا در اتصال به سرور - لطفا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                SendSearchAddress sendSearchAddress=new SendSearchAddress();
                sendSearchAddress.setText(word);
                sendSearchAddress.setSelect("roads,poi");
                sendSearchAddress.setFilter("province eq البرز");
                String json = new Gson().toJson(sendSearchAddress);

                Log.i("QQQQ", json);

                return json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("x-api-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3In0.eyJhdWQiOiIxNjY2NCIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3IiwiaWF0IjoxNjQyMTIyNjk2LCJuYmYiOjE2NDIxMjI2OTYsImV4cCI6MTY0NDYyODI5Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.S6IMCplH_EasvLFTuSYXtMAIK1W1HOlAUYeYciNen1Q-JXmJs-326pziiw1m7RdWKv4naaaPGTiZk2ty0py5PlyHu6eEHKb8sM2ewKbqMcKjpL48O6WDRHw4ESZ3VjppvvSazHnPmxDOc5FtdjDcWKI4YJwXx1cArF8w4-FJNBjYf9YwTBeYlDWS7gS7SDXwl_XIUZK983ytr2gyY_T074d3Z4cqWtwkCQA3LddxxOy_b50BzP0v0aRTvd2nrF5p33K8syqOCDpSC_TrxdDxw2tEJQ4Hqi0LrZcwPaga6qskQyyboEMmBVJe2_npLMI7OacQd5NcedPMnAsXOGZyhw");
                return params;
            }
        };

        searchAddress.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(searchAddress, "CONFIRMREQ");
    }


    public void getCity(String lat,String lon){

        final StringRequest getCityReq = new StringRequest(Request.Method.GET, KavinooLinks.GET_ADDRESS+"?lat="+lat+"&lon="+lon, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                GetAddressResponse getAddressResponse = gson.fromJson(response,GetAddressResponse.class);

                userInfoManager.saveProvince(getAddressResponse.getProvince());
                userInfoManager.saveCounty(getAddressResponse.getCounty());

                Log.i("CCCTTT",response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-api-key", MapKey.MAP_API_KEY);
                return params;
            }
        };

        getCityReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(getCityReq, "GETCITYREQ");
    }


}