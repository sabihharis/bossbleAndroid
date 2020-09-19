package com.bossble.bossble.New_OnBoarding;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
/*
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
*/
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.ProfileSetup.Profile_Setup_Activity;
import com.bossble.bossble.R;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rilixtech.widget.countrycodepicker.Country;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cz.msebera.android.httpclient.Header;

public class New_OnBoardings_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView lifeis, but, only, showtheworld, beyourboss, name;
    TextView dontlimit,joinotset,challenges,campaigns,support,makeworld;


    TextView skip;
    Handler h;
    int sk=0;

    ImageView logo;
    VideoView videoView;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;
    String vdo;

    int back = 0;

    RelativeLayout onboardingRelative, signupRelative, signinRelative, forgotRelative, signupemailRelative, signupphoneRelative, verificationRelative, resetRelative;


    LinearLayout l1, l111, l1111;
    TextView txtaccount, txtforgot, btnemail, btnphone, txtverify;

    //signup
    TextView txtchallenge, txtworld, btninsta, btngoogle, txtmember, txtlogin;
    LoginButton btnsignupfb;
    String latitute;
    String longitude;

    //signupwithemail
    TextView txtenter, txtemail, signupemailtxtmember, signupemailtxtlogin;
    EditText signupemailedemail, signupemailedpassword;
    Button btnsend;
    String signupemail_smscode = "", signup_email = "";

    //forgotpassword
    TextView forgottxtforgot, txtpassword, txthint;
    EditText forgotedemail;
    String mail_phone = "";
    String email = "";
    String password_change_codes = "";
    String mobiles = "";
    int forg = 0;
    String user_id = "";
    String forgot_user_id="";


    //signupphone
    TextView signupphonetxtenter, txtphone, signupphonetxtmember, signupphonetxtlogin;
    EditText edphone, signupphoneedpassword;
    Button signupphonebtnverify;
    com.rilixtech.widget.countrycodepicker.CountryCodePicker select_code;
    String signup_phone_smscode, signup_phone_number = "";
     String phone="";

    //phone or email registeration
    String from = "";
    //verification
    TextView txtresend;
    EditText edv1, edv2, edv3, edv4;
    int signupM = 0;

    //resetpassword
    TextView txtreset, resettxtpassword;
    EditText resetedpassword, edconfirmpassword;

    //signin
    TextView txtlog, txtin, signintxtforgot, signintxtnew, signintxtaccount;
    EditText edemail, edpassword;
    Button btnlogin;
    int video;
    String cip = "";
    String cont = "";

    String cname = "";
    String code = "";

    String id="";

    //resetpassowrd
    String sms_code="";

    //signinfb
    String fbUserEmail="";
    CallbackManager callbackmanager;
    private final static  int RC_SIGN_IN=2;
    SignInButton sign_google ;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build();
    GoogleApiClient googleApiClient;
    String g_name,mail,user_image;
    String personId;

    //signin work
    ImageView signinmail,signinphone;
    com.rilixtech.widget.countrycodepicker.CountryCodePicker signinselect_code;
    EditText signinedphone;
    String signincode;

    //forgot work
    ImageView forgotmail,forgotphone;
    com.rilixtech.widget.countrycodepicker.CountryCodePicker forgotselect_code;
    EditText forgotedphone;
    String forgetcode;


    Button btnreset, btnverify;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    int login=1,forget=1;

    LinearLayout l11;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();
    String countryname="";

    TextView signupphonetdisclaimer,signupemailtdisclaimer;

    SmsVerifyCatcher smsVerifyCatcher;
    int permit=0;

    SmsBroadcastReceiver smsBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new__on_boardings_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        startSmsUserConsent();



/*
        smsVerifyCatcher = new SmsVerifyCatcher(New_OnBoardings_Activity.this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                //String code = parseCode(message);//Parse verification code
                //etCode.setText(code);//set code in edit text
                //then you can send verification code to server
                if (verificationRelative.getVisibility()==View.VISIBLE){

                    Log.e("textmessages",message);


                            edv1.setText(Character.toString(signup_phone_smscode.charAt(0)));
                            edv2.setText(Character.toString(signup_phone_smscode.charAt(1)));
                            edv3.setText(Character.toString(signup_phone_smscode.charAt(2)));
                            edv4.setText(Character.toString(signup_phone_smscode.charAt(3)));


                }
            }
        });
*/

        signupphonetdisclaimer = findViewById(R.id.signupphonetdisclaimer);
        signupemailtdisclaimer = findViewById(R.id.signupemailtdisclaimer);

        signupphonetdisclaimer.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        signupemailtdisclaimer.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));


        skip = findViewById(R.id.skip);
        lifeis = findViewById(R.id.lifeis);
        but = findViewById(R.id.but);
        only = findViewById(R.id.only);
        showtheworld = findViewById(R.id.showtheworld);
        beyourboss = findViewById(R.id.beyourboss);
        logo = findViewById(R.id.onboardinglogo);
        name = findViewById(R.id.name);
        dontlimit = findViewById(R.id.dontlimit);
        joinotset = findViewById(R.id.joinotset);
        challenges = findViewById(R.id.challenges);
        campaigns = findViewById(R.id.campaigns);
        support = findViewById(R.id.support);
        makeworld = findViewById(R.id.makeworld);
        videoView = findViewById(R.id.videoView);
        onboardingRelative = findViewById(R.id.onboardingRelative);
        signupRelative = findViewById(R.id.signupRelative);
        signinRelative = findViewById(R.id.signinRelative);
        l1 = findViewById(R.id.l1);
        signintxtaccount = findViewById(R.id.signintxtaccount);
        signintxtforgot = findViewById(R.id.signintxtforgot);
        forgotRelative = findViewById(R.id.forgotRelative);
        signupemailRelative = findViewById(R.id.signupemailRelative);
        l111 = findViewById(R.id.l111);
        btnemail = findViewById(R.id.btnemail);
        signupphoneRelative = findViewById(R.id.signupphoneRelative);
        l1111 = findViewById(R.id.l1111);
        btnphone = findViewById(R.id.btnphone);
        verificationRelative = findViewById(R.id.verificationRelative);
        txtverify = findViewById(R.id.txtverify);
        resetRelative = findViewById(R.id.resetRelative);
        btnreset = findViewById(R.id.btnreset);
        btnverify = findViewById(R.id.btnverify);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        l11 = findViewById(R.id.l11);
        onboardingFonts();
        signupFindbyIDS();
        signupFonts();
        signupemailFindIDS();
        signupemailFonts();
        forgotFindIDS();
        forgotFonts();
        signupphoneFindIDS();
        signupphoneFonts();
        verificationFindIDS();
        verificationFonts();
        resetFindIDS();
        resetFonts();
        signinFindIDS();
        siginFonts();
        appendlists();

        signinmail = findViewById(R.id.mail);
        signinphone = findViewById(R.id.phone);
        signinselect_code = findViewById(R.id.signinselect_code);
        signinedphone = findViewById(R.id.signinedphone);

        forgotmail = findViewById(R.id.forgotmail);
        forgotphone = findViewById(R.id.forgotphone);
        forgotselect_code = findViewById(R.id.forgotselect_code);
        forgotedphone = findViewById(R.id.forgotedphone);

        signincode = signinselect_code.getDefaultCountryCode();
        forgetcode = forgotselect_code.getDefaultCountryCode();

        signinselect_code.setOnCountryChangeListener(new com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                signincode = signinselect_code.getSelectedCountryCode();
            }
        });

        forgotselect_code.setOnCountryChangeListener(new com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                forgetcode = forgotselect_code.getSelectedCountryCode();

            }
        });


        //signin work
        signinphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
        signinmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                signinmail.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (edemail.getVisibility()!=View.VISIBLE){
                    signinedphone.animate().alpha(0.0f).setDuration(1000);
                    signinselect_code.animate()
                            .alpha(0.0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    signinselect_code.setVisibility(View.GONE);
                                    signinedphone.setVisibility(View.GONE);

                                    signinedphone.setText("");

                                    edemail.setAlpha(0f);
                                    edemail.setVisibility(View.VISIBLE);
                                    edemail.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);

                                    login=1;
                                }
                            });
                }
            }
        });
        signinphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinmail.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                signinphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (signinedphone.getVisibility()!=View.VISIBLE){
                    edemail.animate()
                            .alpha(0.0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    edemail.setVisibility(View.GONE);
                                    signinedphone.setAlpha(0f);
                                    signinedphone.setVisibility(View.VISIBLE);
                                    signinedphone.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);

                                    edemail.setText("");


                                    signinselect_code.setAlpha(0f);
                                    signinselect_code.setVisibility(View.VISIBLE);
                                    signinselect_code.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);
                                    login=2;
                                }
                            });
                }
            }
        });

        //forgot work
        forgotphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
        forgotmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                forgotmail.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (forgotedemail.getVisibility()!=View.VISIBLE){
                    forgotedphone.animate().alpha(0.0f).setDuration(1000);
                    forgotselect_code.animate()
                            .alpha(0.0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    forgotselect_code.setVisibility(View.GONE);
                                    forgotedphone.setVisibility(View.GONE);

                                    forgotedphone.setText("");

                                    forgotedemail.setAlpha(0f);
                                    forgotedemail.setVisibility(View.VISIBLE);
                                    forgotedemail.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);
                                    forget=1;
                                }
                            });
                }
            }
        });

        forgotphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotmail.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.alphawhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                forgotphone.setColorFilter(ContextCompat.getColor(New_OnBoardings_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (forgotedphone.getVisibility()!=View.VISIBLE){
                    forgotedemail.animate()
                            .alpha(0.0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    forgotedemail.setVisibility(View.GONE);

                                    forgotedemail.setText("");


                                    forgotedphone.setAlpha(0f);
                                    forgotedphone.setVisibility(View.VISIBLE);
                                    forgotedphone.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);
                                    forgotselect_code.setAlpha(0f);
                                    forgotselect_code.setVisibility(View.VISIBLE);
                                    forgotselect_code.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .setListener(null);
                                    forget=2;
                                }
                            });
                }
            }
        });

        //googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();//.enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        //signin
        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip", cip);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(New_OnBoardings_Activity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                if (Constant.isOnline(New_OnBoardings_Activity .this)){

                    signin();

                }
                else{
                    Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }

            }
        });

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
        pref.edit().putString(Constant.CODE, code).commit();
        pref.edit().putString(Constant.COUNTRY, cname).commit();
        select_code.setOnCountryChangeListener(new com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                code = select_code.getSelectedCountryCode();
                cname = select_code.getSelectedCountryName();
                pref.edit().putString(Constant.CODE, code).commit();
                pref.edit().putString(Constant.COUNTRY, cname).commit();
            }
        });

        vdo = getIntent().getStringExtra("vdo");
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                videoView.setVideoURI(Uri.parse(vdo));
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mMediaPlayer = mediaPlayer;
                        mMediaPlayer.setLooping(true);

                        if (mcurrentVideoPosition != 0) {
                            mMediaPlayer.seekTo(mcurrentVideoPosition);
                            mMediaPlayer.start();
                        }
                    }
                });
            }
        });
        thread.start();
