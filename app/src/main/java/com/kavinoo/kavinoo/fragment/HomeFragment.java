package com.kavinoo.kavinoo.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.DiscountActivity;
import com.kavinoo.kavinoo.activity.MenuActivity;
import com.kavinoo.kavinoo.activity.OfferActivity;
import com.kavinoo.kavinoo.activity.PlacesWithCategoryActivity;
import com.kavinoo.kavinoo.activity.ProfileActivity;
import com.kavinoo.kavinoo.activity.RecreationActivity;
import com.kavinoo.kavinoo.activity.SearchPlaceActivity;
import com.kavinoo.kavinoo.localdata.adapter.CategoryAdapterHome;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.viewmodel.CategoryViewModel;
import com.kavinoo.kavinoo.onlinedata.adapter.SliderAdapter;
import com.kavinoo.kavinoo.onlinedata.model.slider.SlidesResponse;
import com.kavinoo.kavinoo.onlinedata.viewmodel.SliderViewModel;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private SliderViewModel sliderViewModel;
    SliderAdapter sliderAdapter;
    ViewPager2 viewPagerSlider;
    private Handler sliderHandler=new Handler();

    ArrayList<CategoriesItem> categoriesItemArrayList;
    RecyclerView recyclerHomeCategory;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CategoryViewModel categoryViewModelHome;

    TextView searchPlaceHomeTextView;
    MaterialCardView materialCardViewHomeSearch;

    ImageView recreation;
    ImageView offer;
    ImageView discount;

    CardView profileImage;
    CardView menu;

    ImageView voiceSearchPlacesCatActivty;

    //start voice

    public static final Integer RecordAudioRequestCode = 1;

    private SpeechRecognizer speechRecognizer;

    Intent speechRecognizerIntent;

    boolean performingSpeechSetup;

    boolean voiceClicked = false;

    //end voice

    public HomeFragment(String title) {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchPlaceHomeTextView=view.findViewById(R.id.search_place_home_text_view);
        materialCardViewHomeSearch=view.findViewById(R.id.material_cardView_home_search);
        recreation=view.findViewById(R.id.recreation);
        offer=view.findViewById(R.id.offer);
        discount=view.findViewById(R.id.discount);
        profileImage=view.findViewById(R.id.profile_image);
        menu=view.findViewById(R.id.menu);
        voiceSearchPlacesCatActivty=view.findViewById(R.id.voice_search_places_cat_activty);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        searchPlaceHomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), SearchPlaceActivity.class);
                intent.putExtra("voice_word","");
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),materialCardViewHomeSearch,ViewCompat.getTransitionName(materialCardViewHomeSearch));
                startActivity(intent,optionsCompat.toBundle());
            }
        });


        viewPagerSlider=view.findViewById(R.id.view_pager_slider);
        recyclerHomeCategory=view.findViewById(R.id.recycler_home_category);

        sliderViewModel = new ViewModelProviders().of(this).get(SliderViewModel.class);

        String sliderUrlReq="https://kavinoo.com/api/slides";
        final StringRequest sliderDetailsReq=new StringRequest(Request.Method.GET,sliderUrlReq, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                SlidesResponse slidesResponse = gson.fromJson(response, SlidesResponse.class);

                sliderAdapter=new SliderAdapter(slidesResponse.getSlider(),viewPagerSlider,getContext());
                viewPagerSlider.setAdapter(sliderAdapter);
                viewPagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sliderHandler.removeCallbacks(slideRunnable);
                        sliderHandler.postDelayed(slideRunnable,5000);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        sliderDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(sliderDetailsReq, "SLIDERREQ");


        /*sliderViewModel.getSliderListLData("https://kavinoo.com/api/slides").observe(this, new Observer<SlidesResponse>() {
            @Override
            public void onChanged(SlidesResponse slidesResponse) {

                sliderAdapter=new SliderAdapter(slidesResponse.getSlider(),viewPagerSlider,getContext());
                viewPagerSlider.setAdapter(sliderAdapter);
                viewPagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sliderHandler.removeCallbacks(slideRunnable);
                        sliderHandler.postDelayed(slideRunnable,5000);
                    }
                });
            }
        });
*/
        categoryViewModelHome= new ViewModelProviders().of(this).get(CategoryViewModel.class);

        categoryViewModelHome.all().observe(this, new Observer<List<CategoriesItem>>() {
            @Override
            public void onChanged(List<CategoriesItem> categoryModels) {
                ArrayList<CategoriesItem> categoryModelArrayListConvert=new ArrayList<>();
                for(int i=0;i<categoryModels.size();i++){
                    if(categoryModels.get(i).getParentId()==0 && categoryModelArrayListConvert.size()<=6){
                        categoryModelArrayListConvert.add(categoryModels.get(i));
                    }
                }

                Log.i("couuuuuuuuuu",categoryModelArrayListConvert.size()+" issssss");

                adapter=new CategoryAdapterHome(categoryModelArrayListConvert,getActivity().getApplicationContext());
                recyclerHomeCategory.setAdapter(adapter);
                layoutManager=new GridLayoutManager(getActivity().getApplicationContext(),4);
                recyclerHomeCategory.setLayoutManager(layoutManager);
                recyclerHomeCategory.setHasFixedSize(true);
            }
        });

        recreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), RecreationActivity.class);
                startActivity(intent);
            }
        });
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), OfferActivity.class);
                startActivity(intent);
            }
        });
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), DiscountActivity.class);
                startActivity(intent);
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
                    voiceSearchPlacesCatActivty.callOnClick();
                } else {
                    return;
                }
                voiceSearchPlacesCatActivty.setImageResource(R.drawable.searchwithvoice);
            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                Intent intent=new Intent(getActivity().getApplicationContext(), SearchPlaceActivity.class);
                intent.putExtra("voice_word",data.get(0));
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),materialCardViewHomeSearch,ViewCompat.getTransitionName(materialCardViewHomeSearch));
                startActivity(intent,optionsCompat.toBundle());

                /*word = data.get(0);
                searchPlaceWithCategory.setText(data.get(0));
                getPlaceData();*/

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

        voiceSearchPlacesCatActivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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


    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    public void openVoiceSearch() {

        performingSpeechSetup = true;

        speechRecognizer.startListening(speechRecognizerIntent);

    }

    private Runnable slideRunnable=new Runnable() {
        @Override
        public void run() {
            viewPagerSlider.setCurrentItem(viewPagerSlider.getCurrentItem()+1);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        sliderHandler.removeCallbacks(slideRunnable);
        speechRecognizer.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        speechRecognizer.destroy();

    }
}