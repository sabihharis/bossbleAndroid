package com.bossble.bossble.ProfileSetup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.ForgotPassword.Forgot_Password_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.R;
import com.bossble.bossble.SigninSignup.SigninSignup.Signin_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_With_Phone_Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.mvc.imagepicker.ImagePicker;
import com.rilixtech.widget.countrycodepicker.Country;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;


public class Profile_Setup_Activity extends AppCompatActivity /*implements IPickResult*/ {


    TextView txtfullname, txtbio, txtemail, txtcountry, txtphone, txtbirth, txtprofile;
    EditText edfullname, edbio, edemail, edphone, edbirth, edcountry;
    Button btnsave;
    ImageView imgprofile, imgupload;
    Calendar myCalendar;
    com.rilixtech.widget.countrycodepicker.CountryCodePicker select_code;

    String cname = "";
    String code = "";

    //PickImageDialog dialog;
    boolean gallery = false;
    File fileimage;

    String user_image = "";
    SharedPreferences pref;

    String user_id = "", phone_code = "", phone = "", email = "", from = "";
    String name = "";
    String cname2 ="";

    String social_id = "", platform = "", social_platform = "", full_name = "";

    DatePickerDialog.OnDateSetListener mlistener;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    DatePickerDialog datePickerDialog;
    //

    String signupemail="",signupphone="";
    String phone_email="";

    int satisfy=0;
    String filepath="";
    File ImageFile;

    String cip = "";

    SamLocationRequestService samLocationRequestService;

