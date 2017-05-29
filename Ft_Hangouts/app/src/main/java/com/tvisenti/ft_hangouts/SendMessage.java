package com.tvisenti.ft_hangouts;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendMessage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    DatabaseHelper myDb;
    public static CustomAdapterMessage adapter = null;
    public static ArrayList<Message> ArrayOfSms = new ArrayList<Message>();

    ListView listView;
    EditText editMessage;
    Button sendMessageButton;
    IntentFilter intentFilter;

    String phoneNumber = null;
    String messageSend = null;

    public static boolean onPause = false;
    public static String pauseDate = null;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Utils.createContactIfNotExists(myDb, intent, context);
            onResume();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Bundle extras = getIntent().getExtras();
        phoneNumber = extras.getString("phoneNumber");

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(MainActivity.COLOR_ID));

        setTitle(getString(R.string.messageTitle) + " " + phoneNumber);
        initText();

        myDb = DatabaseHelper.getInstance(this);
        ArrayOfSms = myDb.getAllSms(phoneNumber);
        adapter = new CustomAdapterMessage(ArrayOfSms, this);
        listView.setAdapter(adapter);

        checkPermissions();
        sendMessage();
    }

    private void initText() {
        listView = (ListView) findViewById(R.id.messageListView);
        editMessage = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (Button) findViewById(R.id.messageSendButton);
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayOfSms.clear();
        ArrayOfSms.addAll(myDb.getAllSms(phoneNumber));
        adapter.notifyDataSetChanged();

        DisplayContact.onPause = false;
        if (onPause == true) {
            Toast.makeText(getApplicationContext(), pauseDate, Toast.LENGTH_LONG).show();
            onPause = false;
        }
        registerReceiver(intentReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
        pauseDate = Utils.dateToString();
        unregisterReceiver(intentReceiver);
    }

    public void sendMessage() {
        sendMessageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editMessage.getText().toString().isEmpty()) {
                            messageSend = editMessage.getText().toString();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNumber, null, messageSend, null, null);
                            messageSend = getString(R.string.meMessage) + " " + editMessage.getText().toString();
                            Message mySms = new Message(phoneNumber, messageSend, Utils.dateToString(), 1);
                            myDb.insertDataSms(mySms);
                            editMessage.setText("");
                            adapter.add(mySms);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), R.string.smsSend, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(), R.string.noPermission, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
