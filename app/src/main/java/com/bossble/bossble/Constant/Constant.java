package com.bossble.bossble.Constant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bossble.bossble.CameraWork.Camera_Activity;
import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.Notifications.Notification_Activity;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.R;
import com.bossble.bossble.Search.Search_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Fawad on 5/8/2020.
 */

public class Constant {

    public static String BASE_URL = "https://bossble.com/api/";
    public static final String LOGGED_IN = "lin";
    public static String PREF_BASE = "hdkjhkad";
    public static String FIRST_TIME = "yuydyyu";
    public static String USER_ID = "ghsxhh";
    public static String EMAIL_ID = "ehdciouyoy";
    public static String MOBILE = "kdhuyuioy";
    public static String CODE = "hdshk";
    public static String PHONE = "ydiuy";
    public static String COUNTRY = "dcggidcuyi";
    public static String PROFILE_PICTURE ="jhdscho";
    public static String REG_TOKEN ="dsytyucdt";
    public static String NOTIIFICATION_FLAG ="hxihh";
    public static String PRIVATE_MESSAGES ="jhxcuhyu";
    public static String ADMIRE ="dcuy";
    public static String CHALLENGE_REQUEST ="icudiou";
    public static String COMMENTS ="hscui";
    public static String LIKES ="gdytxugyu";
    public static String CHAT_NOTIFICATION ="dcyuysuxyy";
    public static String NEAR ="whxuiyii";


    public static final String API_KEY_PART_1 = "AIzaSyBMn";

    public static final String API_KEY_PART_2 = "gaPSgufYrW0";

    public static final String API_KEY_PART_3 = "BuWHLsCweOj";

    public static final String API_KEY_PART_4 = "MzI_xuVA";


    public static void bottomNav(final Activity activity, int position, final ScrollView scrollView, final String title){

        final String vdo = String.valueOf(Uri.parse("android.resource://"+activity.getPackageName()+"/"+R.raw.vid202005080004));


        final RoundedImageView create = activity.findViewById(R.id.create);
        final RoundedImageView userimage = activity.findViewById(R.id.userimage);

        final RelativeLayout relative = activity.findViewById(R.id.relative);
        TextView createchallenge = activity.findViewById(R.id.createchallenge);
        TextView createcampaign = activity.findViewById(R.id.createcampaign);
        final TextView text = activity.findViewById(R.id.text);
        final ImageView avibackground = activity.findViewById(R.id.avibackground);

        createcampaign.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        createchallenge.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Regular.ttf"));
        text.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/Roboto-Medium.ttf"));

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) activity.findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.nav_home, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.nav_search, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("",  R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.nav_notifications, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("", R.drawable.nav_profile, R.color.white);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);



        SharedPreferences prfs = activity.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
        String url = prfs.getString(Constant.PROFILE_PICTURE,"");
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(activity)
                .load(url)
                .apply(options)
                .into(userimage);


