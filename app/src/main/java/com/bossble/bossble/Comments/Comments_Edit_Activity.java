package com.bossble.bossble.Comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Comments_Edit_Activity extends AppCompatActivity {

    TextView heading;
    RoundedImageView image;
    EditText edcomment;
    TextView cancel,update;
    String comment="";
    String commentid="";
    String userid="";
    String userimage="";
    String challenge_id = "";
    String reply_id = "";

    String mode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments__edit_);

        heading = findViewById(R.id.heading);
        image = findViewById(R.id.image);
        edcomment = findViewById(R.id.edcomment);
        cancel = findViewById(R.id.cancel);
        update = findViewById(R.id.update);

        heading.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        edcomment.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Regular.ttf"));
        cancel.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));
        update.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));


        if (getIntent().hasExtra("reply")){

            mode="reply";

            if (getIntent().hasExtra("id")){

                commentid = getIntent().getStringExtra("id");
                userid = getIntent().getStringExtra("uid");
                comment = getIntent().getStringExtra("comment");
                userimage = getIntent().getStringExtra("image");
                challenge_id = getIntent().getStringExtra("challenge_id");
                reply_id = getIntent().getStringExtra("reply_id");

                edcomment.setText(comment);

                RequestOptions options2 = new RequestOptions();
                options2.centerCrop();
                options2.placeholder(R.drawable.user_image_placeholder);
                Glide.with(Comments_Edit_Activity.this)
                        .load(userimage)
                        .apply(options2)
                        .into(image);

            }

        }
        else if (getIntent().hasExtra("comment")){

            mode="comment";

            if (getIntent().hasExtra("id")){

                commentid = getIntent().getStringExtra("id");
                userid = getIntent().getStringExtra("uid");
                comment = getIntent().getStringExtra("comment");
                userimage = getIntent().getStringExtra("image");
                challenge_id = getIntent().getStringExtra("challenge_id");

                edcomment.setText(comment);

                RequestOptions options2 = new RequestOptions();
                options2.centerCrop();
                options2.placeholder(R.drawable.user_image_placeholder);
                Glide.with(Comments_Edit_Activity.this)
                        .load(userimage)
                        .apply(options2)
                        .into(image);

            }

        }




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismissKeyboard(Comments_Edit_Activity.this);
                Comments_Edit_Activity.this.onBackPressed();

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismissKeyboard(Comments_Edit_Activity.this);
                if (mode.equals("reply")){
                    if (Constant.isOnline(Comments_Edit_Activity.this)){

                        reply_edit();
                    }
                    else{
                        Constant.ErrorToast(Comments_Edit_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }
                }
                else if (mode.equals("comment")){
                    if (Constant.isOnline(Comments_Edit_Activity.this)){

                        comment_edit();

                    }
                    else{
                        Constant.ErrorToast(Comments_Edit_Activity.this,getResources().getString(R.string.NoInternetConnection));
                    }
                }

            }
        });

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_id.equals(userid))
                {
                    Intent intent = new Intent(Comments_Edit_Activity.this,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(Comments_Edit_Activity.this,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }
            }
        });

    }


    private void comment_edit(){

        String toServer = edcomment.getText().toString();
        String cmnt = StringEscapeUtils.escapeJava(toServer);

        if (cmnt.isEmpty()){
            edcomment.setError(getResources().getString(R.string.Fieldisrequired));
            edcomment.requestFocus();
        }
        else {

            RequestParams params = new RequestParams();
            params.add("comment_id",commentid);
            params.add("user_id",userid);
            params.add("comment",cmnt);


            edcomment.setText("");


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "comment/comment/comment_edit", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String res = new String(responseBody);
                    Log.e("editresp",res);

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true") && errorMessage.equals("")){


                            Intent intent = new Intent(Comments_Edit_Activity.this,Comments_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("challenge_id",challenge_id);
                            startActivity(intent);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    if (responseBody!=null){
                        String res = new String(responseBody);
                        Log.e("editresp",res);

                    }
                    Constant.ErrorToast(Comments_Edit_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                }
            });

        }

    }


    private void reply_edit(){

        String toServer = edcomment.getText().toString();
        String cmnt = StringEscapeUtils.escapeJava(toServer);

        if (cmnt.isEmpty()){
            edcomment.setError(getResources().getString(R.string.Fieldisrequired));
            edcomment.requestFocus();
        }
        else {

            RequestParams params = new RequestParams();
            params.add("comment_id",commentid);
            params.add("user_id",userid);
            params.add("reply_id",reply_id);
            params.add("comment_reply",cmnt);


            edcomment.setText("");


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "comment/comment/comment_reply_edit", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String res = new String(responseBody);
                    Log.e("editresp",res);

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true") && errorMessage.equals("")){


                            Intent intent = new Intent(Comments_Edit_Activity.this,Reply_Comments_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("id",commentid);
                            intent.putExtra("cid",challenge_id);
                            startActivity(intent);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    if (responseBody!=null){
                        String res = new String(responseBody);
                        Log.e("editresp",res);

                    }
                    Constant.ErrorToast(Comments_Edit_Activity.this,getResources().getString(R.string.Somethingwentwrong));

                }
            });

        }

    }


    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

}
