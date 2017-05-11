package com.tvisenti.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tvisenti on 5/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact.db";

    public static final String CONTACT_TABLE = "contact_table";
    public static final String CONTACT_ID = "ID";
    public static final String CONTACT_LASTNAME = "LASTNAME";
    public static final String CONTACT_FIRSTNAME = "FIRSTNAME";
    public static final String CONTACT_PHONE = "PHONE";
    public static final String CONTACT_EMAIL = "EMAIL";
    public static final String CONTACT_ADDRESS = "ADDRESS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CONTACT_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LASTNAME, FIRSTNAME, PHONE, EMAIL, ADDRESS)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        onCreate(db);
    }

    public boolean insertDataContact(String lastname, String firstname, String phone, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_LASTNAME, lastname);
        contentValues.put(CONTACT_LASTNAME, firstname);
        contentValues.put(CONTACT_LASTNAME, phone);
        contentValues.put(CONTACT_LASTNAME, email);
        contentValues.put(CONTACT_LASTNAME, address);
        long result = db.insert(CONTACT_TABLE, null, contentValues);
        if (result == -1)
            return false;
        return true;
    }
}
