package com.bossble.bossble.Notifications;

import android.content.SharedPreferences;
import android.util.Log;

import com.bossble.bossble.Constant.Constant;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Fawad on 6/23/2020.
 */

public class InstanceIDService extends FirebaseInstanceIdService  {
    SharedPreferences preferences;
    final String TAG = "Firebase";
    // StorageUtility storage;
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "Refreshed token: " + refreshedToken);

        if(refreshedToken != null){
            // storage.setDeviceToken(refreshedToken);
            preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
            preferences.edit().putString(Constant.REG_TOKEN,refreshedToken).commit();
            String t=preferences.getString(Constant.REG_TOKEN,"");
            Log.e(TAG, "Refreshedtoken: " + t);
        }
    }
}
