package com.otto.sdk.ui.component.support;

import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

    public static String numberToIDR(long number, boolean isWithSymbol) {
        NumberFormat numberFormat =  NumberFormat.getInstance(Locale.GERMANY);
        numberFormat.setMaximumFractionDigits(4);

        String numberIDR = numberFormat.format(number);
        if (isWithSymbol) {
            return "Rp " + numberIDR;
        } else {
            return numberIDR;
        }
    }


    public static String formatMoneyIDR(long number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String result = "Rp " + formatter.format(number).replaceAll(",", ".");

        return result;
    }

    public static String removeCurrencyFormat(String input){
        String result = input.replaceAll("[a-zA-Z]|\\.|\\ ", "");
        return result;
    }

    public static String removeAllCharacterExpectNumbers(String input){
        String result = input.replaceAll("[^0-9]","");
        return result;
    }
}
