package com.bossble.bossble.Details;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
/*
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
/*
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Adapter.CampaignAdapter;
import com.bossble.bossble.Adapter.Detail_Multiple_Images;
import com.bossble.bossble.Adapter.Multiple_ViewPager_Adapter;
import com.bossble.bossble.Adapter.PeopleJoinedAdapter;
import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Comments.Comments_Activity;
import com.bossble.bossble.Comments.Reply_Comments_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.People_joined;
import com.bossble.bossble.Model.Trending_Challenges;
import com.bossble.bossble.Notifications.Notification_Activity;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.PopupMenuFonts.CustomTypeFaceSpan;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cz.msebera.android.httpclient.Header;
import kotlin.jvm.internal.Ref;
import me.relex.circleindicator.CircleIndicator;

public class Details_Activity extends AppCompatActivity {

    LinearLayout likelayout,coinsreadinglayout;
    RoundedImageView coin;
    TextView like;

    RecyclerView peoplejoinedRV;
    ArrayList<People_joined> peoplejoinedimage = new ArrayList<>();
    PeopleJoinedAdapter peopleJoinedAdapter;

    RoundedImageView image;
    RelativeLayout opacitycover;
    ImageView bronze,silver,gold,back;
    ImageView maincoin,bronzereading,silverreading,goldreading;

    TextView username,usercountry,challengename,challengetxt,comment,views,peoplejoined,hashtags,description;
    ImageView options,userimage;
    Button join;
    ImageView reportblock;

    String uname,uimage,cimage="",likes,comments,ctitle,cdescription,ctags,ctype,useridd="",id="";
    String u_id="";
    ScrollView sv;

    TextView text;

    RelativeLayout relative;

    String challenge_id="",country="", view_count="";
    String comments_count="";
    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<String> imglist = new ArrayList<>();
    Detail_Multiple_Images adapter;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String user_like = "";
    String user_like_reward = "";

    String like_mode_type="";
    String challenge_title="";

    TextView thirdtxt,secondtxt,firsttxt;

    String cause="";

    RoundedImageView joined_user1,joined_user2,joined_user3;
    RelativeLayout medal1,medal2,medal3;
    TextView fst,scnd,thrd;
    LinearLayout linearjoin;
    String join_userid="";
    String joinindicator="";
    String notifications="";

    LinearLayout linearaccept;
    Button accept,reject;
    String joinuser="";
    String type="";
    String backintent="";

    boolean LOGGED_IN = false;

    String liked = "0";

    String vieww="yes";
    String backk="";

    LinearLayout innerlinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        u_id = pref.getString(Constant.USER_ID,"");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        innerlinear = findViewById(R.id.innerlinear);

        linearaccept = findViewById(R.id.linearaccept);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);

        linearjoin = findViewById(R.id.linearjoin);
        joined_user1 = findViewById(R.id.joined_user1);
        joined_user2 = findViewById(R.id.joined_user2);
        joined_user3 = findViewById(R.id.joined_user3);
        medal1 = findViewById(R.id.medal1);
        medal2 = findViewById(R.id.medal2);
        medal3 = findViewById(R.id.medal3);

        fst = findViewById(R.id.fst);
        scnd = findViewById(R.id.scnd);
        thrd = findViewById(R.id.thrd);

        thirdtxt = findViewById(R.id.thirdtxt);
        secondtxt = findViewById(R.id.secondtxt);
        firsttxt = findViewById(R.id.firsttxt);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        likelayout = findViewById(R.id.likelayout);
        coinsreadinglayout = findViewById(R.id.coinsreadinglayout);
        coin = findViewById(R.id.coin);
        like = findViewById(R.id.like);
        peoplejoinedRV = findViewById(R.id.peoplejoinedRV);
        image = findViewById(R.id.image);
        opacitycover = findViewById(R.id.opacitycover);
        bronze = findViewById(R.id.bronze);
        silver = findViewById(R.id.silver);
        gold = findViewById(R.id.gold);
        back = findViewById(R.id.back);
        options = findViewById(R.id.options);
        join = findViewById(R.id.join);
        reportblock = findViewById(R.id.reportblock);
        sv = findViewById(R.id.sv);
        text = findViewById(R.id.text);
        relative = findViewById(R.id.relative);
        username = findViewById(R.id.username);
        userimage = findViewById(R.id.userimage);
        usercountry = findViewById(R.id.usercountry);
        challengename = findViewById(R.id.challengename);
        challengetxt = findViewById(R.id.challengetxt);
        comment = findViewById(R.id.comment);
        views = findViewById(R.id.views);
        peoplejoined = findViewById(R.id.peoplejoined);
        hashtags = findViewById(R.id.hashtags);
        description = findViewById(R.id.description);
        maincoin = findViewById(R.id.maincoin);
        bronzereading = findViewById(R.id.bronzereading);
        silverreading = findViewById(R.id.silverreading);
        goldreading = findViewById(R.id.goldreading);
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);

        username.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        usercountry.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        challengename.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        challengetxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        like.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        comment.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        views.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        join.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        peoplejoined.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        hashtags.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        fst.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        scnd.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        thrd.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Details_Activity.this, 4);
        peoplejoinedRV.setLayoutManager(layoutManager);
        peoplejoinedRV.setNestedScrollingEnabled(false);
        peoplejoinedRV.setLayoutManager(layoutManager);
        peoplejoinedRV.setHasFixedSize(true);



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        viewPager.getLayoutParams().height = height/2;
        opacitycover.getLayoutParams().height = height/2;


        //Uri uri = getIntent().getData();
        if(getIntent().hasExtra("uri")){
/*            Uri uri = Uri.parse(getIntent().getStringExtra("uri"));

            List<String> params = uri.getPathSegments();
            String id = params.get(params.size()-1);
            Log.e("getIntentdetails",id);
            notifications = "yess";*/

            notifications = "yess";
            backk="yes";
            challenge_id = getIntent().getStringExtra("iddd");

            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
            LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
            if (LOGGED_IN) {
                u_id = preferences.getString(Constant.USER_ID,"");
            }
            else {
                u_id = "1";
                vieww="no";

                userimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                usercountry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                reportblock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                views.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                like.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                        return true;
                    }
                });

                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                medal1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                medal2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

                medal3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                });

            }

        }


        if (getIntent().hasExtra("notifications")){
            notifications = "yes";
            challenge_id = getIntent().getStringExtra("np_id");

            if (getIntent().hasExtra("accept")){

                linearjoin.setVisibility(View.GONE);

                linearaccept.setVisibility(View.VISIBLE);


            }
        }
        if (getIntent().hasExtra("id")){
            ctype=getIntent().getStringExtra("type");

            if (getIntent().hasExtra("back")){
                backintent = "yes";
            }

            if (ctype.equals("campaign")){

                join.setText(getResources().getString(R.string.jointhiscampaign));
                challengetxt.setText(getResources().getString(R.string.campaign));
                coin.setVisibility(View.GONE);

            }
            else if (ctype.equals("nearbychallenge")){

            }
            else{
                join.setText(getResources().getString(R.string.jointhischallenge));
            }
            uname = getIntent().getStringExtra("username");
            uimage = getIntent().getStringExtra("userimage");
            useridd = getIntent().getStringExtra("user_id");
            cimage = getIntent().getStringExtra("image");
            likes = getIntent().getStringExtra("likes");
            comments = getIntent().getStringExtra("comments");
            ctitle = getIntent().getStringExtra("title");
            cdescription = getIntent().getStringExtra("description");
            ctags = getIntent().getStringExtra("tags");
            id = getIntent().getStringExtra("id");
            challenge_id = getIntent().getStringExtra("id");
            country = getIntent().getStringExtra("country");
            view_count = getIntent().getStringExtra("view_count");

            like.setText(" "+likes);
            comment.setText(" "+comments);
            views.setText(" "+view_count);

            // already
            if (ctags.equals("") || ctags.isEmpty()){
                hashtags.setText(getResources().getString(R.string.NoHashtags));
            }
            else {
                hashtags.setText(ctags);
            }
            if (cdescription.equals("") || cdescription.isEmpty()){
                description.setText(getResources().getString(R.string.NoDescription));
            }
            else {
                description.setText(cdescription);
            }

            challengename.setText(ctitle);
            //already end

            usercountry.setText(country);


            if (uname.equals("") || uname.equals("null")){
                username.setText("N/A");
            }
            else {
                username.setText(uname);
            }
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.user_image_placeholder);
            Glide.with(Details_Activity.this)
                    .load(uimage)
                    .apply(options)
                    .into(userimage);

            if (cimage.contains(".mp4") || cimage.contains(".jpg") || cimage.contains(".jpeg")){
                RequestOptions options2 = new RequestOptions();
                options2.centerCrop();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(Details_Activity.this)
                        .load(cimage)
                        .apply(options2)
                        .into(image);
            }
            else {
                RequestOptions options2 = new RequestOptions();
                options2.centerCrop();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(Details_Activity.this)
                        .load("")
                        .apply(options2)
                        .into(image);
            }

            if (getIntent().hasExtra("join")){

                medal1.setVisibility(View.GONE);
                medal2.setVisibility(View.GONE);
                medal3.setVisibility(View.GONE);
                fst.setVisibility(View.GONE);
                scnd.setVisibility(View.GONE);
                thrd.setVisibility(View.GONE);
                peoplejoined.setVisibility(View.GONE);
                linearjoin.setVisibility(View.GONE);
                joinindicator="yes";

            }

        }

