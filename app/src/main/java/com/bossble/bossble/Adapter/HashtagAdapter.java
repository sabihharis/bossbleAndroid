package com.bossble.bossble.Adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Hashtags;
import com.bossble.bossble.Model.Search_people;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Fawad on 7/3/2020.
 */

//HashtagAdapter



public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<Hashtags> mnames = new ArrayList<>();
    String identifier = "";

    public HashtagAdapter(Context mcontext, ArrayList<Hashtags> names) {
        this.mcontext = mcontext;
        mnames = names;

    }


    @NonNull
    @Override
    public HashtagAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hashtags_itemlayout, viewGroup, false);
        ViewHolderForCat viewHolderForCat = new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final HashtagAdapter.ViewHolderForCat viewHolderForCat, final int i) {

      /*  viewHolderForCat.name.setText(mnames.get(i));

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });*/



        final Hashtags hashtags = mnames.get(i);

        final String user_id = hashtags.getUser_id();
        final String user_name = hashtags.getUser_name();
        final String user_image = hashtags.getUser_image();
        String challenge_id = hashtags.getChallenge_id();
        final String challenge_title = hashtags.getChallenge_title();
        final String challenge_desccription = hashtags.getChallenge_desccription();
        final String tag = hashtags.getTag();
        final String challenge_tid = hashtags.getChallenge_tid();
        final String comment_count = hashtags.getComment_count();
        final String like_count = hashtags.getLike_count();
        String challenge_image = hashtags.getChallenge_image();








        if (challenge_title.equals("") || challenge_title.equals("null"))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(challenge_title);
        }


        if (tag.equals("") | tag.equals("null"))
        {
            viewHolderForCat.txthashtags.setText("N/A");
        }
        else
        {
            viewHolderForCat.txthashtags.setText(tag);
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.bossble_placeholder_large);
        Glide.with(mcontext)
                .load(hashtags.getChallenge_image())
                .apply(options)
                .into(viewHolderForCat.imgpeople);

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext,Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",hashtags.getChallenge_id());
                intent.putExtra("username",hashtags.getUser_name());
                intent.putExtra("userimage",user_image);
                intent.putExtra("user_id",user_id);
                intent.putExtra("image","");
                intent.putExtra("likes",like_count);
                intent.putExtra("comments",comment_count);
                intent.putExtra("title",challenge_title);
                intent.putExtra("description",challenge_desccription);
                intent.putExtra("tags",tag);

                Log.e("sloguserimage",hashtags.getUser_name());


                if (challenge_tid.equals("5")){
                    intent.putExtra("type","campaign");
                }
                else{
                    intent.putExtra("type","challenge");
                }
                intent.putExtra("country",hashtags.getCountry());
                intent.putExtra("view_count",hashtags.getView_cunt());
                mcontext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return mnames.size();

    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView name;
        RoundedImageView imgpeople;
        TextView txtpeoplename, txtcountry,txthashtags;
        RelativeLayout r1, r2;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);


            imgpeople = itemView.findViewById(R.id.imgpeople);
            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcountry = itemView.findViewById(R.id.txtcountry);
            txthashtags = itemView.findViewById(R.id.txthashtags);
            name = itemView.findViewById(R.id.name);
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Light.ttf"));
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Light.ttf"));
            txtcountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Light.ttf"));
            if (identifier.equals("4")) {
                Log.e("logidentifier", identifier);

                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
            }


        }

    }

    public void updateList(ArrayList<Hashtags> list) {
        mnames = list;
        notifyDataSetChanged();
    }
}
