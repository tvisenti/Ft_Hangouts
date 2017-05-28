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
    public static final String CONTACT_ID = "ID";
    public static final String CONTACT_FIRSTNAME = "FIRSTNAME";
    public static final String CONTACT_LASTNAME = "LASTNAME";
    public static final String CONTACT_PHONE = "PHONE";
    public static final String CONTACT_EMAIL = "EMAIL";
    public static final String CONTACT_ADDRESS = "ADDRESS";

    public static final String SMS_TABLE = "sms_table";
    public static final String SMS_PK = "KEY";
    public static final String SMS_PHONE = "PHONE";
    public static final String SMS_MESSAGE = "MESSAGE";
    public static final String SMS_DATE = "DATE";
    public static final String SMS_SEND = "SEND";

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
        db.execSQL("CREATE TABLE " + CONTACT_TABLE + " (" + CONTACT_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_ID + " INTEGER NOT NULL, " +
                CONTACT_FIRSTNAME + " TEXT NOT NULL, " + CONTACT_LASTNAME + " TEXT NOT NULL, " + CONTACT_PHONE + " TEXT NOT NULL, " +
                CONTACT_EMAIL + " TEXT NOT NULL, " + CONTACT_ADDRESS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + SMS_TABLE + " (" + SMS_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SMS_PHONE + " TEXT NOT NULL, " +
                SMS_MESSAGE + " TEXT NOT NULL, " + SMS_DATE + " TEXT NOT NULL, " + SMS_SEND + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SMS_TABLE);
        onCreate(db);
    }

    public long insertDataContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_ID, contact.getId());
        contentValues.put(CONTACT_FIRSTNAME, contact.getFirstName());
        contentValues.put(CONTACT_LASTNAME, contact.getLastName());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getMail());
        contentValues.put(CONTACT_ADDRESS, contact.getAddress());
        return db.insert(CONTACT_TABLE, null, contentValues);
    }

    public long insertDataSms(Message sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMS_PHONE, sms.getPhone());
        contentValues.put(SMS_MESSAGE, sms.getMessage());
        contentValues.put(SMS_DATE, sms.getDate());
        contentValues.put(SMS_SEND, sms.getSendByMe());
        return db.insert(SMS_TABLE, null, contentValues);
    }

    Integer getLastRow() {
        Integer id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT MAX(" + CONTACT_PK + ") FROM " + CONTACT_TABLE;
        Cursor cur = db.rawQuery(MY_QUERY, null);
        if (cur != null) {
            cur.moveToFirst();
            id = cur.getInt(0);
            Log.d("PK ID = ", id.toString());
        }
        cur.close();
        return id;
    }

    public ArrayList<Message> getAllSms(String phoneNumber) {
        ArrayList<Message> smsList = new ArrayList<Message>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SMS_TABLE + " WHERE " + SMS_PHONE + " = '" + phoneNumber + "'", null);
        Log.d("Cursors: ", cursor.getColumnName(1));
        printTableInLogSms(db);

        if (cursor.moveToFirst()) {
            do {
                Message sms = new Message(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
                smsList.add(sms);
            } while (cursor.moveToNext());
        }
        return smsList;
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CONTACT_TABLE, new String[] { CONTACT_PK, CONTACT_ID, CONTACT_FIRSTNAME,
                        CONTACT_LASTNAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_ADDRESS }, CONTACT_PK + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACT_TABLE, null);
        printTableInLogContact(db);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                MainActivity.ArrayofContact.add(contact);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public boolean updateContact(Contact contact, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(CONTACT_LASTNAME, contact.getLastName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getMail());
        values.put(CONTACT_ADDRESS, contact.getAddress());

        printTableInLogContact(db);
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
        printTableInLogContact(db);
        db.close();
    }


    public void printTableInLogContact(SQLiteDatabase db) {
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

    public void printTableInLogSms(SQLiteDatabase db) {
        String tableName = SMS_TABLE;
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