/*
        sdfsdf
        if (getIntent().hasExtra("viewonly")){
            linearjoin.setVisibility(View.GONE);
        }
*/

        if (ctype!=null && ctype.equals("campaign")){
            coin.setVisibility(View.GONE);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vieww.equals("no")){
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                    else {

                        if (Constant.isOnline(Details_Activity.this)){

                            like(id,"4");
                        }
                        else{
                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                        }


                    }
                }
            });

            like.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (vieww.equals("no")){
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }

                    return false;
                }
            });
        }
        else {

            like.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (vieww.equals("no")){
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                    else {
                        if (likelayout.getVisibility()==View.GONE){
                            likelayout.setAlpha(0f);
                            likelayout.setVisibility(View.VISIBLE);
                            likelayout.animate()
                                    .alpha(1f)
                                    .setDuration(500)
                                    .setListener(null);

                        }


                    }

                    return true;
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (vieww.equals("no")){
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                    else {

                        if (user_like_reward.equals("silver")){
                            if (Constant.isOnline(Details_Activity.this)){

                                like(id,"1");
                            }
                            else{
                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }


                        }
                        else if (user_like_reward.equals("gold")){
                            if (Constant.isOnline(Details_Activity.this)){

                                like(id,"2");
                            }
                            else{
                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }

                        }
                        else if (user_like_reward.equals("bronze")){
                            if (Constant.isOnline(Details_Activity.this)){

                                like(id,"3");

                            }
                            else{
                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }

                        }



                        if (likelayout.getVisibility()==View.VISIBLE){
                            Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                            out.setDuration(300);
                            likelayout.startAnimation(out);
                            likelayout.setVisibility(View.GONE);
                        }
                    }
                }
            });

        }




        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coinsreadinglayout.getVisibility()==View.GONE){
                    coinsreadinglayout.setAlpha(0f);
                    coinsreadinglayout.setVisibility(View.VISIBLE);
                    coinsreadinglayout.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .setListener(null);

                }
                else {

                    Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                    out.setDuration(300);
                    coinsreadinglayout.startAnimation(out);
                    coinsreadinglayout.setVisibility(View.GONE);


                }
            }
        });


        maincoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                coinsreadinglayout.startAnimation(out);
                coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        bronzereading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                coinsreadinglayout.startAnimation(out);
                coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        silverreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                coinsreadinglayout.startAnimation(out);
                coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        goldreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                coinsreadinglayout.startAnimation(out);
                coinsreadinglayout.setVisibility(View.GONE);
            }
        });
        bronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!user_like_reward.equals("bronze")){
                    if (Constant.isOnline(Details_Activity.this)){

                        like(id,"3");
                    }
                    else{
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }

                }

                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });


        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_like_reward.equals("silver")) {
                    if (Constant.isOnline(Details_Activity.this)){

                        like(id,"1");
                    }
                    else{
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }

                }
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });

        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_like_reward.equals("gold")) {

                    if (Constant.isOnline(Details_Activity.this)){

                        like(id,"2");
                    }
                    else{
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }


                }
                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details_Activity.this.onBackPressed();
            }
        });


        if (!joinindicator.equals("yes")){
            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
            String uidd = preferences.getString(Constant.USER_ID,"");
            if (type.equals("3") || type.equals("2")){

                if (joinuser!=null && joinuser.equals("yes")){
                    if (uidd.equals(useridd)){
                        //join.setVisibility(View.GONE);

                        join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                        join.setTextColor(getResources().getColor(R.color.transparent));

                    }
                    else {
                        //join.setVisibility(View.VISIBLE);
                        join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                        join.setTextColor(getResources().getColor(R.color.transparent));

                    }
                }
                else if (joinuser.equals("")){
                    //join.setVisibility(View.GONE);
                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                    join.setTextColor(getResources().getColor(R.color.transparent));

                }
            }
            else {
                if (uidd.equals(useridd)){
                    //join.setVisibility(View.GONE);
                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                    join.setTextColor(getResources().getColor(R.color.transparent));

                }
                else {
                    //join.setVisibility(View.VISIBLE);
                }
            }
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {

                    SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                    String uid = preferences.getString(Constant.USER_ID,"");

                    if (type.equals("3") || type.equals("2")){

                        if (joinuser!=null && joinuser.equals("yes")){
                            if (uid.equals(useridd)){
                                //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                            }
                            else {
                                if (Constant.isOnline(Details_Activity.this)){
                                    joinApi();
                                }
                                else{
                                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                }
                            }
                        }
                        else {
                            //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjointhischallenge));
                        }
                    }
                    else {
                        if (uid.equals(useridd)){
                            //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                        }
                        else {
                            if (Constant.isOnline(Details_Activity.this)){
                                joinApi();
                            }
                            else{
                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }
                        }
                    }
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                String uid = preferences.getString(Constant.USER_ID,"");
                if (uid.equals(useridd)){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                }
                else {
                    if (Constant.isOnline(Details_Activity.this)){
                        joinApi();
                    }
                    else{
                        Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }
                }

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Details_Activity.this.onBackPressed();

            }
        });


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.bossble.com/")
                        .path("/challengeDetails")
                        .appendQueryParameter("challengeId", challenge_id)
                        .fragment("section-name");

                String myUrl = builder.build().toString();


                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()



                        //.setLink(Uri.parse("https://www.bossble.com/"+challenge_id))
                        .setLink(Uri.parse(myUrl))
                        .setDomainUriPrefix("https://bossble.page.link")

                        .setAndroidParameters(
                                new DynamicLink.AndroidParameters.Builder("com.bossble.bossble")
                                        //.setMinimumVersion(125)
                                        .build())
                        .setIosParameters(
                                new DynamicLink.IosParameters.Builder("com.bossble.ios")
                                        .setAppStoreId("535886823")
                                        //.setMinimumVersion("1.0.1")
                                        .build())
                        .setSocialMetaTagParameters(
                                new DynamicLink.SocialMetaTagParameters.Builder()
                                        .setTitle("Bossble")
                                        .setDescription("Challenge the world!")
                                        .build())

                        .buildShortDynamicLink()
                        .addOnCompleteListener(Details_Activity.this, new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    Uri shortLink = task.getResult().getShortLink();
                                    Uri flowchartLink = task.getResult().getPreviewLink();
                                    try {
                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setType("text/plain");
                                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                        String shareMessage= "\nCheck out this post from Bossble,\n\n";
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage + shortLink);
                                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                                    } catch(Exception e) {
                                        //e.toString();
                                    }

                                }
                                else {
                                    Constant.ErrorToast(Details_Activity.this,"Unable to share");
                                }
                            }
                        });


