package com.otto.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.ui.component.support.Logging;

public class Preferences {

    public static final String TAG = "Preferences";

    private Context mContext;

    public static SharedPreferences mSharedPreferences;
    public static final String SESSION_ACCESS_TOKEN = "access_token";


    public Preferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("pede_emoney_preference", 0);
    }

    /**
     * Save data Token
     *
     * @param token
     */
    public void setToken(CreateTokenResponse.Data.Client token) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(Preferences.SESSION_ACCESS_TOKEN, new Gson().toJson(token));
        e.apply();
    }

    /**
     * Synchronize
     */

    /**
     * Clear data Token
     */
    public void clearToken() {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(Preferences.SESSION_ACCESS_TOKEN, "");
        e.commit();
    }

    /**
     * Get Token
     *
     * @return Profile
     */
    public CreateTokenResponse.Data.Client getToken() {
        String tokenJson = mSharedPreferences.getString(Preferences.SESSION_ACCESS_TOKEN, "");
        return new Gson().fromJson(tokenJson, CreateTokenResponse.Data.Client.class);
    }

    /**
     * Get Access Token
     *
     * @return accessToken
     */
    public String getAccessToken() {
        String tokenJson = mSharedPreferences.getString(Preferences.SESSION_ACCESS_TOKEN, "");
        String accessToken = "";
        if (!tokenJson.equals("")) {
            CreateTokenResponse.Data.Client token = new Gson().fromJson(tokenJson, CreateTokenResponse.Data.Client.class);
            accessToken = "Bearer " + token.getAccessToken();
            Logging.debug(TAG, "Access Token: " + accessToken);
        }
        return accessToken;
    }



}