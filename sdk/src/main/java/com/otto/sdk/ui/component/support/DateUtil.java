package com.otto.sdk.ui.component.support;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @SuppressLint("SimpleDateFormat")
    public static String currentDate() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c);
    }

    @SuppressLint("SimpleDateFormat")
    public static String currentDate(String formatDate) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat(formatDate);
        return df.format(c);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Calendar targetDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(targetDate.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Date targetDate, String targetFormat) {
        SimpleDateFormat df = new SimpleDateFormat(targetFormat);
        return df.format(targetDate.getTime());
    }

    public static String getTimestamp(){
        return Long.toString(System.currentTimeMillis()/1000);
    }
}
