package com.otto.sdk.base.support.util;

import android.app.Activity;
import android.util.DisplayMetrics;


public class ScreenUtil {

    public static int getScreenWidth(Activity x) {
        DisplayMetrics metrics = new DisplayMetrics();
        x.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity x) {
        DisplayMetrics metrics = new DisplayMetrics();
        x.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
