package com.otto.sdk.ui.component.support;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.Spanned;
import android.view.Window;
import android.view.WindowManager;

import com.otto.sdk.R;

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

    public static Dialog getProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.primary_progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        return dialog;
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

    public static int removeAllCharacterNumbers(String input){
        String result = input.replaceAll("[^0-9]","");
        return Integer.parseInt(result);
    }
}
