package com.otto.sdk.view.activities.component.support;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by clappingape on 21/02/18.
 */

public class Utils {
    /**
     * Represent to show toast message with long duration
     *
     * @param context
     * @param message
     */
    public static void showToastLong(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Represent to show toast message with short duration
     *
     * @param context
     * @param message
     */
    public static void showToastShort(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Represent to show Snackbar message
     *
     * @param context
     * @param message
     */
    public static void showSnackbarLong(Context context, View view, CharSequence message, String action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, null).show();
    }

    /**
     * Response to get screen width of device
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Resize View
     * @param v = view
     * @param width = view width
     * @param height = view height
     */
    public static void resize(View v, int width, int height) {
        v.getLayoutParams().width = width;
        v.getLayoutParams().height = height;
    }

    /**
     * helper class for converting dp to pixel
     *
     * @param context
     * @param dp
     * @return
     */
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * Covert number to IDR format
     * @param number
     * @param isWithSymbol
     * @return
     */
    public static String numberToIDR(int number, boolean isWithSymbol) {
        String numberIDR = NumberFormat.getInstance(Locale.GERMANY).format(number);
        Log.d("RP", numberIDR);
        if (isWithSymbol) {
            return "Rp " + numberIDR;
        } else {
            return numberIDR;
        }
    }

    /**
     * Covert number to IDR format
     * @param number
     * @param isWithSymbol
     * @return
     */
    public static String numberToIDR(long number, boolean isWithSymbol) {
        String numberIDR = NumberFormat.getInstance(Locale.GERMANY).format(number);
        Log.d("RP", numberIDR);
        if (isWithSymbol) {
            return "Rp " + numberIDR;
        } else {
            return numberIDR;
        }
    }

    /**
     * Set specific margin
     * @param v = View
     * @param l = margin left
     * @param t = margin top
     * @param r = margin right
     * @param b = margin bottom
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
