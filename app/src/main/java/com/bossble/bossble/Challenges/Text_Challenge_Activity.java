package com.bossble.bossble.Challenges;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
/*
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.v7.app.AppCompatActivity;
*/
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Text_Challenge_Activity extends AppCompatActivity {

    RelativeLayout relative;
    EditText text;
    ImageView fonts,color;
    int fontcount=0;
    String fontname="Roboto-Regular.ttf";
    String colorname="#275EA3";
    String description="";
    RoundedImageView post;
    String id="";
    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    String from="";
    String join="";
    String cid="";
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        //EmojiCompat.init(config);

        setContentView(R.layout.activity_text__challenge_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        relative = findViewById(R.id.relative);
        text = findViewById(R.id.text);
        fonts = findViewById(R.id.fonts);
        color = findViewById(R.id.color);
        post = findViewById(R.id.post);
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);


        text.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

        if (getIntent().hasExtra("from")){

            if (getIntent().hasExtra("join")){

                join = getIntent().getStringExtra("join");
                cid = getIntent().getStringExtra("cid");
                type = getIntent().getStringExtra("type");
            }

            from = getIntent().getStringExtra("from");
            if (from.equals("campaign")){
                id="5";
            }
            else if (from.equals("challenge")){
                String type = getIntent().getStringExtra("type");

                if (type.equals("selfchallenge")){
                    id="4";
                }
                else if (type.equals("oneononechallenge")){
                    id="3";
                }
                else if (type.equals("groupchallenge")){
                    id="2";
                }
                else if (type.equals("openchallenge")){
                    id="1";
                }
                else if (type.equals("locationchallenge")){
                    id="6";
                }

            }
        }

        fonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontcount++;

                if (fontcount==1){
                    fontname="Roboto-Bold.ttf";
                }
                else if (fontcount==2){
                    fontname="Roboto-BoldCondensed.ttf";
                }
                else if (fontcount==3){
                    fontname="Roboto-BoldCondensedItalic.ttf";
                }
                else if (fontcount==4){
                    fontname="Roboto-BoldItalic.ttf";
                }
                else if (fontcount==5){
                    fontname="Roboto-Italic.ttf";
                }
                else if (fontcount==6){
                    fontname="Roboto-Light.ttf";
                }
                else if (fontcount==7){
                    fontname="Roboto-LightItalic.ttf";
                }
                else if (fontcount==8){
                    fontname="Roboto-Medium.ttf";
                }
                else if (fontcount==9){
                    fontname="Roboto-MediumItalic.ttf";
                }
                else if (fontcount==10){
                    fontname="Roboto-Regular.ttf";
                }
                else if (fontcount==11){
                    fontname="Roboto-Thin.ttf";
                }
                else if (fontcount==12){
                    fontname="Roboto-ThinItalic.ttf";
                }
                else if (fontcount==13){
                    fontname="Ubuntu-Bold.ttf";
                }
                else if (fontcount==14){
                    fontname="Ubuntu-BoldItalic.ttf";
                }
                else if (fontcount==15){
                    fontname="Ubuntu-Italic.ttf";
                }
                else if (fontcount==16){
                    fontname="Ubuntu-Light.ttf";
                }
                else if (fontcount==17){
                    fontname="Ubuntu-LightItalic.ttf";
                }
                else if (fontcount==18){
                    fontname="Ubuntu-Medium.ttf";
                }
                else if (fontcount==19){
                    fontname="Ubuntu-MediumItalic.ttf";
                }
                else if (fontcount==20){
                    fontname="Ubuntu-Regular.ttf";
                }
                else if (fontcount==21){
                    fontname="Roboto-Regular.ttf";
                    fontcount=0;
                }
                text.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/"+fontname));

            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colordialog();

            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (text.getText().toString().length()>0){
                    post.setVisibility(View.VISIBLE);
                }
                else {
                    post.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                String uid = preferences.getString(Constant.USER_ID,"");
                createCgallenge(id,uid);
*/
                text.setFocusable(false);
                try {

                    String state = Environment.getExternalStorageState();
                    if (!state.equals(Environment.MEDIA_MOUNTED)){
                    }
                    else {
                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                        if (!folder_gui.exists()){
                            folder_gui.mkdirs();
                            Bitmap bitmap = viewToBitmap(relative);
                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test.jpeg");
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                            output.close();
                        }
                        else {

                            Bitmap bitmap = viewToBitmap(relative);
                            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images/test"+folder_gui.listFiles().length+".jpeg");
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                            output.close();

                        }
                    }

                    avibackground.setVisibility(View.VISIBLE);
                    avi.smoothToShow();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            avibackground.setVisibility(View.GONE);
                            avi.smoothToHide();

                            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                            File [] files =folder_gui.listFiles();
                            int index =  folder_gui.listFiles().length;

                            Collections.sort(Arrays.asList(files),new Comparor());

                            String path =files[index-1].getAbsolutePath();

                            if (!join.equals("")){

                                if (from.equals("campaign")){
                                    Intent intent = new Intent(Text_Challenge_Activity.this,ImagePreview_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("galleryimage",path);
                                    intent.putExtra("from",from);
                                    intent.putExtra("join",join);
                                    intent.putExtra("cid", cid);
                                    intent.putExtra("type", type);
                                    startActivity(intent);
                                }
                                else if (from.equals("challenge")){
                                    Intent intent = new Intent(Text_Challenge_Activity.this,ImagePreview_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("galleryimage",path);
                                    intent.putExtra("type",getIntent().getStringExtra("type"));
                                    intent.putExtra("from",from);
                                    intent.putExtra("join",join);
                                    intent.putExtra("cid", cid);
                                    intent.putExtra("type", type);
                                    startActivity(intent);
                                }
                            }
                            else {
                                if (from.equals("campaign")){
                                    Toast.makeText(getApplicationContext(),"emoji",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Text_Challenge_Activity.this,Create_Campaign_Acitivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("loadimage",path);
                                    intent.putExtra("emoji","emoji");
                                    startActivity(intent);
                                }
                                else if (from.equals("challenge")){
                                    Intent intent = new Intent(Text_Challenge_Activity.this,Create_Challenge_Acitivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("loadimage",path);
                                    intent.putExtra("type",getIntent().getStringExtra("type"));
                                    intent.putExtra("emoji","emoji");

                                    startActivity(intent);
                                }
                            }
                        }
                    }, 2000);
                }

                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });


    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void colordialog(){

        final Dialog mybuilder=new Dialog(Text_Challenge_Activity.this);
        mybuilder.setContentView(R.layout.color_picker_dialog);

        TextView heading,done;
        RoundedImageView one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen;

        heading=mybuilder.findViewById(R.id.heading);
        done=mybuilder.findViewById(R.id.done);
        one=mybuilder.findViewById(R.id.one);
        two=mybuilder.findViewById(R.id.two);
        three=mybuilder.findViewById(R.id.three);
        four=mybuilder.findViewById(R.id.four);
        five=mybuilder.findViewById(R.id.five);
        six=mybuilder.findViewById(R.id.six);
        seven=mybuilder.findViewById(R.id.seven);
        eight=mybuilder.findViewById(R.id.eight);
        nine=mybuilder.findViewById(R.id.nine);
        ten=mybuilder.findViewById(R.id.ten);
        eleven=mybuilder.findViewById(R.id.eleven);
        twelve=mybuilder.findViewById(R.id.twelve);
        thirteen=mybuilder.findViewById(R.id.thirteen);
        fourteen=mybuilder.findViewById(R.id.fourteen);
        fifteen=mybuilder.findViewById(R.id.fifteen);

        heading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        done.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#275EA3";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#5C88BF";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#51C2EE";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#56AFB5";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#7CBA52";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#8BC453";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#F8B230";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#E97135";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#C1987C";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#E03D46";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#FF4640";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#D07676";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        thirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#BD89AE";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        fourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#9047F0";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorname="#9086BB";
                relative.setBackgroundColor(Color.parseColor(colorname));
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
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

    private class Comparor implements Comparator<File> {


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

}
