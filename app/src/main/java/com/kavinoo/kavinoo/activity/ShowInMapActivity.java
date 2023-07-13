package com.kavinoo.kavinoo.activity;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.fragment.AroundMeFragment;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;
import com.kavinoo.kavinoo.utils.UserInfoManager;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;

public class ShowInMapActivity extends AppCompatActivity {



    MapboxMap map;
    Style mapStyle;
    MapView mapView;

    //place variables

    ConstraintLayout constraintPlaceAroundMe;
    ImageView headerImagePlaceListImageView;
    TextView titlePlaceListTextView;
    TextView distancePlaceListTextView;
    TextView addressPlaceListTextView;
    TextView visitCountPlaceListTextView;
    TextView commentCountPlaceListTextView;
    TextView favoriteCountPlaceListTextView;
    CardView placeItemBorderCardView;
    ImageView rateImageView;
    Animation aniZoomOut;
    Animation aniZoomIn;

    //end

    //voice


    ImageView voiceSearchCatFr;

    public static final Integer RecordAudioRequestCode = 101;

    private SpeechRecognizer speechRecognizer;

    Intent speechRecognizerIntent;

    boolean performingSpeechSetup;

    boolean voiceClicked = false;




    //end


    List<PlacesItem> placesItemList = new ArrayList<>();
    AppCompatEditText searchPlaceAroundMe;

    List<Bitmap> bitmapPinList = new ArrayList<>();
    List<LatLng> latLngList = new ArrayList<>();

    UserInfoManager userInfoManager;
    LatLng latLngDefault;

