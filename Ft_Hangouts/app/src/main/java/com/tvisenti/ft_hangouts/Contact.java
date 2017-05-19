package com.tvisenti.ft_hangouts;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by tvisenti on 5/12/17.
 */

public class Contact {
    String lastName = null;
    String firstName = null;
    String phone = null;
    String mail = null;
    String address = null;

    public Contact(String firstName, String lastName, String phone, String mail, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
    }

    public Integer getPrimaryKeyContact(DatabaseHelper myDb, Contact contact) {
        Log.d("getPrimaryKeyContact: ", "JE PASSE ICI");
        return myDb.getPrimaryKey(contact);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setMail(String str) {
        this.mail = str;
    }

    public void setAddress(String str) {
        this.address = str;
    }
}
