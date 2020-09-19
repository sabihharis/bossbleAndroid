package com.bossble.bossble.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.New_OnBoarding.New_Video_Activity;
import com.bossble.bossble.Notifications.Notification_Activity;
import com.bossble.bossble.R;
import com.bossble.bossble.Splash_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class ChangePassword_Activity extends AppCompatActivity {

    TextView txtchangepassword,txtcurrentpassword,txtnewpassword,txtconfirmpassword;
    EditText edcurrentpassword,ednewpassword,edconfirmpassword;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_);


        txtcurrentpassword = findViewById(R.id.txtcurrentpassword);
        txtnewpassword = findViewById(R.id.txtnewpassword);
        txtconfirmpassword = findViewById(R.id.txtconfirmpassword);
        txtchangepassword = findViewById(R.id.txtchangepassword);
        edcurrentpassword = findViewById(R.id.edcurrentpassword);
        ednewpassword = findViewById(R.id.ednewpassword);
        edconfirmpassword = findViewById(R.id.edconfirmpassword);
        btnsave = findViewById(R.id.btnsave);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isOnline(ChangePassword_Activity.this)){

                    Change_Password();

                }
                else{
                    Constant.ErrorToast(ChangePassword_Activity.this,getResources().getString(R.string.NoInternetConnection));

                }
            }
        });

        fonts();
    }

    public void fonts()
    {
        txtchangepassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        txtcurrentpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        txtnewpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        txtconfirmpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        edcurrentpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        ednewpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edconfirmpassword.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Light.ttf"));
        btnsave.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
    }

    public void Change_Password()
    {


        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = pref.getString(Constant.USER_ID,"");

        String currentpassword = edcurrentpassword.getText().toString();
        String newpassword = ednewpassword.getText().toString();
        String confirmpassword = edconfirmpassword.getText().toString();


        if (currentpassword.isEmpty()) {
            //ed1.setText(name);
            edcurrentpassword.setError(getResources().getString(R.string.Fieldisrequired));
            edcurrentpassword.requestFocus();
        } else if (newpassword.isEmpty()) {
            //ed1.setText(name);
            ednewpassword.setError(getResources().getString(R.string.Fieldisrequired));
            ednewpassword.requestFocus();
        }
        else if (confirmpassword.isEmpty()) {
            //ed1.setText(name);
            edconfirmpassword.setError(getResources().getString(R.string.Fieldisrequired));
            edconfirmpassword.requestFocus();
        }

        else if (!newpassword.equals(confirmpassword))
        {
            edconfirmpassword.setError(getResources().getString(R.string.Passwordnotmatched));
            edconfirmpassword.requestFocus();
        }
        else
        {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("user_id",u_id);
            params.add("old_password",currentpassword);
            params.add("new_password",newpassword);

            Log.e("paramsofpassword", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "user/user/update_password", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsepassword",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                            Constant.SuccessToast(ChangePassword_Activity.this,getResources().getString(R.string.PasswordChanged));


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    Intent intent = new Intent(ChangePassword_Activity.this,Settings_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }

                            },2000);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("responsepasswordF", response);

                    }
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(ChangePassword_Activity.this,getResources().getString(R.string.Somethingwentwrong));
                }
            });

        }

    }

}
