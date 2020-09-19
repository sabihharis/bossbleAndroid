package com.bossble.bossble.New_OnBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.R;
import com.bossble.bossble.Splash_Activity;

import androidx.appcompat.app.AppCompatActivity;

public class New_Video_Activity extends AppCompatActivity {

    VideoView videoView;

    MediaPlayer mMediaPlayer;
    int video;
    TextView skip;

    int mcurrentVideoPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new__video_);
        videoView = findViewById(R.id.videoView);
        skip = findViewById(R.id.skip);
        skip.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
        preferences.edit().putString(Constant.FIRST_TIME, "1").commit();


        final String vdo = String.valueOf(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.newtextvdo));

        videoView.setVideoURI(Uri.parse(vdo));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                Intent i = new Intent(New_Video_Activity.this, New_OnBoardings_Activity.class);
                i.putExtra("vdo", String.valueOf(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid202005080004)));
                i.putExtra("from", "splash");
                startActivity(i);


            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(New_Video_Activity.this, New_OnBoardings_Activity.class);
                i.putExtra("vdo", String.valueOf(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid202005080004)));
                i.putExtra("from", "splash");
                startActivity(i);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            video = mMediaPlayer.getCurrentPosition();
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaPlayer != null) {
            videoView.seekTo(video);
            videoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
    }

    Boolean twice = false;

    @Override
    public void onBackPressed() {
        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);


    }
}