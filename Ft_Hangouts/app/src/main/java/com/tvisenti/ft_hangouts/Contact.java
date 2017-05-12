package com.tvisenti.ft_hangouts;

/**
 * Created by tvisenti on 5/12/17.
 */

public class Contact {
    String lastName = null;
    String firstName = null;
    String phone = null;
    String mail = null;
    String address = null;

    public Contact(String lastName, String firstName, String phone, String mail, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
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

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setFirstName(String str) {
        this.firstName = str;
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
