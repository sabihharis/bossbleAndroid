package com.bossble.bossble.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
/*
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Model.People;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Fawad on 5/22/2020.
 */
//PeopleAdapter

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<People> mnames=new ArrayList<>();
    String mid="";
    TextView mdone;
    Activity mactivity;
    String radioname ="";
    String radioid ="";

    Set<String> idset = new HashSet<String>();
    Set<String> nameset = new HashSet<String>();
    ArrayList<String> grp_ppl_name = new ArrayList<>();
    ArrayList<String> grp_ppl_id = new ArrayList<>();



    public PeopleAdapter(Context mcontext , ArrayList<People> names, String id, TextView done, Activity activity){
        this.mcontext = mcontext;
        mnames=names;
        mid=id;
        mdone=done;
        mactivity=activity;
    }


    @NonNull
    @Override
    public PeopleAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.people_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final PeopleAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        if (mid.equals("3")){
            viewHolderForCat.rdpeople.setVisibility(View.VISIBLE);
            mdone.setVisibility(View.GONE);

        } //one one one
        else {
            viewHolderForCat.chkpeople.setVisibility(View.VISIBLE);

        } //group challenge


        final People people = mnames.get(i);

        String user_id = people.getUserid();
        String user_name = people.getUsername();
        if (user_name.equals("null") || user_name.equals(""))
        {
            viewHolderForCat.txtpeoplename.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtpeoplename.setText(user_name);
        }

        String user_country = people.getUsercountry();
        if (user_country.equals("null") || user_country.equals(""))
        {
            viewHolderForCat.txtcountry.setText("N/A");
        }
        else
        {
            viewHolderForCat.txtcountry.setText(user_country);
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(people.getUserimage())
                .apply(options)
                .into(viewHolderForCat.imgpeople);


        viewHolderForCat.rdpeople.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    radioname = people.getUsername();
                    radioid = people.getUserid();
                    SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
                    if (!radioid.equals("")){
                        preferences.edit().putString("id",radioid).commit();
                        preferences.edit().putString("name",radioname).commit();
                    }
                    mactivity.onBackPressed();

                }
                else {
                    radioname = "";
                    radioid = "";
                }
            }
        });



        viewHolderForCat.chkpeople.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    grp_ppl_id.add(people.getUserid());
                    grp_ppl_name.add("@"+people.getUsername());

                    idset.addAll(grp_ppl_id);
                    nameset.addAll(grp_ppl_name);

                }
                else {
                    grp_ppl_id.remove(people.getUserid());
                    grp_ppl_name.remove("@"+people.getUsername());

                    idset.remove(people.getUserid());
                    nameset.remove("@"+people.getUsername());

                }
            }
        });


        mdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);

                    preferences.edit().putStringSet("idset",idset).commit();
                    preferences.edit().putStringSet("nameset",nameset).commit();
                    mactivity.onBackPressed();

            }
        });






       /* viewHolderForCat.txtpeoplename.setText(mnames.get(i));

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
*/
    }
    @Override
    public int getItemCount() {

        return mnames.size();

    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {

        RoundedImageView imgpeople;
        TextView txtpeoplename,txtcountry;
        RadioButton rdpeople;
        CheckBox chkpeople;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            imgpeople = itemView.findViewById(R.id.imgpeople);
            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcountry = itemView.findViewById(R.id.txtcountry);
            rdpeople = itemView.findViewById(R.id.rdpeople);
            chkpeople = itemView.findViewById(R.id.checkpeople);
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            txtcountry.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));

        }

    }

    public void updateList(ArrayList<People> list){
        mnames = list;
        notifyDataSetChanged();
    }
}
