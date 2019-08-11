package com.otto.sdk.presenter.manager;

import android.content.Context;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.support.util.CacheUtil;


public class SessionManager {

    public static boolean putSessionLogin(boolean isLogin, Context context) {
        CacheUtil.putPreferenceBoolean(IConfigSDK.SESSION_LOGIN_KEY, isLogin, context);
        return isLogin;
    }

    public static boolean getSessionLogin(boolean isLogin, Context context) {
        return CacheUtil.getPreferenceBoolean(IConfigSDK.SESSION_LOGIN_KEY, context);
    }

    public static boolean putAccountNumber(String account_number, Context context) {
        if (account_number != null) {
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_ACCOUNT_NUMBER, account_number, context);
        }
        return false;
    }

    public static String getAccountNumber(Context context) {
        return CacheUtil.getPreferenceString(IConfigSDK.SESSION_ACCOUNT_NUMBER, context);
    }


    public static boolean putName(String name, Context context) {
        if (name != null) {
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_NAME, name, context);
        }
        return false;
    }

    public static String getName(Context context) {
        return CacheUtil.getPreferenceString(IConfigSDK.SESSION_NAME, context);
    }

    public static boolean putUserID(String user_id, Context context) {
        if (user_id != null) {
            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_USER_ID, Integer.parseInt(user_id), context);
        }
        return false;
    }

    public static String getUserID(Context context) {
        return String.valueOf(CacheUtil.getPreferenceInteger(IConfigSDK.SESSION_USER_ID, context));
    }


//    public static boolean putInstitutionID(String phone, Context context) {
//        if (phone != null) {
//            CacheUtil.putPreferenceString(IConfigSDK.SESSION_INSTITUTION_ID, phone, context);
//        }
//        return false;
//    }
//
//    public static String getInstitutionID(Context context) {
//        return CacheUtil.getPreferenceString(IConfigSDK.SESSION_INSTITUTION_ID, context);
//    }
}
