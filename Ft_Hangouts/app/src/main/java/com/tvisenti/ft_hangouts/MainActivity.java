package com.tvisenti.ft_hangouts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView mListView;

    public static ArrayList<Contact> ArrayofContact = new ArrayList<Contact>();
    public static CustomAdapterContact adapter = null;
    public static int COLOR_ID = 0xFFCCCCCC;

    public static boolean onPause = false;
    public static String pauseDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(COLOR_ID));

        setTitle(R.string.myContactsTitle);

        mListView = (ListView) findViewById(R.id.listView);

        myDb = DatabaseHelper.getInstance(this);
        adapter = new CustomAdapterContact(ArrayofContact, this);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), DisplayContact.class);
                Contact contactId = (Contact) parent.getItemAtPosition(position);
                Integer newId = contactId.getId();
                Log.d("idContact: ", newId.toString());
                myIntent.putExtra("idContact", newId);
                startActivityForResult(myIntent, position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settingChangeBlue:
                COLOR_ID = Color.BLUE;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                return true;
            case R.id.settingChangeGreen:
                COLOR_ID = Color.GREEN;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                return true;
            case R.id.settingChangeGray:
                COLOR_ID = Color.LTGRAY;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                return true;
            case R.id.settingChangeRed:
                COLOR_ID = Color.RED;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        CreateContact.onPause = false;
        DisplayContact.onPause = false;

        ArrayofContact.clear();
        // Print into log
        List<Contact> contacts = myDb.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Name: " + cn;
            Log.d("Name: ", log);
        }
        adapter.notifyDataSetChanged();

        if (onPause == true) {
            Toast.makeText(getApplicationContext(), pauseDate, Toast.LENGTH_LONG).show();
            onPause = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
        pauseDate = Utils.dateToString();
    }


    public void createNewContact(View view) {
        Intent intent = new Intent(this, CreateContact.class);
        startActivity(intent);
    }
}
