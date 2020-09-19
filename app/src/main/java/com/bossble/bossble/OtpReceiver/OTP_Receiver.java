package com.bossble.bossble.OtpReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Fawad on 5/13/2020.
 */

public class OTP_Receiver extends BroadcastReceiver {

    private static EditText editText;

    public void setEditText(EditText editText)
    {
        OTP_Receiver.editText = editText;
    }
    @Override
    public void onReceive(Context context, Intent intent) {


        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage sms : messages)
        {
            String message = sms.getMessageBody();

            String otp = message.split(":")[1];
            Log.e("otpreceiver",otp);
            editText.setText(otp);





        }
    }
}
