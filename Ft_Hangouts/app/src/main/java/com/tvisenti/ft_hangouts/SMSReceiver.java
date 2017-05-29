package com.tvisenti.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by tvisenti on 5/26/17.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strNumber = "";
        String strMessage = "";

        DatabaseHelper myDb = DatabaseHelper.getInstance(context);

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }
                else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) (pdus != null ? pdus[i] : null));
                }
                strNumber += messages[i].getOriginatingAddress();
                strMessage += messages[i].getMessageBody();
            }

            strNumber = strNumber.replace("+33", "0");
            Toast.makeText(context, "Message: " + strNumber, Toast.LENGTH_SHORT).show();
            Intent broadcastReceiver = new Intent();
            broadcastReceiver.setAction("SMS_RECEIVED_ACTION");
            broadcastReceiver.putExtra("number", strNumber);
            broadcastReceiver.putExtra("message", strMessage);
            context.sendBroadcast(broadcastReceiver);
        }
    }
}