/*
        videoView.setVideoURI(Uri.parse(vdo));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);

                if (mcurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mcurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
*/

        if (getIntent().hasExtra("from")){
            String from = getIntent().getStringExtra("from");
            if (from.equals("splash")){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        signupRelative.setAlpha(0f);
                        signupRelative.setVisibility(View.VISIBLE);
                        signupRelative.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(null);
                    }
                }, 1000);
            }
            getLocation();
        }
        else {
            onboardingRelative.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    PerformAnimations();

                }
            }, 1000);
        }



        signupphonebtnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(New_OnBoardings_Activity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (Constant.isOnline(New_OnBoardings_Activity .this)){

                    signup_with_phonw();

                }
                else{
                    Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }

            }
        });




        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        lifeis.getLayoutParams().height = height / 2;
        but.getLayoutParams().height = height / 2;
        only.getLayoutParams().height = height / 2;
        showtheworld.getLayoutParams().height = height / 2;
        beyourboss.getLayoutParams().height = height / 2;
        challenges.getLayoutParams().height = height / 2;
        campaigns.getLayoutParams().height = height / 2;

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sk =1;
                        onboardingRelative.setVisibility(View.GONE);
                        signupRelative.setAlpha(0f);
                        signupRelative.setVisibility(View.VISIBLE);
                        signupRelative.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(null);
                    }
                }, 1000);

                getLocation();


            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signupRelative.setVisibility(View.GONE);
                        signinRelative.setVisibility(View.VISIBLE);
                        signinRelative.animate().alpha(1f).setDuration(500).setListener(null);
                    }
                });
            }
        });

        signintxtaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signinRelative.setVisibility(View.GONE);
                        signupRelative.setVisibility(View.VISIBLE);
                        signupRelative.animate().alpha(1f).setDuration(500).setListener(null);
                        edemail.setText("");
                        edpassword.setText("");
                        signinedphone.setText("");
                    }
                });
            }
        });

        signintxtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signinRelative.setVisibility(View.GONE);
                        forgotRelative.setVisibility(View.VISIBLE);
                        forgotRelative.animate().alpha(1f).setDuration(500).setListener(null);
                    }
                });
            }
        });

        l111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupemailRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signupemailRelative.setVisibility(View.GONE);
                        signinRelative.setVisibility(View.VISIBLE);
                        signinRelative.animate().alpha(1f).setDuration(500).setListener(null);

                        signupemailedemail.setText("");
                        signupemailedpassword.setText("");
                    }
                });
            }
        });
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signupRelative.setVisibility(View.GONE);
                        signupemailRelative.setVisibility(View.VISIBLE);
                        signupemailRelative.animate().alpha(1f).setDuration(500).setListener(null);
                    }
                });
            }
        });
        btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    signupRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            signupRelative.setVisibility(View.GONE);
                            signupphoneRelative.setVisibility(View.VISIBLE);
                            signupphoneRelative.animate().alpha(1f).setDuration(500).setListener(null);
                            edphone.setText("");
                            signupphoneedpassword.setText("");
                        }
                    });
            }
        });
        l1111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupphoneRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        signupphoneRelative.setVisibility(View.GONE);
                        signinRelative.setVisibility(View.VISIBLE);
                        signinRelative.animate().alpha(1f).setDuration(500).setListener(null);
                    }
                });
            }
        });

        txtverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(New_OnBoardings_Activity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (Constant.isOnline(New_OnBoardings_Activity .this)){

                    reset_password();

                }
                else{
                    Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }


            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(New_OnBoardings_Activity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (Constant.isOnline(New_OnBoardings_Activity .this)){

                    forgot_Password();

                }
                else{
                    Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }

            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(New_OnBoardings_Activity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (Constant.isOnline(New_OnBoardings_Activity .this)){

                    Signup_with_email();

                }
                else{
                    Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }

            }
        });


        edv1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edv1.getText().toString().length() == 1)     //size as per your requirement
                {
                    edv2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }

        });

        edv2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edv2.getText().toString().length() == 1)     //size as per your requirement
                {
                    edv3.requestFocus();
                } else if (edv2.getText().toString().length() == 0) {
                    edv1.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }

        });

        edv3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edv3.getText().toString().length() == 1)     //size as per your requirement
                {
                    edv4.requestFocus();
                } else if (edv3.getText().toString().length() == 0) {
                    edv2.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }


        });

        edv4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edv4.getText().toString().length() == 1) {


                    if (forg == 1) {
                        if (Constant.isOnline(New_OnBoardings_Activity .this)){

                            forgetpassword_verification();

                        }
                        else{
                            Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                        }

                    } else {
                        if (Constant.isOnline(New_OnBoardings_Activity .this)){

                            verifciation();

                        }
                        else{
                            Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.NoInternetConnection));
                        }

                    }

                } else if (edv4.getText().toString().length() == 0) {
                    edv3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }


        });

    }

    public String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
    private void PerformAnimations() {
        lifeis.setVisibility(View.VISIBLE);
        Animation right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_anim);
        lifeis.startAnimation(right);

        h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                lifeis.animate()
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                lifeis.setVisibility(View.GONE);
                                but.setAlpha(0f);
                                but.setVisibility(View.VISIBLE);
                                but.animate()
                                        .alpha(1f)
                                        .setDuration(500)
                                        .setListener(null);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        but.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);

                                                but.setVisibility(View.GONE);
                                                only.setVisibility(View.VISIBLE);
                                                Animation right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_animation);
                                                only.startAnimation(right);

                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        only.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                super.onAnimationEnd(animation);

                                                                only.setVisibility(View.GONE);
                                                                showtheworld.setVisibility(View.VISIBLE);
                                                                showtheworld.animate()
                                                                        .alpha(1f)
                                                                        .setDuration(500)
                                                                        .setListener(null);

                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {

                                                                        showtheworld.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                                            @Override
                                                                            public void onAnimationEnd(Animator animation) {
                                                                                super.onAnimationEnd(animation);

                                                                                showtheworld.setVisibility(View.GONE);
                                                                                beyourboss.setVisibility(View.VISIBLE);
                                                                                Animation right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                                                                                beyourboss.startAnimation(right);

                                                                                new Handler().postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {

                                                                                        beyourboss.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                                                            @Override
                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                super.onAnimationEnd(animation);
                                                                                                beyourboss.setVisibility(View.GONE);

                                                                                                challenges.setAlpha(0f);
                                                                                                challenges.setVisibility(View.VISIBLE);
                                                                                                challenges.animate()
                                                                                                        .alpha(1f)
                                                                                                        .setDuration(500)
                                                                                                        .setListener(null);

                                                                                                joinotset.setAlpha(0f);
                                                                                                joinotset.setVisibility(View.VISIBLE);
                                                                                                joinotset.animate()
                                                                                                        .alpha(1f)
                                                                                                        .setDuration(500)
                                                                                                        .setListener(null);

                                                                                                dontlimit.setAlpha(0f);
                                                                                                dontlimit.setVisibility(View.VISIBLE);
                                                                                                dontlimit.animate()
                                                                                                        .alpha(1f)
                                                                                                        .setDuration(500)
                                                                                                        .setListener(null);

                                                                                                new Handler().postDelayed(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {

                                                                                                        joinotset.animate().alpha(0.0f).setDuration(500);
                                                                                                        dontlimit.animate().alpha(0.0f).setDuration(500);
                                                                                                        challenges.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                                                                            @Override
                                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                                super.onAnimationEnd(animation);
                                                                                                                challenges.setVisibility(View.GONE);
                                                                                                                joinotset.setVisibility(View.GONE);
                                                                                                                dontlimit.setVisibility(View.GONE);

                                                                                                                campaigns.setAlpha(0f);
                                                                                                                campaigns.setVisibility(View.VISIBLE);
                                                                                                                campaigns.animate()
                                                                                                                        .alpha(1f)
                                                                                                                        .setDuration(500)
                                                                                                                        .setListener(null);
                                                                                                                support.setAlpha(0f);
                                                                                                                support.setVisibility(View.VISIBLE);
                                                                                                                support.animate()
                                                                                                                        .alpha(1f)
                                                                                                                        .setDuration(500)
                                                                                                                        .setListener(null);
                                                                                                                makeworld.setAlpha(0f);
                                                                                                                makeworld.setVisibility(View.VISIBLE);
                                                                                                                makeworld.animate()
                                                                                                                        .alpha(1f)
                                                                                                                        .setDuration(500)
                                                                                                                        .setListener(null);


                                                                                                                new Handler().postDelayed(new Runnable() {
                                                                                                                    @Override
                                                                                                                    public void run() {

                                                                                                                        makeworld.animate().alpha(0.0f).setDuration(500);
                                                                                                                        support.animate().alpha(0.0f).setDuration(500);
                                                                                                                        campaigns.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                                                                                            @Override
                                                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                                                super.onAnimationEnd(animation);

                                                                                                                                makeworld.setVisibility(View.GONE);
                                                                                                                                support.setVisibility(View.GONE);
                                                                                                                                campaigns.setVisibility(View.GONE);


                                                                                                                                logo.setVisibility(View.VISIBLE);
                                                                                                                                Animation right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                                                                                                                                logo.startAnimation(right);

                                                                                                                                new Handler().postDelayed(new Runnable() {
                                                                                                                                    @Override
                                                                                                                                    public void run() {

                                                                                                                                        name.setAlpha(0f);
                                                                                                                                        name.setVisibility(View.VISIBLE);
                                                                                                                                        name.animate()
                                                                                                                                                .alpha(1f)
                                                                                                                                                .setDuration(500)
                                                                                                                                                .setListener(null);

                                                                                                                                        new Handler().postDelayed(new Runnable() {
                                                                                                                                            @Override
                                                                                                                                            public void run() {

                                                                                                                                                name.animate().alpha(0.0f).setDuration(500);
                                                                                                                                                logo.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                                                                        super.onAnimationEnd(animation);

                                                                                                                                                        name.setVisibility(View.GONE);
                                                                                                                                                        logo.setVisibility(View.GONE);
                                                                                                                                                        onboardingRelative.setVisibility(View.GONE);

                                                                                                                                                        if (sk!=1){
                                                                                                                                                            signupRelative.setVisibility(View.VISIBLE);
                                                                                                                                                            signupRelative.animate()
                                                                                                                                                                    .alpha(1f)
                                                                                                                                                                    .setDuration(500)
                                                                                                                                                                    .setListener(null);
                                                                                                                                                        }
                                                                                                                                                        back = 1;

                                                                                                                                                        new Handler().postDelayed(new Runnable() {
                                                                                                                                                            @Override
                                                                                                                                                            public void run() {
                                                                                                                                                                getLocation();

                                                                                                                                                            }
                                                                                                                                                        }, 2000);

                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                            }
                                                                                                                                        }, 4000);
                                                                                                                                        //////
                                                                                                                                    }
                                                                                                                                }, 2000);

                                                                                                                            }
                                                                                                                        });
                                                                                                                    }
                                                                                                                }, 4000);
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }, 4000);
                                                                                                //////
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }, 3000);
                                                                                //////


                                                                            }
                                                                        });
                                                                    }
                                                                }, 3000);
                                                                //////
                                                            }
                                                        });
                                                    }
                                                }, 3000);
                                                //////
                                            }
                                        });
                                    }
                                }, 1500);
                                /////


                            }
                        });
            }
        }, 3000);
        /////
    }

    Boolean twice = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        avibackground.setVisibility(View.GONE);
        avi.setVisibility(View.GONE);

        if (signinRelative.getVisibility() == View.VISIBLE) {

            signupRelative.setVisibility(View.VISIBLE);
            signupRelative.animate().alpha(1f).setDuration(500).setListener(null);

            signinRelative.animate().alpha(0).setDuration(500);
            signinRelative.setVisibility(View.GONE);
            edemail.setText("");
            edpassword.setText("");
            signinedphone.setText("");


        } else if (forgotRelative.getVisibility() == View.VISIBLE) {

            signinRelative.setVisibility(View.VISIBLE);
            signinRelative.animate().alpha(1f).setDuration(500).setListener(null);

            forgotRelative.animate().alpha(0).setDuration(500);
            forgotRelative.setVisibility(View.GONE);

            forgotedemail.setText("");
            forgotedphone.setText("");


        } else if (signupemailRelative.getVisibility() == View.VISIBLE) {

            signupRelative.setVisibility(View.VISIBLE);
            signupRelative.animate().alpha(1f).setDuration(500).setListener(null);

            signupemailRelative.animate().alpha(0).setDuration(500);
            signupemailRelative.setVisibility(View.GONE);

            signupemailedemail.setText("");
            signupemailedpassword.setText("");




        } else if (signupphoneRelative.getVisibility() == View.VISIBLE) {

            signupRelative.setVisibility(View.VISIBLE);
            signupRelative.animate().alpha(1f).setDuration(500).setListener(null);

            signupphoneRelative.animate().alpha(0).setDuration(500);
            signupphoneRelative.setVisibility(View.GONE);

            edphone.setText("");
            signupphoneedpassword.setText("");


        } else if (resetRelative.getVisibility() == View.VISIBLE) {

            signinRelative.setVisibility(View.VISIBLE);
            signinRelative.animate().alpha(1f).setDuration(500).setListener(null);

            resetRelative.animate().alpha(0).setDuration(500);
            resetRelative.setVisibility(View.GONE);

            forgotedphone.setText("");
            forgotedemail.setText("");
            edemail.setText("");
            edpassword.setText("");
            signinedphone.setText("");


        } else if (verificationRelative.getVisibility() == View.VISIBLE) {

            signupRelative.setVisibility(View.VISIBLE);
            signupRelative.animate().alpha(1f).setDuration(500).setListener(null);

            verificationRelative.animate().alpha(0).setDuration(500);
            verificationRelative.setVisibility(View.GONE);


        } else if (signupRelative.getVisibility() == View.VISIBLE) {
            if (twice == true) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
            twice = true;
            Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    twice = false;
                }
            }, 3000);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mMediaPlayer!=null){
            video = mMediaPlayer.getCurrentPosition();
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaPlayer!=null){
            videoView.seekTo(video);
            videoView.start();
        }
