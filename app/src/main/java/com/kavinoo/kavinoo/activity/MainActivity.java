package com.kavinoo.kavinoo.activity;

import static com.kavinoo.kavinoo.activity.SearchPlaceActivity.RecordAudioRequestCode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kavinoo.kavinoo.FlashyTabLayoutsecond;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.eventbus.MessageEvent;
import com.kavinoo.kavinoo.fragment.AddLocationFragment;
import com.kavinoo.kavinoo.fragment.AroundMeFragment;
import com.kavinoo.kavinoo.fragment.CategoryFragment;
import com.kavinoo.kavinoo.fragment.ClubFragment;
import com.kavinoo.kavinoo.fragment.FavoriteFragment;
import com.kavinoo.kavinoo.fragment.HomeFragment;
import com.kavinoo.kavinoo.utils.UserInfoManager;

import org.aviran.cookiebar2.CookieBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final Integer RecordAudioRequestCodeCat = 101;

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FlashyTabLayoutsecond tabFlashyAnimator;
    private String[] titles = new String[]{"خانه", "دسته بندی","اطراف من", "افزودن مکان", "باشگاه" };

    ViewPager viewPager;

    LocationManager mLocationManager;
    Location myLocation;

    UserInfoManager userInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentList.add(new HomeFragment(titles[0]));
        mFragmentList.add(new CategoryFragment(titles[1]));
        mFragmentList.add(new AroundMeFragment(titles[2],MainActivity.this));
        mFragmentList.add(new AddLocationFragment(titles[3],MainActivity.this));
        mFragmentList.add(new ClubFragment(titles[4],MainActivity.this));

        viewPager = findViewById(R.id.view_pager);
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabFlashyAnimator = new FlashyTabLayoutsecond(tabLayout);

        int valueInPixels = (int) getResources().getDimension(R.dimen._47sdp);

        //float textTabSize=convertPixelsToDp(valueInPixels,MainActivity.this);
        float textTabSize= (float) (valueInPixels/3.4);

        tabFlashyAnimator.addTabItem(titles[0], R.drawable.homenav,textTabSize);
        tabFlashyAnimator.addTabItem(titles[1], R.drawable.catnav, textTabSize);
        tabFlashyAnimator.addTabItem(titles[2], R.drawable.aroundmebig, textTabSize);
        tabFlashyAnimator.addTabItem(titles[3], R.drawable.addlocnav, textTabSize);
        tabFlashyAnimator.addTabItem(titles[4], R.drawable.clubnav, textTabSize);

        viewPager.addOnPageChangeListener(tabFlashyAnimator);
        tabFlashyAnimator.highLightTab(0);

        userInfoManager=new UserInfoManager(MainActivity.this);
        userInfoManager.saveLocationLat("35.701087");
        userInfoManager.saveLocationLon("51.397326");

        checkPermission();

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (event.getMessage().equals("MORECLICKED")) {

            tabFlashyAnimator.highLightTab(1);
            viewPager.setCurrentItem(1);

        }

    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    @Override
    protected void onStart() {
        super.onStart();
        tabFlashyAnimator.onStart((TabLayout) findViewById(R.id.tabLayout));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        tabFlashyAnimator.onStop();
        EventBus.getDefault().unregister(this);
    }



    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        if (requestCode == RecordAudioRequestCodeCat && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        try {
            if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                getLoc();

            } else {

            }
        }catch (Exception e){

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
        final Dialog dialog = new Dialog(MainActivity.this);
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


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;




        CookieBar.Builder c = CookieBar.build(MainActivity.this);
        c.setTitle("برای خروج از اپلیکیشن دوبار روی دکمه بازگشت کلیک کنید");
        c.setSwipeToDismiss(true);
        c.setDuration(3500);
        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);
    }
}