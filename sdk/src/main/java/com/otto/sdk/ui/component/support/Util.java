package com.otto.sdk.ui.component.support;

import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;

import com.otto.sdk.OttoCashSDK;

public class Util {

    /**
     * Get HTML Content
     *
     * @param content
     * @return
     */
    public static Spanned getHTMLContent(String content){
        return Html.fromHtml(content);
    }

    public static String countryCodeReplacedWithZero(String number) {
        String validNumber = number.replaceAll("[^0-9]", "");

        if (!("0".equals(validNumber.substring(0, 1)))) {
            String tempNumber = validNumber.substring(2);
            validNumber = "0" + tempNumber;
        }
        return validNumber;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = OttoCashSDK.getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