/*
        final Uri data = this.getIntent().getData();
        if(data != null && data.getScheme().equals("sociallogin") && data.getFragment() != null) {
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            if (accessToken != null) {
                Log.e("instatoken",accessToken);
            } else {
                Log.e("instatoken","fail");
            }
        }
*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length>0){
            int hasPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
            int hasPermission2 = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS);

            if (hasPermission != PackageManager.PERMISSION_GRANTED || hasPermission2 != PackageManager.PERMISSION_GRANTED) {

                //if (grantResults[0]==PackageManager.PERMISSION_DENIED){


                final AlertDialog.Builder builder = new AlertDialog.Builder(New_OnBoardings_Activity.this);
                builder.setCancelable(true);
                builder.setTitle(getResources().getString(R.string.PermissionRequired));
                //builder.setCancelable(false);
                builder.setMessage(getResources().getString(R.string.GotoAppsettingsPermissionsSMSenableit));
                builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //permit=1;
                        dialogInterface.dismiss();
                        New_OnBoardings_Activity.this.onBackPressed();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }
                });
                builder.show();
                // }
            }
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(New_OnBoardings_Activity.this);
            builder.setCancelable(true);
            builder.setTitle(getResources().getString(R.string.PermissionRequired));
            //builder.setCancelable(false);
            builder.setMessage(getResources().getString(R.string.GotoAppsettingsPermissionsSMSenableit));
            builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // permit=1;
                    dialogInterface.dismiss();
                    New_OnBoardings_Activity.this.onBackPressed();

                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            builder.show();

        }


        }
*/

    public void onboardingFonts() {

        lifeis.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        but.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        only.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        showtheworld.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        beyourboss.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        name.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));

        challenges.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        campaigns.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        skip.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));

        joinotset.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        support.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));

        dontlimit.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        makeworld.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));


    }

    public void signupFindbyIDS() {


        txtchallenge = findViewById(R.id.txtchallenge);
        txtworld = findViewById(R.id.txtworld);
        btninsta = findViewById(R.id.btninsta);
        btngoogle = findViewById(R.id.btngoogle);
        // btnfb = findViewById(R.id.btnfb);
        btnemail = findViewById(R.id.btnemail);
        btnphone = findViewById(R.id.btnphone);
        btnemail = findViewById(R.id.btnemail);
        btnphone = findViewById(R.id.btnphone);
        txtmember = findViewById(R.id.txtmember);
        txtlogin = findViewById(R.id.txtlogin);
        btnsignupfb = findViewById(R.id.btnsignupfb);

        Drawable img2 = getResources().getDrawable(R.drawable.fb_icon);
        btnsignupfb.setCompoundDrawablesWithIntrinsicBounds(img2, null, null, null);
        btnsignupfb.setBackground(getResources().getDrawable(R.drawable.button_curve_transparent));
        btnsignupfb.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        btnsignupfb.setTextSize(14);

        callbackmanager = CallbackManager.Factory.create();
        btnsignupfb.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        btnsignupfb.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }





        });

        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

                startActivityForResult(signInIntent,RC_SIGN_IN);
                googleApiClient.connect();
            }

        });

        btninsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*
                final Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("https")
                        .authority("api.instagram.com")
                        .appendPath("oauth")
                        .appendPath("authorize")
                        .appendQueryParameter("client_id", "660864648100018")
                        .appendQueryParameter("redirect_uri", "sociallogin://redirect")
                        .appendQueryParameter("response_type", "token");
                final Intent browser = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
                startActivity(browser);
*/

            }
        });

