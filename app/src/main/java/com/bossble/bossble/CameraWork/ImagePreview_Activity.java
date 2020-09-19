package com.bossble.bossble.CameraWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
/*
import androidx.core.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bossble.bossble.Adapter.FiltersListAdapter;
import com.bossble.bossble.Adapter.Multiple_ViewPager_Adapter;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crystalviewpager.widgets.CrystalViewPager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cz.msebera.android.httpclient.Header;
import id.zelory.compressor.Compressor;
import me.relex.circleindicator.CircleIndicator;

public class ImagePreview_Activity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    ImageView image,cross,next;
    TextView heading;
    String path="";
    String gallerypath="";
    String from="";
    ArrayList<String> paths = new ArrayList<>();

    CrystalViewPager viewPager;
    CircleIndicator indicator;
    Multiple_ViewPager_Adapter adapter;
    ImageView moreimages;

    String join="";
    String type="";
    String cid="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    ArrayList<File> files = new ArrayList<>();

    RecyclerView filtersRV;
    FiltersListAdapter filtersListAdapter;
    RoundedImageView image1,image2,image3,image4,image5,image6,image7,image8,image9;

    int camera_without_join = 0;
    int camera_without_join_edit = 0;

    int multiple_without_join = 0;
    int multiple_current_position = 0;
    ArrayList<String> multiple_edit_list = new ArrayList<>();

    int camera_with_join=0;
    int camera_with_join_edit=0;
    int multiple_with_join=0;
    Bitmap singleCamBitmap;

    LinearLayout filterslayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview_);

        filterslayout = findViewById(R.id.filterslayout);
        image = findViewById(R.id.image);
        cross = findViewById(R.id.cross);
        next = findViewById(R.id.next);
        heading = findViewById(R.id.heading);
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        moreimages = findViewById(R.id.moreimages);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        filtersRV = findViewById(R.id.filtersRV);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        //setupFiltersList();

        //setFilterListeners();


        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-MediumItalic.ttf"));


        if (getIntent().hasExtra("galleryimage")){

            //with join
            if (getIntent().hasExtra("list") && getIntent().hasExtra("join")){

                multiple_without_join =0;
                camera_without_join=0;
                camera_with_join=0;
                multiple_with_join =1;


                paths.clear();
                paths = getIntent().getStringArrayListExtra("galleryimage");

                multiple_edit_list.clear();
                for (int i=0; i<paths.size(); i++){
                    multiple_edit_list.add("dummy");
                }


                adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,0);
                viewPager.setAdapter(adapter);
                indicator.setViewPager(viewPager);
                viewPager.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);



                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("curremPos", String.valueOf(position));
                        multiple_current_position = position;

                        if (multiple_edit_list.get(position).equals("dummy")){
                            image1.setBorderWidth(Float.parseFloat("5"));
                            image1.setBorderColor(getResources().getColor(R.color.transparent));

                            image2.setBorderWidth(Float.parseFloat("5"));
                            image2.setBorderColor(getResources().getColor(R.color.transparent));

                            image3.setBorderWidth(Float.parseFloat("5"));
                            image3.setBorderColor(getResources().getColor(R.color.transparent));

                            image4.setBorderWidth(Float.parseFloat("5"));
                            image4.setBorderColor(getResources().getColor(R.color.transparent));

                            image5.setBorderWidth(Float.parseFloat("5"));
                            image5.setBorderColor(getResources().getColor(R.color.transparent));

                            image6.setBorderWidth(Float.parseFloat("5"));
                            image6.setBorderColor(getResources().getColor(R.color.transparent));

                            image7.setBorderWidth(Float.parseFloat("5"));
                            image7.setBorderColor(getResources().getColor(R.color.transparent));

                            image8.setBorderWidth(Float.parseFloat("5"));
                            image8.setBorderColor(getResources().getColor(R.color.transparent));

                            image9.setBorderWidth(Float.parseFloat("5"));
                            image9.setBorderColor(getResources().getColor(R.color.transparent));

                        }
                        else {
                            if (multiple_edit_list.get(position).contains("_1")) {

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.golden));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));


                            }
                            else if (multiple_edit_list.get(position).contains("_2")) {

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));


                            }
                            else if (multiple_edit_list.get(position).contains("_3")) {
                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_4")) {
                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_5")) {
                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_6")) {
                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_7")) {
                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_8")) {
                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_9")) {
                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                        }


                    }
                });


                if (paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }

                if (getIntent().getStringExtra("from").equals("campaign")){
                    heading.setText(getResources().getString(R.string.jointhiscampaign));
                }
                else{
                    heading.setText(getResources().getString(R.string.jointhischallenge));
                }
                from = getIntent().getStringExtra("from");
                cid = getIntent().getStringExtra("cid");

                if (getIntent().hasExtra("type")){
                    type = getIntent().getStringExtra("type");
                }

                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        for (int i=0; i<multiple_edit_list.size(); i++){

                            //Log.e("multipleListItems",multiple_edit_list.get(i));

                            File f = new File(paths.get(i));

                            final File newfile = new Compressor.Builder(ImagePreview_Activity.this)
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


                            if (!multiple_edit_list.get(i).equals("dummy")){

                                if (multiple_edit_list.get(i).contains("_1")){

                                }
                                else if (multiple_edit_list.get(i).contains("_2")){

                                    Filter myFilter1 = new Filter();
                                    myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                                    myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter1.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if (multiple_edit_list.get(i).contains("_3")){

                                    Filter myFilter2 = new Filter();
                                    myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter2.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_4")){

                                    Filter myFilter3 = new Filter();
                                    myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                                    myFilter3.addSubFilter(new BrightnessSubFilter(30));
                                    myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                                    myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                                    singleCamBitmap = myFilter3.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_5")){

                                    Filter myFilter4 = new Filter();
                                    myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                                    myFilter4.addSubFilter(new BrightnessSubFilter(15));
                                    myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                                    myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                                    singleCamBitmap = myFilter4.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_6")){

                                    Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = haan.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_7")){

                                    Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = adele.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_8")){

                                    Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = cruz.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_9")){

                                    Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                                    singleCamBitmap = metropolis.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        Intent intent = new Intent(ImagePreview_Activity.this,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("join","join");
                        if (from.equals("campaign")){
                            intent.putStringArrayListExtra("list",paths);
                            intent.putExtra("from","campaign");
                            intent.putExtra("preview","preview");
                            intent.putExtra("cid",cid);
                            intent.putExtra("type",getIntent().getStringExtra("type"));


                        }
                        else if (from.equals("challenge")){
                            intent.putStringArrayListExtra("list",paths);
                            intent.putExtra("from","challenge");
                            intent.putExtra("preview","preview");
                            intent.putExtra("type",getIntent().getStringExtra("type"));
                            intent.putExtra("cid",cid);
                        }
                        startActivity(intent);
                    }
                });


                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        avibackground.setVisibility(View.VISIBLE);
                        avi.smoothToShow();

                        for (int i=0; i<multiple_edit_list.size(); i++){

                            File f = new File(paths.get(i));

                            final File newfile = new Compressor.Builder(ImagePreview_Activity.this)
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

                            if (!multiple_edit_list.get(i).equals("dummy")){

                                if (multiple_edit_list.get(i).contains("_1")){

                                }
                                else if (multiple_edit_list.get(i).contains("_2")){

                                    Filter myFilter1 = new Filter();
                                    myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                                    myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter1.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if (multiple_edit_list.get(i).contains("_3")){

                                    Filter myFilter2 = new Filter();
                                    myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter2.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){

                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_4")){

                                    Filter myFilter3 = new Filter();
                                    myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                                    myFilter3.addSubFilter(new BrightnessSubFilter(30));
                                    myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                                    myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                                    singleCamBitmap = myFilter3.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_5")){

                                    Filter myFilter4 = new Filter();
                                    myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                                    myFilter4.addSubFilter(new BrightnessSubFilter(15));
                                    myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                                    myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                                    singleCamBitmap = myFilter4.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_6")){

                                    Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = haan.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_7")){

                                    Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = adele.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_8")){

                                    Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = cruz.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_9")){

                                    Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                                    singleCamBitmap = metropolis.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                        if (Constant.isOnline(ImagePreview_Activity.this)){

                            AttemptChallengeApiwithedit();

                        }
                        else{
                            Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoInternetConnection));
                        }
                    }
                });


                for (int i=0; i<paths.size(); i++){
                    File file= new File(paths.get(i));
                    if (!file.getAbsolutePath().contains(".mp4")){
                        File newfile = new Compressor.Builder(this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .build()
                                .compressToFile(file);
                        files.add(newfile);

                    }
                }
            }


            //without join
            else if (getIntent().hasExtra("list") && !getIntent().hasExtra("join")){

                multiple_without_join =1;
                camera_without_join=0;
                camera_with_join=0;
                multiple_with_join=0;


                paths.clear();
                paths = getIntent().getStringArrayListExtra("galleryimage");

                multiple_edit_list.clear();
                for (int i=0; i<paths.size(); i++){
                    multiple_edit_list.add("dummy");
                }


                adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,0);
                viewPager.setAdapter(adapter);
                indicator.setViewPager(viewPager);
                viewPager.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);



                if (paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }

                //viewpager se current position utha k isi class mian usi position wali ko edit mardo bitmap or list main replace kr k add krdo


                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

                    @Override
                    public void onPageSelected(int position) {
                        //Log.e("curremPos", String.valueOf(position));
                        multiple_current_position = position;

                        if (multiple_edit_list.get(position).equals("dummy")){
                            image1.setBorderWidth(Float.parseFloat("5"));
                            image1.setBorderColor(getResources().getColor(R.color.transparent));

                            image2.setBorderWidth(Float.parseFloat("5"));
                            image2.setBorderColor(getResources().getColor(R.color.transparent));

                            image3.setBorderWidth(Float.parseFloat("5"));
                            image3.setBorderColor(getResources().getColor(R.color.transparent));

                            image4.setBorderWidth(Float.parseFloat("5"));
                            image4.setBorderColor(getResources().getColor(R.color.transparent));

                            image5.setBorderWidth(Float.parseFloat("5"));
                            image5.setBorderColor(getResources().getColor(R.color.transparent));

                            image6.setBorderWidth(Float.parseFloat("5"));
                            image6.setBorderColor(getResources().getColor(R.color.transparent));

                            image7.setBorderWidth(Float.parseFloat("5"));
                            image7.setBorderColor(getResources().getColor(R.color.transparent));

                            image8.setBorderWidth(Float.parseFloat("5"));
                            image8.setBorderColor(getResources().getColor(R.color.transparent));

                            image9.setBorderWidth(Float.parseFloat("5"));
                            image9.setBorderColor(getResources().getColor(R.color.transparent));

                        }
                        else {
                            if (multiple_edit_list.get(position).contains("_1")) {

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.golden));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));


                            }
                            else if (multiple_edit_list.get(position).contains("_2")) {

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));


                            }
                            else if (multiple_edit_list.get(position).contains("_3")) {
                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_4")) {
                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_5")) {
                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_6")) {
                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                            else if (multiple_edit_list.get(position).contains("_7")) {
                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_8")) {
                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.transparent));
                            }
                            else if (multiple_edit_list.get(position).contains("_9")) {
                                image9.setBorderWidth(Float.parseFloat("5"));
                                image9.setBorderColor(getResources().getColor(R.color.golden));

                                image1.setBorderWidth(Float.parseFloat("5"));
                                image1.setBorderColor(getResources().getColor(R.color.transparent));

                                image2.setBorderWidth(Float.parseFloat("5"));
                                image2.setBorderColor(getResources().getColor(R.color.transparent));

                                image3.setBorderWidth(Float.parseFloat("5"));
                                image3.setBorderColor(getResources().getColor(R.color.transparent));

                                image4.setBorderWidth(Float.parseFloat("5"));
                                image4.setBorderColor(getResources().getColor(R.color.transparent));

                                image5.setBorderWidth(Float.parseFloat("5"));
                                image5.setBorderColor(getResources().getColor(R.color.transparent));

                                image6.setBorderWidth(Float.parseFloat("5"));
                                image6.setBorderColor(getResources().getColor(R.color.transparent));

                                image7.setBorderWidth(Float.parseFloat("5"));
                                image7.setBorderColor(getResources().getColor(R.color.transparent));

                                image8.setBorderWidth(Float.parseFloat("5"));
                                image8.setBorderColor(getResources().getColor(R.color.transparent));

                            }
                        }

                        //setupFiltersList();

                    }
                });


                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        for (int i=0; i<multiple_edit_list.size(); i++){

                            //Log.e("multipleListItems",multiple_edit_list.get(i));

                            File f = new File(paths.get(i));

                            final File newfile = new Compressor.Builder(ImagePreview_Activity.this)
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


                            if (!multiple_edit_list.get(i).equals("dummy")){

                                if (multiple_edit_list.get(i).contains("_1")){

                                }
                                else if (multiple_edit_list.get(i).contains("_2")){

                                    Filter myFilter1 = new Filter();
                                    myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                                    myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter1.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if (multiple_edit_list.get(i).contains("_3")){

                                    Filter myFilter2 = new Filter();
                                    myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter2.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_4")){

                                    Filter myFilter3 = new Filter();
                                    myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                                    myFilter3.addSubFilter(new BrightnessSubFilter(30));
                                    myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                                    myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                                    singleCamBitmap = myFilter3.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_5")){

                                    Filter myFilter4 = new Filter();
                                    myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                                    myFilter4.addSubFilter(new BrightnessSubFilter(15));
                                    myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                                    myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                                    singleCamBitmap = myFilter4.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_6")){

                                    Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = haan.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_7")){

                                    Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = adele.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_8")){

                                    Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = cruz.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_9")){

                                    Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                                    singleCamBitmap = metropolis.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        Intent intent = new Intent(ImagePreview_Activity.this,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (from.equals("campaign")){
                            intent.putStringArrayListExtra("list",paths);
                            intent.putExtra("from","campaign");
                            intent.putExtra("preview","preview");
                        }
                        else if (from.equals("challenge")){
                            intent.putStringArrayListExtra("list",paths);
                            intent.putExtra("from","challenge");
                            intent.putExtra("preview","preview");
                            intent.putExtra("type",getIntent().getStringExtra("type"));
                        }
                        startActivity(intent);


                    }
                });

                if (getIntent().getStringExtra("from").equals("campaign")){
                    heading.setText(getResources().getString(R.string.createyourcampaign));
                }
                from = getIntent().getStringExtra("from");

                next.setOnClickListener(new View.OnClickListener() { //yahan se continure krna hai
                    @Override
                    public void onClick(View view) {

                        for (int i=0; i<multiple_edit_list.size(); i++){

                            File f = new File(paths.get(i));

                            final File newfile = new Compressor.Builder(ImagePreview_Activity.this)
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


                            if (!multiple_edit_list.get(i).equals("dummy")){

                                if (multiple_edit_list.get(i).contains("_1")){

                                }
                                else if (multiple_edit_list.get(i).contains("_2")){

                                    Filter myFilter1 = new Filter();
                                    myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                                    myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter1.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if (multiple_edit_list.get(i).contains("_3")){

                                    Filter myFilter2 = new Filter();
                                    myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                                    singleCamBitmap = myFilter2.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_4")){

                                    Filter myFilter3 = new Filter();
                                    myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                                    myFilter3.addSubFilter(new BrightnessSubFilter(30));
                                    myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                                    myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                                    singleCamBitmap = myFilter3.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_5")){

                                    Filter myFilter4 = new Filter();
                                    myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                                    myFilter4.addSubFilter(new BrightnessSubFilter(15));
                                    myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                                    myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                                    singleCamBitmap = myFilter4.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_6")){

                                    Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = haan.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_7")){

                                    Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = adele.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_8")){

                                    Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                                    singleCamBitmap = cruz.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (multiple_edit_list.get(i).contains("_9")){

                                    Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                                    singleCamBitmap = metropolis.processFilter(bitmap);
                                    try {
                                        String state = Environment.getExternalStorageState();
                                        if (!state.equals(Environment.MEDIA_MOUNTED)){
                                        }
                                        else {
                                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                            if (!folder_gui.exists()){
                                                folder_gui.mkdirs();
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            }
                                            else {
                                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                                singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                                output.close();
                                                paths.remove(i);
                                                int ind = folder_gui.listFiles().length-1;
                                                paths.add(i,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            }
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        if (from.equals("campaign")){
                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Campaign_Acitivity.class);
                            intent.putStringArrayListExtra("galleryimage",paths);
                            intent.putExtra("list","list");
                            startActivity(intent);
                        }
                        else if (from.equals("challenge")){
                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Challenge_Acitivity.class);
                            intent.putStringArrayListExtra("galleryimage",paths);
                            intent.putExtra("list","list");
                            intent.putExtra("type",getIntent().getStringExtra("type"));
                            startActivity(intent);
                        }

                    }
                });
            }
            else{

                    filterslayout.setVisibility(View.GONE);


                if (getIntent().hasExtra("join")){

                    gallerypath = getIntent().getStringExtra("galleryimage");
                    RequestOptions options2 = new RequestOptions();
                    options2.placeholder(R.drawable.bossble_placeholder_large);
                    Glide.with(ImagePreview_Activity.this)
                            .load(gallerypath)
                            .apply(options2)
                            .into(image);

                    File file= new File(gallerypath);

                    if (!file.getAbsolutePath().contains(".mp4")){
                        File newfile = new Compressor.Builder(this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .build()
                                .compressToFile(file);
                        files.add(newfile);
                    }
                    else {
                        files.add(file);
                    }

                    if (getIntent().getStringExtra("from").equals("campaign")){
                        heading.setText(getResources().getString(R.string.jointhiscampaign));
                    }
                    else {
                        heading.setText(getResources().getString(R.string.jointhischallenge));
                    }

                    from = getIntent().getStringExtra("from");
                    cid = getIntent().getStringExtra("cid");

                    if (getIntent().hasExtra("type")){
                        type = getIntent().getStringExtra("type");
                    }


                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Constant.isOnline(ImagePreview_Activity.this)){

                                AttemptChallengeApi();

                            }
                            else{
                                Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }
                        }
                    });
                }
                else {

                    gallerypath = getIntent().getStringExtra("galleryimage");
                    RequestOptions options2 = new RequestOptions();
                    options2.placeholder(R.drawable.bossble_placeholder_large);
                    Glide.with(ImagePreview_Activity.this)
                            .load(gallerypath)
                            .apply(options2)
                            .into(image);

                    File file= new File(gallerypath);

                    if (getIntent().getStringExtra("from").equals("campaign")){
                        heading.setText(getResources().getString(R.string.createyourcampaign));
                    }



                    from = getIntent().getStringExtra("from");

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (from.equals("campaign")){
                                Intent intent = new Intent(ImagePreview_Activity.this,Create_Campaign_Acitivity.class);
                                intent.putExtra("galleryimage",gallerypath);
                                startActivity(intent);
                            }
                            else if (from.equals("challenge")){
                                Intent intent = new Intent(ImagePreview_Activity.this,Create_Challenge_Acitivity.class);
                                intent.putExtra("galleryimage",gallerypath);
                                intent.putExtra("type",getIntent().getStringExtra("type"));
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        }
        else {

            // if from join
            if (getIntent().hasExtra("join")){

                //join with camera

                camera_without_join=0;
                multiple_without_join=0;
                camera_with_join=1;
                multiple_with_join=0;

                join = getIntent().getStringExtra("join");

                File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                File [] files2 =folder_gui.listFiles();
                int index =  folder_gui.listFiles().length;

                Collections.sort(Arrays.asList(files2),new Comparor());

                path =files2[index-1].getAbsolutePath();
                Log.e("folderItem", path);
                image.setImageURI(Uri.parse(path));


                File file= new File(path);
                if (!file.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")){
                    File newfile = new Compressor.Builder(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(70)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .build()
                            .compressToFile(file);

                    files.add(newfile);
                }
                else {
                    files.add(file);
                }

                paths.add(path);


                if(paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }
                if (getIntent().getStringExtra("from").equals("campaign")){
                    heading.setText(getResources().getString(R.string.jointhiscampaign));
                }
                else {
                    heading.setText(getResources().getString(R.string.jointhischallenge));
                }

                from = getIntent().getStringExtra("from");
                cid = getIntent().getStringExtra("cid");

                if (getIntent().hasExtra("type")){
                    type = getIntent().getStringExtra("type");
                }

                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ImagePreview_Activity.this,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("join","join");

                        if (from.equals("campaign")){

                            if (camera_with_join_edit==1){
                                try {
                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","campaign");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("cid",cid);
                                            intent.putExtra("type",getIntent().getStringExtra("type"));

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();

                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","campaign");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("cid",cid);
                                            intent.putExtra("type",getIntent().getStringExtra("type"));

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else {
                                intent.putStringArrayListExtra("list",paths);
                                intent.putExtra("from","campaign");
                                intent.putExtra("preview","preview");
                                intent.putExtra("cid",cid);
                                intent.putExtra("type",getIntent().getStringExtra("type"));

                            }
                        }

                        else if (from.equals("challenge")){

                            if (camera_with_join_edit==1){
                                try {
                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","challenge");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));
                                            intent.putExtra("cid",cid);

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();

                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","challenge");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));
                                            intent.putExtra("cid",cid);

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else {
                                intent.putStringArrayListExtra("list",paths);
                                intent.putExtra("from","challenge");
                                intent.putExtra("preview","preview");
                                intent.putExtra("type",getIntent().getStringExtra("type"));
                                intent.putExtra("cid",cid);

                            }
                        }
                        startActivity(intent);
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (camera_with_join_edit==1){
                            try {

                                String state = Environment.getExternalStorageState();
                                if (!state.equals(Environment.MEDIA_MOUNTED)){
                                }
                                else {
                                    File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                    if (!folder_gui.exists()){
                                        folder_gui.mkdirs();
                                        FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                        singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                        output.close();
                                        paths.remove(0);
                                        paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));

                                        if (Constant.isOnline(ImagePreview_Activity.this)){

                                            AttemptChallengeApiwithedit();

                                        }
                                        else{
                                            Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                        }
                                    }
                                    else {

                                        FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                        singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                        output.close();

                                        paths.remove(0);
                                        int ind = folder_gui.listFiles().length-1;
                                        paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));

                                        if (Constant.isOnline(ImagePreview_Activity.this)){


                                            AttemptChallengeApiwithedit();

                                        }
                                        else{
                                            Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoInternetConnection));
                                        }
                                    }
                                }
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            if (Constant.isOnline(ImagePreview_Activity.this)){

                                AttemptChallengeApi();

                            }
                            else{
                                Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }
                        }


                    }
                });


            }
            else {

                camera_without_join = 1;
                camera_with_join=0;
                multiple_with_join=0;
                multiple_without_join=0;
                // camera capture without join

                File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                File [] files =folder_gui.listFiles();
                int index =  folder_gui.listFiles().length;

                Collections.sort(Arrays.asList(files),new Comparor());

                path =files[index-1].getAbsolutePath();
                Log.e("folderItem", path);
                image.setImageURI(Uri.parse(path));

                paths.add(path);



                if(paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }
                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // for multiple images viewpager ki current position chaiye hogi for edit

                        Intent intent = new Intent(ImagePreview_Activity.this,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (from.equals("campaign")){

                            if (camera_without_join_edit==1){

                                try {
                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","campaign");
                                            intent.putExtra("preview","preview");

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();

                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","campaign");
                                            intent.putExtra("preview","preview");

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                intent.putStringArrayListExtra("list",paths);
                                intent.putExtra("from","campaign");
                                intent.putExtra("preview","preview");
                            }
                        }
                        else if (from.equals("challenge")){

                            if (camera_without_join_edit==1){
                                try {
                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","challenge");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();

                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            //intent
                                            intent.putStringArrayListExtra("list",paths);
                                            intent.putExtra("from","challenge");
                                            intent.putExtra("preview","preview");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else {
                                intent.putStringArrayListExtra("list",paths);
                                intent.putExtra("from","challenge");
                                intent.putExtra("preview","preview");
                                intent.putExtra("type",getIntent().getStringExtra("type"));
                            }
                        }
                        startActivity(intent);
                    }
                });

                if (getIntent().getStringExtra("from").equals("campaign")){
                    heading.setText(getResources().getString(R.string.createyourcampaign));
                }

                from = getIntent().getStringExtra("from");

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (from.equals("campaign")){

                            if (camera_without_join_edit==1){
                                try {

                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));
                                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage",paths);
                                            intent.putExtra("list","list");
                                            startActivity(intent);

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();

                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));
                                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage",paths);
                                            intent.putExtra("list","list");
                                            startActivity(intent);

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Campaign_Acitivity.class);
                            intent.putStringArrayListExtra("galleryimage",paths);
                            intent.putExtra("list","list");
                            startActivity(intent);
                            }
                        }
                        else if (from.equals("challenge")){

                            if (camera_without_join_edit==1){
                                try {

                                    String state = Environment.getExternalStorageState();
                                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                                    }
                                    else {
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        if (!folder_gui.exists()){
                                            folder_gui.mkdirs();
                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/edittest.jpeg"));

                                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage",paths);
                                            intent.putExtra("list","list");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));
                                            startActivity(intent);

                                        }
                                        else {

                                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                                            singleCamBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                                            output.close();
                                            paths.remove(0);
                                            int ind = folder_gui.listFiles().length-1;
                                            paths.add(0,String.valueOf(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+ind+".jpeg"));

                                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage",paths);
                                            intent.putExtra("list","list");
                                            intent.putExtra("type",getIntent().getStringExtra("type"));
                                            startActivity(intent);

                                        }
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                            Intent intent = new Intent(ImagePreview_Activity.this,Create_Challenge_Acitivity.class);
                            intent.putStringArrayListExtra("galleryimage",paths);
                            intent.putExtra("list","list");
                            intent.putExtra("type",getIntent().getStringExtra("type"));
                            startActivity(intent);
                            }
                        }
                    }
                });

            }
        }


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreview_Activity.this.onBackPressed();
            }
        });


        setupFiltersList();

        setFilterListeners();



    }

    private class  Comparor implements Comparator<File> {


        @Override
        public int compare(File file, File t1) {

            long k = file.lastModified() - t1.lastModified();
            if(k > 0){
                return 1;
            }else if(k == 0){
                return 0;
            }else{
                return -1;
            }
        }
    }

    private void AttemptChallengeApi(){

        if (files.size()==0){
            Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoMediaAdded));
        }
        else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
            String userid = preferences.getString(Constant.USER_ID,"");
            RequestParams params = new RequestParams();
            params.add("challenge_type_id",type);
            params.add("user_id",userid);
            params.add("challenge_id",cid);

            //media
            File[] filesarray = new File[files.size()];
            filesarray = files.toArray(filesarray);
            try {
                if (filesarray[0].getAbsolutePath().contains(".mp4")){
                    params.put("video[]",filesarray);
                }
                else {
                    params.put("image[]",filesarray);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Log.e("params", String.valueOf(params));
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "post/post/challenge_reply", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String res = new String(responseBody);
                    Log.e("attemptedres",res);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true") && errorMessage.equals("")){


                            JSONObject item = jsonObject.getJSONObject("dataSet");

                            final String id = item.getString("id");
                            final String user_id = item.getString("user_id");
                            final String title = item.getString("title");
                            final String descritpion = item.getString("descritpion");
                            final String tags = item.getString("tags");
                            String created_at = item.getString("created_at");
                            final String username = item.getString("username");
                            final String profile_image = item.getString("profile_image");
                            final String challenge_type = item.getString("challenge_type");

                            final String description_fonts = "";
                            final String image = "";
/*
                            if (item.has("image")){
                                image = item.getString("image");
                            }
                            else if (item.has("video")){
                                image = item.getString("video");
                            }
*/

                            final String comments_count = item.getString("comments_count");
                            final String like_count = item.getString("like_count");
                            final String view_count = item.getString("view_count");
                            final String country = item.getString("country");


                            Constant.SuccessToast(ImagePreview_Activity.this,getResources().getString(R.string.AttemptedSuccessfully));




                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ImagePreview_Activity.this,Details_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("refresh","refresh");
                                    intent.putExtra("id",id);
                                    intent.putExtra("username",username);
                                    intent.putExtra("userimage",profile_image);
                                    intent.putExtra("user_id",user_id);

                                    intent.putExtra("image", image);

                                    intent.putExtra("fonts",description_fonts);
                                    intent.putExtra("likes",like_count);
                                    intent.putExtra("comments",comments_count);
                                    intent.putExtra("title",title);
                                    intent.putExtra("description",descritpion);
                                    intent.putExtra("tags",tags);

                                    if (challenge_type.equals("5")){
                                        intent.putExtra("type","campaign");
                                    }
                                    else {
                                        intent.putExtra("type","challenge");
                                    }

                                    intent.putExtra("country",country);
                                    intent.putExtra("view_count",view_count);
                                    intent.putExtra("back","back");
                                    intent.putExtra("join","join");

                                    startActivity(intent);
                                }
                            }, 2000);

                        }
                        else if (status.equals("false")){
                            Constant.ErrorToast(ImagePreview_Activity.this,errorMessage);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null){
                        String res = new String(responseBody);
                        Log.e("attemptedresF",res);
                    }
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.Somethingwentwrong));
                }
            });
        }


    }

    private void AttemptChallengeApiwithedit(){

        if (files.size()==0){
            Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.NoMediaAdded));
        }
        else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
            String userid = preferences.getString(Constant.USER_ID,"");
            RequestParams params = new RequestParams();
            params.add("challenge_type_id",type);
            params.add("user_id",userid);
            params.add("challenge_id",cid);

            //media
            files.clear();
            for (int i=0; i<paths.size(); i++){
                File f = new File(paths.get(i));
                files.add(f);
            }

            File[] filesarray = new File[files.size()];
            filesarray = files.toArray(filesarray);

            try {
                if (filesarray[0].getAbsolutePath().contains(".mp4")){
                    params.put("video[]",filesarray);
                }
                else {
                    params.put("image[]",filesarray);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Log.e("params", String.valueOf(params));
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL + "post/post/challenge_reply", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String res = new String(responseBody);
                    Log.e("attemptedres",res);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);


                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");

                        if (status.equals("true") && errorMessage.equals("")){


                            JSONObject item = jsonObject.getJSONObject("dataSet");

                            final String id = item.getString("id");
                            final String user_id = item.getString("user_id");
                            final String title = item.getString("title");
                            final String descritpion = item.getString("descritpion");
                            final String tags = item.getString("tags");
                            String created_at = item.getString("created_at");
                            final String username = item.getString("username");
                            final String profile_image = item.getString("profile_image");
                            final String challenge_type = item.getString("challenge_type");

                            final String description_fonts = "";
                            final String image = "";
/*
                            if (item.has("image")){
                                image = item.getString("image");
                            }
                            else if (item.has("video")){
                                image = item.getString("video");
                            }
*/

                            final String comments_count = item.getString("comments_count");
                            final String like_count = item.getString("like_count");
                            final String view_count = item.getString("view_count");
                            final String country = item.getString("country");


                            Constant.SuccessToast(ImagePreview_Activity.this,getResources().getString(R.string.AttemptedSuccessfully));




                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ImagePreview_Activity.this,Details_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("refresh","refresh");
                                    intent.putExtra("id",id);
                                    intent.putExtra("username",username);
                                    intent.putExtra("userimage",profile_image);
                                    intent.putExtra("user_id",user_id);

                                    intent.putExtra("image", image);

                                    intent.putExtra("fonts",description_fonts);
                                    intent.putExtra("likes",like_count);
                                    intent.putExtra("comments",comments_count);
                                    intent.putExtra("title",title);
                                    intent.putExtra("description",descritpion);
                                    intent.putExtra("tags",tags);

                                    if (challenge_type.equals("5")){
                                        intent.putExtra("type","campaign");
                                    }
                                    else {
                                        intent.putExtra("type","challenge");
                                    }

                                    intent.putExtra("country",country);
                                    intent.putExtra("view_count",view_count);
                                    intent.putExtra("back","back");
                                    intent.putExtra("join","join");

                                    startActivity(intent);
                                }
                            }, 2000);

                        }
                        else if (status.equals("false")){
                            Constant.ErrorToast(ImagePreview_Activity.this,errorMessage);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null){
                        String res = new String(responseBody);
                        Log.e("attemptedresF",res);

                    }
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    Constant.ErrorToast(ImagePreview_Activity.this,getResources().getString(R.string.Somethingwentwrong));
                }
            });
        }


    }

    private void setupFiltersList() {


        if (camera_without_join == 1) {

            File f = new File(paths.get(0));
            if (!f.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")) {
                File newfile = new Compressor.Builder(this)
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

                Bitmap bitmap2 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap2 = bitmap2.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap3 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap3 = bitmap3.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap4 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap4 = bitmap4.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap5 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap5 = bitmap5.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap6 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap6 = bitmap6.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap7 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap7 = bitmap7.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap8 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap8 = bitmap8.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap9 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap9 = bitmap9.copy(Bitmap.Config.RGB_565, true);

                //image1.setImageURI(Uri.parse(paths.get(0)));
                image1.setImageBitmap(bitmap);

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image2.setImageBitmap(myFilter1.processFilter(bitmap2));

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image3.setImageBitmap(myFilter2.processFilter(bitmap3));

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                image4.setImageBitmap(myFilter3.processFilter(bitmap4));

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                image5.setImageBitmap(myFilter4.processFilter(bitmap5));

                Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                image6.setImageBitmap(haan.processFilter(bitmap6));

                Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                image7.setImageBitmap(adele.processFilter(bitmap7));

                Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                image8.setImageBitmap(cruz.processFilter(bitmap8));

                Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                image9.setImageBitmap(metropolis.processFilter(bitmap9));

            }
        } else if (camera_with_join == 1) {
            File f = new File(paths.get(0));
            if (!f.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")) {
                File newfile = new Compressor.Builder(this)
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

                Bitmap bitmap2 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap2 = bitmap2.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap3 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap3 = bitmap3.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap4 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap4 = bitmap4.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap5 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap5 = bitmap5.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap6 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap6 = bitmap6.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap7 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap7 = bitmap7.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap8 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap8 = bitmap8.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap9 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap9 = bitmap9.copy(Bitmap.Config.RGB_565, true);

                image1.setImageURI(Uri.parse(paths.get(0)));

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image2.setImageBitmap(myFilter1.processFilter(bitmap2));

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image3.setImageBitmap(myFilter2.processFilter(bitmap3));

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                image4.setImageBitmap(myFilter3.processFilter(bitmap4));

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                image5.setImageBitmap(myFilter4.processFilter(bitmap5));

                Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                image6.setImageBitmap(haan.processFilter(bitmap6));

                Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                image7.setImageBitmap(adele.processFilter(bitmap7));

                Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                image8.setImageBitmap(cruz.processFilter(bitmap8));

                Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                image9.setImageBitmap(metropolis.processFilter(bitmap9));

            }
        } else if (multiple_without_join == 1) {

            File f = new File(paths.get(multiple_current_position));
            if (!f.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")) {
                File newfile = new Compressor.Builder(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(70)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(f);


                Bitmap bitmap2 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap2 = bitmap2.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap3 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap3 = bitmap3.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap4 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap4 = bitmap4.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap5 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap5 = bitmap5.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap6 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap6 = bitmap6.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap7 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap7 = bitmap7.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap8 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap8 = bitmap8.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap9 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap9 = bitmap9.copy(Bitmap.Config.RGB_565, true);

                image1.setImageURI(Uri.parse(paths.get(multiple_current_position)));

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image2.setImageBitmap(myFilter1.processFilter(bitmap2));

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image3.setImageBitmap(myFilter2.processFilter(bitmap3));

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                image4.setImageBitmap(myFilter3.processFilter(bitmap4));

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                image5.setImageBitmap(myFilter4.processFilter(bitmap5));

                Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                image6.setImageBitmap(haan.processFilter(bitmap6));

                Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                image7.setImageBitmap(adele.processFilter(bitmap7));

                Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                image8.setImageBitmap(cruz.processFilter(bitmap8));

                Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                image9.setImageBitmap(metropolis.processFilter(bitmap9));

            }
        } else if (multiple_with_join == 1) {
            File f = new File(paths.get(multiple_current_position));
            if (!f.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")) {
                File newfile = new Compressor.Builder(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(70)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(f);


                Bitmap bitmap2 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap2 = bitmap2.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap3 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap3 = bitmap3.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap4 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap4 = bitmap4.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap5 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap5 = bitmap5.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap6 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap6 = bitmap6.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap7 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap7 = bitmap7.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap8 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap8 = bitmap8.copy(Bitmap.Config.RGB_565, true);

                Bitmap bitmap9 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                bitmap9 = bitmap9.copy(Bitmap.Config.RGB_565, true);

                image1.setImageURI(Uri.parse(paths.get(multiple_current_position)));

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image2.setImageBitmap(myFilter1.processFilter(bitmap2));

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image3.setImageBitmap(myFilter2.processFilter(bitmap3));

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                image4.setImageBitmap(myFilter3.processFilter(bitmap4));

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                image5.setImageBitmap(myFilter4.processFilter(bitmap5));

                Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                image6.setImageBitmap(haan.processFilter(bitmap6));

                Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                image7.setImageBitmap(adele.processFilter(bitmap7));

                Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                image8.setImageBitmap(cruz.processFilter(bitmap8));

                Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                image9.setImageBitmap(metropolis.processFilter(bitmap9));

            } else {
                Drawable d = getResources().getDrawable(R.drawable.joined_04);

                Bitmap bitmap1 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap3 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap4 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap5 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap6 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap7 = ((BitmapDrawable) d).getBitmap();
                Bitmap bitmap8 = ((BitmapDrawable) d).getBitmap();


                bitmap1 = bitmap1.copy(Bitmap.Config.RGB_565, true);
                bitmap2 = bitmap2.copy(Bitmap.Config.RGB_565, true);
                bitmap3 = bitmap3.copy(Bitmap.Config.RGB_565, true);
                bitmap4 = bitmap4.copy(Bitmap.Config.RGB_565, true);
                bitmap5 = bitmap5.copy(Bitmap.Config.RGB_565, true);
                bitmap6 = bitmap6.copy(Bitmap.Config.RGB_565, true);
                bitmap7 = bitmap7.copy(Bitmap.Config.RGB_565, true);
                bitmap8 = bitmap8.copy(Bitmap.Config.RGB_565, true);


                image1.setImageDrawable(ImagePreview_Activity.this.getResources().getDrawable(R.drawable.joined_04));

                Filter myFilter1 = new Filter();
                myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                myFilter1.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image2.setImageBitmap(myFilter1.processFilter(bitmap1));

                Filter myFilter2 = new Filter();
                myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                image3.setImageBitmap(myFilter2.processFilter(bitmap2));

                Filter myFilter3 = new Filter();
                myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                myFilter3.addSubFilter(new BrightnessSubFilter(30));
                myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                image4.setImageBitmap(myFilter3.processFilter(bitmap3));

                Filter myFilter4 = new Filter();
                myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                myFilter4.addSubFilter(new BrightnessSubFilter(15));
                myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                image5.setImageBitmap(myFilter4.processFilter(bitmap4));

                Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                image6.setImageBitmap(haan.processFilter(bitmap5));

                Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                image7.setImageBitmap(adele.processFilter(bitmap6));

                Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                image8.setImageBitmap(cruz.processFilter(bitmap7));

                Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                image9.setImageBitmap(metropolis.processFilter(bitmap8));

            }

        }
    }

    private void setFilterListeners(){

        if (paths!=null && paths.size()>0){
           File f = new File(paths.get(0));

            if (!f.getAbsolutePath().contains(".mp4") && !getIntent().hasExtra("emoji")) {
                final File newfile = new Compressor.Builder(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(70)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(f);


                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.golden));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));


                        if (camera_without_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            image.setImageBitmap(bitmap);

                            singleCamBitmap = bitmap;

                            camera_without_join_edit=1;

                        }

                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);
                            image.setImageBitmap(bitmap);
                            singleCamBitmap = bitmap;

                            camera_with_join_edit=1;

                        }
                        else if (multiple_without_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_1");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_1");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });

                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter myFilter1 = new Filter();
                            myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                            myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));

                            singleCamBitmap = myFilter1.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);


                            camera_without_join_edit=1;


