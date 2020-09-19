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
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bossble.bossble.Adapter.CommentAdapter;
import com.bossble.bossble.Adapter.CommentReplyAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.Comment_Reply;
import com.bossble.bossble.Model.Comments;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class Reply_Comments_Activity extends AppCompatActivity implements CommentReplyAdapter.Callback {

    String id="",cid="";
    RoundedImageView imgpeople;
    TextView txtpeoplename,txtcomment,heading,txtreply;
    RecyclerView recyclerreply;

    CommentReplyAdapter commentReplyAdapter;

    ArrayList<Comment_Reply>filters = new ArrayList<>();

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    RelativeLayout maincomment;


    EditText edcomment;
    ImageView imgcomment;
    String username="";
    String userid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply__comments_);


        imgpeople = findViewById(R.id.imgpeople);
        txtpeoplename = findViewById(R.id.txtpeoplename);
        txtcomment = findViewById(R.id.txtcomment);
        recyclerreply = findViewById(R.id.recyclerreply);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        maincomment = findViewById(R.id.maincomment);
        heading = findViewById(R.id.heading);
        txtreply = findViewById(R.id.txtreply);
        edcomment = findViewById(R.id.edcomment);
        imgcomment = findViewById(R.id.imgcomment);

        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        txtreply.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtcomment.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Regular.ttf"));
        txtpeoplename.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Medium.ttf"));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerreply.setLayoutManager(layoutManager);
        recyclerreply.setHasFixedSize(true);
        recyclerreply.setNestedScrollingEnabled(false);



        if (getIntent().hasExtra("username")){
            id= getIntent().getStringExtra("id");
            cid= getIntent().getStringExtra("cid");
            if (Constant.isOnline(Reply_Comments_Activity.this)){

                comments2();

            }
            else{
                Constant.ErrorToast(Reply_Comments_Activity.this,getResources().getString(R.string.NoInternetConnection));
            }

        }
        else {
            id= getIntent().getStringExtra("id");
            cid= getIntent().getStringExtra("cid");
            if (Constant.isOnline(Reply_Comments_Activity.this)){

                comments2();

            }
            else{
                Constant.ErrorToast(Reply_Comments_Activity.this,getResources().getString(R.string.NoInternetConnection));
            }

        }


        txtreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismissKeyboard(Reply_Comments_Activity.this);
                if (Constant.isOnline(Reply_Comments_Activity.this)){

                    reply_comment();
                }
                else{
                    Constant.ErrorToast(Reply_Comments_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }
            }
        });

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");

        imgpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_id.equals(userid))
                {
                    Intent intent = new Intent(Reply_Comments_Activity.this,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(Reply_Comments_Activity.this,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }

            }
        });

        txtpeoplename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_id.equals(userid))
                {
                    Intent intent = new Intent(Reply_Comments_Activity.this,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(Reply_Comments_Activity.this,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",userid);
                    startActivity(intent);
                }


            }
        });

    }

    public void comments2()
    {

        filters.clear();


        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.add("challenge_id",cid);
        params.add("limit","200");
        params.add("offset","0");


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "comment/comment/comment_list?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsecomment",response);


                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true"))
                    {

                        maincomment.setVisibility(View.VISIBLE);
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray comments = dataSet.getJSONArray("comments");
                        if (comments.length()>0) {

                            for (int i = 0; i < comments.length(); i++) {
                                JSONObject item = comments.getJSONObject(i);

                                String comment_id = item.getString("comment_id");

                                if (comment_id.equals(id)){

                                    userid = item.getString("id");
                                    username = item.getString("username");
                                    String userimage = item.getString("image");
                                    String serverResponse = item.getString("comment");
                                    String usercomment = StringEscapeUtils.unescapeJava(serverResponse);

                                    txtpeoplename.setText(username);
                                    txtcomment.setText(usercomment);
                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.user_image_placeholder);
                                    Glide.with(Reply_Comments_Activity.this)
                                            .load(userimage)
                                            .apply(options2)
                                            .into(imgpeople);

                                    JSONArray comment_reply = item.getJSONArray("comment_reply");
                                    if (comment_reply.length()>0)
                                    {
                                        for (int j =0 ; j < comment_reply.length();j++)
                                        {
                                            JSONObject item1 = comment_reply.getJSONObject(j);

                                            String user_id = item1.getString("id");
                                            String user_name = item1.getString("username");
                                            String serverResponse2 = item1.getString("comment_reply");
                                            String user_comment_reply = StringEscapeUtils.unescapeJava(serverResponse2);
                                            String user_image = item1.getString("image");
                                            String commentt_id = item1.getString("comment_id");
                                            String challenge_id = item1.getString("challenge_id");
                                            String reply_id = item1.getString("reply_id");


                                            Comment_Reply comment_reply1 = new Comment_Reply();
                                            comment_reply1.setUser_id(user_id);
                                            comment_reply1.setUser_name(user_name);
                                            comment_reply1.setUser_comment_reply(user_comment_reply);
                                            comment_reply1.setUser_image(user_image);

                                            comment_reply1.setCommentt_id(commentt_id);
                                            comment_reply1.setChallenge_id(challenge_id);
                                            comment_reply1.setReply_id(reply_id);




                                            filters.add(comment_reply1);

                                            commentReplyAdapter = new CommentReplyAdapter(getApplicationContext(),filters,Reply_Comments_Activity.this,Reply_Comments_Activity.this);
                                            recyclerreply.setAdapter(commentReplyAdapter);
                                            commentReplyAdapter.notifyDataSetChanged();


                                        }
                                    }

                                }


                            }
                        }

                        if (filters.size()>0){
                            recyclerreply.smoothScrollToPosition(filters.size());

                        }

                    }
                    else if (status.equals("false"))
                    {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("HomeRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false")){

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Reply_Comments_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });



    }


    public void comments3()
    {

        filters.clear();




        RequestParams params = new RequestParams();
        params.add("challenge_id",cid);
        params.add("limit","200");
        params.add("offset","0");


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "comment/comment/comment_list?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsecomment",response);




                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true"))
                    {

                        maincomment.setVisibility(View.VISIBLE);
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray comments = dataSet.getJSONArray("comments");
                        if (comments.length()>0) {

                            for (int i = 0; i < comments.length(); i++) {
                                JSONObject item = comments.getJSONObject(i);

                                String comment_id = item.getString("comment_id");

                                if (comment_id.equals(id)){

                                    String userid = item.getString("id");
                                    username = item.getString("username");
                                    String userimage = item.getString("image");
                                    String serverResponse = item.getString("comment");
                                    String usercomment = StringEscapeUtils.unescapeJava(serverResponse);

                                    txtpeoplename.setText(username);
                                    txtcomment.setText(usercomment);
                                    RequestOptions options2 = new RequestOptions();
                                    options2.centerCrop();
                                    options2.placeholder(R.drawable.user_image_placeholder);
                                    Glide.with(Reply_Comments_Activity.this)
                                            .load(userimage)
                                            .apply(options2)
                                            .into(imgpeople);

                                    JSONArray comment_reply = item.getJSONArray("comment_reply");
                                    if (comment_reply.length()>0)
                                    {
                                        for (int j =0 ; j < comment_reply.length();j++)
                                        {
                                            JSONObject item1 = comment_reply.getJSONObject(j);

                                            String user_id = item1.getString("id");
                                            String user_name = item1.getString("username");
                                            String serverResponse2 = item1.getString("comment_reply");
                                            String user_comment_reply = StringEscapeUtils.unescapeJava(serverResponse2);
                                            String user_image = item1.getString("image");
                                            String commentt_id = item1.getString("comment_id");
                                            String challenge_id = item1.getString("challenge_id");
                                            String reply_id = item1.getString("reply_id");

                                            Comment_Reply comment_reply1 = new Comment_Reply();
                                            comment_reply1.setUser_id(user_id);
                                            comment_reply1.setUser_name(user_name);
                                            comment_reply1.setUser_comment_reply(user_comment_reply);
                                            comment_reply1.setUser_image(user_image);
                                            comment_reply1.setCommentt_id(commentt_id);
                                            comment_reply1.setChallenge_id(challenge_id);
                                            comment_reply1.setReply_id(reply_id);




                                            filters.add(comment_reply1);

                                            commentReplyAdapter = new CommentReplyAdapter(getApplicationContext(),filters,Reply_Comments_Activity.this,Reply_Comments_Activity.this);
                                            recyclerreply.setAdapter(commentReplyAdapter);
                                            commentReplyAdapter.notifyDataSetChanged();


                                        }
                                    }

                                }


                            }
                        }

                        if (filters.size()>0){
                            recyclerreply.smoothScrollToPosition(filters.size());

                        }

                    }
                    else if (status.equals("false"))
                    {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("HomeRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false")){

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        });



    }


    public void reply_comment()
    {

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String user_id = pref.getString(Constant.USER_ID,"");

        String toServer = edcomment.getText().toString();
        String keyword = StringEscapeUtils.escapeJava(toServer);
        if (keyword.isEmpty())
        {
            edcomment.setError(getResources().getString(R.string.Fieldisrequired));
            edcomment.requestFocus();
        }
        else
        {
            RequestParams params = new RequestParams();
            params.add("user_id",user_id);
            params.add("challenge_id",cid);
            params.add("comment_reply",keyword);
            params.add("comment_id",id);

            Log.e("paramsofreply", String.valueOf(params));

            edcomment.setText("");


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "comment/comment/comment_reply", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsereplycomment",response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            //Constant.SuccessToast(Comments_Activity.this,"Comment Added Successfully");
                            comments3();

                        }
                        else if (status.equals("false"))
                        {

                            //Constant.ErrorToast(Comments_Activity.this,jsonObject.getString("errorMessage"));
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    Constant.ErrorToast(Reply_Comments_Activity.this,getResources().getString(R.string.Somethingwentwrong));

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

    @Override
    public void onCommentDeleted(String status) {
        if (status.equals("true")){
            comments3();
        }
    }
}
