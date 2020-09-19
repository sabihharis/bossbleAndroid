package com.bossble.bossble.Settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bossble.bossble.Adapter.ProfileAttemptedAdapter;
import com.bossble.bossble.Adapter.ProfileCreatedAdapter;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.Profile_attempted;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class EditProfile_Activity extends AppCompatActivity {


    ImageView header,fulllogo,back,imgchangepassword,arrow;
    TextView txteditprofile,txtfullname,txtbio,txtemail,txtphone,txtcountry,txtsecurity,txtchangepassword,txtprofile;
    EditText edfullname,edbio,edemail,edphone,edcountry;
    //CountryCodePicker select_code;
    RelativeLayout r3;
    Button btnsave;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        header = findViewById(R.id.header);
        fulllogo = findViewById(R.id.fulllogo);
        back = findViewById(R.id.back);
        imgchangepassword = findViewById(R.id.imgchangepassword);
        arrow = findViewById(R.id.arrow);
        txteditprofile = findViewById(R.id.txteditprofile);
        txtfullname = findViewById(R.id.txtfullname);
        txtbio = findViewById(R.id.txtbio);
        txtemail = findViewById(R.id.txtemail);
        txtphone = findViewById(R.id.txtphone);
        txtcountry = findViewById(R.id.txtcountry);
        txtsecurity = findViewById(R.id.txtsecurity);
        txtprofile = findViewById(R.id.txtprofile);
        txtchangepassword = findViewById(R.id.txtchangepassword);
        edfullname = findViewById(R.id.edfullname);
        edbio = findViewById(R.id.edbio);
        edemail = findViewById(R.id.edemail);
        edphone = findViewById(R.id.edphone);
        edcountry = findViewById(R.id.edcountry);
        //select_code = findViewById(R.id.select_code);
        r3 = findViewById(R.id.r3);
        btnsave = findViewById(R.id.btnsave);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        fonts();


        if (Constant.isOnline(EditProfile_Activity.this)){

            profile_get();

        }
        else{
            Constant.ErrorToast(EditProfile_Activity.this,getResources().getString(R.string.NoInternetConnection));

        }


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isOnline(EditProfile_Activity.this)){

                    profile_post();

                }
                else{
                    Constant.ErrorToast(EditProfile_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }

            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile_Activity.this,ChangePassword_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    public void fonts() {
        txteditprofile.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        txtprofile.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        txtfullname.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtbio.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtcountry.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtchangepassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtsecurity.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        edfullname.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edbio.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edcountry.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnsave.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void profile_get()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String u_id = pref.getString(Constant.USER_ID,"");


        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.add("user_id",u_id);
        params.add("viewer_id",u_id);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "user/user/get_user_profile?user_id=", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responseget",response);

                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        String user_id = dataSet.getString("id");
                        String username = dataSet.getString("username");
                        String bio = dataSet.getString("bio");
                        String email = dataSet.getString("email");
                        String country = dataSet.getString("country");

                        edfullname.setText(username);
                        edbio.setText(StringEscapeUtils.unescapeJava(bio));
                        edemail.setText(email);
                        edcountry.setText(country);


                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(EditProfile_Activity.this,jsonObject.getString("errorMessage"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsegetF", response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(EditProfile_Activity.this,getResources().getString(R.string.NoInternetConnection));
            }
        });





    }

    public void profile_post()
    {
        String toServer = edbio.getText().toString();
        String bio = StringEscapeUtils.escapeJava(toServer);
        String email = edemail.getText().toString();
        String username = edfullname.getText().toString();
        String country = edcountry.getText().toString();

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String u_id = pref.getString(Constant.USER_ID,"");

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.add("bio",bio);
        params.add("user_id",u_id);
        params.add("email",email);
        params.add("username",username);
        params.add("country",country);
        params.add("mobile","");

        Log.e("editParams", String.valueOf(params));


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/update_profile", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsepost",response);

                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        Constant.SuccessToast(EditProfile_Activity.this,getResources().getString(R.string.ProfileUpdated));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(EditProfile_Activity.this,Settings_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        },1000);




                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(EditProfile_Activity.this,jsonObject.getString("errorMessage"));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsepostF", response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(EditProfile_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }

        });







    }

}

