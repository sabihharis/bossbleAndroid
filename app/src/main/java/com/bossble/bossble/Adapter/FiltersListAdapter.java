package com.bossble.bossble.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class FiltersListAdapter extends RecyclerView.Adapter<FiltersListAdapter.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<String> mnames=new ArrayList<>();
    String imgfwd="";
    int a=0;
    private int lastPosition = -1;

    public FiltersListAdapter(Context mcontext , ArrayList<String> names){
        this.mcontext = mcontext;
        mnames=names;
    }


    @NonNull
    @Override
    public FiltersListAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filters_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final FiltersListAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        Collections.reverse(mnames);

        viewHolderForCat.name.setText(mnames.get(i));

        viewHolderForCat.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (i > lastPosition){

            Drawable d = viewHolderForCat.image.getDrawable();
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            bitmap = bitmap.copy( Bitmap.Config.ARGB_8888 , true);

            if (mnames.get(i).equals("Normal")){

                viewHolderForCat.image.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.joined_04));

            }
            else if (mnames.get(i).equals("Struck")){

                Filter clarendon = FilterPack.getAweStruckVibeFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));

            }
            else if (mnames.get(i).equals("Clarendon")){
                Filter clarendon = FilterPack.getClarendon(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("OldMan")){
                Filter clarendon = FilterPack.getOldManFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Mars")){
                Filter clarendon = FilterPack.getMarsFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Rise")){
                Filter clarendon = FilterPack.getRiseFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("April")){
                Filter clarendon = FilterPack.getAprilFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Amazon")){
                Filter clarendon = FilterPack.getAmazonFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Starlit")){
                Filter clarendon = FilterPack.getStarLitFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Whisper")){
                Filter clarendon = FilterPack.getNightWhisperFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Lime")){
                Filter clarendon = FilterPack.getLimeStutterFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Haan")){
                Filter clarendon = FilterPack.getHaanFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Bluemess")){
                Filter clarendon = FilterPack.getBlueMessFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Adele")){
                Filter clarendon = FilterPack.getAdeleFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Cruz")){
                Filter clarendon = FilterPack.getCruzFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Metropolis")){
                Filter clarendon = FilterPack.getMetropolis(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));
            }
            else if (mnames.get(i).equals("Audrey")){
                Filter clarendon = FilterPack.getAudreyFilter(mcontext);
                viewHolderForCat.image.setImageBitmap(clarendon.processFilter(bitmap));

               // a=1;
            }
            //a=1;

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


        TextView name;
        ImageView image;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Medium.ttf"));

        }

    }

}