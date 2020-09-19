package com.bossble.bossble.ProfileSetup;

/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;

public class Full_Profile_Picture_Activity extends AppCompatActivity {

    ImageView image;
    String img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__profile__picture_);





        image = findViewById(R.id.image);

        if (getIntent().hasExtra("image")){
            img = getIntent().getStringExtra("image");
        }



        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(Full_Profile_Picture_Activity.this)
                .load(img)
                .apply(options)
                .into(image);



    }
}
