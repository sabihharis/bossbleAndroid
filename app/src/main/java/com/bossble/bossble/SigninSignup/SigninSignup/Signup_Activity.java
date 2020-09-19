package com.bossble.bossble.SigninSignup.SigninSignup;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
/*
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
*/
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.signin.SignIn;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Signup_Activity extends Activity implements GoogleApiClient.OnConnectionFailedListener {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    TextView txtchallenge,txtworld,txtmember,txtlogin;
    Button btnfb;
    TextView btninsta,btngoogle,btnemail,btnphone;

    String video_duration;

    GoogleSignInClient mGoogleSignInClient;
    int video;

    //CallbackManager callbackmanager;
    //int RC_SIGN_IN=0;

    private final static  int RC_SIGN_IN=2;
    SignInButton sign_google ;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build();
    GoogleApiClient googleApiClient;

    String name,mail,user_image;
    String cip="";

    String latitute ;
    String longitude;
    String personId;

    String fbUserEmail="";
    CallbackManager callbackmanager;
    LoginButton sign_fb;
    LinearLayout l1;

    String id="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);


        FacebookSdk.sdkInitialize(getApplicationContext());


        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();//.enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


        SamLocationRequestService samLocationRequestService = new SamLocationRequestService(Signup_Activity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                Log.e("Location", String.valueOf(location));

                latitute = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                Log.e("lat",latitute);
                Log.e("long",longitude);
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);

                    String add = "";
                    if (addresses.size() > 0)
                    {
                        Log.e("countryname1",addresses.get(0).getCountryName());
                    }
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        },10);

        txtchallenge = findViewById(R.id.txtchallenge);
        txtworld = findViewById(R.id.txtworld);
        txtmember = findViewById(R.id.txtmember);
        txtlogin = findViewById(R.id.txtlogin);
       // btnfb = findViewById(R.id.btnfb);
        btninsta = findViewById(R.id.btninsta);
        btngoogle = findViewById(R.id.btngoogle);
        btnemail = findViewById(R.id.btnemail);
        btnphone = findViewById(R.id.btnphone);
        videoBG = findViewById(R.id.videoView);
        sign_fb = findViewById(R.id.btnsignupfb);
        l1 = findViewById(R.id.l1);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        /*sign_fb.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        sign_fb.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }



        });*/

        Drawable img2 = getResources().getDrawable( R.drawable.fb_icon);
        sign_fb.setCompoundDrawablesWithIntrinsicBounds( img2, null, null, null);
        sign_fb.setBackground(getResources().getDrawable(R.drawable.button_curve_transparent));
        sign_fb.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        sign_fb.setTextSize(14);
        //sign_fb.setPadding(30, 45, 45, 45);
        //sign_fb.setCompoundDrawablePadding(80);

        callbackmanager = CallbackManager.Factory.create();
        sign_fb.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        sign_fb.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
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

        requestSMSPermission();
        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip",cip);


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
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        printHashKey(this);

        if (getIntent().hasExtra("currentposition"))
        {
            video_duration = getIntent().getStringExtra("currentposition");
            Log.e("getduration",video_duration);
            mcurrentVideoPosition = Integer.parseInt(video_duration);

            videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer = mediaPlayer;
                    mMediaPlayer.setLooping(true);

                    if (mcurrentVideoPosition !=0)
                    {
                        mMediaPlayer.seekTo(mcurrentVideoPosition);
                        mMediaPlayer.start();
                    }
                }
            });

        }

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();
                Intent intent = new Intent(Signup_Activity.this,Signin_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition));
                startActivity(intent);
            }
        });

        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();
                Intent intent = new Intent(Signup_Activity.this,Signup_with_email_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition));
                startActivity(intent);
            }
        });

        btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();
                Intent intent = new Intent(Signup_Activity.this,Signup_With_Phone_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition));
                startActivity(intent);
            }
        });

        fonts();
        Uri uri = Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                +R.raw.vid202005080004

        );

        videoBG.setVideoURI(uri);

        videoBG.start();

        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);

                if (mcurrentVideoPosition !=0)
                {
                    mMediaPlayer.seekTo(mcurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });




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
        Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);

    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

