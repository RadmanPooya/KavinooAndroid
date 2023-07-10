package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.adapter.FilterFacilitiesAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.PlacesListAdapter;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.facilitiesfilter.FacilitiesItem;
import com.kavinoo.kavinoo.onlinedata.model.facilitiesfilter.FacilitiesResponse;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mrapp.android.bottomsheet.BottomSheet;

public class SearchPlaceActivity extends AppCompatActivity implements FilterFacilitiesAdapter.ISelectFacility{

    private static final int REQUEST_CODE_RECORD_AUDIO = 20;

    RecyclerView recyclerPlaces;

    RecyclerView.Adapter adapterPlaces;

    RecyclerView.LayoutManager layoutManagerPlaces;

    MaterialCardView filterPlaceCategoryCardView;

    List<Integer> facilitiesIdList = new ArrayList<>();

    List<FacilitiesItem> facilitiesItemList;

    List<PlacesItem> placesItemList = new ArrayList<>();

    AppCompatEditText searchPlaceWithCategory;

    ShimmerFrameLayout shimmerPlaceList;

    ImageView voiceSearchPlacesCatActivty;

    //start voice

    public static final Integer RecordAudioRequestCode = 1;

    private SpeechRecognizer speechRecognizer;

    Intent speechRecognizerIntent;

    boolean performingSpeechSetup;

    boolean voiceClicked = false;

    //end voice

    UserInfoManager userInfoManager;

    CardView sortCardView;
    TextView sortTextView;

    List<String> sorting = new ArrayList<>();

    BottomSheet.Builder sortingBottomSheetBuilder;

    String sortIdSelected = "0";

    String word = "";

