package com.bossble.bossble.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
/*
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.BlockedUsers;
import com.bossble.bossble.Model.Search_people;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bossble.bossble.Settings.BlockedAccount_Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

//BlockUserAdapter
public class BlockUserAdapter extends RecyclerView.Adapter<BlockUserAdapter.ViewHolderForCat> {

    private Context mcontext ;
    ArrayList<BlockedUsers> mnames=new ArrayList<>();
    String identifier="";
    String u_id="";
    Activity mactivity;

    public BlockUserAdapter(Context mcontext , ArrayList<BlockedUsers> names,Activity activity){
        this.mcontext = mcontext;
        mnames=names;
        mactivity = activity;

    }


    @NonNull
    @Override
    public BlockUserAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blockuser_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final BlockUserAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final BlockedUsers blockedUsers = mnames.get(i);
        //u_id = blockedUsers.getU_id();
        String username = blockedUsers.getUsername();
        if (username.equals("") || username.equals("null"))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(username);
        }


        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(blockedUsers.getImage())
                .apply(options)
                .into(viewHolderForCat.imgpeople);


        viewHolderForCat.btnunblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u_id = blockedUsers.getU_id();

                if (Constant.isOnline(mcontext)){

                    unblock(u_id);

                }
                else{
                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                }


            }
        });

    }


    @Override
    public int getItemCount() {

        return mnames.size();

    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        RoundedImageView imgpeople;
        TextView txtpeoplename,txtcountry;
        RelativeLayout r1,r2;
        Button btnunblock;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);


            imgpeople = itemView.findViewById(R.id.imgpeople);
            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcountry = itemView.findViewById(R.id.txtcountry);
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
            btnunblock = itemView.findViewById(R.id.btnunblock);
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtcountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            /*if (identifier.equals("4"))
            {
                Log.e("logidentifier",identifier);

                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
            }
*/



        }

    }

    public void updateList(ArrayList<BlockedUsers> list){
        mnames = list;
        notifyDataSetChanged();
    }

    public void unblock(String u_id)
    {
        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        String user_id = pref.getString(Constant.USER_ID,"");


        RequestParams params = new RequestParams();
        params.add("user_id",user_id);
        params.add("block_user_id[]",u_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "user/user/unblock_user",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responseblock",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        String unblock_user = dataSet.getString("unblock_user");

                        Intent intent = new Intent(mcontext,BlockedAccount_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mcontext.startActivity(intent);

                        // Constant.ErrorToast(mcontext.this,jsonObject.getString("errorMessage"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));

            }
        });





    }

}
