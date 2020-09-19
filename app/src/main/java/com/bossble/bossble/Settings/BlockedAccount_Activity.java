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

import com.bossble.bossble.Adapter.BlockUserAdapter;
import com.bossble.bossble.Adapter.SearchAdapter;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.BlockedUsers;
import com.bossble.bossble.Model.Search_people;
import com.bossble.bossble.ProfileSetup.Admire_Activity;
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

public class BlockedAccount_Activity extends AppCompatActivity {

    TextView txtblockuser,noblock;
    ImageView back;
    EditText search;
    RecyclerView recyclerblock;
    ArrayList<BlockedUsers> filters=new ArrayList<>();
    BlockUserAdapter blockUserAdapter;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_account_);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        noblock = findViewById(R.id.noblock);
        recyclerblock = findViewById(R.id.recyclerblock);
        txtblockuser = findViewById(R.id.txtblockuser);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);



        blockUserAdapter = new BlockUserAdapter(BlockedAccount_Activity.this,filters,BlockedAccount_Activity.this);
        recyclerblock.setAdapter(blockUserAdapter);
        blockUserAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerblock.setLayoutManager(layoutManager);
        recyclerblock.setHasFixedSize(true);
        recyclerblock.setNestedScrollingEnabled(false);


        fonts();

        if (Constant.isOnline(BlockedAccount_Activity.this)){

            blocked_users();

        }
        else{
            Constant.ErrorToast(BlockedAccount_Activity.this,getResources().getString(R.string.NoInternetConnection));

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
                    filters(editable.toString());
                }

            }

            private void filters(String s) {
            }
        });



    }

    public void fonts()
    {
        txtblockuser.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf"));
    }

    public void blocked_users()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String user_id = pref.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",user_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "user/user/block_user_list?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responseblock",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {


                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        JSONArray dataSet = jsonObject.getJSONArray("dataSet");

                        if (dataSet.length()==0)
                        {
                            noblock.setVisibility(View.VISIBLE);
                        }

                        else if (dataSet.length()>0) {
                            for (int i = 0; i < dataSet.length(); i++) {
                                JSONObject item = dataSet.getJSONObject(i);

                                String u_id = item.getString("id");
                                String username = item.getString("username");
                                String image = item.getString("image");

                                BlockedUsers blockedUsers = new BlockedUsers();
                                blockedUsers.setU_id(u_id);
                                blockedUsers.setUsername(username);
                                blockedUsers.setImage(image);


                                filters.add(blockedUsers);
                                blockUserAdapter = new BlockUserAdapter(BlockedAccount_Activity.this,filters,BlockedAccount_Activity.this);
                                recyclerblock.setAdapter(blockUserAdapter);
                                blockUserAdapter.notifyDataSetChanged();



                            }
                        }




                    }
                    else if (status.equals("false"))
                    {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        Constant.ErrorToast(BlockedAccount_Activity.this,jsonObject.getString("errorMessage"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responseblockF",response);
                }
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                Constant.ErrorToast(BlockedAccount_Activity.this,getResources().getString(R.string.Somethingwentwrong));
            }
        });



    }



}
