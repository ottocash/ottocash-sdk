package com.otto.sdk.presenter.manager;

import android.content.Context;

import com.otto.sdk.IConfig;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class SessionManager {

    public static boolean putSessionLogin(boolean isLogin, Context context) {
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, isLogin, context);
        return isLogin;
    }

    public static boolean getSessionLogin(Context context) {
        return CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, context);
    }

    public static boolean putAccountNumber(String account_number, Context context) {
        if (account_number != null) {
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, account_number, context);
        }
        return false;
    }

    public static String getAccountNumber(Context context) {
        return CacheUtil.getPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, context);
    }


    public static boolean putName(String name, Context context) {
        if (name != null) {
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_NAME, name, context);
        }
        return false;
    }

    public static String getName(Context context) {
        return CacheUtil.getPreferenceString(IConfig.OC_SESSION_NAME, context);
    }

    public static boolean putUserID(String user_id, Context context) {
        if (user_id != null) {
            CacheUtil.putPreferenceInteger(IConfig.OC_SESSION_USER_ID, Integer.parseInt(user_id), context);
        }
        return false;
    }

    public static String getUserID(Context context) {
        return String.valueOf(CacheUtil.getPreferenceInteger(IConfig.OC_SESSION_USER_ID, context));
    }


//    public static boolean putInstitutionID(String phone, Context context) {
//        if (phone != null) {
//            CacheUtil.putPreferenceString(IConfig.SESSION_INSTITUTION_ID, phone, context);
//        }
//        return false;
//    }
//
//    public static String getInstitutionID(Context context) {
//        return CacheUtil.getPreferenceString(IConfig.SESSION_INSTITUTION_ID, context);
//    }
}
