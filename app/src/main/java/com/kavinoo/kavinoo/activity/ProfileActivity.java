package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.utils.UserInfoManager;

public class ProfileActivity extends AppCompatActivity {


    UserInfoManager userInfoManager;
    String mobileUser;
    TextView mobileTextView;
    TextView nameText;
    TextView nameTitle;
    TextView mobileTitle;

    CardView menuToolbar;
    CardView backToolbar;

    ImageView reportCadr;
    ImageView favoriteCadr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mobileTextView=findViewById(R.id.mobile_text);
        nameText=findViewById(R.id.name_text);
        nameTitle=findViewById(R.id.name_title);
        mobileTitle=findViewById(R.id.mobile_title);

        menuToolbar=findViewById(R.id.menu_toolbar);
        backToolbar=findViewById(R.id.back_toolbar);

        reportCadr=findViewById(R.id.report_cadr);
        favoriteCadr=findViewById(R.id.favorite_cadr);

        userInfoManager=new UserInfoManager(ProfileActivity.this);

        mobileUser=userInfoManager.getMobile();

        if(mobileUser.equals("")){
            mobileTextView.setText("ثبت نام نشده");
            nameTitle.setVisibility(View.INVISIBLE);
            nameText.setVisibility(View.INVISIBLE);
        }else{
            mobileTextView.setText(mobileUser);
            nameText.setText(userInfoManager.getName());
        }


        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
        backToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reportCadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobileUser.equals("")){
                    showGotoRegister();
                }else{
                    Intent intent=new Intent(ProfileActivity.this,ReportActivity.class);
                    startActivity(intent);
                }

            }
        });
        favoriteCadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showGotoRegister() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.go_to_register_club_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView registerButtonDialog;

        registerButtonDialog = dialog.findViewById(R.id.register_button_dialog);

        registerButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}