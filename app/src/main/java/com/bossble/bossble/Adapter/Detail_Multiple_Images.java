package com.bossble.bossble.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
/*
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
*/
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Details.FullImage_Activity;
import com.bossble.bossble.Details.VideoPlayer_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;


public class Detail_Multiple_Images extends PagerAdapter {

    Context mContext;
    ArrayList<String> mpaths=new ArrayList<>();
    LayoutInflater mLayoutInflater;
    Activity mactivity;

    String mcimage="";
    String muname="";
    String muimage="";
    String mlikes="";
    String mcomments_count="";
    String mctitle="";
    String mctype="";
    String mid="";
    String mcountry="";
    String mview_count="";
    String mmode;

    public Detail_Multiple_Images(Context context, ArrayList<String> paths,Activity activity,String cimage,String uname,String uimage,String likes,String comments_count,String ctitle,String ctype,String id,String country,String view_count, String mode) {
        mContext = context;
        mpaths=paths;
        mactivity=activity;

        mcimage=cimage;
        muname=uname;
        muimage = uimage;
        mlikes = likes;
        mcomments_count = comments_count;
        mctitle = ctitle;
        mctype = ctype;
        mid = id;
        mcountry=country;
        mview_count=view_count;
        mmode = mode;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mpaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.detail_multiple_images_layout, container, false);
        ImageView imageView = itemView.findViewById(R.id.image);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        mactivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        imageView.getLayoutParams().height = height/2;


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mpaths.get(0).contains(".mp4")){
                    if (mmode.equals("no")){
                        Intent intent = new Intent(mContext,VideoPlayer_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putStringArrayListExtra("image",mpaths);
                        intent.putExtra("username",muname);
                        intent.putExtra("userimage",muimage);
                        intent.putExtra("likes",mlikes);
                        intent.putExtra("comments",mcomments_count);
                        intent.putExtra("title",mctitle);
                        intent.putExtra("type",mctype);
                        intent.putExtra("id",mid);
                        intent.putExtra("country",mcountry);
                        intent.putExtra("views", mview_count);
                        intent.putExtra("mode", "mode");
                        mContext.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(mContext,VideoPlayer_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putStringArrayListExtra("image",mpaths);
                        intent.putExtra("username",muname);
                        intent.putExtra("userimage",muimage);
                        intent.putExtra("likes",mlikes);
                        intent.putExtra("comments",mcomments_count);
                        intent.putExtra("title",mctitle);
                        intent.putExtra("type",mctype);
                        intent.putExtra("id",mid);
                        intent.putExtra("country",mcountry);
                        intent.putExtra("views", mview_count);
                        mContext.startActivity(intent);
                    }
                }
                else if (mpaths.get(0).contains(".jpg") || mpaths.get(0).contains(".jpeg")){

                    if (mmode.equals("no")){

                        Intent intent = new Intent(mContext,FullImage_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putStringArrayListExtra("image",mpaths);
                        intent.putExtra("username",muname);
                        intent.putExtra("userimage",muimage);
                        intent.putExtra("likes",mlikes);
                        intent.putExtra("comments",mcomments_count);
                        intent.putExtra("title",mctitle);
                        intent.putExtra("type",mctype);
                        intent.putExtra("id",mid);
                        intent.putExtra("country",mcountry);
                        intent.putExtra("views", mview_count);
                        intent.putExtra("mode", "mode");
                        mContext.startActivity(intent);

                    }
                    else {

                        Intent intent = new Intent(mContext,FullImage_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putStringArrayListExtra("image",mpaths);
                        intent.putExtra("username",muname);
                        intent.putExtra("userimage",muimage);
                        intent.putExtra("likes",mlikes);
                        intent.putExtra("comments",mcomments_count);
                        intent.putExtra("title",mctitle);
                        intent.putExtra("type",mctype);
                        intent.putExtra("id",mid);
                        intent.putExtra("country",mcountry);
                        intent.putExtra("views", mview_count);
                        mContext.startActivity(intent);

                    }

                }
                else {
                    Toast.makeText(mContext,"No media available",Toast.LENGTH_SHORT).show();
                }



            }
        });


        RequestOptions options2 = new RequestOptions();
        options2.placeholder(R.drawable.bossble_placeholder_large);
        Glide.with(mContext)
                .load(mpaths.get(position))
                .apply(options2)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}