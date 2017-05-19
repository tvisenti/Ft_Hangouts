package com.tvisenti.ft_hangouts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView mListView;

    public static ArrayList<Contact> ArrayofContact = new ArrayList<Contact>();
    public static ArrayAdapter<Contact> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.myContactsTitle);

        mListView = (ListView) findViewById(R.id.listView);

        myDb = DatabaseHelper.getInstance(this);

        adapter = new ArrayAdapter<Contact>(MainActivity.this,
                android.R.layout.simple_list_item_1, ArrayofContact);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), DisplayContact.class);
                Contact contactId = (Contact) parent.getItemAtPosition(position);
                Integer trueId = contactId.getPrimaryKeyContact(myDb, contactId);
                Log.d("IdContact: ", String.valueOf(id + 1));
                Log.d("trueId: ", trueId.toString());
                Log.d("Name: ", contactId.getFirstName());
                myIntent.putExtra("idContact", trueId);
                startActivityForResult(myIntent, position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayofContact.clear();
        // Print into log
        List<Contact> contacts = myDb.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Name: " + cn;
            Log.d("Name: ", log);
        }
        adapter.notifyDataSetChanged();
    }

    public void createNewContact(View view) {
        Intent intent = new Intent(this, createContact.class);
        startActivity(intent);
    }
}
