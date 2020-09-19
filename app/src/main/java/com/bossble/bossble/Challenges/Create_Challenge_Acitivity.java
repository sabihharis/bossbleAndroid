package com.bossble.bossble.Challenges;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Adapter.Multiple_Campaign_Images;
import com.bossble.bossble.Adapter.Multiple_Challenge_Images;
import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.Maps.MapsActivity;
import com.bossble.bossble.People_Activity;
import com.bossble.bossble.PopupMenuFonts.CustomTypeFaceSpan;
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
import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;
import id.zelory.compressor.Compressor;

public class Create_Challenge_Acitivity extends AppCompatActivity implements Multiple_Challenge_Images.Callback {

    ImageView fulllogo;
    EditText title,description,tags;
    TextView mediatxt,heading,people;
    RoundedImageView image;
    Button postchallenge;
    String path = "";
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    ArrayList<File> files = new ArrayList<>();

    RelativeLayout challengetyperelative,publicprovate;
    TextView challengetpetxt,whicanseetxt,changemode;
    ImageView challengetpeimg;

    String type="";
    String id="";
    String pubblicprivateid="1";

    String lat_param="";
    String long_param="";
    String radius="";
    RelativeLayout selectmaps;
    TextView gotomap;

    ArrayList<String> ppl_ids = new ArrayList<>();
    ArrayList<String> ppl_name = new ArrayList<>();
    ArrayList<String> grp_ppl_name = new ArrayList<>();
    ArrayList<String> grp_ppl_id = new ArrayList<>();
    Set<String> idset = new HashSet<String>();
    Set<String> nameset = new HashSet<String>();

    String ids = "";
    String name ="";

    ArrayList<String> paths = new ArrayList<>();
    RecyclerView multiplerv;
    Multiple_Challenge_Images adapter;
    ImageView moreimages;
    ScrollView sv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__challenge__acitivity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Constant.bottomNav(Create_Challenge_Acitivity.this,0,sv,"");



