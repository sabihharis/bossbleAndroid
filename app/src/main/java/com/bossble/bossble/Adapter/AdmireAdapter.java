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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.People;
import com.bossble.bossble.Model.Search_people;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Fawad on 6/3/2020.
 */
//AdmireAdapter

public class AdmireAdapter extends RecyclerView.Adapter<AdmireAdapter.ViewHolderForCat> {

    private Context mcontext ;
    ArrayList<People> mnames=new ArrayList<>();



    public AdmireAdapter(Context mcontext , ArrayList<People> names){
        this.mcontext = mcontext;
        mnames=names;
    }


    @NonNull
    @Override
    public AdmireAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admire_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdmireAdapter.ViewHolderForCat viewHolderForCat, final int i) {

       /* viewHolderForCat.name.setText(mnames.get(i));

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });*/

        final People people = mnames.get(i);

        final String user_id = people.getUserid();
        String user_name = people.getUsername();
        if (user_name.equals("") || user_name.equals("null"))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(user_name);
        }

        String country = people.getUsercountry();
        if (country.equals("") | country.equals("null"))
        {
            viewHolderForCat.txtcountry.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtcountry.setText(country);
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(people.getUserimage())
                .apply(options)
                .into(viewHolderForCat.imgpeople);

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

                String u_id = pref.getString(Constant.USER_ID,"");
                if (u_id.equals(user_id))
                {
                    Intent intent = new Intent(mcontext, Personal_Profile_Activity.class);
                    intent.putExtra("user_id",people.getUserid());
                    mcontext.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(mcontext, Profile_of_others_Activity.class);
                    intent.putExtra("user_id",people.getUserid());
                    mcontext.startActivity(intent);
                }



            }
        });


    }

    @Override
    public int getItemCount() {

        return mnames.size();

    }

    public void updateList(ArrayList<People> list) {
        mnames = list;
        notifyDataSetChanged();
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
          //  name = itemView.findViewById(R.id.name);
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
//            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtcountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));



        }

    }

}