package com.bossble.bossble.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
/*
import android.support.v4.view.PagerAdapter;
*/
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bossble.bossble.Details.FullImage_Activity;
import com.bossble.bossble.Details.VideoPlayer_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class Full_Multiple_Image extends PagerAdapter {

    Context mContext;
    ArrayList<String> mpaths=new ArrayList<>();
    LayoutInflater mLayoutInflater;
    Activity mactivity;


    public Full_Multiple_Image(Context context, ArrayList<String> paths,Activity activity) {
        mContext = context;
        mpaths=paths;
        mactivity=activity;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mpaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.full_multiple_image_layout, container, false);
        ImageView imageView = itemView.findViewById(R.id.image);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mactivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        imageView.getLayoutParams().height = height/2;

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
        container.removeView((ImageView) object);
    }
}