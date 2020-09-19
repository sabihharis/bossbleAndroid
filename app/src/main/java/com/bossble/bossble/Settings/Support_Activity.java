package com.bossble.bossble.Settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bossble.bossble.R;

import androidx.appcompat.app.AppCompatActivity;

public class Support_Activity extends AppCompatActivity {

    TextView txtsupport,txtdes1,emailheading,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_);

        txtsupport = findViewById(R.id.txtsupport);
        txtdes1 = findViewById(R.id.txtdes1);
        emailheading = findViewById(R.id.emailheading);
        email = findViewById(R.id.email);

        Fonts();


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email.getText().toString()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Bossble Contact Support");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {

                }

            }
        });

    }

    public void Fonts()
    {
        txtsupport.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        emailheading.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtdes1.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        email.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
    }

}
