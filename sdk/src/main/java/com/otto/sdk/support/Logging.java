package com.otto.sdk.support;

import android.util.Log;

import com.otto.sdk.BuildConfig;


public class Logging {

    public static final String REQUEST = " Request: ";
    public static final String RESPONSE = " Response: ";

    /**
     * Logging for Debug
     *
     * @param tag
     * @param message
     */
    public static void debug(String tag, String message){
        if(BuildConfig.DEBUG && tag != null && message != null){
            Log.d(tag, message);
        }
    }

    /**
     * Logging for Verbose
     *
     * @param tag
     * @param message
     */
    public static void verbose(String tag, String message){
        if(BuildConfig.DEBUG){
            Log.v(tag, message);
        }
    }

    /**
     * Logging for Error
     *
     * @param tag
     * @param message
     */
    public static void error(String tag, String message){
        Log.e(tag, message);
    }

    /**
     * Logging for Information
     *
     * @param tag
     * @param message
     */
    public static void info(String tag, String message){
        Log.i(tag, message);
    }

    /**
     * Logging for Warning
     *
     * @param tag
     * @param message
     */
    public static void warning(String tag, String message){
        Log.w(tag, message);
    }

    /**
     * Logging for Request
     *
     * @param tag
     * @param message
     */
    public static void request(String tag, String message){
        debug(tag, REQUEST + message);
    }

    /**
     * Logging for Response
     *
     * @param tag
     * @param message
     */
    public static void response(String tag, String message){
        debug(tag, RESPONSE + message);
    }
}