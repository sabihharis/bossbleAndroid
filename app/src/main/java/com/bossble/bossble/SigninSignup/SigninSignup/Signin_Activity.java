package com.bossble.bossble.SigninSignup.SigninSignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
/*
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
*/
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.ForgotPassword.Forgot_Password_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Signin_Activity extends AppCompatActivity {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    TextView txtlog,txtin,txtforgot,txtnew,txtaccount,txthint;
    EditText edemail,edpassword;
    Button btnlogin;
    String video_duration;
    int video;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    String cip="";
    String cont="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        txtlog = findViewById(R.id.txtlog);
        txtin = findViewById(R.id.txtin);
        txtforgot = findViewById(R.id.txtforgot);
        txtnew = findViewById(R.id.txtnew);
        txtaccount = findViewById(R.id.txtaccount);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btnlogin);
        videoBG = findViewById(R.id.videoView);
        txthint = findViewById(R.id.txthint);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

       /* LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();*/



        String locale = getResources().getConfiguration().locale.getCountry();
        Log.e("locale",locale);

        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip",cip);


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



       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
              // video_duration = videoBG.getCurrentPosition();
               Log.e("currentposition", String.valueOf(video_duration));

               signin();
           }
       });

       // stopPosition = videoView.getCurrentPosition();


        txtaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();

                Intent intent = new Intent(Signin_Activity.this,Signup_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                startActivity(intent);
            }
        });

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();
                Intent intent = new Intent(Signin_Activity.this, Forgot_Password_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
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

        //Constant.ErrorToast(Signin_Activity.this,"dfgdgdfgdf");

    }


    @Override
    protected void onPause() {
        super.onPause();

        video = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoBG.seekTo(video);
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
        txtlog.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtnew.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtforgot.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txthint.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtaccount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
    }

    public void signin()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String mail_phone = edemail.getText().toString();
        String pass = edpassword.getText().toString();
        if (mail_phone.isEmpty())
        {
            edemail.setError("Field is required");
            edemail.requestFocus();
        }
        else if (pass.isEmpty())
        {
            edpassword.setError("Field is required");
            edpassword.requestFocus();
        }
        else if (mail_phone.matches("[0-9]+") && mail_phone.length() > 2) {
            cont = "+"+mail_phone;
            Log.e("cont",cont);
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);
            RequestParams params = new RequestParams();
            params.add("password",pass);
            params.add("device_id","ewewewq");
            params.add("device_token","fdfdfdfdfdfdfd");
            params.add("ip_address",cip);
            params.add("device_type","android");
            params.add("mobile_email",cont);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "signIn", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("signinresponse",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");




                        if (status.equals("true"))
                        {
                           // Constant.SuccessToast(Signin_Activity.this,jsonObject.getString("Successfully Logged In"));
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String id = dataSet.getString("id");
                            String email = dataSet.getString("email");
                            String mobile = dataSet.getString("mobile");
                            pref.edit().putString(Constant.EMAIL_ID,email).commit();
                            pref.edit().putString(Constant.MOBILE,mobile).commit();
                            pref.edit().putString(Constant.USER_ID,id).commit();
                            pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                            Intent intent = new Intent(Signin_Activity.this, Home_Activity.class);

                            startActivity(intent);
                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Signin_Activity.this,jsonObject.getString(errorMessage));

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
                        Log.e("signinresponseF",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");
                            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
        {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);
            RequestParams params = new RequestParams();
            params.add("password",pass);
            params.add("device_id","ewewewq");
            params.add("device_token","fdfdfdfdfdfdfd");
            params.add("ip_address",cip);
            params.add("device_type","android");
            params.add("mobile_email",mail_phone);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "signIn", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("signinresponse",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");


                            //Constant.ErrorToast(Signin_Activity.this,jsonObject.getString(errorMessage));

                        if (status.equals("true"))
                        {
                            //Constant.SuccessToast(Signin_Activity.this,jsonObject.getString("Successfully Logged In"));
                            //Constant.SuccessToast(Signin_Activity.this,errorMessage);

                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String id = dataSet.getString("id");
                            String email = dataSet.getString("email");
                            String mobile = dataSet.getString("mobile");
                            pref.edit().putString(Constant.EMAIL_ID,email).commit();
                            pref.edit().putString(Constant.MOBILE,mobile).commit();
                            pref.edit().putString(Constant.USER_ID,mobile).commit();
                            pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                            Intent intent = new Intent(Signin_Activity.this, Home_Activity.class);
                            startActivity(intent);
                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Signin_Activity.this,errorMessage);

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
                        Log.e("signinresponseF",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");
                            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else
        {
            edemail.setError("Invalid Pattern");
            edemail.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signin_Activity.this,Signup_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
        startActivity(intent);
    }
}
