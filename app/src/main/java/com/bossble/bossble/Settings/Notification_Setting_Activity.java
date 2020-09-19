package com.bossble.bossble.Settings;

import android.content.SharedPreferences;
import android.graphics.Typeface;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Notifications.Notification_Activity;
import com.bossble.bossble.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Notification_Setting_Activity extends AppCompatActivity {

    TextView txtprivate,txtadmire,txtchallenge,txtcomments,txtlikes,txtchat,txtnear,txtnotification;
    Switch switchprivate,switchadmire,switchchallenge,switchcomments,switchlikes,switchchat,switchnear;


    String priv,admire,challenge,comments,likes,chat,near;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__setting_);

        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        txtprivate = findViewById(R.id.txtprivate);
        txtadmire = findViewById(R.id.txtadmire);
        txtchallenge = findViewById(R.id.txtchallenge);
        txtcomments = findViewById(R.id.txtcomments);
        txtlikes = findViewById(R.id.txtlikes);
        txtchat = findViewById(R.id.txtchat);
        txtnear = findViewById(R.id.txtnear);
        switchprivate = findViewById(R.id.switchprivate);
        switchadmire = findViewById(R.id.switchadmire);
        switchchallenge = findViewById(R.id.switchchallenge);
        switchcomments = findViewById(R.id.switchcomments);
        switchlikes = findViewById(R.id.switchlikes);
        switchnear = findViewById(R.id.switchnear);
        switchchat = findViewById(R.id.switchchat);
        txtnotification = findViewById(R.id.txtnotification);

        SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
/*
        if (pref.contains(Constant.PRIVATE_MESSAGES))
        {
            priv = pref.getString(Constant.PRIVATE_MESSAGES,"");

            if (priv.equals("1"))
            {
                switchprivate.setChecked(true);
            }
            else if (priv.equals("0"))
            {
                switchprivate.setChecked(false);
            }
        }

        if (pref.contains(Constant.ADMIRE))
        {
            admire = pref.getString(Constant.ADMIRE,"");

            if (admire.equals("1"))
            {
                switchadmire.setChecked(true);
            }
            else if (admire.equals("0"))
            {
                switchadmire.setChecked(false);
            }
        }

        if (pref.contains(Constant.CHALLENGE_REQUEST))
        {
            challenge = pref.getString(Constant.CHALLENGE_REQUEST,"");

            if (challenge.equals("1"))
            {
                switchchallenge.setChecked(true);
            }
            else if (challenge.equals("0"))
            {
                switchchallenge.setChecked(false);
            }
        }



        if (pref.contains(Constant.COMMENTS))
        {
            comments = pref.getString(Constant.COMMENTS,"");

            if (comments.equals("1"))
            {
                switchcomments.setChecked(true);
            }
            else if (comments.equals("0"))
            {
                switchcomments.setChecked(false);
            }
        }

        if (pref.contains(Constant.LIKES))
        {
            likes = pref.getString(Constant.LIKES,"");

            if (likes.equals("1"))
            {
                switchlikes.setChecked(true);
            }
            else if (likes.equals("0"))
            {
                switchlikes.setChecked(false);
            }
        }

        if (pref.contains(Constant.CHAT_NOTIFICATION))
        {
            chat = pref.getString(Constant.CHAT_NOTIFICATION,"");

            if (chat.equals("1"))
            {
                switchchat.setChecked(true);
            }
            else if (chat.equals("0"))
            {
                switchchat.setChecked(false);
            }
        }
*/









        switchprivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        priv="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        priv="0";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
            }
        });
        switchadmire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        admire="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        admire="0";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
            }
        });
        switchchallenge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        challenge="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        challenge="0";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }

                }
            }
        });
        switchcomments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        comments="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }


                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        comments="0";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }


                }
            }
        });
        switchlikes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        likes="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        likes="0";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }



                }
            }
        });
        switchchat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        chat="1";
                        notification_settings();

                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }


                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        chat="0";
                        notification_settings();


                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }


                }
            }
        });
        switchnear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        near="1";
                        notification_settings();


                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                }
                else
                {
                    if (Constant.isOnline(Notification_Setting_Activity.this)){

                        near="0";
                        notification_settings();


                    }
                    else{
                        Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                }
            }
        });

        Fonts();


        if (Constant.isOnline(Notification_Setting_Activity.this)){
            getNotificationSettings();
        }
        else {
            Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.NoInternetConnection));
        }



    }

    public void Fonts()
    {

        txtprivate.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtadmire.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtchallenge.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtcomments.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtlikes.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtchat.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtnear.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtnotification.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));

    }

    public void notification_settings()
    {


        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",u_id);
        params.add("admire_admiring",admire);
        params.add("private_messages",priv);
        params.add("challenge_request",challenge);
        params.add("comments",comments);
        params.add("likes",likes);
        params.add("near_by", near);

        Log.e("paramsofnoti", String.valueOf(params));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "notification/notification/notification_setting",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsenoti",response);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {

/*
                        pref.edit().putString(Constant.PRIVATE_MESSAGES,priv).commit();
                        pref.edit().putString(Constant.ADMIRE,admire).commit();
                        pref.edit().putString(Constant.CHALLENGE_REQUEST,challenge).commit();
                        pref.edit().putString(Constant.COMMENTS,comments).commit();
                        pref.edit().putString(Constant.LIKES,likes).commit();
                        pref.edit().putString(Constant.CHAT_NOTIFICATION,chat).commit();
                        pref.edit().putString(Constant.NEAR,near).commit();
*/




                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.Somethingwentwrong));
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();



            }
        });


    }

    private void getNotificationSettings(){

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",uid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "notification/notification/get_user_prefrence?user_id=", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = new String(responseBody);
                Log.e("settingsprefs",resp);
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        priv = dataSet.getString("private_messages");
                        admire = dataSet.getString("admire_admiring");
                        challenge = dataSet.getString("challenge_request");
                        comments = dataSet.getString("comments");
                        likes = dataSet.getString("likes");
                        near = dataSet.getString("near_by");
                        chat = dataSet.getString("chat_notification");

                        if (priv.equals("1")){
                            switchprivate.setChecked(true);
                        }
                        else {
                            switchprivate.setChecked(false);
                        }

                        if (admire.equals("1")){
                            switchadmire.setChecked(true);

                        }
                        else {
                            switchadmire.setChecked(false);
                        }

                        if (challenge.equals("1")){
                            switchchallenge.setChecked(true);

                        }
                        else {
                            switchchallenge.setChecked(false);
                        }

                        if (comments.equals("1")){
                            switchcomments.setChecked(true);
                        }
                        else {
                            switchcomments.setChecked(false);
                        }

                        if (likes.equals("1")){
                            switchlikes.setChecked(true);
                        }
                        else {
                            switchlikes.setChecked(false);
                        }
                        if (near.equals("1")){
                            switchnear.setChecked(true);
                        }
                        else {
                            switchnear.setChecked(false);
                        }

                        if (chat.equals("1")){
                            switchchat.setChecked(true);
                        }
                        else {
                            switchchat.setChecked(false);
                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String resp = new String(responseBody);
                    Log.e("settingsprefsF",resp);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Notification_Setting_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });
    }

}