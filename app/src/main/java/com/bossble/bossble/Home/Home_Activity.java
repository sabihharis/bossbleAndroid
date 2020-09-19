package com.bossble.bossble.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.bossble.bossble.Adapter.CampaignAdapter;
import com.bossble.bossble.Adapter.NearByChallengeAdapter;
import com.bossble.bossble.Adapter.TrendingChallengesAdapter;
import com.bossble.bossble.BuildConfig;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.VideoPlayer_Activity;
import com.bossble.bossble.Model.Campaigns;
import com.bossble.bossble.Model.NearBy_Challenges;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.Model.Trending_Challenges;
import com.bossble.bossble.R;

import com.bossble.bossble.Show_All.Show_All_Activity;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class Home_Activity extends AppCompatActivity {

    ScrollView sv;
    RelativeLayout root;

    ImageView chat;
    TextView capmpaigns_heading,showall_campaigns;
    TextView nearchallenges_heading,showall_nearchallenges;
    TextView trendingchallenges_heading,showall_trendingchallenges;



    RecyclerView campaignsRV;
    CampaignAdapter campaignAdapter;

    RecyclerView nearchallengesRV;
    NearByChallengeAdapter nearByChallengeAdapter;
    RecyclerView trendingchallengesRV;

    ArrayList<Campaigns> campaignslist = new ArrayList<>();
    ArrayList<NearBy_Challenges> nearbylist = new ArrayList<>();
    ArrayList<String> trendingusername = new ArrayList<>();
    ArrayList<Integer> trendinguserimage = new ArrayList<>();
    ArrayList<Integer> trendingcoingimage = new ArrayList<>();
    ArrayList<Integer> trendingchallengegimage = new ArrayList<>();
    ArrayList<String> trendingchallengename = new ArrayList<>();
    ArrayList<String> trendingchallengetime = new ArrayList<>();
    ArrayList<String> trendingchallengelike = new ArrayList<>();
    ArrayList<String> trendingchallengecomments = new ArrayList<>();
    ArrayList<String> trendingchallengeviews = new ArrayList<>();

    ArrayList<Trending_Challenges> trendinglist=new ArrayList<>();
    ArrayList<String> usernamelist=new ArrayList<>();
    ArrayList<String> userimagelist=new ArrayList<>();
    ArrayList<String> cimagelist=new ArrayList<>();
    ArrayList<String> cnamelist=new ArrayList<>();
    ArrayList<String> cidlist=new ArrayList<>();
    ArrayList<String> cdescriptionlist=new ArrayList<>();
    ArrayList<String> likeslist=new ArrayList<>();
    ArrayList<String> commentslist=new ArrayList<>();
    ArrayList<String> tagslist=new ArrayList<>();
    ArrayList<String> cfontlist=new ArrayList<>();
    ArrayList<String> views=new ArrayList<>();

    ArrayList<String> Cusernamelist=new ArrayList<>();
    ArrayList<String> Cuserimagelist=new ArrayList<>();
    ArrayList<String> Ccimagelist=new ArrayList<>();
    ArrayList<String> Ccnamelist=new ArrayList<>();
    ArrayList<String> Ccidlist=new ArrayList<>();
    ArrayList<String> Ccdescriptionlist=new ArrayList<>();
    ArrayList<String> Clikeslist=new ArrayList<>();
    ArrayList<String> Ccommentslist=new ArrayList<>();
    ArrayList<String> Ctagslist=new ArrayList<>();
    ArrayList<String> Ccfontlist=new ArrayList<>();
    ArrayList<String> Cviews=new ArrayList<>();
    ArrayList<String> campaign_multiple= new ArrayList<>();

    TrendingChallengesAdapter trendingChallengesAdapter;

    AVLoadingIndicatorView avi;
    ImageView avibackground;

    ImageView fulllogo;


    TextView notrending,nonearby,nocampaigns;


    String country="";

    int a=0;

    PullRefreshLayout layrefreshlayout;

    TextView loadmore;

    int opencount=0;
    int oneononecount=0;
    int selfcount=0;
    int groupcount=0;
    int trendingpagination=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        FindByIds();

        Constant.bottomNav(Home_Activity.this,0,sv,"home");

        setFonts();
        setListeners();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        campaignsRV.setLayoutManager(layoutManager);
        campaignsRV.setHasFixedSize(true);



        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        nearchallengesRV.setLayoutManager(layoutManager2);
        nearchallengesRV.setHasFixedSize(true);


        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        trendingchallengesRV.setLayoutManager(layoutManager3);
        trendingchallengesRV.setHasFixedSize(true);
        trendingchallengesRV.setNestedScrollingEnabled(false);

        trendingusername.add("Sebastian Bach");
        trendingusername.add("Ingrid Goodwell");
        trendingusername.add("John Smith");
        trendinguserimage.add(R.drawable.user_01);
        trendinguserimage.add(R.drawable.user_02);
        trendinguserimage.add(R.drawable.suggested_user_05);
        trendingcoingimage.add(R.drawable.bronze_rank_icon);
        trendingcoingimage.add(R.drawable.silver_rank_icon);
        trendingcoingimage.add(R.drawable.gold_rank_icon);
        trendingchallengegimage.add(R.drawable.trending_challenge_01);
        trendingchallengegimage.add(R.drawable.trending_challenge_02);
        trendingchallengegimage.add(R.drawable.trending_challenge_01);
        trendingchallengename.add("Quickest Rubik");
        trendingchallengename.add("Photography Gear");
        trendingchallengename.add("Bottle Flip");
        trendingchallengetime.add("20 mins ago");
        trendingchallengetime.add("38 mins ago");
        trendingchallengetime.add("45 mins ago");
        trendingchallengelike.add("3.2K");
        trendingchallengelike.add("5.6K");
        trendingchallengelike.add("10");
        trendingchallengecomments.add("128");
        trendingchallengecomments.add("23");
        trendingchallengecomments.add("5K");
        trendingchallengeviews.add("15K");
        trendingchallengeviews.add("8K");
        trendingchallengeviews.add("106");

        SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);


        if (pref.contains("liked")){

            if (Constant.isOnline(Home_Activity.this)){

                HomeApi();
            }
            else{
                Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));

            }

        }
        else {
            if (pref.contains("home_indicator")){

                if (getIntent().hasExtra("refresh")){
                    if (Constant.isOnline(Home_Activity.this)){

                        HomeApi();
                    }
                    else{
                        Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            if (Constant.isOnline(Home_Activity.this)){

                                setDataWithoutApi();
                            }
                            else{

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                    }
                                });
                            }

                        }
                    });

                }

            }
            else {
                if (Constant.isOnline(Home_Activity.this)){

                    HomeApi();
                }
                else{
                    Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }
            }
        }
    }

    private void FindByIds(){
        loadmore = findViewById(R.id.loadmore);
        root = findViewById(R.id.root);
        sv = findViewById(R.id.sv);
        chat = findViewById(R.id.chat);
        capmpaigns_heading = findViewById(R.id.capmpaigns_heading);
        showall_campaigns = findViewById(R.id.showall_campaigns);
        nearchallenges_heading = findViewById(R.id.nearchallenges_heading);
        showall_nearchallenges = findViewById(R.id.showall_nearchallenges);
        trendingchallenges_heading = findViewById(R.id.trendingchallenges_heading);
        showall_trendingchallenges = findViewById(R.id.showall_trendingchallenges);
        campaignsRV = findViewById(R.id.campaignsRV);
        nearchallengesRV = findViewById(R.id.nearchallengesRV);
        trendingchallengesRV = findViewById(R.id.trendingchallengesRV);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        fulllogo = findViewById(R.id.fulllogo);

        notrending = findViewById(R.id.notrending);
        nonearby = findViewById(R.id.nonearby);
        nocampaigns = findViewById(R.id.nocampaigns);

        layrefreshlayout = findViewById(R.id.swipeRefreshLayout);

    }

    private void setFonts(){

        loadmore.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        capmpaigns_heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        showall_campaigns.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));

        nearchallenges_heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        showall_nearchallenges.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));

        trendingchallenges_heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        showall_trendingchallenges.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));

        notrending.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        nonearby.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        nocampaigns.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));


    }

    private void setListeners(){
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();

/*
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
*/

/*
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
*/

            }
        });
        showall_campaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this,Show_All_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","campaign");
                intent.putStringArrayListExtra("usernamelist",Cusernamelist);
                intent.putStringArrayListExtra("userimagelist",Cuserimagelist);
                intent.putStringArrayListExtra("cnamelist",Ccnamelist);
                intent.putStringArrayListExtra("cimagelist",Ccimagelist);
                intent.putStringArrayListExtra("cidlist",Ccidlist);
                intent.putStringArrayListExtra("cdescriptionlist",Ccdescriptionlist);
                intent.putStringArrayListExtra("likeslist",Clikeslist);
                intent.putStringArrayListExtra("commentslist",Ccommentslist);
                intent.putStringArrayListExtra("tagslist",Ctagslist);
                intent.putStringArrayListExtra("fontslist",Ccfontlist);
                intent.putStringArrayListExtra("views",Cviews);
                intent.putExtra("country",country);
                startActivity(intent);
            }
        });
        showall_nearchallenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this,Show_All_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","nearbychallenge");
                intent.putStringArrayListExtra("usernamelist",usernamelist);
                intent.putStringArrayListExtra("userimagelist",userimagelist);
                intent.putStringArrayListExtra("cnamelist",cnamelist);
                intent.putStringArrayListExtra("cimagelist",cimagelist);
                intent.putStringArrayListExtra("cidlist",cidlist);
                intent.putStringArrayListExtra("cdescriptionlist",cdescriptionlist);
                intent.putStringArrayListExtra("likeslist",likeslist);
                intent.putStringArrayListExtra("commentslist",commentslist);
                intent.putStringArrayListExtra("tagslist",tagslist);
                intent.putStringArrayListExtra("fontslist",cfontlist);
                intent.putStringArrayListExtra("views",views);
                intent.putExtra("country",country);

                startActivity(intent);
            }
        });
        showall_trendingchallenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.fullScroll(ScrollView.FOCUS_UP);

            }
        });


        layrefreshlayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                layrefreshlayout.post(new Runnable() {

                    @Override
                    public void run() {
                        layrefreshlayout.setRefreshing(true);

                        //HomeApi2();
                        if (Constant.isOnline(Home_Activity.this)){

                            HomeApi2();
                        }
                        else{
                            Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));

                        }
                    }
                });
            }
        });

        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.isOnline(Home_Activity.this)){

                    loadMoreApi();
                }
                else{
                    Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }

            }
        });

    }

    private void HomeApi(){

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");
        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://bossble.com/api/post/post/get_challenges?user_id="+u_id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("HomeResp",response);

                avibackground.post(new Runnable() {

                    @Override
                    public void run() {
                        avibackground.setVisibility(View.GONE);
                        avi.smoothToHide();
                    }
                });


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){



                        pref.edit().remove("liked").commit();

                        pref.edit().putString("home_indicator","yes").commit();


                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        JSONArray campaign = dataSet.getJSONArray("campaign");
                        JSONArray location_challenge = dataSet.getJSONArray("location_challenge");
                        JSONArray open_challenge = dataSet.getJSONArray("open_challenge");
                        JSONArray one_on_one_challenge = dataSet.getJSONArray("one_on_one_challenge");
                        JSONArray self_challenge = dataSet.getJSONArray("self_challenge");
                        JSONArray group_challenge = dataSet.getJSONArray("group_challenge");

                        opencount = open_challenge.length();
                        oneononecount = one_on_one_challenge.length();
                        selfcount = self_challenge.length();
                        groupcount = group_challenge.length();

                        pref.edit().putString("opencount",String.valueOf(opencount)).commit();
                        pref.edit().putString("oneononecount",String.valueOf(oneononecount)).commit();
                        pref.edit().putString("selfcount",String.valueOf(selfcount)).commit();
                        pref.edit().putString("groupcount",String.valueOf(groupcount)).commit();

                        if (campaign.length()>0){
                            for (int i=0; i<campaign.length(); i++){

                                Campaigns campaigns = new Campaigns();

                                JSONObject item = campaign.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");

                                country = item.getString("country");
                                campaigns.setId(id);
                                campaigns.setUser_id(user_id);
                                campaigns.setTitle(title);
                                campaigns.setDescritpion(descritpion);
                                campaigns.setTags(tags);
                                campaigns.setCreated_at(created_at);
                                campaigns.setUsername(username);
                                campaigns.setProfile_image(profile_image);
                                campaigns.setDescription_background(description_background);
                                campaigns.setDescription_fonts(description_fonts);
                                campaigns.setComments_count(comments_count);
                                campaigns.setLike_count(like_count);
                                campaigns.setChallenge_attempted_count(challenge_attempted_count);
                                campaigns.setVideo(video);
                                campaigns.setImage(image);
                                campaigns.setView_count(view_count);
                                campaigns.setCountry(country);

                                campaignslist.add(campaigns);

                                Clikeslist.add(like_count);
                                Ccommentslist.add(comments_count);
                                Ctagslist.add(tags);
                                Cusernamelist.add(username);
                                Cuserimagelist.add(profile_image);
                                Ccnamelist.add(title);
                                Ccfontlist.add(description_fonts);
                                Cviews.add(view_count);

                                if (!image.equals("")){
                                    Ccimagelist.add(image);
                                }
                                else if (!video.equals("")){
                                    Ccimagelist.add(video);
                                }
                                else if (!description_background.equals("")){
                                    Ccimagelist.add(description_background);
                                }
                                else {
                                    Ccimagelist.add("");
                                }

                                Ccidlist.add(id);
                                Ccdescriptionlist.add(descritpion);
                                // preferences





                                nocampaigns.setVisibility(View.GONE);
                                if (campaignslist.isEmpty()){
                                    nocampaigns.setVisibility(View.VISIBLE);
                                }

                                Gson gson = new Gson();
                                String json = gson.toJson(campaignslist);
                                pref.edit().putString("campaignslist",json).commit();


                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {



                                        Gson gsonn = new Gson();

                                        String jsonText7 = gsonn.toJson(Clikeslist);
                                        pref.edit().putString("Clikeslist",jsonText7).commit();

                                        String jsonText8 = gsonn.toJson(Ccommentslist);
                                        pref.edit().putString("Ccommentslist",jsonText8).commit();

                                        String jsonText9 = gsonn.toJson(Ctagslist);
                                        pref.edit().putString("Ctagslist",jsonText9).commit();

                                        String jsonText10 = gsonn.toJson(Cusernamelist);
                                        pref.edit().putString("Cusernamelist",jsonText10).commit();

                                        String jsonText11 = gsonn.toJson(Cuserimagelist);
                                        pref.edit().putString("Cuserimagelist",jsonText11).commit();


                                        String jsonText = gsonn.toJson(Ccnamelist);
                                        pref.edit().putString("Ccnamelist",jsonText).commit();

                                        String jsonText2 = gsonn.toJson(Ccfontlist);
                                        pref.edit().putString("Ccfontlist",jsonText2).commit();


                                        String jsonText3 = gsonn.toJson(Cviews);
                                        pref.edit().putString("Cviews",jsonText3).commit();


                                        String jsonText4 = gsonn.toJson(Ccimagelist);
                                        pref.edit().putString("Ccimagelist",jsonText4).commit();


                                        String jsonText5 = gsonn.toJson(Ccidlist);
                                        pref.edit().putString("Ccidlist",jsonText5).commit();

                                        String jsonText6 = gsonn.toJson(Ccdescriptionlist);
                                        pref.edit().putString("Ccdescriptionlist",jsonText6).commit();

                                    }
                                });

                                campaignAdapter = new CampaignAdapter(Home_Activity.this,campaignslist);
                                campaignsRV.setAdapter(campaignAdapter);
                                campaignAdapter.notifyDataSetChanged();


                            }
                        }
                        if (location_challenge.length()>0){
                            for (int i=0; i<location_challenge.length(); i++){

                                NearBy_Challenges nearBy_challenges = new NearBy_Challenges();

                                JSONObject item = location_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");
                                country = item.getString("country");
                                if (item.has("address")){
                                    String address = item.getString("address");

                                    nearBy_challenges.setAddress(address);

                                }
                                else {
                                    nearBy_challenges.setAddress("");
                                }

                                nearBy_challenges.setId(id);
                                nearBy_challenges.setUser_id(user_id);
                                nearBy_challenges.setTitle(title);
                                nearBy_challenges.setDescritpion(descritpion);
                                nearBy_challenges.setTags(tags);
                                nearBy_challenges.setCreated_at(created_at);
                                nearBy_challenges.setUsername(username);
                                nearBy_challenges.setProfile_image(profile_image);
                                nearBy_challenges.setDescription_background(description_background);
                                nearBy_challenges.setDescription_fonts(description_fonts);
                                nearBy_challenges.setComments_count(comments_count);
                                nearBy_challenges.setLike_count(like_count);
                                nearBy_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                nearBy_challenges.setVideo(video);
                                nearBy_challenges.setImage(image);
                                nearBy_challenges.setCountry(country);
                                nearBy_challenges.setView_count(view_count);
                                nearbylist.add(nearBy_challenges);

                                likeslist.add(like_count);
                                commentslist.add(comments_count);
                                tagslist.add(tags);
                                usernamelist.add(username);
                                userimagelist.add(profile_image);
                                cnamelist.add(title);
                                cfontlist.add(description_fonts);
                                views.add(view_count);
                                if (!image.equals("")){
                                    cimagelist.add(image);

                                }
                                else if (!video.equals("")){
                                    cimagelist.add(video);

                                }
                                else if (!description_background.equals("")){
                                    cimagelist.add(description_background);

                                }
                                else {
                                    cimagelist.add("");
                                }


                                cidlist.add(id);
                                cdescriptionlist.add(descritpion);

                                nonearby.setVisibility(View.GONE);
                                if (nearbylist.isEmpty()){
                                    nonearby.setVisibility(View.VISIBLE);
                                }

                                Gson gson = new Gson();
                                String json = gson.toJson(nearbylist);
                                pref.edit().putString("nearbylist",json).commit();


                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {



                                        Gson gsonn = new Gson();

                                        String jsonText7 = gsonn.toJson(likeslist);
                                        pref.edit().putString("likeslist",jsonText7).commit();

                                        String jsonText8 = gsonn.toJson(commentslist);
                                        pref.edit().putString("commentslist",jsonText8).commit();

                                        String jsonText9 = gsonn.toJson(tagslist);
                                        pref.edit().putString("tagslist",jsonText9).commit();

                                        String jsonText10 = gsonn.toJson(usernamelist);
                                        pref.edit().putString("usernamelist",jsonText10).commit();

                                        String jsonText11 = gsonn.toJson(userimagelist);
                                        pref.edit().putString("userimagelist",jsonText11).commit();

                                        String jsonText = gsonn.toJson(cnamelist);
                                        pref.edit().putString("cnamelist",jsonText).commit();

                                        String jsonText2 = gsonn.toJson(cfontlist);
                                        pref.edit().putString("cfontlist",jsonText2).commit();


                                        String jsonText3 = gsonn.toJson(views);
                                        pref.edit().putString("views",jsonText3).commit();


                                        String jsonText4 = gsonn.toJson(cimagelist);
                                        pref.edit().putString("cimagelist",jsonText4).commit();


                                        String jsonText5 = gsonn.toJson(cidlist);
                                        pref.edit().putString("cidlist",jsonText5).commit();

                                        String jsonText6 = gsonn.toJson(cdescriptionlist);
                                        pref.edit().putString("cdescriptionlist",jsonText6).commit();

                                    }
                                });

                                nearByChallengeAdapter = new NearByChallengeAdapter(Home_Activity.this,nearbylist);
                                nearchallengesRV.setAdapter(nearByChallengeAdapter);
                                nearByChallengeAdapter.notifyDataSetChanged();

                            }
                        }

                        if (open_challenge.length()>0){

                            for (int i=0; i<open_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = open_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");
                                country = item.getString("country");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);

                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");


                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }

                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }



                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);


                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);

                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (one_on_one_challenge.length()>0){

                            for (int i=0; i<one_on_one_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = one_on_one_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);


                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);



                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");

                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);

                                if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                    SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                    String myid = preferences.getString(Constant.USER_ID,"");

                                    JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                    if (challeneg_join_member.length()>0){

                                        for (int k=0; k<challeneg_join_member.length(); k++){

                                            JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                            String join_id = join_item.getString("id");
                                            if (join_id.equals(myid)){

                                                trending_challenges.setJoinuser("yes");
                                                break;
                                            }
                                        }
                                    }
                                }


                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }

                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);
                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (self_challenge.length()>0){

                            for (int i=0; i<self_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = self_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);


                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);



                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");



                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }

                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);


                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);
                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (group_challenge.length()>0){

                            for (int i=0; i<group_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = group_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);

                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");

                                if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                    SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                    String myid = preferences.getString(Constant.USER_ID,"");

                                    JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                    if (challeneg_join_member.length()>0){

                                        for (int k=0; k<challeneg_join_member.length(); k++){

                                            JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                            String join_id = join_item.getString("id");
                                            if (join_id.equals(myid)){

                                                trending_challenges.setJoinuser("yes");
                                                break;
                                            }
                                        }
                                    }
                                }


                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }

                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }
                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);
                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);

                                trendinglist.add(trending_challenges);
                            }
                        }

                        Gson gson = new Gson();
                        String json = gson.toJson(trendinglist);
                        pref.edit().putString("trendinglist",json).commit();


                        trendingChallengesAdapter = new TrendingChallengesAdapter(Home_Activity.this,trendinglist,Home_Activity.this,avi,avibackground);
                        trendingchallengesRV.setAdapter(trendingChallengesAdapter);
                        trendingChallengesAdapter.notifyDataSetChanged();
                        chat.setVisibility(View.VISIBLE);
                        capmpaigns_heading.setVisibility(View.VISIBLE);
                        showall_campaigns.setVisibility(View.VISIBLE);
                        nearchallenges_heading.setVisibility(View.VISIBLE);
                        showall_nearchallenges.setVisibility(View.VISIBLE);
                        trendingchallenges_heading.setVisibility(View.VISIBLE);
                        loadmore.setVisibility(View.VISIBLE);
                        showall_trendingchallenges.setVisibility(View.GONE);

                    }
                    else if (status.equals("false")){

                        // Toast.makeText(getApplicationContext(),jsonObject.getString("")"Something went wrong",Toast.LENGTH_SHORT).show();
                        chat.setVisibility(View.VISIBLE);
                        capmpaigns_heading.setVisibility(View.VISIBLE);
                        showall_campaigns.setVisibility(View.VISIBLE);
                        nearchallenges_heading.setVisibility(View.VISIBLE);
                        showall_nearchallenges.setVisibility(View.VISIBLE);
                        trendingchallenges_heading.setVisibility(View.VISIBLE);
                        showall_trendingchallenges.setVisibility(View.GONE);
                        loadmore.setVisibility(View.GONE);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("HomeRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false")){

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }



        });

    }

    private void HomeApi2(){


        campaignslist.clear();
        nearbylist.clear();
        trendingusername.clear();
        trendinguserimage.clear();
        trendingcoingimage.clear();
        trendingchallengegimage.clear();
        trendingchallengename.clear();
        trendingchallengetime.clear();
        trendingchallengelike.clear();
        trendingchallengecomments.clear();
        trendingchallengeviews.clear();

        trendinglist.clear();

        usernamelist.clear();
        userimagelist.clear();
        cimagelist.clear();
        cnamelist.clear();
        cidlist.clear();
        cdescriptionlist.clear();
        likeslist.clear();
        commentslist.clear();
        tagslist.clear();
        cfontlist.clear();
        views.clear();

        Cusernamelist.clear();
        Cuserimagelist.clear();
        Ccimagelist.clear();
        Ccnamelist.clear();
        Ccidlist.clear();
        Ccdescriptionlist.clear();
        Clikeslist.clear();
        Ccommentslist.clear();
        Ctagslist.clear();
        Ccfontlist.clear();
        Cviews.clear();
        campaign_multiple.clear();



        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();
        chat.setVisibility(View.GONE);
        capmpaigns_heading.setVisibility(View.GONE);
        showall_campaigns.setVisibility(View.GONE);
        nearchallenges_heading.setVisibility(View.GONE);
        showall_nearchallenges.setVisibility(View.GONE);
        trendingchallenges_heading.setVisibility(View.GONE);
        showall_trendingchallenges.setVisibility(View.GONE);
        loadmore.setVisibility(View.GONE);

        campaignsRV.setVisibility(View.GONE);
        nearchallengesRV.setVisibility(View.GONE);
        trendingchallengesRV.setVisibility(View.GONE);



        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://bossble.com/api/post/post/get_challenges?user_id="+u_id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("HomeResp",response);

                layrefreshlayout.post(new Runnable() {

                    @Override
                    public void run() {
                        layrefreshlayout.setRefreshing(false);
                        avibackground.setVisibility(View.GONE);
                        avi.smoothToHide();
                    }
                });

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){

                        pref.edit().putString("home_indicator","yes").commit();


                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray campaign = dataSet.getJSONArray("campaign");
                        JSONArray location_challenge = dataSet.getJSONArray("location_challenge");

                        JSONArray open_challenge = dataSet.getJSONArray("open_challenge");
                        JSONArray one_on_one_challenge = dataSet.getJSONArray("one_on_one_challenge");
                        JSONArray self_challenge = dataSet.getJSONArray("self_challenge");
                        JSONArray group_challenge = dataSet.getJSONArray("group_challenge");

                        opencount = open_challenge.length();
                        oneononecount = one_on_one_challenge.length();
                        selfcount = self_challenge.length();
                        groupcount = group_challenge.length();

                        pref.edit().putString("opencount",String.valueOf(opencount)).commit();
                        pref.edit().putString("oneononecount",String.valueOf(oneononecount)).commit();
                        pref.edit().putString("selfcount",String.valueOf(selfcount)).commit();
                        pref.edit().putString("groupcount",String.valueOf(groupcount)).commit();

                        if (campaign.length()>0){
                            for (int i=0; i<campaign.length(); i++){

                                Campaigns campaigns = new Campaigns();

                                JSONObject item = campaign.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");

                                country = item.getString("country");
                                campaigns.setId(id);
                                campaigns.setUser_id(user_id);
                                campaigns.setTitle(title);
                                campaigns.setDescritpion(descritpion);
                                campaigns.setTags(tags);
                                campaigns.setCreated_at(created_at);
                                campaigns.setUsername(username);
                                campaigns.setProfile_image(profile_image);
                                campaigns.setDescription_background(description_background);
                                campaigns.setDescription_fonts(description_fonts);
                                campaigns.setComments_count(comments_count);
                                campaigns.setLike_count(like_count);
                                campaigns.setChallenge_attempted_count(challenge_attempted_count);
                                campaigns.setVideo(video);
                                campaigns.setImage(image);
                                campaigns.setView_count(view_count);
                                campaigns.setCountry(country);
                                campaignslist.add(campaigns);
                                Clikeslist.add(like_count);
                                Ccommentslist.add(comments_count);
                                Ctagslist.add(tags);
                                Cusernamelist.add(username);
                                Cuserimagelist.add(profile_image);
                                Ccnamelist.add(title);
                                Ccfontlist.add(description_fonts);
                                Cviews.add(view_count);
                                if (!image.equals("")){
                                    Ccimagelist.add(image);

                                }
                                else if (!video.equals("")){
                                    Ccimagelist.add(video);

                                }
                                else if (!description_background.equals("")){
                                    Ccimagelist.add(description_background);

                                }
                                else {
                                    Ccimagelist.add("");

                                }
                                Ccidlist.add(id);
                                Ccdescriptionlist.add(descritpion);

                                nocampaigns.setVisibility(View.GONE);
                                if (campaignslist.isEmpty()){
                                    nocampaigns.setVisibility(View.VISIBLE);
                                }

                                Gson gson = new Gson();
                                String json = gson.toJson(campaignslist);
                                pref.edit().putString("campaignslist",json).commit();


                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {



                                        Gson gsonn = new Gson();

                                        String jsonText7 = gsonn.toJson(Clikeslist);
                                        pref.edit().putString("Clikeslist",jsonText7).commit();

                                        String jsonText8 = gsonn.toJson(Ccommentslist);
                                        pref.edit().putString("Ccommentslist",jsonText8).commit();

                                        String jsonText9 = gsonn.toJson(Ctagslist);
                                        pref.edit().putString("Ctagslist",jsonText9).commit();

                                        String jsonText10 = gsonn.toJson(Cusernamelist);
                                        pref.edit().putString("Cusernamelist",jsonText10).commit();

                                        String jsonText11 = gsonn.toJson(Cuserimagelist);
                                        pref.edit().putString("Cuserimagelist",jsonText11).commit();


                                        String jsonText = gsonn.toJson(Ccnamelist);
                                        pref.edit().putString("Ccnamelist",jsonText).commit();

                                        String jsonText2 = gsonn.toJson(Ccfontlist);
                                        pref.edit().putString("Ccfontlist",jsonText2).commit();


                                        String jsonText3 = gsonn.toJson(Cviews);
                                        pref.edit().putString("Cviews",jsonText3).commit();


                                        String jsonText4 = gsonn.toJson(Ccimagelist);
                                        pref.edit().putString("Ccimagelist",jsonText4).commit();


                                        String jsonText5 = gsonn.toJson(Ccidlist);
                                        pref.edit().putString("Ccidlist",jsonText5).commit();

                                        String jsonText6 = gsonn.toJson(Ccdescriptionlist);
                                        pref.edit().putString("Ccdescriptionlist",jsonText6).commit();
                                    }
                                });







                                campaignAdapter = new CampaignAdapter(Home_Activity.this,campaignslist);
                                campaignsRV.setAdapter(campaignAdapter);
                                campaignAdapter.notifyDataSetChanged();


                            }
                        }
                        if (location_challenge.length()>0){
                            for (int i=0; i<location_challenge.length(); i++){

                                NearBy_Challenges nearBy_challenges = new NearBy_Challenges();

                                JSONObject item = location_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");
                                country = item.getString("country");
                                if (item.has("address")){
                                    String address = item.getString("address");

                                    nearBy_challenges.setAddress(address);

                                }
                                else {
                                    nearBy_challenges.setAddress("");
                                }
                                nearBy_challenges.setId(id);
                                nearBy_challenges.setUser_id(user_id);
                                nearBy_challenges.setTitle(title);
                                nearBy_challenges.setDescritpion(descritpion);
                                nearBy_challenges.setTags(tags);
                                nearBy_challenges.setCreated_at(created_at);
                                nearBy_challenges.setUsername(username);
                                nearBy_challenges.setProfile_image(profile_image);
                                nearBy_challenges.setDescription_background(description_background);
                                nearBy_challenges.setDescription_fonts(description_fonts);
                                nearBy_challenges.setComments_count(comments_count);
                                nearBy_challenges.setLike_count(like_count);
                                nearBy_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                nearBy_challenges.setVideo(video);
                                nearBy_challenges.setImage(image);
                                nearBy_challenges.setCountry(country);
                                nearBy_challenges.setView_count(view_count);
                                nearbylist.add(nearBy_challenges);

                                likeslist.add(like_count);
                                commentslist.add(comments_count);
                                tagslist.add(tags);
                                usernamelist.add(username);
                                userimagelist.add(profile_image);
                                cnamelist.add(title);
                                cfontlist.add(description_fonts);
                                views.add(view_count);
                                if (!image.equals("")){
                                    cimagelist.add(image);

                                }
                                else if (!video.equals("")){
                                    cimagelist.add(video);

                                }
                                else if (!description_background.equals("")){
                                    cimagelist.add(description_background);

                                }
                                else {
                                    cimagelist.add("");
                                }


                                cidlist.add(id);
                                cdescriptionlist.add(descritpion);

                                nonearby.setVisibility(View.GONE);
                                if (nearbylist.isEmpty()){
                                    nonearby.setVisibility(View.VISIBLE);
                                }

                                Gson gson = new Gson();
                                String json = gson.toJson(nearbylist);
                                pref.edit().putString("nearbylist",json).commit();


                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {


                                        Gson gsonn = new Gson();

                                        String jsonText7 = gsonn.toJson(likeslist);
                                        pref.edit().putString("likeslist",jsonText7).commit();

                                        String jsonText8 = gsonn.toJson(commentslist);
                                        pref.edit().putString("commentslist",jsonText8).commit();

                                        String jsonText9 = gsonn.toJson(tagslist);
                                        pref.edit().putString("tagslist",jsonText9).commit();

                                        String jsonText10 = gsonn.toJson(usernamelist);
                                        pref.edit().putString("usernamelist",jsonText10).commit();

                                        String jsonText11 = gsonn.toJson(userimagelist);
                                        pref.edit().putString("userimagelist",jsonText11).commit();

                                        String jsonText = gsonn.toJson(cnamelist);
                                        pref.edit().putString("cnamelist",jsonText).commit();

                                        String jsonText2 = gsonn.toJson(cfontlist);
                                        pref.edit().putString("cfontlist",jsonText2).commit();


                                        String jsonText3 = gsonn.toJson(views);
                                        pref.edit().putString("views",jsonText3).commit();


                                        String jsonText4 = gsonn.toJson(cimagelist);
                                        pref.edit().putString("cimagelist",jsonText4).commit();


                                        String jsonText5 = gsonn.toJson(cidlist);
                                        pref.edit().putString("cidlist",jsonText5).commit();

                                        String jsonText6 = gsonn.toJson(cdescriptionlist);
                                        pref.edit().putString("cdescriptionlist",jsonText6).commit();                                    }
                                });



                                nearByChallengeAdapter = new NearByChallengeAdapter(Home_Activity.this,nearbylist);
                                nearchallengesRV.setAdapter(nearByChallengeAdapter);
                                nearByChallengeAdapter.notifyDataSetChanged();

                            }
                        }

                        if (open_challenge.length()>0){

                            for (int i=0; i<open_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = open_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                String view_count = item.getString("view_count");
                                country = item.getString("country");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);

                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");

                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);

                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }
                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);

                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (one_on_one_challenge.length()>0){

                            for (int i=0; i<one_on_one_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = one_on_one_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");

                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);



                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");

                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);

                                if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                    SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                    String myid = preferences.getString(Constant.USER_ID,"");

                                    JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                    if (challeneg_join_member.length()>0){

                                        for (int k=0; k<challeneg_join_member.length(); k++){

                                            JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                            String join_id = join_item.getString("id");
                                            if (join_id.equals(myid)){

                                                trending_challenges.setJoinuser("yes");
                                                break;
                                            }
                                        }
                                    }
                                }



                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }
                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);
                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (self_challenge.length()>0){

                            for (int i=0; i<self_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = self_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");
                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);



                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");



                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);

                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }
                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);
                                trendinglist.add(trending_challenges);
                            }
                        }
                        if (group_challenge.length()>0){

                            for (int i=0; i<group_challenge.length(); i++){
                                Trending_Challenges trending_challenges = new Trending_Challenges();
                                JSONObject item = group_challenge.getJSONObject(i);
                                String id = item.getString("id");
                                String user_id = item.getString("user_id");
                                String title = item.getString("title");
                                String descritpion = item.getString("descritpion");
                                String tags = item.getString("tags");
                                String created_at = item.getString("created_at");
                                String username = item.getString("username");
                                String profile_image = item.getString("profile_image");
                                String description_background = item.getString("description_background");
                                String description_fonts = item.getString("description_fonts");
                                String comments_count = item.getString("comments_count");
                                String like_count = item.getString("like_count");
                                String challenge_attempted_count = item.getString("challenge_attempted_count");
                                String video = item.getString("video");
                                String image = item.getString("image");
                                country = item.getString("country");
                                String view_count = item.getString("view_count");
                                String challenge_title = item.getString("challenge_title");
                                trending_challenges.setChallenge_title(challenge_title);

                                String user_like = item.getString("user_like");
                                String user_like_reward = item.getString("user_like_reward");

                                trending_challenges.setUser_like(user_like);
                                trending_challenges.setUser_like_reward(user_like_reward);

                                JSONObject like_type_count = item.getJSONObject("like_type_count");
                                String silver = like_type_count.getString("silver");
                                String gold = like_type_count.getString("gold");
                                String bronze = like_type_count.getString("bronze");

                                trending_challenges.setSilver(silver);
                                trending_challenges.setGold(gold);
                                trending_challenges.setBronze(bronze);

                                if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                    SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                    String myid = preferences.getString(Constant.USER_ID,"");

                                    JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                    if (challeneg_join_member.length()>0){

                                        for (int k=0; k<challeneg_join_member.length(); k++){

                                            JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                            String join_id = join_item.getString("id");
                                            if (join_id.equals(myid)){

                                                trending_challenges.setJoinuser("yes");
                                                break;
                                            }
                                        }
                                    }
                                }



                                if (item.get("challenge_reply") instanceof JSONArray){

                                    JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                    if (challenge_reply.length()>0){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String usid = preferences.getString(Constant.USER_ID,"");
                                        for (int j=0; j<challenge_reply.length();j++){

                                            JSONObject useridItem = challenge_reply.getJSONObject(j);

                                            String alluserid = useridItem.getString("user_id");
                                            if (usid.equals(alluserid)){
                                                trending_challenges.setAll_userid("yes");
                                                break;
                                            }

                                        }


                                        String count = String.valueOf(challenge_reply.length());
                                        trending_challenges.setCount(count);

                                        if (challenge_reply.length()==1){

                                            JSONObject item2 = challenge_reply.getJSONObject(0);
                                            String profile_imag = item2.getString("profile_image");
                                            final String id1 = item2.getString("id");
                                            final String user_id1 = item2.getString("user_id");
                                            final String username1 = item2.getString("username");
                                            final String like1 = item2.getString("like_count");
                                            final String comments_count1 = item2.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item2.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item2.getString("challenge_title");

                                            trending_challenges.setProfile_img1(profile_imag);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==2){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);

                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");


                                        }
                                        else if (challenge_reply.length()==3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);

                                        }
                                        else if (challenge_reply.length()>3){

                                            JSONObject item1 = challenge_reply.getJSONObject(0);

                                            String profile_image1 = item1.getString("profile_image");
                                            final String id1 = item1.getString("id");
                                            final String user_id1 = item1.getString("user_id");
                                            final String username1 = item1.getString("username");
                                            final String like1 = item1.getString("like_count");
                                            final String comments_count1 = item1.getString("comments_count");
                                            final String title1 = title;
                                            final String description1 = descritpion;
                                            final String tags1 = tags;
                                            final String country1 = item1.getString("country");
                                            final String view_count1 = "0";
                                            final String challenge_title1 = item1.getString("challenge_title");

                                            JSONObject item2 = challenge_reply.getJSONObject(1);
                                            String profile_image2 = item2.getString("profile_image");
                                            final String id2 = item2.getString("id");
                                            final String user_id2 = item2.getString("user_id");
                                            final String username2 = item2.getString("username");
                                            final String like2 = item2.getString("like_count");
                                            final String comments_count2 = item2.getString("comments_count");
                                            final String title2 = title;
                                            final String description2 = descritpion;
                                            final String tags2 = tags;
                                            final String country2 = item2.getString("country");
                                            final String view_count2 = "0";
                                            final String challenge_title2 = item2.getString("challenge_title");


                                            JSONObject item3 = challenge_reply.getJSONObject(2);
                                            String profile_image3 = item3.getString("profile_image");
                                            final String id3 = item3.getString("id");
                                            final String user_id3 = item3.getString("user_id");
                                            final String username3 = item3.getString("username");
                                            final String like3 = item3.getString("like_count");
                                            final String comments_count3 = item3.getString("comments_count");
                                            final String title3 = title;
                                            final String description3 = descritpion;
                                            final String tags3 = tags;
                                            final String country3 = item3.getString("country");
                                            final String view_count3 = "0";
                                            final String challenge_title3 = item3.getString("challenge_title");



                                            trending_challenges.setProfile_img1(profile_image1);
                                            trending_challenges.setId1(id1);
                                            trending_challenges.setUser_id1(user_id1);
                                            trending_challenges.setUsername1(username1);
                                            trending_challenges.setLike1(like1);
                                            trending_challenges.setComments_count1(comments_count1);
                                            trending_challenges.setTitle1(title1);
                                            trending_challenges.setDescription1(description1);
                                            trending_challenges.setTags1(tags1);
                                            trending_challenges.setCountry1(country1);
                                            trending_challenges.setView_count1(view_count1);
                                            trending_challenges.setChallenge_title1(challenge_title1);

                                            trending_challenges.setProfile_img2(profile_image2);
                                            trending_challenges.setId2(id2);
                                            trending_challenges.setUser_id2(user_id2);
                                            trending_challenges.setUsername2(username2);
                                            trending_challenges.setLike2(like2);
                                            trending_challenges.setComments_count2(comments_count2);
                                            trending_challenges.setTitle2(title2);
                                            trending_challenges.setDescription2(description2);
                                            trending_challenges.setTags2(tags2);
                                            trending_challenges.setCountry2(country2);
                                            trending_challenges.setView_count2(view_count2);
                                            trending_challenges.setChallenge_title2(challenge_title2);

                                            trending_challenges.setProfile_img3(profile_image3);
                                            trending_challenges.setId3(id3);
                                            trending_challenges.setUser_id3(user_id3);
                                            trending_challenges.setUsername3(username3);
                                            trending_challenges.setLike3(like3);
                                            trending_challenges.setComments_count3(comments_count3);
                                            trending_challenges.setTitle3(title3);
                                            trending_challenges.setDescription3(description3);
                                            trending_challenges.setTags3(tags3);
                                            trending_challenges.setCountry3(country3);
                                            trending_challenges.setView_count3(view_count3);
                                            trending_challenges.setChallenge_title3(challenge_title3);
                                        }
                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");
                                    }

                                }
                                else {
                                    trending_challenges.setAll_userid("");
                                    trending_challenges.setCount("0");

                                    trending_challenges.setProfile_img1("no");
                                    trending_challenges.setId1("no");
                                    trending_challenges.setUser_id1("no");
                                    trending_challenges.setUsername1("no");
                                    trending_challenges.setLike1("no");
                                    trending_challenges.setComments_count1("no");
                                    trending_challenges.setTitle1("no");
                                    trending_challenges.setDescription1("no");
                                    trending_challenges.setTags1("no");
                                    trending_challenges.setCountry1("no");
                                    trending_challenges.setView_count1("no");
                                    trending_challenges.setChallenge_title1("no");

                                    trending_challenges.setProfile_img2("no");
                                    trending_challenges.setId2("no");
                                    trending_challenges.setUser_id2("no");
                                    trending_challenges.setUsername2("no");
                                    trending_challenges.setLike2("no");
                                    trending_challenges.setComments_count2("no");
                                    trending_challenges.setTitle2("no");
                                    trending_challenges.setDescription2("no");
                                    trending_challenges.setTags2("no");
                                    trending_challenges.setCountry2("no");
                                    trending_challenges.setView_count2("no");
                                    trending_challenges.setChallenge_title2("no");

                                    trending_challenges.setProfile_img3("no");
                                    trending_challenges.setId3("no");
                                    trending_challenges.setUser_id3("no");
                                    trending_challenges.setUsername3("no");
                                    trending_challenges.setLike3("no");
                                    trending_challenges.setComments_count3("no");
                                    trending_challenges.setTitle3("no");
                                    trending_challenges.setDescription3("no");
                                    trending_challenges.setTags3("no");
                                    trending_challenges.setCountry3("no");
                                    trending_challenges.setView_count3("no");
                                    trending_challenges.setChallenge_title3("no");

                                }
                                trending_challenges.setId(id);
                                trending_challenges.setUser_id(user_id);
                                trending_challenges.setTitle(title);
                                trending_challenges.setDescritpion(descritpion);
                                trending_challenges.setTags(tags);
                                trending_challenges.setCreated_at(created_at);
                                trending_challenges.setUsername(username);
                                trending_challenges.setProfile_image(profile_image);
                                trending_challenges.setDescription_background(description_background);
                                trending_challenges.setDescription_fonts(description_fonts);
                                trending_challenges.setComments_count(comments_count);
                                trending_challenges.setLike_count(like_count);
                                trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                trending_challenges.setVideo(video);
                                trending_challenges.setImage(image);
                                trending_challenges.setCountry(country);
                                trending_challenges.setView_count(view_count);

                                trendinglist.add(trending_challenges);
                            }
                        }

                        Gson gson = new Gson();
                        String json = gson.toJson(trendinglist);
                        pref.edit().putString("trendinglist",json).commit();



                        trendingChallengesAdapter = new TrendingChallengesAdapter(Home_Activity.this,trendinglist,Home_Activity.this,avi,avibackground);
                        trendingchallengesRV.setAdapter(trendingChallengesAdapter);
                        trendingChallengesAdapter.notifyDataSetChanged();
                        chat.setVisibility(View.VISIBLE);
                        capmpaigns_heading.setVisibility(View.VISIBLE);
                        showall_campaigns.setVisibility(View.VISIBLE);
                        nearchallenges_heading.setVisibility(View.VISIBLE);
                        showall_nearchallenges.setVisibility(View.VISIBLE);
                        trendingchallenges_heading.setVisibility(View.VISIBLE);
                        loadmore.setVisibility(View.VISIBLE);
                        showall_trendingchallenges.setVisibility(View.GONE);

                        campaignsRV.setVisibility(View.VISIBLE);
                        nearchallengesRV.setVisibility(View.VISIBLE);
                        trendingchallengesRV.setVisibility(View.VISIBLE);


                    }
                    else if (status.equals("false")){

                        chat.setVisibility(View.VISIBLE);
                        capmpaigns_heading.setVisibility(View.VISIBLE);
                        showall_campaigns.setVisibility(View.VISIBLE);
                        nearchallenges_heading.setVisibility(View.VISIBLE);
                        showall_nearchallenges.setVisibility(View.VISIBLE);
                        trendingchallenges_heading.setVisibility(View.VISIBLE);
                        loadmore.setVisibility(View.VISIBLE);
                        showall_trendingchallenges.setVisibility(View.GONE);

                        campaignsRV.setVisibility(View.VISIBLE);
                        nearchallengesRV.setVisibility(View.VISIBLE);
                        trendingchallengesRV.setVisibility(View.VISIBLE);

                        layrefreshlayout.setRefreshing(false);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                layrefreshlayout.setRefreshing(false);

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("HomeRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false")){

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setDataWithoutApi(){

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("campaignslist", "");
        List<Campaigns> campaignslist2 = new ArrayList<>();
        campaignslist2 = Arrays.asList(gson.fromJson(json , Campaigns[].class));

        Gson gsonn = new Gson();

        String jsonText = preferences.getString("Ccnamelist", null);
        String[] text = gsonn.fromJson(jsonText, String[].class);
        Ccnamelist.addAll (Arrays.asList(text));

        String jsonText1 = preferences.getString("Clikeslist", null);
        String[] text1 = gsonn.fromJson(jsonText1, String[].class);
        Clikeslist.addAll (Arrays.asList(text1));

        String jsonText2 = preferences.getString("Ccommentslist", null);
        String[] text2 = gsonn.fromJson(jsonText2, String[].class);
        Ccommentslist.addAll (Arrays.asList(text2));

        String jsonText3 = preferences.getString("Ctagslist", null);
        String[] text3 = gsonn.fromJson(jsonText3, String[].class);
        Ctagslist.addAll (Arrays.asList(text3));

        String jsonText4 = preferences.getString("Cusernamelist", null);
        String[] text4 = gsonn.fromJson(jsonText4, String[].class);
        Cusernamelist.addAll (Arrays.asList(text4));

        String jsonText5 = preferences.getString("Cuserimagelist", null);
        String[] text5 = gsonn.fromJson(jsonText5, String[].class);
        Cuserimagelist.addAll (Arrays.asList(text5));



        String jsonText6 = preferences.getString("Ccfontlist", null);
        String[] text6 = gsonn.fromJson(jsonText6, String[].class);
        Ccfontlist.addAll (Arrays.asList(text6));

        String jsonText7 = preferences.getString("Cviews", null);
        String[] text7 = gsonn.fromJson(jsonText7, String[].class);
        Cviews.addAll (Arrays.asList(text7));

        String jsonText8 = preferences.getString("Ccimagelist", null);
        String[] text8 = gsonn.fromJson(jsonText8, String[].class);
        Ccimagelist.addAll (Arrays.asList(text8));

        String jsonText9 = preferences.getString("Ccidlist", null);
        String[] text9 = gsonn.fromJson(jsonText9, String[].class);
        Ccidlist.addAll (Arrays.asList(text9));

        String jsonText10 = preferences.getString("Ccdescriptionlist", null);
        String[] text10 = gsonn.fromJson(jsonText10, String[].class);
        Ccdescriptionlist.addAll (Arrays.asList(text10));

        campaignAdapter = new CampaignAdapter(Home_Activity.this,campaignslist2);
        campaignsRV.setAdapter(campaignAdapter);
        campaignAdapter.notifyDataSetChanged();



        Gson gson2 = new Gson();
        String json2 = preferences.getString("nearbylist", "");
        List<NearBy_Challenges> nearbylist2 = new ArrayList<>();
        nearbylist2 = Arrays.asList(gson2.fromJson(json2 , NearBy_Challenges[].class));


        String jsonTextt = preferences.getString("likeslist", null);
        String[] textt = gsonn.fromJson(jsonTextt, String[].class);
        likeslist.addAll (Arrays.asList(textt));

        String jsonText11 = preferences.getString("commentslist", null);
        String[] text11 = gsonn.fromJson(jsonText11, String[].class);
        commentslist.addAll (Arrays.asList(text11));

        String jsonText22 = preferences.getString("tagslist", null);
        String[] text22 = gsonn.fromJson(jsonText22, String[].class);
        tagslist.addAll (Arrays.asList(text22));

        String jsonText33 = preferences.getString("usernamelist", null);
        String[] text33 = gsonn.fromJson(jsonText33, String[].class);
        usernamelist.addAll (Arrays.asList(text33));

        String jsonText44 = preferences.getString("userimagelist", null);
        String[] text44 = gsonn.fromJson(jsonText44, String[].class);
        userimagelist.addAll (Arrays.asList(text44));

        String jsonText55 = preferences.getString("cnamelist", null);
        String[] text55 = gsonn.fromJson(jsonText55, String[].class);
        cnamelist.addAll (Arrays.asList(text55));

        String jsonText66 = preferences.getString("cfontlist", null);
        String[] text66 = gsonn.fromJson(jsonText66, String[].class);
        cfontlist.addAll (Arrays.asList(text66));

        String jsonText77 = preferences.getString("views", null);
        String[] text77 = gsonn.fromJson(jsonText77, String[].class);
        views.addAll (Arrays.asList(text77));

        String jsonText88 = preferences.getString("cimagelist", null);
        String[] text88 = gsonn.fromJson(jsonText88, String[].class);
        cimagelist.addAll (Arrays.asList(text88));

        String jsonText99 = preferences.getString("cidlist", null);
        String[] text99 = gsonn.fromJson(jsonText99, String[].class);
        cidlist.addAll (Arrays.asList(text99));

        String jsonText100 = preferences.getString("cdescriptionlist", null);
        String[] text100 = gsonn.fromJson(jsonText100, String[].class);
        cdescriptionlist.addAll (Arrays.asList(text100));





        nearByChallengeAdapter = new NearByChallengeAdapter(Home_Activity.this,nearbylist2);
        nearchallengesRV.setAdapter(nearByChallengeAdapter);
        nearByChallengeAdapter.notifyDataSetChanged();



        Gson gson3 = new Gson();
        String json3 = preferences.getString("trendinglist", "");
        List<Trending_Challenges> trendinglist2 = new ArrayList<>();
        trendinglist2 = Arrays.asList(gson3.fromJson(json3 , Trending_Challenges[].class));


        trendingChallengesAdapter = new TrendingChallengesAdapter(Home_Activity.this,trendinglist2,Home_Activity.this,avi,avibackground);
        trendingchallengesRV.setAdapter(trendingChallengesAdapter);
        trendingChallengesAdapter.notifyDataSetChanged();

        opencount = Integer.parseInt(preferences.getString("opencount","0"));
        oneononecount = Integer.parseInt(preferences.getString("oneononecount","0"));
        selfcount = Integer.parseInt(preferences.getString("selfcount","0"));
        groupcount = Integer.parseInt(preferences.getString("groupcount","0"));


        chat.setVisibility(View.VISIBLE);
        capmpaigns_heading.setVisibility(View.VISIBLE);
        showall_campaigns.setVisibility(View.VISIBLE);
        nearchallenges_heading.setVisibility(View.VISIBLE);
        showall_nearchallenges.setVisibility(View.VISIBLE);
        trendingchallenges_heading.setVisibility(View.VISIBLE);
        loadmore.setVisibility(View.VISIBLE);
        showall_trendingchallenges.setVisibility(View.GONE);

        campaignsRV.setVisibility(View.VISIBLE);
        nearchallengesRV.setVisibility(View.VISIBLE);
        trendingchallengesRV.setVisibility(View.VISIBLE);



    }

    private void loadMoreApi(){

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        trendingusername.clear();
        trendinguserimage.clear();
        trendingcoingimage.clear();
        trendingchallengegimage.clear();
        trendingchallengename.clear();
        trendingchallengetime.clear();
        trendingchallengelike.clear();
        trendingchallengecomments.clear();
        trendingchallengeviews.clear();
        trendinglist.clear();

        final SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");


        RequestParams param = new RequestParams();

        param.add("user_id",uid);
        param.add("limit",String.valueOf(trendingpagination));
        param.add("offset","0");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/load_more_challenges", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                Log.e("loadmoreRes",res);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray open_challenge = dataSet.getJSONArray("open_challenge");
                        JSONArray one_on_one_challenge = dataSet.getJSONArray("one_on_one_challenge");
                        JSONArray self_challenge = dataSet.getJSONArray("self_challenge");
                        JSONArray group_challenge = dataSet.getJSONArray("group_challenge");


                        int opencoun = open_challenge.length();
                        int oneononecoun = one_on_one_challenge.length();
                        int selfcoun = self_challenge.length();
                        int groupcoun = group_challenge.length();


                        if (opencoun==opencount && oneononecoun==oneononecount && selfcoun==selfcount && groupcoun==groupcount){
                            loadmore.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"No more data found",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            opencount = open_challenge.length();
                            oneononecount = one_on_one_challenge.length();
                            selfcount = self_challenge.length();
                            groupcount = group_challenge.length();

                            preferences.edit().putString("opencount",String.valueOf(opencount)).commit();
                            preferences.edit().putString("oneononecount",String.valueOf(oneononecount)).commit();
                            preferences.edit().putString("selfcount",String.valueOf(selfcount)).commit();
                            preferences.edit().putString("groupcount",String.valueOf(groupcount)).commit();


                            trendingpagination = trendingpagination + 5;

                            if (open_challenge.length()>0){

                                for (int i=0; i<open_challenge.length(); i++){
                                    Trending_Challenges trending_challenges = new Trending_Challenges();
                                    JSONObject item = open_challenge.getJSONObject(i);
                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    String view_count = item.getString("view_count");
                                    country = item.getString("country");

                                    String challenge_title = item.getString("challenge_title");
                                    trending_challenges.setChallenge_title(challenge_title);

                                    String user_like = item.getString("user_like");
                                    String user_like_reward = item.getString("user_like_reward");

                                    trending_challenges.setUser_like(user_like);
                                    trending_challenges.setUser_like_reward(user_like_reward);

                                    JSONObject like_type_count = item.getJSONObject("like_type_count");
                                    String silver = like_type_count.getString("silver");
                                    String gold = like_type_count.getString("gold");
                                    String bronze = like_type_count.getString("bronze");


                                    if (item.get("challenge_reply") instanceof JSONArray){

                                        JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                        if (challenge_reply.length()>0){

                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                            String usid = preferences.getString(Constant.USER_ID,"");
                                            for (int j=0; j<challenge_reply.length();j++){

                                                JSONObject useridItem = challenge_reply.getJSONObject(j);

                                                String alluserid = useridItem.getString("user_id");
                                                if (usid.equals(alluserid)){
                                                    trending_challenges.setAll_userid("yes");
                                                    break;
                                                }

                                            }

                                            String count = String.valueOf(challenge_reply.length());
                                            trending_challenges.setCount(count);

                                            if (challenge_reply.length()==1){

                                                JSONObject item2 = challenge_reply.getJSONObject(0);
                                                String profile_imag = item2.getString("profile_image");
                                                final String id1 = item2.getString("id");
                                                final String user_id1 = item2.getString("user_id");
                                                final String username1 = item2.getString("username");
                                                final String like1 = item2.getString("like_count");
                                                final String comments_count1 = item2.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item2.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item2.getString("challenge_title");

                                                trending_challenges.setProfile_img1(profile_imag);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2("no");
                                                trending_challenges.setId2("no");
                                                trending_challenges.setUser_id2("no");
                                                trending_challenges.setUsername2("no");
                                                trending_challenges.setLike2("no");
                                                trending_challenges.setComments_count2("no");
                                                trending_challenges.setTitle2("no");
                                                trending_challenges.setDescription2("no");
                                                trending_challenges.setTags2("no");
                                                trending_challenges.setCountry2("no");
                                                trending_challenges.setView_count2("no");
                                                trending_challenges.setChallenge_title2("no");

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==2){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);

                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);

                                            }
                                            else if (challenge_reply.length()>3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);
                                            }
                                        }
                                        else {
                                            trending_challenges.setAll_userid("");
                                            trending_challenges.setCount("0");

                                            trending_challenges.setProfile_img1("no");
                                            trending_challenges.setId1("no");
                                            trending_challenges.setUser_id1("no");
                                            trending_challenges.setUsername1("no");
                                            trending_challenges.setLike1("no");
                                            trending_challenges.setComments_count1("no");
                                            trending_challenges.setTitle1("no");
                                            trending_challenges.setDescription1("no");
                                            trending_challenges.setTags1("no");
                                            trending_challenges.setCountry1("no");
                                            trending_challenges.setView_count1("no");
                                            trending_challenges.setChallenge_title1("no");

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");
                                        }

                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");

                                    }



                                    trending_challenges.setSilver(silver);
                                    trending_challenges.setGold(gold);
                                    trending_challenges.setBronze(bronze);


                                    trending_challenges.setId(id);
                                    trending_challenges.setUser_id(user_id);
                                    trending_challenges.setTitle(title);
                                    trending_challenges.setDescritpion(descritpion);
                                    trending_challenges.setTags(tags);
                                    trending_challenges.setCreated_at(created_at);
                                    trending_challenges.setUsername(username);
                                    trending_challenges.setProfile_image(profile_image);
                                    trending_challenges.setDescription_background(description_background);
                                    trending_challenges.setDescription_fonts(description_fonts);
                                    trending_challenges.setComments_count(comments_count);
                                    trending_challenges.setLike_count(like_count);
                                    trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    trending_challenges.setVideo(video);
                                    trending_challenges.setImage(image);
                                    trending_challenges.setCountry(country);
                                    trending_challenges.setView_count(view_count);

                                    trendinglist.add(trending_challenges);
                                }
                            }
                            if (one_on_one_challenge.length()>0){

                                for (int i=0; i<one_on_one_challenge.length(); i++){
                                    Trending_Challenges trending_challenges = new Trending_Challenges();
                                    JSONObject item = one_on_one_challenge.getJSONObject(i);
                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    String challenge_title = item.getString("challenge_title");
                                    trending_challenges.setChallenge_title(challenge_title);


                                    String user_like = item.getString("user_like");
                                    String user_like_reward = item.getString("user_like_reward");

                                    trending_challenges.setUser_like(user_like);
                                    trending_challenges.setUser_like_reward(user_like_reward);



                                    JSONObject like_type_count = item.getJSONObject("like_type_count");
                                    String silver = like_type_count.getString("silver");
                                    String gold = like_type_count.getString("gold");
                                    String bronze = like_type_count.getString("bronze");

                                    trending_challenges.setSilver(silver);
                                    trending_challenges.setGold(gold);
                                    trending_challenges.setBronze(bronze);

                                    if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String myid = preferences.getString(Constant.USER_ID,"");

                                        JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                        if (challeneg_join_member.length()>0){

                                            for (int k=0; k<challeneg_join_member.length(); k++){

                                                JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                                String join_id = join_item.getString("id");
                                                if (join_id.equals(myid)){

                                                    trending_challenges.setJoinuser("yes");
                                                    break;
                                                }
                                            }
                                        }
                                    }


                                    if (item.get("challenge_reply") instanceof JSONArray){

                                        JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                        if (challenge_reply.length()>0){

                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                            String usid = preferences.getString(Constant.USER_ID,"");
                                            for (int j=0; j<challenge_reply.length();j++){

                                                JSONObject useridItem = challenge_reply.getJSONObject(j);

                                                String alluserid = useridItem.getString("user_id");
                                                if (usid.equals(alluserid)){
                                                    trending_challenges.setAll_userid("yes");
                                                    break;
                                                }

                                            }


                                            String count = String.valueOf(challenge_reply.length());
                                            trending_challenges.setCount(count);

                                            if (challenge_reply.length()==1){

                                                JSONObject item2 = challenge_reply.getJSONObject(0);
                                                String profile_imag = item2.getString("profile_image");
                                                final String id1 = item2.getString("id");
                                                final String user_id1 = item2.getString("user_id");
                                                final String username1 = item2.getString("username");
                                                final String like1 = item2.getString("like_count");
                                                final String comments_count1 = item2.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item2.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item2.getString("challenge_title");

                                                trending_challenges.setProfile_img1(profile_imag);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2("no");
                                                trending_challenges.setId2("no");
                                                trending_challenges.setUser_id2("no");
                                                trending_challenges.setUsername2("no");
                                                trending_challenges.setLike2("no");
                                                trending_challenges.setComments_count2("no");
                                                trending_challenges.setTitle2("no");
                                                trending_challenges.setDescription2("no");
                                                trending_challenges.setTags2("no");
                                                trending_challenges.setCountry2("no");
                                                trending_challenges.setView_count2("no");
                                                trending_challenges.setChallenge_title2("no");

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==2){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);

                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);

                                            }
                                            else if (challenge_reply.length()>3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);
                                            }
                                        }
                                        else {
                                            trending_challenges.setAll_userid("");
                                            trending_challenges.setCount("0");

                                            trending_challenges.setProfile_img1("no");
                                            trending_challenges.setId1("no");
                                            trending_challenges.setUser_id1("no");
                                            trending_challenges.setUsername1("no");
                                            trending_challenges.setLike1("no");
                                            trending_challenges.setComments_count1("no");
                                            trending_challenges.setTitle1("no");
                                            trending_challenges.setDescription1("no");
                                            trending_challenges.setTags1("no");
                                            trending_challenges.setCountry1("no");
                                            trending_challenges.setView_count1("no");
                                            trending_challenges.setChallenge_title1("no");

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");
                                        }

                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");

                                    }

                                    trending_challenges.setId(id);
                                    trending_challenges.setUser_id(user_id);
                                    trending_challenges.setTitle(title);
                                    trending_challenges.setDescritpion(descritpion);
                                    trending_challenges.setTags(tags);
                                    trending_challenges.setCreated_at(created_at);
                                    trending_challenges.setUsername(username);
                                    trending_challenges.setProfile_image(profile_image);
                                    trending_challenges.setDescription_background(description_background);
                                    trending_challenges.setDescription_fonts(description_fonts);
                                    trending_challenges.setComments_count(comments_count);
                                    trending_challenges.setLike_count(like_count);
                                    trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    trending_challenges.setVideo(video);
                                    trending_challenges.setImage(image);
                                    trending_challenges.setCountry(country);
                                    trending_challenges.setView_count(view_count);
                                    trendinglist.add(trending_challenges);
                                }
                            }
                            if (self_challenge.length()>0){

                                for (int i=0; i<self_challenge.length(); i++){
                                    Trending_Challenges trending_challenges = new Trending_Challenges();
                                    JSONObject item = self_challenge.getJSONObject(i);
                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    String challenge_title = item.getString("challenge_title");
                                    trending_challenges.setChallenge_title(challenge_title);


                                    String user_like = item.getString("user_like");
                                    String user_like_reward = item.getString("user_like_reward");

                                    trending_challenges.setUser_like(user_like);
                                    trending_challenges.setUser_like_reward(user_like_reward);



                                    JSONObject like_type_count = item.getJSONObject("like_type_count");
                                    String silver = like_type_count.getString("silver");
                                    String gold = like_type_count.getString("gold");
                                    String bronze = like_type_count.getString("bronze");



                                    if (item.get("challenge_reply") instanceof JSONArray){

                                        JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                        if (challenge_reply.length()>0){

                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                            String usid = preferences.getString(Constant.USER_ID,"");
                                            for (int j=0; j<challenge_reply.length();j++){

                                                JSONObject useridItem = challenge_reply.getJSONObject(j);

                                                String alluserid = useridItem.getString("user_id");
                                                if (usid.equals(alluserid)){
                                                    trending_challenges.setAll_userid("yes");
                                                    break;
                                                }

                                            }


                                            String count = String.valueOf(challenge_reply.length());
                                            trending_challenges.setCount(count);

                                            if (challenge_reply.length()==1){

                                                JSONObject item2 = challenge_reply.getJSONObject(0);
                                                String profile_imag = item2.getString("profile_image");
                                                final String id1 = item2.getString("id");
                                                final String user_id1 = item2.getString("user_id");
                                                final String username1 = item2.getString("username");
                                                final String like1 = item2.getString("like_count");
                                                final String comments_count1 = item2.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item2.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item2.getString("challenge_title");

                                                trending_challenges.setProfile_img1(profile_imag);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2("no");
                                                trending_challenges.setId2("no");
                                                trending_challenges.setUser_id2("no");
                                                trending_challenges.setUsername2("no");
                                                trending_challenges.setLike2("no");
                                                trending_challenges.setComments_count2("no");
                                                trending_challenges.setTitle2("no");
                                                trending_challenges.setDescription2("no");
                                                trending_challenges.setTags2("no");
                                                trending_challenges.setCountry2("no");
                                                trending_challenges.setView_count2("no");
                                                trending_challenges.setChallenge_title2("no");

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==2){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);

                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);

                                            }
                                            else if (challenge_reply.length()>3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);
                                            }
                                        }
                                        else {
                                            trending_challenges.setAll_userid("");
                                            trending_challenges.setCount("0");

                                            trending_challenges.setProfile_img1("no");
                                            trending_challenges.setId1("no");
                                            trending_challenges.setUser_id1("no");
                                            trending_challenges.setUsername1("no");
                                            trending_challenges.setLike1("no");
                                            trending_challenges.setComments_count1("no");
                                            trending_challenges.setTitle1("no");
                                            trending_challenges.setDescription1("no");
                                            trending_challenges.setTags1("no");
                                            trending_challenges.setCountry1("no");
                                            trending_challenges.setView_count1("no");
                                            trending_challenges.setChallenge_title1("no");

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");
                                        }

                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");

                                    }

                                    trending_challenges.setSilver(silver);
                                    trending_challenges.setGold(gold);
                                    trending_challenges.setBronze(bronze);


                                    trending_challenges.setId(id);
                                    trending_challenges.setUser_id(user_id);
                                    trending_challenges.setTitle(title);
                                    trending_challenges.setDescritpion(descritpion);
                                    trending_challenges.setTags(tags);
                                    trending_challenges.setCreated_at(created_at);
                                    trending_challenges.setUsername(username);
                                    trending_challenges.setProfile_image(profile_image);
                                    trending_challenges.setDescription_background(description_background);
                                    trending_challenges.setDescription_fonts(description_fonts);
                                    trending_challenges.setComments_count(comments_count);
                                    trending_challenges.setLike_count(like_count);
                                    trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    trending_challenges.setVideo(video);
                                    trending_challenges.setImage(image);
                                    trending_challenges.setCountry(country);
                                    trending_challenges.setView_count(view_count);
                                    trendinglist.add(trending_challenges);
                                }
                            }
                            if (group_challenge.length()>0){

                                for (int i=0; i<group_challenge.length(); i++){
                                    Trending_Challenges trending_challenges = new Trending_Challenges();
                                    JSONObject item = group_challenge.getJSONObject(i);
                                    String id = item.getString("id");
                                    String user_id = item.getString("user_id");
                                    String title = item.getString("title");
                                    String descritpion = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String created_at = item.getString("created_at");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String description_background = item.getString("description_background");
                                    String description_fonts = item.getString("description_fonts");
                                    String comments_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    String challenge_attempted_count = item.getString("challenge_attempted_count");
                                    String video = item.getString("video");
                                    String image = item.getString("image");
                                    country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    String challenge_title = item.getString("challenge_title");
                                    trending_challenges.setChallenge_title(challenge_title);

                                    String user_like = item.getString("user_like");
                                    String user_like_reward = item.getString("user_like_reward");

                                    trending_challenges.setUser_like(user_like);
                                    trending_challenges.setUser_like_reward(user_like_reward);

                                    JSONObject like_type_count = item.getJSONObject("like_type_count");
                                    String silver = like_type_count.getString("silver");
                                    String gold = like_type_count.getString("gold");
                                    String bronze = like_type_count.getString("bronze");

                                    if (item.has("challeneg_join_member") && item.get("challeneg_join_member") instanceof JSONArray){

                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                        String myid = preferences.getString(Constant.USER_ID,"");

                                        JSONArray challeneg_join_member = item.getJSONArray("challeneg_join_member");

                                        if (challeneg_join_member.length()>0){

                                            for (int k=0; k<challeneg_join_member.length(); k++){

                                                JSONObject join_item = challeneg_join_member.getJSONObject(k);
                                                String join_id = join_item.getString("id");
                                                if (join_id.equals(myid)){

                                                    trending_challenges.setJoinuser("yes");
                                                    break;
                                                }
                                            }
                                        }
                                    }


                                    if (item.get("challenge_reply") instanceof JSONArray){

                                        JSONArray challenge_reply = item.getJSONArray("challenge_reply");

                                        if (challenge_reply.length()>0){

                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                                            String usid = preferences.getString(Constant.USER_ID,"");
                                            for (int j=0; j<challenge_reply.length();j++){

                                                JSONObject useridItem = challenge_reply.getJSONObject(j);

                                                String alluserid = useridItem.getString("user_id");
                                                if (usid.equals(alluserid)){
                                                    trending_challenges.setAll_userid("yes");
                                                    break;
                                                }

                                            }

                                            String count = String.valueOf(challenge_reply.length());
                                            trending_challenges.setCount(count);

                                            if (challenge_reply.length()==1){

                                                JSONObject item2 = challenge_reply.getJSONObject(0);
                                                String profile_imag = item2.getString("profile_image");
                                                final String id1 = item2.getString("id");
                                                final String user_id1 = item2.getString("user_id");
                                                final String username1 = item2.getString("username");
                                                final String like1 = item2.getString("like_count");
                                                final String comments_count1 = item2.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item2.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item2.getString("challenge_title");

                                                trending_challenges.setProfile_img1(profile_imag);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2("no");
                                                trending_challenges.setId2("no");
                                                trending_challenges.setUser_id2("no");
                                                trending_challenges.setUsername2("no");
                                                trending_challenges.setLike2("no");
                                                trending_challenges.setComments_count2("no");
                                                trending_challenges.setTitle2("no");
                                                trending_challenges.setDescription2("no");
                                                trending_challenges.setTags2("no");
                                                trending_challenges.setCountry2("no");
                                                trending_challenges.setView_count2("no");
                                                trending_challenges.setChallenge_title2("no");

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==2){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);

                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3("no");
                                                trending_challenges.setId3("no");
                                                trending_challenges.setUser_id3("no");
                                                trending_challenges.setUsername3("no");
                                                trending_challenges.setLike3("no");
                                                trending_challenges.setComments_count3("no");
                                                trending_challenges.setTitle3("no");
                                                trending_challenges.setDescription3("no");
                                                trending_challenges.setTags3("no");
                                                trending_challenges.setCountry3("no");
                                                trending_challenges.setView_count3("no");
                                                trending_challenges.setChallenge_title3("no");


                                            }
                                            else if (challenge_reply.length()==3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);

                                            }
                                            else if (challenge_reply.length()>3){

                                                JSONObject item1 = challenge_reply.getJSONObject(0);

                                                String profile_image1 = item1.getString("profile_image");
                                                final String id1 = item1.getString("id");
                                                final String user_id1 = item1.getString("user_id");
                                                final String username1 = item1.getString("username");
                                                final String like1 = item1.getString("like_count");
                                                final String comments_count1 = item1.getString("comments_count");
                                                final String title1 = title;
                                                final String description1 = descritpion;
                                                final String tags1 = tags;
                                                final String country1 = item1.getString("country");
                                                final String view_count1 = "0";
                                                final String challenge_title1 = item1.getString("challenge_title");

                                                JSONObject item2 = challenge_reply.getJSONObject(1);
                                                String profile_image2 = item2.getString("profile_image");
                                                final String id2 = item2.getString("id");
                                                final String user_id2 = item2.getString("user_id");
                                                final String username2 = item2.getString("username");
                                                final String like2 = item2.getString("like_count");
                                                final String comments_count2 = item2.getString("comments_count");
                                                final String title2 = title;
                                                final String description2 = descritpion;
                                                final String tags2 = tags;
                                                final String country2 = item2.getString("country");
                                                final String view_count2 = "0";
                                                final String challenge_title2 = item2.getString("challenge_title");


                                                JSONObject item3 = challenge_reply.getJSONObject(2);
                                                String profile_image3 = item3.getString("profile_image");
                                                final String id3 = item3.getString("id");
                                                final String user_id3 = item3.getString("user_id");
                                                final String username3 = item3.getString("username");
                                                final String like3 = item3.getString("like_count");
                                                final String comments_count3 = item3.getString("comments_count");
                                                final String title3 = title;
                                                final String description3 = descritpion;
                                                final String tags3 = tags;
                                                final String country3 = item3.getString("country");
                                                final String view_count3 = "0";
                                                final String challenge_title3 = item3.getString("challenge_title");



                                                trending_challenges.setProfile_img1(profile_image1);
                                                trending_challenges.setId1(id1);
                                                trending_challenges.setUser_id1(user_id1);
                                                trending_challenges.setUsername1(username1);
                                                trending_challenges.setLike1(like1);
                                                trending_challenges.setComments_count1(comments_count1);
                                                trending_challenges.setTitle1(title1);
                                                trending_challenges.setDescription1(description1);
                                                trending_challenges.setTags1(tags1);
                                                trending_challenges.setCountry1(country1);
                                                trending_challenges.setView_count1(view_count1);
                                                trending_challenges.setChallenge_title1(challenge_title1);

                                                trending_challenges.setProfile_img2(profile_image2);
                                                trending_challenges.setId2(id2);
                                                trending_challenges.setUser_id2(user_id2);
                                                trending_challenges.setUsername2(username2);
                                                trending_challenges.setLike2(like2);
                                                trending_challenges.setComments_count2(comments_count2);
                                                trending_challenges.setTitle2(title2);
                                                trending_challenges.setDescription2(description2);
                                                trending_challenges.setTags2(tags2);
                                                trending_challenges.setCountry2(country2);
                                                trending_challenges.setView_count2(view_count2);
                                                trending_challenges.setChallenge_title2(challenge_title2);

                                                trending_challenges.setProfile_img3(profile_image3);
                                                trending_challenges.setId3(id3);
                                                trending_challenges.setUser_id3(user_id3);
                                                trending_challenges.setUsername3(username3);
                                                trending_challenges.setLike3(like3);
                                                trending_challenges.setComments_count3(comments_count3);
                                                trending_challenges.setTitle3(title3);
                                                trending_challenges.setDescription3(description3);
                                                trending_challenges.setTags3(tags3);
                                                trending_challenges.setCountry3(country3);
                                                trending_challenges.setView_count3(view_count3);
                                                trending_challenges.setChallenge_title3(challenge_title3);
                                            }
                                        }
                                        else {
                                            trending_challenges.setAll_userid("");
                                            trending_challenges.setCount("0");

                                            trending_challenges.setProfile_img1("no");
                                            trending_challenges.setId1("no");
                                            trending_challenges.setUser_id1("no");
                                            trending_challenges.setUsername1("no");
                                            trending_challenges.setLike1("no");
                                            trending_challenges.setComments_count1("no");
                                            trending_challenges.setTitle1("no");
                                            trending_challenges.setDescription1("no");
                                            trending_challenges.setTags1("no");
                                            trending_challenges.setCountry1("no");
                                            trending_challenges.setView_count1("no");
                                            trending_challenges.setChallenge_title1("no");

                                            trending_challenges.setProfile_img2("no");
                                            trending_challenges.setId2("no");
                                            trending_challenges.setUser_id2("no");
                                            trending_challenges.setUsername2("no");
                                            trending_challenges.setLike2("no");
                                            trending_challenges.setComments_count2("no");
                                            trending_challenges.setTitle2("no");
                                            trending_challenges.setDescription2("no");
                                            trending_challenges.setTags2("no");
                                            trending_challenges.setCountry2("no");
                                            trending_challenges.setView_count2("no");
                                            trending_challenges.setChallenge_title2("no");

                                            trending_challenges.setProfile_img3("no");
                                            trending_challenges.setId3("no");
                                            trending_challenges.setUser_id3("no");
                                            trending_challenges.setUsername3("no");
                                            trending_challenges.setLike3("no");
                                            trending_challenges.setComments_count3("no");
                                            trending_challenges.setTitle3("no");
                                            trending_challenges.setDescription3("no");
                                            trending_challenges.setTags3("no");
                                            trending_challenges.setCountry3("no");
                                            trending_challenges.setView_count3("no");
                                            trending_challenges.setChallenge_title3("no");
                                        }

                                    }
                                    else {
                                        trending_challenges.setAll_userid("");
                                        trending_challenges.setCount("0");

                                        trending_challenges.setProfile_img1("no");
                                        trending_challenges.setId1("no");
                                        trending_challenges.setUser_id1("no");
                                        trending_challenges.setUsername1("no");
                                        trending_challenges.setLike1("no");
                                        trending_challenges.setComments_count1("no");
                                        trending_challenges.setTitle1("no");
                                        trending_challenges.setDescription1("no");
                                        trending_challenges.setTags1("no");
                                        trending_challenges.setCountry1("no");
                                        trending_challenges.setView_count1("no");
                                        trending_challenges.setChallenge_title1("no");

                                        trending_challenges.setProfile_img2("no");
                                        trending_challenges.setId2("no");
                                        trending_challenges.setUser_id2("no");
                                        trending_challenges.setUsername2("no");
                                        trending_challenges.setLike2("no");
                                        trending_challenges.setComments_count2("no");
                                        trending_challenges.setTitle2("no");
                                        trending_challenges.setDescription2("no");
                                        trending_challenges.setTags2("no");
                                        trending_challenges.setCountry2("no");
                                        trending_challenges.setView_count2("no");
                                        trending_challenges.setChallenge_title2("no");

                                        trending_challenges.setProfile_img3("no");
                                        trending_challenges.setId3("no");
                                        trending_challenges.setUser_id3("no");
                                        trending_challenges.setUsername3("no");
                                        trending_challenges.setLike3("no");
                                        trending_challenges.setComments_count3("no");
                                        trending_challenges.setTitle3("no");
                                        trending_challenges.setDescription3("no");
                                        trending_challenges.setTags3("no");
                                        trending_challenges.setCountry3("no");
                                        trending_challenges.setView_count3("no");
                                        trending_challenges.setChallenge_title3("no");

                                    }
                                    trending_challenges.setSilver(silver);
                                    trending_challenges.setGold(gold);
                                    trending_challenges.setBronze(bronze);
                                    trending_challenges.setId(id);
                                    trending_challenges.setUser_id(user_id);
                                    trending_challenges.setTitle(title);
                                    trending_challenges.setDescritpion(descritpion);
                                    trending_challenges.setTags(tags);
                                    trending_challenges.setCreated_at(created_at);
                                    trending_challenges.setUsername(username);
                                    trending_challenges.setProfile_image(profile_image);
                                    trending_challenges.setDescription_background(description_background);
                                    trending_challenges.setDescription_fonts(description_fonts);
                                    trending_challenges.setComments_count(comments_count);
                                    trending_challenges.setLike_count(like_count);
                                    trending_challenges.setChallenge_attempted_count(challenge_attempted_count);
                                    trending_challenges.setVideo(video);
                                    trending_challenges.setImage(image);
                                    trending_challenges.setCountry(country);
                                    trending_challenges.setView_count(view_count);

                                    trendinglist.add(trending_challenges);
                                }
                            }

                            Gson gson = new Gson();
                            String json = gson.toJson(trendinglist);
                            preferences.edit().putString("trendinglist",json).commit();


                            trendingChallengesAdapter = new TrendingChallengesAdapter(Home_Activity.this,trendinglist,Home_Activity.this,avi,avibackground);
                            trendingchallengesRV.setAdapter(trendingChallengesAdapter);
                            trendingChallengesAdapter.notifyDataSetChanged();

                        }
                    }
                    else if (status.equals("false")){


                    }

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Home_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                if (responseBody!=null){

                    String res = new String(responseBody);
                    Log.e("loadmoreResF",res);

                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    Boolean twice=false;
    @Override
    public void onBackPressed() {
        if (twice==true){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice=true;
        Toast.makeText(getApplicationContext(),getResources().getString(R.string.Pressbackagaintoexit),Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);

    }



}