/*
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });
*/


                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter myFilter1 = new Filter();
                            myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                            myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                            image.setImageBitmap(myFilter1.processFilter(bitmap));

                            singleCamBitmap = myFilter1.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }
                        else if (multiple_without_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_2");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);


                            File f = new File(paths.get(multiple_current_position));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter myFilter1 = new Filter();
                            myFilter1.addSubFilter(new SaturationSubfilter(2.0f));
                            myFilter1.addSubFilter(new ColorOverlaySubFilter(100, .2f, .2f, .0f));
                            singleCamBitmap = myFilter1.processFilter(bitmap);



                            // callback.onFilterselected(multiple_current_position,2);


                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_2");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }


                    }
                });

                image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter myFilter2 = new Filter();
                            myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));

                            singleCamBitmap = myFilter2.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter myFilter2 = new Filter();
                            myFilter2.addSubFilter(new ColorOverlaySubFilter(200, .2f, .2f, .0f));
                            image.setImageBitmap(myFilter2.processFilter(bitmap));

                            singleCamBitmap = myFilter2.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }

                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_3");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_3");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }


                    }
                });

                image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter myFilter3 = new Filter();
                            myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                            myFilter3.addSubFilter(new BrightnessSubFilter(30));
                            myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                            myFilter3.addSubFilter(new SaturationSubfilter(1.5f));

                            singleCamBitmap = myFilter3.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter myFilter3 = new Filter();
                            myFilter3.addSubFilter(new ContrastSubFilter(0.7f));
                            myFilter3.addSubFilter(new BrightnessSubFilter(30));
                            myFilter3.addSubFilter(new ColorOverlaySubFilter(100, .1f, .1f, .0f));
                            myFilter3.addSubFilter(new SaturationSubfilter(1.5f));
                            image.setImageBitmap(myFilter3.processFilter(bitmap));
                            singleCamBitmap = myFilter3.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }

                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_4");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_4");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });

                image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter myFilter4 = new Filter();
                            myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                            myFilter4.addSubFilter(new BrightnessSubFilter(15));
                            myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                            myFilter4.addSubFilter(new SaturationSubfilter(2.5f));

                            singleCamBitmap = myFilter4.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter myFilter4 = new Filter();
                            myFilter4.addSubFilter(new ContrastSubFilter(0.5f));
                            myFilter4.addSubFilter(new BrightnessSubFilter(15));
                            myFilter4.addSubFilter(new ColorOverlaySubFilter(50, .1f, .1f, .0f));
                            myFilter4.addSubFilter(new SaturationSubfilter(2.5f));
                            image.setImageBitmap(myFilter4.processFilter(bitmap));

                            singleCamBitmap = myFilter4.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }

                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_5");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_5");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });

                image6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);

                            singleCamBitmap = haan.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter haan = FilterPack.getMarsFilter(ImagePreview_Activity.this);
                            image.setImageBitmap(haan.processFilter(bitmap));

                            singleCamBitmap = haan.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }

                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_6");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_6");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });

                image7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);

                            singleCamBitmap = adele.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter adele = FilterPack.getAdeleFilter(ImagePreview_Activity.this);
                            image.setImageBitmap(adele.processFilter(bitmap));

                            singleCamBitmap = adele.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }

                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_7");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_7");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });

                image8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {
                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);

                            singleCamBitmap = cruz.processFilter(bitmap);
                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter cruz = FilterPack.getCruzFilter(ImagePreview_Activity.this);
                            image.setImageBitmap(cruz.processFilter(bitmap));

                            singleCamBitmap = cruz.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }


                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_8");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_8");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });


                image9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image9.setBorderWidth(Float.parseFloat("5"));
                        image9.setBorderColor(getResources().getColor(R.color.golden));

                        image1.setBorderWidth(Float.parseFloat("5"));
                        image1.setBorderColor(getResources().getColor(R.color.transparent));

                        image2.setBorderWidth(Float.parseFloat("5"));
                        image2.setBorderColor(getResources().getColor(R.color.transparent));

                        image3.setBorderWidth(Float.parseFloat("5"));
                        image3.setBorderColor(getResources().getColor(R.color.transparent));

                        image4.setBorderWidth(Float.parseFloat("5"));
                        image4.setBorderColor(getResources().getColor(R.color.transparent));

                        image5.setBorderWidth(Float.parseFloat("5"));
                        image5.setBorderColor(getResources().getColor(R.color.transparent));

                        image6.setBorderWidth(Float.parseFloat("5"));
                        image6.setBorderColor(getResources().getColor(R.color.transparent));

                        image7.setBorderWidth(Float.parseFloat("5"));
                        image7.setBorderColor(getResources().getColor(R.color.transparent));

                        image8.setBorderWidth(Float.parseFloat("5"));
                        image8.setBorderColor(getResources().getColor(R.color.transparent));

                        if (camera_without_join==1) {

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(newfile.getAbsolutePath());
                            bitmap = bitmap.copy( Bitmap.Config.RGB_565 , true);

                            Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);

                            singleCamBitmap = metropolis.processFilter(bitmap);

                            image.setImageBitmap(singleCamBitmap);

                            camera_without_join_edit=1;

                        }
                        else if (camera_with_join==1){

                            File f = new File(paths.get(0));
                            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                            bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

                            Filter metropolis = FilterPack.getMetropolis(ImagePreview_Activity.this);
                            image.setImageBitmap(metropolis.processFilter(bitmap));

                            singleCamBitmap = metropolis.processFilter(bitmap);

                            camera_with_join_edit=1;

                        }
                        else if (multiple_without_join==1){
                            multiple_edit_list.remove(multiple_current_position);

                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_9");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }
                        else if (multiple_with_join==1){

                            multiple_edit_list.remove(multiple_current_position);
                            multiple_edit_list.add(multiple_current_position,String.valueOf(multiple_current_position)+"_9");

                            adapter = new Multiple_ViewPager_Adapter(ImagePreview_Activity.this,paths,multiple_edit_list,multiple_current_position);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);

                            viewPager.setCurrentItem(multiple_current_position);

                        }

                    }
                });
            }
        }



}


}
