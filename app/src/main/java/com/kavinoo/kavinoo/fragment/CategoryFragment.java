package com.kavinoo.kavinoo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.MenuActivity;
import com.kavinoo.kavinoo.activity.ProfileActivity;
import com.kavinoo.kavinoo.activity.SearchPlaceActivity;
import com.kavinoo.kavinoo.localdata.adapter.CategoryAdapterMain;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.viewmodel.CategoryViewModel;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoryFragment extends Fragment{

    RecyclerView recyclerMainCategory;
    CategoryAdapterMain adapter;
    RecyclerView.LayoutManager layoutManager;
    CategoryViewModel categoryViewModelMain;
    ArrayList<CategoriesItem> categoryModelArrayListConvert=new ArrayList<>();

    AppCompatEditText categorySearchEditText;

    CategoryAdapterMain.OnClickCategoryMain onClickCategoryMain;

    //start voice
    
    ImageView voiceSearchCatFr;

    public static final Integer RecordAudioRequestCode = 101;

    private SpeechRecognizer speechRecognizer;

    Intent speechRecognizerIntent;

    boolean performingSpeechSetup;

    boolean voiceClicked = false;


    //end voice

    CardView profileImage;
    CardView menu;


    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(String title) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerMainCategory=view.findViewById(R.id.recycler_main_category);
        categorySearchEditText=view.findViewById(R.id.category_search_edit_text);
        voiceSearchCatFr=view.findViewById(R.id.voice_search_cat_fr);

        profileImage=view.findViewById(R.id.profile_image);
        menu=view.findViewById(R.id.menu);

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

        onClickCategoryMain=new CategoryAdapterMain.OnClickCategoryMain() {
            @Override
            public void onClickCategory() {
                categorySearchEditText.setText("");
            }
        };
        categoryViewModelMain= new ViewModelProviders().of(this).get(CategoryViewModel.class);

        categoryViewModelMain.mainCategories().observe(this, new Observer<List<CategoriesItem>>() {
            @Override
            public void onChanged(List<CategoriesItem> categoriesItems) {

                categoryModelArrayListConvert= (ArrayList<CategoriesItem>) categoriesItems;
                adapter=new CategoryAdapterMain(categoryModelArrayListConvert,getActivity().getApplicationContext(),onClickCategoryMain);
                recyclerMainCategory.setAdapter(adapter);
                layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerMainCategory.setLayoutManager(layoutManager);
                recyclerMainCategory.setHasFixedSize(true);
            }
        });

        categorySearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

        categorySearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

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
                categorySearchEditText.setText(word);
                filter(word);
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

    public void openVoiceSearch() {

        performingSpeechSetup = true;

        speechRecognizer.startListening(speechRecognizerIntent);

    }



    void filter(String text){
        List<CategoriesItem> temp = new ArrayList();
        for(CategoriesItem d: categoryModelArrayListConvert){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getCategoryTitle().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }

    @Override
    public void onPause() {
        super.onPause();
        speechRecognizer.destroy();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        speechRecognizer.destroy();
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }



}