        sv=findViewById(R.id.sv);
        fulllogo=findViewById(R.id.fulllogo);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        tags=findViewById(R.id.tags);
        mediatxt=findViewById(R.id.mediatxt);
        image=findViewById(R.id.image);
        heading=findViewById(R.id.heading);
        postchallenge=findViewById(R.id.postchallenge);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        people = findViewById(R.id.people);
        challengetyperelative = findViewById(R.id.challengetyperelative);
        publicprovate = findViewById(R.id.publicprovate);
        challengetpetxt = findViewById(R.id.challengetpetxt);
        whicanseetxt = findViewById(R.id.whicanseetxt);
        changemode = findViewById(R.id.changemode);
        challengetpeimg = findViewById(R.id.challengetpeimg);
        selectmaps = findViewById(R.id.selectmaps);
        gotomap = findViewById(R.id.gotomap);
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
        postchallenge.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        people.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        challengetpetxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        whicanseetxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        gotomap.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        changemode.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));



        changemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(Create_Challenge_Acitivity.this, changemode);
                popup.getMenuInflater().inflate(R.menu.menu2, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        changemode.setText(item.getTitle());
                        if (item.getTitle().equals(getResources().getString(R.string.PUBLIC))){
                            pubblicprivateid="1";
                        }
                        else if (item.getTitle().equals(getResources().getString(R.string.PRIVATE))){
                            pubblicprivateid="2";
                        }
                        return true;
                    }
                });

                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem mi = menu.getItem(i);
                    applyFontToMenuItem(mi);
                }

                popup.show(); //showing popup menu
            }
        });


        postchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                String uid = preferences.getString(Constant.USER_ID,"");
                dismissKeyboard(Create_Challenge_Acitivity.this);

                if (Constant.isOnline(Create_Challenge_Acitivity.this)){

                    createCgallenge(id,uid,pubblicprivateid);

                }
                else{
                    Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.NoInternetConnection));
                }
            }
        });


        if (getIntent().hasExtra("galleryimage")){

            if (getIntent().hasExtra("list")){

                type = getIntent().getStringExtra("type");

                paths = getIntent().getStringArrayListExtra("galleryimage");
                adapter = new Multiple_Challenge_Images(Create_Challenge_Acitivity.this,paths,title,description,tags,gotomap,type,lat_param,long_param,Create_Challenge_Acitivity.this);
                multiplerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                multiplerv.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);

                if (paths.size()<6){
                    moreimages.setVisibility(View.VISIBLE);
                }

                if (getIntent().hasExtra("title")){
                    title.setText(getIntent().getStringExtra("title"));
                    description.setText(getIntent().getStringExtra("description"));
                    tags.setText(getIntent().getStringExtra("hashtags"));
                    if (!getIntent().getStringExtra("maptext").equals("")){
                        gotomap.setText(getIntent().getStringExtra("maptext"));
                        lat_param = getIntent().getStringExtra("latitude");
                        long_param = getIntent().getStringExtra("longitude");
                    }
                }



                moreimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = title.getText().toString();
                        String desc = description.getText().toString();
                        String hash = tags.getText().toString();
                        String maptext = gotomap.getText().toString();

                        Intent intent = new Intent(Create_Challenge_Acitivity.this,Camera_Activity2.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("title",name);
                        intent.putExtra("description",desc);
                        intent.putExtra("hashtags",hash);
                        intent.putStringArrayListExtra("list",paths);
                        intent.putExtra("type",type);
                        intent.putExtra("from","challenge");

                        intent.putExtra("maptext",maptext);
                        intent.putExtra("latitude",lat_param);
                        intent.putExtra("longitude",long_param);
                        startActivity(intent);

/*
                        else if (id.equals("3") && !ids.equals("")){
                            String[] idsarray = new String[ppl_ids.size()];
                            idsarray = ppl_ids.toArray(idsarray);
                            params.add("follower_id[]",idsarray[0]);
                        }
                        // if group based
                        else if (id.equals("2") && idset.size()>0){
                            String[] idsarray = new String[idset.size()];
                            idsarray = idset.toArray(idsarray);
                            for (int i=0; i<idset.size(); i++){
                                params.add("follower_id[]",idsarray[i]);
                            }
                        }
*/

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
            else {
                type = getIntent().getStringExtra("type");
                path = getIntent().getStringExtra("galleryimage");
                RequestOptions options2 = new RequestOptions();
                options2.placeholder(R.drawable.bossble_placeholder_large);
                Glide.with(Create_Challenge_Acitivity.this)
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

            if (type.equals("selfchallenge")){

                id="4";
                challengetpetxt.setText(getResources().getString(R.string.SelfChallenge));
                challengetpeimg.setImageResource(R.drawable.self_challenge_icon);
            }
            else if (type.equals("oneononechallenge")){
                id="3";
                challengetpetxt.setText(getResources().getString(R.string.oneononeChallenge));
                challengetpeimg.setImageResource(R.drawable.one_on_one_icon);
                people.setVisibility(View.VISIBLE);

            }
            else if (type.equals("groupchallenge")){
                id="2";
                challengetpetxt.setText(getResources().getString(R.string.GroupChallenge));
                challengetpeimg.setImageResource(R.drawable.group_challenge_icon);
                people.setVisibility(View.VISIBLE);

            }
            else if (type.equals("openchallenge")){
                id="1";
                challengetpetxt.setText(getResources().getString(R.string.OpenChallenge));
                challengetpeimg.setImageResource(R.drawable.open_challenge_icon);

            }
            else if (type.equals("locationchallenge")){
                id="6";
                challengetpetxt.setText(getResources().getString(R.string.LocationBaseChallenge));
                challengetpeimg.setImageResource(R.drawable.location_challenge_icon);
                selectmaps.setVisibility(View.VISIBLE);
            }
        }
        else if (getIntent().hasExtra("loadimage")){
            path = getIntent().getStringExtra("loadimage");
            RequestOptions options2 = new RequestOptions();
            options2.placeholder(R.drawable.bossble_placeholder_large);
            Glide.with(Create_Challenge_Acitivity.this)
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
            //files.add(file);

            type = getIntent().getStringExtra("type");
            if (type.equals("selfchallenge")){

                id="4";
                challengetpetxt.setText(getResources().getString(R.string.SelfChallenge));
                challengetpeimg.setImageResource(R.drawable.self_challenge_icon);
            }
            else if (type.equals("oneononechallenge")){
                id="3";
                challengetpetxt.setText(getResources().getString(R.string.oneononeChallenge));
                challengetpeimg.setImageResource(R.drawable.one_on_one_icon);
                people.setVisibility(View.VISIBLE);

            }
            else if (type.equals("groupchallenge")){
                id="2";
                challengetpetxt.setText(getResources().getString(R.string.GroupChallenge));
                challengetpeimg.setImageResource(R.drawable.group_challenge_icon);
                people.setVisibility(View.VISIBLE);

            }
            else if (type.equals("openchallenge")){
                id="1";
                challengetpetxt.setText(getResources().getString(R.string.OpenChallenge));
                challengetpeimg.setImageResource(R.drawable.open_challenge_icon);

            }
            else if (type.equals("locationchallenge")){
                id="6";
                challengetpetxt.setText(getResources().getString(R.string.LocationBaseChallenge));
                challengetpeimg.setImageResource(R.drawable.location_challenge_icon);
                selectmaps.setVisibility(View.VISIBLE);
            }
        }

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Challenge_Acitivity.this, People_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        selectmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Challenge_Acitivity.this, MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        challengetyperelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChallengeTypeDialog(Create_Challenge_Acitivity.this);
            }
        });


    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Medium.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeFaceSpan("", font,getResources().getColor(R.color.dark_blue)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    public void createCgallenge(String challengid,String uid,String pubprivid){

        String name = title.getText().toString();
        String desc = description.getText().toString();
        String hash = tags.getText().toString();

        if (name.isEmpty()){
            title.setError(getResources().getString(R.string.Fieldisrequired));
            title.requestFocus();
        }
        else if (files.size()==0){
            Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.NoMediaAdded));
        }
        else if (id.equals("6") && lat_param.equals("")){

            Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.Locationisrequired));

        }
        else if (id.equals("3") && ppl_ids.isEmpty()){

            Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.PeopleNotSelected));

        }
        else if (id.equals("2") && idset.isEmpty()){

            Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.PeopleNotSelected));

        }
        else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("challenge_type_id",challengid);
            params.add("user_id",uid);
            params.add("title",name);
            params.add("descritpion",desc);
            params.add("hashtags",hash);
            params.add("attachment_type","1");
            params.add("privacy_level_id",pubprivid);

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

            // if location based
            if (id.equals("6") && !lat_param.equals("")){
                params.add("latitude",lat_param);
                params.add("longitude",long_param);
                params.add("radius",radius+"km");
                params.add("address",gotomap.getText().toString());
            }

            // if one-on-one
            else if (id.equals("3") && !ids.equals("")){
                String[] idsarray = new String[ppl_ids.size()];
                idsarray = ppl_ids.toArray(idsarray);
                params.add("follower_id[]",idsarray[0]);
            }

            // if group based
            else if (id.equals("2") && idset.size()>0){
                String[] idsarray = new String[idset.size()];
                idsarray = idset.toArray(idsarray);
                for (int i=0; i<idset.size(); i++){
                    params.add("follower_id[]",idsarray[i]);
                }
            }

            Log.e("params", String.valueOf(params));
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL +"post/post/create_challenge", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("challengeres",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true")){

                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, Context.MODE_PRIVATE);

                            preferences.edit().remove("id").commit();
                            preferences.edit().remove("name").commit();
                            preferences.edit().remove("lat").commit();
                            preferences.edit().remove("long").commit();
                            preferences.edit().remove("address").commit();
                            preferences.edit().remove("idset").commit();
                            preferences.edit().remove("nameset").commit();
                            preferences.edit().remove("radius").commit();


                            JSONObject item = jsonObject.getJSONObject("dataSet");
                            final String id = item.getString("id");
                            final String user_id = item.getString("user_id");
                            final String title = item.getString("title");
                            final String descritpion = item.getString("descritpion");
                            final String tags = item.getString("tags");
                            String created_at = item.getString("created_at");
                            final String username = item.getString("username");
                            final String profile_image = item.getString("profile_image");

                            final String description_fonts = "";
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


                            Constant.SuccessToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.ChallengeCreated));

                            final String finalImage = image;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Create_Challenge_Acitivity.this,Details_Activity.class);
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
                                    intent.putExtra("type","challenge");
                                    intent.putExtra("country",country);
                                    intent.putExtra("view_count",view_count);
                                    intent.putExtra("back","back");


                                    startActivity(intent);
                                }
                            }, 2000);
                        }
                        else if (status.equals("false")){
                            Constant.ErrorToast(Create_Challenge_Acitivity.this,errorMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("challengeresF",response);
                    }

                    Constant.ErrorToast(Create_Challenge_Acitivity.this,getResources().getString(R.string.Somethingwentwrong));

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                }
            });

        }

    }


    private void backdialog(){

        final Dialog mybuilder=new Dialog(Create_Challenge_Acitivity.this);
        mybuilder.setContentView(R.layout.backpress_popup_layout);

        TextView note,description;
        Button yes,no;

        note=mybuilder.findViewById(R.id.note);
        description=mybuilder.findViewById(R.id.description);
        yes=mybuilder.findViewById(R.id.yes);
        no=mybuilder.findViewById(R.id.no);

        note.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));

        description.setText("Do you want to cancel your challenge?");


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, Context.MODE_PRIVATE);

                preferences.edit().remove("id").commit();
                preferences.edit().remove("name").commit();
                preferences.edit().remove("lat").commit();
                preferences.edit().remove("long").commit();
                preferences.edit().remove("address").commit();
                preferences.edit().remove("idset").commit();
                preferences.edit().remove("nameset").commit();
                preferences.edit().remove("radius").commit();


                Intent intent = new Intent(Create_Challenge_Acitivity.this,Home_Activity.class);
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
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        if (id.equals("3")){ //one on one
            if (preferences.contains("id")){
                ids = preferences.getString("id","");
                name = preferences.getString("name","");

                if (ppl_ids.size()>0){
                    if (!ppl_ids.get(0).equals(ids)){

                        //new id
                        String des = description.getText().toString().replace(" @"+ppl_name.get(0),"");

                        ppl_ids.remove(0);
                        ppl_name.remove(0);
                        ppl_ids.add(ids);
                        ppl_name.add(name);

                        String des2 = des+" @"+ppl_name.get(0);
                        description.setText(des2);

                    }
                    else {
                        /*ppl_ids.add(ids);
                        ppl_name.add(name);
                        String des = description.getText()+" @"+ppl_name.get(0);
                        description.setText(des);*/
                    }
                }
                else {
                    ppl_ids.add(ids);
                    ppl_name.add(name);
                    String des = description.getText()+" @"+ppl_name.get(0);
                    description.setText(des);
                }
            }
        }
        if (id.equals("6")){ //location based
            if (preferences.contains("lat")){
                lat_param = preferences.getString("lat","");
                long_param = preferences.getString("long","");
                String address = preferences.getString("address","");
                radius = preferences.getString("radius","");
                gotomap.setText("");
                gotomap.setText(address);
                ppl_ids.clear();
            }
        }
        if (id.equals("2")){ //group challenge
            if (preferences.contains("idset")){

                if (idset.size()>0){
                    String des = description.getText().toString().replace(" "+String.valueOf(nameset).replace("[",""),"");
                    String des3 = des.replace("]","");

                    idset = preferences.getStringSet("idset",null);
                    nameset = preferences.getStringSet("nameset",null);
                    String des1 = des3+" "+String.valueOf(nameset).replace("[","");
                    String des2 = des1.replace("]","");
                    description.setText(des2);


                }
                else {

                    idset = preferences.getStringSet("idset",null);
                    nameset = preferences.getStringSet("nameset",null);
                    String des = description.getText()+" "+String.valueOf(nameset).replace("[","");
                    String des2 = des.replace("]","");
                    description.setText(des2);

                    Log.e("namesetvalue", String.valueOf(nameset));

                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        preferences.edit().remove("id").commit();
        preferences.edit().remove("name").commit();
        preferences.edit().remove("address").commit();
        preferences.edit().remove("lat").commit();
        preferences.edit().remove("long").commit();

        preferences.edit().remove("idset").commit();
        preferences.edit().remove("nameset").commit();
        preferences.edit().remove("radius").commit();


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


    public  void ChallengeTypeDialog(final Activity activity){

        final Dialog mybuilder=new Dialog(activity);
        mybuilder.setContentView(R.layout.challengetype_dialog);

        TextView text,selfchallenge,oneononechallenge,groupchallenge,openchallenge,locationchallenge;

        text=mybuilder.findViewById(R.id.text);
        selfchallenge=mybuilder.findViewById(R.id.selfchallenge);
        oneononechallenge=mybuilder.findViewById(R.id.oneononechallenge);
        groupchallenge=mybuilder.findViewById(R.id.groupchallenge);
        openchallenge=mybuilder.findViewById(R.id.openchallenge);
        locationchallenge=mybuilder.findViewById(R.id.locationchallenge);

        text.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Medium.ttf"));
        selfchallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        oneononechallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        groupchallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        openchallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        locationchallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));


        selfchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                id="4";
                challengetpetxt.setText(getResources().getString(R.string.SelfChallenge));
                challengetpeimg.setImageResource(R.drawable.self_challenge_icon);
                people.setVisibility(View.GONE);
                selectmaps.setVisibility(View.GONE);

            }
        });
        oneononechallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                id="3";
                challengetpetxt.setText(getResources().getString(R.string.oneononeChallenge));
                challengetpeimg.setImageResource(R.drawable.one_on_one_icon);
                people.setVisibility(View.VISIBLE);
                selectmaps.setVisibility(View.GONE);


            }
        });
        groupchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                id="2";
                challengetpetxt.setText(getResources().getString(R.string.GroupChallenge));
                challengetpeimg.setImageResource(R.drawable.group_challenge_icon);
                people.setVisibility(View.VISIBLE);
                selectmaps.setVisibility(View.GONE);

            }
        });
        openchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                id="1";
                challengetpetxt.setText(getResources().getString(R.string.OpenChallenge));
                challengetpeimg.setImageResource(R.drawable.open_challenge_icon);
                people.setVisibility(View.GONE);
                selectmaps.setVisibility(View.GONE);

            }
        });
        locationchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                id="6";
                challengetpetxt.setText(getResources().getString(R.string.LocationBaseChallenge));
                challengetpeimg.setImageResource(R.drawable.location_challenge_icon);
                selectmaps.setVisibility(View.VISIBLE);
                people.setVisibility(View.GONE);

            }
        });


        mybuilder.show();
        mybuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = mybuilder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
    }


    @Override
    public void onRemoved(ArrayList<String> imagelist, String listsize, String Pathremoved, String indexremoved) {
        if (Integer.parseInt(listsize)<6){
            moreimages.setVisibility(View.VISIBLE);
        }
        paths = imagelist;
        files.remove(Integer.parseInt(indexremoved));

    }
}
