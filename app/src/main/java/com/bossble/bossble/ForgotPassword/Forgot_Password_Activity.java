package com.bossble.bossble.ForgotPassword;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.bossble.bossble.R;
import com.bossble.bossble.SigninSignup.SigninSignup.Signin_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_With_Phone_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Verification_code_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Forgot_Password_Activity extends AppCompatActivity {



    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    TextView txtforgot,txtpassword,txthint;
    EditText edemail;
    Button btnverify;

    String video_duration;
    int video;

    String mail_phone ="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot__password_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Uri uri = Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                + R.raw.vid202005080004

        );
        txtforgot = findViewById(R.id.txtforgot);
        txtpassword = findViewById(R.id.txtpassword);
        edemail = findViewById(R.id.edemail);
        btnverify = findViewById(R.id.btnverify);
        txthint = findViewById(R.id.txthint);

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

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                forgot_Password();
            }
        });

        fonts();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mcurrentVideoPosition = mMediaPlayer.getCurrentPosition();
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
        txtforgot.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txthint.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnverify.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
    }

    public void forgot_Password()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

          mail_phone = edemail.getText().toString();

        if (mail_phone.isEmpty())
        {
            edemail.setError("Field is required");
            edemail.requestFocus();
        }
        else if (mail_phone.matches("[0-9]+") && mail_phone.length() > 2) {
            RequestParams params = new RequestParams();
            if (Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
            {
                params.add("mobile_email",mail_phone);
            }
            else
            {
                params.add("mobile_email","+"+mail_phone);
            }

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "forgetPassword", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("forgotresponse",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true"))
                        {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String verification_code = dataSet.getString("verification_code");
                            String email = dataSet.getString("email");
                            String password_change_code = dataSet.getString("password_change_code");
                            String mobile = dataSet.getString("mobile");


                            Intent intent = new Intent(Forgot_Password_Activity.this,Verification_code_Activity.class);
                            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                            intent.putExtra("verification_code",verification_code);
                            intent.putExtra("password_change_code",password_change_code);
                            intent.putExtra("email2",email);
                            intent.putExtra("mobile",mobile);
                            intent.putExtra("from2","from2");
                            startActivity(intent);


                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Forgot_Password_Activity.this,jsonObject.getString("errorMessage"));
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
                    Log.e("forgotresponseF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });


        }
        else if (Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
        {
            RequestParams params = new RequestParams();
            if (Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
            {
                params.add("mobile_email",mail_phone);
            }
            else
            {
                params.add("mobile_email","+"+mail_phone);
            }

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "forgetPassword", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("forgotresponse",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true"))
                        {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String verification_code = dataSet.getString("verification_code");
                            String email = dataSet.getString("email");
                            String password_change_code = dataSet.getString("password_change_code");
                            String mobile = dataSet.getString("mobile");


                            Intent intent = new Intent(Forgot_Password_Activity.this,Verification_code_Activity.class);
                            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                            intent.putExtra("verification_code",verification_code);
                            intent.putExtra("password_change_code",password_change_code);
                            intent.putExtra("email2",email);
                            intent.putExtra("mobile",mobile);
                            intent.putExtra("from2","from2");
                            startActivity(intent);


                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Forgot_Password_Activity.this,jsonObject.getString("errorMessage"));
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
                    Log.e("forgotresponseF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Forgot_Password_Activity.this,Signin_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
        startActivity(intent);
    }
}
