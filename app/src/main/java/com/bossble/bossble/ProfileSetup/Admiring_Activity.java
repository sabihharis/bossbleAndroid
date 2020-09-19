package com.bossble.bossble.ProfileSetup;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.AdmireAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.People;
import com.bossble.bossble.R;
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

public class Admiring_Activity extends AppCompatActivity {

    ImageView header,fulllogo,back;
    TextView heading,noadmire;
    EditText search;
    RecyclerView recycleradmire;
    ArrayList<People> filters=new ArrayList<>();
    AdmireAdapter admireAdapter;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    String user_id="";
    ScrollView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admiring_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Constant.bottomNav(Admiring_Activity.this,1,sv,"");

        if (getIntent().hasExtra("user_id"))
        {
            user_id = getIntent().getStringExtra("user_id");
        }


        sv = findViewById(R.id.sv);
        header = findViewById(R.id.header);
        fulllogo = findViewById(R.id.fulllogo);
        back = findViewById(R.id.back);
        heading = findViewById(R.id.heading);
        search = findViewById(R.id.search);
        recycleradmire = findViewById(R.id.recycleradmire);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        noadmire = findViewById(R.id.noadmire);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleradmire.setLayoutManager(layoutManager);
        recycleradmire.setHasFixedSize(true);
        recycleradmire.setNestedScrollingEnabled(false);
        fonts();
        if (Constant.isOnline(Admiring_Activity.this)){

            people();
        }
        else{
            Constant.ErrorToast(Admiring_Activity.this,getResources().getString(R.string.NoInternetConnection));

        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!filters.isEmpty())
                {
                    filter(editable.toString());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admiring_Activity.this, Home_Activity.class);
                startActivity(intent);
            }
        });

    }
    public void fonts()
    {
        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        search.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Regular.ttf"));

    }


    public void people()
    {

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL+"follow/follow/get_follow_list?user_id="+user_id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsepeople",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true"))
                    {

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);


                        JSONArray dataSet = jsonObject.getJSONArray("dataSet");
                        if (dataSet.length()==0)
                        {
                            noadmire.setVisibility(View.VISIBLE);
                        }
                        else if (dataSet.length()>0) {
                            for (int i = 0; i < dataSet.length(); i++) {
                                JSONObject item = dataSet.getJSONObject(i);


                                String user_id = item.getString("id");
                                String username = item.getString("username");
                               // String usercountry = item.getString("country");
                                String userimage = item.getString("image");

                                People people = new People();
                                people.setUserid(user_id);
                                people.setUsername(username);
                                people.setUsercountry("");
                                people.setUserimage(userimage);

                                filters.add(people);
                                admireAdapter = new AdmireAdapter(Admiring_Activity.this,filters);
                                recycleradmire.setAdapter(admireAdapter);
                                admireAdapter.notifyDataSetChanged();




                            }
                        }


                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(Admiring_Activity.this,jsonObject.getString("errorMessage"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsepeopleF", response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(Admiring_Activity.this,getResources().getString(R.string.Somethingwentwrong));


            }
        });

    }
    void filter(String text) {
        ArrayList<People> temp = new ArrayList();
        for (People d2 : filters) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d2.getUsername().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d2);
            }
        }
        //update recyclerview
        admireAdapter.updateList(temp);


    }
}
