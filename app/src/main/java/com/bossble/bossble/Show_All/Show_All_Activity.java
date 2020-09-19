package com.bossble.bossble.Show_All;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.ShowAllAdapter;
import com.bossble.bossble.Adapter.ShowAllCampaigns;
import com.bossble.bossble.Adapter.ShowAllNearByAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.Campaigns;
import com.bossble.bossble.Model.NearBy_Challenges;
import com.bossble.bossble.R;
import com.bossble.bossble.Settings.ReportUser_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class Show_All_Activity extends AppCompatActivity {

    TextView type;
    ImageView back;
    RecyclerView showallRV;

    ArrayList<String> usernamelist=new ArrayList<>();
    ArrayList<String> userimagelist=new ArrayList<>();
    ArrayList<String> cimagelist=new ArrayList<>();
    ArrayList<String> cnamelist=new ArrayList<>();
    ArrayList<String> cidlist=new ArrayList<>();
    ArrayList<String> cdescriptionlist=new ArrayList<>();
    ArrayList<String> likeslist=new ArrayList<>();
    ArrayList<String> commentslist=new ArrayList<>();
    ArrayList<String> tagslist=new ArrayList<>();
    ArrayList<String> fontslist=new ArrayList<>();
    ArrayList<String> viewslist=new ArrayList<>();

    String country="";

    ShowAllAdapter showAllAdapter;

    TextView loadmore;
    ArrayList<Campaigns> campaignslist = new ArrayList<>();
    ShowAllCampaigns showAllCampaigns;

    ArrayList<NearBy_Challenges> nearbylist = new ArrayList<>();
    ShowAllNearByAdapter showAllNearByAdapter;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String limit = "10";
    String total = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all_);

        back=findViewById(R.id.back);
        type=findViewById(R.id.type);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        showallRV=findViewById(R.id.showallRV);
        loadmore=findViewById(R.id.loadmore);

        type.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        loadmore.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        showallRV.setLayoutManager(layoutManager3);
        showallRV.setHasFixedSize(true);
        showallRV.setNestedScrollingEnabled(false);

        if (getIntent().hasExtra("type")){
            String types = getIntent().getStringExtra("type");
            if (types.equals("campaign")){
                type.setText("CAMPAIGNS");

                if (Constant.isOnline(Show_All_Activity.this)){

                    allcampaigns();

                }
                else{
                    Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }


                loadmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Integer.parseInt(total) > campaignslist.size()){

                            int minus = Integer.parseInt(total) - campaignslist.size();
                            if (minus>=10){
                                limit=String.valueOf(Integer.parseInt(limit)+10);
                            }
                            else {
                                limit=String.valueOf(Integer.parseInt(limit)+minus);
                            }
                            if (Constant.isOnline(Show_All_Activity.this)){

                                allcampaigns();

                            }
                            else{
                                Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.NoInternetConnection));

                            }
                        }
                    }
                });


            }
            else {
                type.setText(getResources().getString(R.string.CHALLENGESNEARME));

                if (Constant.isOnline(Show_All_Activity.this)){

                    allnearby();

                }
                else{
                    Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }

                loadmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Integer.parseInt(total) > nearbylist.size()){

                            int minus = Integer.parseInt(total) - nearbylist.size();
                            if (minus>=10){
                                limit=String.valueOf(Integer.parseInt(limit)+10);
                            }
                            else {
                                limit=String.valueOf(Integer.parseInt(limit)+minus);
                            }
                            if (Constant.isOnline(Show_All_Activity.this)){

                                allnearby();

                            }
                            else{
                                Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.NoInternetConnection));

                            }
                        }
                    }
                });
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_All_Activity.this.onBackPressed();
            }
        });


    }

    private void allcampaigns(){



        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("limit",limit);
        params.add("offset","0");
        params.add("user_id",uid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/campaign_listing?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("res",res);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){

                        campaignslist.clear();

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        total = dataSet.getString("total");

                        if (dataSet.get("campaign_listing") instanceof JSONArray) {

                            JSONArray campaign_listing = dataSet.getJSONArray("campaign_listing");
                            if (campaign_listing.length()>0){

                                for (int i=0; i<campaign_listing.length(); i++){

                                    Campaigns campaigns = new Campaigns();

                                    JSONObject item = campaign_listing.getJSONObject(i);
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

                                    showAllCampaigns = new ShowAllCampaigns(Show_All_Activity.this,campaignslist);
                                    showallRV.setAdapter(showAllCampaigns);
                                    showAllCampaigns.notifyDataSetChanged();


                                    if (campaignslist.size()==Integer.parseInt(total)){
                                        loadmore.setVisibility(View.GONE);
                                    }
                                    else {
                                        loadmore.setVisibility(View.VISIBLE);
                                    }

                                }
                            }

                        }


                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("resF",res);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });

    }


    private void allnearby(){



        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("limit",limit);
        params.add("offset","0");
        params.add("user_id",uid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/location_challenge_listing?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("res",res);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){

                        nearbylist.clear();

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        total = dataSet.getString("total");

                        if (dataSet.get("location_listing") instanceof JSONArray) {

                            JSONArray location_listing = dataSet.getJSONArray("location_listing");
                            if (location_listing.length()>0){

                                for (int i=0; i<location_listing.length(); i++){


                                    NearBy_Challenges nearBy_challenges = new NearBy_Challenges();

                                    JSONObject item = location_listing.getJSONObject(i);
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


                                    showAllNearByAdapter = new ShowAllNearByAdapter(Show_All_Activity.this,nearbylist);
                                    showallRV.setAdapter(showAllNearByAdapter);
                                    showAllNearByAdapter.notifyDataSetChanged();


                                    if (nearbylist.size()==Integer.parseInt(total)){
                                        loadmore.setVisibility(View.GONE);
                                    }
                                    else {
                                        loadmore.setVisibility(View.VISIBLE);
                                    }

                                }
                            }

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("resF",res);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Show_All_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });

    }


}