        if (title.equals("profile"))
        {
            userimage.setBorderWidth(Float.parseFloat("5"));
            userimage.setBorderColor(activity.getResources().getColor(R.color.golden));

        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (relative.getVisibility()==View.GONE){

                    Animation RightSwipe = AnimationUtils.loadAnimation(activity, R.anim.slideup);
                    relative.startAnimation(RightSwipe);
                    avibackground.setVisibility(View.VISIBLE);
                    relative.setVisibility(View.VISIBLE);

                    create.setBorderWidth(Float.parseFloat("8"));
                    create.setBorderColor(activity.getResources().getColor(R.color.golden));
                }
                else{
                    Animation RightSwipe = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                    relative.startAnimation(RightSwipe);
                    avibackground.setVisibility(View.GONE);
                    relative.setVisibility(View.GONE);

                    create.setBorderWidth(Float.parseFloat("0"));
                    create.setBorderColor(activity.getResources().getColor(R.color.transparent));

                }
            }
        });

        createcampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation RightSwipe = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                relative.startAnimation(RightSwipe);
                relative.setVisibility(View.GONE);
                avibackground.setVisibility(View.GONE);

                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));

                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","campaign");
                activity.startActivity(intent);

            }
        });

        createchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation RightSwipe = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                relative.startAnimation(RightSwipe);
                relative.setVisibility(View.GONE);
                avibackground.setVisibility(View.GONE);
                Constant.ChallengeTypeDialog(activity,create);

                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));

            }
        });

        avibackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation RightSwipe = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                relative.startAnimation(RightSwipe);
                relative.setVisibility(View.GONE);
                avibackground.setVisibility(View.GONE);

                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));

            }
        });



        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if(position==0){

                    if (title.equals("home")){
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                    else {

                        Intent intent = new Intent(activity, Home_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);

                    }

                }
                else if(position==1){
                    if (title.equals("search"))
                    {

                    }
                    else
                    {
                        Intent intent = new Intent(activity, Search_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("bottom","bottom");
                        activity.startActivity(intent);
                    }

                }
                else if(position==2){

                }

                else if(position==3){

                    if (title.equals("notifications"))
                    {

                    }
                    else
                    {
                        Intent intent = new Intent(activity, Notification_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("bottom","bottom");
                        activity.startActivity(intent);

                    }

                }
                else if(position==4){

                    if (title.equals("profile"))
                   {

                   }
                   else
                   {
                       Intent intent = new Intent(activity, Personal_Profile_Activity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("bottom","bottom");
                       activity.startActivity(intent);

                   }

                }

                return false;
            }
        });

        bottomNavigation.setCurrentItem(position, false);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(Color.parseColor("#F0C30E"));
        bottomNavigation.setInactiveColor(Color.parseColor("#262335"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

    }

    public static void ChallengeTypeDialog(final Activity activity, final RoundedImageView create){

        final Dialog mybuilder=new Dialog(activity);
        mybuilder.setContentView(R.layout.challengetype_dialog);

      //  mybuilder.setCancelable(false);



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
                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","challenge");
                intent.putExtra("type","selfchallenge");
                activity.startActivity(intent);

                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));
            }
        });
        oneononechallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","challenge");
                intent.putExtra("type","oneononechallenge");
                activity.startActivity(intent);
                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));
            }
        });
        groupchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","challenge");
                intent.putExtra("type","groupchallenge");
                activity.startActivity(intent);

                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));
            }
        });
        openchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","challenge");
                intent.putExtra("type","openchallenge");
                activity.startActivity(intent);
                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));

            }
        });
        locationchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();
                Intent intent = new Intent(activity,Camera_Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from","challenge");
                intent.putExtra("type","locationchallenge");
                activity.startActivity(intent);
                create.setBorderWidth(Float.parseFloat("0"));
                create.setBorderColor(activity.getResources().getColor(R.color.transparent));

            }
        });


        mybuilder.show();
        mybuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = mybuilder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
    }

    public static void LikeSuccessToast(Activity context,String message){
        new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title(context.getResources().getString(R.string.Successfully))
                .titleTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Ubuntu-Light.ttf"))
                .titleSizeInSp(15)
                .message(message)
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(context.getResources().getDrawable(R.drawable.success_toast_background))
                .duration(2000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }

    public static void SuccessToast(Activity context,String message){
        new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title(context.getResources().getString(R.string.Success))
                .titleTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Ubuntu-Light.ttf"))
                .titleSizeInSp(15)
                .message(message)
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(context.getResources().getDrawable(R.drawable.success_toast_background))
                .duration(2000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }

    public static void ErrorToast(Activity context,String message){
        new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title(context.getResources().getString(R.string.Error))
                .titleTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Ubuntu-Light.ttf"))
                .titleSizeInSp(15)
                .message(message)
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(context.getResources().getDrawable(R.drawable.error_toast_background))
                .duration(3000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


}
