package com.tvisenti.ft_hangouts;

import android.content.Intent;
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

    public static ArrayList<String> ArrayofContact = new ArrayList<String>();
    public static ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.myContactsTitle);

        myDb = DatabaseHelper.getInstance(this);
        mListView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, ArrayofContact);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), DisplayContact.class);
                Integer newPos = position + 1;
                String log = "New Position: " + newPos;
                Log.d("New Position: ", log);
                myIntent.putExtra("idContact", newPos);
                startActivityForResult(myIntent, newPos);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayofContact.clear();
        myDb.getAllContacts();
        // Print into log
        List<String> contacts = ArrayofContact;
        for (String cn : contacts) {
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
