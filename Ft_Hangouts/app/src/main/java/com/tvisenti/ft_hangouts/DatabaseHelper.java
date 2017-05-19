package com.tvisenti.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvisenti on 5/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static final String DATABASE_NAME = "contact.db";
    public static final String CONTACT_TABLE = "contact_table";
    public static final String CONTACT_PK = "KEY";
    public static final String CONTACT_FIRSTNAME = "FIRSTNAME";
    public static final String CONTACT_LASTNAME = "LASTNAME";
    public static final String CONTACT_PHONE = "PHONE";
    public static final String CONTACT_EMAIL = "EMAIL";
    public static final String CONTACT_ADDRESS = "ADDRESS";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
//        context.deleteDatabase(DATABASE_NAME);
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CONTACT_TABLE + " (" + CONTACT_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_FIRSTNAME +
                " TEXT NOT NULL, " + CONTACT_LASTNAME + " TEXT NOT NULL, " + CONTACT_PHONE + " TEXT NOT NULL, " + CONTACT_EMAIL +
                " TEXT NOT NULL, " + CONTACT_ADDRESS + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        onCreate(db);
    }

    public long insertDataContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_FIRSTNAME, contact.getFirstName());
        contentValues.put(CONTACT_LASTNAME, contact.getLastName());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getMail());
        contentValues.put(CONTACT_ADDRESS, contact.getAddress());
        return db.insert(CONTACT_TABLE, null, contentValues);
    }

    Integer getPrimaryKey(Contact contact) {
        Log.d("getPrimaryKey: ", "Je suis la");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CONTACT_TABLE, new String[]{ CONTACT_PK }, null, new String[]{CONTACT_FIRSTNAME, CONTACT_LASTNAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_ADDRESS}, null, null, null, null);
        Log.d("getPrimaryKey: ", "Query ok");
        if(cursor.getCount() < 1) {
            cursor.close();
            Log.d("getPrimaryKey: ", "pas trouve");
            return -1;
        }
        Log.d("getPrimaryKey: ", "Avant move");
        cursor.moveToFirst();
        Log.d("getPrimaryKey: ", "Avant return");
        return (int) cursor.getLong(cursor.getColumnIndex(CONTACT_PK));
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CONTACT_TABLE, new String[] { CONTACT_PK, CONTACT_FIRSTNAME,
                        CONTACT_LASTNAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_ADDRESS }, CONTACT_PK + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + CONTACT_TABLE, null);
        printTableInLog(db);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                MainActivity.ArrayofContact.add(contact);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM TABLE", null);
        if (c != null) {
            c.moveToFirst();
        }
        db.close();
        return c;
    }

    public boolean updateContact(Contact contact, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(CONTACT_LASTNAME, contact.getLastName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getMail());
        values.put(CONTACT_ADDRESS, contact.getAddress());

        printTableInLog(db);
        if (db.update(CONTACT_TABLE, values, CONTACT_PK + " =?", new String[] { String.valueOf(id) }) != -1) {
            db.close();
            return true;
        }
        db.close();
        return false;

    }

    public void deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACT_TABLE, CONTACT_PK + " =?",
                new String[] { String.valueOf(id) });
        printTableInLog(db);
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + CONTACT_TABLE, null);
        db.close();
        return cursor.getCount();
    }

    public void printTableInLog(SQLiteDatabase db) {
        String tableName = CONTACT_TABLE;
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        Log.d("DbHelper, ", tableString);
        return;
    }
}
