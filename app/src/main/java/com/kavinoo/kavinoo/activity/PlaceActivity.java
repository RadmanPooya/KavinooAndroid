package com.kavinoo.kavinoo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.viewmodel.PlaceDetailsViewModel;
import com.kavinoo.kavinoo.onlinedata.adapter.CommentsAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.DigitalMenuAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.FacilitiesAdapter;
import com.kavinoo.kavinoo.onlinedata.adapter.PlaceImageSliderAdapter;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.addassessment.AddAssessmentModel;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.AssessmentsItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.DigitalMenusItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.FacilitiesItem;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.PlaceDetails;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.PlaceImageSliderModel;
import com.kavinoo.kavinoo.onlinedata.model.responseelan.ElanResponse;
import com.kavinoo.kavinoo.utils.DialogWithVolume;
import com.kavinoo.kavinoo.utils.NullStringToEmptyAdapterFactory;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.kavinoo.kavinoo.utils.UserInfoManager;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.aviran.cookiebar2.CookieBar;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PlaceActivity extends AppCompatActivity {

    int idPlace = 0;
    String distance = "0";

    TextView placeTitleMain;
    TextView distancePlaceDetails;
    TextView visitCountPlaceDetails;
    TextView commentCountPlaceDetails;
    TextView favoriteCountPlaceDetails;
    SliderView placeImageSlider;
    ImageView mapCrk;
    TextView address;

    ImageView website;
    ImageView whatsappId;
    ImageView instagramId;
    ImageView phoneNumbers;

    RecyclerView commentRecyclerView;
    RecyclerView.Adapter adapterComments;
    RecyclerView.LayoutManager layoutManagerComments;

    List<FacilitiesItem> facilitiesPlace = new ArrayList<>();
    List<DigitalMenusItem> digitalMenusPlace = new ArrayList<>();

    ImageView facilitiesPlaceImageView;
    ImageView digitalMenuPlaceImageView;

    PlaceDetails placeDetailsInActivity;
    com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails placeDetailsLocal;

    //player variables

    PlayerView playerView;
    ProgressBar progressBar;
    CardView fullScreenCardView;
    SimpleExoPlayer simpleExoPlayer;

    String globalVideoPath;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    DefaultHttpDataSourceFactory factory;

    //

    ImageView tizerImageView;

    //add comment
    ImageView addComment;

    // rate
    CardView addRateImageView;
    public static final String BOUNDARY = "ANY_STRING";

    //share
    CardView sharePlace;
    //deep link
    boolean isFromDeepLink = false;
    String body = "";
    //routong
    ImageView routing;
    //
    ImageView internetTaxi;

    //favorite
    CardView favoriteplace;
    PlaceDetailsViewModel placeDetailsViewModel;
    //panorama
    ImageView panorama;
    //elan
    ImageView elanPlace;
    //rate show
    ImageView rateImageView;
    TextView rateNumber;
    //no cimment
    TextView noCommentTextView;

    UserInfoManager userInfoManager;
    //loading
    ProgressBar spinKitLoadingPlace;
    ConstraintLayout loadingConstraint;

    Animation aniZoomOut;

    //report
    CardView reportPlace;

    //slogan
    ConstraintLayout sloganConstraint;
    TextView sloganText;

    //description
    CardView descriptionCard;
    TextView descriptionText;
    TextView descriptionMore;

    //assessment

    ConstraintLayout assessmentConstraint;

    CircularProgressBar assessment1Progress;
    CircularProgressBar assessment2Progress;
    CircularProgressBar assessment3Progress;
    CircularProgressBar assessment4Progress;

    TextView assessment1Text;
    TextView assessment2Text;
    TextView assessment3Text;
    TextView assessment4Text;

    CardView assessment1Card;
    CardView assessment2Card;
    CardView assessment3Card;
    CardView assessment4Card;

    LinearLayout assessment1Linear;
    LinearLayout assessment2Linear;
    LinearLayout assessment3Linear;
    LinearLayout assessment4Linear;

    CardView addAssessmentCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


        userInfoManager = new UserInfoManager(PlaceActivity.this);

        findViews();
        setClicks();

        if (getIntent().getAction() != null) {
            Intent intent = getIntent();
            Uri data = intent.getData();
            String id = data.getQueryParameter("place");
            idPlace = Integer.parseInt(id);
            isFromDeepLink = true;

        } else {
            idPlace = getIntent().getIntExtra("idPlace", 0);
            distance = getIntent().getStringExtra("distance");

        }

        placeDetailsViewModel = new ViewModelProviders().of(this).get(PlaceDetailsViewModel.class);

        getPlaceDetails(String.valueOf(idPlace));


    }



    public void findViews() {
        spinKitLoadingPlace = findViewById(R.id.spin_kit_loading_place);
        placeTitleMain = findViewById(R.id.place_title_main);
        distancePlaceDetails = findViewById(R.id.distance_place_details);
        visitCountPlaceDetails = findViewById(R.id.visit_count_place_details);
        commentCountPlaceDetails = findViewById(R.id.comment_count_place_details);
        favoriteCountPlaceDetails = findViewById(R.id.favorite_count_place_details);
        placeImageSlider = findViewById(R.id.place_image_slider);
        mapCrk = findViewById(R.id.map_crk);
        instagramId = findViewById(R.id.instagram_id);
        whatsappId = findViewById(R.id.whatsapp_id);
        website = findViewById(R.id.website);
        commentRecyclerView = findViewById(R.id.comment_recycler_view);
        facilitiesPlaceImageView = findViewById(R.id.facilities_place_image_view);
        digitalMenuPlaceImageView = findViewById(R.id.digital_menu_place_image_view);
        phoneNumbers = findViewById(R.id.phone_numbers);
        tizerImageView = findViewById(R.id.tizer_image_view);
        addComment = findViewById(R.id.add_comment);
        addRateImageView = findViewById(R.id.add_rate_image_view);
        rateNumber = findViewById(R.id.rate_number);
        sharePlace = findViewById(R.id.share_place);
        routing = findViewById(R.id.routing);
        favoriteplace = findViewById(R.id.favorite_place);
        panorama = findViewById(R.id.panorama);
        elanPlace = findViewById(R.id.elan_place);
        rateImageView = findViewById(R.id.rate_image_view);
        internetTaxi = findViewById(R.id.internet_taxi);
        address = findViewById(R.id.address);
        noCommentTextView = findViewById(R.id.no_comment_text_view);
        loadingConstraint = findViewById(R.id.loading_constraint);
        reportPlace = findViewById(R.id.report_place);
        sloganConstraint = findViewById(R.id.slogan_constraint);
        sloganText = findViewById(R.id.slogan_text);
        descriptionCard = findViewById(R.id.description_card);
        descriptionText = findViewById(R.id.description_text);
        descriptionMore = findViewById(R.id.description_more);
        assessment1Progress = findViewById(R.id.assessment1_progress);
        assessment2Progress = findViewById(R.id.assessment2_progress);
        assessment3Progress = findViewById(R.id.assessment3_progress);
        assessment4Progress = findViewById(R.id.assessment4_progress);
        assessment1Text = findViewById(R.id.assessment1_text);
        assessment2Text = findViewById(R.id.assessment2_text);
        assessment3Text = findViewById(R.id.assessment3_text);
        assessment4Text = findViewById(R.id.assessment4_text);
        assessment1Card = findViewById(R.id.assessment1_card);
        assessment2Card = findViewById(R.id.assessment2_card);
        assessment3Card = findViewById(R.id.assessment3_card);
        assessment4Card = findViewById(R.id.assessment4_card);
        assessment1Linear = findViewById(R.id.assessment1_linear);
        assessment2Linear = findViewById(R.id.assessment2_linear);
        assessment3Linear = findViewById(R.id.assessment3_linear);
        assessment4Linear = findViewById(R.id.assessment4_linear);
        assessmentConstraint = findViewById(R.id.assessment_constraint);
        addAssessmentCard = findViewById(R.id.add_assessment_card);


    }

    public void setClicks() {

        addAssessmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAssessmentDialog(placeDetailsInActivity.getAssessments(),placeDetailsLocal.getId());
            }
        });

        reportPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReportPlace();
            }
        });

        descriptionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptionMore.getText().toString().matches("مشاهده بیشتر")) {
                    descriptionText.setMaxLines(30);
                    descriptionMore.setText("بستن");
                } else {
                    descriptionText.setMaxLines(3);
                    descriptionMore.setText("مشاهده بیشتر");
                }

            }
        });

        MultiplePulseRing multiplePulseRing = new MultiplePulseRing();
        spinKitLoadingPlace.setIndeterminateDrawable(multiplePulseRing);

        aniZoomOut = AnimationUtils.loadAnimation(PlaceActivity.this, R.anim.zoom_out_loading);


        instagramId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeDetailsInActivity.getInstagramId().equals("")){

                    CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                    c.setTitle("برای این مکان صفحه اینستاگرام ثبت نشده است.");
                    c.setSwipeToDismiss(true);
                    c.setDuration(2500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                }else{


                    Uri uri = Uri.parse("https://instagram.com/_u/" + placeDetailsInActivity.getInstagramId());
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://instagram.com/" + placeDetailsInActivity.getInstagramId())));
                    }
                }
            }
        });

        whatsappId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeDetailsInActivity.getTelegramId().equals("")){
                    CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                    c.setTitle("برای این مکان شماره واتس اپ ثبت نشده است.");
                    c.setSwipeToDismiss(true);
                    c.setDuration(2500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }else{
                    String number = placeDetailsInActivity.getTelegramId().replaceFirst("^0+(?!$)", "");
                    number = "+98" + number;
                    String url = "https://api.whatsapp.com/send?phone=" + number;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeDetailsInActivity.getWebsite().equals("")){
                    CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                    c.setTitle("برای این مکان وب سایت ثبت نشده است.");
                    c.setSwipeToDismiss(true);
                    c.setDuration(2500);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }else{
                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sharingIntent.setData(Uri.parse(placeDetailsInActivity.getWebsite()));
                    Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chooserIntent);
                }
            }
        });

        phoneNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneNumberDialog();
            }
        });

        facilitiesPlaceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFacilitiesDialog(facilitiesPlace);
            }
        });
        digitalMenuPlaceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDigitalMenuDialog(digitalMenusPlace);
            }
        });
        tizerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTizerDialog(globalVideoPath);
            }
        });
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        addRateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating();
            }
        });
        sharePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, placeTitleMain.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT, "https://kavinoo.com?place=" + String.valueOf(idPlace));
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });

        routing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", placeDetailsInActivity.getLatitude(), placeDetailsInActivity.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "نقشه مورد نظر برای مسیریابی"));

            }
        });

        internetTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", placeDetailsInActivity.getLatitude(), placeDetailsInActivity.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "انتخاب تاکسی اینترنتی مورد نظر"));
            }
        });

        favoriteplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeDetailsViewModel.insert(placeDetailsLocal);
                CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                c.setTitle(" به علاقه مندی ها اضافه شد در صفحه علاقه مندی ها میتوانید مشاهده کنید");
                c.setSwipeToDismiss(true);
                c.setDuration(3500);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        panorama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                c.setTitle("برای مکان مورد نظر تصویر 360 درجه وجود ندارد");
                c.setSwipeToDismiss(true);
                c.setDuration(3500);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        elanPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendElan();
            }
        });
    }


    public void getPlaceDetails(final String placeId) {
        String url = KavinooLinks.getPlaceDetailsLink + placeId;

        final StringRequest placeDetailsReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                PlaceDetails placeDetails = gson.fromJson(response, PlaceDetails.class);

                placeDetailsInActivity = placeDetails;

                placeTitleMain.setText(placeDetails.getTitle());
                distancePlaceDetails.setText(distance);
                visitCountPlaceDetails.setText(String.valueOf(placeDetails.getVisitCount()));
                commentCountPlaceDetails.setText(String.valueOf(placeDetails.getCommentsCount()));
                favoriteCountPlaceDetails.setText(String.valueOf(placeDetails.getFavoriteCount()));
                address.setText(placeDetails.getAddress());

                if (placeDetails.getSlogan().equals("")) {
                    sloganConstraint.setVisibility(View.GONE);
                }
                if (!placeDetails.getSlogan().equals("")) {
                    sloganConstraint.setVisibility(View.VISIBLE);
                    sloganText.setText(placeDetails.getSlogan());
                }

                if (placeDetails.getDescription().equals("")) {
                    descriptionCard.setVisibility(View.GONE);
                }
                if (!placeDetails.getDescription().equals("")) {
                    descriptionCard.setVisibility(View.VISIBLE);
                    descriptionText.setText(placeDetails.getDescription());
                }


                if (placeDetails.getAssessments().size() == 0) {
                    assessmentConstraint.setVisibility(View.GONE);
                } else {
                    assessmentConstraint.setVisibility(View.VISIBLE);
                    if (placeDetails.getAssessments().size() == 1) {
                        float p1 = 2.5f;
                        if (placeDetails.getAssessments().get(0).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(0).getAvgRate());
                        }
                        float pF = p1 / 5f;
                        pF = pF * 100;
                        assessment1Progress.setProgress(pF);
                        assessment1Text.setText(placeDetails.getAssessments().get(0).getTitle());

                        assessment2Progress.setVisibility(View.INVISIBLE);
                        assessment3Progress.setVisibility(View.INVISIBLE);
                        assessment4Progress.setVisibility(View.INVISIBLE);

                        assessment2Linear.setVisibility(View.GONE);
                        assessment3Linear.setVisibility(View.GONE);
                        assessment4Linear.setVisibility(View.GONE);

                    }
                    if (placeDetails.getAssessments().size() == 2) {
                        float p1 = 2.5f;
                        if (placeDetails.getAssessments().get(0).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(0).getAvgRate());
                        }

                        float pF = p1 / 5f;
                        pF = pF * 100;
                        assessment1Progress.setProgress(pF);
                        assessment1Text.setText(placeDetails.getAssessments().get(0).getTitle());

                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(1).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(1).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment2Progress.setProgress(pF);
                        assessment2Text.setText(placeDetails.getAssessments().get(1).getTitle());


                        assessment3Progress.setVisibility(View.INVISIBLE);
                        assessment4Progress.setVisibility(View.INVISIBLE);

                        assessment3Linear.setVisibility(View.GONE);
                        assessment4Linear.setVisibility(View.GONE);
                    }
                    if (placeDetails.getAssessments().size() == 3) {

                        float p1 = 2.5f;
                        if (placeDetails.getAssessments().get(0).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(0).getAvgRate());
                        }

                        float pF = p1 / 5f;
                        pF = pF * 100;
                        assessment1Progress.setProgress(pF);
                        assessment1Text.setText(placeDetails.getAssessments().get(0).getTitle());

                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(1).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(1).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment2Progress.setProgress(pF);
                        assessment2Text.setText(placeDetails.getAssessments().get(1).getTitle());

                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(2).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(2).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment3Progress.setProgress(pF);
                        assessment3Text.setText(placeDetails.getAssessments().get(2).getTitle());


                        assessment4Progress.setVisibility(View.INVISIBLE);

                        assessment4Linear.setVisibility(View.GONE);
                    }
                    if (placeDetails.getAssessments().size() >= 4) {
                        float p1 = 2.5f;
                        if (placeDetails.getAssessments().get(0).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(0).getAvgRate());
                        }

                        float pF = p1 / 5f;
                        pF = pF * 100;
                        assessment1Progress.setProgress(pF);
                        assessment1Text.setText(placeDetails.getAssessments().get(0).getTitle());


                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(1).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(1).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment2Progress.setProgress(pF);
                        assessment2Text.setText(placeDetails.getAssessments().get(1).getTitle());

                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(2).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(2).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment3Progress.setProgress(pF);
                        assessment3Text.setText(placeDetails.getAssessments().get(2).getTitle());

                        p1 = 2.5f;
                        if (placeDetails.getAssessments().get(3).getAvgRate().equals("")) {

                        } else {
                            p1 = Float.parseFloat(placeDetails.getAssessments().get(3).getAvgRate());
                        }
                        pF = p1 / 5f;
                        pF = pF * 100;
                        assessment4Progress.setProgress(pF);
                        assessment4Text.setText(placeDetails.getAssessments().get(3).getTitle());


                    }
                }

                rateNumber.setText(placeDetails.getRate()+"");
                int rondRate = (int) placeDetails.getRate();
                if (rondRate == 0) {
                    rateImageView.setImageResource(R.drawable.hrate0);
                }
                if (rondRate == 1) {
                    rateImageView.setImageResource(R.drawable.hrate1);
                }
                if (rondRate == 2) {
                    rateImageView.setImageResource(R.drawable.hrate2);
                }
                if (rondRate == 3) {
                    rateImageView.setImageResource(R.drawable.hrate3);
                }
                if (rondRate == 4) {
                    rateImageView.setImageResource(R.drawable.hrate4);
                }
                if (rondRate == 5) {
                    rateImageView.setImageResource(R.drawable.hrate5);
                }


                PlaceImageSliderAdapter adapter = new PlaceImageSliderAdapter(PlaceActivity.this);

                String urlCrk = "https://map.ir/static?width=700&height=400&markers=color:blue|label:" + placeDetails.getTitle() + "|" + placeDetails.getLongitude() + "," + placeDetails.getLatitude() + "&zoom_level=16";


                GlideUrl glideUrl = new GlideUrl(urlCrk,
                        new LazyHeaders.Builder()
                                .addHeader("x-api-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3In0.eyJhdWQiOiIxNjY2NCIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3IiwiaWF0IjoxNjQyMTIyNjk2LCJuYmYiOjE2NDIxMjI2OTYsImV4cCI6MTY0NDYyODI5Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.S6IMCplH_EasvLFTuSYXtMAIK1W1HOlAUYeYciNen1Q-JXmJs-326pziiw1m7RdWKv4naaaPGTiZk2ty0py5PlyHu6eEHKb8sM2ewKbqMcKjpL48O6WDRHw4ESZ3VjppvvSazHnPmxDOc5FtdjDcWKI4YJwXx1cArF8w4-FJNBjYf9YwTBeYlDWS7gS7SDXwl_XIUZK983ytr2gyY_T074d3Z4cqWtwkCQA3LddxxOy_b50BzP0v0aRTvd2nrF5p33K8syqOCDpSC_TrxdDxw2tEJQ4Hqi0LrZcwPaga6qskQyyboEMmBVJe2_npLMI7OacQd5NcedPMnAsXOGZyhw")
                                .build());

                Glide.with(PlaceActivity.this)
                        .load(glideUrl)
                        .into(mapCrk);


                if (placeDetails.getImage().getImage01().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel1 = new PlaceImageSliderModel();
                    placeImageSliderModel1.setImage(placeDetails.getImage().getImage01());
                    adapter.addItem(placeImageSliderModel1);

                }
                if (placeDetails.getImage().getImage02().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel2 = new PlaceImageSliderModel();
                    placeImageSliderModel2.setImage(placeDetails.getImage().getImage02());
                    adapter.addItem(placeImageSliderModel2);

                }
                if (placeDetails.getImage().getImage03().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel3 = new PlaceImageSliderModel();
                    placeImageSliderModel3.setImage(placeDetails.getImage().getImage03());
                    adapter.addItem(placeImageSliderModel3);
                }
                if (placeDetails.getImage().getImage04().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel4 = new PlaceImageSliderModel();
                    placeImageSliderModel4.setImage(placeDetails.getImage().getImage04());
                    adapter.addItem(placeImageSliderModel4);
                }
                if (placeDetails.getImage().getImage05().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel5 = new PlaceImageSliderModel();
                    placeImageSliderModel5.setImage(placeDetails.getImage().getImage05());
                    adapter.addItem(placeImageSliderModel5);

                }
                if (placeDetails.getImage().getImage06().equals("")) {

                } else {

                    PlaceImageSliderModel placeImageSliderModel6 = new PlaceImageSliderModel();
                    placeImageSliderModel6.setImage(placeDetails.getImage().getImage06());
                    adapter.addItem(placeImageSliderModel6);
                }
                if (placeDetails.getImage().getImage07().equals("")) {

                } else {
                    PlaceImageSliderModel placeImageSliderModel7 = new PlaceImageSliderModel();
                    placeImageSliderModel7.setImage(placeDetails.getImage().getImage07());
                    adapter.addItem(placeImageSliderModel7);
                }
                if (placeDetails.getImage().getImage08().equals("")) {
                } else {
                    PlaceImageSliderModel placeImageSliderModel8 = new PlaceImageSliderModel();
                    placeImageSliderModel8.setImage(placeDetails.getImage().getImage08());
                    adapter.addItem(placeImageSliderModel8);
                }
                if (placeDetails.getImage().getImage09().equals("")) {
                } else {
                    PlaceImageSliderModel placeImageSliderModel9 = new PlaceImageSliderModel();
                    placeImageSliderModel9.setImage(placeDetails.getImage().getImage09());
                    adapter.addItem(placeImageSliderModel9);
                }
                if (placeDetails.getImage().getImage10().equals("")) {
                } else {
                    PlaceImageSliderModel placeImageSliderModel10 = new PlaceImageSliderModel();
                    placeImageSliderModel10.setImage(placeDetails.getImage().getImage10());
                    adapter.addItem(placeImageSliderModel10);
                }


                placeImageSlider.setSliderAdapter(adapter);
                placeImageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP);

                Random rand = new Random();
                String random = String.valueOf(rand.nextInt(4) + 1);

                if (random.equals("1")) {
                    placeImageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
                }
                if (random.equals("2")) {
                    placeImageSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
                }
                if (random.equals("3")) {
                    placeImageSlider.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
                }
                if (random.equals("4")) {
                    placeImageSlider.setSliderTransformAnimation(SliderAnimations.GATETRANSFORMATION);
                }

                placeImageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                placeImageSlider.setScrollTimeInSec(3); //set scroll delay in seconds :
                placeImageSlider.startAutoCycle();

                //comments initialize

                if (placeDetails.getComments().size() == 0) {
                    noCommentTextView.setVisibility(View.VISIBLE);
                } else {
                    noCommentTextView.setVisibility(View.GONE);
                }
                adapterComments = new CommentsAdapter(PlaceActivity.this, placeDetails.getComments(),PlaceActivity.this);
                commentRecyclerView.setAdapter(adapterComments);
                layoutManagerComments = new LinearLayoutManager(PlaceActivity.this);
                commentRecyclerView.setLayoutManager(layoutManagerComments);
                commentRecyclerView.setHasFixedSize(true);
                adapterComments.notifyDataSetChanged();

                //facilities
                facilitiesPlace = placeDetails.getFacilities();

                //digitalmenu
                digitalMenusPlace = placeDetails.getDigitalMenus();
                //tizer
                globalVideoPath = placeDetails.getImage().getVideo();

                com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails placeDetails2 = gson.fromJson(response, com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails.class);
                placeDetailsLocal = placeDetails2;


                loadingConstraint.startAnimation(aniZoomOut);
                loadingConstraint.setVisibility(View.INVISIBLE);

                updateVisitCount();

                if(!placeDetailsInActivity.getInstagramId().equals("")){
                    Animation animInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_out);
                    instagramId.startAnimation(animInOut);
                }
                if (!placeDetailsInActivity.getTelegramId().equals("")) {
                    Animation animInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_out);
                    whatsappId.startAnimation(animInOut);
                }
                if (!placeDetailsInActivity.getWebsite().equals("")) {
                    Animation animInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_out);
                    website.startAnimation(animInOut);
                }
                if (!placeDetailsInActivity.getTelephone01().equals("") || !placeDetailsInActivity.getTelephone02().equals("") || !placeDetailsInActivity.getTelephone03().equals("") || !placeDetailsInActivity.getTelephone04().equals("") || !placeDetailsInActivity.getMobile02().equals("") || !placeDetailsInActivity.getMobile03().equals("")) {
                    Animation animInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_out);
                    phoneNumbers.startAnimation(animInOut);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        placeDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(placeDetailsReq, "PLACEDETAILSREQ");

    }

    public void updateVisitCount() {
        final StringRequest updateVisit = new StringRequest(Request.Method.POST, KavinooLinks.UPDATE_PLACE_VISIT_COUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(idPlace));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + TokenContainer.getToken());
                return header;
            }

        };

        updateVisit.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(updateVisit, "UPDATEVISIT");

    }

    public void showFacilitiesDialog(List<FacilitiesItem> facilitiesItemList) {

        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.facilities_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RecyclerView filterCategoryRecyclerView;
        final RecyclerView.Adapter adapterfilter;
        RecyclerView.LayoutManager layoutManagerFilter;

        filterCategoryRecyclerView = dialog.findViewById(R.id.filter_category_recycler_view);

        adapterfilter = new FacilitiesAdapter(PlaceActivity.this, facilitiesItemList);
        filterCategoryRecyclerView.setAdapter(adapterfilter);
        layoutManagerFilter = new GridLayoutManager(PlaceActivity.this, 2);
        filterCategoryRecyclerView.setLayoutManager(layoutManagerFilter);
        filterCategoryRecyclerView.setHasFixedSize(true);

        dialog.show();
    }

    public void showDigitalMenuDialog(List<DigitalMenusItem> digitalMenusItems) {

        if (digitalMenusItems.size() == 0) {
            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
            c.setTitle("منو دیجیتال موجود نمی باشد");
            c.setSwipeToDismiss(true);
            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        } else {
            final Dialog dialog = new Dialog(PlaceActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.digital_menu_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            RecyclerView digitalMenuRecyclerView;
            final RecyclerView.Adapter adapterDigitalMenu;
            RecyclerView.LayoutManager layoutManagerDigitalMenu;

            digitalMenuRecyclerView = dialog.findViewById(R.id.digital_menu_recycler_view);

            adapterDigitalMenu = new DigitalMenuAdapter(PlaceActivity.this, digitalMenusItems);
            digitalMenuRecyclerView.setAdapter(adapterDigitalMenu);
            layoutManagerDigitalMenu = new LinearLayoutManager(PlaceActivity.this);
            digitalMenuRecyclerView.setLayoutManager(layoutManagerDigitalMenu);
            digitalMenuRecyclerView.setHasFixedSize(true);

            dialog.show();
        }


    }

    public void showAddAssessmentDialog(final List<AssessmentsItem> assessments, final int placeIdAssessment) {


        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_assessment_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView addAssessment;

        final ProgressBar spinKitAddAssessment;
        final TextView addAssessmentTextView;

        Slider slider1;
        Slider slider2;
        Slider slider3;
        Slider slider4;

        TextView ass1;
        TextView ass2;
        TextView ass3;
        TextView ass4;

        ass1=dialog.findViewById(R.id.ass1);
        ass2=dialog.findViewById(R.id.ass2);
        ass3=dialog.findViewById(R.id.ass3);
        ass4=dialog.findViewById(R.id.ass4);

        slider1=dialog.findViewById(R.id.slider1);
        slider2=dialog.findViewById(R.id.slider2);
        slider3=dialog.findViewById(R.id.slider3);
        slider4=dialog.findViewById(R.id.slider4);
        addAssessment=dialog.findViewById(R.id.add_assessment);
        spinKitAddAssessment=dialog.findViewById(R.id.spin_kit_add_assessment);
        addAssessmentTextView=dialog.findViewById(R.id.add_assessment_text_view);

        final AddAssessmentModel addAssessmentModel=new AddAssessmentModel();

        final List<com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem> assessmentsItems=new ArrayList<>();

        ThreeBounce threeBounce = new ThreeBounce();
        spinKitAddAssessment.setIndeterminateDrawable(threeBounce);
        spinKitAddAssessment.setVisibility(View.INVISIBLE);

        final int[] rates=new int[4];

        if(assessments.size()==1){

            slider1.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[0]=(int) value;
                }
            });

            ass1.setText(assessments.get(0).getTitle());


            ass2.setVisibility(View.GONE);
            ass3.setVisibility(View.GONE);
            ass4.setVisibility(View.GONE);

            slider2.setVisibility(View.GONE);
            slider3.setVisibility(View.GONE);
            slider4.setVisibility(View.GONE);

        }
        if (assessments.size() == 2) {

            slider1.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[0]=(int) value;
                }
            });
            slider2.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[1]=(int) value;
                }
            });

            ass1.setText(assessments.get(0).getTitle());
            ass2.setText(assessments.get(1).getTitle());


            ass3.setVisibility(View.GONE);
            ass4.setVisibility(View.GONE);

            slider3.setVisibility(View.GONE);
            slider4.setVisibility(View.GONE);

        }
        if (assessments.size() == 3) {

            slider1.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[0]=(int) value;
                }
            });
            slider2.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[1]=(int) value;
                }
            });
            slider3.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[2]=(int) value;
                }
            });

            ass1.setText(assessments.get(0).getTitle());
            ass2.setText(assessments.get(1).getTitle());
            ass3.setText(assessments.get(2).getTitle());


            ass4.setVisibility(View.GONE);

            slider4.setVisibility(View.GONE);

        }
        if (assessments.size() >= 4) {

            slider1.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[0]=(int) value;
                }
            });
            slider2.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[1]=(int) value;
                }
            });
            slider3.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[2]=(int) value;
                }
            });
            slider4.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    rates[3]=(int) value;
                }
            });

            ass1.setText(assessments.get(0).getTitle());
            ass2.setText(assessments.get(1).getTitle());
            ass3.setText(assessments.get(2).getTitle());
            ass4.setText(assessments.get(3).getTitle());

        }

        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assessments.size() >= 4) {
                    for (int i=0;i<4;i++){
                        com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem assessmentsItem=new com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem();
                        assessmentsItem.setRate(rates[i]);
                        assessmentsItem.setId(assessments.get(i).getId());
                        assessmentsItems.add(assessmentsItem);
                    }
                }
                if (assessments.size() == 3) {
                    for (int i = 0; i < 3; i++) {
                        com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem assessmentsItem = new com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem();
                        assessmentsItem.setRate(rates[i]);
                        assessmentsItem.setId(assessments.get(i).getId());
                        assessmentsItems.add(assessmentsItem);
                    }
                }
                if (assessments.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem assessmentsItem = new com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem();
                        assessmentsItem.setRate(rates[i]);
                        assessmentsItem.setId(assessments.get(i).getId());
                        assessmentsItems.add(assessmentsItem);
                    }
                }
                if (assessments.size() == 1) {
                    for (int i = 0; i < 1; i++) {
                        com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem assessmentsItem = new com.kavinoo.kavinoo.onlinedata.model.addassessment.AssessmentsItem();
                        assessmentsItem.setRate(rates[i]);
                        assessmentsItem.setId(assessments.get(i).getId());
                        assessmentsItems.add(assessmentsItem);
                    }
                }
                addAssessmentModel.setPlaceId(String.valueOf(placeIdAssessment));
                addAssessmentModel.setAssessments(assessmentsItems);
                Log.i("PPQW"," is model  ::::: "+addAssessmentModel.toString());
                final String json = new Gson().toJson(addAssessmentModel);

                spinKitAddAssessment.setVisibility(View.VISIBLE);
                addAssessmentTextView.setVisibility(View.INVISIBLE);
                final StringRequest addCommentReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_ASSESSMENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PPQW"," is response ::::: "+response);

                        spinKitAddAssessment.setVisibility(View.INVISIBLE);
                        addAssessmentTextView.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                        c.setTitle(" بازخورد شما با موفقیت ثبت شد");
                        c.setSwipeToDismiss(true);
                        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitAddAssessment.setVisibility(View.INVISIBLE);
                        addAssessmentTextView.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                        c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                        c.setSwipeToDismiss(true);
                        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("data",  json);

                        return params;
                    }


                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<String, String>();
                        header.put("Authorization", "Bearer " + TokenContainer.getToken());
                        return header;
                    }

                };

                addCommentReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                App.getInstance().addToRequestQueue(addCommentReq, "ADDCOMMENTREQ");

            }
        });





        dialog.show();

    }

    public void showPhoneNumberDialog() {

        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.phone_number_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout telephone01Linear;
        TextView telephone01;
        LinearLayout telephone02Linear;
        TextView telephone02;
        LinearLayout telephone03Linear;
        TextView telephone03;
        LinearLayout telephone04Linear;
        TextView telephone04;
        LinearLayout mobile02Linear;
        TextView mobile02;
        LinearLayout mobile03Linear;
        TextView mobile03;

        if (placeDetailsInActivity.getTelephone01().equals("") && placeDetailsInActivity.getTelephone02().equals("") && placeDetailsInActivity.getTelephone03().equals("") && placeDetailsInActivity.getTelephone04().equals("") && placeDetailsInActivity.getMobile02().equals("") && placeDetailsInActivity.getMobile03().equals("")) {
            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
            c.setTitle("برای این مکان تلفن ثبت نشده است.");
            c.setSwipeToDismiss(true);
            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        }else{
            telephone01Linear = dialog.findViewById(R.id.telephone01_linear);
            telephone01 = dialog.findViewById(R.id.telephone01);
            telephone02Linear = dialog.findViewById(R.id.telephone02_linear);
            telephone02 = dialog.findViewById(R.id.telephone02);
            telephone03Linear = dialog.findViewById(R.id.telephone03_linear);
            telephone03 = dialog.findViewById(R.id.telephone03);
            telephone04Linear = dialog.findViewById(R.id.telephone04_linear);
            telephone04 = dialog.findViewById(R.id.telephone04);
            mobile02Linear = dialog.findViewById(R.id.mobile02_linear);
            mobile02 = dialog.findViewById(R.id.mobile02);
            mobile03Linear = dialog.findViewById(R.id.mobile03_linear);
            mobile03 = dialog.findViewById(R.id.mobile03);

            if (placeDetailsInActivity.getTelephone01().equals("")) {
                telephone01Linear.setVisibility(View.GONE);
            } else {
                telephone01.setText(placeDetailsInActivity.getTelephone01());
                telephone01Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getTelephone01();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }

            if (placeDetailsInActivity.getTelephone02().equals("")) {
                telephone02Linear.setVisibility(View.GONE);
            } else {
                telephone02.setText(placeDetailsInActivity.getTelephone02());
                telephone02Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getTelephone02();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }

            if (placeDetailsInActivity.getTelephone03().equals("")) {
                telephone03Linear.setVisibility(View.GONE);
            } else {
                telephone03.setText(placeDetailsInActivity.getTelephone03());
                telephone03Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getTelephone03();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }

            if (placeDetailsInActivity.getTelephone04().equals("")) {
                telephone04Linear.setVisibility(View.GONE);
            } else {
                telephone04.setText(placeDetailsInActivity.getTelephone04());
                telephone04Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getTelephone04();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }

            if (placeDetailsInActivity.getMobile02().equals("")) {
                mobile02Linear.setVisibility(View.GONE);
            } else {
                mobile02.setText(placeDetailsInActivity.getMobile02());
                mobile02Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getMobile02();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }

            if (placeDetailsInActivity.getMobile03().equals("")) {
                mobile03Linear.setVisibility(View.GONE);
            } else {
                mobile03.setText(placeDetailsInActivity.getMobile03());
                mobile03Linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String m = placeDetailsInActivity.getMobile03();
                        char[] mc = m.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append("+");
                        sb.append("9");
                        sb.append("8");
                        for (int i = 1; i < mc.length; i++) {
                            sb.append(mc[i]);
                        }
                        String phone = sb.toString();
                        callIntent.setData(Uri.parse("tel: " + phone));
                        startActivity(callIntent);
                    }
                });
            }


            dialog.show();
        }


    }


    public void showTizerDialog(String videoPath) {



        if (videoPath.equals("")) {
            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
            c.setTitle("برای این مکان تیزر ثبت نشده است");
            c.setSwipeToDismiss(true);
            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        } else {
            final DialogWithVolume dialog = new DialogWithVolume(PlaceActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.tizer_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            playerView = dialog.findViewById(R.id.player_view);
            fullScreenCardView = playerView.findViewById(R.id.full_screen_card_view);
            progressBar = dialog.findViewById(R.id.progress_bar);

            fullScreenCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent=new Intent(PlaceActivity.this, FullScreenTizerActivity.class);
                        intent.putExtra("path",videoPath);
                        intent.putExtra("position_now",simpleExoPlayer.getCurrentPosition());
                        startActivity(intent);
                        releasePlayer();
                        dialog.dismiss();
                    }catch (Exception e){

                    }

                }
            });

            if (simpleExoPlayer != null) {
                simpleExoPlayer.release();
            }


            Uri videoUrl = Uri.parse(videoPath);


            LoadControl loadControl = new DefaultLoadControl();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(PlaceActivity.this, trackSelector, loadControl);
            factory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource;

            mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);

            playerView.setPlayer(simpleExoPlayer);

            simpleExoPlayer.prepare(mediaSource, true, false);

            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.addListener(new Player.DefaultEventListener() {

                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                    super.onTimelineChanged(timeline, manifest, reason);
                }


                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    super.onTracksChanged(trackGroups, trackSelections);
                }

                @Override
                public void onLoadingChanged(boolean isLoading) {
                    super.onLoadingChanged(isLoading);
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    super.onPlayerStateChanged(playWhenReady, playbackState);
                    if (playbackState == Player.STATE_BUFFERING) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else if (playbackState == Player.STATE_READY) {
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                    super.onRepeatModeChanged(repeatMode);
                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled);
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    super.onPlayerError(error);
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    super.onPositionDiscontinuity(reason);
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                    super.onPlaybackParametersChanged(playbackParameters);
                }

                @Override
                public void onSeekProcessed() {
                    super.onSeekProcessed();
                }

                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {
                    super.onTimelineChanged(timeline, manifest);
                }
            });

            dialog.setPlayer(simpleExoPlayer);

            dialog.show();

            dialog.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        releasePlayer();
                        dialog.dismiss();
                    }
                    return true;
                }
            });

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    releasePlayer();
                }
            });
        }

    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void showRating() {
        if (TokenContainer.getToken().equals("")) {
            showGotoRegister();
        } else {
            final Dialog dialog = new Dialog(PlaceActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.rating_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final String[] rate = {String.valueOf(2.5)};
            MaterialRatingBar materialRatingBar;
            ImageView addRate;
            final TextView addRateTextView;
            final ProgressBar spinKitAddRate;

            materialRatingBar = dialog.findViewById(R.id.rating_bar);
            addRate = dialog.findViewById(R.id.add_rate);
            addRateTextView = dialog.findViewById(R.id.add_rate_text_view);
            spinKitAddRate = dialog.findViewById(R.id.spin_kit_add_rate);

            ThreeBounce threeBounce = new ThreeBounce();
            spinKitAddRate.setIndeterminateDrawable(threeBounce);
            spinKitAddRate.setVisibility(View.INVISIBLE);


            materialRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    rate[0] = String.valueOf(rating);
                }
            });

            addRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addRateTextView.setVisibility(View.INVISIBLE);
                    spinKitAddRate.setVisibility(View.VISIBLE);

                    final StringRequest addRateReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_RATE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            spinKitAddRate.setVisibility(View.INVISIBLE);
                            addRateTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                            c.setTitle(" امتیاز شما با موفقیت ثبت شد");
                            c.setSwipeToDismiss(true);
                            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            spinKitAddRate.setVisibility(View.INVISIBLE);
                            addRateTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                            c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                            c.setSwipeToDismiss(true);
                            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("placeId", String.valueOf(idPlace));
                            params.put("rate", rate[0]);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new HashMap<String, String>();
                            header.put("Authorization", "Bearer " + TokenContainer.getToken());
                            return header;
                        }

                    };

                    addRateReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    App.getInstance().addToRequestQueue(addRateReq, "ADDRATEREQ");


                }
            });

            dialog.show();
        }

    }

    public void showGotoRegister() {
        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.go_to_register_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showReportPlace() {
        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.report_place_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout reportPlaceLinear;

        reportPlaceLinear = dialog.findViewById(R.id.report_place_linear);

        reportPlaceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                c.setTitle("گزارش شما با موفقیت ثبت شد");
                c.setSwipeToDismiss(true);
                c.setDuration(3500);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        dialog.show();
    }

    public void showGotoRegisterForComment() {
        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.go_to_register_for_comment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showGotoRegisterForElan() {
        final Dialog dialog = new Dialog(PlaceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.go_to_register_for_elan_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void sendComment() {
        if (TokenContainer.getToken().equals("")) {
            showGotoRegisterForComment();
        } else {
            final Dialog dialog = new Dialog(PlaceActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.add_comment_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            ImageView addComment;
            final TextView addCommentTextView;
            final ProgressBar spinKitAddComment;
            final EditText bodyEditText;

            addComment = dialog.findViewById(R.id.add_comment);
            addCommentTextView = dialog.findViewById(R.id.add_comment_text_view);
            spinKitAddComment = dialog.findViewById(R.id.spin_kit_add_comment);
            bodyEditText = dialog.findViewById(R.id.body_edit_text);

            ThreeBounce threeBounce = new ThreeBounce();
            spinKitAddComment.setIndeterminateDrawable(threeBounce);
            spinKitAddComment.setVisibility(View.INVISIBLE);

            addComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    body = bodyEditText.getText().toString();
                    addCommentTextView.setVisibility(View.INVISIBLE);
                    spinKitAddComment.setVisibility(View.VISIBLE);

                    final StringRequest addCommentReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_COMMENT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            spinKitAddComment.setVisibility(View.INVISIBLE);
                            addCommentTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                            c.setTitle(" نظر شما با موفقیت ثبت شد");
                            c.setSwipeToDismiss(true);
                            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                            addActivityScore("6",String.valueOf(idPlace));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            spinKitAddComment.setVisibility(View.INVISIBLE);
                            addCommentTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                            c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                            c.setSwipeToDismiss(true);
                            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("place_id", String.valueOf(idPlace));
                            params.put("title", " ");
                            params.put("body", body);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new HashMap<String, String>();
                            header.put("Authorization", "Bearer " + TokenContainer.getToken());
                            return header;
                        }

                    };

                    addCommentReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    App.getInstance().addToRequestQueue(addCommentReq, "ADDCOMMENTREQ");


                }
            });

            dialog.show();
        }
    }

    public void addActivityScore(final String activityScoreId, final String placeId){

        StringRequest addScoreReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_ACTIVITY_SCORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("activity_score_id", activityScoreId);

                params.put("place_id",placeId);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + TokenContainer.getToken());
                return header;
            }

        };

        addScoreReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(addScoreReq, "ADDCOMMENTREQ");

    }

    public void sendElan() {
        if (TokenContainer.getToken().equals("")) {
            showGotoRegisterForElan();
        } else {
            final Dialog dialog = new Dialog(PlaceActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.elan_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            ImageView elan;
            final TextView elanTextView;
            final ProgressBar spinKitElan;

            elan = dialog.findViewById(R.id.elan);
            elanTextView = dialog.findViewById(R.id.elan_text_view);
            spinKitElan = dialog.findViewById(R.id.spin_kit_elan);

            ThreeBounce threeBounce = new ThreeBounce();
            spinKitElan.setIndeterminateDrawable(threeBounce);
            spinKitElan.setVisibility(View.INVISIBLE);


            elan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    elanTextView.setVisibility(View.INVISIBLE);
                    spinKitElan.setVisibility(View.VISIBLE);

                    final StringRequest elanReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_ELAN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            spinKitElan.setVisibility(View.INVISIBLE);
                            elanTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                            ElanResponse elanResponse = gson.fromJson(response, ElanResponse.class);
                            if (elanResponse.getStatusCode() == 200) {
                                CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                                c.setTitle(" حضور شما با موفقیت ثبت شد");
                                c.setSwipeToDismiss(true);
                                c.setDuration(3500);
                                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                                addActivityScore("1",String.valueOf(idPlace));
                            } else {
                                CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                                c.setTitle("شما در موقعیت مکانی نزدیک به محل قرار ندارید یا اطلاعات مکان شما در دسترس نیست");
                                c.setSwipeToDismiss(true);
                                c.setDuration(3500);
                                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            spinKitElan.setVisibility(View.INVISIBLE);
                            elanTextView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            CookieBar.Builder c = CookieBar.build(PlaceActivity.this);
                            c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                            c.setSwipeToDismiss(true);
                            c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                            ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("place_id", String.valueOf(idPlace));
                            params.put("lat", "30.302098");
                            params.put("lon", "57.089559");
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new HashMap<String, String>();
                            header.put("Authorization", "Bearer " + TokenContainer.getToken());
                            return header;
                        }

                    };

                    elanReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    App.getInstance().addToRequestQueue(elanReq, "ELANREQ");


                }
            });

            dialog.show();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFromDeepLink) {
            Intent intent = new Intent(PlaceActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}