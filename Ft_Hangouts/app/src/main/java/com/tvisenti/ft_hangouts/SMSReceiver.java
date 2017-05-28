package com.tvisenti.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by tvisenti on 5/26/17.
 */

public class SMSReceiver extends BroadcastReceiver {

    DatabaseHelper myDb;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        myDb = DatabaseHelper.getInstance(context);
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
//                strMessage += "SMS From: " + messages[i].getOriginatingAddress();
//                strMessage += " : ";
//                strMessage += messages[i].getMessageBody();
//                strMessage += "\n";
                Message mySms = new Message(messages[i].getOriginatingAddress(), messages[i].getMessageBody(), Utils.dateToString(), 1);
                myDb.insertDataSms(mySms);
            }

            Log.e("SMS", strMessage);
            Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();


        }
    }
}