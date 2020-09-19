package com.bossble.bossble.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.BuildConfig;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.R;
import com.facebook.login.LoginManager;

import androidx.appcompat.app.AppCompatActivity;

public class Settings_Activity extends AppCompatActivity {

    ImageView imgeditprofile,imgarrow,block_user,imgarrow2,imgreport,imgarrow3,imglegal,imgarrow4,imgsupport,imgarrow5,imgshare,imgarrow6,imgnotifications,imgarrow7,imglanguage,imgarrow8,imglogout,imgarrow9,imgdeactiveate,imgarrow10;
    TextView txteditprofile,txtblockuser,txtreport,txtlegal,txtsupport,txtshare,txtnotifications,txtlanguage,txtlogout,txtdeactivate,txtsettings;

    RelativeLayout r10,r11,r12,r13,r14,r15,r16,r17,r18,r19;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        imgeditprofile = findViewById(R.id.imgeditprofile);
        imgarrow = findViewById(R.id.imgarrow);
        block_user = findViewById(R.id.imgblockuser);
        imgarrow2 = findViewById(R.id.imgarrow2);
        imgreport = findViewById(R.id.imgreport);
        imgarrow3 = findViewById(R.id.imgarrow3);
        imglegal = findViewById(R.id.imglegal);
        imgarrow4 = findViewById(R.id.imgarrow4);
        imgsupport = findViewById(R.id.imgsupport);
        imgarrow5 = findViewById(R.id.imgarrow5);
        imgshare = findViewById(R.id.imgshare);
        imgarrow6 = findViewById(R.id.imgarrow6);
        imgnotifications = findViewById(R.id.imgnotifications);
        imgarrow7 = findViewById(R.id.imgarrow7);
        imglanguage = findViewById(R.id.imglanguage);
        imgarrow8 = findViewById(R.id.imgarrow8);
        imglogout = findViewById(R.id.imglogout);
        imgarrow9 = findViewById(R.id.imgarrow9);
        imgdeactiveate = findViewById(R.id.imgdeactiveate);
        imgarrow10 = findViewById(R.id.imgarrow10);
        txteditprofile = findViewById(R.id.txteditprofile);
        txtblockuser = findViewById(R.id.txtblockuser);
        txtreport = findViewById(R.id.txtreport);
        txtlegal = findViewById(R.id.txtlegal);
        txtsupport = findViewById(R.id.txtsupport);
        txtshare = findViewById(R.id.txtshare);
        txtnotifications = findViewById(R.id.txtnotifications);
        txtlanguage = findViewById(R.id.txtlanguage);
        txtlogout = findViewById(R.id.txtlogout);
        txtdeactivate = findViewById(R.id.txtdeativate);
        txtsettings = findViewById(R.id.txtsettings);
        r10 = findViewById(R.id.r10);
        r11 = findViewById(R.id.r11);
        r12 = findViewById(R.id.r12);
        r13 = findViewById(R.id.r13);
        r14 = findViewById(R.id.r14);
        r15 = findViewById(R.id.r15);
        r16 = findViewById(R.id.r16);
        r17 = findViewById(R.id.r17);
        r18 = findViewById(R.id.r18);
        r19 = findViewById(R.id.r19);

        Fonts();


        r10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,EditProfile_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        r11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,BlockedAccount_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        r12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,ReportUser_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        r13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,Legal_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        r14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,Support_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        r15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nCheckout Bossble it's exciting,\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        r16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,Notification_Setting_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        r18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String vdo = String.valueOf(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid202005080004));
                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                preferences.edit().remove(Constant.LOGGED_IN).commit();
                preferences.edit().remove(Constant.USER_ID).commit();
                preferences.edit().remove(Constant.EMAIL_ID).commit();
                preferences.edit().remove(Constant.PHONE).commit();
                preferences.edit().remove(Constant.PROFILE_PICTURE).commit();

                preferences.edit().remove("profile_name").commit();
                preferences.edit().remove("profile_country").commit();
                preferences.edit().remove("profile_bio").commit();
                preferences.edit().remove("profile_follow").commit();
                preferences.edit().remove("profile_follower").commit();
                preferences.edit().remove("profile_cover").commit();
                preferences.edit().remove("profile_image").commit();
                preferences.edit().remove("createdlist").commit();
                preferences.edit().remove("profile_indicator").commit();


                preferences.edit().remove("home_indicator").commit();
                preferences.edit().remove("campaignslist").commit();
                preferences.edit().remove("Clikeslist").commit();
                preferences.edit().remove("Ccommentslist").commit();
                preferences.edit().remove("Ctagslist").commit();
                preferences.edit().remove("Cusernamelist").commit();
                preferences.edit().remove("Cuserimagelist").commit();
                preferences.edit().remove("Ccnamelist").commit();
                preferences.edit().remove("Ccfontlist").commit();
                preferences.edit().remove("Cviews").commit();
                preferences.edit().remove("Ccimagelist").commit();
                preferences.edit().remove("Ccidlist").commit();
                preferences.edit().remove("Ccdescriptionlist").commit();
                preferences.edit().remove("nearbylist").commit();
                preferences.edit().remove("likeslist").commit();
                preferences.edit().remove("commentslist").commit();
                preferences.edit().remove("tagslist").commit();
                preferences.edit().remove("usernamelist").commit();
                preferences.edit().remove("userimagelist").commit();
                preferences.edit().remove("cnamelist").commit();
                preferences.edit().remove("cfontlist").commit();
                preferences.edit().remove("views").commit();
                preferences.edit().remove("cimagelist").commit();
                preferences.edit().remove("cidlist").commit();
                preferences.edit().remove("cdescriptionlist").commit();
                preferences.edit().remove("trendinglist").commit();

                LoginManager.getInstance().logOut();


                Intent intent = new Intent(Settings_Activity.this,New_OnBoardings_Activity.class);
                intent.putExtra("vdo",vdo);
                intent.putExtra("from","splash");
                startActivity(intent);
            }
        });

        r19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Activity.this,DeactivateAccount_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });




    }

    public void Fonts()
    {

        txteditprofile.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtblockuser.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtreport.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtlegal.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtsupport.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtshare.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtnotifications.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtlanguage.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtlogout.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtdeactivate.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtsettings.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
    }
}
