package com.bossble.bossble.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
/*
import android.support.v4.view.PagerAdapter;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sokolov.androidsizes.ISize;
import com.sokolov.androidsizes.SizeFromImage;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import androidx.viewpager.widget.PagerAdapter;
import id.zelory.compressor.Compressor;

public class Multiple_ViewPager_Adapter extends PagerAdapter {

    Context mContext;
    ArrayList<String> mpaths=new ArrayList<>();
    ArrayList<String> Mmultiples=new ArrayList<>();
    LayoutInflater mLayoutInflater;
    ImageView imageView;
    int mcurrent = 0;
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    public Multiple_ViewPager_Adapter(Context context, ArrayList<String> paths,ArrayList<String> multiples,int current) {
        mContext = context;
        mpaths=paths;
        Mmultiples = multiples;
        mcurrent = current;
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
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_itemlayout, container, false);
        imageView = itemView.findViewById(R.id.image);


        File f = new File(mpaths.get(position));

            final File newfile = new Compressor.Builder(mContext)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(70)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build()
                    .compressToFile(f);

            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

        if (Mmultiples.get(position).equals("dummy")){

            RequestOptions options2 = new RequestOptions();
            options2.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(mContext)
                    .load(bitmap)
                    .apply(options2)
                    .into(imageView);
        }
        else {

            if (Mmultiples.get(position).contains("_1")) {
                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(bitmap)
                        .apply(options2)
                        .into(imageView);

            }
            else if (Mmultiples.get(position).contains("_2")) {

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                Bitmap singleCamBitmap = myFilter1.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);

            }
            else if (Mmultiples.get(position).contains("_3")) {

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                Bitmap singleCamBitmap = myFilter2.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_4")) {

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                Bitmap singleCamBitmap = myFilter3.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_5")) {

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                Bitmap singleCamBitmap = myFilter4.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_6")) {

                Filter haan = FilterPack.getMarsFilter(mContext);
                Bitmap singleCamBitmap = haan.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_7")) {

                Filter adele = FilterPack.getAdeleFilter(mContext);
                Bitmap singleCamBitmap = adele.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_8")) {

                Filter cruz = FilterPack.getCruzFilter(mContext);
                Bitmap singleCamBitmap = cruz.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);
            }
            else if (Mmultiples.get(position).contains("_9")) {

                Filter metropolis = FilterPack.getMetropolis(mContext);
                Bitmap singleCamBitmap = metropolis.processFilter(bitmap);

                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(mContext)
                        .load(singleCamBitmap)
                        .apply(options2)
                        .into(imageView);

            }
        }


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}