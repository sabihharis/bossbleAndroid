package com.bossble.bossble.ForgotPassword;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.text.TextUtils;
import android.util.Log;
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
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_With_Phone_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Reset_Password_Activity extends AppCompatActivity {


    TextView txtreset,txtpassword;
    EditText edpassword,edconfirmpassword;
    Button btnreset;


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    String v1,v2;

    String video_duration;
    int video;

    String user_id="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset__password_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        txtreset = findViewById(R.id.txtreset);
        txtpassword = findViewById(R.id.txtpassword);
        edpassword = findViewById(R.id.edpassword);
        edconfirmpassword = findViewById(R.id.edconfirmpassword);
        btnreset = findViewById(R.id.btnreset);
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

        if (getIntent().hasExtra("user_id"))
        {
            user_id = getIntent().getStringExtra("user_id");
        }



        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              reset_password();
            }
        });

        fonts();

        Uri uri = Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                + R.raw.vid202005080004

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

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                reset_password();
            }
        });

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
        txtreset.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        edconfirmpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnreset.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
    }

    public void reset_password()
    {
        v1=edpassword.getText().toString();
        v2=edconfirmpassword.getText().toString();

        if(TextUtils.isEmpty(edpassword.getText()))
        {
            edpassword.setError("Field is required");
            edpassword.requestFocus();
        }
        else if(TextUtils.isEmpty(edconfirmpassword.getText()))
        {
            edconfirmpassword.setError("Field is required");
            edconfirmpassword.requestFocus();
        }
        else if (!v2.equals(v1))
        {
            edconfirmpassword.setError("Password not matched");
            edconfirmpassword.requestFocus();

        }
        else
        {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("password",v2);
            params.add("user_id",user_id);

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "new_password", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsereset",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true"))
                        {

                            Intent intent = new Intent(Reset_Password_Activity.this, Signin_Activity.class);
                            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                            startActivity(intent);
                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Reset_Password_Activity.this,jsonObject.getString("errorMessage"));
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
                        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_SHORT).show();
                        Log.e("responsereset",response);

                    }
                }
            });



        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Reset_Password_Activity.this,Forgot_Password_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
        startActivity(intent);
    }
}
