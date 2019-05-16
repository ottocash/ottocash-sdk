package com.otto.sdk.view.activities.component.support;

import android.util.DisplayMetrics;

import com.otto.sdk.OttoCashSdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeviceUtil {


    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = OttoCashSdk.getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = OttoCashSdk.getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getTodayFormattedDate(String format, int dayDeviation, int monthDeviation) {
        SimpleDateFormat formatter;
        Calendar now = Calendar.getInstance();
        try {
            formatter = new SimpleDateFormat(format);
            now.set(now.get(Calendar.YEAR),
                    (now.get(Calendar.MONTH) + 1) - monthDeviation,
                    now.get(Calendar.DAY_OF_MONTH) - dayDeviation);
            return formatter.format(now.getTime());
        } catch (Exception e) {
            return "invalid date format";
        }
    }

    /**
     * @param input put all numeric
     * @return valid digits of eas13 barcode
     */
    public static String generateEas13(String input) {
        int sum = 0;
        int e = 0;
        int o = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((int) input.charAt(i) % 2 == 0) {
                e += (int) input.charAt(i);
            } else {
                o += (int) input.charAt(i);
            }
        }
        o = o * 3;
        int total = o + e;
        if (total % 10 == 0) {
            sum = 0;//ceksum 0
        } else {
            sum = 10 - (total % 10);
        }
        return input + sum;
    }

}