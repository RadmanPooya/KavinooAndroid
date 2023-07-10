package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kavinoo.kavinoo.R;

import org.aviran.cookiebar2.CookieBar;

public class MenuActivity extends AppCompatActivity {


    ImageView enterToSite;
    ImageView emailToKavinoo;
    ImageView sendToFriends;
    ImageView company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        enterToSite=findViewById(R.id.enter_to_site);
        emailToKavinoo=findViewById(R.id.email_to_kavinoo);
        sendToFriends=findViewById(R.id.send_to_friends);
        company=findViewById(R.id.company);

        enterToSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse("https://kavinoo.com"));
                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooserIntent);
            }
        });

        emailToKavinoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@kavinoo.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "اپلیکیشن کاوینو");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    CookieBar.Builder c = CookieBar.build(MenuActivity.this);
                    c.setTitle("متاسفانه اپلیکیشنی برای ارسال ایمیل ندارید");
                    c.setSwipeToDismiss(true);
                    c.setCookiePosition(CookieBar.BOTTOM);
                    ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                }
            }
        });

        sendToFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "کاوینو بانک اطلاعات شهری و راهنمای گردشگری مبتنی بر نقشه است که به شما این امکان را میدهد براساس موقعیت ، آدرس نزدیکترین و بر اساس رای کاربران بهترین محل ها را جستجو و پیدا کنید. از لینک زیر میتوانید این اپلیکیشن را دانلود کنید : https://kavinoo.com");
                startActivity(intent);
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse("https://radmanpooya.com"));
                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooserIntent);
            }
        });

    }
}