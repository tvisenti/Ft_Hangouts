package com.tvisenti.ft_hangouts;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = DatabaseHelper.getInstance(this);
    }

    public void createNewContact(View view) {
        Intent intent = new Intent(this, createContact.class);
        startActivity(intent);
    }
}
