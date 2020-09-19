package com.bossble.bossble.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.CampaignSearchAdapter;
import com.bossble.bossble.Adapter.ChallengeSearchAdapter;
import com.bossble.bossble.Adapter.HashtagAdapter;
import com.bossble.bossble.Adapter.SearchAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.CampaignSearch;
import com.bossble.bossble.Model.ChallengeSearch;
import com.bossble.bossble.Model.Hashtags;
import com.bossble.bossble.Model.Search_people;
import com.bossble.bossble.People_Activity;
import com.bossble.bossble.ProfileSetup.Profile_Setup_Activity;
import com.bossble.bossble.R;
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

public class Search_Activity extends AppCompatActivity {

    ImageView fulllogo,back;
    TextView heading,challenges,campaigns,tags,profile,recyclerheading,txtnoprofile;
    RecyclerView searchRV;
    EditText search;
    String filter="";
    ImageView imgsearch;
    ArrayList<Search_people> filters=new ArrayList<>();
    SearchAdapter searchAdapter;

    ArrayList<ChallengeSearch> filters2=new ArrayList<>();
    ChallengeSearchAdapter challengeSearchAdapter;

    ArrayList<CampaignSearch> filters3=new ArrayList<>();
    CampaignSearchAdapter campaignSearchAdapter;

    ArrayList<Hashtags> filters4=new ArrayList<>();
    HashtagAdapter hashtagAdapter;


    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    String identifier="";
    int apihit=1;
    String bottom ="";
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Constant.bottomNav(Search_Activity.this,1,sv,"search");

        apihit=1;
        sv = findViewById(R.id.sv);

        fulllogo = findViewById(R.id.fulllogo);
        back = findViewById(R.id.back);
        heading = findViewById(R.id.heading);
        challenges = findViewById(R.id.challenges);
        campaigns = findViewById(R.id.campaigns);
        tags = findViewById(R.id.tags);
        profile = findViewById(R.id.profile);
        recyclerheading = findViewById(R.id.recyclerheading);
        searchRV = findViewById(R.id.searchRV);
        search = findViewById(R.id.search);
        imgsearch = findViewById(R.id.imgsearch);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        txtnoprofile = findViewById(R.id.txtnoprofile);

        recyclerheading.setText(getResources().getString(R.string.SEARCHCHALLENGES));

        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        tags.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        challenges.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        campaigns.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        profile.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        recyclerheading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        search.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Regular.ttf"));


        if (getIntent().hasExtra("bottom")){
            bottom = "yes";
        }

        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Activity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search_Activity.this.onBackPressed();
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerheading.setText(getResources().getString(R.string.SEARCHCHALLENGES));
                identifier="1";
                apihit=1;
                if (challenges.getCurrentTextColor()==getResources().getColor(R.color.dark_blue)){

                    challenges.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="";

                }
                else {

                    challenges.setTextColor(getResources().getColor(R.color.dark_blue));
                    campaigns.setTextColor(getResources().getColor(R.color.darkgray));
                    tags.setTextColor(getResources().getColor(R.color.darkgray));
                    profile.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="challenges";

                }

