package com.bossble.bossble.OnBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
/*
import androidx.core.app.Fragment;
import androidx.core.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
*/
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.Adapter.CustomViewpager;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.R;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_Activity;

import java.util.ArrayList;
import java.util.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class On_Boarding_Activity extends AppCompatActivity {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    ViewPager pager;
    CircleIndicator indicator;
    CustomViewpager customViewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    TextView txtskip;
    Button btncontinue;

    String video_duration;

    String vdo;
    int page=0;
    int a=0;

    Timer timer;
    final long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on__boarding_);

        FindByIds();
        fonts();
        //setupOnboardings();

        vdo = getIntent().getStringExtra("vdo");
        videoBG.setVideoURI(Uri.parse(vdo));
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);

                if (mcurrentVideoPosition !=0)
                {
                    mMediaPlayer.seekTo(mcurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        preferences.edit().putString(Constant.FIRST_TIME,"f").commit();

        fragments.add(new Fragment_OnBoarding_1());
        fragments.add(new Fragment_OnBorading_2());
        fragments.add(new Fragment_OnBoarding_3());
        customViewPager = new CustomViewpager(getSupportFragmentManager(),fragments);
        pager.setAdapter(customViewPager);
        indicator.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    pager.setOnTouchListener(new View.OnTouchListener()
                    {
                        @Override
                        public boolean onTouch(View v, MotionEvent event)
                        {
                            return true;
                        }
                    });

                    indicator.setVisibility(View.GONE);
                    txtskip.setVisibility(View.GONE);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mcurrentVideoPosition  =videoBG.getCurrentPosition();
                            Intent intent = new Intent(On_Boarding_Activity.this, Signup_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                            startActivity(intent);                        }
                    }, 2000);



                }
                else{
                    indicator.setVisibility(View.VISIBLE);
                }
            }
        });


        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(On_Boarding_Activity.this,Signup_Activity.class);
                startActivity(intent);
            }
        });



    }

/*
    private void setupOnboardings(){
        pager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        if (a==0){

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (page != 3) {
                        page++;
                    }
                    pager.setCurrentItem(page, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);

        }
    }
*/
    private void FindByIds(){
        videoBG = findViewById(R.id.videoView);
        txtskip = findViewById(R.id.txtskip);
        pager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        btncontinue = findViewById(R.id.btncontinue);
    }
    @Override
    protected void onPause() {
        super.onPause();

        mcurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    Boolean twice=false;
    @Override
    public void onBackPressed() {
        if (twice==true){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice=true;
        Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);

    }
    public void fonts()
    {
        txtskip.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        btncontinue.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));


    }
}