    MaterialCardView showInLocation;

    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;
    int lastPage=1;
    int allPagesCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        toolbarMain=findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPlaceActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPlaceActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        findViews();
        onClickViews();
        setData();
        getFacilities();

    }

    public static void showKeyboard(AppCompatEditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void setData() {

        searchPlaceWithCategory.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                showKeyboard(searchPlaceWithCategory,SearchPlaceActivity.this);
            }
        }, 1400);
        /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchPlaceWithCategory, InputMethodManager.SHOW_IMPLICIT);*/

        userInfoManager = new UserInfoManager(SearchPlaceActivity.this);

        sortingBottomSheetBuilder = new BottomSheet.Builder(SearchPlaceActivity.this, R.style.BottomSheet);
        sortingBottomSheetBuilder.setTitleColor(Color.parseColor("#367ab9"));
        //sortingBottomSheetBuilder.setBackgroundColor(Color.parseColor("#367ab9"));
        sortingBottomSheetBuilder.addItem(0, "نزدیک ترین");
        sortingBottomSheetBuilder.addItem(1, "بهترین");
        sortingBottomSheetBuilder.addItem(2, "بهترین و نزدیک ترین");
        sortIdSelected = "0";


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

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
                    CookieBar.Builder c = CookieBar.build(SearchPlaceActivity.this);
                    c.setTitle(" خطا در شناسایی کلمات - لطفا دوباره امتحان کنید");
                    c.setSwipeToDismiss(true);
                    c.setDuration(3500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    voiceSearchPlacesCatActivty.callOnClick();
                } else {
                    return;
                }
                voiceSearchPlacesCatActivty.setImageResource(R.drawable.searchwithvoice);
            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                word = data.get(0);
                searchPlaceWithCategory.setText(data.get(0));
                getPlaceData();
                voiceSearchPlacesCatActivty.setImageResource(R.drawable.searchwithvoice);
                voiceSearchPlacesCatActivty.callOnClick();

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    public void findViews() {
        shimmerPlaceList = findViewById(R.id.shimmer_place_list);
        recyclerPlaces = findViewById(R.id.recycler_places_with_category);
        filterPlaceCategoryCardView = findViewById(R.id.filter_place_category_card_view);
        searchPlaceWithCategory = findViewById(R.id.search_place_with_category);
        voiceSearchPlacesCatActivty = findViewById(R.id.voice_search_places_cat_activty);
        sortCardView = findViewById(R.id.sort_card_view);
        sortTextView = findViewById(R.id.sort_text_view);
        showInLocation=findViewById(R.id.show_in_location);

    }

    public void onClickViews() {
        filterPlaceCategoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
        showInLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPlaceActivity.this,ShowInMapActivity.class);
                intent.putExtra("word",word);
                intent.putExtra("cat_id","notset");
                startActivity(intent);
            }
        });
        searchPlaceWithCategory.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                word = searchPlaceWithCategory.getText().toString();

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("SSSS", searchPlaceWithCategory.getText().toString());

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    shimmerPlaceList.setVisibility(View.VISIBLE);
                    recyclerPlaces.setVisibility(View.GONE);
                    getPlaceData();

                    return true;
                }
                return false;
            }
        });

        voiceSearchPlacesCatActivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(SearchPlaceActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                }else if (voiceClicked) {
                    performingSpeechSetup = false;
                    voiceSearchPlacesCatActivty.setImageResource(R.drawable.searchwithvoice);
                    speechRecognizer.stopListening();
                    voiceClicked = false;

                } else {
                    voiceClicked = true;
                    voiceSearchPlacesCatActivty.setImageResource(R.drawable.recordvoice);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openVoiceSearch();
                        }
                    }, 300);


                }


            }
        });

        sortCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet categoryBottomSheet = sortingBottomSheetBuilder.create();
                categoryBottomSheet.show();
                sortingBottomSheetBuilder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (String.valueOf(id).equals("0")) {
                            sortTextView.setText("نزدیک ترین");
                            sortIdSelected = "0";
                        }
                        if (String.valueOf(id).equals("1")) {
                            sortTextView.setText("بهترین");
                            sortIdSelected = "1";
                        }
                        if (String.valueOf(id).equals("2")) {
                            sortTextView.setText("بهترین و نزدیک ترین");
                            sortIdSelected = "2";
                        }
                        getPlaceData();
                    }
                });
            }
        });

        recyclerPlaces.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    if(lastPage <=allPagesCount){
                        getPlaceDataTwo(String.valueOf(lastPage+1));
                    }

                }
            }
        });

    }

    public void openVoiceSearch() {

        performingSpeechSetup = true;

        speechRecognizer.startListening(speechRecognizerIntent);

    }


    public void showFilterDialog() {

        facilitiesIdList.clear();

        final Dialog dialog = new Dialog(SearchPlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RecyclerView filterCategoryRecyclerView;
        final RecyclerView.Adapter adapterfilter;
        RecyclerView.LayoutManager layoutManagerFilter;
        CardView setFilterCardView;

        filterCategoryRecyclerView = dialog.findViewById(R.id.filter_category_recycler_view);
        setFilterCardView = dialog.findViewById(R.id.set_filter_card_view);

        adapterfilter = new FilterFacilitiesAdapter(SearchPlaceActivity.this, facilitiesItemList, SearchPlaceActivity.this);
        filterCategoryRecyclerView.setAdapter(adapterfilter);
        layoutManagerFilter = new GridLayoutManager(SearchPlaceActivity.this, 2);
        filterCategoryRecyclerView.setLayoutManager(layoutManagerFilter);
        filterCategoryRecyclerView.setHasFixedSize(true);
        //Log.i("SEET", facilitiesItemList.toString());

        setFilterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaceData();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void getPlaceData() {
        final StringRequest placeReq = new StringRequest(Request.Method.POST, KavinooLinks.SEARCH_PLACE, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Log.i("FACILI", "place : " + placesItemList);

                getFacilities();

                Gson gson = new Gson();

                PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                placesItemList = placesResponse.getPlaces();

                //s
                allPagesCount = placesResponse.getMeta().getLastPage();
                //n

                adapterPlaces = new PlacesListAdapter(placesItemList, SearchPlaceActivity.this);
                recyclerPlaces.setAdapter(adapterPlaces);
                layoutManagerPlaces = new LinearLayoutManager(SearchPlaceActivity.this);
                recyclerPlaces.setLayoutManager(layoutManagerPlaces);
                recyclerPlaces.setHasFixedSize(true);
                adapterPlaces.notifyDataSetChanged();

                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                shimmerPlaceList.startAnimation(animFadeOut);
                shimmerPlaceList.setVisibility(View.GONE);
                recyclerPlaces.setVisibility(View.VISIBLE);
                recyclerPlaces.startAnimation(animFadeIn);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.i("FACILI", "response : " + error);


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
                if (sortIdSelected.equals("0")) {
                    params.put("sort", "distance");
                }
                if (sortIdSelected.equals("1")) {
                    params.put("sort", "rate");
                }
                if (sortIdSelected.equals("2")) {
                    params.put("sort", "distance");
                    params.put("bestRate", "true");
                }


                //JSONArray facilitiesFilter = new JSONArray();
                for (int i = 0; i < facilitiesIdList.size(); i++) {
                    params.put("facilities[]", String.valueOf(facilitiesIdList.get(i)));
                }


                return params;
            }
        };

        placeReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(placeReq, "SEARCHPLACEREQ");

    }

    public void getPlaceDataTwo(String pageNumber) {
        final StringRequest placeReq = new StringRequest(Request.Method.POST, KavinooLinks.SEARCH_PLACE+"?page="+pageNumber, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {

                lastPage++;

                getFacilities();

                Gson gson = new Gson();

                PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);

                placesItemList.addAll(placesResponse.getPlaces());
                adapterPlaces.notifyDataSetChanged();

                /*adapterPlaces = new PlacesListAdapter(placesItemList, SearchPlaceActivity.this);
                recyclerPlaces.setAdapter(adapterPlaces);
                layoutManagerPlaces = new LinearLayoutManager(SearchPlaceActivity.this);
                recyclerPlaces.setLayoutManager(layoutManagerPlaces);
                recyclerPlaces.setHasFixedSize(true);
                adapterPlaces.notifyDataSetChanged();

                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                shimmerPlaceList.startAnimation(animFadeOut);
                shimmerPlaceList.setVisibility(View.GONE);
                recyclerPlaces.setVisibility(View.VISIBLE);
                recyclerPlaces.startAnimation(animFadeIn);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.i("FACILI", "response : " + error);


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
                if (sortIdSelected.equals("0")) {
                    params.put("sort", "distance");
                }
                if (sortIdSelected.equals("1")) {
                    params.put("sort", "rate");
                }
                if (sortIdSelected.equals("2")) {
                    params.put("sort", "distance");
                    params.put("bestRate", "true");
                }


                //JSONArray facilitiesFilter = new JSONArray();
                for (int i = 0; i < facilitiesIdList.size(); i++) {
                    params.put("facilities[]", String.valueOf(facilitiesIdList.get(i)));
                }


                return params;
            }
        };

        placeReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(placeReq, "SEARCHPLACEREQ");

    }


    public void getFacilities() {
        String url = KavinooLinks.getFacilitiesLink;
        final StringRequest facilitiesReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                FacilitiesResponse facilitiesResponse = gson.fromJson(response, FacilitiesResponse.class);

                facilitiesItemList = facilitiesResponse.getFacilities();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("SEET", error.getMessage() + "   is errrrrrrrrrrrr");

            }
        });

        facilitiesReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(facilitiesReq, "FACILITIESREQ");

    }

    @Override
    public void onSelectFacility(int selectedFacilityId) {
        facilitiesIdList.add(selectedFacilityId);
        Log.i("FACILI", "select : " + selectedFacilityId);
    }

    @Override
    public void onUnSelectFacility(int unSelectedFacilityId) {
        facilitiesIdList.removeAll(Arrays.asList(unSelectedFacilityId));
        Log.i("FACILI", "un select : " + unSelectedFacilityId);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        speechRecognizer.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        speechRecognizer.destroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

}