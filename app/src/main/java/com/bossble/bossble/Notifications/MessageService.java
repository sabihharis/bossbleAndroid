package com.bossble.bossble.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.New_OnBoarding.New_OnBoardings_Activity;
import com.bossble.bossble.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Fawad on 6/23/2020.
 */

public class MessageService extends FirebaseMessagingService {
    String CHANNEL_ID = "your_name";// The id of the channel.
    CharSequence name = "Bossble";// The user-visible name of the channel.
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private static final String TAG = FirebaseMessagingService.class.getSimpleName();
    boolean LOGGED_IN = false;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "FCM Push Message: " + remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification().getBody());
    }
    private void sendNotification(String messageBody) {

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String notificationflag=preferences.getString(Constant.NOTIIFICATION_FLAG,"");


        Intent intent = new Intent();

        if (preferences.contains(Constant.FIRST_TIME)) {

            LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
            if (LOGGED_IN) {

                intent = new Intent(this, Notification_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Notification", true);
                intent.putExtra("bottom", "bottom");
            }
            else {
                final String vdo = String.valueOf(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid202005080004));
                Intent i = new Intent(this, New_OnBoardings_Activity.class);
                i.putExtra("vdo",vdo);
                i.putExtra("from","splash");
            }

        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


/*
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 */
/* Request code *//*
, intent,
                PendingIntent.FLAG_ONE_SHOT);
*/

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mBuilder = new NotificationCompat.Builder(this)
//                .setContentText("4")
                    .setSmallIcon(R.mipmap.rounded_icon)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setLights(Color.RED, 1000, 1000)
                    .setChannelId(CHANNEL_ID)
                    .setContentText(messageBody)
                    .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle("Bossble");


            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (preferences.contains(Constant.NOTIIFICATION_FLAG)){
                if (notificationflag.equals("on")){
                    mNotificationManager.createNotificationChannel(mChannel);
                    mNotificationManager.notify(0 /* ID of notification */, mBuilder.build());
                }
            }
            else{
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager.notify(0 /* ID of notification */, mBuilder.build());
            }

        }
        else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.mipmap.rounded_icon)
                    .setContentTitle("Bossble")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLights(Color.RED, 1000, 1000)
                    .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(resultPendingIntent);
            if (preferences.contains(Constant.NOTIIFICATION_FLAG)){
                if (notificationflag.equals("on")) {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                }
            }
            else{
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }

        }
    }
}





