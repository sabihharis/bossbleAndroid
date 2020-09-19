package com.bossble.bossble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
/*
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.New_OnBoarding.New_Video_Activity;
import com.bossble.bossble.Notifications.Notification_Activity;
import com.bossble.bossble.OnBoarding.On_Boarding_Activity;
import com.bossble.bossble.SigninSignup.SigninSignup.Signup_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Splash_Activity extends AppCompatActivity {
    boolean LOGGED_IN = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String vdo = String.valueOf(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid202005080004));

        hashFromSHA1("09:49:8E:4C:58:D9:0A:14:62:8D:86:7B:0F:84:9B:11:91:D9:5D:8D");

        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_);

       // printHashKey(Splash_Activity.this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("FCMTOKEnFail", String.valueOf(task.getException()));
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                        preferences.edit().putString(Constant.REG_TOKEN,token).commit();
                        String t=preferences.getString(Constant.REG_TOKEN,"");
                        Log.e("FCMTOKEn",t);

                    }
                });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(Splash_Activity.this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                Uri deepLink = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();

                                    String id = deepLink.getQueryParameter("challengeId");



                                    Intent intent = new Intent(Splash_Activity.this,Details_Activity.class);
                                    intent.putExtra("uri",String.valueOf(deepLink));
                                    intent.putExtra("iddd",id);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                                else {
                                    if (getIntent().getExtras() != null) {

                                        String extra = String.valueOf(getIntent().getExtras());
                                        if (extra.contains("high")){
                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                            if (preferences.contains(Constant.FIRST_TIME)) {

                                                LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
                                                if (LOGGED_IN) {

                                                    Intent intent = new Intent(Splash_Activity.this,Notification_Activity.class);
                                                    intent.putExtra("bottom", "bottom");
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Intent i = new Intent(Splash_Activity.this, New_OnBoardings_Activity.class);
                                                    i.putExtra("vdo",vdo);
                                                    i.putExtra("from","splash");
                                                    startActivity(i);
                                                }

                                            }
                                        }
                                        else {
                                            SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                            if (preferences.contains(Constant.FIRST_TIME)) {

                                                LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
                                                if (LOGGED_IN) {

                                                    Intent i = new Intent(Splash_Activity.this, Home_Activity.class);
                                                    i.putExtra("refresh","refresh");
                                                    startActivity(i);
                                                }
                                                else {
                                                    Intent i = new Intent(Splash_Activity.this, New_OnBoardings_Activity.class);
                                                    i.putExtra("vdo",vdo);
                                                    i.putExtra("from","splash");
                                                    startActivity(i);
                                                }

                                            }

                                            else {
                                                Intent intent = new Intent(Splash_Activity.this, New_Video_Activity.class);
                                                intent.putExtra("vdo",vdo);
                                                startActivity(intent);
                                            }
                                        }

                                    }
                                    else {
                                        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                                        if (preferences.contains(Constant.FIRST_TIME)) {

                                            LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
                                            if (LOGGED_IN) {

                                                Intent i = new Intent(Splash_Activity.this, Home_Activity.class);
                                                i.putExtra("refresh","refresh");
                                                startActivity(i);
                                            }
                                            else {
                                                Intent i = new Intent(Splash_Activity.this, New_OnBoardings_Activity.class);
                                                i.putExtra("vdo",vdo);
                                                i.putExtra("from","splash");
                                                startActivity(i);
                                            }

                                        }

                                        else {
                                            Intent intent = new Intent(Splash_Activity.this, New_Video_Activity.class);
                                            intent.putExtra("vdo",vdo);
                                            startActivity(intent);
                                        }
                                    }

                                }
                            }
                        })
                        .addOnFailureListener(Splash_Activity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               // Constant.ErrorToast(Splash_Activity.this,getResources().getString(R.string.NoInternetConnection));
                            }
                        });

            }
        },2000);

    }

    public void hashFromSHA1(String sha1) {
        String[] arr = sha1.split(":");
        byte[] byteArr = new  byte[arr.length];

        for (int i = 0; i< arr.length; i++) {
            byteArr[i] = Integer.decode("0x" + arr[i]).byteValue();
        }

        Log.e("hash : ", Base64.encodeToString(byteArr, Base64.NO_WRAP));
    }


/*
    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("keyhash",hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
        }
    }
*/
}
