package com.bossble.bossble.Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Comments.Comments_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.khizar1556.mkvideoplayer.MKPlayer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class VideoPlayer_Activity extends AppCompatActivity implements MKPlayer.playerCallbacks{

    ArrayList<String> cimage=new ArrayList<>();
    MKPlayer mkplayer;
    //new
    RoundedImageView userimage;
    TextView username,usercountry,like,comment,views;
    LinearLayout likelayout;
    ImageView bronze,silver,gold;
    String uname="",uimage="",likes="",comments="",type="",id="",country="",view_count="";

    String user_like = "";
    String user_like_reward = "";
    String like_mode_type="";

    String mode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_);

        userimage=findViewById(R.id.userimage);
        username=findViewById(R.id.username);
        usercountry=findViewById(R.id.usercountry);
        like=findViewById(R.id.like);
        comment=findViewById(R.id.comment);
        views=findViewById(R.id.views);
        likelayout=findViewById(R.id.likelayout);
        bronze=findViewById(R.id.bronze);
        silver=findViewById(R.id.silver);
        gold=findViewById(R.id.gold);

        if (getIntent().hasExtra("mode")){
            mode="no";

            userimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                }
            });

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                }
            });

            usercountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                }
            });

            like.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                    return true;
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));

                }
            });

            views.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
            });
        }



        uname = getIntent().getStringExtra("username");
        uimage = getIntent().getStringExtra("userimage");
        cimage=getIntent().getStringArrayListExtra("image");
        likes = getIntent().getStringExtra("likes");
        comments = getIntent().getStringExtra("comments");
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        country = getIntent().getStringExtra("country");
        view_count = getIntent().getStringExtra("views");

        views.setText(view_count);



        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("no")){
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
                else {
                    Intent intent = new Intent(VideoPlayer_Activity.this, Comments_Activity.class);
                    intent.putExtra("challenge_id",id);
                    startActivity(intent);
                }
            }
        });



        if (usercountry.equals("") || usercountry.equals("null"))
        {
            usercountry.setText("N/A");
        }
        else
        {
            usercountry.setText(country);
        }




        if (uname.equals("") || uname.equals("null")){
            username.setText("N/A");
        }
        else {
            username.setText(uname);
        }
        like.setText(" "+likes);
        comment.setText(" "+comments);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(VideoPlayer_Activity.this)
                .load(uimage)
                .apply(options)
                .into(userimage);

        if (cimage!=null && !cimage.isEmpty()){
            if (cimage.get(0).contains(".mp4")){

                mkplayer = new  MKPlayer(VideoPlayer_Activity.this);
                mkplayer.play(cimage.get(0));
                mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
                    @Override
                    public void onNextClick() {

                    }
                    @Override
                    public void onPreviousClick() {

                    }
                });
            }
        }


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("no")){
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
            }
        });
        usercountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("no")){
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
            }
        });
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("no")){
                    Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                }
            }
        });

        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_like_reward.equals("silver")) {
                    if (Constant.isOnline(VideoPlayer_Activity.this)){

                        like(id,"1");
                    }
                    else{
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                Animation out = AnimationUtils.makeOutAnimation(VideoPlayer_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });

        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_like_reward.equals("gold")) {

                    if (Constant.isOnline(VideoPlayer_Activity.this)){

                        like(id,"2");
                    }
                    else{
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                Animation out = AnimationUtils.makeOutAnimation(VideoPlayer_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });
        bronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_like_reward.equals("bronze")){
                    if (Constant.isOnline(VideoPlayer_Activity.this)){

                        like(id,"3");
                    }
                    else{
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                Animation out = AnimationUtils.makeOutAnimation(VideoPlayer_Activity.this, true);
                out.setDuration(300);
                likelayout.startAnimation(out);
                likelayout.setVisibility(View.GONE);
            }
        });


        if (type.equals("campaign")){
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mode.equals("no")){
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                    else {
                        if (Constant.isOnline(VideoPlayer_Activity.this)){

                            like(id,"4");
                        }
                        else{
                            Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                        }

                    }
                }
            });

        }
        else {

            like.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mode.equals("no")){
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
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

                    if (mode.equals("no")){
                        Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Signintoexplore));
                    }
                    else {
                        if (likelayout.getVisibility()==View.VISIBLE) {
                            Animation out = AnimationUtils.makeOutAnimation(VideoPlayer_Activity.this, true);
                            out.setDuration(300);
                            likelayout.startAnimation(out);
                            likelayout.setVisibility(View.GONE);

                        }

                        if (user_like_reward.equals("silver")){
                            if (Constant.isOnline(VideoPlayer_Activity.this)){

                                like(id,"1");
                            }
                            else{
                                Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                            }


                        }
                        else if (user_like_reward.equals("gold")){
                            if (Constant.isOnline(VideoPlayer_Activity.this)){

                                like(id,"2");
                            }
                            else{
                                Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                            }

                        }
                        else if (user_like_reward.equals("bronze")){
                            if (Constant.isOnline(VideoPlayer_Activity.this)){

                                like(id,"3");
                            }
                            else{
                                Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.NoInternetConnection));

                            }

                        }
                    }
                }
            });
        }




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
                        Constant.LikeSuccessToast(VideoPlayer_Activity.this,getResources().getString(R.string.Liked));
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

                            }

                        }
                        detail2();

                    }
                    else if (status.equals("false") && errorMessage.equals("")){
                        preferences.edit().putString("liked","yes").commit();

                        like.setTextColor(getResources().getColor(R.color.white));
                        like.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                        String like_increase =String.valueOf(Integer.parseInt(likes)-1);
                        like.setText(" "+like_increase);
                        likes = like_increase;
                        user_like = "0";


                        detail2();


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
                Constant.ErrorToast(VideoPlayer_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });

    }


    public void detail()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("challenge_id",id);
        if (mode.equals("no")){
            params.add("user_id","1");

        }
        else {
            params.add("user_id",u_id);
        }

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


                        String comments_count = dataSet.getString("comments_count");
                        //view_count = dataSet.getString("view_count");

                        comment.setText(" "+comments_count);

                        user_like = dataSet.getString("user_like");
                        user_like_reward = dataSet.getString("user_like_reward");
                        String like_count = dataSet.getString("like_count");
                        likes = like_count;
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

    public void detail2()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("challenge_id",id);
        if (mode.equals("no")){
            params.add("user_id","1");
        }
        else {
            params.add("user_id",u_id);
        }

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


                        user_like = dataSet.getString("user_like");
                        user_like_reward = dataSet.getString("user_like_reward");
                        String like_count = dataSet.getString("like_count");
                        likes = like_count;


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mkplayer!=null){
            mkplayer.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mkplayer!=null){
            mkplayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mkplayer.play(cimage.get(0));
        detail();


    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onPreviousClick() {

    }
}
