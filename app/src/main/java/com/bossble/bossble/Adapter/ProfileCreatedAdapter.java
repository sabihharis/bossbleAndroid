package com.bossble.bossble.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
/*
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Campaigns;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.security.auth.callback.Callback;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Fawad on 5/20/2020.
 */

//ProfileCreatedAdapter


public class ProfileCreatedAdapter extends RecyclerView.Adapter<ProfileCreatedAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<Profile_challenges> mnames=new ArrayList<>();
    String mMode="";
    Activity mactivity;
    Callback callback;


    public ProfileCreatedAdapter(Context mcontext , List<Profile_challenges> names,String mode,Activity activity,Callback callback){
        this.mcontext = mcontext;
        mnames=names;
        mMode = mode;
        mactivity = activity;
        this.callback=callback;

    }


    @NonNull
    @Override
    public ProfileCreatedAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_created_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileCreatedAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        Collections.sort(mnames,new Comparor());

        final Profile_challenges profile_challenges = mnames.get(i);

        String title = profile_challenges.getTitle();
        if (title.equals("null") || title.equals(""))
        {
            viewHolderForCat.name.setText("N/A");
        }
        else
        {
            if (!profile_challenges.getDescription_background().equals("")){
                if (profile_challenges.getTitle().length()>15){
                    String name = profile_challenges.getTitle().substring(0,13);
                    viewHolderForCat.name.setText(name+"..");
                }
                else {
                    viewHolderForCat.name.setText(title);
                }
            }
            else {
                viewHolderForCat.name.setText(title);
            }

        }
        if (profile_challenges.getImage()!=null && !profile_challenges.getImage().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(profile_challenges.getImage())
                    .apply(options)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);

        }
        else if (profile_challenges.getVideo()!=null && !profile_challenges.getVideo().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(profile_challenges.getVideo())
                    .apply(options)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_video);

        }
        else if (profile_challenges.getDescription_background().contains("#")){

            viewHolderForCat.image.setImageBitmap(null);
            Drawable mDrawable = ContextCompat.getDrawable(mcontext, R.drawable.random_relative_background);
            mDrawable.setTint(Color.parseColor(profile_challenges.getDescription_background()));
            viewHolderForCat.image.setBackground(mDrawable);

            if (!profile_challenges.getDescription_fonts().equals("")){
                viewHolderForCat.name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/"+profile_challenges.getDescription_fonts()));
            }

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);

        }
        else {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load("")
                    .apply(options)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setVisibility(View.GONE);
        }



        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",profile_challenges.getId());
                intent.putExtra("username",profile_challenges.getUsername());
                intent.putExtra("userimage",profile_challenges.getProfile_image());
                intent.putExtra("user_id",profile_challenges.getUser_id());
                intent.putExtra("country",profile_challenges.getUsercountry());
                if (!profile_challenges.getImage().equals("")){
                    intent.putExtra("image",profile_challenges.getImage());

                }
                else if (!profile_challenges.getVideo().equals("")){
                    intent.putExtra("image",profile_challenges.getVideo());
                }
                else if (!profile_challenges.getDescription_background().equals("")){
                    intent.putExtra("image",profile_challenges.getDescription_background());
                }
                else {
                    intent.putExtra("image","");
                }

                intent.putExtra("fonts",profile_challenges.getDescription_fonts());

                intent.putExtra("likes",profile_challenges.getLike_count());
                intent.putExtra("comments",profile_challenges.getComments_count());
                intent.putExtra("title",profile_challenges.getTitle());
                intent.putExtra("description",profile_challenges.getDescritpion());
                intent.putExtra("tags",profile_challenges.getTags());
                intent.putExtra("type",profile_challenges.getType());
                intent.putExtra("view_count",profile_challenges.getView_count());
                mcontext.startActivity(intent);
            }
        });

        if (mMode.equals("other")){
            viewHolderForCat.delete.setVisibility(View.GONE);
        }

        viewHolderForCat.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletedialog(profile_challenges.getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mnames.size();
    }
    //for removing redundancy
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    //end for removing redundancy


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView name;
        RoundedImageView image;
        ImageView mediatype,delete;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            mediatype = itemView.findViewById(R.id.mediatype);
            delete = itemView.findViewById(R.id.delete);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));

        }

    }

    private class  Comparor implements Comparator<Profile_challenges> {


        @Override
        public int compare(Profile_challenges profile_challenges, Profile_challenges t1) {
            return profile_challenges.getId().compareTo(t1.getId());
        }
    }

    public interface Callback{
        void onPostDeleted(String status);
    }


    private void delete(String challenge_id){

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String usid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("challenge_id",challenge_id);
        params.add("user_id",usid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "post/post/delete_challenge", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("delResp",res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){

                        callback.onPostDeleted("true");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){

                    String res = new String(responseBody);
                    Log.e("delRespF",res);

                }
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));


            }
        });



    }



    public  void deletedialog(final String cid){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mactivity);
        builder1.setMessage(mcontext.getResources().getString(R.string.Areyousureyouwanttodeletethispost));
        builder1.setCancelable(false);

        builder1.setNegativeButton(
                mcontext.getResources().getString(R.string.CANCEL),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        builder1.setPositiveButton(
                mcontext.getResources().getString(R.string.Deletee),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (Constant.isOnline(mcontext)){


                            delete(cid);

                        }
                        else{
                            Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                        }

                    }
                });


        final AlertDialog alert11 = builder1.create();
        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mcontext.getResources().getColor(R.color.black));
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mcontext.getResources().getColor(R.color.lightfbcolor));

            }
        });


        alert11.show();

    }




}