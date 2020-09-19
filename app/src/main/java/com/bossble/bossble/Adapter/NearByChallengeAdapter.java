package com.bossble.bossble.Adapter;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.NearBy_Challenges;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class NearByChallengeAdapter extends RecyclerView.Adapter<NearByChallengeAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<NearBy_Challenges> mnames=new ArrayList<>();

    public NearByChallengeAdapter(Context mcontext , List<NearBy_Challenges> names){
        this.mcontext = mcontext;
        mnames=names;
    }


    @NonNull
    @Override
    public NearByChallengeAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nearbychallenge_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final NearByChallengeAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");

        final NearBy_Challenges nearBy_challenges = mnames.get(i);


        viewHolderForCat.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = nearBy_challenges.getUser_id();
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




        RequestOptions options2 = new RequestOptions();
        options2.centerCrop();
        options2.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(nearBy_challenges.getProfile_image())
                .apply(options2)
                .into(viewHolderForCat.userimage);


        if (!nearBy_challenges.getDescription_background().equals("")){
            if (nearBy_challenges.getTitle().length()>15){
                String name = nearBy_challenges.getTitle().substring(0,13);
                viewHolderForCat.name.setText(name+"..");
            }
            else {
                viewHolderForCat.name.setText(nearBy_challenges.getTitle());
            }
        }
        else {
            viewHolderForCat.name.setText(nearBy_challenges.getTitle());
        }

        if (nearBy_challenges.getAddress().equals("") || nearBy_challenges.getAddress().isEmpty() || nearBy_challenges.getAddress().equals("null")){

            viewHolderForCat.locationname.setText("N/A");
        }
        else {
            if (nearBy_challenges.getAddress().length()>25){
                String address = nearBy_challenges.getAddress().substring(0,23);
                viewHolderForCat.locationname.setText(address+"..");
            }
            else {
                viewHolderForCat.locationname.setText(nearBy_challenges.getAddress());
            }
        }

        if (!nearBy_challenges.getImage().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(nearBy_challenges.getImage())
                    .apply(options)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);

        }
        else if (!nearBy_challenges.getVideo().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(nearBy_challenges.getVideo())
                    .apply(options)
                    .into(viewHolderForCat.image);

            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_video);

        }
        else if (!nearBy_challenges.getDescription_background().equals("")){

            viewHolderForCat.image.setImageBitmap(null);
            Drawable mDrawable = ContextCompat.getDrawable(mcontext, R.drawable.random_relative_background);
            mDrawable.setTint(Color.parseColor(nearBy_challenges.getDescription_background()));
            viewHolderForCat.image.setBackground(mDrawable);

            if (!nearBy_challenges.getDescription_fonts().equals("")){
                viewHolderForCat.name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/"+nearBy_challenges.getDescription_fonts()));
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
                intent.putExtra("id",nearBy_challenges.getId());
                intent.putExtra("username",nearBy_challenges.getUsername());
                intent.putExtra("userimage",nearBy_challenges.getProfile_image());
                intent.putExtra("user_id",nearBy_challenges.getUser_id());

                if (!nearBy_challenges.getImage().equals("")){
                    intent.putExtra("image",nearBy_challenges.getImage());

                }
                else if (!nearBy_challenges.getVideo().equals("")){
                    intent.putExtra("image",nearBy_challenges.getVideo());
                }
                else if (!nearBy_challenges.getDescription_background().equals("")){
                    intent.putExtra("image",nearBy_challenges.getDescription_background());
                }
                else {
                    intent.putExtra("image","");
                }

                intent.putExtra("fonts",nearBy_challenges.getDescription_fonts());



                intent.putExtra("likes",nearBy_challenges.getLike_count());
                intent.putExtra("comments",nearBy_challenges.getComments_count());
                intent.putExtra("title",nearBy_challenges.getTitle());
                intent.putExtra("description",nearBy_challenges.getDescritpion());
                intent.putExtra("tags",nearBy_challenges.getTags());
                intent.putExtra("type","nearbychallenge");
                intent.putExtra("country",nearBy_challenges.getCountry());
                intent.putExtra("view_count",nearBy_challenges.getView_count());
                mcontext.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        if (mnames.size()>5){
            return 5;
        }
        else {
            return mnames.size();

        }
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


        TextView name,locationname;
        RoundedImageView image,userimage;
        ImageView mediatype;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            userimage = itemView.findViewById(R.id.userimage);
            mediatype = itemView.findViewById(R.id.mediatype);
            locationname = itemView.findViewById(R.id.locationname);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));
            locationname.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        }

    }

}