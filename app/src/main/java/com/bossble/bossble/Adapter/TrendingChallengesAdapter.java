package com.bossble.bossble.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
/*
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
*/
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Comments.Comments_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Trending_Challenges;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class TrendingChallengesAdapter extends RecyclerView.Adapter<TrendingChallengesAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<Trending_Challenges> mtrend = new ArrayList<>();
    Activity mactivity;
    String current="";

    AVLoadingIndicatorView mavi;
    ImageView mavibackground;


    public TrendingChallengesAdapter(Context mcontext , List<Trending_Challenges> trendingusername,Activity activity,AVLoadingIndicatorView avi,ImageView avibackground){
        this.mcontext = mcontext;
        mtrend = trendingusername ;
        mactivity = activity;
        mavi = avi;
        mavibackground = avibackground;

    }

    @NonNull
    @Override
    public TrendingChallengesAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trendingchallenge_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendingChallengesAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        Collections.sort(mtrend,new Comparor());

        final Trending_Challenges trending_challenges = mtrend.get(i);
        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");


        if(trending_challenges.getUser_like().equals("1")){
            if (trending_challenges.getUser_like_reward().equals("bronze")){

                viewHolderForCat.like.setTextColor(mcontext.getResources().getColor(R.color.bronzecoin));
                viewHolderForCat.like.setCompoundDrawableTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.bronzecoin)));

            }
            else if (trending_challenges.getUser_like_reward().equals("silver")){
                viewHolderForCat.like.setTextColor(mcontext.getResources().getColor(R.color.silvercoin));
                viewHolderForCat.like.setCompoundDrawableTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.silvercoin)));

            }
            else if (trending_challenges.getUser_like_reward().equals("gold")){
                viewHolderForCat.like.setTextColor(mcontext.getResources().getColor(R.color.goldcoin));
                viewHolderForCat.like.setCompoundDrawableTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.goldcoin)));

            }
        }
        else {
            viewHolderForCat.like.setTextColor(mcontext.getResources().getColor(R.color.white));
            viewHolderForCat.like.setCompoundDrawableTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.white)));

        }



        //user info
        if (trending_challenges.getUsername().equals("")){
            viewHolderForCat.username.setText("N/A");
        }
        else {
            viewHolderForCat.username.setText(trending_challenges.getUsername());
        }

        viewHolderForCat.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = trending_challenges.getUser_id();
                if (u_id.equals(user_id))
                {
                    Intent intent = new Intent(mcontext,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }

            }
        });


        viewHolderForCat.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = trending_challenges.getUser_id();
                if (u_id.equals(user_id))
                {
                    Intent intent = new Intent(mcontext,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }

            }
        });

        viewHolderForCat.usercountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = trending_challenges.getUser_id();
                if (u_id.equals(user_id))
                {
                    Intent intent = new Intent(mcontext,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",user_id);
                    mcontext.startActivity(intent);
                }

            }
        });

        String country = trending_challenges.getCountry();
        if (country.equals("") || country.equals("null"))
        {
            viewHolderForCat.usercountry.setText("N/A");

        }
        else
        {
            viewHolderForCat.usercountry.setText(country);
        }




        //viewHolderForCat.usercountry.setText(trending_challenges.getC());

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(trending_challenges.getProfile_image())
                .apply(options)
                .into(viewHolderForCat.userimage);


        //challenge info
        if (trending_challenges.getTitle().length()>22){
            String name = trending_challenges.getTitle().substring(0,20);
            viewHolderForCat.challengename.setText(name+"..");
        }
        else {
            viewHolderForCat.challengename.setText(trending_challenges.getTitle());
        }



        viewHolderForCat.time.setText(parseDateToddMMyyyy(trending_challenges.getCreated_at()));

        viewHolderForCat.like.setText(" "+trending_challenges.getLike_count());
        viewHolderForCat.comment.setText(" "+trending_challenges.getComments_count());
        viewHolderForCat.views.setText(" "+trending_challenges.getView_count());

        //  viewHolderForCat.views.setText(" "+trending_challenges.getComments_count());

        if (!trending_challenges.getImage().equals("")){
            RequestOptions options2 = new RequestOptions();
            options2.centerCrop();
            options2.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(trending_challenges.getImage())
                    .apply(options2)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);
        }
        else if (!trending_challenges.getVideo().equals("")){
            RequestOptions options2 = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(trending_challenges.getVideo())
                    .apply(options2)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_video);

        }
        else if (!trending_challenges.getDescription_background().equals("")){

            viewHolderForCat.image.setImageBitmap(null);
            Drawable mDrawable = ContextCompat.getDrawable(mcontext, R.drawable.random_relative_background);
            mDrawable.setTint(Color.parseColor(trending_challenges.getDescription_background()));
            viewHolderForCat.image.setBackground(mDrawable);

            if (!trending_challenges.getDescription_fonts().equals("")){
                viewHolderForCat.challengename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/"+trending_challenges.getDescription_fonts()));
            }

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);
            viewHolderForCat.opacitycover.setVisibility(View.GONE);

        }
        else {
            RequestOptions options3 = new RequestOptions();
            options3.centerCrop();
            options3.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load("")
                    .apply(options3)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setVisibility(View.VISIBLE);
        }



        viewHolderForCat.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Comments_Activity.class);
                intent.putExtra("challenge_id",trending_challenges.getId());
                mcontext.startActivity(intent);

            }
        });

        viewHolderForCat.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //else {
                if (trending_challenges.getUser_like_reward().equals("silver")){
                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"1",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }
                else if (trending_challenges.getUser_like_reward().equals("gold")){
                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"2",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }
                else if (trending_challenges.getUser_like_reward().equals("bronze")){
                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"3",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }


                if (viewHolderForCat.likelayout.getVisibility()==View.VISIBLE){
                    Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                    out.setDuration(300);
                    viewHolderForCat.likelayout.startAnimation(out);
                    viewHolderForCat.likelayout.setVisibility(View.GONE);

                }



                //}
            }
        });

        viewHolderForCat.like.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if (viewHolderForCat.likelayout.getVisibility()==View.GONE){
                    viewHolderForCat.likelayout.setAlpha(0f);
                    viewHolderForCat.likelayout.setVisibility(View.VISIBLE);
                    viewHolderForCat.likelayout.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .setListener(null);

                }

                return true;
            }
        });



        viewHolderForCat.bronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!trending_challenges.getUser_like_reward().equals("bronze")){

                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"3",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }

                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.likelayout.startAnimation(out);
                viewHolderForCat.likelayout.setVisibility(View.GONE);

            }
        });


        viewHolderForCat.silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!trending_challenges.getUser_like_reward().equals("silver")){

                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"1",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }

                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.likelayout.startAnimation(out);
                viewHolderForCat.likelayout.setVisibility(View.GONE);
            }
        });

        viewHolderForCat.gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!trending_challenges.getUser_like_reward().equals("gold")){

                    if (Constant.isOnline(mcontext)){

                        like(trending_challenges.getId(),"2",trending_challenges,i);

                    }
                    else{
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                    }


                }

                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.likelayout.startAnimation(out);
                viewHolderForCat.likelayout.setVisibility(View.GONE);
            }
        });

        viewHolderForCat.coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolderForCat.coinsreadinglayout.getVisibility()==View.GONE){
                    viewHolderForCat.coinsreadinglayout.setAlpha(0f);
                    viewHolderForCat.coinsreadinglayout.setVisibility(View.VISIBLE);
                    viewHolderForCat.coinsreadinglayout.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .setListener(null);

                }
                else {

                    Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                    out.setDuration(300);
                    viewHolderForCat.coinsreadinglayout.startAnimation(out);
                    viewHolderForCat.coinsreadinglayout.setVisibility(View.GONE);


                }
            }
        });


        viewHolderForCat.maincoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.coinsreadinglayout.startAnimation(out);
                viewHolderForCat.coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        viewHolderForCat.bronzereading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.coinsreadinglayout.startAnimation(out);
                viewHolderForCat.coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        viewHolderForCat.silverreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.coinsreadinglayout.startAnimation(out);
                viewHolderForCat.coinsreadinglayout.setVisibility(View.GONE);
            }
        });

        viewHolderForCat.goldreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation out = AnimationUtils.makeOutAnimation(mcontext, true);
                out.setDuration(300);
                viewHolderForCat.coinsreadinglayout.startAnimation(out);
                viewHolderForCat.coinsreadinglayout.setVisibility(View.GONE);
            }
        });




        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",trending_challenges.getId());
                intent.putExtra("username",trending_challenges.getUsername());
                intent.putExtra("userimage",trending_challenges.getProfile_image());
                intent.putExtra("user_id",trending_challenges.getUser_id());

                if (!trending_challenges.getImage().equals("")){
                    intent.putExtra("image",trending_challenges.getImage());
                }
                else if (!trending_challenges.getVideo().equals("")){
                    intent.putExtra("image",trending_challenges.getVideo());
                }
                else if (!trending_challenges.getDescription_background().equals("")){
                    intent.putExtra("image",trending_challenges.getDescription_background());
                }
                else {
                    intent.putExtra("image","");
                }

                intent.putExtra("fonts",trending_challenges.getDescription_fonts());
                intent.putExtra("likes",trending_challenges.getLike_count());
                intent.putExtra("comments",trending_challenges.getComments_count());
                intent.putExtra("title",trending_challenges.getTitle());
                intent.putExtra("description",trending_challenges.getDescritpion());
                intent.putExtra("tags",trending_challenges.getTags());
                intent.putExtra("type","challenge");
                intent.putExtra("country",trending_challenges.getCountry());
                intent.putExtra("view_count",trending_challenges.getView_count());
                mcontext.startActivity(intent);
            }
        });

        viewHolderForCat.joined_user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderForCat.joined_user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderForCat.joined_user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

            viewHolderForCat.join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (viewHolderForCat.join.getText().toString().equals(mcontext.getResources().getString(R.string.View))){

                        Intent intent = new Intent(mcontext,Details_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id",trending_challenges.getId());
                        intent.putExtra("username",trending_challenges.getUsername());
                        intent.putExtra("userimage",trending_challenges.getProfile_image());
                        intent.putExtra("user_id",trending_challenges.getUser_id());

                        if (!trending_challenges.getImage().equals("")){
                            intent.putExtra("image",trending_challenges.getImage());
                        }
                        else if (!trending_challenges.getVideo().equals("")){
                            intent.putExtra("image",trending_challenges.getVideo());
                        }
                        else if (!trending_challenges.getDescription_background().equals("")){
                            intent.putExtra("image",trending_challenges.getDescription_background());
                        }
                        else {
                            intent.putExtra("image","");
                        }

                        intent.putExtra("fonts",trending_challenges.getDescription_fonts());
                        intent.putExtra("likes",trending_challenges.getLike_count());
                        intent.putExtra("comments",trending_challenges.getComments_count());
                        intent.putExtra("title",trending_challenges.getTitle());
                        intent.putExtra("description",trending_challenges.getDescritpion());
                        intent.putExtra("tags",trending_challenges.getTags());
                        intent.putExtra("type","challenge");
                        intent.putExtra("country",trending_challenges.getCountry());
                        intent.putExtra("view_count",trending_challenges.getView_count());
                        intent.putExtra("viewonly","viewonly");
                        mcontext.startActivity(intent);

                    }
                    else {
                        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                        String uid = preferences.getString(Constant.USER_ID,"");


                        if (trending_challenges.getChallenge_title().equals("1-on-1 Challenge ") || trending_challenges.getChallenge_title().equals("Group Challenge")){

                            if (trending_challenges.getJoinuser()!=null && trending_challenges.getJoinuser().equals("yes")){

                                if(uid.equals(trending_challenges.getUser_id())){
                                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjoinyourownchallenge));
                                }
                                else if (trending_challenges.getAll_userid()!=null && trending_challenges.getAll_userid().equals("yes")){
                                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.AlreadyAttempted));
                                }
                                else {

                                    if (Constant.isOnline(mcontext)){

                                        joinApi(trending_challenges);

                                    }
                                    else{
                                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                                    }


                                }
                            }
                            else {
                                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjointhischallenge));
                            }

                        }
                        else {

                            if(uid.equals(trending_challenges.getUser_id())){
                                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjoinyourownchallenge));
                            }
                            else if (trending_challenges.getAll_userid()!=null && trending_challenges.getAll_userid().equals("yes")){
                                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.AlreadyAttempted));
                            }
                            else {

                                if (Constant.isOnline(mcontext)){

                                    joinApi(trending_challenges);

                                }
                                else{
                                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                                }


                            }
                        }
                    }
                }
            });
      //  }

        viewHolderForCat.peoplejoined.setText(trending_challenges.getCount()+" PEOPLE JOINED");

        if(trending_challenges.getCount().equals("0")){
            viewHolderForCat.seeall.setVisibility(View.GONE);
        }
        else {
            viewHolderForCat.seeall.setText("See All ("+trending_challenges.getCount()+")");

        }

        if (trending_challenges.getProfile_img1().equals("no") && trending_challenges.getProfile_img2().equals("no") && trending_challenges.getProfile_img3().equals("no")){

            viewHolderForCat.medal2.setVisibility(View.GONE);
            viewHolderForCat.medal3.setVisibility(View.GONE);
            viewHolderForCat.medal1.setVisibility(View.GONE);
            viewHolderForCat.scnd.setVisibility(View.GONE);
            viewHolderForCat.thrd.setVisibility(View.GONE);
            viewHolderForCat.fst.setVisibility(View.GONE);
            viewHolderForCat.notxt.setVisibility(View.VISIBLE);

        }
        else if (!trending_challenges.getProfile_img1().equals("no") && trending_challenges.getProfile_img2().equals("no") && trending_challenges.getProfile_img3().equals("no") ){

            viewHolderForCat.medal2.setVisibility(View.GONE);
            viewHolderForCat.medal3.setVisibility(View.GONE);
            viewHolderForCat.scnd.setVisibility(View.GONE);
            viewHolderForCat.thrd.setVisibility(View.GONE);

            if (trending_challenges.getProfile_img1().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img1())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }

            // first listener
            viewHolderForCat.joined_user1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId1());
                    intent.putExtra("username",trending_challenges.getUsername1());
                    intent.putExtra("userimage",trending_challenges.getProfile_img1());
                    intent.putExtra("user_id",trending_challenges.getUser_id1());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike1());
                    intent.putExtra("comments",trending_challenges.getComments_count1());
                    intent.putExtra("title",trending_challenges.getTitle1());
                    intent.putExtra("description",trending_challenges.getDescription1());
                    intent.putExtra("tags",trending_challenges.getTags1());
                    if (trending_challenges.getChallenge_title1().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry1());
                    intent.putExtra("view_count",trending_challenges.getView_count1());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });

        }
        else if (!trending_challenges.getProfile_img1().equals("no") && !trending_challenges.getProfile_img2().equals("no") && trending_challenges.getProfile_img3().equals("no")){

            viewHolderForCat.medal3.setVisibility(View.GONE);
            viewHolderForCat.thrd.setVisibility(View.GONE);

            if (trending_challenges.getProfile_img1().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img1())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }

            if (trending_challenges.getProfile_img2().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user2);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img2())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user2);

            }

            // two listeners
            viewHolderForCat.joined_user1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId1());
                    intent.putExtra("username",trending_challenges.getUsername1());
                    intent.putExtra("userimage",trending_challenges.getProfile_img1());
                    intent.putExtra("user_id",trending_challenges.getUser_id1());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike1());
                    intent.putExtra("comments",trending_challenges.getComments_count1());
                    intent.putExtra("title",trending_challenges.getTitle1());
                    intent.putExtra("description",trending_challenges.getDescription1());
                    intent.putExtra("tags",trending_challenges.getTags1());
                    if (trending_challenges.getChallenge_title1().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry1());
                    intent.putExtra("view_count",trending_challenges.getView_count1());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });


            viewHolderForCat.joined_user2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId2());
                    intent.putExtra("username",trending_challenges.getUsername2());
                    intent.putExtra("userimage",trending_challenges.getProfile_img2());
                    intent.putExtra("user_id",trending_challenges.getUser_id2());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike2());
                    intent.putExtra("comments",trending_challenges.getComments_count2());
                    intent.putExtra("title",trending_challenges.getTitle2());
                    intent.putExtra("description",trending_challenges.getDescription2());
                    intent.putExtra("tags",trending_challenges.getTags2());
                    if (trending_challenges.getChallenge_title2().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry2());
                    intent.putExtra("view_count",trending_challenges.getView_count2());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });





        }
        else {
            if (trending_challenges.getProfile_img1().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img1())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user1);

            }

            if (trending_challenges.getProfile_img2().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user2);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img2())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user2);

            }

            if (trending_challenges.getProfile_img3().equals("")){

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load("")
                        .apply(options3)
                        .into(viewHolderForCat.joined_user3);

            }
            else {

                RequestOptions options3 = new RequestOptions();
                options3.centerCrop();
                options3.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mcontext)
                        .load(trending_challenges.getProfile_img3())
                        .apply(options3)
                        .into(viewHolderForCat.joined_user3);

            }


            // two listeners
            viewHolderForCat.joined_user1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId1());
                    intent.putExtra("username",trending_challenges.getUsername1());
                    intent.putExtra("userimage",trending_challenges.getProfile_img1());
                    intent.putExtra("user_id",trending_challenges.getUser_id1());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike1());
                    intent.putExtra("comments",trending_challenges.getComments_count1());
                    intent.putExtra("title",trending_challenges.getTitle1());
                    intent.putExtra("description",trending_challenges.getDescription1());
                    intent.putExtra("tags",trending_challenges.getTags1());
                    if (trending_challenges.getChallenge_title1().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry1());
                    intent.putExtra("view_count",trending_challenges.getView_count1());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });


            viewHolderForCat.joined_user2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId2());
                    intent.putExtra("username",trending_challenges.getUsername2());
                    intent.putExtra("userimage",trending_challenges.getProfile_img2());
                    intent.putExtra("user_id",trending_challenges.getUser_id2());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike2());
                    intent.putExtra("comments",trending_challenges.getComments_count2());
                    intent.putExtra("title",trending_challenges.getTitle2());
                    intent.putExtra("description",trending_challenges.getDescription2());
                    intent.putExtra("tags",trending_challenges.getTags2());
                    if (trending_challenges.getChallenge_title2().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry2());
                    intent.putExtra("view_count",trending_challenges.getView_count2());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });

            viewHolderForCat.joined_user3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",trending_challenges.getId3());
                    intent.putExtra("username",trending_challenges.getUsername3());
                    intent.putExtra("userimage",trending_challenges.getProfile_img3());
                    intent.putExtra("user_id",trending_challenges.getUser_id3());
                    intent.putExtra("image","");
                    intent.putExtra("likes",trending_challenges.getLike3());
                    intent.putExtra("comments",trending_challenges.getComments_count3());
                    intent.putExtra("title",trending_challenges.getTitle3());
                    intent.putExtra("description",trending_challenges.getDescription3());
                    intent.putExtra("tags",trending_challenges.getTags3());
                    if (trending_challenges.getChallenge_title3().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",trending_challenges.getCountry3());
                    intent.putExtra("view_count",trending_challenges.getView_count3());
                    intent.putExtra("join","join");
                    mcontext.startActivity(intent);
                }
            });





        }





        if (Integer.parseInt(trending_challenges.getSilver()) > Integer.parseInt(trending_challenges.getGold()) && Integer.parseInt(trending_challenges.getSilver())> Integer.parseInt(trending_challenges.getBronze())){

            viewHolderForCat.coin.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.bronzereading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getSilver());

            if (Integer.parseInt(trending_challenges.getGold()) > Integer.parseInt(trending_challenges.getBronze())){

                viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getGold());
                viewHolderForCat.goldreading.setImageResource(R.drawable.bronze_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getBronze());

            }
            else {
                viewHolderForCat.silverreading.setImageResource(R.drawable.bronze_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getBronze());
                viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());
            }
        }
        else if (Integer.parseInt(trending_challenges.getGold())>Integer.parseInt(trending_challenges.getSilver()) && Integer.parseInt(trending_challenges.getGold())> Integer.parseInt(trending_challenges.getBronze())){

            viewHolderForCat.coin.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.bronzereading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getGold());


            if (Integer.parseInt(trending_challenges.getSilver()) > Integer.parseInt(trending_challenges.getBronze())){

                viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

                viewHolderForCat.goldreading.setImageResource(R.drawable.bronze_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getBronze());

            }
            else {

                viewHolderForCat.silverreading.setImageResource(R.drawable.bronze_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getBronze());

                viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());

            }



        }
        else if (Integer.parseInt(trending_challenges.getBronze())>Integer.parseInt(trending_challenges.getSilver()) && Integer.parseInt(trending_challenges.getBronze())> Integer.parseInt(trending_challenges.getGold())){

            viewHolderForCat.coin.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getBronze());


            if (Integer.parseInt(trending_challenges.getSilver()) > Integer.parseInt(trending_challenges.getGold())){

                viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

                viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());

            }
            else {

                viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getGold());

                viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());

            }


        }
        else if (Integer.parseInt(trending_challenges.getGold())==Integer.parseInt(trending_challenges.getSilver()) &&Integer.parseInt(trending_challenges.getGold()) ==Integer.parseInt(trending_challenges.getBronze())){
            viewHolderForCat.coin.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getBronze());

            if (Integer.parseInt(trending_challenges.getSilver()) > Integer.parseInt(trending_challenges.getGold())){

                viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

                viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());

            }
            else {

                viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getGold());

                viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());

            }

        }

        else if (Integer.parseInt(trending_challenges.getGold())==Integer.parseInt(trending_challenges.getSilver()) &&
                Integer.parseInt(trending_challenges.getGold())!=Integer.parseInt(trending_challenges.getBronze())){

            viewHolderForCat.coin.setImageResource(R.drawable.gold_rank_icon);

            viewHolderForCat.bronzereading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getGold());


            viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

            viewHolderForCat.goldreading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getBronze());



        }
        else if (Integer.parseInt(trending_challenges.getGold())==Integer.parseInt(trending_challenges.getBronze()) &&
                Integer.parseInt(trending_challenges.getGold())!=Integer.parseInt(trending_challenges.getSilver())){

            viewHolderForCat.bronzereading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getGold());


            viewHolderForCat.silverreading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getBronze());

            viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());

        }

        else if (Integer.parseInt(trending_challenges.getSilver())==Integer.parseInt(trending_challenges.getBronze()) &&
                Integer.parseInt(trending_challenges.getSilver())!=Integer.parseInt(trending_challenges.getGold())){

            viewHolderForCat.bronzereading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getSilver());


            viewHolderForCat.silverreading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getBronze());

            viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());

        }
        else if (Integer.parseInt(trending_challenges.getSilver())==Integer.parseInt(trending_challenges.getGold()) &&
                Integer.parseInt(trending_challenges.getSilver())!=Integer.parseInt(trending_challenges.getBronze())){

            viewHolderForCat.bronzereading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getSilver());


            viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getGold());

            viewHolderForCat.goldreading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getBronze());

        }

        else if (Integer.parseInt(trending_challenges.getBronze())==Integer.parseInt(trending_challenges.getSilver()) &&
                Integer.parseInt(trending_challenges.getBronze())!=Integer.parseInt(trending_challenges.getGold())){

            viewHolderForCat.bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getBronze());


            viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

            viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());


        }
        else if (Integer.parseInt(trending_challenges.getBronze())==Integer.parseInt(trending_challenges.getGold()) &&
                Integer.parseInt(trending_challenges.getBronze())!=Integer.parseInt(trending_challenges.getSilver())){

            viewHolderForCat.bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getBronze());


            viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
            viewHolderForCat.secondtxt.setText(trending_challenges.getGold());

            viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
            viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());


        }

        else {

            viewHolderForCat.coin.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.bronzereading.setImageResource(R.drawable.bronze_rank_icon);
            viewHolderForCat.firsttxt.setText(trending_challenges.getBronze());

            if (Integer.parseInt(trending_challenges.getSilver()) > Integer.parseInt(trending_challenges.getGold())){

                viewHolderForCat.silverreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getSilver());

                viewHolderForCat.goldreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getGold());

            }
            else {

                viewHolderForCat.silverreading.setImageResource(R.drawable.gold_rank_icon);
                viewHolderForCat.secondtxt.setText(trending_challenges.getGold());

                viewHolderForCat.goldreading.setImageResource(R.drawable.silver_rank_icon);
                viewHolderForCat.thirdtxt.setText(trending_challenges.getSilver());

            }

        }


        //SETTING VIEW AND JOIN
        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");
        if (trending_challenges.getChallenge_title().equals("1-on-1 Challenge ") || trending_challenges.getChallenge_title().equals("Group Challenge")){

            if (trending_challenges.getJoinuser()!=null && trending_challenges.getJoinuser().equals("yes")){

                if(uid.equals(trending_challenges.getUser_id())){
                    viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                    viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                    viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

                }
                else if (trending_challenges.getAll_userid()!=null && trending_challenges.getAll_userid().equals("yes")){
                    viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                    viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                    viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

                }
                else {

                    if (Constant.isOnline(mcontext)){
                        viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_curve);
                        viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.JOIN));
                        viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.black));

                    }
                    else{
                        viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                        viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                        viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

                    }
                }
            }
            else {
                viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

            }
        }
        else {

            if(uid.equals(trending_challenges.getUser_id())){
                viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

            }
            else if (trending_challenges.getAll_userid()!=null && trending_challenges.getAll_userid().equals("yes")){
                viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

            }
            else {

                if (Constant.isOnline(mcontext)){
                    viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_curve);
                    viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.JOIN));
                    viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.black));

                }
                else{
                    viewHolderForCat.join.setBackgroundResource(R.drawable.joinbtn_redcurve);
                    viewHolderForCat.join.setText(mcontext.getResources().getString(R.string.View));
                    viewHolderForCat.join.setTextColor(mcontext.getResources().getColor(R.color.white));

                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return mtrend.size();
        //return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView username,usercountry,challengename,challengetxt,time,like,comment,views,peoplejoined,thirdtxt,secondtxt,firsttxt,notxt;//morenumber;
        RoundedImageView image,coin,userimage,joined_user1,joined_user2,joined_user3;//joined_user4;
        RelativeLayout opacitycover;//joined_more_opacity;
        LinearLayout likelayout,coinsreadinglayout;
        Button join;
        ImageView bronze,silver,gold,mediatype;
        ImageView maincoin,bronzereading,silverreading,goldreading;

        TextView fst,scnd,thrd,seeall;
        RelativeLayout medal1,medal2,medal3;



        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);
            notxt = itemView.findViewById(R.id.notxt);
            image = itemView.findViewById(R.id.image);
            coin = itemView.findViewById(R.id.coin);
            userimage = itemView.findViewById(R.id.userimage);
            opacitycover = itemView.findViewById(R.id.opacitycover);
            likelayout = itemView.findViewById(R.id.likelayout);
            joined_user1 = itemView.findViewById(R.id.joined_user1);
            joined_user2 = itemView.findViewById(R.id.joined_user2);
            joined_user3 = itemView.findViewById(R.id.joined_user3);
            mediatype = itemView.findViewById(R.id.mediatype);
            bronze = itemView.findViewById(R.id.bronze);
            silver = itemView.findViewById(R.id.silver);
            gold = itemView.findViewById(R.id.gold);
            username = itemView.findViewById(R.id.username);
            usercountry = itemView.findViewById(R.id.usercountry);
            challengename = itemView.findViewById(R.id.challengename);
            challengetxt = itemView.findViewById(R.id.challengetxt);
            time = itemView.findViewById(R.id.time);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            views = itemView.findViewById(R.id.views);
            join = itemView.findViewById(R.id.join);
            peoplejoined = itemView.findViewById(R.id.peoplejoined);
            bronzereading = itemView.findViewById(R.id.bronzereading);
            silverreading = itemView.findViewById(R.id.silverreading);
            goldreading = itemView.findViewById(R.id.goldreading);
            maincoin = itemView.findViewById(R.id.maincoin);
            coinsreadinglayout = itemView.findViewById(R.id.coinsreadinglayout);
            thirdtxt = itemView.findViewById(R.id.thirdtxt);
            secondtxt = itemView.findViewById(R.id.secondtxt);
            firsttxt = itemView.findViewById(R.id.firsttxt);

            fst = itemView.findViewById(R.id.fst);
            scnd = itemView.findViewById(R.id.scnd);
            thrd = itemView.findViewById(R.id.thrd);
            seeall = itemView.findViewById(R.id.seeall);
            medal1 = itemView.findViewById(R.id.medal1);
            medal2 = itemView.findViewById(R.id.medal2);
            medal3 = itemView.findViewById(R.id.medal3);

            username.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Bold.ttf"));
            usercountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            challengename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Bold.ttf"));
            challengetxt.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));
            time.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            like.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            comment.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            views.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));

            join.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Bold.ttf"));
            peoplejoined.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Medium.ttf"));

            fst.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            scnd.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            thrd.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            seeall.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));
            notxt.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        }

    }

    private void like(String challengeid, final String type, final Trending_Challenges trending_challenges, final int i){

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",uid);
        params.add("challenge_id",challengeid);
        params.add("type",type);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "like/like/add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("likeresp",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
                        Constant.LikeSuccessToast(mactivity,mcontext.getResources().getString(R.string.Liked));
                        updateLike(i,trending_challenges,type,"true");



                    }
                    else if(status.equals("false")){

                        updateLike(i,trending_challenges,type,"false");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("likerespF",response);

                }
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));
            }
        });

    }

    private void updateLike(final int adapterPosition,Trending_Challenges trending_challenges,String type,String status) {

        detail3(trending_challenges.getId(),trending_challenges,adapterPosition);

        if (status.equals("false")){

            trending_challenges.setLike_count(String.valueOf(Integer.parseInt(trending_challenges.getLike_count())-1));
            trending_challenges.setUser_like("0");
            if (type.equals("1")){
                trending_challenges.setUser_like_reward("");

            }
            else if (type.equals("2")){
                trending_challenges.setUser_like_reward("");

            }
            else if (type.equals("3")){
                trending_challenges.setUser_like_reward("");

            }

            notifyItemChanged(adapterPosition, "prelike");

        }
        else {
            if (!trending_challenges.getUser_like().equals("1")){
                trending_challenges.setLike_count(String.valueOf(Integer.parseInt(trending_challenges.getLike_count())+1));
                trending_challenges.setUser_like("1");
                if (type.equals("1")){
                    trending_challenges.setUser_like_reward("silver");
                    //trending_challenges.setSilver(String.valueOf(Integer.parseInt(trending_challenges.getSilver())+1));
                }
                else if (type.equals("2")){
                    trending_challenges.setUser_like_reward("gold");
                    //trending_challenges.setGold(String.valueOf(Integer.parseInt(trending_challenges.getGold())+1));

                }
                else if (type.equals("3")){
                    trending_challenges.setUser_like_reward("bronze");
                    // trending_challenges.setBronze(String.valueOf(Integer.parseInt(trending_challenges.getBronze())+1));

                }

                notifyItemChanged(adapterPosition, "prelike");
            }
            else {
                if (type.equals("1")){
                    trending_challenges.setUser_like_reward("silver");
                    //trending_challenges.setSilver(String.valueOf(Integer.parseInt(trending_challenges.getSilver())+1));

                }
                else if (type.equals("2")){
                    trending_challenges.setUser_like_reward("gold");
                    //trending_challenges.setGold(String.valueOf(Integer.parseInt(trending_challenges.getGold())+1));

                }
                else if (type.equals("3")){
                    trending_challenges.setUser_like_reward("bronze");
                    //trending_challenges.setBronze(String.valueOf(Integer.parseInt(trending_challenges.getBronze())+1));

                }
                notifyItemChanged(adapterPosition, "prelike");

            }
        }

    }

    private class  Comparor implements Comparator<Trending_Challenges> {

        @Override
        public int compare(Trending_Challenges trending_challenges, Trending_Challenges t1) {
            return t1.getId().compareTo(trending_challenges.getId());
        }
    }

    public void detail3(String id, final Trending_Challenges trending_challenges, final int pos)
    {
        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String u_id = preferences.getString(Constant.USER_ID,"");
        RequestParams params = new RequestParams();
        params.add("challenge_id",id);
        params.add("user_id",u_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASE_URL + "post/post/get_challege_detail?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String response = new String(responseBody);
                Log.e("responsedetail",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");


                    if (status.equals("true"))
                    {
                        JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                        JSONObject like_type_count = dataSet.getJSONObject("like_type_count");
                        String silver = like_type_count.getString("silver");
                        String gold = like_type_count.getString("gold");
                        String bronze = like_type_count.getString("bronze");

                        trending_challenges.setSilver(silver);
                        trending_challenges.setGold(gold);
                        trending_challenges.setBronze(bronze);

                        notifyItemChanged(pos, "prelike");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("responsedetailF", response);
                }
            }
        });



    }

    private void joinApi(final Trending_Challenges trending_challenges){

        mavibackground.setVisibility(View.VISIBLE);
        mavi.smoothToShow();

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",uid);
        params.add("challenge_id",trending_challenges.getId());
        params.add("accept_reject","1");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "post/post/join_challenge", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("joinres",res);

                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true") && errorMessage.equals("")){

                        Intent intent = new Intent(mcontext,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("from","challenge");
                        intent.putExtra("join","join");
                        intent.putExtra("cid",trending_challenges.getId());


                        if (trending_challenges.getChallenge_title().equals("Open Challenge")){

                            intent.putExtra("type","1");

                        }
                        else if (trending_challenges.getChallenge_title().equals("1-on-1 Challenge ")){

                            intent.putExtra("type","3");

                        }
                        else if (trending_challenges.getChallenge_title().equals("Self Challenge")){

                            intent.putExtra("type","4");

                        }
                        else if (trending_challenges.getChallenge_title().equals("Group Challenge")){

                            intent.putExtra("type","2");

                        }

                        mcontext.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("joinresF",res);
                }
                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));
            }
        });
    }

}