//        mcurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
    public void fonts()
    {
        txtchallenge.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtworld.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtmember.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        //btnfb.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        btninsta.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        btngoogle.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        btnemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        btnphone.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("keyhash",hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                else {
                    Toast.makeText(getApplicationContext(),"Permission Denied for contacts",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void requestSMSPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this,permission);

        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Signup_Activity.this);
            builder.setTitle("Sms access needed");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setMessage("please confirm Sms access");//TODO put real question
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(
                            new String[]
                                    {Manifest.permission.RECEIVE_SMS}
                            , 100);
                }
            });
            builder.show();


        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e("result", String.valueOf(result.getStatus()));
        }else {
            callbackmanager.onActivityResult(requestCode,resultCode,data);

        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("checkgmail","yes");
        if(result.isSuccess()){
            Log.e("checkgmail","yes2");
            GoogleSignInAccount account = result.getSignInAccount();

            name=account.getDisplayName();
            mail = account.getEmail();
            user_image = String.valueOf(account.getPhotoUrl());
             personId = account.getId();
//            Log.e("name",name);


            signin(name,mail,personId,"2",user_image);

        }

        else{
            Log.e("checkgmail","yes5");
            Log.e("error", String.valueOf(result.getStatus()));
            Toast.makeText(getApplicationContext(),"Account not found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {


            if (currentAccessToken==null)
            {
                //fname.setText("");
            }
            else {

                loaduserprofile(currentAccessToken);
                Log.e("fb_token", String.valueOf(currentAccessToken.getToken()));
            }
        }
    };

    private void loaduserprofile (AccessToken newAccesstoken)
    {


        final GraphRequest request = GraphRequest.newMeRequest(newAccesstoken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                  //  Log.e("Response_facebook",response.getJSONObject().toString());
                    String res = new String(String.valueOf(response));
                    Log.e("resfbb",res);
                    // mail =object.getString("email");
                    String fb_first =object.getString("first_name");
                    String  fb_last = object.getString("last_name");
                    String  fb_id = object.getString("id");
                    String fb_name = fb_first+" "+fb_last;
                    Uri profilePictureUri= Profile.getCurrentProfile().getProfilePictureUri(200 , 200);
                    String fb_image = String.valueOf(profilePictureUri);

                    //String  fb_pic = object.getString("profile_picture");
                    //user_image = "https://graph.facebook.com/"+fb_id+"/picture?type=normal";

                    name = fb_first+" "+fb_last;

//                    Log.e("fb_email",mail);
                    Log.e("fb_first",fb_first);
                    Log.e("fb_last",fb_last);
                    Log.e("fb_id",fb_id);
                    //Log.e("image_url",user_image);

                    if (!object.has("email")){
                        String Email = object.getString("id");
                        fbUserEmail= Email+""+"@facebook.com";
                        Log.e("fb_email",fbUserEmail);

                        //   3135416846468468@facebook.com
                    }
                    else{
                        fbUserEmail = object.getString("email");
                        Log.e("fb_email",fbUserEmail);
                    }

                    signin(fb_name,fbUserEmail,fb_id,"1",fb_image);





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



        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();

            params.add("register_ip",cip);
            params.add("device_id","ewewewq");
            params.add("device_token","fdfdfdfdfdfdfd");
            params.add("device_type","android");
            params.add("latitude",latitute);
            params.add("longitude",longitude);
            params.add("social_media_id",personId);
            params.add("social_platform",platform);
            Log.e("s_params", String.valueOf(params));


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "login_social", params, new AsyncHttpResponseHandler() {
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

                        /*if (status.equals("true"))
                        {*/

                            //JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                             //id = dataSet.getString("id");
                            if (errorMessage.equals("Social media account does not exsits") && platform.equals("2"))
                            {
                                Intent intent = new Intent(Signup_Activity.this, Profile_Setup_Activity.class);
                                intent.putExtra("social_id",personId);
                                intent.putExtra("name",name);
                                intent.putExtra("mail",mail);
                                intent.putExtra("user_image",user_image);
                                intent.putExtra("social_platform",platform);
                                startActivity(intent);
                                Auth.GoogleSignInApi.signOut(googleApiClient);
                                googleApiClient.disconnect();
                                googleApiClient.connect();
                            }
                            else if (errorMessage.equals("Social media account does not exsits") && platform.equals("1"))
                            {
                                Intent intent = new Intent(Signup_Activity.this, Profile_Setup_Activity.class);
                                intent.putExtra("social_id",personId);
                                intent.putExtra("name",name);
                                intent.putExtra("mail",mail);
                                intent.putExtra("user_image",user_image);
                                intent.putExtra("social_platform",platform);
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
                                pref.edit().putString(Constant.EMAIL_ID,email).commit();
                                pref.edit().putString(Constant.MOBILE,mobile).commit();
                                pref.edit().putString(Constant.USER_ID,id).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                                Intent intent = new Intent(Signup_Activity.this,Home_Activity.class);
                                startActivity(intent);
                                Auth.GoogleSignInApi.signOut(googleApiClient);
                                googleApiClient.disconnect();
                                googleApiClient.connect();

                                LoginManager.getInstance().logOut();

                            }

                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Signup_Activity.this,jsonObject.getString("errorMessage"));
                            Auth.GoogleSignInApi.signOut(googleApiClient);
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                            LoginManager.getInstance().logOut();
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




    }


