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

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Campaigns;
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

public class ShowAllCampaigns extends RecyclerView.Adapter<ShowAllCampaigns.ViewHolderForCat> {

    private Context mcontext ;
    List<Campaigns> mnames=new ArrayList<>();
    String imgfwd="";

    public ShowAllCampaigns(Context mcontext , List<Campaigns> names){
        this.mcontext = mcontext;
        mnames=names;
    }


    @NonNull
    @Override
    public ShowAllCampaigns.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.showall_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowAllCampaigns.ViewHolderForCat viewHolderForCat, final int i) {

        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");

        final Campaigns campaigns = mnames.get(i);


        viewHolderForCat.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = campaigns.getUser_id();
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
                .load(campaigns.getProfile_image())
                .apply(options2)
                .into(viewHolderForCat.userimage);

        if (!campaigns.getDescription_background().equals("")){
            if (campaigns.getTitle().length()>15){
                String name = campaigns.getTitle().substring(0,13);
                viewHolderForCat.name.setText(name+"..");
            }
            else {
                viewHolderForCat.name.setText(campaigns.getTitle());
            }
        }
        else {
            viewHolderForCat.name.setText(campaigns.getTitle());
        }

        viewHolderForCat.description.setText("Created by "+ campaigns.getUsername());



        if (!campaigns.getImage().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(campaigns.getImage())
                    .apply(options)
                    .into(viewHolderForCat.image);
            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);
        }
        else if (!campaigns.getVideo().equals("")){
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(campaigns.getVideo())
                    .apply(options)
                    .into(viewHolderForCat.image);
            viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_video);

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
                intent.putExtra("id",campaigns.getId());
                intent.putExtra("username",campaigns.getUsername());
                intent.putExtra("userimage",campaigns.getProfile_image());
                intent.putExtra("user_id",campaigns.getUser_id());

                if (!campaigns.getImage().equals("")){
                    intent.putExtra("image",campaigns.getImage());

                }
                else if (!campaigns.getVideo().equals("")){
                    intent.putExtra("image",campaigns.getVideo());
                }
                else if (!campaigns.getDescription_background().equals("")){
                    intent.putExtra("image",campaigns.getDescription_background());
                }
                else {
                    intent.putExtra("image","");
                }

                intent.putExtra("fonts",campaigns.getDescription_fonts());

                //intent.putStringArrayListExtra("imagelist",campaigns.getCampaign_images());


                intent.putExtra("likes",campaigns.getLike_count());
                intent.putExtra("comments",campaigns.getComments_count());
                intent.putExtra("title",campaigns.getTitle());
                intent.putExtra("description",campaigns.getDescritpion());
                intent.putExtra("tags",campaigns.getTags());
                intent.putExtra("type","campaign");
                intent.putExtra("country",campaigns.getCountry());
                intent.putExtra("view_count",campaigns.getView_count());
                mcontext.startActivity(intent);
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


        TextView name,description;
        RoundedImageView image,userimage;
        ImageView mediatype;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            userimage = itemView.findViewById(R.id.userimage);
            mediatype = itemView.findViewById(R.id.mediatype);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));
            description.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Regular.ttf"));

        }

    }

}
