package com.bossble.bossble.ProfileSetup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.ProfileAttemptedAdapter;
import com.bossble.bossble.Adapter.ProfileCreatedAdapter;
import com.bossble.bossble.Adapter.ProfileWonAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.ChallengeWon;
import com.bossble.bossble.Model.Profile_attempted;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class Profile_of_others_Activity extends AppCompatActivity implements ProfileCreatedAdapter.Callback{


    ImageView fulllogo,imgback,imgcover;
    RoundedImageView imgprofile;
    TextView txtprofilename,txtprofilename2,txtprofilecountry,txtprofilecountry2,txtfan,txtfancount,txtfanof,txtfanofcount,txtaboutme,txtbio,txtcreated,txtattempted,txtwon;
    Button btnfollow,btnmessage;
    RecyclerView recyclercreated,recyclerattempted,recyclerwon;

    com.bossble.bossble.Adapter.ProfileCreatedAdapter ProfileCreatedAdapter;
    ArrayList<Profile_challenges> createdlist = new ArrayList<>();

    com.bossble.bossble.Adapter.ProfileAttemptedAdapter ProfileAttemptedAdapter;
    ProfileWonAdapter profileWonAdapter;
    List<Profile_attempted> attemptedlist = new ArrayList<>();
    ArrayList<ChallengeWon> attemptedlist2 = new ArrayList<>();

    String user_id="";
    String is_follow_user="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    LinearLayout l1,l2;
    String follow_count="";
    RelativeLayout r1;
    String image="";
    String cover_image="";
    ScrollView sv;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_of_others_);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Constant.bottomNav(Profile_of_others_Activity.this,4,sv,"");



        if (getIntent().hasExtra("user_id"))
        {
            user_id = getIntent().getStringExtra("user_id");
        }



        sv = findViewById(R.id.sv);
        fulllogo = findViewById(R.id.fulllogo);
        imgback = findViewById(R.id.imgback);
        imgcover = findViewById(R.id.imgcover);
        imgprofile = findViewById(R.id.imgprofile);
        txtprofilename = findViewById(R.id.txtprofilename);
        txtprofilename2 = findViewById(R.id.txtprofilename2);
        txtprofilecountry = findViewById(R.id.txtprofilecountry);
        txtprofilecountry2 = findViewById(R.id.txtprofilecountry2);
        txtfan = findViewById(R.id.txtfan);
        txtfancount = findViewById(R.id.txtfancount);
        txtfanof = findViewById(R.id.txtfanof);
        txtfanofcount = findViewById(R.id.txtfanofcount);
        txtaboutme = findViewById(R.id.txtaboutme);
        txtbio = findViewById(R.id.txtbio);
        txtcreated = findViewById(R.id.txtcreated);
        txtattempted = findViewById(R.id.txtattempted);
        txtwon = findViewById(R.id.txtwon);
        recyclercreated = findViewById(R.id.recyclercreated);
        recyclerattempted = findViewById(R.id.recyclerattempted);
        recyclerwon = findViewById(R.id.recyclerwon);
        btnfollow = findViewById(R.id.btnfollow);
        btnmessage = findViewById(R.id.btnmessage);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        r1 = findViewById(R.id.r1);



        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_of_others_Activity.this, Admire_Activity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_of_others_Activity.this, Admiring_Activity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });




        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_of_others_Activity.this,Home_Activity.class);
                startActivity(intent);
            }
        });


        // ProfileCreatedAdapter = new ProfileCreatedAdapter(getApplicationContext(),createdlist);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        ((LinearLayoutManager) layoutManager4).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager4).setStackFromEnd(true);
        recyclercreated.setLayoutManager(layoutManager4);
        // recyclercreated.setAdapter(ProfileCreatedAdapter);
        recyclercreated.setNestedScrollingEnabled(false);
        //ProfileCreatedAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        ((LinearLayoutManager) layoutManager5).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager5).setStackFromEnd(true);
        recyclerattempted.setLayoutManager(layoutManager5);
        recyclerattempted.setNestedScrollingEnabled(false);