                filters2.clear();
                challengeSearchAdapter = new ChallengeSearchAdapter(Search_Activity.this, filters2);
                searchRV.setAdapter(challengeSearchAdapter);
                challengeSearchAdapter.notifyDataSetChanged();


            }
        });


        campaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerheading.setText(getResources().getString(R.string.SEARCHCAMPAIGNS));
                identifier="2";
                apihit=2;
                if (campaigns.getCurrentTextColor()==getResources().getColor(R.color.dark_blue)){
                    campaigns.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="";

                }
                else {
                    campaigns.setTextColor(getResources().getColor(R.color.dark_blue));
                    challenges.setTextColor(getResources().getColor(R.color.darkgray));
                    tags.setTextColor(getResources().getColor(R.color.darkgray));
                    profile.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="campaigns";

                }

                filters3.clear();
                campaignSearchAdapter = new CampaignSearchAdapter(Search_Activity.this, filters3);
                searchRV.setAdapter(campaignSearchAdapter);
                campaignSearchAdapter.notifyDataSetChanged();

            }
        });
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerheading.setText(getResources().getString(R.string.SEARCHHASHTAGS));
                identifier="3";
                apihit=3;
                if (tags.getCurrentTextColor()==getResources().getColor(R.color.dark_blue)){
                    tags.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="";

                }
                else {
                    tags.setTextColor(getResources().getColor(R.color.dark_blue));
                    challenges.setTextColor(getResources().getColor(R.color.darkgray));
                    campaigns.setTextColor(getResources().getColor(R.color.darkgray));
                    profile.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="tags";

                }
                filters4.clear();
                hashtagAdapter = new HashtagAdapter(Search_Activity.this, filters4);
                searchRV.setAdapter(hashtagAdapter);
                hashtagAdapter.notifyDataSetChanged();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerheading.setText(getResources().getString(R.string.SEARCHPROFILE));
                identifier="4";
                apihit=4;
                if (profile.getCurrentTextColor()==getResources().getColor(R.color.dark_blue)){
                    profile.setTextColor(getResources().getColor(R.color.darkgray));
                    filter="";

                }
                else {
                    profile.setTextColor(getResources().getColor(R.color.dark_blue));
                    challenges.setTextColor(getResources().getColor(R.color.darkgray));
                    campaigns.setTextColor(getResources().getColor(R.color.darkgray));
                    tags.setTextColor(getResources().getColor(R.color.darkgray));

                    filter="profile";

                }
                filters.clear();
                searchAdapter = new SearchAdapter(Search_Activity.this,filters,identifier);
                searchRV.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();

            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(Search_Activity.this);
                if (apihit==1)
                {
                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        challenge();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else if (apihit==4)
                {
                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        searchpeople();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else if (apihit==2)
                {
                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        campaign();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else if (apihit==3)
                {
                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        hashtags();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }


            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                dismissKeyboard(Search_Activity.this);
                String text = search.getText().toString();
                if(text.isEmpty()){
                    search.setError(getResources().getString(R.string.Fieldisrequired));
                    search.requestFocus();
                }
                else if (actionId== EditorInfo.IME_ACTION_SEARCH && apihit==1){
                    if (Constant.isOnline(Search_Activity.this)){


                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        challenge();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }


                }
                    else if (actionId== EditorInfo.IME_ACTION_SEARCH && apihit==4)
                    {
                        if (Constant.isOnline(Search_Activity.this)){

                            filters.clear();
                            filters2.clear();
                            filters3.clear();
                            filters4.clear();

                            searchpeople();

                        }
                        else{
                            Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                        }
                    }
                else if (actionId== EditorInfo.IME_ACTION_SEARCH && apihit==2)
                {

                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        campaign();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }else if (actionId== EditorInfo.IME_ACTION_SEARCH && apihit==3)
                {

                    if (Constant.isOnline(Search_Activity.this)){

                        filters.clear();
                        filters2.clear();
                        filters3.clear();
                        filters4.clear();

                        hashtags();

                    }
                    else{
                        Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }



                return false;
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        searchRV.setLayoutManager(layoutManager);
        searchRV.setHasFixedSize(true);
        searchRV.setNestedScrollingEnabled(false);

        //

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        searchRV.setLayoutManager(layoutManager2);
        searchRV.setHasFixedSize(true);
        searchRV.setNestedScrollingEnabled(false);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                /*if (filters.size()>0) {
                    filter(s.toString());
                }*/
            }
        });




    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    public void searchpeople()
    {

        String keyword = search.getText().toString();
        if (keyword.isEmpty())
        {
            search.setError(getResources().getString(R.string.Fieldisrequired));
            search.requestFocus();
        }
        else {
            filters.clear();
            txtnoprofile.setVisibility(View.GONE);
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("keyword",keyword);
            params.add("limit","");
            params.add("offset","");
            Log.e("paramsofsearch", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Constant.BASE_URL + "search/search/search_user?", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsesearch",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                            JSONArray user_founds = dataSet.getJSONArray("user_founds");



                            if (user_founds.length()==0)
                            {
                                txtnoprofile.setText("No Profiles");
                                txtnoprofile.setVisibility(View.VISIBLE);
                                filters.clear();
                                searchRV.setVisibility(View.GONE);


                            }
                            else if (user_founds.length()>0){

                                txtnoprofile.setVisibility(View.GONE);
                                searchRV.setVisibility(View.VISIBLE);

                                for (int i=0; i<user_founds.length(); i++) {
                                    JSONObject item = user_founds.getJSONObject(i);

                                    String user_id = item.getString("id");
                                    String username = item.getString("username");
                                    String image = item.getString("image");
                                    String country = item.getString("country");

                                    Search_people search_people = new Search_people();
                                    search_people.setUser_id(user_id);
                                    search_people.setUser_name(username);
                                    search_people.setImage(image);
                                    search_people.setCountry(country);

                                    filters.add(search_people);
                                    searchAdapter = new SearchAdapter(Search_Activity.this,filters,identifier);
                                    searchRV.setAdapter(searchAdapter);
                                    searchAdapter.notifyDataSetChanged();

                                }
                            }



                        }
                        else if(status.equals("false"))
                        {
                            avi.smoothToHide();
                            avibackground.setVisibility(View.GONE);
                            Constant.ErrorToast(Search_Activity.this,jsonObject.getString("errorMessage"));
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
                    Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                }
            });



        }

    }

    public void challenge()
    {
        String keyword = search.getText().toString();
        if (keyword.isEmpty())
        {
            search.setError(getResources().getString(R.string.Fieldisrequired));
            search.requestFocus();
        }
        else {
            filters2.clear();
            txtnoprofile.setVisibility(View.GONE);
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("keyword",keyword);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Constant.BASE_URL + "search/search/search_challenge?", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsesearchc",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true")) {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            JSONArray results = dataSet.getJSONArray("result");

                            if (results.length()==0)
                            {
                                txtnoprofile.setText(getResources().getString(R.string.NoChallenges));
                                txtnoprofile.setVisibility(View.VISIBLE);
                                filters2.clear();
                                searchRV.setVisibility(View.GONE);
                            }
                            else if (results.length() > 0) {
                                txtnoprofile.setVisibility(View.GONE);
                                searchRV.setVisibility(View.VISIBLE);
                                for (int i = 0; i < results.length(); i++) {

                                    ChallengeSearch challengeSearch = new ChallengeSearch();

                                    JSONObject item = results.getJSONObject(i);

                                    String challenge_id = item.getString("challenge_id");
                                    String title = item.getString("title");
                                    if (item.has("image")){
                                        String image = item.getString("image");
                                        challengeSearch.setImage(image);

                                    }
                                    else if (item.has("video")){
                                        String image = item.getString("video");
                                        challengeSearch.setImage(image);

                                    }
                                    else {
                                        challengeSearch.setImage("");

                                    }
                                    String user_id = item.getString("user_id");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String like = item.getString("like_count");
                                    String comments_count = item.getString("comments_count");
                                    String description = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String country = item.getString("country");
                                    String view_count = "0";
                                    String challenge_title = item.getString("challenge_title");

                                    challengeSearch.setUser_id(user_id);
                                    challengeSearch.setUsername(username);
                                    challengeSearch.setProfile_image(profile_image);
                                    challengeSearch.setLike_count(like);
                                    challengeSearch.setComments_count(comments_count);
                                    challengeSearch.setDescription(description);
                                    challengeSearch.setTags(tags);
                                    challengeSearch.setCountry(country);
                                    challengeSearch.setView_count(view_count);
                                    challengeSearch.setChallenge_title(challenge_title);
                                    challengeSearch.setCahllenge_id(challenge_id);
                                    challengeSearch.setTitle(title);
                                    filters2.add(challengeSearch);

                                    challengeSearchAdapter = new ChallengeSearchAdapter(Search_Activity.this, filters2);
                                    searchRV.setAdapter(challengeSearchAdapter);
                                    challengeSearchAdapter.notifyDataSetChanged();


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
                    Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.Somethingwentwrong));


                }
            });





        }
    }

    public void campaign()
    {
        String keyword = search.getText().toString();
        if (keyword.isEmpty())
        {
            search.setError(getResources().getString(R.string.Fieldisrequired));
            search.requestFocus();
        }
        else {
            filters3.clear();
            txtnoprofile.setVisibility(View.GONE);
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("keyword",keyword);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Constant.BASE_URL + "search/search/search_campaign?", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsesearchc",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true")) {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            JSONArray results = dataSet.getJSONArray("result");

                            if (results.length()==0)
                            {
                                txtnoprofile.setText(getResources().getString(R.string.NoCampaigns));
                                txtnoprofile.setVisibility(View.VISIBLE);
                                filters3.clear();
                                searchRV.setVisibility(View.GONE);
                            }
                            else if (results.length() > 0) {
                                txtnoprofile.setVisibility(View.GONE);
                                searchRV.setVisibility(View.VISIBLE);
                                for (int i = 0; i < results.length(); i++) {

                                    CampaignSearch challengeSearch = new CampaignSearch();

                                    JSONObject item = results.getJSONObject(i);

                                    String challenge_id = item.getString("challenge_id");
                                    String title = item.getString("title");
                                    if (item.has("image")){
                                        String image = item.getString("image");
                                        challengeSearch.setImage(image);

                                    }
                                    else if (item.has("video")){
                                        String image = item.getString("video");
                                        challengeSearch.setImage(image);

                                    }
                                    else {
                                        challengeSearch.setImage("");

                                    }
                                    String user_id = item.getString("user_id");
                                    String username = item.getString("username");
                                    String profile_image = item.getString("profile_image");
                                    String like = item.getString("like_count");
                                    String comments_count = item.getString("comments_count");
                                    String description = item.getString("descritpion");
                                    String tags = item.getString("tags");
                                    String country = item.getString("country");
                                    String view_count = "0";
                                    String challenge_title = item.getString("challenge_title");

                                    challengeSearch.setUser_id(user_id);
                                    challengeSearch.setUsername(username);
                                    challengeSearch.setProfile_image(profile_image);
                                    challengeSearch.setLike_count(like);
                                    challengeSearch.setComments_count(comments_count);
                                    challengeSearch.setDescription(description);
                                    challengeSearch.setTags(tags);
                                    challengeSearch.setCountry(country);
                                    challengeSearch.setView_count(view_count);
                                    challengeSearch.setChallenge_title(challenge_title);
                                    challengeSearch.setCahllenge_id(challenge_id);
                                    challengeSearch.setTitle(title);
                                    filters3.add(challengeSearch);

                                    campaignSearchAdapter = new CampaignSearchAdapter(Search_Activity.this, filters3);
                                    searchRV.setAdapter(campaignSearchAdapter);
                                    campaignSearchAdapter.notifyDataSetChanged();


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
                    Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.Somethingwentwrong));


                }
            });

        }
    }

    public void hashtags()
    {
        String keyword = search.getText().toString();
        if (keyword.isEmpty())
        {
            search.setError(getResources().getString(R.string.Fieldisrequired));
            search.requestFocus();
        }
        else
        {
            filters4.clear();
            txtnoprofile.setVisibility(View.GONE);
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("keyword",keyword);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Constant.BASE_URL + "search/search/search_tag?", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("responsetags",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("true")) {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            JSONArray results = dataSet.getJSONArray("result");
                            if (results.length() == 0) {
                                txtnoprofile.setText("No Hashtags");
                                txtnoprofile.setVisibility(View.VISIBLE);
                                filters4.clear();
                                searchRV.setVisibility(View.GONE);
                            } else if (results.length() > 0) {
                                txtnoprofile.setVisibility(View.GONE);
                                searchRV.setVisibility(View.VISIBLE);
                                for (int i = 0; i < results.length(); i++) {

                                    Hashtags hashtags = new Hashtags();

                                    JSONObject item = results.getJSONObject(i);
                                    String user_id = item.getString("user_id");
                                    String user_name = item.getString("username");
                                    String user_image = item.getString("profile_image");
                                    String challenge_id = item.getString("challenge_id");
                                    String challenge_title = item.getString("title");
                                    String challenge_desccription = item.getString("descritpion");
                                    String tag = item.getString("tags");
                                    String challenge_tid = item.getString("challenge_type");
                                    String comment_count = item.getString("comments_count");
                                    String like_count = item.getString("like_count");
                                    if(item.has("image")){
                                        String challenge_image = item.getString("image");
                                        hashtags.setChallenge_image(challenge_image);

                                    }
                                    else {
                                        hashtags.setChallenge_image("");
                                    }
                                    String country = item.getString("country");
                                    String view_count = item.getString("view_count");

                                    hashtags.setUser_id(user_id);
                                    hashtags.setUser_name(user_name);
                                    hashtags.setUser_image(user_image);
                                    hashtags.setChallenge_id(challenge_id);
                                    hashtags.setChallenge_title(challenge_title);
                                    hashtags.setChallenge_desccription(challenge_desccription);
                                    hashtags.setTag(tag);
                                    hashtags.setChallenge_tid(challenge_tid);
                                    hashtags.setComment_count(comment_count);
                                    hashtags.setLike_count(like_count);
                                    hashtags.setCountry(country);
                                    hashtags.setView_cunt(view_count);

                                    filters4.add(hashtags);
                                    hashtagAdapter = new HashtagAdapter(Search_Activity.this, filters4);
                                    searchRV.setAdapter(hashtagAdapter);
                                    hashtagAdapter.notifyDataSetChanged();






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
                        String response = new String(responseBody);
                        Log.e("responsetagsF",response);
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
                    Constant.ErrorToast(Search_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                }
            });

        }

    }


    @Override
    public void onBackPressed() {
        if (bottom.equals("")){
            super.onBackPressed();
        }
        else {
            Intent intent = new Intent(Search_Activity.this,Home_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
