package com.kavinoo.kavinoo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.LoginActivity;
import com.kavinoo.kavinoo.activity.MenuActivity;
import com.kavinoo.kavinoo.activity.ProfileActivity;
import com.kavinoo.kavinoo.activity.SelectLocationActivity;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesResponse;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.addplaceresponse.AddPlaceGeneralResponse;
import com.kavinoo.kavinoo.utils.NullStringToEmptyAdapterFactory;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mrapp.android.bottomsheet.BottomSheet;


public class AddLocationFragment extends Fragment {

    EditText titlePlace;

    TextView category;
    TextView subCategory;

    ImageView sabtGeneralCadr;

    TextView sabtGeneral;


    List<CategoriesItem> categoriesItems = new ArrayList<>();
    BottomSheet.Builder categoryBottomSheetBuilder;
    String categoryIdSelected = "notset";

    List<CategoriesItem> subCategoriesItems = new ArrayList<>();
    BottomSheet.Builder subCategoryBottomSheetBuilder;
    String subCategoryIdSelected = "notset";

    ProgressBar sabtProgress;

    Context myContext;

    ImageView mapDemo;

    UserInfoManager userInfoManager;

    EditText locationAdd;

    String lastLat;
    String lastLon;

    LinearLayout addFavoriteBusinessLinear;
    LinearLayout addMyBusinessLinear;
    ImageView addFavoriteBusiness;
    TextView addFavoriteBusinessTextView;
    ImageView addMyBusiness;
    TextView addMyBusinessTextView;

    ImageView downloadKvisitor;

    CardView kavinooProfile;
    CardView menuToolbar;
    ConstraintLayout toolbarMain;


    public AddLocationFragment() {

    }

    public AddLocationFragment(String title) {
        // Required empty public constructor
    }

