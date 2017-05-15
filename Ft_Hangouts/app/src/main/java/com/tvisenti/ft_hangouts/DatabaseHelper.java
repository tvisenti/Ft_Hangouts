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
        db.execSQL("CREATE TABLE " + CONTACT_TABLE + " (" + CONTACT_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_ID + " INTEGER , " +
                CONTACT_FIRSTNAME + " TEXT NOT NULL, " + CONTACT_LASTNAME + " TEXT NOT NULL, " + CONTACT_PHONE + " TEXT NOT NULL, " + CONTACT_EMAIL +
                " TEXT NOT NULL, " + CONTACT_ADDRESS + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        onCreate(db);
    }

    public boolean insertDataContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_ID, contact.getId());
        contentValues.put(CONTACT_FIRSTNAME, contact.getFirstName());
        contentValues.put(CONTACT_LASTNAME, contact.getLastName());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getMail());
        contentValues.put(CONTACT_ADDRESS, contact.getAddress());
        long result = db.insert(CONTACT_TABLE, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        return true;
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CONTACT_TABLE, new String[] { CONTACT_PK, CONTACT_ID, CONTACT_FIRSTNAME,
                        CONTACT_LASTNAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_ADDRESS }, CONTACT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + CONTACT_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

                String name = cursor.getString(2)+ " " + cursor.getString(3) + "\n" + cursor.getString(4);
                MainActivity.ArrayofContact.add(name);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(CONTACT_LASTNAME, contact.getLastName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getMail());
        values.put(CONTACT_ADDRESS, contact.getAddress());

        printTableInLog(db);

        return db.update(CONTACT_TABLE, values, CONTACT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param table the table to update in
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, CONFLICT_NONE);
    }

    public void deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACT_TABLE, CONTACT_ID + " = ?",
                new String[] { String.valueOf(id) });

        ContentValues values = new ContentValues();
        values.put(CONTACT_ID, );
        db.update(CONTACT_TABLE, )

        db.execSQL("UPDATE " + CONTACT_TABLE + " SET " + CONTACT_ID + " = ?" + new String[] { String.valueOf(CONTACT_ID - 1) }));
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + CONTACT_TABLE, null);

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
