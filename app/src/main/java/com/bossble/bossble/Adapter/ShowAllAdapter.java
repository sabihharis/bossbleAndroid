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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolderForCat> {

    private Context mcontext ;

    ArrayList<String> musernamelist=new ArrayList<>();
    ArrayList<String> muserimagelist=new ArrayList<>();
    ArrayList<String> mcimagelist=new ArrayList<>();
    ArrayList<String> mcnamelist=new ArrayList<>();
    ArrayList<String> mcidlist=new ArrayList<>();
    ArrayList<String> mcdescriptionlist=new ArrayList<>();
    ArrayList<String> mlikeslist=new ArrayList<>();
    ArrayList<String> mcommentslist=new ArrayList<>();
    ArrayList<String> mtagslist=new ArrayList<>();
    ArrayList<String> mfontslist=new ArrayList<>();
    String mtypes;
    String mcountry;
    ArrayList<String> mviewslist = new ArrayList<>();

    public ShowAllAdapter(Context mcontext , ArrayList<String> usernamelist, ArrayList<String> userimagelist, ArrayList<String> cimagelist, ArrayList<String> cnamelist,
                          ArrayList<String> cidlist, ArrayList<String> cdescriptionlist, ArrayList<String> likeslist, ArrayList<String> commentslist, ArrayList<String> tagslist,
                          String types, ArrayList<String> fontslist,String country,ArrayList<String> viewslist){
        this.mcontext = mcontext;
        musernamelist=usernamelist;
        muserimagelist=userimagelist;
        mcimagelist=cimagelist;
        mcnamelist=cnamelist;
        mcidlist=cidlist;
        mcdescriptionlist=cdescriptionlist;
        mlikeslist=likeslist;
        mcommentslist=commentslist;
        mtagslist=tagslist;
        mtypes=types;
        mfontslist = fontslist;
        mcountry = country;
        mviewslist = viewslist;
    }
    @NonNull
    @Override
    public ShowAllAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.showall_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowAllAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        RequestOptions options2 = new RequestOptions();
        options2.centerCrop();
        options2.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(muserimagelist.get(i))
                .apply(options2)
                .into(viewHolderForCat.userimage);

        if (mcimagelist.get(i).contains(".mp4") || mcimagelist.get(i).contains(".jpg") || mcimagelist.get(i).contains(".jpeg")){

            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mcontext)
                    .load(mcimagelist.get(i))
                    .apply(options)
                    .into(viewHolderForCat.image);

            if (mcimagelist.get(i).equals(".mp4")){
                viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_video);
            }
            else {
                viewHolderForCat.mediatype.setImageResource(R.drawable.ic_post_image);
            }

        }
        else if (mcimagelist.get(i).contains("#")){

            viewHolderForCat.image.setImageBitmap(null);
            Drawable mDrawable = ContextCompat.getDrawable(mcontext, R.drawable.random_relative_background);
            mDrawable.setTint(Color.parseColor(mcimagelist.get(i)));
            viewHolderForCat.image.setBackground(mDrawable);

            if (!mfontslist.get(i).equals("")){
                viewHolderForCat.name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/"+mfontslist.get(i)));
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


        if (mcnamelist.get(i).length()>22){

            String name2 = mcnamelist.get(i).substring(0,20);
            viewHolderForCat.name.setText(name2+"..");
        }
        else {
            viewHolderForCat.name.setText(mcnamelist.get(i));
        }

        viewHolderForCat.description.setText(mcontext.getResources().getString(R.string.Createdby)+" "+musernamelist.get(i));






        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",mcidlist.get(i));
                intent.putExtra("username",musernamelist.get(i));
                intent.putExtra("userimage",muserimagelist.get(i));

                intent.putExtra("image",mcimagelist.get(i));
                intent.putExtra("fonts",mfontslist.get(i));

                intent.putExtra("likes",mlikeslist.get(i));
                intent.putExtra("comments",mcommentslist.get(i));
                intent.putExtra("title",mcnamelist.get(i));
                intent.putExtra("description",mcdescriptionlist.get(i));
                intent.putExtra("tags",mtagslist.get(i));
                intent.putExtra("type",mtypes);
                intent.putExtra("country",mcountry);
                intent.putExtra("view_count",mviewslist.get(i));

                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
            return mcnamelist.size();
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