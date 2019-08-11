package com.otto.sdk.base.support.util;

import android.os.Build;


public class FilterUtil {
    public static final int ANDROID_VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    public static final String EQUAL_OR_ABOVE = ">=";
    public static final String EQUAL = "==";

    public static boolean isAndroidVersion(int androidVersion, String operator){
        int version = Build.VERSION.SDK_INT;
        boolean result = false;
        switch(operator){
            case ">=":
                result = version >= androidVersion;
                break;
            case "==":
                result = version == androidVersion;
                break;
        }

        return result;
    }
}