/*
                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("http://www.bossble.com/"+challenge_id))
                        .setDomainUriPrefix("http://bossble.page.link")
                        .setAndroidParameters(
                                new DynamicLink.AndroidParameters.Builder("com.bossble.bossble")
                                        //.setMinimumVersion(125)
                                        .build())
                        .setIosParameters(
                                new DynamicLink.IosParameters.Builder("com.bossble.ios")
                                        //.setAppStoreId("123456789")
                                        //.setMinimumVersion("1.0.1")
                                        .build())
                        .setSocialMetaTagParameters(
                                new DynamicLink.SocialMetaTagParameters.Builder()
                                        .setTitle("Bossble")
                                        .setDescription("Challenge the world!")
                                        .build())
                        .buildDynamicLink();  // Or buildShortDynamicLink()

                Uri dynamicLinkUri = dynamicLink.getUri();

                try {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nCheck out this post from Bossble,\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage + dynamicLinkUri);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));

                } catch(Exception e) {
                    //e.toString();
                }
*/

            }
        });

        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {
                    if (u_id.equals(useridd))
                    {
                        Intent intent = new Intent(Details_Activity.this,Personal_Profile_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }
                    else
                    {

                        Intent intent = new Intent(Details_Activity.this,Profile_of_others_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }

                }
            }
        });


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {

                    if (u_id.equals(useridd))
                    {
                        Intent intent = new Intent(Details_Activity.this,Personal_Profile_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }
                    else
                    {

                        Intent intent = new Intent(Details_Activity.this,Profile_of_others_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }

                }
            }
        });

        usercountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {
                    if (u_id.equals(useridd))
                    {
                        Intent intent = new Intent(Details_Activity.this,Personal_Profile_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }
                    else
                    {

                        Intent intent = new Intent(Details_Activity.this,Profile_of_others_Activity.class);
                        intent.putExtra("user_id",useridd);
                        startActivity(intent);
                    }


                }
            }
        });






        reportblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {
                    PopupMenu popup = new PopupMenu(Details_Activity.this, reportblock);
                    popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {


                            if (item.getTitle().toString().equals("Report"))
                            {
                                if (Constant.isOnline(Details_Activity.this)){

                                    report_user();
                                }
                                else{
                                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                }

                            }
                            else if (item.getTitle().toString().equals("Block"))
                            {
                                if (Constant.isOnline(Details_Activity.this)){

                                    block_user();
                                }
                                else{
                                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                }
                            }

                            return true;
                        }
                    });

                    Menu menu = popup.getMenu();
                    for (int i = 0; i < menu.size(); i++) {
                        MenuItem mi = menu.getItem(i);
                        applyFontToMenuItem(mi);

                    }

                    popup.show(); //showing popup menu
                }
            }
        });


        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vieww.equals("no")){
                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {
                    Intent intent = new Intent(Details_Activity.this, Comments_Activity.class);
                    intent.putExtra("challenge_id",challenge_id);
                    startActivity(intent);
                }
            }
        });


        if (Constant.isOnline(Details_Activity.this)){

            detail();
        }
        else{
            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
        }


    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeFaceSpan("", font,getResources().getColor(R.color.dark_blue)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void like(String challengeid, final String type){

        final SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",uid);
        params.add("challenge_id",challengeid);
        params.add("type",type);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "like/like/add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("likeresp",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true")){

                        Constant.LikeSuccessToast(Details_Activity.this,getResources().getString(R.string.Liked));

                        preferences.edit().putString("liked","yes").commit();



                        if (!user_like.equals("1")){

                            String like_increase =String.valueOf(Integer.parseInt(likes)+1);
                            like.setText(" "+like_increase);
                            likes = like_increase;
                            user_like = "1";

                            if (type.equals("3")){

                                like.setTextColor(getResources().getColor(R.color.bronzecoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.bronzecoin)));

                            }
                            else if (type.equals("1")){

                                like.setTextColor(getResources().getColor(R.color.silvercoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.silvercoin)));

                            }
                            else if (type.equals("2")){

                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));

                            }
                            else if (type.equals("4")){
                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));

                            }


                        }
                        else {
                            if (like_mode_type.equals(type)){
                                like.setTextColor(getResources().getColor(R.color.white));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                            }
                            else {
                                if (type.equals("3")){

                                    like.setTextColor(getResources().getColor(R.color.bronzecoin));
                                    like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.bronzecoin)));
                                    like_mode_type = type;

                                }
                                else if (type.equals("1")){

                                    like.setTextColor(getResources().getColor(R.color.silvercoin));
                                    like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.silvercoin)));

                                    like_mode_type = type;
                                }
                                else if (type.equals("2")){

                                    like.setTextColor(getResources().getColor(R.color.goldcoin));
                                    like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));

                                    like_mode_type = type;
                                }
                                else if (type.equals("4")){
                                    like.setTextColor(getResources().getColor(R.color.goldcoin));
                                    like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));

                                }


                            }

                        }

                        detail3();
                    }
                    else if (status.equals("false")){

                        preferences.edit().putString("liked","yes").commit();

                        like.setTextColor(getResources().getColor(R.color.white));
                        like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                        String like_increase =String.valueOf(Integer.parseInt(likes)-1);
                        like.setText(" "+like_increase);
                        likes = like_increase;
                        user_like = "0";

                        detail3();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("likerespF",response);

                }
                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });

    }

    public void detail()
    {
        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
        params.add("user_id",u_id);
        if (!joinindicator.equals("")){
            params.add("flag","1");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/get_challege_detail?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsedetail",response);
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");


                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        ctitle = dataSet.getString("title");
                        cdescription = dataSet.getString("descritpion");
                        ctags = dataSet.getString("tags");

                        challengename.setText(ctitle);

                        if (ctags.equals("") || ctags.isEmpty()){
                            hashtags.setText(getResources().getString(R.string.NoHashtags));
                        }
                        else {
                            hashtags.setText(ctags);
                        }
                        if (cdescription.equals("") || cdescription.isEmpty()){
                            description.setText(getResources().getString(R.string.NoDescription));
                        }
                        else {
                            description.setText(cdescription);
                        }

                        type = dataSet.getString("challenge_type");


                        if (notifications.equals("yes") || notifications.equals("yess")){

                            if (type.equals("5")){

                                join.setText(getResources().getString(R.string.jointhiscampaign));
                                challengetxt.setText(getResources().getString(R.string.campaign));
                                coin.setVisibility(View.GONE);
                                ctype="campaign";

                            }
                            else{
                                join.setText(getResources().getString(R.string.jointhischallenge));
                                ctype="challenge";

                            }
                            uname = dataSet.getString("username");
                            uimage = dataSet.getString("profile_image");
                            useridd = dataSet.getString("user_id");
                            id = dataSet.getString("challenge_id");
                            challenge_id = dataSet.getString("challenge_id");
                            country = dataSet.getString("country");
                            view_count = dataSet.getString("view_count");

                            likes = dataSet.getString("like_count");


                            if (uname.equals("") || uname.equals("null")){
                                username.setText("N/A");
                            }
                            else {
                                username.setText(uname);
                            }

                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            options.placeholder(R.drawable.user_image_placeholder);
                            Glide.with(Details_Activity.this)
                                    .load(uimage)
                                    .apply(options)
                                    .into(userimage);

                            usercountry.setText(country);

                            views.setText(" "+view_count);

                            if (ctype!=null && ctype.equals("campaign")){
                                coin.setVisibility(View.GONE);
                                like.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (vieww.equals("no")){
                                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                        }
                                        else {
                                            like(id,"4");

                                        }
                                    }
                                });

                                like.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
                                        if (vieww.equals("no")){
                                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                        }

                                        return false;
                                    }
                                });
                            }
                            else {

                                like.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {

                                        if (vieww.equals("no")){
                                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                        }
                                        else {
                                            if (likelayout.getVisibility()==View.GONE){
                                                likelayout.setAlpha(0f);
                                                likelayout.setVisibility(View.VISIBLE);
                                                likelayout.animate()
                                                        .alpha(1f)
                                                        .setDuration(500)
                                                        .setListener(null);

                                            }
                                        }
                                        return true;
                                    }
                                });

                                like.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (vieww.equals("no")){
                                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                        }
                                        else {
                                            if (user_like_reward.equals("silver")){
                                                like(id,"1");

                                            }
                                            else if (user_like_reward.equals("gold")){
                                                like(id,"2");
                                            }
                                            else if (user_like_reward.equals("bronze")){
                                                like(id,"3");
                                            }



                                            if (likelayout.getVisibility()==View.VISIBLE){
                                                Animation out = AnimationUtils.makeOutAnimation(Details_Activity.this, true);
                                                out.setDuration(300);
                                                likelayout.startAnimation(out);
                                                likelayout.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                });

                            }


                        }


                        if (dataSet.has("challeneg_join_member") && dataSet.get("challeneg_join_member") instanceof JSONArray){

                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                            String myid = preferences.getString(Constant.USER_ID,"");

                            JSONArray challeneg_join_member = dataSet.getJSONArray("challeneg_join_member");

                            if (challeneg_join_member.length()>0){

                                for (int k=0; k<challeneg_join_member.length(); k++){

                                    JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                    String join_id = join_item.getString("id");
                                    if (join_id.equals(myid)){

                                        joinuser = "yes";
                                        break;
                                    }
                                }
                            }
                        }


                        comments_count = dataSet.getString("comments_count");
                        comment.setText(" "+comments_count);

                        if (dataSet.has("all_images")){
                            JSONArray all_images = dataSet.getJSONArray("all_images");
                            if (all_images.length()>0){
                                for (int i=0; i<all_images.length(); i++){
                                    String img = all_images.getString(i);
                                    imglist.add(img);

                                }

                                adapter = new Detail_Multiple_Images(Details_Activity.this,imglist,Details_Activity.this,cimage,uname,uimage,likes,comments_count,ctitle,ctype,id,country,view_count,vieww);
                                viewPager.setAdapter(adapter);
                                indicator.setViewPager(viewPager);

                            }
                        }
                        else {
                            String video = dataSet.getString("video");
                            imglist.add(video);
                            adapter = new Detail_Multiple_Images(Details_Activity.this,imglist,Details_Activity.this,cimage,uname,uimage,likes,comments_count,ctitle,ctype,id,country,view_count,vieww);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                        }

                        if (imglist.size()==1){
                            indicator.setVisibility(View.GONE);
                        }


                        user_like = dataSet.getString("user_like");
                        user_like_reward = dataSet.getString("user_like_reward");
                        challenge_title = dataSet.getString("challenge_title");

                        if (challenge_title.equals("Location Base Challenge")){
                            challengetxt.setVisibility(View.VISIBLE);
                            String address = dataSet.getString("address");

                            if (address.equals("null") || address.equals("") || address.isEmpty()){
                                challengetxt.setText("N/A");
                            }
                            else {
                                challengetxt.setText(address);
                            }
                        }
                        else if (challenge_title.equals("Create Campaign")){
                            challengetxt.setVisibility(View.GONE);
                            join.setText(getResources().getString(R.string.jointhiscampaign));

                        }
                        else {
                            challengetxt.setVisibility(View.GONE);
                        }

                        String like_count = dataSet.getString("like_count");
                        like.setText(" "+like_count);

                        if(user_like.equals("1")){
                            if (user_like_reward.equals("bronze")){

                                like.setTextColor(getResources().getColor(R.color.bronzecoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.bronzecoin)));
                                like_mode_type = "3";
                            }
                            else if (user_like_reward.equals("silver")){
                                like.setTextColor(getResources().getColor(R.color.silvercoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.silvercoin)));
                                like_mode_type ="1" ;
                            }
                            else if (user_like_reward.equals("gold")){
                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));
                                like_mode_type = "2";
                            }
                            else if (user_like_reward.equals("campaign_like")){
                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));
                                like_mode_type ="4" ;
                            }

                        }
                        else {
                            like.setTextColor(getResources().getColor(R.color.white));
                            like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        }
                        JSONObject like_type_count = dataSet.getJSONObject("like_type_count");
                        String silver = like_type_count.getString("silver");
                        String gold = like_type_count.getString("gold");
                        String bronze = like_type_count.getString("bronze");

                        setCoins(silver,gold,bronze);


                        if (dataSet.get("challenge_reply") instanceof JSONArray){

                            JSONArray challenge_reply = dataSet.getJSONArray("challenge_reply");
                            if (challenge_reply.length()>0) {

                                String count = String.valueOf(challenge_reply.length());
                                peoplejoined.setText(count + " PEOPLE JOINED");

                                if (challenge_reply.length() == 1) {

                                    JSONObject item = challenge_reply.getJSONObject(0);
                                    final String profile_image = item.getString("profile_image");
                                    final String id = item.getString("id");
                                    final String user_id = item.getString("user_id");
                                    final String username = item.getString("username");
                                    final String like = item.getString("like_count");
                                    final String comments_count = item.getString("comments_count");
                                    final String title = ctitle;
                                    final String description = cdescription;
                                    final String tags = ctags;
                                    final String country = item.getString("country");
                                    final String view_count = "0";
                                    final String challenge_title = item.getString("challenge_title");


                                    medal1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Signintoexplore));
                                            } else {
                                                Intent intent = new Intent(Details_Activity.this, Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id", id);
                                                intent.putExtra("username", username);
                                                intent.putExtra("userimage", profile_image);
                                                intent.putExtra("user_id", user_id);
                                                intent.putExtra("image", "");
                                                intent.putExtra("likes", like);
                                                intent.putExtra("comments", comments_count);
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", description);
                                                intent.putExtra("tags", tags);
                                                if (challenge_title.equals("Create Campaign")) {
                                                    intent.putExtra("type", "campaign");
                                                } else {
                                                    intent.putExtra("type", "challenge");
                                                }
                                                intent.putExtra("country", country);
                                                intent.putExtra("view_count", view_count);
                                                intent.putExtra("join", "join");

                                                startActivity(intent);

                                            }
                                        }
                                    });


                                    //join gone
                                    if (!joinindicator.equals("yes")){
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                        String uidd = preferences.getString(Constant.USER_ID, "");
                                        if (type.equals("3") || type.equals("2")) {

                                            if (joinuser != null && joinuser.equals("yes")) {
                                                if (uidd.equals(useridd)) {
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                } else if (uidd.equals(user_id)) {
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                } else {
                                                    //join.setVisibility(View.VISIBLE);
                                                }
                                            } else if (joinuser.equals("")){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                        } else {

                                            if (uidd.equals(useridd)) {
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            } else if (uidd.equals(user_id)) {
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            } else {
                                                //join.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    }



                                    join.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            if (vieww.equals("no")) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Signintoexplore));
                                            } else {

                                                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                                String uid = preferences.getString(Constant.USER_ID, "");

                                                if (type.equals("3") || type.equals("2")) {

                                                    if (joinuser != null && joinuser.equals("yes")) {
                                                        if (uid.equals(useridd)) {
                                                            //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                        } else if (uid.equals(user_id)) {
                                                            //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                                        } else {
                                                            if (Constant.isOnline(Details_Activity.this)) {
                                                                joinApi();
                                                            } else {
                                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                                            }
                                                        }
                                                    } else {
                                                        //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjointhischallenge));
                                                    }
                                                } else {

                                                    if (uid.equals(useridd)) {
                                                        //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                    } else if (uid.equals(user_id)) {
                                                        //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                                    } else {
                                                        if (Constant.isOnline(Details_Activity.this)) {
                                                            joinApi();
                                                        } else {
                                                            Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    accept.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                            String uid = preferences.getString(Constant.USER_ID, "");
                                            if (uid.equals(useridd)) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                            } else if (uid.equals(user_id)) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                            } else {
                                                if (Constant.isOnline(Details_Activity.this)) {
                                                    joinApi();
                                                } else {
                                                    Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                                }
                                            }

                                        }
                                    });


                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.bossble_placeholder_large);
                                    Glide.with(Details_Activity.this)
                                            .load(profile_image)
                                            .apply(options2)
                                            .into(joined_user1);

                                    joined_user2.setVisibility(View.GONE);
                                    medal2.setVisibility(View.GONE);
                                    scnd.setVisibility(View.GONE);
                                    joined_user3.setVisibility(View.GONE);
                                    medal3.setVisibility(View.GONE);
                                    thrd.setVisibility(View.GONE);


                                } else if (challenge_reply.length() == 2) {

                                    JSONObject item1 = challenge_reply.getJSONObject(0);
                                    final String profile_image1 = item1.getString("profile_image");
                                    final String id1 = item1.getString("id");
                                    final String user_id1 = item1.getString("user_id");
                                    final String username1 = item1.getString("username");
                                    final String like1 = item1.getString("like_count");
                                    final String comments_count1 = item1.getString("comments_count");
                                    final String title1 = ctitle;
                                    final String description1 = cdescription;
                                    final String tags1 = ctags;
                                    final String country1 = item1.getString("country");
                                    final String view_count1 = "0";
                                    final String challenge_title1 = item1.getString("challenge_title");

                                    JSONObject item2 = challenge_reply.getJSONObject(1);
                                    final String profile_image2 = item2.getString("profile_image");
                                    final String id2 = item2.getString("id");
                                    final String user_id2 = item2.getString("user_id");
                                    final String username2 = item2.getString("username");
                                    final String like2 = item2.getString("like_count");
                                    final String comments_count2 = item2.getString("comments_count");
                                    final String title2 = ctitle;
                                    final String description2 = cdescription;
                                    final String tags2 = ctags;
                                    final String country2 = item2.getString("country");
                                    final String view_count2 = "0";
                                    final String challenge_title2 = item2.getString("challenge_title");

                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.bossble_placeholder_large);
                                    Glide.with(Details_Activity.this)
                                            .load(profile_image1)
                                            .apply(options2)
                                            .into(joined_user1);

                                    Glide.with(Details_Activity.this)
                                            .load(profile_image2)
                                            .apply(options2)
                                            .into(joined_user2);

                                    joined_user3.setVisibility(View.GONE);
                                    medal3.setVisibility(View.GONE);
                                    thrd.setVisibility(View.GONE);

                                    medal1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Signintoexplore));
                                            } else {
                                                Intent intent = new Intent(Details_Activity.this, Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id", id1);
                                                intent.putExtra("username", username1);
                                                intent.putExtra("userimage", profile_image1);
                                                intent.putExtra("user_id", user_id1);
                                                intent.putExtra("image", "");
                                                intent.putExtra("likes", like1);
                                                intent.putExtra("comments", comments_count1);
                                                intent.putExtra("title", title1);
                                                intent.putExtra("description", description1);
                                                intent.putExtra("tags", tags1);
                                                if (challenge_title1.equals("Create Campaign")) {
                                                    intent.putExtra("type", "campaign");
                                                } else {
                                                    intent.putExtra("type", "challenge");
                                                }
                                                intent.putExtra("country", country1);
                                                intent.putExtra("view_count", view_count1);
                                                intent.putExtra("join", "join");

                                                startActivity(intent);

                                            }


                                        }
                                    });

                                    medal2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")) {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Signintoexplore));
                                            } else {
                                                Intent intent = new Intent(Details_Activity.this, Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id", id2);
                                                intent.putExtra("username", username2);
                                                intent.putExtra("userimage", profile_image2);
                                                intent.putExtra("user_id", user_id2);
                                                intent.putExtra("image", "");
                                                intent.putExtra("likes", like2);
                                                intent.putExtra("comments", comments_count2);
                                                intent.putExtra("title", title2);
                                                intent.putExtra("description", description2);
                                                intent.putExtra("tags", tags2);
                                                if (challenge_title2.equals("Create Campaign")) {
                                                    intent.putExtra("type", "campaign");
                                                } else {
                                                    intent.putExtra("type", "challenge");
                                                }
                                                intent.putExtra("country", country2);
                                                intent.putExtra("view_count", view_count2);
                                                intent.putExtra("join", "join");

                                                startActivity(intent);

                                            }
                                        }
                                    });

                                    //join visibility gone
                                    if (!joinindicator.equals("yes")){
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                        String uiddd = preferences.getString(Constant.USER_ID, "");
                                        if (type.equals("3") || type.equals("2")) {

                                            if (joinuser != null && joinuser.equals("yes")) {
                                                if (uiddd.equals(useridd)) {
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                } else if (uiddd.equals(user_id1) || uiddd.equals(user_id2)) {
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                } else {
                                                    //join.setVisibility(View.VISIBLE);
                                                }
                                            } else if (joinuser.equals("")){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                        } else {
                                            if (uiddd.equals(useridd)) {
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            } else if (uiddd.equals(user_id1) || uiddd.equals(user_id2)) {
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            } else {
                                                //join.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }



                                join.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (vieww.equals("no")) {
                                            Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Signintoexplore));
                                        } else {

                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                            String uid = preferences.getString(Constant.USER_ID, "");

                                            if (type.equals("3") || type.equals("2")) {

                                                if (joinuser != null && joinuser.equals("yes")) {
                                                    if (uid.equals(useridd)) {
                                                        //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                    } else if (uid.equals(user_id1) || uid.equals(user_id2)) {
                                                        //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                                    } else {
                                                        if (Constant.isOnline(Details_Activity.this)) {
                                                            joinApi();
                                                        } else {
                                                            Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                                        }
                                                    }
                                                } else {
                                                    //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjointhischallenge));
                                                }
                                            } else {
                                                if (uid.equals(useridd)) {
                                                    //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                } else if (uid.equals(user_id1) || uid.equals(user_id2)) {
                                                    //Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                                } else {
                                                    if (Constant.isOnline(Details_Activity.this)) {
                                                        joinApi();
                                                    } else {
                                                        Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });

                                accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                        String uid = preferences.getString(Constant.USER_ID, "");
                                        if (uid.equals(useridd)) {
                                            Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                        } else if (uid.equals(user_id1) || uid.equals(user_id2)) {
                                            Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.AlreadyAttempted));
                                        } else {
                                            if (Constant.isOnline(Details_Activity.this)) {
                                                joinApi();
                                            } else {
                                                Constant.ErrorToast(Details_Activity.this, getResources().getString(R.string.NoInternetConnection));
                                            }
                                        }

                                    }
                                });

                                }

                                else if (challenge_reply.length()==3){

                                    JSONObject item1 = challenge_reply.getJSONObject(0);
                                    final String profile_image1 = item1.getString("profile_image");
                                    final String id1 = item1.getString("id");
                                    final String user_id1 = item1.getString("user_id");
                                    final String username1 = item1.getString("username");
                                    final String like1 = item1.getString("like_count");
                                    final String comments_count1 = item1.getString("comments_count");
                                    final String title1 = ctitle;
                                    final String description1 = cdescription;
                                    final String tags1 = ctags;
                                    final String country1 = item1.getString("country");
                                    final String view_count1 = "0";
                                    final String challenge_title1 = item1.getString("challenge_title");

                                    JSONObject item2 = challenge_reply.getJSONObject(1);
                                    final String profile_image2 = item2.getString("profile_image");
                                    final String id2 = item2.getString("id");
                                    final String user_id2 = item2.getString("user_id");
                                    final String username2 = item2.getString("username");
                                    final String like2 = item2.getString("like_count");
                                    final String comments_count2 = item2.getString("comments_count");
                                    final String title2 = ctitle;
                                    final String description2 = cdescription;
                                    final String tags2 = ctags;
                                    final String country2 = item2.getString("country");
                                    final String view_count2 = "0";
                                    final String challenge_title2 = item2.getString("challenge_title");

                                    JSONObject item3 = challenge_reply.getJSONObject(2);
                                    final String profile_image3 = item3.getString("profile_image");
                                    final String id3 = item3.getString("id");
                                    final String user_id3 = item3.getString("user_id");
                                    final String username3 = item3.getString("username");
                                    final String like3 = item3.getString("like_count");
                                    final String comments_count3 = item3.getString("comments_count");
                                    final String title3 = ctitle;
                                    final String description3 = cdescription;
                                    final String tags3 = ctags;
                                    final String country3 = item3.getString("country");
                                    final String view_count3 = "0";
                                    final String challenge_title3 = item3.getString("challenge_title");


                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.bossble_placeholder_large);
                                    Glide.with(Details_Activity.this)
                                            .load(profile_image1)
                                            .apply(options2)
                                            .into(joined_user1);

                                    Glide.with(Details_Activity.this)
                                            .load(profile_image2)
                                            .apply(options2)
                                            .into(joined_user2);

                                    Glide.with(Details_Activity.this)
                                            .load(profile_image3)
                                            .apply(options2)
                                            .into(joined_user3);


                                    medal1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id1);
                                                intent.putExtra("username",username1);
                                                intent.putExtra("userimage",profile_image1);
                                                intent.putExtra("user_id",user_id1);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like1);
                                                intent.putExtra("comments",comments_count1);
                                                intent.putExtra("title",title1);
                                                intent.putExtra("description",description1);
                                                intent.putExtra("tags",tags1);
                                                if (challenge_title1.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country1);
                                                intent.putExtra("view_count",view_count1);
                                                intent.putExtra("join","join");

                                                startActivity(intent);

                                            }


                                        }
                                    });

                                    medal2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id2);
                                                intent.putExtra("username",username2);
                                                intent.putExtra("userimage",profile_image2);
                                                intent.putExtra("user_id",user_id2);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like2);
                                                intent.putExtra("comments",comments_count2);
                                                intent.putExtra("title",title2);
                                                intent.putExtra("description",description2);
                                                intent.putExtra("tags",tags2);
                                                if (challenge_title2.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country2);
                                                intent.putExtra("view_count",view_count2);
                                                intent.putExtra("join","join");

                                                startActivity(intent);

                                            }
                                        }
                                    });

                                    medal3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id3);
                                                intent.putExtra("username",username3);
                                                intent.putExtra("userimage",profile_image3);
                                                intent.putExtra("user_id",user_id3);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like3);
                                                intent.putExtra("comments",comments_count3);
                                                intent.putExtra("title",title3);
                                                intent.putExtra("description",description3);
                                                intent.putExtra("tags",tags3);
                                                if (challenge_title3.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country3);
                                                intent.putExtra("view_count",view_count3);
                                                intent.putExtra("join","join");

                                                startActivity(intent);

                                            }


                                        }
                                    });

                                    //join visibility gone
                                    if (!joinindicator.equals("yes")){
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String uid = preferences.getString(Constant.USER_ID,"");

                                        if (type.equals("3") || type.equals("2")){

                                            if (joinuser!=null && joinuser.equals("yes")){
                                                if (uid.equals(useridd)){
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                }
                                                else if(uid.equals(user_id1) || uid.equals(user_id2) || uid.equals(user_id3)){
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                }
                                                else {
                                                    //join.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            else if (joinuser.equals("")){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                        }
                                        else {
                                            if (uid.equals(useridd)){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                            else if(uid.equals(user_id1) || uid.equals(user_id2) || uid.equals(user_id3)){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                            else{
                                                //join.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    }


                                    join.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {

                                                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                                String uid = preferences.getString(Constant.USER_ID,"");

                                                if (type.equals("3") || type.equals("2")){

                                                    if (joinuser!=null && joinuser.equals("yes")){
                                                        if (uid.equals(useridd)){
                                                            //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                        }
                                                        else if(uid.equals(user_id1) || uid.equals(user_id2) || uid.equals(user_id3)){
                                                            //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.AlreadyAttempted));
                                                        }
                                                        else {
                                                            if (Constant.isOnline(Details_Activity.this)){
                                                                joinApi();
                                                            }
                                                            else{
                                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                                            }
                                                        }
                                                    }
                                                    else {
                                                        //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjointhischallenge));
                                                    }
                                                }
                                                else {
                                                    if (uid.equals(useridd)){
                                                        //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                                    }
                                                    else if(uid.equals(user_id1) || uid.equals(user_id2) || uid.equals(user_id3)){
                                                        //Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.AlreadyAttempted));
                                                    }
                                                    else{
                                                        if (Constant.isOnline(Details_Activity.this)){
                                                            joinApi();
                                                        }
                                                        else{
                                                            Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    accept.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                            String uid = preferences.getString(Constant.USER_ID,"");
                                            if (uid.equals(useridd)){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                            }
                                            else if(uid.equals(user_id1) || uid.equals(user_id2) || uid.equals(user_id3)){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.AlreadyAttempted));
                                            }
                                            else{
                                                if (Constant.isOnline(Details_Activity.this)){
                                                    joinApi();
                                                }
                                                else{
                                                    Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                                }
                                            }

                                        }
                                    });



                                }
                                else if (challenge_reply.length()>3){
                                    JSONObject item1 = challenge_reply.getJSONObject(0);
                                    final String profile_image1 = item1.getString("profile_image");
                                    final String id1 = item1.getString("id");
                                    final String user_id1 = item1.getString("user_id");
                                    final String username1 = item1.getString("username");
                                    final String like1 = item1.getString("like_count");
                                    final String comments_count1 = item1.getString("comments_count");
                                    final String title1 = ctitle;
                                    final String description1 = cdescription;
                                    final String tags1 = ctags;
                                    final String country1 = item1.getString("country");
                                    final String view_count1 = "0";
                                    final String challenge_title1 = item1.getString("challenge_title");

                                    JSONObject item2 = challenge_reply.getJSONObject(1);
                                    final String profile_image2 = item2.getString("profile_image");
                                    final String id2 = item2.getString("id");
                                    final String user_id2 = item2.getString("user_id");
                                    final String username2 = item2.getString("username");
                                    final String like2 = item2.getString("like_count");
                                    final String comments_count2 = item2.getString("comments_count");
                                    final String title2 = ctitle;
                                    final String description2 = cdescription;
                                    final String tags2 = ctags;
                                    final String country2 = item2.getString("country");
                                    final String view_count2 = "0";
                                    final String challenge_title2 = item2.getString("challenge_title");

                                    JSONObject item3 = challenge_reply.getJSONObject(2);
                                    final String profile_image3 = item3.getString("profile_image");
                                    final String id3 = item3.getString("id");
                                    final String user_id3 = item3.getString("user_id");
                                    final String username3 = item3.getString("username");
                                    final String like3 = item3.getString("like_count");
                                    final String comments_count3 = item3.getString("comments_count");
                                    final String title3 = ctitle;
                                    final String description3 = cdescription;
                                    final String tags3 = ctags;
                                    final String country3 = item3.getString("country");
                                    final String view_count3 = "0";
                                    final String challenge_title3 = item3.getString("challenge_title");


                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.bossble_placeholder_large);
                                    Glide.with(Details_Activity.this)
                                            .load(profile_image1)
                                            .apply(options2)
                                            .into(joined_user1);

                                    Glide.with(Details_Activity.this)
                                            .load(profile_image2)
                                            .apply(options2)
                                            .into(joined_user2);

                                    Glide.with(Details_Activity.this)
                                            .load(profile_image3)
                                            .apply(options2)
                                            .into(joined_user3);


                                    medal1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id1);
                                                intent.putExtra("username",username1);
                                                intent.putExtra("userimage",profile_image1);
                                                intent.putExtra("user_id",user_id1);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like1);
                                                intent.putExtra("comments",comments_count1);
                                                intent.putExtra("title",title1);
                                                intent.putExtra("description",description1);
                                                intent.putExtra("tags",tags1);
                                                if (challenge_title1.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country1);
                                                intent.putExtra("view_count",view_count1);
                                                intent.putExtra("join","join");
                                                startActivity(intent);

                                            }
                                        }
                                    });

                                    medal2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id2);
                                                intent.putExtra("username",username2);
                                                intent.putExtra("userimage",profile_image2);
                                                intent.putExtra("user_id",user_id2);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like2);
                                                intent.putExtra("comments",comments_count2);
                                                intent.putExtra("title",title2);
                                                intent.putExtra("description",description2);
                                                intent.putExtra("tags",tags2);
                                                if (challenge_title2.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country2);
                                                intent.putExtra("view_count",view_count2);
                                                intent.putExtra("join","join");

                                                startActivity(intent);

                                            }
                                        }
                                    });

                                    medal3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (vieww.equals("no")){
                                                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Signintoexplore));
                                            }
                                            else {
                                                Intent intent = new Intent(Details_Activity.this,Details_Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("id",id3);
                                                intent.putExtra("username",username3);
                                                intent.putExtra("userimage",profile_image3);
                                                intent.putExtra("user_id",user_id3);
                                                intent.putExtra("image","");
                                                intent.putExtra("likes",like3);
                                                intent.putExtra("comments",comments_count3);
                                                intent.putExtra("title",title3);
                                                intent.putExtra("description",description3);
                                                intent.putExtra("tags",tags3);
                                                if (challenge_title3.equals("Create Campaign")){
                                                    intent.putExtra("type","campaign");
                                                }
                                                else {
                                                    intent.putExtra("type","challenge");
                                                }
                                                intent.putExtra("country",country3);
                                                intent.putExtra("view_count",view_count3);
                                                intent.putExtra("join","join");
                                                startActivity(intent);

                                            }

                                        }
                                    });




                                    for (int i=3; i<challenge_reply.length(); i++){

                                        People_joined people_joined = new People_joined();

                                        JSONObject item = challenge_reply.getJSONObject(i);
                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String username = item.getString("username");
                                        String profile_image = item.getString("profile_image");
                                        String like = item.getString("like_count");
                                        String comments_count = item.getString("comments_count");
                                        //String title = item.getString("title");
                                        //String description = item.getString("description");
                                        //String tags = item.getString("tags");
                                        String country = item.getString("country");
                                        String view_count = "0";
                                        String challenge_title = item.getString("challenge_title");

                                        people_joined.setId(id);
                                        people_joined.setUser_id(user_id);
                                        people_joined.setUsername(username);
                                        people_joined.setProfile_image(profile_image);
                                        people_joined.setLike_count(like);
                                        people_joined.setComments_count(comments_count);
                                        people_joined.setTitle(ctitle);
                                        people_joined.setDescription(cdescription);
                                        people_joined.setTags(ctags);
                                        people_joined.setCountry(country);
                                        people_joined.setView_count(view_count);
                                        people_joined.setChallenge_title(challenge_title);

                                        peoplejoinedimage.add(people_joined);


                                        peopleJoinedAdapter = new PeopleJoinedAdapter(Details_Activity.this,peoplejoinedimage,join,Details_Activity.this,challenge_id,ctype,
                                                avi,avibackground,challenge_title,user_id1,user_id2,user_id3,useridd,joinuser,vieww,linearjoin);
                                        peoplejoinedRV.setAdapter(peopleJoinedAdapter);
                                        peopleJoinedAdapter.notifyDataSetChanged();

                                    }

                                }
                            }
                            else {
                                peoplejoined.setText("0 "+ getResources().getString(R.string.peoplejoined));

                                joined_user1.setVisibility(View.GONE);
                                medal1.setVisibility(View.GONE);
                                fst.setVisibility(View.GONE);
                                joined_user2.setVisibility(View.GONE);
                                medal2.setVisibility(View.GONE);
                                scnd.setVisibility(View.GONE);
                                joined_user3.setVisibility(View.GONE);
                                medal3.setVisibility(View.GONE);
                                thrd.setVisibility(View.GONE);


                                if (challenge_reply.length()==0){
                                    //join gone
                                    if (!joinindicator.equals("yes")){
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                        String uidd = preferences.getString(Constant.USER_ID, "");
                                        if (type.equals("3") || type.equals("2")) {

                                            if (joinuser != null && joinuser.equals("yes")) {
                                                if (uidd.equals(useridd)) {
                                                    //join.setVisibility(View.GONE);
                                                    join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                    join.setTextColor(getResources().getColor(R.color.transparent));

                                                }
                                                else {
                                                    //join.setVisibility(View.VISIBLE);
                                                }
                                            } else if (joinuser.equals("")){
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }
                                        } else {

                                            if (uidd.equals(useridd)) {
                                                //join.setVisibility(View.GONE);
                                                join.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_btn_curve));
                                                join.setTextColor(getResources().getColor(R.color.transparent));

                                            }  else {
                                                //join.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    }
                                }

                            }
                        }
                        else {
                            peoplejoined.setVisibility(View.GONE);
                            //peoplejoined.setText("0 PEOPLE JOINED");
                            joined_user1.setVisibility(View.GONE);
                            medal1.setVisibility(View.GONE);
                            fst.setVisibility(View.GONE);
                            joined_user2.setVisibility(View.GONE);
                            medal2.setVisibility(View.GONE);
                            scnd.setVisibility(View.GONE);
                            joined_user3.setVisibility(View.GONE);
                            medal3.setVisibility(View.GONE);
                            thrd.setVisibility(View.GONE);

                        }




                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsedetailF", response);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });



    }

    private void setCoins(String silver, String gold, String bronze){

        // start of first condition

        // if silver is greater
        if (Integer.parseInt(silver) > Integer.parseInt(gold) && Integer.parseInt(silver)> Integer.parseInt(bronze)){

            coin.setImageResource(R.drawable.silver_rank_icon);
            bronzereading.setImageResource(R.drawable.silver_rank_icon);
            firsttxt.setText(silver);

            // if gold is greater than bronze
            if (Integer.parseInt(gold) > Integer.parseInt(bronze)){

                silverreading.setImageResource(R.drawable.gold_rank_icon);
                secondtxt.setText(gold);
                goldreading.setImageResource(R.drawable.bronze_rank_icon);
                thirdtxt.setText(bronze);
            }

            // if bronze is greater than gold
            else {
                silverreading.setImageResource(R.drawable.bronze_rank_icon);
                secondtxt.setText(bronze);
                goldreading.setImageResource(R.drawable.gold_rank_icon);
                thirdtxt.setText(gold);
            }
        }
        // end of first condition



        else if (Integer.parseInt(gold)>Integer.parseInt(silver) && Integer.parseInt(gold)> Integer.parseInt(bronze)){

            coin.setImageResource(R.drawable.gold_rank_icon);
            bronzereading.setImageResource(R.drawable.gold_rank_icon);
            firsttxt.setText(gold);


            if (Integer.parseInt(silver) > Integer.parseInt(bronze)){

                silverreading.setImageResource(R.drawable.silver_rank_icon);
                secondtxt.setText(silver);

                goldreading.setImageResource(R.drawable.bronze_rank_icon);
                thirdtxt.setText(bronze);

            }
            else {

                silverreading.setImageResource(R.drawable.bronze_rank_icon);
                secondtxt.setText(bronze);

                goldreading.setImageResource(R.drawable.silver_rank_icon);
                thirdtxt.setText(silver);

            }



        }
        else if (Integer.parseInt(bronze)>Integer.parseInt(silver) && Integer.parseInt(bronze)> Integer.parseInt(gold)){

            coin.setImageResource(R.drawable.bronze_rank_icon);
            bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            firsttxt.setText(bronze);


            if (Integer.parseInt(silver) > Integer.parseInt(gold)){

                silverreading.setImageResource(R.drawable.silver_rank_icon);
                secondtxt.setText(silver);

                goldreading.setImageResource(R.drawable.gold_rank_icon);
                thirdtxt.setText(gold);

            }
            else {

                silverreading.setImageResource(R.drawable.gold_rank_icon);
                secondtxt.setText(gold);

                goldreading.setImageResource(R.drawable.silver_rank_icon);
                thirdtxt.setText(silver);

            }


        }
        else if (Integer.parseInt(gold)==Integer.parseInt(silver) &&Integer.parseInt(gold) ==Integer.parseInt(bronze)){
            coin.setImageResource(R.drawable.bronze_rank_icon);
            bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            firsttxt.setText(bronze);

            if (Integer.parseInt(silver) > Integer.parseInt(gold)){

                silverreading.setImageResource(R.drawable.silver_rank_icon);
                secondtxt.setText(silver);

                goldreading.setImageResource(R.drawable.gold_rank_icon);
                thirdtxt.setText(gold);

            }
            else {

                silverreading.setImageResource(R.drawable.gold_rank_icon);
                secondtxt.setText(gold);

                goldreading.setImageResource(R.drawable.silver_rank_icon);
                thirdtxt.setText(silver);

            }

        }

        // if only gold and silver are equal
        else if (Integer.parseInt(gold)==Integer.parseInt(silver) &&
                Integer.parseInt(gold)!=Integer.parseInt(bronze)){

            coin.setImageResource(R.drawable.gold_rank_icon);

            bronzereading.setImageResource(R.drawable.gold_rank_icon);
            firsttxt.setText(gold);

            silverreading.setImageResource(R.drawable.silver_rank_icon);
            secondtxt.setText(silver);

            goldreading.setImageResource(R.drawable.bronze_rank_icon);
            thirdtxt.setText(bronze);

        }

        // if only gold and bronze are equal
        else if (Integer.parseInt(gold)==Integer.parseInt(bronze) &&
                Integer.parseInt(gold)!=Integer.parseInt(silver)){

            bronzereading.setImageResource(R.drawable.gold_rank_icon);
            firsttxt.setText(gold);


            silverreading.setImageResource(R.drawable.bronze_rank_icon);
            secondtxt.setText(bronze);

            goldreading.setImageResource(R.drawable.silver_rank_icon);
            thirdtxt.setText(silver);

        }

        else if (Integer.parseInt(silver)==Integer.parseInt(bronze) &&
                Integer.parseInt(silver)!=Integer.parseInt(gold)){

            bronzereading.setImageResource(R.drawable.silver_rank_icon);
            firsttxt.setText(silver);


            silverreading.setImageResource(R.drawable.bronze_rank_icon);
            secondtxt.setText(bronze);

            goldreading.setImageResource(R.drawable.gold_rank_icon);
            thirdtxt.setText(gold);

        }
        else if (Integer.parseInt(silver)==Integer.parseInt(gold) &&
                Integer.parseInt(silver)!=Integer.parseInt(bronze)){

            bronzereading.setImageResource(R.drawable.silver_rank_icon);
            firsttxt.setText(silver);


            silverreading.setImageResource(R.drawable.gold_rank_icon);
            secondtxt.setText(gold);

            goldreading.setImageResource(R.drawable.bronze_rank_icon);
            thirdtxt.setText(bronze);

        }

        else if (Integer.parseInt(bronze)==Integer.parseInt(silver) &&
                Integer.parseInt(bronze)!=Integer.parseInt(gold)){

            bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            firsttxt.setText(bronze);


            silverreading.setImageResource(R.drawable.silver_rank_icon);
            secondtxt.setText(silver);

            goldreading.setImageResource(R.drawable.gold_rank_icon);
            thirdtxt.setText(gold);


        }
        else if (Integer.parseInt(bronze)==Integer.parseInt(gold) &&
                Integer.parseInt(bronze)!=Integer.parseInt(silver)){

            bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            firsttxt.setText(bronze);


            silverreading.setImageResource(R.drawable.gold_rank_icon);
            secondtxt.setText(gold);

            goldreading.setImageResource(R.drawable.silver_rank_icon);
            thirdtxt.setText(silver);


        }

        else {

            coin.setImageResource(R.drawable.bronze_rank_icon);
            bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            firsttxt.setText(bronze);

            if (Integer.parseInt(silver) > Integer.parseInt(gold)){

                silverreading.setImageResource(R.drawable.silver_rank_icon);
                secondtxt.setText(silver);

                goldreading.setImageResource(R.drawable.gold_rank_icon);
                thirdtxt.setText(gold);

            }
            else {

                silverreading.setImageResource(R.drawable.gold_rank_icon);
                secondtxt.setText(gold);

                goldreading.setImageResource(R.drawable.silver_rank_icon);
                thirdtxt.setText(silver);

            }

        }

    }

    public void detail2()
    {
        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
        params.add("user_id",u_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/get_challege_detail?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String response = new String(responseBody);
                Log.e("responsedetail",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");


                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        comments_count = dataSet.getString("comments_count");
                        comment.setText(" "+comments_count);


                        user_like = dataSet.getString("user_like");
                        user_like_reward = dataSet.getString("user_like_reward");


                        challenge_title = dataSet.getString("challenge_title");


                        like_mode_type = user_like_reward;
                        String like_count = dataSet.getString("like_count");
                        like.setText(" "+like_count);

                        if(user_like.equals("1")){
                            if (user_like_reward.equals("bronze")){

                                like.setTextColor(getResources().getColor(R.color.bronzecoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.bronzecoin)));
                                like_mode_type = "3";
                            }
                            else if (user_like_reward.equals("silver")){
                                like.setTextColor(getResources().getColor(R.color.silvercoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.silvercoin)));
                                like_mode_type ="1" ;
                            }
                            else if (user_like_reward.equals("gold")){
                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));
                                like_mode_type = "2";
                            }
                            else if (user_like_reward.equals("campaign_like")){
                                like.setTextColor(getResources().getColor(R.color.goldcoin));
                                like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.goldcoin)));
                                like_mode_type ="4" ;
                            }


                        }
                        else {
                            like.setTextColor(getResources().getColor(R.color.white));
                            like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        }

                        JSONObject like_type_count = dataSet.getJSONObject("like_type_count");
                        String silver = like_type_count.getString("silver");
                        String gold = like_type_count.getString("gold");
                        String bronze = like_type_count.getString("bronze");

                        setCoins(silver,gold,bronze);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsedetailF", response);
                }
            }
        });



    }

    public void detail3()
    {
        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
        params.add("user_id",u_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/get_challege_detail?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String response = new String(responseBody);
                Log.e("responsedetail",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");


                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONObject like_type_count = dataSet.getJSONObject("like_type_count");
                        String silver = like_type_count.getString("silver");
                        String gold = like_type_count.getString("gold");
                        String bronze = like_type_count.getString("bronze");
                        user_like_reward = dataSet.getString("user_like_reward");
                        challenge_title = dataSet.getString("challenge_title");


                        setCoins(silver,gold,bronze);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsedetailF", response);
                }
            }
        });



    }

    private void joinApi(){

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",u_id);
        params.add("challenge_id",challenge_id);
        params.add("accept_reject","1");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "post/post/join_challenge", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("joinres",res);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true") && errorMessage.equals("")){

                        Intent intent = new Intent(Details_Activity.this,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (ctype.equals("campaign")){
                            intent.putExtra("from","campaign");
                            intent.putExtra("join","join");
                            intent.putExtra("cid",challenge_id);
                        }
                        else {
                            intent.putExtra("from","challenge");
                            intent.putExtra("join","join");
                            intent.putExtra("type","selfchallenge");
                            intent.putExtra("cid",challenge_id);
                        }

                        if (challenge_title.equals("Open Challenge")){

                            intent.putExtra("type","1");

                        }
                        else if (challenge_title.equals("1-on-1 Challenge ")){

                            intent.putExtra("type","3");

                        }
                        else if (challenge_title.equals("Self Challenge")){

                            intent.putExtra("type","4");

                        }
                        else if (challenge_title.equals("Group Challenge")){

                            intent.putExtra("type","2");

                        }
                        else if (challenge_title.equals("Location Base Challenge")){

                            intent.putExtra("type","6");

                        }
                        else if (challenge_title.equals("Create Campaign")){

                            intent.putExtra("type","5");

                        }




                        startActivity(intent);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("joinresF",res);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });
    }

    public void block_user()
    {
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = preferences.getString(Constant.USER_ID,"");

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        RequestParams params = new RequestParams();
        params.add("user_id",u_id);
        params.add("block_user_id",useridd);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL +"user/user/block_user",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responseblock",response);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true"))
                    {

                        Constant.LikeSuccessToast(Details_Activity.this,getResources().getString(R.string.Blocked));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Details_Activity.this,Home_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("refresh","refresh");
                                startActivity(intent);
                            }
                        }, 2000);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("responseblock",response);

                }

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Somethingwentwrong));


            }
        });







    }

    public void ReportDialog(final Activity activity){

        final Dialog mybuilder=new Dialog(activity);
        mybuilder.setContentView(R.layout.report_dialog);

        // TextView text,selfchallenge,oneononechallenge,groupchallenge,openchallenge,locationchallenge;

        RadioGroup radiogroup;
        final RadioButton radio1,radio2,radio3,radio4,radio5;
        Button btnreport;
        final EditText edreport;
        TextView text;
        ImageView imgcross;




        radiogroup=mybuilder.findViewById(R.id.radiogroup);
        radio1=mybuilder.findViewById(R.id.radio1);
        radio2=mybuilder.findViewById(R.id.radio2);
        radio3=mybuilder.findViewById(R.id.radio3);
        radio4=mybuilder.findViewById(R.id.radio4);
        radio5=mybuilder.findViewById(R.id.radio5);
        btnreport=mybuilder.findViewById(R.id.btnreport);
        edreport=mybuilder.findViewById(R.id.edother);
        text=mybuilder.findViewById(R.id.text);
        imgcross=mybuilder.findViewById(R.id.imgcross);




        radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edreport.setVisibility(View.GONE);

                    cause=radio1.getText().toString();
                }
            }
        });

        radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edreport.setVisibility(View.GONE);

                    cause=radio2.getText().toString();
                }
            }
        });


        radio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edreport.setVisibility(View.GONE);

                    cause=radio3.getText().toString();
                }
            }
        });

        radio4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edreport.setVisibility(View.GONE);

                    cause=radio4.getText().toString();
                }
            }
        });

        radio5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    edreport.setVisibility(View.VISIBLE);


                    if (edreport.getText().toString().equals(""))
                    {
                        cause=radio5.getText().toString();
                    }
                    else if (!edreport.getText().toString().equals(""))
                    {
                        cause=edreport.getText().toString();
                    }

                }

            }
        });






        text.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Medium.ttf"));
        radio1.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        radio2.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        radio3.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        radio4.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        radio5.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        btnreport.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Bold.ttf"));
        edreport.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));





        mybuilder.show();
        mybuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = mybuilder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        imgcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
            }
        });

        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report_user();
                mybuilder.dismiss();
            }
        });


    }