    public AddLocationFragment(String title, Context context) {
        this.myContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titlePlace = view.findViewById(R.id.title_place);
        category = view.findViewById(R.id.category);
        subCategory = view.findViewById(R.id.sub_category);
        sabtGeneralCadr = view.findViewById(R.id.sabt_general_cadr);
        sabtGeneral = view.findViewById(R.id.sabt_general);
        mapDemo = view.findViewById(R.id.map_demo);
        locationAdd = view.findViewById(R.id.location_add);
        addFavoriteBusiness=view.findViewById(R.id.add_favorite_business);
        addFavoriteBusinessTextView=view.findViewById(R.id.add_favorite_business_text_view);
        addMyBusiness=view.findViewById(R.id.add_my_business);
        addMyBusinessTextView=view.findViewById(R.id.add_my_business_text_view);
        addFavoriteBusinessLinear=view.findViewById(R.id.add_favorite_business_linear);
        addMyBusinessLinear=view.findViewById(R.id.add_my_business_linear);
        downloadKvisitor=view.findViewById(R.id.download_kvisitor);
        toolbarMain=view.findViewById(R.id.toolbar_main);
        menuToolbar=toolbarMain.findViewById(R.id.menu_toolbar);
        kavinooProfile=toolbarMain.findViewById(R.id.kavinoo_profile);

        locationAdd.setEnabled(false);

        final Animation aniZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in_two);
        final Animation aniZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out_two);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        kavinooProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        userInfoManager = new UserInfoManager(myContext);

        getCategory();

        downloadKvisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.kavinoo.kavinoobusiness"));
                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooserIntent);
            }
        });

        addFavoriteBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavoriteBusiness.setImageResource(R.drawable.tabonaddplace);
                addFavoriteBusinessTextView.setTextColor(Color.parseColor("#ffffff"));

                addMyBusiness.setImageResource(R.drawable.taboffaddplace);
                addMyBusinessTextView.setTextColor(Color.parseColor("#bfced5"));

                addFavoriteBusinessLinear.setVisibility(View.VISIBLE);
                addMyBusinessLinear.setVisibility(View.GONE);

                addFavoriteBusinessLinear.startAnimation(aniZoomIn);
                addMyBusinessLinear.startAnimation(aniZoomOut);


            }
        });

        addMyBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMyBusiness.setImageResource(R.drawable.tabonaddplace);
                addMyBusinessTextView.setTextColor(Color.parseColor("#ffffff"));

                addFavoriteBusiness.setImageResource(R.drawable.taboffaddplace);
                addFavoriteBusinessTextView.setTextColor(Color.parseColor("#bfced5"));

                addMyBusinessLinear.setVisibility(View.VISIBLE);
                addFavoriteBusinessLinear.setVisibility(View.GONE);

                addMyBusinessLinear.startAnimation(aniZoomIn);
                addFavoriteBusinessLinear.startAnimation(aniZoomOut);


            }
        });


        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheet categoryBottomSheet = categoryBottomSheetBuilder.create();
                categoryBottomSheet.show();
                categoryBottomSheetBuilder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        subCategoriesItems.clear();
                        subCategoryIdSelected = "notset";
                        subCategory.setText("");
                        category.setText(categoriesItems.get(position).getCategoryTitle());
                        categoryIdSelected = categoriesItems.get(position).getCategoryId() + "";
                        for (int mI = 0; mI < categoriesItems.size(); mI++) {
                            if (String.valueOf(categoriesItems.get(mI).getCategoryId()).equals(categoryIdSelected)) {
                                userInfoManager.savePinImage(categoriesItems.get(mI).getPinImage());
                                break;
                            }
                        }
                        getSubCategory(categoryIdSelected);
                    }
                });

            }
        });

        subCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoryIdSelected.equals("notset")) {
                    BottomSheet subCategoryBottomSheet = subCategoryBottomSheetBuilder.create();
                    subCategoryBottomSheet.show();
                    subCategoryBottomSheetBuilder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            subCategory.setText(subCategoriesItems.get(position).getCategoryTitle());
                            subCategoryIdSelected = subCategoriesItems.get(position).getCategoryId() + "";
                        }
                    });
                } else {
                    CookieBar.Builder c = CookieBar.build(getActivity());
                    c.setTitle(" لطفا ابتدا دسته بندی را انتخاب نمایید و سپس زیر دسته بندی");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }
            }
        });

        sabtGeneralCadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categoryIdSelected.equals("notset") || subCategoryIdSelected.equals("notset")){
                    CookieBar.Builder c = CookieBar.build(getActivity());
                    c.setTitle(" لطفا همه ی فیلدها را تکمیل نمایید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }else {
                    if(locationAdd.getText().toString().equals(" آدرس ")){
                        CookieBar.Builder c = CookieBar.build(getActivity());
                        c.setTitle(" لطفا آدرس را از روی نقشه انتخاب نمایید");
                        c.setSwipeToDismiss(true);
                        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    }else {
                        addMyFavoritePlace();
                    }
                }
            }
        });

        sabtProgress = view.findViewById(R.id.progress_sabt_general);
        ThreeBounce threeBounce = new ThreeBounce();
        sabtProgress.setIndeterminateDrawable(threeBounce);

        mapDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryIdSelected.equals("notset") || subCategory.equals("notset")) {
                    CookieBar.Builder c = CookieBar.build(getActivity());
                    c.setTitle(" لطفا ابتدا دسته بندی و زیر دسته بندی را انتخاب نمایید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                } else {
                    Intent intent = new Intent(myContext, SelectLocationActivity.class);
                    for (int mI = 0; mI < categoriesItems.size(); mI++) {
                        if (String.valueOf(categoriesItems.get(mI).getCategoryId()).equals(categoryIdSelected)) {
                            intent.putExtra("pin_image", categoriesItems.get(mI).getPinImage());
                            break;
                        }
                    }
                    startActivity(intent);
                }

            }
        });


    }

    public void getCategory() {
        if (getActivity().getApplicationContext() != null) {

            try {
                String categoryUrlReq = "https://kavinoo.com/api/categories";

                final StringRequest categoryDetailsReq = new StringRequest(Request.Method.GET, categoryUrlReq, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();
                        CategoriesResponse categoriesResponse = gson.fromJson(response, CategoriesResponse.class);

                        categoriesItems = categoriesResponse.getCategories();


                        categoryBottomSheetBuilder = new BottomSheet.Builder(myContext, R.style.BottomSheet);
                        categoryBottomSheetBuilder.setTitleColor(Color.parseColor("#367ab9"));
                        //categoryBottomSheetBuilder.setBackgroundColor(Color.parseColor("#367ab9"));
                        for (int mI = 0; mI < categoriesItems.size(); mI++) {
                            categoryBottomSheetBuilder.addItem(mI, categoriesItems.get(mI).getCategoryTitle());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AAAA", error.getMessage() + " ppppp");
                    }
                });
                categoryDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                App.getInstance().addToRequestQueue(categoryDetailsReq, "CATEGORYREQ");

            } catch (NullPointerException e) {

            }

        }


    }

    public void getSubCategory(String categoryId) {

        subCategory.setClickable(false);
        subCategory.setHint("در حال دریافت اطلاعات ...");

        String categoryUrlReq = "https://kavinoo.com/api/categories/"+categoryId;

        final StringRequest categoryDetailsReq = new StringRequest(Request.Method.GET, categoryUrlReq, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                subCategory.setClickable(true);
                subCategory.setHint("انتخاب زیر دسته بندی");
                Gson gson = new Gson();
                CategoriesResponse categoriesResponse = gson.fromJson(response, CategoriesResponse.class);

                subCategoriesItems = categoriesResponse.getCategories();

                subCategoryBottomSheetBuilder = new BottomSheet.Builder(myContext, R.style.BottomSheet);
                subCategoryBottomSheetBuilder.setTitleColor(Color.parseColor("#367ab9"));
                //categoryBottomSheetBuilder.setBackgroundColor(Color.parseColor("#367ab9"));
                for (int mI = 0; mI < subCategoriesItems.size(); mI++) {
                    subCategoryBottomSheetBuilder.addItem(mI, subCategoriesItems.get(mI).getCategoryTitle());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                subCategory.setClickable(true);
                subCategory.setHint("انتخاب زیر دسته بندی");

                CookieBar.Builder c = CookieBar.build(getActivity());
                c.setTitle(" لطفا اتصال اینترنت خود را بررسی فرمایید");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });
        categoryDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(categoryDetailsReq, "CATEGORYREQ");

    }

    /*public void getSubCategory(String categoryId) {
        if (getActivity().getApplicationContext() != null) {
            try {
                String categoryUrlReq = "https://kavinoo.com/api/categories/" + categoryId;

                final StringRequest categoryDetailsReq = new StringRequest(Request.Method.GET, categoryUrlReq, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();
                        CategoriesResponse categoriesResponse = gson.fromJson(response, CategoriesResponse.class);

                        subCategoriesItems = categoriesResponse.getCategories();

                        subCategoryBottomSheetBuilder = new BottomSheet.Builder(myContext, R.style.BottomSheet);
                        subCategoryBottomSheetBuilder.setTitleColor(Color.parseColor("#367ab9"));
                        //categoryBottomSheetBuilder.setBackgroundColor(Color.parseColor("#367ab9"));
                        for (int mI = 0; mI < subCategoriesItems.size(); mI++) {
                            subCategoryBottomSheetBuilder.addItem(mI, subCategoriesItems.get(mI).getCategoryTitle());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AAAA", error.getMessage() + " ppppp");
                    }
                });
                categoryDetailsReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                App.getInstance().addToRequestQueue(categoryDetailsReq, "CATEGORYREQ");

            } catch (NullPointerException e) {

            }

        }


    }
*/

    @Override
    public void onResume() {
        super.onResume();


        if (userInfoManager.getAddLon().equals("") && userInfoManager.getAddLat().equals("")) {
            Log.i("PPAASS", " is add on resss");
        } else {
            String urlCrk = "https://map.ir/static?width=700&height=400&markers=color:blue|label:" + "مکان انتخاب شده" + "|" + userInfoManager.getAddLon() + "," + userInfoManager.getAddLat() + "&zoom_level=16";

            Log.i("PPAASS", urlCrk);

            GlideUrl glideUrl = new GlideUrl(urlCrk,
                    new LazyHeaders.Builder()
                            .addHeader("x-api-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3In0.eyJhdWQiOiIxNjY2NCIsImp0aSI6ImFhNzlhZDAyMDZjM2JjMjVhYzA4MzE0ZTNlNGM2OGUwY2UwMWYzOGIxYzg5OGEzODEzMTllY2RhMWQwNzE0Yzg4MzdmZTU1ZTZhZDQyZWQ3IiwiaWF0IjoxNjQyMTIyNjk2LCJuYmYiOjE2NDIxMjI2OTYsImV4cCI6MTY0NDYyODI5Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.S6IMCplH_EasvLFTuSYXtMAIK1W1HOlAUYeYciNen1Q-JXmJs-326pziiw1m7RdWKv4naaaPGTiZk2ty0py5PlyHu6eEHKb8sM2ewKbqMcKjpL48O6WDRHw4ESZ3VjppvvSazHnPmxDOc5FtdjDcWKI4YJwXx1cArF8w4-FJNBjYf9YwTBeYlDWS7gS7SDXwl_XIUZK983ytr2gyY_T074d3Z4cqWtwkCQA3LddxxOy_b50BzP0v0aRTvd2nrF5p33K8syqOCDpSC_TrxdDxw2tEJQ4Hqi0LrZcwPaga6qskQyyboEMmBVJe2_npLMI7OacQd5NcedPMnAsXOGZyhw")
                            .build());

            Glide.with(getActivity())
                    .load(glideUrl)
                    .into(mapDemo);


            locationAdd.setEnabled(true);
            locationAdd.setTextColor(Color.parseColor("#647278"));
            locationAdd.setText(userInfoManager.getProvince() + " ، " + userInfoManager.getCounty());
            locationAdd.requestFocus();
            locationAdd.setSelection(locationAdd.getText().length());

            lastLat=userInfoManager.getAddLat();
            lastLon=userInfoManager.getAddLon();

            userInfoManager.saveAddLocationLat("");
            userInfoManager.saveAddLocationLon("");

        }


    }


    public void addMyFavoritePlace() {
        if (TokenContainer.getToken().equals("")) {
            showGotoRegister();
        } else {

            sabtGeneral.setVisibility(View.INVISIBLE);
            sabtProgress.setVisibility(View.VISIBLE);

            final StringRequest addRateReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_PLACE_GENERAL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    AddPlaceGeneralResponse addPlaceGeneralResponse = gson.fromJson(response, AddPlaceGeneralResponse.class);

                    Log.i("AADD",String.valueOf(addPlaceGeneralResponse.getData()));

                    addPlaceAddress(String.valueOf(addPlaceGeneralResponse.getData()));



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    sabtProgress.setVisibility(View.INVISIBLE);
                    sabtGeneral.setVisibility(View.VISIBLE);
                    CookieBar.Builder c = CookieBar.build(getActivity());
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
                    params.put("title", titlePlace.getText().toString());
                    params.put("category_id", subCategoryIdSelected);
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
            App.getInstance().addToRequestQueue(addRateReq, "ADDPLACEREQ");


        }

    }

    public void addPlaceAddress(final String idInserted){

        final StringRequest addRateReq = new StringRequest(Request.Method.POST, KavinooLinks.ADD_PLACE_ADDRESSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("AADD",response.toString());

                sabtProgress.setVisibility(View.INVISIBLE);
                sabtGeneral.setVisibility(View.VISIBLE);
                CookieBar.Builder c = CookieBar.build(getActivity());
                c.setTitle(" مکان محبوب شما با موفقیت ثبت شد");
                c.setSwipeToDismiss(true);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                addActivityScore("7","");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                sabtProgress.setVisibility(View.INVISIBLE);
                sabtGeneral.setVisibility(View.VISIBLE);
                CookieBar.Builder c = CookieBar.build(getActivity());
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
                params.put("id", idInserted);
                params.put("latitude", lastLat);
                params.put("longitude", lastLon);
                params.put("address", locationAdd.getText().toString()+"");
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
        App.getInstance().addToRequestQueue(addRateReq, "ADDPLACEREQ");

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


    public void showGotoRegister() {
        final Dialog dialog = new Dialog(myContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.to_register_from_add_location);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}