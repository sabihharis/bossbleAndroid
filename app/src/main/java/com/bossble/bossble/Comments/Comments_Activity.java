package com.bossble.bossble.Comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Adapter.AdmireAdapter;
import com.bossble.bossble.Adapter.CommentAdapter;
import com.bossble.bossble.Adapter.CommentReplyAdapter;
import com.bossble.bossble.Adapter.CommentsAdapter;
import com.bossble.bossble.Adapter.ProfileAttemptedAdapter;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.Comment_Reply;
import com.bossble.bossble.Model.Comments;
import com.bossble.bossble.Model.People;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.ProfileSetup.Admiring_Activity;
import com.bossble.bossble.R;
import com.bossble.bossble.Search.Search_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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

public class Comments_Activity extends AppCompatActivity implements CommentAdapter.Callback {

    EditText edcomment;
    ImageView imgcomment,header,fulllogo,back;
    RecyclerView recyclercomment;

    ArrayList<Comments> filters=new ArrayList<>();
    ArrayList<Comment_Reply> reply_list=new ArrayList<>();
    ArrayList<String> commentsid_list=new ArrayList<>();
    CommentAdapter commentsAdapter;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String challenge_id="";

    TextView empcomment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (getIntent().hasExtra("challenge_id"))
        {
            challenge_id = getIntent().getStringExtra("challenge_id");
        }


        edcomment = findViewById(R.id.edcomment);
        imgcomment = findViewById(R.id.imgcomment);
        recyclercomment = findViewById(R.id.recyclercomment);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        header = findViewById(R.id.header);
        fulllogo = findViewById(R.id.fulllogo);
        back = findViewById(R.id.back);
        empcomment = findViewById(R.id.empcomment);

        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comments_Activity.this, Home_Activity.class);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
               /* Intent intent = new Intent(Comments_Activity.this, Details_Activity.class);
                startActivity(intent);*/
            }
        });


        imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismissKeyboard(Comments_Activity.this);

                if (Constant.isOnline(Comments_Activity.this)){


                    add_comment();

                }
                else{
                    Constant.ErrorToast(Comments_Activity.this,getResources().getString(R.string.NoInternetConnection));
                }




            }
        });




        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclercomment.setLayoutManager(layoutManager);
        recyclercomment.setHasFixedSize(true);
        recyclercomment.setNestedScrollingEnabled(false);

        Fonts();


        if (Constant.isOnline(Comments_Activity.this)){


            comments();


        }
        else{
            Constant.ErrorToast(Comments_Activity.this,getResources().getString(R.string.NoInternetConnection));
        }

    }


    public void Fonts()
    {
        edcomment.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
    }

    public void comments()
    {

        filters.clear();

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
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

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray comments = dataSet.getJSONArray("comments");
                        if (comments.length()>0) {
                            for (int i = 0; i < comments.length(); i++) {
                                JSONObject item = comments.getJSONObject(i);

                                String userid = item.getString("id");
                                String username = item.getString("username");
                                String userimage = item.getString("image");

                                String serverResponse = item.getString("comment");
                                String usercomment = StringEscapeUtils.unescapeJava(serverResponse);

                                String comment_id = item.getString("comment_id");
                                String challenge_id = item.getString("challenge_id");

                                Comments comments1 = new Comments();
                                comments1.setUserid(userid);
                                comments1.setUsername(username);
                                comments1.setUserimage(userimage);
                                comments1.setUsercomment(usercomment);
                                comments1.setComment_id(comment_id);
                                comments1.setChallenge_id(challenge_id);

                                if (item.has("comment_reply_count")){
                                    comments1.setUserreply(item.getString("comment_reply_count"));

                                }
                                else {
                                    comments1.setUserreply("0");
                                }

                                filters.add(comments1);


                                /*JSONArray comment_reply = item.getJSONArray("comment_reply");
                                if (comment_reply.length()>0)
                                {
                                    for (int j =0 ; j < comment_reply.length();j++)
                                    {
                                        JSONObject item1 = comment_reply.getJSONObject(j);

                                        String user_id = item1.getString("id");
                                        String user_name = item1.getString("username");
                                        String user_comment_reply = item1.getString("comment_reply");
                                        String user_image = item1.getString("image");

                                        Comment_Reply comment_reply1 = new Comment_Reply();
                                        comment_reply1.setUser_id(user_id);
                                        comment_reply1.setUser_name(user_name);
                                        comment_reply1.setUser_comment_reply(user_comment_reply);
                                        comment_reply1.setUser_image(user_image);




                                        commentsid_list.add(user_comment_reply);
                                        comments1.setList(commentsid_list);



                                    }
                                    filters.add(comments1);

                                    //commentsid_list.add(comment_id);
                                }*/
                                //

                                commentsAdapter = new CommentAdapter(getApplicationContext(),filters,challenge_id,Comments_Activity.this,Comments_Activity.this);
                                recyclercomment.setAdapter(commentsAdapter);
                                commentsAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                    else if (status.equals("false"))
                    {
                        Constant.ErrorToast(Comments_Activity.this,jsonObject.getString("errorMessage"));
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
                Constant.ErrorToast(Comments_Activity.this,getResources().getString(R.string.Somethingwentwrong));


            }
        });



    }

    public void comments2()
    {

        filters.clear();



        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
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


                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");

                        if (dataSet.has("comments")){
                            JSONArray comments = dataSet.getJSONArray("comments");

                            if (comments.length()>0) {
                                for (int i = 0; i < comments.length(); i++) {
                                    JSONObject item = comments.getJSONObject(i);

                                    String userid = item.getString("id");
                                    String username = item.getString("username");
                                    String userimage = item.getString("image");
                                    String serverResponse = item.getString("comment");
                                    String usercomment = StringEscapeUtils.unescapeJava(serverResponse);
                                    String comment_id = item.getString("comment_id");
                                    String challenge_id = item.getString("challenge_id");

                                    Comments comments1 = new Comments();
                                    comments1.setUserid(userid);
                                    comments1.setUsername(username);
                                    comments1.setUserimage(userimage);
                                    comments1.setUsercomment(usercomment);
                                    comments1.setComment_id(comment_id);
                                    comments1.setChallenge_id(challenge_id);

                                    if (item.has("comment_reply_count")){
                                        comments1.setUserreply(item.getString("comment_reply_count"));

                                    }
                                    else {
                                        comments1.setUserreply("0");
                                    }

                                    filters.add(comments1);

                                    commentsAdapter = new CommentAdapter(getApplicationContext(),filters,challenge_id,Comments_Activity.this,Comments_Activity.this);
                                    recyclercomment.setAdapter(commentsAdapter);
                                    commentsAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                        else {
                            commentsAdapter = new CommentAdapter(getApplicationContext(),filters,challenge_id,Comments_Activity.this,Comments_Activity.this);
                            recyclercomment.setAdapter(commentsAdapter);
                            commentsAdapter.notifyDataSetChanged();
                        }
                        if (filters.size()>0){
                            recyclercomment.smoothScrollToPosition(filters.size());

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

    public void add_comment()
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
            params.add("challenge_id",challenge_id);
            params.add("comment",keyword);

            edcomment.setText("");

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "comment/comment/add_comment", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsecomment",response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            comments2();

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
                    if(responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("responsecommentF",response);
                    }

                    Constant.ErrorToast(Comments_Activity.this,getResources().getString(R.string.Somethingwentwrong));

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
            comments2();
        }
    }
}






