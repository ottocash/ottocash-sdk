package com.otto.sdk.ui.component.support;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    public static String formatDateToString(String value, String fromPattern, String toPattern) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(fromPattern);
        SimpleDateFormat newFormat = new SimpleDateFormat(toPattern);
        try {
            Date date = format.parse(value);
            result = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
