package com.bossble.bossble.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
/*
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Profile_attempted;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Fawad on 5/20/2020.
 */
//ProfileAttemptedAdapter

public class ProfileAttemptedAdapter extends RecyclerView.Adapter<ProfileAttemptedAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<Profile_attempted> mnames=new ArrayList<>();

    public ProfileAttemptedAdapter(Context mcontext , List<Profile_attempted> names){
        this.mcontext = mcontext;
        mnames=names;
    }


    @NonNull
    @Override
    public ProfileAttemptedAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_attempted_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileAttemptedAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        Collections.sort(mnames,new Comparor());
        final Profile_attempted profile_challenges = mnames.get(i);

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
                intent.putExtra("join","join");
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return mnames.size();
    }

    private class  Comparor implements Comparator<Profile_attempted> {


        @Override
        public int compare(Profile_attempted profile_challenges, Profile_attempted t1) {
            return profile_challenges.getId().compareTo(t1.getId());
        }
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView name;
        RoundedImageView image;
        ImageView mediatype;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            mediatype = itemView.findViewById(R.id.mediatype);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));

        }

    }

}