package com.otto.sdk.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.otto.sdk.model.api.response.RegisterResponseSDK;

public class Preferences {

    public static final String TAG = "Preferences";

    private Context mContext;

    public static SharedPreferences mSharedPreferences;

    public static final String IS_USER_LOGIN = "is_user_login";
    public static final String TOKEN = "token";
    public static final String USER_DATA = "user_data";
    public static final String USER_DETAIL_DATA = "user_detail_data";
    public static final String LOGIN_TYPE = "login_type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String REFRESH_TOKEN = "refresh_token";

    //Chat
    public static final String UPGRADE_PEDE_PLUS_DATA = "upgrade_pede_plus_data";

    public Preferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("pede_emoney_preference", 0);
    }

    /**
     *  Save login status to app preferences
     * @param isLogin [Boolean]
     */
    public void setLogin(boolean isLogin) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putBoolean(Preferences.IS_USER_LOGIN, isLogin);
        e.apply();
    }

    /**
     * GET User Login Status
     * @return Login Status [Boolean]
     */
    @SuppressLint("LongLogTag")
    public boolean getLoginStatus() {
        boolean isLogin = mSharedPreferences.getBoolean(Preferences.IS_USER_LOGIN, false);
        Log.d(TAG + " Login Status", " ==> " + isLogin);
        return isLogin;
    }



    /**
     * Save User Data
     * @param registerResponse
     */
    public void saveUserData(RegisterResponseSDK registerResponse) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(Preferences.USER_DATA, new Gson().toJson(registerResponse));
        e.apply();
    }

    /**
     * Get User Data
     * @return userData
     */
    public RegisterResponseSDK registerResponse(){
        String  userDataJSON = mSharedPreferences.getString(Preferences.USER_DATA, "");
        return new Gson().fromJson(userDataJSON, RegisterResponseSDK.class);
    }

}