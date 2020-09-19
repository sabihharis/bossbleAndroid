package com.bossble.bossble.Campaigns;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Adapter.Multiple_Campaign_Images;
import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.Challenges.Text_Challenge_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;
import id.zelory.compressor.Compressor;

public class Create_Campaign_Acitivity extends AppCompatActivity implements Multiple_Campaign_Images.Callback {

    ImageView fulllogo;
    EditText title,description,tags;
    TextView mediatxt,heading;
    RoundedImageView image;
    Button startcampaign;
    String path = "";
    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    ArrayList<File> files = new ArrayList<>();
    ArrayList<String> paths = new ArrayList<>();

    RecyclerView multiplerv;
    Multiple_Campaign_Images adapter;
    ImageView moreimages;
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__campaign__acitivity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Constant.bottomNav(Create_Campaign_Acitivity.this,0,sv,"");

        sv=findViewById(R.id.sv);
        fulllogo=findViewById(R.id.fulllogo);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        tags=findViewById(R.id.tags);
        mediatxt=findViewById(R.id.mediatxt);
        image=findViewById(R.id.image);
        heading=findViewById(R.id.heading);
        startcampaign=findViewById(R.id.startcampaign);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        multiplerv = findViewById(R.id.multiplerv);
        moreimages = findViewById(R.id.moreimages);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        multiplerv.setLayoutManager(layoutManager);
        multiplerv.setHasFixedSize(true);


        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        title.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        tags.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        mediatxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        startcampaign.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));

        fulllogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Campaign_Acitivity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        startcampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Campaign_Acitivity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        startcampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(Create_Campaign_Acitivity.this);

                if (Constant.isOnline(Create_Campaign_Acitivity.this)){

                    createCampaign();

                }
                else{
                    Constant.ErrorToast(Create_Campaign_Acitivity.this,getResources().getString(R.string.NoInternetConnection));
                }
            }
        });


        if (getIntent().hasExtra("galleryimage")){


            if (getIntent().hasExtra("list")){

                if (getIntent().hasExtra("title")){
                    title.setText(getIntent().getStringExtra("title"));
                    description.setText(getIntent().getStringExtra("description"));
                    tags.setText(getIntent().getStringExtra("hashtags"));
                }

                paths = getIntent().getStringArrayListExtra("galleryimage");
                adapter = new Multiple_Campaign_Images(Create_Campaign_Acitivity.this,paths,title,description,tags,Create_Campaign_Acitivity.this);
                multiplerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                multiplerv.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);


                Log.e("pathsIindex",paths.get(0));

                if (paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }

                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = title.getText().toString();
                        String desc = description.getText().toString();
                        String hash = tags.getText().toString();
                        Intent intent = new Intent(Create_Campaign_Acitivity.this,Camera_Activity2.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("title",name);
                        intent.putExtra("description",desc);
                        intent.putExtra("hashtags",hash);
                        intent.putStringArrayListExtra("list",paths);
                        intent.putExtra("from","campaign");
                        startActivity(intent);

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
                //files.add(file);

            }
            else{
                path = getIntent().getStringExtra("galleryimage");
                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(Create_Campaign_Acitivity.this)
                        .load(path)
                        .apply(options2)
                        .into(image);

                File file= new File(path);
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
            }
        }
        else if (getIntent().hasExtra("loadimage")){
            path = getIntent().getStringExtra("loadimage");
            RequestOptions options2 = new RequestOptions();
            options2.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(Create_Campaign_Acitivity.this)
                    .load(path)
                    .apply(options2)
                    .into(image);

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
        }
    }

    public void createCampaign(){
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");
        String name = title.getText().toString();
        final String desc = description.getText().toString();
        String hash = tags.getText().toString();
        File f = new File(path);

        if (name.isEmpty()){
            title.setError(getResources().getString(R.string.Fieldisrequired));
            title.requestFocus();
        }
        else if (files.size()==0){
            Constant.ErrorToast(Create_Campaign_Acitivity.this,getResources().getString(R.string.NoMediaAdded));
        }
        else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);
            RequestParams params = new RequestParams();
            params.add("challenge_type_id","5");
            params.add("user_id",uid);
            params.add("title",name);
            params.add("descritpion",desc);
            params.add("hashtags",hash);
            params.add("attachment_type","1");
            params.add("privacy_level_id","1");

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
            client.post(Constant.BASE_URL +"post/post/create_challenge", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("campaignres",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true")){


                            JSONObject item = jsonObject.getJSONObject("dataSet");
                            final String id = item.getString("id");
                            final String user_id = item.getString("user_id");
                            final String title = item.getString("title");
                            final String descritpion = item.getString("descritpion");
                            final String tags = item.getString("tags");
                            String created_at = item.getString("created_at");
                            final String username = item.getString("username");
                            final String profile_image = item.getString("profile_image");

                            String description_background = "";
                            final String description_fonts = "";
                            String challenge_attempted_count = "";
                            String image = "";
                            if (item.has("image")){
                                 image = item.getString("image");
                            }
                            else if (item.has("video")){
                                image = item.getString("video");
                            }

                            final String comments_count = item.getString("comments_count");
                            final String like_count = item.getString("like_count");
                            final String view_count = item.getString("view_count");
                            final String country = item.getString("country");
                            String challenge_title = item.getString("challenge_title");
                            String user_like = item.getString("user_like");
                            String user_like_reward = item.getString("user_like_reward");




                            Constant.SuccessToast(Create_Campaign_Acitivity.this,getResources().getString(R.string.CampaignCreated));


                            final String finalImage = image;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Create_Campaign_Acitivity.this,Details_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    intent.putExtra("refresh","refresh");
                                    intent.putExtra("id",id);
                                    intent.putExtra("username",username);
                                    intent.putExtra("userimage",profile_image);
                                    intent.putExtra("user_id",user_id);

                                    intent.putExtra("image", finalImage);

                                    intent.putExtra("fonts",description_fonts);
                                    intent.putExtra("likes",like_count);
                                    intent.putExtra("comments",comments_count);
                                    intent.putExtra("title",title);
                                    intent.putExtra("description",descritpion);
                                    intent.putExtra("tags",tags);
                                    intent.putExtra("type","campaign");
                                    intent.putExtra("country",country);
                                    intent.putExtra("view_count",view_count);
                                    intent.putExtra("back","back");

                                    startActivity(intent);
                                }
                            }, 2000);
                        }
                        else if (status.equals("false")){
                            Constant.ErrorToast(Create_Campaign_Acitivity.this,errorMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("campaignresF",response);

                    }
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    Constant.ErrorToast(Create_Campaign_Acitivity.this,getResources().getString(R.string.Somethingwentwrong));

                }
            });

        }

    }

    private void backdialog(){

        final Dialog mybuilder=new Dialog(Create_Campaign_Acitivity.this);
        mybuilder.setContentView(R.layout.backpress_popup_layout);

        TextView note,description;
        Button yes,no;

        note=mybuilder.findViewById(R.id.note);
        description=mybuilder.findViewById(R.id.description);
        yes=mybuilder.findViewById(R.id.yes);
        no=mybuilder.findViewById(R.id.no);

        note.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));

        description.setText(getResources().getString(R.string.Doyouwanttocancelyourcampaign));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                Intent intent = new Intent(Create_Campaign_Acitivity.this,Home_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
            }
        });


        mybuilder.show();
        mybuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = mybuilder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);



    }

    @Override
    public void onBackPressed() {

        backdialog();

    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    @Override
    public void onRemoved(ArrayList<String> imagelist, String listsize, String Pathremoved, String indexremoved) {
        if (Integer.parseInt(listsize)<6){
            moreimages.setVisibility(View.VISIBLE);
        }
        paths = imagelist;
        files.remove(Integer.parseInt(indexremoved));

    }}
