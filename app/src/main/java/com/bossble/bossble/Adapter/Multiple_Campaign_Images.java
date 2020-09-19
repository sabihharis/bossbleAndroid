package com.bossble.bossble.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
/*
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
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

import javax.security.auth.callback.Callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class Multiple_Campaign_Images extends RecyclerView.Adapter<Multiple_Campaign_Images.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<String> mnames=new ArrayList<>();
    EditText mtitle;
    EditText mdescription;
    EditText mtags;
    Callback callback;

    public Multiple_Campaign_Images(Context mcontext , ArrayList<String> names, EditText title,EditText description,EditText tags,Callback callback){
        this.mcontext = mcontext;
        mnames=names;
        mtitle = title;
        mdescription = description;
        mtags = tags;
        this.callback=callback;

    }


    @NonNull
    @Override
    public Multiple_Campaign_Images.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiple_images_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Multiple_Campaign_Images.ViewHolderForCat viewHolderForCat, final int i) {

        RequestOptions options2 = new RequestOptions();
        options2.placeholder(R.drawable.bossble_placeholder_large);
        Glide.with(mcontext)
                .load(mnames.get(i))
                .apply(options2)
                .into(viewHolderForCat.image);



/*
        if (mnames.size()==1){
            viewHolderForCat.remove.setVisibility(View.GONE);

        }
*/

        viewHolderForCat.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String removedpath = mnames.get(i);
                mnames.remove(mnames.get(i));
                notifyDataSetChanged();
                callback.onRemoved(mnames,String.valueOf(mnames.size()),removedpath,String.valueOf(i));


                /*if (mnames.size()==1){
                    viewHolderForCat.remove.setVisibility(View.GONE);
                }*/


            }
        });

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mtitle.getText().toString();
                String desc = mdescription.getText().toString();
                String hash = mtags.getText().toString();
                Intent intent = new Intent(mcontext,Camera_Activity2.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",name);
                intent.putExtra("description",desc);
                intent.putExtra("hashtags",hash);
                intent.putStringArrayListExtra("list",mnames);
                intent.putExtra("from","campaign");
                intent.putExtra("pos",String.valueOf(i));
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


        RoundedImageView image;
        ImageView remove;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);

        }

    }

    public interface Callback{
        void onRemoved(ArrayList<String> imagelist,String listsize,String Pathremoved,String indexremoved);
    }


}