/*
    public void report_user(String c)
    {

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = preferences.getString(Constant.USER_ID,"");

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();


        RequestParams params = new RequestParams();
        params.add("user_id",useridd);
        params.add("reported_user_id",u_id);
        params.add("cause",c);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/reposts_user", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsereport",response);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String ststus = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (ststus.equals("true"))
                    {

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        String repoted_admin = dataSet.getString("repoted_admin");
                        Constant.SuccessToast(Details_Activity.this,repoted_admin);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("responsereport",response);

                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();


            }
        });



    }
*/

    public void report_user()
    {

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = preferences.getString(Constant.USER_ID,"");

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();


        RequestParams params = new RequestParams();
        params.add("user_id",u_id);
        params.add("challenge_id",challenge_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "post/post/reported_challenge", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsereport",response);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String ststus = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (ststus.equals("true"))
                    {

                        Constant.SuccessToast(Details_Activity.this,getResources().getString(R.string.ReportedSuccessfully));

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("responsereport",response);

                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Details_Activity.this,getResources().getString(R.string.Somethingwentwrong));


            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        detail2();
    }

    @Override
    public void onBackPressed() {

        if (backk.equals("yes")){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);

        }
        else {
            SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
            if (pref.contains("liked")){
                Intent intent = new Intent(Details_Activity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else {

                if (backintent.equals("yes")){
                    Intent intent = new Intent(Details_Activity.this,Home_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    super.onBackPressed();
                }
            }
        }
    }
}