    String latitude="";
    String longitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile__setup_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);

        user_id = pref.getString(Constant.USER_ID, "");
        phone_code = pref.getString(Constant.CODE, "");
        //phone = pref.getString(Constant.PHONE, "");
        email = pref.getString(Constant.EMAIL_ID, "");
        cname = pref.getString(Constant.COUNTRY, "");
        myCalendar = Calendar.getInstance();
        txtfullname = findViewById(R.id.txtfullname);
        txtbio = findViewById(R.id.txtbio);
        txtemail = findViewById(R.id.txtemail);
        txtcountry = findViewById(R.id.txtcountry);
        txtphone = findViewById(R.id.txtphone);
        txtbirth = findViewById(R.id.txtbirth);
        edfullname = findViewById(R.id.edfullname);
        edbio = findViewById(R.id.edbio);
        edemail = findViewById(R.id.edemail);
        edphone = findViewById(R.id.edphone);
        edcountry = findViewById(R.id.edcountry);
        edbirth = findViewById(R.id.edbirth);
        btnsave = findViewById(R.id.btnsave);
        imgprofile = findViewById(R.id.imgplaceholder);
        imgupload = findViewById(R.id.imgupload);
        txtprofile = findViewById(R.id.txtprofile);
        select_code = findViewById(R.id.select_code);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        //signin
        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip", cip);

        samLocationRequestService = new SamLocationRequestService(Profile_Setup_Activity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                Log.e("Location", String.valueOf(location));

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());




            }
        },10);

        if (getIntent().hasExtra("fromemail")) {
            satisfy=1;
            Log.e("satisfy", String.valueOf(satisfy));
            email = getIntent().getStringExtra("signupemail");
            code = getIntent().getStringExtra("fromemailcode");
             cname2 = getIntent().getStringExtra("cname");
            edemail.setText(email);
            edcountry.setText(select_code.getDefaultCountryName());
            edemail.setEnabled(false);

            if (code.equals("1") && cname2.equals("United States")){

                select_code.setCountryForNameCode("US");

            }
            else if (code.equals("1") && cname2.equals("Canada")){
                select_code.setCountryForNameCode("CA");

            }
            else if (code.equals("1") && cname2.equals("Dominican Republic")){
                select_code.setCountryForNameCode("DO");

            }
            else if (code.equals("1") && cname2.equals("Puerto Rico")){
                select_code.setCountryForNameCode("PR");

            }
            else {
                select_code.setCountryForPhoneCode(Integer.parseInt(code));
            }

            edcountry.setText(select_code.getSelectedCountryName());



        }

        else if (getIntent().hasExtra("fromphone")) {
            satisfy=2;
            code = getIntent().getStringExtra("fromphonecode");
             cname2 = getIntent().getStringExtra("cname");
            signupphone = getIntent().getStringExtra("signupphone");



            if (code.equals("1") && cname2.equals("United States")){

                select_code.setCountryForNameCode("US");

            }
            else if (code.equals("1") && cname2.equals("Canada")){
                select_code.setCountryForNameCode("CA");

            }
            else if (code.equals("1") && cname2.equals("Dominican Republic")){
                select_code.setCountryForNameCode("DO");

            }
            else if (code.equals("1") && cname2.equals("Puerto Rico")){
                select_code.setCountryForNameCode("PR");

            }
            else if (!code.equals("1")){
                select_code.setCountryForPhoneCode(Integer.parseInt(code));
            }
            else
            {
                select_code.setCountryForNameCode("DE");
            }

            edphone.setText(signupphone);
            edcountry.setText(select_code.getSelectedCountryName());

            select_code.setEnabled(false);
            select_code.setActivated(false);
            select_code.setFocusable(false);
            select_code.setClickable(false);
            edphone.setEnabled(false);
        }
        else if (getIntent().hasExtra("social_id")) {
            satisfy=3;
            social_id = getIntent().getStringExtra("social_id");
            full_name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("mail");
            Log.e("psocial_id",social_id);
            Log.e("pfull_name",full_name);
            if (!getIntent().getStringExtra("user_image").equals("null")){
                user_image = getIntent().getStringExtra("user_image");
            }
            social_platform = getIntent().getStringExtra("social_platform");
            cname2 = getIntent().getStringExtra("cname");
            code = getIntent().getStringExtra("fromsocialcode");

            if (code.equals("1") && cname2.equals("United States")){

                select_code.setCountryForNameCode("US");

            }
            else if (code.equals("1") && cname2.equals("Canada")){
                select_code.setCountryForNameCode("CA");

            }
            else if (code.equals("1") && cname2.equals("Dominican Republic")){
                select_code.setCountryForNameCode("DO");

            }
            else if (code.equals("1") && cname2.equals("Puerto Rico")){
                select_code.setCountryForNameCode("PR");

            }
            else {
                select_code.setCountryForPhoneCode(Integer.parseInt(code));
            }

            edcountry.setText(select_code.getSelectedCountryName());

            if (social_platform.equals("1")) {
                platform = "facebook";

            } else if (social_platform.equals("2")) {
                platform = "google";
            } else if (social_platform.equals("3")) {
                platform = "instagram";
            }


            edemail.setEnabled(false);

        }




        if (!user_image.equals("") || !user_image.isEmpty()) {
            if (platform.equals("facebook")){
                if (user_image.contains("graph.facebook.com")){
                    RequestOptions options = new RequestOptions();
                    options.centerCrop();
                    options.placeholder(R.drawable.user_image_placeholder);
                    Glide.with(Profile_Setup_Activity.this)
                            .load(user_image)
                            .apply(options)
                            .into(imgprofile);
                }
                else {
                    user_image="";
                }
            }
            else{
                if (user_image.equals("null")){
                    user_image="";
                }
                else {
                    RequestOptions options = new RequestOptions();
                    options.centerCrop();
                    options.placeholder(R.drawable.user_image_placeholder);
                    Glide.with(Profile_Setup_Activity.this)
                            .load(user_image)
                            .apply(options)
                            .into(imgprofile);

                }
            }
        }
        if (!full_name.isEmpty())
        {
            edfullname.setText(full_name);
        }


        if (!email.isEmpty()) {
            edemail.setText(email);
        }


        edcountry.setEnabled(false);


        select_code.setOnCountryChangeListener(new com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                code = select_code.getSelectedCountryCode();
                cname = select_code.getSelectedCountryName();
                Log.e("selectedcode", cname);
                edcountry.setText(cname);
            }
        });

        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(Profile_Setup_Activity.this, "Select your image:");

            }
        });
        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(Profile_Setup_Activity.this, "Select your image:");
            }
        });


        fonts();

        mlistener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
                Log.e("Dateeee",i +" "+i1+" "+i2);

            }
        };
        edbirth.setShowSoftInputOnFocus(false);

        datePickerDialog = new DatePickerDialog(Profile_Setup_Activity.this,android.R.style.Theme_Holo_Dialog_MinWidth,mlistener,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        edbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog.show();

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(Profile_Setup_Activity.this);
                if (!social_id.equals("")) {
                    if (Constant.isOnline(Profile_Setup_Activity.this)){

                        social_signup(ImageFile);

                    }
                    else{
                        Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                } else if (satisfy==1){
                    if (Constant.isOnline(Profile_Setup_Activity.this)){

                        profile_setup_email();

                    }
                    else{
                        Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                }
                else if (satisfy==2){

                    if (Constant.isOnline(Profile_Setup_Activity.this)){

                        profile_setup_phone();

                    }
                    else{
                        Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.NoInternetConnection));

                    }
                }
            }
        });


    }

    public void fonts() {
        txtfullname.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtbio.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtcountry.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtbirth.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        txtprofile.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        edfullname.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edbio.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edbirth.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edcountry.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnsave.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        /*txtin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtnew.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtforgot.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtaccount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));*/
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edbirth.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData()!=null){
            Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
            imgprofile.setImageBitmap(bitmap);

            filepath = ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
            ImageFile = new File(filepath);

        }
    }

    public void profile_setup_phone() {

        full_name = edfullname.getText().toString();
        String toServer = edbio.getText().toString();
        String bio = StringEscapeUtils.escapeJava(toServer);
        email = edemail.getText().toString();
        String country = edcountry.getText().toString();
        String birth = edbirth.getText().toString();

        if (full_name.isEmpty()) {
            //ed1.setText(name);
            edfullname.setError(getResources().getString(R.string.Fieldisrequired));
            edfullname.requestFocus();
        } else if (bio.isEmpty()) {
            //ed1.setText(name);
            edbio.setError(getResources().getString(R.string.Fieldisrequired));
            edbio.requestFocus();
        } else if (email.isEmpty()) {
            //ed1.setText(name);
            edemail.setError(getResources().getString(R.string.Fieldisrequired));
            edemail.requestFocus();
        } else if (birth.isEmpty()) {
            //ed1.setText(name);
            edbirth.setError(getResources().getString(R.string.Fieldisrequired));
            edbirth.requestFocus();
        } else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("user_id", user_id);
            params.add("username", full_name);
            params.add("bio_date", bio);
            params.add("country", country);
            params.add("dob", birth);
            params.add("email", email);
            if (ImageFile != null) {
                try {
                    params.put("image", ImageFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Log.e("paramsprofile", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/setup_profile", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responseprofile", response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String user_id = dataSet.getString("id");

                            if (dataSet.get("image") instanceof JSONArray){




                            }
                            else {
                                String image = dataSet.getString("image");

                                pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                            }

                            Intent intent = new Intent(Profile_Setup_Activity.this, Home_Activity.class);
                            startActivity(intent);
                            code="";
                            email="";
                            pref.edit().remove(Constant.PHONE).commit();
                            pref.edit().remove(Constant.CODE).commit();
                            pref.edit().putString(Constant.USER_ID,user_id).commit();
                            pref.edit().remove(Constant.EMAIL_ID).commit();
                            pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                        }
                        else
                        {
                            Constant.ErrorToast(Profile_Setup_Activity.this, jsonObject.getString("errorMessage"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                    String response = new String(responseBody);
                    Log.e("responseprofileF", response);

                }
            });


        }


    }
    public void profile_setup_email() {
        full_name = edfullname.getText().toString();

        String toServer = edbio.getText().toString();
        String bio = StringEscapeUtils.escapeJava(toServer);

        signupphone = edphone.getText().toString();
        String country = edcountry.getText().toString();


        String birth = edbirth.getText().toString();

        if (full_name.isEmpty()) {
            edfullname.setError(getResources().getString(R.string.Fieldisrequired));
            edfullname.requestFocus();
        } else if (bio.isEmpty()) {
            //ed1.setText(name);
            edbio.setError(getResources().getString(R.string.Fieldisrequired));
            edbio.requestFocus();
        } else if (email.isEmpty()) {
            //ed1.setText(name);
            edemail.setError(getResources().getString(R.string.Fieldisrequired));
            edemail.requestFocus();
        } else if (birth.isEmpty()) {
            //ed1.setText(name);
            edbirth.setError(getResources().getString(R.string.Fieldisrequired));
            edbirth.requestFocus();
        } else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("user_id", user_id);
            params.add("username", full_name);
            params.add("bio_date", bio);
            params.add("country", country);
            params.add("dob", birth);
            params.add("mobile",code + signupphone);
            if (ImageFile != null) {
                try {
                    params.put("image", ImageFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/setup_profile", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responseprofile", response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String user_id = dataSet.getString("id");

                            Intent intent = new Intent(Profile_Setup_Activity.this, Home_Activity.class);
                            startActivity(intent);
                            code="";
                            email="";
                            if (dataSet.get("image") instanceof JSONArray){




                            }
                            else {
                                String image = dataSet.getString("image");

                                pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                            }

                            pref.edit().remove(Constant.PHONE).commit();
                            pref.edit().remove(Constant.CODE).commit();
                            pref.edit().putString(Constant.USER_ID,user_id).commit();
                            pref.edit().remove(Constant.EMAIL_ID).commit();
                            pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                        }
                        else
                        {
                            Constant.ErrorToast(Profile_Setup_Activity.this, jsonObject.getString("errorMessage"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                    String response = new String(responseBody);
                    Log.e("responseprofileF", response);

                }
            });


        }


    }

    public void social_signup(File file) {

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String device_token = pref.getString(Constant.REG_TOKEN,"");


        String dob = edbirth.getText().toString();
        String country = edcountry.getText().toString();
        String toServer = edbio.getText().toString();
        String bio = StringEscapeUtils.escapeJava(toServer);
        phone = edphone.getText().toString();

        if (bio.isEmpty()) {
            //ed1.setText(name);
            edbio.setError(getResources().getString(R.string.Fieldisrequired));
            edbio.requestFocus();
        } else if (dob.isEmpty()) {
            //ed1.setText(name);
            edbirth.setError(getResources().getString(R.string.Fieldisrequired));
            edbirth.requestFocus();
        }
        else {


            RequestParams params = new RequestParams();

            if (!user_image.equals("") && !user_image.isEmpty() && !user_image.equals("null")) {
                params.add("profile_url", user_image);
                Log.e("log_user_image", user_image);
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);
                params.add("username", full_name);
                params.add("dob", dob);
                params.add("email", email);
                params.add("social_media_id", social_id);
                params.add("social_platform",social_platform);
                params.add("device_type", "android");
                params.add("device_token",device_token);
                params.add("country", country);
                params.add("bio_date", bio);
                params.add("mobile", code + phone);


                Log.e("e_params", String.valueOf(params));

                AsyncHttpClient client = new AsyncHttpClient();
                client.post("https://bossble.com/api/user/user/setup_social_profile", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                        String response = new String(responseBody);
                        Log.e("responsesignup", response);
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("true")) {
                                signin();
                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                                String user_id = dataSet.getString("id");

                                Intent intent = new Intent(Profile_Setup_Activity.this, Home_Activity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("name", name);
                                intent.putExtra("mail", email);
                                intent.putExtra("user_image", user_image);
                                if (dataSet.get("image") instanceof JSONArray){




                                }
                                else {
                                    String image = dataSet.getString("image");

                                    pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                                }

                                pref.edit().putString(Constant.EMAIL_ID, email).commit();
                                pref.edit().putString(Constant.USER_ID, user_id).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN, true).commit();

                                startActivity(intent);
                            } else if (status.equals("false")) {
                                Constant.ErrorToast(Profile_Setup_Activity.this, jsonObject.getString("errorMessage"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (responseBody != null) {

                            String response = new String(responseBody);
                            Log.e("profile_response", response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String errorMessage = jsonObject.getString("errorMessage");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.Somethingwentwrong));


                    }
                });

            }
            else {
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);
                if (file != null) {
                    try {
                        params.put("image", file);
                        Log.e("log_image", String.valueOf(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                params.add("username", full_name);
                params.add("dob", dob);
                params.add("email", email);
                params.add("social_media_id", social_id);
                params.add("social_platform", platform);
                params.add("device_type", "android");
                params.add("device_token",device_token);
                params.add("country", country);
                params.add("bio_date", bio);
                params.add("mobile", code + phone);
                Log.e("e_params", String.valueOf(params));
                AsyncHttpClient client = new AsyncHttpClient();
                client.post("https://bossble.com/api/user/user/setup_social_profile", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                        String response = new String(responseBody);
                        Log.e("responsesignup", response);
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("true")) {
                                signin();
                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                                String user_id = dataSet.getString("id");

                                Intent intent = new Intent(Profile_Setup_Activity.this, Home_Activity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("name", name);
                                intent.putExtra("mail", email);
                                intent.putExtra("user_image", user_image);
                                if (dataSet.get("image") instanceof JSONArray){




                                }
                                else {
                                    String image = dataSet.getString("image");

                                    pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                                }

                                final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                pref.edit().putString(Constant.EMAIL_ID, email).commit();
                                pref.edit().putString(Constant.USER_ID, user_id).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN, true).commit();

                                startActivity(intent);
                            } else if (status.equals("false")) {
                                Constant.ErrorToast(Profile_Setup_Activity.this, jsonObject.getString("errorMessage"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (responseBody != null) {

                            String response = new String(responseBody);
                            Log.e("profile_response", response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String errorMessage = jsonObject.getString("errorMessage");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        Constant.ErrorToast(Profile_Setup_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                    }
                });


            }
        }

    }

    public void signin()
    {

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String device_token = pref.getString(Constant.REG_TOKEN,"");


        RequestParams params = new RequestParams();

        params.add("register_ip",cip);
        params.add("device_id","ewewewq");
        params.add("device_token",device_token);
        params.add("device_type","android");
        params.add("latitude",latitude);
        params.add("longitude",longitude);
        params.add("social_media_id",social_id);
        params.add("social_platform",platform);
        params.add("email",email);
        Log.e("s_params", String.valueOf(params));


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/login_social", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String response = new String(responseBody);
                Log.e("signinresponse",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                        /*if (status.equals("true"))
                        {*/

                    //JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                    //id = dataSet.getString("id");

                     if (errorMessage.equals(""))
                    {

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        String id = dataSet.getString("id");
                        String email = dataSet.getString("email");
                        String mobile = dataSet.getString("mobile");
                        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                        if (dataSet.get("image") instanceof JSONArray){

                        }
                        else {
                            String image = dataSet.getString("image");

                            pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                        }

                        pref.edit().putString(Constant.EMAIL_ID,email).commit();
                        pref.edit().putString(Constant.MOBILE,mobile).commit();
                        pref.edit().putString(Constant.USER_ID,id).commit();
                        pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                        Intent intent = new Intent(Profile_Setup_Activity.this,Home_Activity.class);
                        startActivity(intent);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.e("signinresponseF", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



    }




    @Override
    public void onBackPressed() {
        final String vdo = String.valueOf(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid202005080004));
        Intent intent = new Intent(Profile_Setup_Activity.this,New_OnBoardings_Activity.class);
        intent.putExtra("vdo",vdo);
        intent.putExtra("from","splash");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }




}

