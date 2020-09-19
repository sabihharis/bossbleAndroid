package com.bossble.bossble.Notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.NotificationAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.Notifications;
import com.bossble.bossble.R;
import com.bossble.bossble.Search.Search_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class Notification_Activity extends AppCompatActivity {

    ImageView fulllogo,back;
    TextView heading;
    RecyclerView notificationRV;
    ArrayList<Notifications> notificationslist=new ArrayList<>();
    NotificationAdapter searchAdapter;

    String bottom ="";
    ScrollView sv;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    TextView notxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);

        Constant.bottomNav(Notification_Activity.this,3,sv,"notifications");

        sv = findViewById(R.id.sv);
        fulllogo = findViewById(R.id.fulllogo);
        back = findViewById(R.id.back);
        heading = findViewById(R.id.heading);
        notificationRV = findViewById(R.id.notificationRV);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        notxt = findViewById(R.id.notxt);


        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        notxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));

        if (getIntent().hasExtra("bottom")){
            bottom = "yes";
        }


        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notification_Activity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification_Activity.this.onBackPressed();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        notificationRV.setLayoutManager(layoutManager);
        notificationRV.setHasFixedSize(true);
        notificationRV.setNestedScrollingEnabled(false);

        if (Constant.isOnline(Notification_Activity.this)){

            notifications();
        }
        else{
            Constant.ErrorToast(Notification_Activity.this,getResources().getString(R.string.NoInternetConnection));

        }

    }

    @Override
    public void onBackPressed() {
        if (bottom.equals("")){
            super.onBackPressed();
        }
        else {
            Intent intent = new Intent(Notification_Activity.this,Home_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void notifications(){

        avibackground.setVisibility(View.VISIBLE);
        avi.smoothToShow();
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String userid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",userid);
        params.add("limit","200");
        params.add("offset","0");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "notification/notification/notification_list?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("notificationRes",res);

                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        String total_count = dataSet.getString("total_count");

                        if (dataSet.has("data_set")){

                            if (dataSet.get("data_set") instanceof JSONArray){

                                JSONArray data_set = dataSet.getJSONArray("data_set");

                                if (data_set.length()>0){

                                    for (int i=0; i<data_set.length(); i++){

                                        JSONObject item = data_set.getJSONObject(i);

                                        String user_id = item.getString("user_id");
                                        String username = item.getString("username");
                                        String image = item.getString("image");
                                        String notification_id = item.getString("notification_id");
                                        String notification = item.getString("notification");
                                        String title = item.getString("title");
                                        String notification_type = item.getString("notification_type");
                                        String is_readed = item.getString("is_readed");
                                        String create_date = item.getString("create_date");
                                        String post_id = item.getString("post_id");
                                        String notification_attribute = item.getString("notification_attribute");

                                        Notifications notifications = new Notifications();
                                        notifications.setUser_id(user_id);
                                        notifications.setUsername(username);
                                        notifications.setImage(image);
                                        notifications.setNotification_id(notification_id);
                                        notifications.setNotification(notification);
                                        notifications.setTitle(title);
                                        notifications.setNotification_type(notification_type);
                                        notifications.setIs_readed(is_readed);
                                        notifications.setCreate_date(create_date);
                                        notifications.setPost_id(post_id);
                                        notifications.setNotification_attribute(notification_attribute);


                                        notificationslist.add(notifications);



                                        searchAdapter = new NotificationAdapter(Notification_Activity.this,notificationslist,avi,avibackground,Notification_Activity.this);
                                        notificationRV.setAdapter(searchAdapter);
                                        searchAdapter.notifyDataSetChanged();

                                    }

                                }
                                else {
                                    notxt.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
                                notxt.setVisibility(View.VISIBLE);
                            }

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("notificationRes",res);
                }
                avibackground.setVisibility(View.GONE);
                avi.smoothToHide();
                Constant.ErrorToast(Notification_Activity.this,getResources().getString(R.string.Somethingwentwrong));

            }
        });


    }
}
