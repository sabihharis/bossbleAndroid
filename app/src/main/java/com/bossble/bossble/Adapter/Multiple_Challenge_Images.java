package com.bossble.bossble.Adapter;

import android.content.Context;
import android.content.Intent;
/*
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Multiple_Challenge_Images extends RecyclerView.Adapter<Multiple_Challenge_Images.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<String> mnames=new ArrayList<>();

    EditText mtitle;
    EditText mdescription;
    EditText mtags;
    TextView mgotomap;
    String mtype="";
    String mlat_param="";
    String mlong_param="";
    Callback callback;


    public Multiple_Challenge_Images(Context mcontext , ArrayList<String> names, EditText title,EditText description,EditText tags,TextView gotomap,String type,String lat_param,String long_param,Callback callback){
        this.mcontext = mcontext;
        mnames=names;
        mtitle = title;
        mdescription = description;
        mtags = tags;
        mgotomap = gotomap;
        mtype = type;
        mlat_param = lat_param;
        mlong_param = long_param;
        this.callback=callback;

    }


    @NonNull
    @Override
    public Multiple_Challenge_Images.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiple_images_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Multiple_Challenge_Images.ViewHolderForCat viewHolderForCat, final int i) {

        RequestOptions options2 = new RequestOptions();
        options2.placeholder(R.drawable.bossble_placeholder_large);
        Glide.with(mcontext)
                .load(mnames.get(i))
                .apply(options2)
                .into(viewHolderForCat.image);

/*        if (mnames.size()==1){
            viewHolderForCat.remove.setVisibility(View.GONE);

        }*/

        viewHolderForCat.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String removedpath = mnames.get(i);
                mnames.remove(mnames.get(i));
                notifyDataSetChanged();
                callback.onRemoved(mnames,String.valueOf(mnames.size()),removedpath, String.valueOf(i));

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
                String maptext = mgotomap.getText().toString();

                Intent intent = new Intent(mcontext,Camera_Activity2.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",name);
                intent.putExtra("description",desc);
                intent.putExtra("hashtags",hash);
                intent.putStringArrayListExtra("list",mnames);
                intent.putExtra("type",mtype);
                intent.putExtra("from","challenge");

                intent.putExtra("maptext",maptext);
                intent.putExtra("latitude",mlat_param);
                intent.putExtra("longitude",mlong_param);
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

    public interface Callback{
        void onRemoved(ArrayList<String> imagelist,String listsize,String Pathremoved,String indexremoved);
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        RoundedImageView image;
        ImageView remove;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);

        }

    }

}