/*
        final Uri data = this.getIntent().getData();
        if(data != null && data.getScheme().equals("sociallogin") && data.getFragment() != null) {
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            if (accessToken != null) {
                Log.e("instatoken",accessToken);
            } else {
                Log.e("instatoken","fail");
            }
        }
*/


    }

    public void signupFonts() {
        txtchallenge.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtworld.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        txtmember.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtlogin.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        btninsta.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        btngoogle.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        btnemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        btnphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void signupemailFindIDS() {


        txtenter = findViewById(R.id.txtenter);
        txtemail = findViewById(R.id.txtemail);
        signupemailtxtmember = findViewById(R.id.signupemailtxtmember);
        signupemailtxtlogin = findViewById(R.id.signupemailtxtlogin);
        signupemailedemail = findViewById(R.id.signupemailedemail);
        signupemailedpassword = findViewById(R.id.signupemailedpassword);
        txtemail = findViewById(R.id.txtemail);
        btnsend = findViewById(R.id.btnsend);
    }

    public void signupemailFonts() {
        txtenter.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        signupemailtxtmember.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        signupemailtxtlogin.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        signupemailedemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        signupemailedpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnsend.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void forgotFindIDS() {


        forgottxtforgot = findViewById(R.id.forgottxtforgot);
        txtpassword = findViewById(R.id.txtpassword);

        forgotedemail = findViewById(R.id.forgotedemail);
        //txthint = findViewById(R.id.txthint);

    }

    public void forgotFonts() {
        forgottxtforgot.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        forgotedemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        //txthint.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnverify.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void signupphoneFindIDS() {


        signupphonetxtenter = findViewById(R.id.signupphonetxtenter);
        txtphone = findViewById(R.id.txtphone);
        signupphonetxtmember = findViewById(R.id.signupphonetxtmember);
        signupphonetxtlogin = findViewById(R.id.signupphonetxtlogin);
        edphone = findViewById(R.id.edphone);
        signupphoneedpassword = findViewById(R.id.signupphoneedpassword);
        signupphonebtnverify = findViewById(R.id.signupphonebtnverify);
        select_code = findViewById(R.id.select_code);

    }

    public void signupphoneFonts() {
        signupphonetxtenter.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        signupphonetxtmember.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        signupphonetxtlogin.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        edphone.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        signupphoneedpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        signupphonebtnverify.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void verificationFindIDS() {

        txtresend = findViewById(R.id.txtresend);
        edv1 = findViewById(R.id.edv1);
        edv2 = findViewById(R.id.edv2);
        edv3 = findViewById(R.id.edv3);
        edv4 = findViewById(R.id.edv4);
    }

    public void verificationFonts() {
        txtverify.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtresend.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        edv1.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        edv2.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        edv3.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        edv4.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void resetFindIDS() {


        txtreset = findViewById(R.id.txtreset);
        resettxtpassword = findViewById(R.id.resettxtpassword);
        resetedpassword = findViewById(R.id.resetedpassword);
        edconfirmpassword = findViewById(R.id.edconfirmpassword);
        btnreset = findViewById(R.id.btnreset);
    }

    public void resetFonts() {
        txtreset.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        resettxtpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        resetedpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edconfirmpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnreset.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void signinFindIDS() {


        txtlog = findViewById(R.id.txtlog);
        txtin = findViewById(R.id.txtin);
        signintxtforgot = findViewById(R.id.signintxtforgot);
        signintxtnew = findViewById(R.id.signintxtnew);
        signintxtaccount = findViewById(R.id.signintxtaccount);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.signinedpassword);
        btnlogin = findViewById(R.id.btnlogin);
    }

    public void siginFonts() {
        txtlog.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        txtin.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        signintxtnew.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        signintxtforgot.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Light.ttf"));
        signintxtaccount.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Bold.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnlogin.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    //location
    private void getLocation() {
        SamLocationRequestService samLocationRequestService = new SamLocationRequestService(New_OnBoardings_Activity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                latitute = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);

                    String add = "";
                    if (addresses.size() > 0) {
                        countryname = addresses.get(0).getCountryName();


                        //signup with phone
                        if(!countryname.equals("")){
                            int index = names.indexOf(countryname);

                            if (index==-1){

/*
                                Log.e("namescountindex", String.valueOf(names.indexOf("Germany")));
                                Log.e("namescount", String.valueOf(names.get(names.indexOf("Germany"))));
                                Log.e("codescount", String.valueOf(codes.get(names.indexOf("Germany"))));
*/

                                index = names.indexOf("Germany");
                                countryname = "Germany";
                            }

                            code = codes.get(index);
                            cname = countryname;
                            if (code.equals("1") && cname.equals("United States")){

                                select_code.setCountryForNameCode("US");
                                signinselect_code.setCountryForNameCode("US");
                                forgotselect_code.setCountryForNameCode("US");

                            }
                            else if (code.equals("1") && cname.equals("Canada")){

                                select_code.setCountryForNameCode("CA");
                                signinselect_code.setCountryForNameCode("CA");
                                forgotselect_code.setCountryForNameCode("CA");

                            }
                            else if (code.equals("1") && cname.equals("Dominican Republic")){
                                select_code.setCountryForNameCode("DO");
                                signinselect_code.setCountryForNameCode("DO");
                                forgotselect_code.setCountryForNameCode("DO");
                            }
                            else if (code.equals("1") && cname.equals("Puerto Rico")){
                                select_code.setCountryForNameCode("PR");
                                signinselect_code.setCountryForNameCode("PR");
                                forgotselect_code.setCountryForNameCode("PR");
                            }
                            else {
                                select_code.setCountryForPhoneCode(Integer.parseInt(code));
                                signinselect_code.setCountryForPhoneCode(Integer.parseInt(code));
                                forgotselect_code.setCountryForPhoneCode(Integer.parseInt(code));
                            }

                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                            preferences.edit().putString(Constant.CODE, code).commit();
                            preferences.edit().putString(Constant.COUNTRY, cname).commit();

                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }, 10);


    }
    //APIS

    //signin
    public void signin() {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
        final String mail_phone = edemail.getText().toString();
        final String signinphone = signinedphone.getText().toString();
        String device_token = pref.getString(Constant.REG_TOKEN,"");
        String pass = edpassword.getText().toString();

        if (login==1){
            if (mail_phone.isEmpty()) {
                edemail.setError(getResources().getString(R.string.Fieldisrequired));
                edemail.requestFocus();
            }
            else if (pass.isEmpty()) {
                edpassword.setError(getResources().getString(R.string.Fieldisrequired));
                edpassword.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches()){
                edemail.setError(getResources().getString(R.string.InvalidEmail));
                edemail.requestFocus();
            }
            else{
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);
                RequestParams params = new RequestParams();
                params.add("password", pass);
                params.add("device_id", "ewewewq");
                params.add("device_token",device_token);
                params.add("ip_address", cip);
                params.add("device_type", "android");
                params.add("mobile_email", mail_phone);
                AsyncHttpClient client = new AsyncHttpClient();
                Log.e("signinParams", String.valueOf(params));
                client.post(Constant.BASE_URL + "user/user/signIn", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.e("signinresponse", response);
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("true")) {

                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                                String id = dataSet.getString("id");
                                String email = dataSet.getString("email");
                                String mobile = dataSet.getString("mobile");
                                String username = dataSet.getString("username");

                                if(username.equals("") || username.equals("null"))
                                {
                                    Intent intent = new Intent(New_OnBoardings_Activity.this,Profile_Setup_Activity.class);
                                    intent.putExtra("fromemail", "email");
                                    intent.putExtra("signupemail",mail_phone);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(New_OnBoardings_Activity.this, Home_Activity.class);
                                    startActivity(intent);
                                }


                                if (dataSet.get("image") instanceof JSONArray){

                                }
                                else {
                                    String image = dataSet.getString("image");

                                    pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                                }

                                pref.edit().putString(Constant.EMAIL_ID, email).commit();
                                pref.edit().putString(Constant.MOBILE, mobile).commit();
                                pref.edit().putString(Constant.USER_ID, id).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN, true).commit();


                            }
                            else if (status.equals("false")) {
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                                Constant.ErrorToast(New_OnBoardings_Activity.this, errorMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
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
                        else {
                            Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");
                        }
                    }
                });
            }

        }
        else if (login==2){
            if (signinphone.isEmpty()) {
                edpassword.setError(getResources().getString(R.string.Fieldisrequired));
                edpassword.requestFocus();
            }
            else if (signinphone.length()<10) {
                edpassword.setError(getResources().getString(R.string.Atleast10digitsrequired));
                edpassword.requestFocus();
            }
            else if (pass.isEmpty()) {
                edpassword.setError(getResources().getString(R.string.Fieldisrequired));
                edpassword.requestFocus();
            }

            else {
                cont = signincode+ signinphone;
                Log.e("contlog",cont);
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);
                RequestParams params = new RequestParams();
                params.add("password", pass);
                params.add("device_id", "ewewewq");
                params.add("device_token", device_token);
                params.add("ip_address", cip);
                params.add("device_type", "android");
                params.add("mobile_email",cont);
                Log.e("paramsignlog", String.valueOf(params));
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Constant.BASE_URL + "user/user/signIn", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.e("signinresponse", response);
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");


                            if (status.equals("true")) {
                                // Constant.SuccessToast(Signin_Activity.this,jsonObject.getString("Successfully Logged In"));
                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                                String id = dataSet.getString("id");
                                String email = dataSet.getString("email");
                                String mobile = dataSet.getString("mobile");
                                String username = dataSet.getString("username");

                                if(username.equals("") || username.equals("null"))
                                {
                                    Intent intent = new Intent(New_OnBoardings_Activity.this,Profile_Setup_Activity.class);
                                    intent.putExtra("fromphone", "phone");
                                    intent.putExtra("fromphonecode", signincode);
                                    intent.putExtra("signupphone",signinphone);
                                    startActivity(intent);
                                }
                                else
                                {


                                    Intent intent = new Intent(New_OnBoardings_Activity.this, Home_Activity.class);
                                    startActivity(intent);

                                }
                                if (dataSet.get("image") instanceof JSONArray){

                                }
                                else {
                                    String image = dataSet.getString("image");

                                    pref.edit().putString(Constant.PROFILE_PICTURE, image).commit();
                                }

                                pref.edit().putString(Constant.EMAIL_ID, email).commit();
                                pref.edit().putString(Constant.MOBILE, mobile).commit();
                                pref.edit().putString(Constant.USER_ID, id).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN, true).commit();

                            } else if (status.equals("false")) {
                                Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

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
                        else {
                            Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                        }
                    }
                });
            }
        }
    }

    //forgot
    public void forgot_Password() {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);

        mail_phone = forgotedemail.getText().toString();
        String forgotnum = forgotedphone.getText().toString();

        if (forget==1){
            if (mail_phone.isEmpty()) {
                forgotedemail.setError(getResources().getString(R.string.Fieldisrequired));
                forgotedemail.requestFocus();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(forgotedemail.getText().toString()).matches()){
                forgotedemail.setError(getResources().getString(R.string.InvalidEmail));
                forgotedemail.requestFocus();
            }
            else {
                RequestParams params = new RequestParams();
                params.add("mobile_email", mail_phone);
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Constant.BASE_URL + "user/user/forgetPassword", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String response = new String(responseBody);
                        Log.e("forgotresponse", response);

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("true")) {
                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                                String verification_code = dataSet.getString("verification_code");
                                email = dataSet.getString("email");
                                password_change_codes = dataSet.getString("password_change_code");
                                mobiles = dataSet.getString("mobile");

                                forg = 1;

                                forgotRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        forgotRelative.setVisibility(View.GONE);
                                        verificationRelative.setVisibility(View.VISIBLE);
                                        verificationRelative.animate().alpha(1f).setDuration(500).setListener(null);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                edv1.setText(Character.toString(password_change_codes.charAt(0)));
                                                edv2.setText(Character.toString(password_change_codes.charAt(1)));
                                                edv3.setText(Character.toString(password_change_codes.charAt(2)));
                                                edv4.setText(Character.toString(password_change_codes.charAt(3)));


                                            }
                                        }, 1000);
                                    }
                                });


                            } else if (status.equals("false")) {
                                Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        if (responseBody!=null){
                            String response = new String(responseBody);
                            Log.e("forgotresponseF", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String errorMessage = jsonObject.getString("errorMessage");
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                        }
                    }
                });

            }

        }
        else if (forget==2){
            if (forgotnum.isEmpty()) {
                forgotedphone.setError(getResources().getString(R.string.Fieldisrequired));
                forgotedphone.requestFocus();
            }
            else if (forgotnum.length()<10){
                forgotedphone.setError(getResources().getString(R.string.Atleast10digitsrequired));
                forgotedphone.requestFocus();            }
            else {
                RequestParams params = new RequestParams();
                params.add("mobile_email", "+" + forgetcode+ forgotnum);

                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Constant.BASE_URL + "user/user/forgetPassword", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String response = new String(responseBody);
                        Log.e("forgotresponse", response);

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("true")) {
                                JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                                user_id = dataSet.getString("id");
                                String verification_code = dataSet.getString("verification_code");
                                email = dataSet.getString("email");
                                password_change_codes = dataSet.getString("password_change_code");
                                mobiles = dataSet.getString("mobile");


                                forg = 1;

                                forgotRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        forgotRelative.setVisibility(View.GONE);
                                        verificationRelative.setVisibility(View.VISIBLE);
                                        verificationRelative.animate().alpha(1f).setDuration(500).setListener(null);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                edv1.setText(Character.toString(password_change_codes.charAt(0)));
                                                edv2.setText(Character.toString(password_change_codes.charAt(1)));
                                                edv3.setText(Character.toString(password_change_codes.charAt(2)));
                                                edv4.setText(Character.toString(password_change_codes.charAt(3)));


                                            }
                                        }, 1000);

                                    }
                                });


                            } else if (status.equals("false")) {
                                Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        if (responseBody!=null){
                            String response = new String(responseBody);
                            Log.e("forgotresponseF", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String errorMessage = jsonObject.getString("errorMessage");
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");
                        }


                    }
                });


            }
        }

    }

    //verification
    public void forgetpassword_verification() {
        String et1 = edv1.getText().toString();
        String et2 = edv2.getText().toString();
        String et3 = edv3.getText().toString();
        String et4 = edv4.getText().toString();

        String verification_code = et1 + et2 + et3 + et4;

        if (et1.isEmpty()) {
            edv1.setError(getResources().getString(R.string.Required));
            edv1.requestFocus();
        } else if (et2.isEmpty()) {
            edv2.setError(getResources().getString(R.string.Required));
            edv2.requestFocus();

        } else if (et3.isEmpty()) {
            edv3.setError(getResources().getString(R.string.Required));
            edv3.requestFocus();

        } else if (et4.isEmpty()) {
            edv4.setError(getResources().getString(R.string.Required));
            edv4.requestFocus();

        } else if (!verification_code.equals(password_change_codes)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.WrongVerificationCode), Toast.LENGTH_SHORT).show();
        } else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();

            if (!email.isEmpty()) {
                params.add("mobile_email", email);
                params.add("code", password_change_codes);
            } else {
                params.add("mobile_email", mobiles);
                params.add("code", password_change_codes);
            }

          /*  params.add("mobile_email",email2);
            params.add("code",password_change_code);*/

            Log.e("log_params", String.valueOf(params));


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/validate_change_password_code", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsecode", response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true")) {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            user_id = dataSet.getString("id");

                            verificationRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    verificationRelative.setVisibility(View.GONE);
                                    resetRelative.setVisibility(View.VISIBLE);
                                    resetRelative.animate().alpha(1f).setDuration(500).setListener(null);
                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("responsecodeF", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");
                            if (status.equals("false")) {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }



                }
            });
        }
    }

    public void verifciation() {

        String et1 = edv1.getText().toString();
        String et2 = edv2.getText().toString();
        String et3 = edv3.getText().toString();
        String et4 = edv4.getText().toString();

        String verification_codeee = et1 + et2 + et3 + et4;

        if (et1.isEmpty()) {
            edv1.setError(getResources().getString(R.string.Required));
            edv1.requestFocus();
        } else if (et2.isEmpty()) {
            edv2.setError(getResources().getString(R.string.Required));
            edv2.requestFocus();

        } else if (et3.isEmpty()) {
            edv3.setError(getResources().getString(R.string.Required));
            edv3.requestFocus();

        } else if (et4.isEmpty()) {
            edv4.setError(getResources().getString(R.string.Required));
            edv4.requestFocus();
            Log.e("signupemailcode",signupemail_smscode);

        }
        else if (!verification_codeee.equals(signupemail_smscode) && !verification_codeee.equals(signup_phone_smscode)){
         dismissKeyboard(New_OnBoardings_Activity.this);
         Constant.ErrorToast(New_OnBoardings_Activity.this,getResources().getString(R.string.InvalidCode));
        }
        else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            if (signupM == 1) {
                params.add("mobile_email", signup_email);
                params.add("code", signupemail_smscode);
            } else if (signupM==2){
                params.add("mobile_email", signup_phone_number);
                params.add("code", signup_phone_smscode);

            }


            Log.e("params", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/verification", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                    String response = new String(responseBody);
                    Log.e("verificationresponse", response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");
                            if (status.equals("true"))
                            {
                                if (signupM==1)
                                {
                                    Intent intent = new Intent(New_OnBoardings_Activity.this, Profile_Setup_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("fromemail", "email");
                                    intent.putExtra("signupemail",signup_email);

                                    if(code.equals("") || cname.equals("")){
                                        intent.putExtra("fromemailcode", select_code.getDefaultCountryCode());
                                        intent.putExtra("cname",select_code.getDefaultCountryName());
                                    }
                                    else {
                                        intent.putExtra("fromemailcode", code);
                                        intent.putExtra("cname",cname);

                                    }

                                    startActivity(intent);
                                    signup_email="";
                                }
                                else if (signupM==2)
                                {
                                    Intent intent = new Intent(New_OnBoardings_Activity.this, Profile_Setup_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("fromphone", "phone");
                                    intent.putExtra("signupphone",phone);


                                    if(code.equals("") || cname.equals("")){
                                        intent.putExtra("fromphonecode", select_code.getDefaultCountryCode());
                                        intent.putExtra("cname",select_code.getDefaultCountryName());
                                    }
                                    else {
                                        intent.putExtra("fromphonecode", code);
                                        intent.putExtra("cname",cname);

                                    }


                                    startActivity(intent);
                                    phone="";
                                }


                            } else if (status.equals("false")) {
                                Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    if (responseBody != null) {
                        String response = new String(responseBody);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Log.e("verificationreposnseF", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("false")) {
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else {
                        Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                    }

                }
            });


        }


    }

    public void reset_password() {
        String v1 = resetedpassword.getText().toString();
        String v2 = edconfirmpassword.getText().toString();

        if (TextUtils.isEmpty(resetedpassword.getText())) {
            resetedpassword.setError(getResources().getString(R.string.Fieldisrequired));
            resetedpassword.requestFocus();
        } else if (TextUtils.isEmpty(edconfirmpassword.getText())) {
            edconfirmpassword.setError(getResources().getString(R.string.Fieldisrequired));
            edconfirmpassword.requestFocus();
        } else if (!v2.equals(v1)) {
            edconfirmpassword.setError(getResources().getString(R.string.Passwordnotmatched));
            edconfirmpassword.requestFocus();

        } else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("password", v2);
            params.add("user_id", user_id);
            Log.e("paramsreset", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/new_password", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsereset", response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true")) {

                            resetRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    resetRelative.setVisibility(View.GONE);
                                    signinRelative.setVisibility(View.VISIBLE);
                                    signinRelative.animate().alpha(1f).setDuration(500).setListener(null);
                                }
                            });
                        } else if (status.equals("false")) {
                            Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                    if (responseBody != null) {
                        String response = new String(responseBody);
                       Log.e("responseresetF", response);

                    }
                }
            });


        }
    }

    public void Signup_with_email() {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
        String device_token = pref.getString(Constant.REG_TOKEN,"");

        final String mail = signupemailedemail.getText().toString();
        String pass = signupemailedpassword.getText().toString();
        if (mail.isEmpty()) {
            signupemailedemail.setError(getResources().getString(R.string.Fieldisrequired));
            signupemailedemail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(signupemailedemail.getText().toString()).matches()) {
            signupemailedemail.setError(getResources().getString(R.string.InvalidEmail));
            signupemailedemail.requestFocus();
        } else if (pass.isEmpty()) {
            signupemailedpassword.setError(getResources().getString(R.string.Fieldisrequired));
            signupemailedpassword.requestFocus();
        } else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("register_ip", cip);
            params.add("latitude", latitute);
            params.add("longitude", longitude);
            params.add("device_type", "android");
            params.add("device_id", "ewewewq");
            params.add("device_token",device_token);
            params.add("email", mail);
            params.add("signup_flag", "2");
            params.add("password", pass);

//            Log.e("paramslat",latitute);

            Log.e("signupwithemailparams", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/register_member", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("signupresponse", response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true")) {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            user_id = dataSet.getString("user_id");

                            signupemail_smscode = dataSet.getString("sms_code");
                            pref.edit().putString(Constant.USER_ID, user_id).commit();
                            pref.edit().putString(Constant.EMAIL_ID, signup_email).commit();


                            signup_email = mail;
                            signupM = 1;

                            signupemailRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    signupemailRelative.setVisibility(View.GONE);
                                    verificationRelative.setVisibility(View.VISIBLE);
                                    verificationRelative.animate().alpha(1f).setDuration(500).setListener(null);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            /*edv1.setText(Character.toString(signupemail_smscode.charAt(0)));
                                            edv2.setText(Character.toString(signupemail_smscode.charAt(1)));
                                            edv3.setText(Character.toString(signupemail_smscode.charAt(2)));
                                            edv4.setText(Character.toString(signupemail_smscode.charAt(3)));
*/

                                        }
                                    }, 1000);

                                }
                            });

                        } else if (status.equals("false")) {
                            Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody != null) {

                            String response = new String(responseBody);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            Log.e("signupresponseF", response);

                    }
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                }
            });
        }
    }

    public void signup_with_phonw() {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
        String device_token = pref.getString(Constant.REG_TOKEN,"");

        phone = edphone.getText().toString();

        signup_phone_number = "+" + code + phone;

        String pass = signupphoneedpassword.getText().toString();
        if (phone.isEmpty()) {
            edphone.setError(getResources().getString(R.string.Fieldisrequired));
            edphone.requestFocus();
        } else if (phone.length() < 10) {
            edphone.setError(getResources().getString(R.string.Atleast10digitsrequired));
            edphone.requestFocus();
        } else if (pass.isEmpty()) {
            signupphoneedpassword.setError(getResources().getString(R.string.Fieldisrequired));
            signupphoneedpassword.requestFocus();
        } else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("register_ip", cip);
            params.add("latitude", latitute);
            params.add("longitude", longitude);
            params.add("device_type", "android");
            params.add("device_id", "ewewewq");
            params.add("device_token",device_token);
            params.add("mobile", signup_phone_number);
            params.add("signup_flag", "1");
            params.add("password", pass);

            Log.e("l_params", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/register_member", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("signupresponse", response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true")) {
                            // Constant.SuccessToast(Signup_With_Phone_Activity.this,errorMessage);
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            user_id = dataSet.getString("user_id");
                            signup_phone_smscode = dataSet.getString("sms_code");
                            Log.e("sms_code", signup_phone_smscode);
                            pref.edit().putString(Constant.USER_ID, user_id).commit();
                            pref.edit().putString(Constant.CODE, code).commit();
                            pref.edit().putString(Constant.PHONE, phone).commit();
                            pref.edit().putString(Constant.COUNTRY, cname).commit();
                            pref.edit().remove(Constant.EMAIL_ID).commit();

                            signupM = 2;

                            signupphoneRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    signupphoneRelative.setVisibility(View.GONE);
                                    verificationRelative.setVisibility(View.VISIBLE);
                                    verificationRelative.animate().alpha(1f).setDuration(500).setListener(null);

/*                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            edv1.setText(Character.toString(signup_phone_smscode.charAt(0)));
                                            edv2.setText(Character.toString(signup_phone_smscode.charAt(1)));
                                            edv3.setText(Character.toString(signup_phone_smscode.charAt(2)));
                                            edv4.setText(Character.toString(signup_phone_smscode.charAt(3)));


                                        }
                                    }, 1000);*/

                                }
                            });
                        } else if (status.equals("false")) {
                            Constant.ErrorToast(New_OnBoardings_Activity.this, jsonObject.getString("errorMessage"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                    if (responseBody != null) {
                        String response = new String(responseBody);
                        Log.e("signupresponseF", response);

                    }

                }
            });
        }
    }

    //forget resend code
    public void resend_forgot() {
        String et1 = edv1.getText().toString();
        String et2 = edv2.getText().toString();
        String et3 = edv3.getText().toString();
        String et4 = edv4.getText().toString();

        String verification_code = et1 + et2 + et3 + et4;

        if (et1.isEmpty()) {
            edv1.setError(getResources().getString(R.string.Required));
            edv1.requestFocus();
        } else if (et2.isEmpty()) {
            edv2.setError(getResources().getString(R.string.Required));
            edv2.requestFocus();

        } else if (et3.isEmpty()) {
            edv3.setError(getResources().getString(R.string.Required));
            edv3.requestFocus();

        } else if (et4.isEmpty()) {
            edv4.setError(getResources().getString(R.string.Required));
            edv4.requestFocus();

        } else if (!verification_code.equals(password_change_codes)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.WrongVerificationCode), Toast.LENGTH_SHORT).show();
        } else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();

            if (!email.isEmpty()) {
                params.add("mobile_email", email);
                params.add("flag", "2");
            } else {
                params.add("mobile_email", mobiles);
                params.add("flag", "2");
            }
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "code_resend", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("response_fresend", response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true")) {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                            user_id = dataSet.getString("id");

                            password_change_codes = dataSet.getString("password_change_code");


                            verificationRelative.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    verificationRelative.setVisibility(View.GONE);
                                    resetRelative.setVisibility(View.VISIBLE);
                                    resetRelative.animate().alpha(1f).setDuration(500).setListener(null);
                                }
                            });


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    String response = new String(responseBody);
                    Log.e("response_fresendF", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("false")) {


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });


        }


    }

    public void resend_signup() {
        String et1 = edv1.getText().toString();
        String et2 = edv2.getText().toString();
        String et3 = edv3.getText().toString();
        String et4 = edv4.getText().toString();

        String verification_code = et1 + et2 + et3 + et4;

        if (et1.isEmpty()) {
            edv1.setError(getResources().getString(R.string.Required));
            edv1.requestFocus();
        } else if (et2.isEmpty()) {
            edv2.setError(getResources().getString(R.string.Required));
            edv2.requestFocus();

        } else if (et3.isEmpty()) {
            edv3.setError(getResources().getString(R.string.Required));
            edv3.requestFocus();

        } else if (et4.isEmpty()) {
            edv4.setError(getResources().getString(R.string.Required));
            edv4.requestFocus();

        } else if (!verification_code.equals(signup_phone_smscode) || !verification_code.equals(signupemail_smscode)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.WrongVerificationCode), Toast.LENGTH_SHORT).show();
        } else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();

            if (!signup_phone_number.equals("")) {
                params.add("mobile_email", signup_phone_number);
                params.add("flag", "1");
            } else if (!signup_email.equals("")) {
                params.add("mobile_email", signup_email);
                params.add("flag", "1");

            }
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "code_resend", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("response_Sresend", response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true")) {

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                             sms_code = dataSet.getString("verification_code");


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    String response = new String(responseBody);
                    Log.e("response_SresendF", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("false")) {


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e("result", String.valueOf(result.getStatus()));
        }
        else if (requestCode==2000){
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
            }
        }
        else {
            callbackmanager.onActivityResult(requestCode,resultCode,data);

        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("checkgmail","yes");
        if(result.isSuccess()){
            Log.e("checkgmail","yes2");
            GoogleSignInAccount account = result.getSignInAccount();

            g_name=account.getDisplayName();
            mail = account.getEmail();
            user_image = String.valueOf(account.getPhotoUrl());
            personId = account.getId();
//            Log.e("name",name);

            Log.e("logmailgoogle",mail);


            if (Constant.isOnline(New_OnBoardings_Activity .this)){

                signin(g_name,mail,personId,"2",user_image);

            }
            else{
                Constant.ErrorToast(New_OnBoardings_Activity.this,"No Internet Connection");
            }


        }

        else{
            Log.e("checkgmail","yes5");
            Log.e("error", String.valueOf(result.getStatus()));
            Toast.makeText(getApplicationContext(),"Account not found", Toast.LENGTH_SHORT).show();
        }
    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {


            if (currentAccessToken==null)
            {
            }
            else {

                loaduserprofile(currentAccessToken);
            }
        }
    };

    private void loaduserprofile (AccessToken newAccesstoken)
    {


        final GraphRequest request = GraphRequest.newMeRequest(newAccesstoken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    //String res = new String(String.valueOf(response));
                    //Log.e("resfbb",res);
                    String fb_first =object.getString("first_name");
                    String  fb_last = object.getString("last_name");
                    String  fb_id = object.getString("id");
                    String fb_name = fb_first+" "+fb_last;

                    String fb_image="";
                    if(Profile.getCurrentProfile()!=null){
                        Uri profilePictureUri= Profile.getCurrentProfile().getProfilePictureUri(200 , 200);
                        fb_image = String.valueOf(profilePictureUri);

                    }

                    //Log.e("fb_first",fb_first);
                    //Log.e("fb_last",fb_last);
                    //Log.e("fb_id",fb_id);

                    if (!object.has("email")){
                        String Email = object.getString("id");
                        fbUserEmail= Email+""+"@facebook.com";
                        //Log.e("fb_email",fbUserEmail);

                        //   3135416846468468@facebook.com
                    }
                    else{
                        fbUserEmail = object.getString("email");
                        //Log.e("fb_email",fbUserEmail);
                    }


                    if (Constant.isOnline(New_OnBoardings_Activity .this)){

                        signin(fb_name,fbUserEmail,fb_id,"1",fb_image);

                    }
                    else{
                        Constant.ErrorToast(New_OnBoardings_Activity.this,"No Internet Connection");
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void signin(final String name, final String mail, final String personId,final String platform,final String user_image)
    {

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String device_token = pref.getString(Constant.REG_TOKEN,"");

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        params.add("register_ip",cip);
        params.add("device_id","ewewewq");
        params.add("device_token",device_token);
        //params.add("device_token","dfasda");
        params.add("device_type","android");
        params.add("latitude",latitute);
        params.add("longitude",longitude);
        params.add("social_media_id",personId);
        params.add("social_platform",platform);
        params.add("email",mail);
        Log.e("s_params", String.valueOf(params));


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/login_social", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                String response = new String(responseBody);
                Log.e("signinresponse",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (errorMessage.equals("Social media account does not exsits") && platform.equals("2"))
                    {
                        Intent intent = new Intent(New_OnBoardings_Activity.this, Profile_Setup_Activity.class);
                        intent.putExtra("social_id",personId);
                        intent.putExtra("name",name);
                        intent.putExtra("mail",mail);
                        intent.putExtra("user_image",user_image);
                        intent.putExtra("social_platform",platform);

                        if(code.equals("") || cname.equals("")){
                            intent.putExtra("fromsocialcode", select_code.getDefaultCountryCode());
                            intent.putExtra("cname",select_code.getDefaultCountryName());
                        }
                        else {
                            intent.putExtra("fromsocialcode", code);
                            intent.putExtra("cname",cname);

                        }

                        startActivity(intent);

                        Auth.GoogleSignInApi.signOut(googleApiClient);
                        googleApiClient.disconnect();
                        googleApiClient.connect();

                    }
                    else if (errorMessage.equals("Social media account does not exsits") && platform.equals("1"))
                    {
                        Intent intent = new Intent(New_OnBoardings_Activity.this, Profile_Setup_Activity.class);
                        intent.putExtra("social_id",personId);
                        intent.putExtra("name",name);
                        intent.putExtra("mail",mail);
                        intent.putExtra("user_image",user_image);
                        intent.putExtra("social_platform",platform);
                        if(code.equals("") || cname.equals("")){
                            intent.putExtra("fromsocialcode", select_code.getDefaultCountryCode());
                            intent.putExtra("cname",select_code.getDefaultCountryName());
                        }
                        else {
                            intent.putExtra("fromsocialcode", code);
                            intent.putExtra("cname",cname);

                        }
                        startActivity(intent);
                        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                        pref.edit().putString(Constant.EMAIL_ID,mail).commit();
                        pref.edit().putString(Constant.USER_ID,id).commit();
                        pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                        LoginManager.getInstance().logOut();

                    }
                    else if (errorMessage.equals(""))
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

                        Intent intent = new Intent(New_OnBoardings_Activity.this,Home_Activity.class);
                        startActivity(intent);

                        if (platform.equals("1"))
                        {
                            LoginManager.getInstance().logOut();
                        }
                        else if (platform.equals("2"))
                        {
                            Auth.GoogleSignInApi.signOut(googleApiClient);
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                        }

                    }

                    else if (status.equals("false"))
                    {
                        Constant.ErrorToast(New_OnBoardings_Activity.this,jsonObject.getString("errorMessage"));

                        if (platform.equals("1"))
                        {
                            LoginManager.getInstance().logOut();
                        }
                        else if (platform.equals("2"))
                        {
                            Auth.GoogleSignInApi.signOut(googleApiClient);
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                        }

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
                else {
                    Constant.ErrorToast(New_OnBoardings_Activity.this,"Something went wrong");

                }
            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    private void appendlists(){
        names.add("Afghanistan");
        names.add("Albania");
        names.add("Algeria");
        names.add("American Samoa");
        names.add("Andorra");
        names.add("Angola");
        names.add("Anguilla");
        names.add("Antarctica");
        names.add("Antigua");
        names.add("Argentina");
        names.add("Armenia");
        names.add("Aruba");
        names.add("Ascension");
        names.add("Australia");
        names.add("Australian External Territories");
        names.add("Austria");
        names.add("Azerbaijan");
        names.add("Bahamas");
        names.add("Bahrain");
        names.add("Bangladesh");
        names.add("Barbados");
        names.add("Barbuda");
        names.add("Belarus");
        names.add("Belgium");
        names.add("Belize");
        names.add("Benin");
        names.add("Bermuda");
        names.add("Bhutan");
        names.add("Bolivia");
        names.add("Bosnia & Herzegovina");
        names.add("Botswana");
        names.add("Brazil");
        names.add("British Virgin Islands");
        names.add("Brunei Darussalam");
        names.add("Bulgaria");
        names.add("Burkina Faso");
        names.add("Burundi");
        names.add("Cambodia");
        names.add("Cameroon");
        names.add("Canada");
        names.add("Cape Verde Islands");
        names.add("Cayman Islands");
        names.add("Central African Republic");
        names.add("Chad");
        names.add("Chatham Island (New Zealand)");
        names.add("Chile");
        names.add("China");
        names.add("Christmas Island");
        names.add("Cocos-Keeling Islands");
        names.add("Colombia");
        names.add("Comoros");
        names.add("Congo");
        names.add("Congo, Dem. Rep. of  (former Zaire)");
        names.add("Cook Islands");
        names.add("Costa Rica");
        names.add("Côte d'Ivoire (Ivory Coast)");
        names.add("Croatia");
        names.add("Cuba");
        names.add("Cuba (Guantanamo Bay)");
        names.add("Curaçao");
        names.add("Cyprus");
        names.add("Czech Republic");
        names.add("Denmark");
        names.add("Diego Garcia");
        names.add("Djibouti");
        names.add("Dominica");
        names.add("Dominican Republic");
        names.add("East Timor");
        names.add("Easter Island");
        names.add("Ecuador");
        names.add("Egypt");
        names.add("El Salvador");
        names.add("Equatorial Guinea");
        names.add("Eritrea");
        names.add("Estonia");
        names.add("Ethiopia");
        names.add("Falkland Islands (Malvinas)");
        names.add("Faroe Islands");
        names.add("Fiji Islands");
        names.add("Finland");
        names.add("France");
        names.add("French Antilles");
        names.add("French Guiana");
        names.add("French Polynesia");
        names.add("Gabonese Republic");
        names.add("Gambia");
        names.add("Georgia");
        names.add("Germany");
        names.add("Ghana");
        names.add("Gibraltar");
        names.add("Greece");
        names.add("Greenland");
        names.add("Grenada");
        names.add("Guadeloupe");
        names.add("Guam");
        names.add("Guantanamo Bay");
        names.add("Guatemala");
        names.add("Guinea-Bissau");
        names.add("Guinea");
        names.add("Guyana");
        names.add("Haiti");
        names.add("Honduras");
        names.add("Hong Kong");
        names.add("Hungary");
        names.add("Iceland");
        names.add("India");
        names.add("Indonesia");
        names.add("Iran");
        names.add("Iraq");
        names.add("Ireland");
        names.add("Israel");
        names.add("Italy");
        names.add("Jamaica");
        names.add("Japan");
        names.add("Jordan");
        names.add("Kazakhstan");
        names.add("Kenya");
        names.add("Kiribati");
        names.add("Korea (North)");
        names.add("Korea (South)");
        names.add("Kuwait");
        names.add("Kyrgyz Republic");
        names.add("Laos");
        names.add("Latvia");
        names.add("Lebanon");
        names.add("Lesotho");
        names.add("Liberia");
        names.add("Libya");
        names.add("Liechtenstein");
        names.add("Lithuania");
        names.add("Luxembourg");
        names.add("Macao");
        names.add("Macedonia (Former Yugoslav Rep of.)");
        names.add("Madagascar");
        names.add("Malawi");
        names.add("Malaysia");
        names.add("Maldives");
        names.add("Mali Republic");
        names.add("Malta");
        names.add("Marshall Islands");
        names.add("Martinique");
        names.add("Mauritania");
        names.add("Mauritius");
        names.add("Mayotte Island");
        names.add("Mexico");
        names.add("Micronesia, (Federal States of)");
        names.add("Midway Island");
        names.add("Moldova");
        names.add("Monaco");
        names.add("Mongolia");
        names.add("Montenegro");
        names.add("Montserrat");
        names.add("Morocco");
        names.add("Mozambique");
        names.add("Myanmar");
        names.add("Namibia");
        names.add("Nauru");
        names.add("Nepal");
        names.add("Netherlands");
        names.add("Netherlands Antilles");
        names.add("Nevis");
        names.add("New Caledonia");
        names.add("New Zealand");
        names.add("Nicaragua");
        names.add("Niger");
        names.add("Nigeria");
        names.add("Niue");
        names.add("Norfolk Island");
        names.add("Northern Marianas Islands (Saipan, Rota, & Tinian)");
        names.add("Oman");
        names.add("Pakistan");
        names.add("Palau");
        names.add("Palestinian Settlements");
        names.add("Panama");
        names.add("Papua New Guinea");
        names.add("Paraguay");
        names.add("Peru");
        names.add("Philippines");
        names.add("Poland");
        names.add("Portugal");
        names.add("Puerto Rico");
        names.add("Qatar");
        names.add("Réunion Island");
        names.add("Romania");
        names.add("Russia");
        names.add("Rwandese Republic");
        names.add("St. Helena");
        names.add("St. Kitts/Nevis");
        names.add("St. Lucia");
        names.add("St. Pierre & Miquelon");
        names.add("St. Vincent & Grenadines");
        names.add("Samoa");
        names.add("San Marino");
        names.add("São Tomé and Principe");
        names.add("Saudi Arabia");
        names.add("Senegal");
        names.add("Serbia");
        names.add("Seychelles Republic");
        names.add("Sierra Leone");
        names.add("Singapore");
        names.add("Slovak Republic");
        names.add("Slovenia");
        names.add("Solomon Islands");
        names.add("Somali Democratic Republic");
        names.add("South Africa");
        names.add("Spain");
        names.add("Sri Lanka");
        names.add("Sudan");
        names.add("Suriname");
        names.add("Swaziland");
        names.add("Sweden");
        names.add("Switzerland");
        names.add("Syria");
        names.add("Taiwan");
        names.add("Tajikistan");
        names.add("Tanzania");
        names.add("Thailand");
        names.add("Timor Leste");
        names.add("Togolese Republic");
        names.add("Tokelau");
        names.add("Tonga Islands");
        names.add("Trinidad & Tobago");
        names.add("Tunisia");
        names.add("Turkey");
        names.add("Turkmenistan");
        names.add("Turks and Caicos Islands");
        names.add("Tuvalu");
        names.add("Uganda");
        names.add("Ukraine");
        names.add("United Arab Emirates");
        names.add("United Kingdom");
        names.add("United States");
        names.add("US Virgin Islands");
        names.add("Uruguay");
        names.add("Uzbekistan");
        names.add("Vanuatu");
        names.add("Venezuela");
        names.add("Vietnam");
        names.add("Wake Island");
        names.add("Wallis and Futuna Islands");
        names.add("Yemen");
        names.add("Zambia");
        names.add("Zanzibar");
        names.add("Zimbabwe");




        codes.add("93");
        codes.add("355");
        codes.add("213");
        codes.add("1684");
        codes.add("376");
        codes.add("244");
        codes.add("1264");
        codes.add("672");
        codes.add("1268");
        codes.add("54");
        codes.add("374");
        codes.add("297");
        codes.add("247");
        codes.add("61");
        codes.add("672");
        codes.add("43");
        codes.add("994");
        codes.add("1242");
        codes.add("973");
        codes.add("880");
        codes.add("1246");
        codes.add("1268");
        codes.add("375");
        codes.add("32");
        codes.add("501");
        codes.add("229");
        codes.add("1441");
        codes.add("975");
        codes.add("591");
        codes.add("387");
        codes.add("267");
        codes.add("55");
        codes.add("1284");
        codes.add("673");
        codes.add("359");
        codes.add("226");
        codes.add("257");
        codes.add("855");
        codes.add("237");
        codes.add("1");
        codes.add("238");
        codes.add("1345");
        codes.add("236");
        codes.add("235");
        codes.add("64");
        codes.add("56");
        codes.add("86");
        codes.add("61");
        codes.add("61");
        codes.add("57");
        codes.add("269");
        codes.add("242");
        codes.add("243");
        codes.add("682");
        codes.add("506");
        codes.add("225");
        codes.add("385");
        codes.add("53");
        codes.add("5399");
        codes.add("5999");
        codes.add("357");
        codes.add("420");
        codes.add("45");
        codes.add("246");
        codes.add("253");
        codes.add("1767");
        codes.add("1809");
        codes.add("670");
        codes.add("56");
        codes.add("593");
        codes.add("20");
        codes.add("503");
        codes.add("240");
        codes.add("291");
        codes.add("372");
        codes.add("251");
        codes.add("500");
        codes.add("298");
        codes.add("679");
        codes.add("358");
        codes.add("33");
        codes.add("596");
        codes.add("594");
        codes.add("689");
        codes.add("241");
        codes.add("220");
        codes.add("995");
        codes.add("49");
        codes.add("233");
        codes.add("350");
        codes.add("30");
        codes.add("299");
        codes.add("1473");
        codes.add("590");
        codes.add("1671");
        codes.add("5399");
        codes.add("502");
        codes.add("245");
        codes.add("224");
        codes.add("592");
        codes.add("509");
        codes.add("504");
        codes.add("852");
        codes.add("36");
        codes.add("354");
        codes.add("91");
        codes.add("62");
        codes.add("98");
        codes.add("964");
        codes.add("353");
        codes.add("972");
        codes.add("39");
        codes.add("1876");
        codes.add("81");
        codes.add("962");
        codes.add("7");
        codes.add("254");
        codes.add("686");
        codes.add("850");
        codes.add("82");
        codes.add("965");
        codes.add("996");
        codes.add("856");
        codes.add("371");
        codes.add("961");
        codes.add("266");
        codes.add("231");
        codes.add("218");
        codes.add("423");
        codes.add("370");
        codes.add("352");
        codes.add("853");
        codes.add("389");
        codes.add("261");
        codes.add("265");
        codes.add("60");
        codes.add("960");
        codes.add("223");
        codes.add("356");
        codes.add("692");
        codes.add("596");
        codes.add("222");
        codes.add("230");
        codes.add("269");
        codes.add("52");
        codes.add("691");
        codes.add("1808");
        codes.add("373");
        codes.add("377");
        codes.add("976");
        codes.add("382");
        codes.add("1664");
        codes.add("212");
        codes.add("258");
        codes.add("95");
        codes.add("264");
        codes.add("674");
        codes.add("977");
        codes.add("31");
        codes.add("599");
        codes.add("1869");
        codes.add("687");
        codes.add("64");
        codes.add("505");
        codes.add("227");
        codes.add("234");
        codes.add("683");
        codes.add("672");
        codes.add("1670");
        codes.add("968");
        codes.add("92");
        codes.add("680");
        codes.add("970");
        codes.add("507");
        codes.add("675");
        codes.add("595");
        codes.add("51");
        codes.add("63");
        codes.add("48");
        codes.add("351");
        codes.add("1787");
        codes.add("974");
        codes.add("262");
        codes.add("40");
        codes.add("7");
        codes.add("250");
        codes.add("290");
        codes.add("1869");
        codes.add("1758");
        codes.add("508");
        codes.add("1784");
        codes.add("685");
        codes.add("378");
        codes.add("239");
        codes.add("966");
        codes.add("221");
        codes.add("381");
        codes.add("248");
        codes.add("232");
        codes.add("65");
        codes.add("421");
        codes.add("386");
        codes.add("677");
        codes.add("252");
        codes.add("27");
        codes.add("34");
        codes.add("94");
        codes.add("249");
        codes.add("597");
        codes.add("268");
        codes.add("46");
        codes.add("41");
        codes.add("963");
        codes.add("886");
        codes.add("992");
        codes.add("255");
        codes.add("66");
        codes.add("670");
        codes.add("228");
        codes.add("690");
        codes.add("676");
        codes.add("1868");
        codes.add("216");
        codes.add("90");
        codes.add("993");
        codes.add("1649");
        codes.add("688");
        codes.add("256");
        codes.add("380");
        codes.add("971");
        codes.add("44");
        codes.add("1");
        codes.add("1340");
        codes.add("598");
        codes.add("998");
        codes.add("678");
        codes.add("58");
        codes.add("84");
        codes.add("808");
        codes.add("681");
        codes.add("967");
        codes.add("260");
        codes.add("255");
        codes.add("263");

    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void getOtpFromMessage(String message) {


        String[] separated = message.split(":");
        Log.e("messagescode",separated[1].trim());
        if (signup_phone_smscode.equals(separated[1].trim())){

            if (verificationRelative.getVisibility()==View.VISIBLE){

                Log.e("textmessages",message);


                edv1.setText(Character.toString(signup_phone_smscode.charAt(0)));
                edv2.setText(Character.toString(signup_phone_smscode.charAt(1)));
                edv3.setText(Character.toString(signup_phone_smscode.charAt(2)));
                edv4.setText(Character.toString(signup_phone_smscode.charAt(3)));


            }

        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, 2000);
                    }
                    @Override
                    public void onFailure() {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    public static class SmsBroadcastReceiver extends BroadcastReceiver{
        SmsBroadcastReceiverListener smsBroadcastReceiverListener;
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == SmsRetriever.SMS_RETRIEVED_ACTION) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        smsBroadcastReceiverListener.onSuccess(messageIntent);
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        smsBroadcastReceiverListener.onFailure();
                        break;
                }
            }
        }
        public interface SmsBroadcastReceiverListener {
            void onSuccess(Intent intent);
            void onFailure();
        }
    }
}