    String word;
    String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_in_map);

        word=getIntent().getStringExtra("word");
        catId=getIntent().getStringExtra("cat_id");
        Log.i("SHOWMAP", word+ " is wwoooord");
        Log.i("SHOWMAP", catId+ " is catttt");


        constraintPlaceAroundMe=findViewById(R.id.constraint_place_around_me);
        headerImagePlaceListImageView=findViewById(R.id.header_image_place_list);
        titlePlaceListTextView=findViewById(R.id.title_place_list);
        distancePlaceListTextView=findViewById(R.id.distance_place_list);
        addressPlaceListTextView=findViewById(R.id.address_place_list);
        visitCountPlaceListTextView=findViewById(R.id.visit_count_place_list);
        commentCountPlaceListTextView=findViewById(R.id.comment_count_place_list);
        favoriteCountPlaceListTextView=findViewById(R.id.favorite_count_place_list);
        placeItemBorderCardView=findViewById(R.id.place_item_border_card_view);
        rateImageView=findViewById(R.id.rate_image_view);

        voiceSearchCatFr=findViewById(R.id.voice_search_cat_fr);


        aniZoomIn = AnimationUtils.loadAnimation(ShowInMapActivity.this, R.anim.zoom_in_two);
        aniZoomOut = AnimationUtils.loadAnimation(ShowInMapActivity.this, R.anim.zoom_out_two);

        userInfoManager = new UserInfoManager(ShowInMapActivity.this);

        mapView = findViewById(R.id.map_view);
        searchPlaceAroundMe = findViewById(R.id.search_place_around_me);
        mapView.onCreate(savedInstanceState);
        latLngDefault = new LatLng();
        latLngDefault.setLatitude(Double.parseDouble(userInfoManager.getLat()));
        latLngDefault.setLongitude(Double.parseDouble(userInfoManager.getLon()));


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                map = mapboxMap;
                map.setStyle(new Style.Builder().fromUri(MapirStyle.LIGHT), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        zoomToSpecificLocationDefault(latLngDefault);
                        getPlaceData(word);

                    }
                });
            }
        });

        searchPlaceAroundMe.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getPlaceData(searchPlaceAroundMe.getText().toString());

                    return true;
                }
                return false;
            }
        });


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(ShowInMapActivity.this);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        //extra test
        speechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        //end

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                performingSpeechSetup = false;
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                if (i == SpeechRecognizer.ERROR_NO_MATCH) {
                    CookieBar.Builder c = CookieBar.build(ShowInMapActivity.this);
                    c.setTitle(" خطا در شناسایی کلمات - لطفا دوباره امتحان کنید");
                    c.setSwipeToDismiss(true);
                    c.setDuration(3500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    voiceSearchCatFr.callOnClick();
                } else {
                    return;
                }
                voiceSearchCatFr.setImageResource(R.drawable.searchwithvoice);
            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String word = data.get(0);
                searchPlaceAroundMe.setText(word);
                getPlaceData(word);
                voiceSearchCatFr.setImageResource(R.drawable.searchwithvoice);
                voiceSearchCatFr.callOnClick();

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        voiceSearchCatFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ShowInMapActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                }else if (voiceClicked) {
                    performingSpeechSetup = false;
                    voiceSearchCatFr.setImageResource(R.drawable.searchwithvoice);
                    speechRecognizer.stopListening();
                    voiceClicked = false;

                } else {
                    voiceClicked = true;
                    voiceSearchCatFr.setImageResource(R.drawable.recordvoice);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openVoiceSearch();
                        }
                    }, 300);

                }

            }
        });
    }

    private void addSymbolToMap(LatLng samplePoint, Bitmap bitmap, final PlacesItem placesItem) {
        String imageId = "sample_image_id" + samplePoint.getLatitude() + "@" + samplePoint.getLongitude();
        mapStyle.addImage(imageId, bitmap);
        // create symbol manager object
        SymbolManager sampleSymbolManager = new SymbolManager(mapView, map, mapStyle);
        sampleSymbolManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public void onAnnotationClick(Symbol symbol) {

                constraintPlaceAroundMe.setVisibility(View.GONE);
                constraintPlaceAroundMe.startAnimation(aniZoomOut);

                constraintPlaceAroundMe.setVisibility(View.VISIBLE);
                constraintPlaceAroundMe.startAnimation(aniZoomIn);


                Picasso.with(ShowInMapActivity.this).load(placesItem.getHeaderImage()).fit().into(headerImagePlaceListImageView);
                titlePlaceListTextView.setText(placesItem.getTitle());
                double distance=placesItem.getDistance();
                String distanceText="";
                if(distance>=1000){
                    double dm=distance/1000;
                    int d=(int)dm;
                    distanceText=d+" کیلومتری شما";
                }else {
                    int d=(int)distance;
                    distanceText=d+" متری شما";
                }
                distancePlaceListTextView.setText(distanceText);

                Log.i("FACILI","adapter list : "+placesItemList.toString());

                addressPlaceListTextView.setText(placesItem.getAddress());
                visitCountPlaceListTextView.setText(String.valueOf(placesItem.getVisitCount()));
                commentCountPlaceListTextView.setText(String.valueOf(placesItem.getCommentsCount()));
                favoriteCountPlaceListTextView.setText(String.valueOf(placesItem.getFavoriteCount()));

                int rondRate=(int)placesItem.getRate();
                if(rondRate==0){
                    rateImageView.setImageResource(R.drawable.rate0);
                }
                if(rondRate==1){
                    rateImageView.setImageResource(R.drawable.rate1);
                }
                if(rondRate==2){
                    rateImageView.setImageResource(R.drawable.rate2);
                }
                if(rondRate==3){
                    rateImageView.setImageResource(R.drawable.rate3);
                }
                if(rondRate==4){
                    rateImageView.setImageResource(R.drawable.rate4);
                }
                if(rondRate==5){
                    rateImageView.setImageResource(R.drawable.rate5);
                }

                final String finalDistanceText = distanceText;
                placeItemBorderCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ShowInMapActivity.this, PlaceActivity.class);
                        intent.putExtra("idPlace",placesItem.getId());
                        intent.putExtra("distance", finalDistanceText);
                        startActivity(intent);
                    }
                });
                //Toast.makeText(ShowInMapActivity.this, "This is CLICK_EVENT"+placesItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {
                //Toast.makeText(ShowInMapActivity.this, "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
        // set non-data-driven properties, such as:
        sampleSymbolManager.setIconAllowOverlap(true);
        sampleSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
        // Add symbol at specified lat/lon
        SymbolOptions sampleSymbolOptions = new SymbolOptions();
        sampleSymbolOptions.withLatLng(samplePoint);
        sampleSymbolOptions.withIconImage(imageId);

        //
        int valueInPixels = (int) getResources().getDimension(R.dimen._100sdp);

        float textTabSize= (float) (valueInPixels/540f);


        sampleSymbolOptions.withIconSize(textTabSize);
        // save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }

    private void zoomToSpecificLocation(LatLng samplePoint) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, 12));
    }

    public void getPlaceData(final String word) {
        final StringRequest placeReq = new StringRequest(Request.Method.POST, KavinooLinks.SEARCH_PLACE, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(final String response) {


                if(word.equals("")){
                    Log.i("QQWWX", "in ifff");

                    placesItemList.clear();

                    Gson gson = new Gson();

                    PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                    placesItemList = placesResponse.getPlaces();

                    new ShowInMapActivity.MyTask().execute(placesItemList);

                }else{
                    Log.i("QQWWX", "in elseeeeeeeeeeeeeee");

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {
                            map = mapboxMap;
                            map.setStyle(new Style.Builder().fromUri(MapirStyle.LIGHT), new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    mapStyle = style;
                                    placesItemList.clear();

                                    Gson gson = new Gson();

                                    PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                                    placesItemList = placesResponse.getPlaces();
                                    Log.i("QQWWX", placesResponse.toString());

                                    new ShowInMapActivity.MyTask().execute(placesItemList);
                                }
                            });
                        }
                    });
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("word", word);
                params.put("lat", userInfoManager.getLat());
                params.put("lon", userInfoManager.getLon());
                if (catId.equals("notset")){

                }else{
                    params.put("category", catId);
                }
                return params;
            }
        };

        placeReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(placeReq, "AROUNDREQ");

    }



    private class MyTask extends AsyncTask<List<PlacesItem>, Void, List<LatLng>> {


        @Override
        protected void onPostExecute(List<LatLng> latLngs) {
            super.onPostExecute(latLngs);

            new ShowInMapActivity.MyTaskBitmap().execute(placesItemList);

        }

        @Override
        protected List<LatLng> doInBackground(List<PlacesItem>... lists) {

            for (int i = 0; i < lists[0].size(); i++) {

                LatLng samplePoint = new LatLng();

                samplePoint.setLatitude(lists[0].get(i).getLatitude());
                samplePoint.setLongitude(lists[0].get(i).getLongitude());

                latLngList.add(i, samplePoint);

            }

            return latLngList;
        }
    }

    private class MyTaskBitmap extends AsyncTask<List<PlacesItem>, Void, List<Bitmap>> {


        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);


            try {
                for (int i = 0; i < bitmaps.size(); i++) {

                    addSymbolToMap(latLngList.get(i), bitmaps.get(i), placesItemList.get(i));

                }
                zoomToSpecificLocation(latLngList.get(0));
            }catch (Exception e){

            }
        }

        @Override
        protected List<Bitmap> doInBackground(List<PlacesItem>... lists) {

            List<Bitmap> bitmaps = new ArrayList<>();

            ArrayList<String> paths = new ArrayList<>();

            for (int i = 0; i < lists[0].size(); i++) {
                try {
                    String pinPath="";
                    if(lists[0].get(i).getCategory().getPinImage() == null){
                        pinPath = lists[0].get(i).getCategory().getParent().getPinImage();
                    }else{
                        pinPath = lists[0].get(i).getCategory().getPinImage();
                    }

                    paths.add(pinPath);
                    FutureTarget<Bitmap> futureBitmap = Glide.with(ShowInMapActivity.this)
                            .asBitmap()
                            .load(pinPath)
                            .submit();
                    Bitmap bitmapPin;
                    Bitmap myBitmap = futureBitmap.get();
                    bitmapPin = Bitmap.createBitmap(myBitmap);
                    bitmaps.add(i, bitmapPin);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            return bitmaps;
        }
    }



    public void openVoiceSearch() {

        performingSpeechSetup = true;

        speechRecognizer.startListening(speechRecognizerIntent);

    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        speechRecognizer.destroy();

    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        speechRecognizer.destroy();

    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

    }

    private void zoomToSpecificLocationDefault(LatLng samplePoint) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, 12));
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(ShowInMapActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }
    

}