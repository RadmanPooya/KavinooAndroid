package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.adapter.CategoryAdapterSecond;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesResponse;
import com.kavinoo.kavinoo.localdata.viewmodel.CategoryViewModel;
import com.squareup.picasso.Picasso;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.bottomsheet.BottomSheet;

public class SecondaryCategoryActivity extends AppCompatActivity {

    String categoryUrlReq;


    CategoryViewModel secondCategoryViewModel;

    RecyclerView recyclerMainSecCategory;
    CategoryAdapterSecond adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CategoriesItem> categoryModelArrayListConvert = new ArrayList<>();

    AppCompatEditText searchSecondaryCategoryEditText;
    String catTitle;
    int catId;
    String catImage;
    int parentId;
    String description;
    boolean hasChild;

    ImageView categoryItemSecondImageView;
    TextView categoryItemSecondTextView;


    ImageView voiceSearchSecCat;
    
    public static final Integer RecordAudioRequestCode = 1;

    private SpeechRecognizer speechRecognizer;

    Intent speechRecognizerIntent;

    boolean performingSpeechSetup;

    boolean voiceClicked = false;



    CategoryAdapterSecond.OnClickSecondaryCategory onClickSecondaryCategory;


    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_category);
        recyclerMainSecCategory = findViewById(R.id.recycler_main_sec_category);

        searchSecondaryCategoryEditText = findViewById(R.id.search_secondary_category_edit_text);

        categoryItemSecondImageView=findViewById(R.id.category_item_second_image_view);
        categoryItemSecondTextView=findViewById(R.id.category_item_second_text_view);
        voiceSearchSecCat=findViewById(R.id.voice_search_sec_cat);

        toolbarMain=findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondaryCategoryActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondaryCategoryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


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
                    CookieBar.Builder c = CookieBar.build(SecondaryCategoryActivity.this);
                    c.setTitle(" خطا در شناسایی کلمات - لطفا دوباره امتحان کنید");
                    c.setSwipeToDismiss(true);
                    c.setDuration(3500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    voiceSearchSecCat.callOnClick();
                } else {
                    return;
                }
                voiceSearchSecCat.setImageResource(R.drawable.searchwithvoice);
            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String word = data.get(0);
                searchSecondaryCategoryEditText.setText(word);
                filter(word);
                voiceSearchSecCat.setImageResource(R.drawable.searchwithvoice);
                voiceSearchSecCat.callOnClick();

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        voiceSearchSecCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(SecondaryCategoryActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                }else if (voiceClicked) {
                    performingSpeechSetup = false;
                    voiceSearchSecCat.setImageResource(R.drawable.searchwithvoice);
                    speechRecognizer.stopListening();
                    voiceClicked = false;

                } else {
                    voiceClicked = true;
                    voiceSearchSecCat.setImageResource(R.drawable.recordvoice);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openVoiceSearch();
                        }
                    }, 300);


                }


            }
        });

        onClickSecondaryCategory=new CategoryAdapterSecond.OnClickSecondaryCategory() {
            @Override
            public void onClickSecondaryCategory() {
                searchSecondaryCategoryEditText.setText("");
            }
        };
        searchSecondaryCategoryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) SecondaryCategoryActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SecondaryCategoryActivity.this.getCurrentFocus().getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });


        secondCategoryViewModel = new ViewModelProviders().of(this).get(CategoryViewModel.class);

        Intent intent = getIntent();

        catId = intent.getIntExtra("category_id", 0);
        catImage = intent.getStringExtra("category_image");
        catTitle = intent.getStringExtra("category_title");
        parentId = intent.getIntExtra("parent_id",0);
        description = intent.getStringExtra("description");
        hasChild = intent.getBooleanExtra("has_child",false);

        Picasso.with(SecondaryCategoryActivity.this).load(catImage).fit().into(categoryItemSecondImageView);
        categoryItemSecondTextView.setText(catTitle);


        searchSecondaryCategoryEditText.setHint("جستجو در دسته بندی " + catTitle + " ...");

        categoryUrlReq = "https://kavinoo.com/api/categories/" + catId;

        final StringRequest categoryDetailsReq = new StringRequest(Request.Method.GET, categoryUrlReq, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Gson gson = new Gson();
                CategoriesResponse categoriesResponse = gson.fromJson(response, CategoriesResponse.class);

                List<CategoriesItem> categoriesItems = categoriesResponse.getCategories();
                secondCategoryViewModel.insert(categoriesItems);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        categoryDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(categoryDetailsReq, "CATEGORYSECREQ");

        secondCategoryViewModel = new ViewModelProviders().of(this).get(CategoryViewModel.class);

        secondCategoryViewModel.catWithParentId(String.valueOf(catId)).observe(this, new Observer<List<CategoriesItem>>() {
            @Override
            public void onChanged(List<CategoriesItem> categoriesItems) {

                categoryModelArrayListConvert = (ArrayList<CategoriesItem>) categoriesItems;
                adapter = new CategoryAdapterSecond(categoryModelArrayListConvert, SecondaryCategoryActivity.this,onClickSecondaryCategory);
                recyclerMainSecCategory.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(SecondaryCategoryActivity.this);
                recyclerMainSecCategory.setLayoutManager(layoutManager);
                recyclerMainSecCategory.setHasFixedSize(true);
            }
        });

        searchSecondaryCategoryEditText.addTextChangedListener(new TextWatcher() {
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



    public void openVoiceSearch() {

        performingSpeechSetup = true;

        speechRecognizer.startListening(speechRecognizerIntent);

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