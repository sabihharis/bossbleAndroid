package com.bossble.bossble.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.R;
import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class DeactivateAccount_Activity extends AppCompatActivity {

    TextView txtdeactivateaccount,txthi,txtdeactivateinfo;
    Button btndeactive;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_account_);

        txtdeactivateaccount = findViewById(R.id.txtdeactivateaccount);
        txthi = findViewById(R.id.txthi);
        txtdeactivateinfo = findViewById(R.id.txtdeactiveinfo);
        btndeactive = findViewById(R.id.btndeactivate);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        btndeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isOnline(DeactivateAccount_Activity.this)){

                    deactivate_account();

                }
                else{
                    Constant.ErrorToast(DeactivateAccount_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }

            }
        });



        Fonts();




    }

    public void Fonts()
    {
        txtdeactivateaccount.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        txthi.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        txtdeactivateinfo.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        btndeactive.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void deactivate_account()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");


        RequestParams params = new RequestParams();
        params.add("user_id",u_id);



        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/member_deactivate",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsedeactivate",response);

                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals(""))
                    {

                        Constant.SuccessToast(DeactivateAccount_Activity.this,getResources().getString(R.string.AccountDeactivated));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

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


                                Intent intent = new Intent(DeactivateAccount_Activity.this,New_OnBoardings_Activity.class);
                                intent.putExtra("vdo",vdo);
                                intent.putExtra("from","splash");
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
                    Log.e("responsedeactivateF", response);

                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(DeactivateAccount_Activity.this,getResources().getString(R.string.Somethingwentwrong));


            }
        });

    }


}
