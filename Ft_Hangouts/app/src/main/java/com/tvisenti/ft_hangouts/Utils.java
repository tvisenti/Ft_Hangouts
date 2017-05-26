package com.tvisenti.ft_hangouts;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tvisenti on 5/26/17.
 */

public class Utils {

    public static boolean checkInfo(String firstName, String lastName, String phoneNumber, Context context) {
        Pattern p = Pattern.compile("^(0|\\+33)[1-9]([-. ]?[0-9]{2}){4}");
        Matcher m = p.matcher(phoneNumber);
        if (firstName.isEmpty()) {
            Toast.makeText(context, R.string.firstNameEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastName.isEmpty()) {
            Toast.makeText(context, R.string.lastNameEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!m.matches()) {
            Toast.makeText(context, R.string.wrongNumber, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static String dateToString() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd '-' HH:mm:ss");
        return date.format(new Date());
    }
}
