package com.bossble.bossble.SigninSignup.SigninSignup;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.ForgotPassword.Forgot_Password_Activity;
import com.bossble.bossble.ForgotPassword.Reset_Password_Activity;
import com.bossble.bossble.OtpReceiver.OTP_Receiver;
import com.bossble.bossble.ProfileSetup.Profile_Setup_Activity;
import com.bossble.bossble.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Verification_code_Activity extends AppCompatActivity {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    TextView txtverify,txtresend;
    EditText edv1,edv2,edv3,edv4;

    String video_duration;
    int video;

    String sms_code="",email="";
    String v1="",v2="",v3="",v4="";
    String verification_code="";
    String phone="",from="";

    String verification_c="",password_change_code="",from2="",email2="",mobile="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification_code_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //register email
        if (getIntent().hasExtra("sms_code"))
        {
            sms_code = getIntent().getStringExtra("sms_code");
            email = getIntent().getStringExtra("email");
            Log.e("code_rec",sms_code);
            Log.e("email",email);
            a =1;

        }
        //register phonr
        else if(getIntent().hasExtra("ssms_code"))
        {
            sms_code = getIntent().getStringExtra("ssms_code");
            phone = getIntent().getStringExtra("phone");
            from = getIntent().getStringExtra("from");
            Log.e("code_rec",sms_code);
            a =2;

        }
        //forgot
        else if (getIntent().hasExtra("from2"))
        {
            from2 = getIntent().getStringExtra("from2");
            verification_c = getIntent().getStringExtra("verification_code");
            password_change_code = getIntent().getStringExtra("password_change_code");
            email2 = getIntent().getStringExtra("email2");
            mobile = getIntent().getStringExtra("mobile");
            Log.e("verification_code",verification_c);
            Log.e("password_change_code",password_change_code);
            Log.e("email2",email2);
            a =3;
        }




        txtverify = findViewById(R.id.txtverify);
        txtresend = findViewById(R.id.txtresend);
        edv1 = findViewById(R.id.edv1);
        edv2 = findViewById(R.id.edv2);
        edv3 = findViewById(R.id.edv3);
        edv4 = findViewById(R.id.edv4);
        videoBG = findViewById(R.id.videoView);

        new OTP_Receiver().setEditText(edv1);

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


        txtresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from2.equals("from2"))
                {
                    resend_forgot();
                }
                else
                {
                    resend_signup();
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

                if(edv4.getText().toString().length()==1){


                    mcurrentVideoPosition  =videoBG.getCurrentPosition();
                    if (from2.equals("from2"))
                    {
                        forgetpassword_verification();
                    }
                    else
                    {
                        verifciation();
                    }

                }

                else if (edv4.getText().toString().length() == 0) {
                    edv3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }


        });
        if (!sms_code.equals(""))
        {
            edv1.setText(Character.toString(sms_code.charAt(0)));
            edv2.setText(Character.toString(sms_code.charAt(1)));
            edv3.setText(Character.toString(sms_code.charAt(2)));
            edv4.setText(Character.toString(sms_code.charAt(3)));
        }
       else
        {
            edv1.setText(Character.toString(password_change_code.charAt(0)));
            edv2.setText(Character.toString(password_change_code.charAt(1)));
            edv3.setText(Character.toString(password_change_code.charAt(2)));
            edv4.setText(Character.toString(password_change_code.charAt(3)));
        }

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
        txtverify.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtresend.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edv1.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        edv2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        edv3.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        edv4.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));

    }

    public void verifciation()
    {

        String et1=edv1.getText().toString();
        String et2=edv2.getText().toString();
        String et3=edv3.getText().toString();
        String et4=edv4.getText().toString();

        verification_code = et1+et2+et3+et4;

        if(et1.isEmpty()){
            edv1.setError("Required");
            edv1.requestFocus();
        }
        else if(et2.isEmpty()){
            edv2.setError("Required");
            edv2.requestFocus();

        }
        else if(et3.isEmpty()){
            edv3.setError("Required");
            edv3.requestFocus();

        }
        else if(et4.isEmpty()){
            edv4.setError("Required");
            edv4.requestFocus();

        }
        else if (!verification_code.equals(sms_code)){
            Toast.makeText(getApplicationContext(),"Wrong Verification Code",Toast.LENGTH_SHORT).show();
        }
        else
        {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            if (from.equals("2"))
            {
                params.add("mobile_email",phone);
                params.add("code",sms_code);
            }
            else
            {
                params.add("mobile_email",email);
                params.add("code",sms_code);

            }


            Log.e("params", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "verification", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                    String response = new String(responseBody);
                    Log.e("verificationresponse",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");
                            if (status.equals("true"))
                            {
                                Intent intent = new Intent(Verification_code_Activity.this, Profile_Setup_Activity.class);
                                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition));
                                intent.putExtra("from",from);
                                startActivity(intent);
                            }
                            else if (status.equals("false"))
                            {
                                Constant.ErrorToast(Verification_code_Activity.this,jsonObject.getString("errorMessage"));
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
                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_SHORT).show();
                        Log.e("verificationreposnseF",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String errorMessage = jsonObject.getString("errorMessage");

                            if (status.equals("false")) {
                                Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
            });






        }



    }

    public void forgetpassword_verification()
    {
        String et1=edv1.getText().toString();
        String et2=edv2.getText().toString();
        String et3=edv3.getText().toString();
        String et4=edv4.getText().toString();

        verification_code = et1+et2+et3+et4;

        if(et1.isEmpty()){
            edv1.setError("Required");
            edv1.requestFocus();
        }
        else if(et2.isEmpty()){
            edv2.setError("Required");
            edv2.requestFocus();

        }
        else if(et3.isEmpty()){
            edv3.setError("Required");
            edv3.requestFocus();

        }
        else if(et4.isEmpty()){
            edv4.setError("Required");
            edv4.requestFocus();

        }
        else if (!verification_code.equals(password_change_code)){
            Toast.makeText(getApplicationContext(),"Wrong Verification Code",Toast.LENGTH_SHORT).show();
        }
        else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();

            if (!email2.isEmpty())
            {
                params.add("mobile_email",email2);
                params.add("code",password_change_code);
            }
            else
            {
                params.add("mobile_email",phone);
                params.add("code",password_change_code);
            }

           /* params.add("mobile_email",email2);
            params.add("code",password_change_code);*/

            Log.e("log_params", String.valueOf(params));


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "validate_change_password_code", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsecode",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String user_id = dataSet.getString("id");

                            Intent intent = new Intent(Verification_code_Activity.this,Reset_Password_Activity.class);
                            intent.putExtra("user_id",user_id);
                            startActivity(intent);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    String response = new String(responseBody);
                    Log.e("responsecodeF",response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("false"))
                        {

                        }



                        } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }



    public void resend_forgot()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        if (!email2.isEmpty())
        {
            params.add("mobile_email",email2);
            params.add("flag","2");
        }
        else
        {
            params.add("mobile_email",phone);
            params.add("flag","2");
        }
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "code_resend", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("response_fresend",response);
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true"))
                    {

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        password_change_code = dataSet.getString("password_change_code");


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
                Log.e("response_fresendF",response);

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

    public void resend_signup()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        if (from.equals("2"))
        {
            params.add("mobile_email",phone);
            params.add("flag","1");
        }
        else
        {
            params.add("mobile_email",email);
            params.add("flag","1");

        }
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "code_resend", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("response_Sresend",response);
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true"))
                    {

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
                Log.e("response_SresendF",response);

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

    @Override
    public void onBackPressed() {

        if (a==1)
        {
            Intent intent = new Intent(Verification_code_Activity.this,Signup_with_email_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
            startActivity(intent);
        }
       else if (a==2)
        {
            Intent intent = new Intent(Verification_code_Activity.this,Signup_With_Phone_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
            startActivity(intent);
        }
        else if (a==3)
        {
            Intent intent = new Intent(Verification_code_Activity.this,Forgot_Password_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
            startActivity(intent);
        }

    }


}
