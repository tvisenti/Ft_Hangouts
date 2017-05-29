package com.tvisenti.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateContact extends AppCompatActivity {

    EditText editFirstName, editLastName, editPhone, editMail, editAddress;
    Button buttonAddContact;
    DatabaseHelper myDb;
    IntentFilter intentFilter;

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
        setContentView(R.layout.activity_create_contact);

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(MainActivity.COLOR_ID));

        setTitle(R.string.createContactTitle);
        myDb = DatabaseHelper.getInstance(this);
        editFirstName = (EditText) findViewById(R.id.textFirstName);
        editLastName = (EditText) findViewById(R.id.textLastName);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        editAddress = (EditText) findViewById(R.id.textAddress);
        buttonAddContact = (Button) findViewById(R.id.buttonAdd);

        addContact();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity.onPause = false;
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

    public void addContact() {
        buttonAddContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkContext();
                    }
                }
        );
    }

    private void checkContext() {
        if (Utils.checkInfo(editFirstName.getText().toString(), editLastName.getText().toString(), editPhone.getText().toString(), this) == true) {
            Contact contact = new Contact(editFirstName.getText().toString(), editLastName.getText().toString(), editPhone.getText().toString(), editMail.getText().toString(), editAddress.getText().toString());
            contact.setId((myDb.getLastRow()));
            Log.d("addContact ID: ", contact.getId().toString());
            if (myDb.insertDataContact(contact) != -1) {
                myDb.updateContactId(contact.getId());
                Toast.makeText(CreateContact.this, R.string.contactCreated, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(CreateContact.this, R.string.contactNotCreated, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
