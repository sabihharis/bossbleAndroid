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
import com.bossble.bossble.Model.Comments;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Fawad on 6/6/2020.
 */


/////////////////////////////

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolderForCat> {

    private Context mcontext;
    List<Comments> mnames = new ArrayList<>();

    public CommentsAdapter(Context mcontext, ArrayList<Comments> names) {
        this.mcontext = mcontext;
        mnames = names;
    }


    @NonNull
    @Override
    public CommentsAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_itemlayout, viewGroup, false);
        ViewHolderForCat viewHolderForCat = new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final Comments comments = mnames.get(i);



        RequestOptions options2 = new RequestOptions();
        options2.centerCrop();
        options2.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(comments.getUserimage())
                .apply(options2)
                .into(viewHolderForCat.imgpeople);

        String username = comments.getUsername();
        if (username.equals("null") || username.equals(""))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(username);
        }

        String usercomment = comments.getUsercomment();
        if (usercomment.equals("null") || usercomment.equals(""))
        {
            viewHolderForCat.txtcomment.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtcomment.setText(usercomment);
        }


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



        TextView txtpeoplename,txtcomment,txtreply;
        RoundedImageView imgpeople;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcomment = itemView.findViewById(R.id.txtcomment);
            txtreply = itemView.findViewById(R.id.txtreply);
            imgpeople = itemView.findViewById(R.id.imgpeople);
            txtcomment.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));
            txtreply.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));

        }

    }
}


