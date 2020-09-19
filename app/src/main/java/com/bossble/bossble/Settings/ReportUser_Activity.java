package com.bossble.bossble.Settings;

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
import android.widget.TextView;

import com.bossble.bossble.Adapter.ReportAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.ReportUser;
import com.bossble.bossble.ProfileSetup.Admire_Activity;
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

public class ReportUser_Activity extends AppCompatActivity {

    TextView txtreport,noreport;
    EditText search;
    RecyclerView recyclerreport;
    ReportAdapter reportAdapter;
    ArrayList<ReportUser> filters2=new ArrayList<>();
    private AVLoadingIndicatorView avi;
    ImageView avibackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user_);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        txtreport = findViewById(R.id.txtreport);
        noreport = findViewById(R.id.noreport);
        search = findViewById(R.id.search);
        recyclerreport = findViewById(R.id.recyclerreport);
        avibackground = findViewById(R.id.avibackground);
        avi = findViewById(R.id.avi);

        // noadmire = findViewById(R.id.noadmire);



        Fonts();





        reportAdapter = new ReportAdapter(ReportUser_Activity.this,filters2);
        recyclerreport.setAdapter(reportAdapter);
        reportAdapter.notifyDataSetChanged();


        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerreport.setLayoutManager(layoutManager2);
        recyclerreport.setHasFixedSize(true);
        recyclerreport.setNestedScrollingEnabled(false);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (!filters2.isEmpty())
                {
                    filters2(editable.toString());
                }

            }
        });


        if (Constant.isOnline(ReportUser_Activity.this)){


            report_post();

        }
        else{
            Constant.ErrorToast(ReportUser_Activity.this,getResources().getString(R.string.NoInternetConnection));

        }






    }

    private void filters2(String s) {
    }

    public void Fonts()
    {
        txtreport.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        search.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Regular.ttf"));

    }

    public void report_post()
    {

        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String user_id = pref.getString(Constant.USER_ID,"");


        RequestParams params = new RequestParams();
        params.add("limit","100");
        params.add("offset","0");
        params.add("user_id",user_id);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/get_reported_challenge_list?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsereport",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true")) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONArray challenge = dataSet.getJSONArray("challenge");
                        if (challenge.length()==0)
                        {
                            noreport.setVisibility(View.VISIBLE);
                        }
                        else if (challenge.length() > 0) {
                            for (int i = 0; i < challenge.length(); i++) {
                                JSONObject item = challenge.getJSONObject(i);

                                String u_id = item.getString("id");
                                String challenge_id = item.getString("challenge_id");
                                String title = item.getString("title");
                                String image = item.getString("image");

                                ReportUser reportUser = new ReportUser();
                                reportUser.setU_id(u_id);
                                reportUser.setU_id(challenge_id);
                                reportUser.setTitle(title);
                                reportUser.setImage(image);


                                filters2.add(reportUser);


                                reportAdapter = new ReportAdapter(ReportUser_Activity.this,filters2);
                                recyclerreport.setAdapter(reportAdapter);
                                reportAdapter.notifyDataSetChanged();






                            }
                        }
                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(ReportUser_Activity.this,jsonObject.getString("errorMessage"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsereportF",response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(ReportUser_Activity.this,getResources().getString(R.string.Somethingwentwrong));
            }
        });

    }

}
