package com.otto.sdk;

public interface IConfig {

    String API_BASE_URL = "https://ottocash-sdk.clappingape.com/";


    //Session
    String SESSION_LOGIN_KEY = "session_login";
    String SESSION_LOGIN_PROVIDER_KEY = "session_login_provider";
    String SESSION_INSTITUTION_ID = "Institution-ID";

    String SESSION_USER_ID = "user_id";
    String SESSION_ACCOUNT_NUMBER = "account_number";
    String SESSION_EMONEY_BALANCE = "emoney_balance";
    String SESSION_TOTAL = "total";


    final boolean SESSION_CHECK_PHONE_NUMBER = false;
    final boolean SESSION_IS_LOGIN = false;
    final boolean SESSION_LOG_OUT = false;

    String SESSION_PHONE = "phone";
    String SESSION_EMONEY = "emoney";
    String SESSION_PACKAGE_NAME = "package_name";
    String SESSION_NAME = "name";
    String SESSION_EMAIL = "email";
    String SESSION_ID = "id";
    String SESSION_SECRET = "secret";
    String SESSION_ACCESS_TOKEN = "access_token";

    //Login
    int SOCMED_MANUAL = 4;


    //API 11 - ?
    int KEY_API_REGISTER = 11;
    int KEY_API_LOGIN = 12;
    int KEY_API_INQUIRY = 13;
    int KEY_API_OTP_VERIFICATION = 14;
    int KEY_API_OTP_REQUEST = 15;
    int KEY_API_REVIEW_CHECK_OUT = 16;
    int KEY_API_PAYMENT_VALIDATE = 17;
    int KEY_API_CHECK_PHONE_NUMBER = 18;
    int KEY_API_CLIENTS = 19;
    int KEY_API_TOKEN = 20;
    int KEY_API_SECURITY = 21;
    int KEY_API_HISTORIES=22;


}
