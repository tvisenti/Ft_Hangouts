package com.tvisenti.ft_hangouts;

/**
 * Created by tvisenti on 5/26/17.
 */

public class Sms {
    Integer idPhone = 0;
    String phone = null;
    String message = null;
    String date = null;
    Integer sendByMe = null;

    public Sms(Integer id, String phone, String message, String date, Integer send) {
        this.idPhone = id;
        this.phone = phone;
        this.message = message;
        this.date = date;
        this.sendByMe = send;
    }

    public Sms(String phone, String message, String date, Integer send) {
        this.phone = phone;
        this.message = message;
        this.date = date;
        this.sendByMe = send;
    }

    public Integer getId() {
        return idPhone;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public Integer getSendByMe() {
        return sendByMe;
    }

    public void setId(Integer newId) {
        this.idPhone = newId;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public void setSendByMe(Integer sendByMe) {
        this.sendByMe = sendByMe;
    }

}
