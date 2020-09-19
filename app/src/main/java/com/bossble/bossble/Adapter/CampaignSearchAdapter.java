package com.bossble.bossble.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.CampaignSearch;
import com.bossble.bossble.Model.ChallengeSearch;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CampaignSearchAdapter extends RecyclerView.Adapter<CampaignSearchAdapter.ViewHolderForCat> {

    private Context mcontext ;
    ArrayList<CampaignSearch> mnames=new ArrayList<>();
    String identifier="";

    public CampaignSearchAdapter(Context mcontext , ArrayList<CampaignSearch> names){
        this.mcontext = mcontext;
        mnames=names;

    }


    @NonNull
    @Override
    public CampaignSearchAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challenge_search_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final CampaignSearchAdapter.ViewHolderForCat viewHolderForCat, final int i) {




        final CampaignSearch challengeSearch = mnames.get(i);

        String challenge_name = challengeSearch.getTitle();
        if (challenge_name.equals("") || challenge_name.equals("null"))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(challenge_name);
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(challengeSearch.getImage())
                .apply(options)
                .into(viewHolderForCat.imgpeople);


        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext,Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",challengeSearch.getCahllenge_id());
                intent.putExtra("username",challengeSearch.getUsername());
                intent.putExtra("userimage",challengeSearch.getProfile_image());
                intent.putExtra("user_id",challengeSearch.getUser_id());
                intent.putExtra("image","");
                intent.putExtra("likes",challengeSearch.getLike_count());
                intent.putExtra("comments",challengeSearch.getComments_count());
                intent.putExtra("title",challengeSearch.getTitle());
                intent.putExtra("description",challengeSearch.getDescription());
                intent.putExtra("tags",challengeSearch.getTags());
                if (challengeSearch.getChallenge_title().equals("Create Campaign")){
                    intent.putExtra("type","campaign");
                }
                else{
                    intent.putExtra("type","challenge");
                }
                intent.putExtra("country",challengeSearch.getCountry());
                intent.putExtra("view_count",challengeSearch.getView_count());
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
        TextView txtpeoplename,txtcountry;
        RelativeLayout r1,r2;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);


            imgpeople = itemView.findViewById(R.id.imgpeople);
            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcountry = itemView.findViewById(R.id.txtcountry);
            name = itemView.findViewById(R.id.name);
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtcountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            if (identifier.equals("4"))
            {
                Log.e("logidentifier",identifier);

                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
            }


        }

    }

   /* public void updateList(ArrayList<String> list){
        mnames = list;
        notifyDataSetChanged();
    }*/
}
