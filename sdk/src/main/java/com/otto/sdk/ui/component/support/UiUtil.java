package com.otto.sdk.ui.component.support;

import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;

public class UiUtil {

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


    public static String formatMoneyIDR(long number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String result = "Rp " + formatter.format(number).replaceAll(",", ".");

        return result;
    }
}
