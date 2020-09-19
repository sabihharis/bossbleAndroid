package com.bossble.bossble;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bossble.bossble.Adapter.PeopleAdapter;
import com.bossble.bossble.Adapter.SearchAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Model.People;
import com.bossble.bossble.Search.Search_Activity;
import com.bossble.bossble.Show_All.Show_All_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class People_Activity extends AppCompatActivity {

    ImageView back;

    TextView txtpeople,txtfrequent,txtchallenge,nopeople;
    EditText search;

    ArrayList<People> list=new ArrayList<>();
    RecyclerView recyclerpeople;
    PeopleAdapter peopleAdapter;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    ImageView fulllogo;

    String id="";
    TextView done;
    ScrollView sv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        sv = findViewById(R.id.sv);

        Constant.bottomNav(People_Activity.this,0,sv,"");

        back = findViewById(R.id.back);
        txtpeople = findViewById(R.id.txtpeople);
        txtfrequent = findViewById(R.id.txtfrequent);
        txtchallenge = findViewById(R.id.txtchallenge);
        search = findViewById(R.id.search);
        recyclerpeople = findViewById(R.id.recyclerpeople);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);
        fulllogo = findViewById(R.id.fulllogo);
        nopeople = findViewById(R.id.nopeople);
        fulllogo = findViewById(R.id.fulllogo);
        done = findViewById(R.id.done);


        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(People_Activity.this,Home_Activity.class);
                startActivity(intent);
            }
        });


        if (getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");

            if (id.equals("3")){
                txtfrequent.setText(getResources().getString(R.string.FREQUENTLYCHALLENGED2));
            }
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerpeople.setLayoutManager(layoutManager);
        recyclerpeople.setHasFixedSize(true);
        recyclerpeople.setNestedScrollingEnabled(false);

        peopleAdapter = new PeopleAdapter(People_Activity.this,list,id,done,People_Activity.this);
        recyclerpeople.setAdapter(peopleAdapter);



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!list.isEmpty())
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

        Fonts();
        if (Constant.isOnline(People_Activity.this)){


            people();

        }
        else{
            Constant.ErrorToast(People_Activity.this,getResources().getString(R.string.NoInternetConnection));
        }

    }

    public void Fonts()
    {
        txtpeople.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        txtfrequent.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtchallenge.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        search.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
    }

    public void people()
    {

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String user_id = pref.getString(Constant.USER_ID,"");

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
                            nopeople.setVisibility(View.VISIBLE);
                        }
                        else if (dataSet.length()>0) {
                            for (int i = 0; i < dataSet.length(); i++) {
                                JSONObject item = dataSet.getJSONObject(i);


                                String user_id = item.getString("id");
                                String username = item.getString("username");
                                //String usercountry = item.getString("country");
                                String userimage = item.getString("image");

                                People people = new People();
                                people.setUserid(user_id);
                                people.setUsername(username);
                                people.setUsercountry("");
                                people.setUserimage(userimage);

                                list.add(people);
                                peopleAdapter.notifyDataSetChanged();



                            }
                        }


                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(People_Activity.this,jsonObject.getString("errorMessage"));
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
                Constant.ErrorToast(People_Activity.this,getResources().getString(R.string.Somethingwentwrong));
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
            }
        });

    }


    void filter(String text) {
        ArrayList<People> temp = new ArrayList();
        for (People d2 : list) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d2.getUsername().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d2);
            }
        }
        //update recyclerview
        peopleAdapter.updateList(temp);


    }
}
