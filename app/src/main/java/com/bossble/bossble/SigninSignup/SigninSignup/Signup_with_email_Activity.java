package com.bossble.bossble.SigninSignup.SigninSignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
/*
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
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.R;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

//import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;

public class Signup_with_email_Activity extends AppCompatActivity {


    TextView txtmember,txtlogin,txtenter,txtemail;
    EditText edemail,edpassword;
    Button btnsend;

    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    String video_duration;
    int video;

   /* SamLocationRequestService samLocationRequestService;

    String latitude="";
    String longitude="";*/
    String cip="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String latitute ;
    String longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_with_email_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

      /*  //for sending latitude and longitude of the current location of the user
        samLocationRequestService = new SamLocationRequestService(Signup_with_email_Activity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                Log.e("Location", String.valueOf(location));

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());




            }
        },10);*/


        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

         cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip",cip);


        SamLocationRequestService samLocationRequestService = new SamLocationRequestService(Signup_with_email_Activity.this, new SamLocationRequestService.SamLocationListener() {
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



        txtmember = findViewById(R.id.txtmember);
        txtlogin = findViewById(R.id.txtlogin);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnsend = findViewById(R.id.btnsend);
        txtenter = findViewById(R.id.txtenter);
        txtemail = findViewById(R.id.txtemail);
        videoBG = findViewById(R.id.videoView);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);


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


        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentVideoPosition  =videoBG.getCurrentPosition();
                Intent intent = new Intent(Signup_with_email_Activity.this,Signin_Activity.class);
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


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Signup_with_email();
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        //mcurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  videoBG.seekTo(stopPosition);
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
        txtenter.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtmember.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnsend.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
    }

    public void Signup_with_email() {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        final String mail = edemail.getText().toString();
        String pass = edpassword.getText().toString();
        if (mail.isEmpty())
        {
            edemail.setError("Field is required");
            edemail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
        {
            edemail.setError("Invalid Email");
            edemail.requestFocus();
        }
        else if (pass.isEmpty())
        {
            edpassword.setError("Field is required");
            edpassword.requestFocus();
        }
        else{
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("register_ip",cip);
            params.add("latitude",latitute);
            params.add("longitude",longitude);
            params.add("device_type","android");
            params.add("device_id","ewewewq");
            params.add("device_token","fdfdfdfdfdfdfd");
            params.add("email",mail);
            params.add("signup_flag","2");
            params.add("password",pass);

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL+"register_member",params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("signupresponse",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String user_id = dataSet.getString("user_id");
                            String sms_code = dataSet.getString("sms_code");
                            Log.e("sms_code",sms_code);
                            pref.edit().putString(Constant.USER_ID,user_id).commit();
                            pref.edit().putString(Constant.EMAIL_ID,mail).commit();

                            Intent intent = new Intent(Signup_with_email_Activity.this,Verification_code_Activity.class);
                            intent.putExtra("sms_code",sms_code);
                            intent.putExtra("email",mail);
                            startActivity(intent);
                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Signup_with_email_Activity.this,jsonObject.getString("errorMessage"));
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        if (responseBody != null) {
                            String response = new String(responseBody);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            Log.e("signupresponseF", response);

                        }
                    }

                    /*try {
                        JSONObject jsonobject = new JSONObject(response);
                        String status = jsonobject.getString("status");
                        String errorMessage = jsonobject.getString("errorMessage");

                        if (status.equals("false")) {
                            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }
                }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup_with_email_Activity.this,Signup_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
        startActivity(intent);
    }
}