/*
        profileWonAdapter = new ProfileWonAdapter(getApplicationContext(),attemptedlist2);
        recyclerwon.setAdapter(profileWonAdapter);
        profileWonAdapter.notifyDataSetChanged();
*/

        RecyclerView.LayoutManager layoutManager6 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        ((LinearLayoutManager) layoutManager6).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager6).setStackFromEnd(true);
        recyclerwon.setLayoutManager(layoutManager6);
        recyclerwon.setNestedScrollingEnabled(false);



        txtattempted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclercreated.getVisibility()==View.VISIBLE ){
                    txtcreated.setTextColor(getResources().getColor(R.color.darkgray));
                    txtwon.setTextColor(getResources().getColor(R.color.darkgray));
                    txtattempted.setTextColor(getResources().getColor(R.color.dark_blue));

                    recyclercreated.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recyclercreated.setVisibility(View.GONE);
                            recyclerwon.setVisibility(View.GONE);
                            recyclerattempted.setVisibility(View.VISIBLE);
                            recyclerattempted.animate().alpha(1f).setDuration(500).setListener(null);
                        }
                    });

                }
                else if (recyclerwon.getVisibility()==View.VISIBLE){
                    txtcreated.setTextColor(getResources().getColor(R.color.darkgray));
                    txtwon.setTextColor(getResources().getColor(R.color.darkgray));
                    txtattempted.setTextColor(getResources().getColor(R.color.dark_blue));
                    recyclerwon.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recyclercreated.setVisibility(View.GONE);
                            recyclerwon.setVisibility(View.GONE);
                            recyclerattempted.setVisibility(View.VISIBLE);
                            recyclerattempted.animate().alpha(1f).setDuration(500).setListener(null);
                        }
                    });


                }

            }
        });

        txtwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerattempted.getVisibility()==View.VISIBLE ){
                    txtcreated.setTextColor(getResources().getColor(R.color.darkgray));
                    txtattempted.setTextColor(getResources().getColor(R.color.darkgray));
                    txtwon.setTextColor(getResources().getColor(R.color.dark_blue));
                    recyclerattempted.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recyclerattempted.setVisibility(View.GONE);
                            recyclercreated.setVisibility(View.GONE);
                            recyclerwon.setVisibility(View.VISIBLE);
                            recyclerwon.animate().alpha(1f).setDuration(500).setListener(null);
                        }
                    });

                }
                else if (recyclercreated.getVisibility()==View.VISIBLE){

                        txtcreated.setTextColor(getResources().getColor(R.color.darkgray));
                        txtattempted.setTextColor(getResources().getColor(R.color.darkgray));
                        txtwon.setTextColor(getResources().getColor(R.color.dark_blue));
                        recyclercreated.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                recyclerattempted.setVisibility(View.GONE);
                                recyclercreated.setVisibility(View.GONE);
                                recyclerwon.setVisibility(View.VISIBLE);
                                recyclerwon.animate().alpha(1f).setDuration(500).setListener(null);
                            }
                        });

                }
            }
        });

        txtcreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerattempted.getVisibility()==View.VISIBLE ){
                    txtcreated.setTextColor(getResources().getColor(R.color.dark_blue));
                    txtattempted.setTextColor(getResources().getColor(R.color.darkgray));
                    txtwon.setTextColor(getResources().getColor(R.color.darkgray));
                    recyclerattempted.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recyclerattempted.setVisibility(View.GONE);
                            recyclerwon.setVisibility(View.GONE);
                            recyclercreated.setVisibility(View.VISIBLE);
                            recyclercreated.animate().alpha(1f).setDuration(500).setListener(null);
                        }
                    });

                }
                else if (recyclerwon.getVisibility()==View.VISIBLE){
                    txtcreated.setTextColor(getResources().getColor(R.color.dark_blue));
                    txtattempted.setTextColor(getResources().getColor(R.color.darkgray));
                    txtwon.setTextColor(getResources().getColor(R.color.darkgray));
                    recyclerwon.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recyclerattempted.setVisibility(View.GONE);
                            recyclerwon.setVisibility(View.GONE);
                            recyclercreated.setVisibility(View.VISIBLE);
                            recyclercreated.animate().alpha(1f).setDuration(500).setListener(null);
                        }
                    });

                }
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.isOnline(Profile_of_others_Activity.this)){

                    if (is_follow_user.equals("1"))
                    {

                        unfollow();
                        profile_of_others("");
                    }
                    else if (is_follow_user.equals("0"))
                    {
                        follow();
                        profile_of_others("");
                    }
                }
                else{
                    Constant.ErrorToast(Profile_of_others_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }
            }
        });


        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_of_others_Activity.this,Full_Profile_Picture_Activity.class);
                intent.putExtra("image",image);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Profile_of_others_Activity.this,imgprofile,ViewCompat.getTransitionName(imgprofile));
                startActivity(intent,options.toBundle());
            }
        });

        imgcover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_of_others_Activity.this,Full_Profile_Picture_Activity.class);
                intent.putExtra("image",cover_image);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Profile_of_others_Activity.this,imgcover,ViewCompat.getTransitionName(imgcover));
                startActivity(intent,options.toBundle());
            }
        });






        if (follow_count.length()==0 || follow_count.length()>100)
        {
            imgprofile.setBorderColor(R.color.black);
            imgprofile.setBorderWidth(Float.parseFloat("8"));

        }
        else if (follow_count.length()==100 || follow_count.length()>1000)
        {
            imgprofile.setBorderColor(R.color.bronze);
            imgprofile.setBorderWidth(Float.parseFloat("8"));
        }
        else if (follow_count.length()==1000 || follow_count.length()>10000)
        {
            imgprofile.setBorderColor(R.color.silver);
            imgprofile.setBorderWidth(Float.parseFloat("8"));
        }
        else if (follow_count.length()==10000 || follow_count.length()>100000)
        {
            imgprofile.setBorderColor(R.color.yellow);
            imgprofile.setBorderWidth(Float.parseFloat("8"));
        }
        else if (follow_count.length()==100000 || follow_count.length()>10000000)
        {
            imgprofile.setBorderColor(R.color.diamond);
            imgprofile.setBorderWidth(Float.parseFloat("8"));
        }





        Fonts();

        if (Constant.isOnline(Profile_of_others_Activity.this)){

            profile_of_others("117");
        }
        else{
            Constant.ErrorToast(Profile_of_others_Activity.this,getResources().getString(R.string.NoInternetConnection));

        }



    }

    public void Fonts()
    {
        txtprofilename.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtprofilename2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtprofilecountry.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtprofilecountry2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtfan.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtfancount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        txtfanof.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtfanofcount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        txtaboutme.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtbio.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtcreated.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        txtattempted.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        txtwon.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        btnfollow.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnmessage.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
    }

    public void profile_of_others(String id)
    {

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = pref.getString(Constant.USER_ID,"");

        //String user_id = pref.getString(Constant.USER_ID,"");

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.add("user_id",viewer_id);
        params.add("viewer_id",user_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "user/user/get_user_profile?user_id=",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responseprofileofothers",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");


                    if (status.equals("true"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        imgcover.setVisibility(View.VISIBLE);
                        txtprofilename.setVisibility(View.VISIBLE);
                        txtprofilename2.setVisibility(View.VISIBLE);
                        txtprofilecountry.setVisibility(View.VISIBLE);
                        txtprofilecountry2.setVisibility(View.VISIBLE);
                        imgprofile.setVisibility(View.VISIBLE);
                        r1.setVisibility(View.VISIBLE);
                        txtaboutme.setVisibility(View.VISIBLE);
                        txtbio.setVisibility(View.VISIBLE);
                        txtcreated.setVisibility(View.VISIBLE);
                        txtattempted.setVisibility(View.VISIBLE);
                        txtwon.setVisibility(View.VISIBLE);
                        btnfollow.setVisibility(View.VISIBLE);
                        btnmessage.setVisibility(View.VISIBLE);

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        String username = dataSet.getString("username");
                        String country = dataSet.getString("country");
                        String serverResponse = dataSet.getString("bio");
                        String bio = StringEscapeUtils.unescapeJava(serverResponse);
                        follow_count = dataSet.getString("follow_count");
                        String follower_count = dataSet.getString("follower_count");
                        is_follow_user = dataSet.getString("is_follow_user");
                        if (is_follow_user.equals("1"))
                        {
                            btnfollow.setText(getResources().getString(R.string.unadmire));
                        }
                        else if (is_follow_user.equals("0"))
                        {
                            btnfollow.setText(getResources().getString(R.string.admire));
                        }


                        txtprofilename.setText(username);
                        txtprofilename2.setText(username);
                        txtprofilecountry.setText(country);
                        txtprofilecountry2.setText(country);
                        txtfancount.setText(follow_count);
                        txtfanofcount.setText(follower_count);


                        if (bio.equals("") || bio.equals("null"))
                        {
                            txtbio.setText("N/A");
                        }
                        else
                        {
                            txtbio.setText(bio);
                        }



                        if (dataSet.get("image") instanceof JSONArray){

                            image = "";
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            options.placeholder(R.drawable.user_image_placeholder);
                            Glide.with(Profile_of_others_Activity.this)
                                    .load("")
                                    .apply(options)
                                    .into(imgprofile);

                        }
                        else {
                            image = dataSet.getString("image");
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            options.placeholder(R.drawable.user_image_placeholder);
                            Glide.with(Profile_of_others_Activity.this)
                                    .load(image)
                                    .apply(options)
                                    .into(imgprofile);

                        }
                        if (dataSet.get("cover_image") instanceof JSONArray){

                            cover_image="";
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            options.placeholder(R.drawable.bossble_placeholder_large);
                            Glide.with(Profile_of_others_Activity.this)
                                    .load("")
                                    .apply(options)
                                    .into(imgcover);

                        }
                        else {
                            cover_image = dataSet.getString("cover_image");
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            options.placeholder(R.drawable.bossble_placeholder_large);
                            Glide.with(Profile_of_others_Activity.this)
                                    .load(cover_image)
                                    .apply(options)
                                    .into(imgcover);

                        }

                        JSONObject posted_challenge = dataSet.getJSONObject("posted_challenges");

                        if (posted_challenge.get("campaign") instanceof JSONArray){
                            JSONArray campaign = posted_challenge.getJSONArray("campaign");

                            if (campaign.length()>0) {
                                for (int i = 0; i < campaign.length(); i++) {
                                    JSONObject item = campaign.getJSONObject(i);

                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");



                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("campaign");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);


                                }
                            }
                        }

                        if (posted_challenge.get("open_challenge") instanceof JSONArray) {

                            JSONArray open_challenge = posted_challenge.getJSONArray("open_challenge");
                            if (open_challenge.length()>0)
                            {
                                for (int i = 0; i < open_challenge.length(); i++) {
                                    JSONObject item = open_challenge.getJSONObject(i);

                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");


                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("challenge");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);

                                }
                            }
                        }

                        if (posted_challenge.get("one_on_one_challenge") instanceof JSONArray) {
                            JSONArray one_on_one_challenge = posted_challenge.getJSONArray("one_on_one_challenge");
                            if (one_on_one_challenge.length()>0)
                            {
                                for (int i = 0; i < one_on_one_challenge.length(); i++) {
                                    JSONObject item = one_on_one_challenge.getJSONObject(i);

                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("challenge");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);




                                }
                            }
                        }

                        ////
                        if (posted_challenge.get("self_challenge") instanceof JSONArray) {

                            JSONArray self_challenge = posted_challenge.getJSONArray("self_challenge");
                            if (self_challenge.length()>0)
                            {
                                for (int i = 0; i < self_challenge.length(); i++) {
                                    JSONObject item = self_challenge.getJSONObject(i);


                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");


                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("challenge");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);



                                }
                            }


                        }
                        if (posted_challenge.get("location_challenge") instanceof JSONArray) {
                            JSONArray location_challenge = posted_challenge.getJSONArray("location_challenge");
                            if (location_challenge.length()>0)
                            {
                                for (int i = 0; i < location_challenge.length(); i++) {
                                    JSONObject item = location_challenge.getJSONObject(i);


                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");


                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("nearbychallenge");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);

                                }
                            }

                        }

                        if (posted_challenge.get("group_challenge") instanceof JSONArray) {
                            JSONArray group_challenge = posted_challenge.getJSONArray("group_challenge");
                            if (group_challenge.length()>0)
                            {
                                for (int i = 0; i < group_challenge.length(); i++) {
                                    JSONObject item = group_challenge.getJSONObject(i);


                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String user_country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    Profile_challenges profile_challenges = new Profile_challenges();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setType("challenge");
                                    profile_challenges.setView_count(view_count);

                                    createdlist.add(profile_challenges);

                                }

                            }
                        }

                        ProfileCreatedAdapter = new ProfileCreatedAdapter(getApplicationContext(),createdlist,"other",Profile_of_others_Activity.this,Profile_of_others_Activity.this);
                        recyclercreated.setAdapter(ProfileCreatedAdapter);
                        ProfileCreatedAdapter.notifyDataSetChanged();

                        JSONObject attempted_challenges = dataSet.getJSONObject("attempted_challenges");

                        if (attempted_challenges.get("campaign") instanceof JSONArray) {
                            JSONArray acampaign = attempted_challenges.getJSONArray("campaign");

                            if (acampaign.length()>0) {
                                for (int i = 0; i < acampaign.length(); i++) {
                                    JSONObject item = acampaign.getJSONObject(i);

                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);

                                }
                            }
                        }
                        if (attempted_challenges.get("open_challenge") instanceof JSONArray) {
                            JSONArray aopen_challenge = attempted_challenges.getJSONArray("open_challenge");
                            if (aopen_challenge.length()>0)
                            {
                                for (int i = 0; i < aopen_challenge.length(); i++) {
                                    JSONObject item = aopen_challenge.getJSONObject(i);


                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);


                                }
                            }
                        }
                        if (attempted_challenges.get("one_on_one_challenge") instanceof JSONArray) {
                            JSONArray aone_on_one_challenge = attempted_challenges.getJSONArray("one_on_one_challenge");
                            if (aone_on_one_challenge.length()>0)
                            {
                                for (int i = 0; i < aone_on_one_challenge.length(); i++) {
                                    JSONObject item = aone_on_one_challenge.getJSONObject(i);



                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);
                                }
                            }
                        }
                        if (attempted_challenges.get("self_challenge") instanceof JSONArray) {
                            JSONArray aself_challenge = attempted_challenges.getJSONArray("self_challenge");
                            if (aself_challenge.length()>0)
                            {
                                for (int i = 0; i < aself_challenge.length(); i++) {
                                    JSONObject item = aself_challenge.getJSONObject(i);



                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);

                                }
                            }
                        }
                        if (attempted_challenges.get("location_challenge") instanceof JSONArray) {
                            JSONArray alocation_challenge = attempted_challenges.getJSONArray("location_challenge");
                            if (alocation_challenge.length()>0)
                            {
                                for (int i = 0; i < alocation_challenge.length(); i++) {
                                    JSONObject item = alocation_challenge.getJSONObject(i);



                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);
                                }
                            }
                        }
                        if (attempted_challenges.get("group_challenge") instanceof JSONArray) {
                            JSONArray agroup_challenge = attempted_challenges.getJSONArray("group_challenge");
                            if (agroup_challenge.length()>0)
                            {
                                for (int i = 0; i < agroup_challenge.length(); i++) {
                                    JSONObject item = agroup_challenge.getJSONObject(i);


                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String user_country = item.getString("country");
                                    String view_count = "0";


                                    Profile_attempted profile_challenges = new Profile_attempted();
                                    profile_challenges.setId(id);
                                    profile_challenges.setUser_id(user_id);
                                    profile_challenges.setTitle(title);
                                    profile_challenges.setVideo(video);
                                    profile_challenges.setImage(image);
                                    profile_challenges.setDescritpion(descritpion);
                                    profile_challenges.setTags(tags);
                                    profile_challenges.setCreated_at(created_at);
                                    profile_challenges.setUsername(username);
                                    profile_challenges.setProfile_image(profile_image);
                                    profile_challenges.setDescription_background(description_background);
                                    profile_challenges.setDescription_fonts(description_fonts);
                                    profile_challenges.setComments_count(comments_count);
                                    profile_challenges.setLike_count(like_count);
                                    profile_challenges.setChallenge_attempted_count("0");
                                    profile_challenges.setUsercountry(user_country);
                                    profile_challenges.setView_count(view_count);
                                    profile_challenges.setType("campaign");
                                    attemptedlist.add(profile_challenges);
                                }

                            }
                        }

                        ProfileAttemptedAdapter = new ProfileAttemptedAdapter(getApplicationContext(),attemptedlist);
                        recyclerattempted.setAdapter(ProfileAttemptedAdapter);
                        ProfileAttemptedAdapter.notifyDataSetChanged();

                        //challenge won
                        //challenge won
                        JSONObject challenges_score = dataSet.getJSONObject("challenges_score");

                        if (challenges_score.has("campaign")){
                            if (challenges_score.get("campaign") instanceof JSONArray) {
                                JSONArray campaign = challenges_score.getJSONArray("campaign");
                                if (campaign.length()>0) {
                                    for (int i = 0; i < campaign.length(); i++) {
                                        JSONObject item = campaign.getJSONObject(i);

                                        ChallengeWon profile_challenges = new ChallengeWon();

                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");

                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");
                                        attemptedlist2.add(profile_challenges);

                                    }
                                }
                            }
                        }
                        if (challenges_score.has("open_challenge")){
                            if (challenges_score.get("open_challenge") instanceof JSONArray) {
                                JSONArray open_challenge = challenges_score.getJSONArray("open_challenge");

                                if (open_challenge.length()>0)
                                {
                                    for (int i = 0; i < open_challenge.length(); i++) {
                                        JSONObject item = open_challenge.getJSONObject(i);

                                        ChallengeWon profile_challenges = new ChallengeWon();

                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");
                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");
                                        profile_challenges.setType("challenge");

                                        attemptedlist2.add(profile_challenges);


                                    }
                                }
                            }
                        }
                        if (challenges_score.has("one_on_one_challenge")){
                            if (challenges_score.get("one_on_one_challenge") instanceof JSONArray) {
                                JSONArray one_on_one_challenge = challenges_score.getJSONArray("one_on_one_challenge");
////
                                if (one_on_one_challenge.length()>0)
                                {
                                    for (int i = 0; i < one_on_one_challenge.length(); i++) {
                                        JSONObject item = one_on_one_challenge.getJSONObject(i);

                                        ChallengeWon profile_challenges = new ChallengeWon();
                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");
                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");

                                        profile_challenges.setType("challenge");

                                        attemptedlist2.add(profile_challenges);

                                    }
                                }
                            }
                        }
                        if (challenges_score.has("location_challenge")){
                            if (challenges_score.get("location_challenge") instanceof JSONArray) {
                                JSONArray location_challenge = challenges_score.getJSONArray("location_challenge");
                                if (location_challenge.length()>0)
                                {
                                    for (int i = 0; i < location_challenge.length(); i++) {
                                        JSONObject item = location_challenge.getJSONObject(i);
                                        ChallengeWon profile_challenges = new ChallengeWon();


                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");
                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");
                                        profile_challenges.setType("nearbychallenge");

                                        attemptedlist2.add(profile_challenges);
                                    }
                                }

                            }
                        }
                        if (challenges_score.has("self_challenge")){
                            if (challenges_score.get("self_challenge") instanceof JSONArray) {
                                JSONArray self_challenge = challenges_score.getJSONArray("self_challenge");
                                if (self_challenge.length()>0)
                                {
                                    for (int i = 0; i < self_challenge.length(); i++) {
                                        ChallengeWon profile_challenges = new ChallengeWon();
                                        JSONObject item = self_challenge.getJSONObject(i);


                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");
                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");
                                        profile_challenges.setType("challenge");

                                        attemptedlist2.add(profile_challenges);

                                    }
                                }

                            }
                        }
                        if (challenges_score.has("group_challenge")){
                            if (challenges_score.get("group_challenge") instanceof JSONArray) {
                                JSONArray group_challenge = challenges_score.getJSONArray("group_challenge");
                                if (group_challenge.length()>0)
                                {
                                    for (int i = 0; i < group_challenge.length(); i++) {
                                        ChallengeWon profile_challenges = new ChallengeWon();

                                        JSONObject item = group_challenge.getJSONObject(i);

                                        String id = item.getString("id");
                                        String user_id = item.getString("user_id");
                                        String title = item.getString("title");
                                        if (item.has("video")){
                                            String video = item.getString("video");
                                            profile_challenges.setVideo(video);

                                        }
                                        if (item.has("image")){
                                            String image = item.getString("image");
                                            profile_challenges.setImage(image);
                                        }
                                        String descritpion = item.getString("descritpion");
                                        String tags = item.getString("tags");
                                        String created_at = item.getString("created_at");
                                        String profile_image = item.getString("profile_image");
                                        String usernamec = item.getString("username");
                                        String comments_count = item.getString("comments_count");
                                        String like_count = item.getString("like_count");
                                        String user_country = item.getString("country");
                                        String view_count = item.getString("view_count");
                                        if(item.has("score")){
                                            if (item.get("score") instanceof JSONObject) {
                                                JSONObject score = item.getJSONObject("score");
                                                String position = score.getString("position");
                                                profile_challenges.setPosition(position);
                                            }
                                        }
                                        else {
                                            profile_challenges.setPosition("N/A");
                                        }

                                        profile_challenges.setId(id);
                                        profile_challenges.setUser_id(user_id);
                                        profile_challenges.setTitle(title);
                                        profile_challenges.setDescritpion(descritpion);
                                        profile_challenges.setTags(tags);
                                        profile_challenges.setCreated_at(created_at);
                                        profile_challenges.setUsername(usernamec);
                                        profile_challenges.setProfile_image(profile_image);
                                        profile_challenges.setDescription_background("");
                                        profile_challenges.setDescription_fonts("");
                                        profile_challenges.setComments_count(comments_count);
                                        profile_challenges.setLike_count(like_count);
                                        profile_challenges.setUsercountry(user_country);
                                        profile_challenges.setView_count(view_count);
                                        profile_challenges.setChallenge_attempted_count("");
                                        profile_challenges.setType("challenge");

                                        attemptedlist2.add(profile_challenges);

                                    }

                                }
                            }
                        }


                        profileWonAdapter = new ProfileWonAdapter(getApplicationContext(),attemptedlist2);
                        recyclerwon.setAdapter(profileWonAdapter);
                        profileWonAdapter.notifyDataSetChanged();

                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(Profile_of_others_Activity.this,jsonObject.getString("errorMessage"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responseprofileofotherF", response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(Profile_of_others_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                if (Constant.isOnline(Profile_of_others_Activity.this)){


                }
                else{
                    Constant.ErrorToast(Profile_of_others_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }
            }
        });
    }

    public void follow()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = pref.getString(Constant.USER_ID,"");

        //String user_id = pref.getString(Constant.USER_ID,"");

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.add("user_id",viewer_id);
        params.add("follower_id",user_id);
        params.add("is_follow","1");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "follow/follow/follow", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("responsefollow",response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true"))
                    {  avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        btnfollow.setText(getResources().getString(R.string.ADMIRED));
                        Constant.SuccessToast(Profile_of_others_Activity.this, getResources().getString(R.string.AdmiredSuccessfully));
                        // profile_of_others("");
                    }
                    else if (status.equals("false"))
                    {  avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(Profile_of_others_Activity.this, jsonObject.getString("errorMessage"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    String response = new String(responseBody);
                    Log.e("responsefollowF", response);
                }
            }
        });



    }

    public void unfollow()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String viewer_id = pref.getString(Constant.USER_ID,"");

        //String user_id = pref.getString(Constant.USER_ID,"");

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.add("user_id",viewer_id);
        params.add("follower_id",user_id);
        params.add("unfollow","1");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "follow/follow/unfollow", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("responseunfollow",response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true"))
                    {  avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        btnfollow.setText(getResources().getString(R.string.admire));
                        Constant.SuccessToast(Profile_of_others_Activity.this, getResources().getString(R.string.UnadmireSuccessfully));
                        //profile_of_others("");

                    }
                    else if (status.equals("false"))
                    {  avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(Profile_of_others_Activity.this, jsonObject.getString("errorMessage"));
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    String response = new String(responseBody);
                    Log.e("responseunfollowF", response);
                }
            }
        });



    }

    @Override
    public void onPostDeleted(String status) {

    }
}
