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
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<Search_people> mnames=new ArrayList<>();
    String identifier="";

    public SearchAdapter(Context mcontext , ArrayList<Search_people> names,String midentifier){
        this.mcontext = mcontext;
        mnames=names;
        identifier = midentifier;
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolderForCat viewHolderForCat, final int i) {

       /* viewHolderForCat.name.setText(mnames.get(i));

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });*/



       final Search_people search_people = mnames.get(i);

       final String user_id = search_people.getUser_id();
       String user_name = search_people.getUser_name();
       if (user_name.equals("") || user_name.equals("null"))
       {
           viewHolderForCat.txtpeoplename.setText("N/A");
       }
       else
       {
           viewHolderForCat.txtpeoplename.setText(user_name);
       }

       String country = search_people.getCountry();
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
                .load(search_people.getImage())
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
                    intent.putExtra("user_id",search_people.getUser_id());
                    mcontext.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(mcontext, Profile_of_others_Activity.class);
                    intent.putExtra("user_id",search_people.getUser_id());
                    mcontext.startActivity(intent);
                }



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

    public void updateList(ArrayList<Search_people> list){
        mnames = list;
        notifyDataSetChanged();
    }
}