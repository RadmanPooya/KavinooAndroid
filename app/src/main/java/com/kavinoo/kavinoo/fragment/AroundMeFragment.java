package com.kavinoo.kavinoo.fragment;

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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.kavinoo.kavinoo.activity.PlaceActivity;
import com.kavinoo.kavinoo.activity.SearchPlaceActivity;
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


public class AroundMeFragment extends Fragment {


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

    Context myContext;

    UserInfoManager userInfoManager;
    LatLng latLngDefault;

    CardView distanceCardView;

    TextView distanceSelectedTextView;
    String distanceSelected = "500";


    public AroundMeFragment() {

    }

    public AroundMeFragment(String title) {
        // Required empty public constructor
    }

    public AroundMeFragment(String title, Context context) {
        this.myContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_around_me, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintPlaceAroundMe = view.findViewById(R.id.constraint_place_around_me);
        headerImagePlaceListImageView = view.findViewById(R.id.header_image_place_list);
        titlePlaceListTextView = view.findViewById(R.id.title_place_list);
        distancePlaceListTextView = view.findViewById(R.id.distance_place_list);
        addressPlaceListTextView = view.findViewById(R.id.address_place_list);
        visitCountPlaceListTextView = view.findViewById(R.id.visit_count_place_list);
        commentCountPlaceListTextView = view.findViewById(R.id.comment_count_place_list);
        favoriteCountPlaceListTextView = view.findViewById(R.id.favorite_count_place_list);
        placeItemBorderCardView = view.findViewById(R.id.place_item_border_card_view);
        rateImageView = view.findViewById(R.id.rate_image_view);
        voiceSearchCatFr = view.findViewById(R.id.voice_search_cat_fr);
        distanceSelectedTextView = view.findViewById(R.id.distance_selected_text_view);
        distanceCardView = view.findViewById(R.id.distance_card_view);


        aniZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in_two);
        aniZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out_two);

        userInfoManager = new UserInfoManager(myContext);

        mapView = view.findViewById(R.id.map_view);
        searchPlaceAroundMe = view.findViewById(R.id.search_place_around_me);
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
                        getPlaceData("");

                    }
                });
            }
        });

        searchPlaceAroundMe.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    getPlaceData(searchPlaceAroundMe.getText().toString());

                    return true;
                }
                return false;
            }
        });


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());

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
                    CookieBar.Builder c = CookieBar.build(getActivity());
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

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                } else if (voiceClicked) {
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

        distanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDistanceDialog();
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


                Picasso.with(myContext).load(placesItem.getHeaderImage()).fit().into(headerImagePlaceListImageView);
                titlePlaceListTextView.setText(placesItem.getTitle());
                double distance = placesItem.getDistance();
                String distanceText = "";
                if (distance >= 1000) {
                    double dm = distance / 1000;
                    int d = (int) dm;
                    distanceText = d + " کیلومتری شما";
                } else {
                    int d = (int) distance;
                    distanceText = d + " متری شما";
                }
                distancePlaceListTextView.setText(distanceText);

                Log.i("FACILI", "adapter list : " + placesItemList.toString());

                addressPlaceListTextView.setText(placesItem.getAddress());
                visitCountPlaceListTextView.setText(String.valueOf(placesItem.getVisitCount()));
                commentCountPlaceListTextView.setText(String.valueOf(placesItem.getCommentsCount()));
                favoriteCountPlaceListTextView.setText(String.valueOf(placesItem.getFavoriteCount()));

                int rondRate = (int) placesItem.getRate();
                if (rondRate == 0) {
                    rateImageView.setImageResource(R.drawable.rate0);
                }
                if (rondRate == 1) {
                    rateImageView.setImageResource(R.drawable.rate1);
                }
                if (rondRate == 2) {
                    rateImageView.setImageResource(R.drawable.rate2);
                }
                if (rondRate == 3) {
                    rateImageView.setImageResource(R.drawable.rate3);
                }
                if (rondRate == 4) {
                    rateImageView.setImageResource(R.drawable.rate4);
                }
                if (rondRate == 5) {
                    rateImageView.setImageResource(R.drawable.rate5);
                }

                final String finalDistanceText = distanceText;
                placeItemBorderCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myContext, PlaceActivity.class);
                        intent.putExtra("idPlace", placesItem.getId());
                        intent.putExtra("distance", finalDistanceText);
                        myContext.startActivity(intent);
                    }
                });
                //Toast.makeText(getActivity().getApplicationContext(), "This is CLICK_EVENT"+placesItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {
                //Toast.makeText(getActivity().getApplicationContext(), "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
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
        int valueInPixels = (int) myContext.getResources().getDimension(R.dimen._100sdp);

        float textTabSize = (float) (valueInPixels / 540f);

        sampleSymbolOptions.withIconSize(textTabSize);
        // save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }

    private void zoomToSpecificLocation(LatLng samplePoint) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, 12));
    }

    private void zoomToSpecificLocationDefault(LatLng samplePoint) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, 12));
    }


    public void getPlaceData(final String word) {
        final StringRequest placeReq = new StringRequest(Request.Method.POST, KavinooLinks.SEARCH_PLACE_IN_MAP, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(final String response) {

                Log.i("SSSLLL",response.toString());


                if (word.equals("")) {
                    placesItemList.clear();

                    Gson gson = new Gson();

                    PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                    placesItemList = placesResponse.getPlaces();

                    new MyTask().execute(placesItemList);

                } else {
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {
                            map = mapboxMap;
                            map.setStyle(new Style.Builder().fromUri(MapirStyle.LIGHT), new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    mapStyle = style;
                                    placesItemList.clear();

                                    Log.i("QQWW", response.toString());

                                    Gson gson = new Gson();

                                    PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                                    placesItemList = placesResponse.getPlaces();

                                    new MyTask().execute(placesItemList);
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
                params.put("distance", distanceSelected);

                Log.i("SSSLLL",params.toString());
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

            new MyTaskBitmap().execute(placesItemList);


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
                    paths.add(lists[0].get(i).getCategory().getParent().getPinImage());
                    FutureTarget<Bitmap> futureBitmap = Glide.with(myContext)
                            .asBitmap()
                            .load(lists[0].get(i).getCategory().getParent().getPinImage())
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }


    public void showDistanceDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.distance_places_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CardView distanceValue100;
        CardView distanceValue200;
        CardView distanceValue500;
        CardView distanceValue800;
        CardView distanceValue1000;
        CardView distanceValue2000;
        CardView distanceValue5000;
        CardView distanceValue8000;
        CardView distanceValue10000;
        CardView distanceValue20000;

        distanceValue100 = dialog.findViewById(R.id.distance_value_100);
        distanceValue200 = dialog.findViewById(R.id.distance_value_200);
        distanceValue500 = dialog.findViewById(R.id.distance_value_500);
        distanceValue800 = dialog.findViewById(R.id.distance_value_800);
        distanceValue1000 = dialog.findViewById(R.id.distance_value_1000);
        distanceValue2000 = dialog.findViewById(R.id.distance_value_2000);
        distanceValue5000 = dialog.findViewById(R.id.distance_value_5000);
        distanceValue8000 = dialog.findViewById(R.id.distance_value_8000);
        distanceValue10000 = dialog.findViewById(R.id.distance_value_10000);
        distanceValue20000 = dialog.findViewById(R.id.distance_value_20000);

        distanceValue100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("100 متر");
                distanceSelected = "100";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("200 متر");
                distanceSelected = "200";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("500 متر");
                distanceSelected = "500";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("800 متر");
                distanceSelected = "800";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("1 کیلومتر");
                distanceSelected = "1000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("2 کیلومتر");
                distanceSelected = "2000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("5 کیلومتر");
                distanceSelected = "5000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue8000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("8 کیلومتر");
                distanceSelected = "8000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("10 کیلومتر");
                distanceSelected = "10000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });
        distanceValue20000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceSelectedTextView.setText("20 کیلومتر");
                distanceSelected = "20000";
                getPlaceData(searchPlaceAroundMe.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }
}