package com.otto.sdk;

import java.io.File;

public interface IConfig {

    //Development
    String API_BASE_URL = "https://ottocash-sdk.ptape.com/";

    //Production
    //String API_BASE_URL = "https://sdkmw.ottocash.id:8443/";

    //Session
    String OC_SESSION_LOGIN_KEY = "Ottocash-SDK-Login";
    String OC_SESSION_USER_ID = "user_id";
    String OC_SESSION_ACCOUNT_NUMBER = "account_number";
    String OC_SESSION_EMONEY_BALANCE = "emoney_balance";
    String OC_SESSION_IS_ACTIVE = "is_active";
    String OC_SESSION_NEED_OTP = "is_need_otp";
    String OC_SESSION_TOTAL = "total";
    String OC_SESSION_PHONE = "phone";
    String OC_SESSION_NAME = "name";
    String OC_SESSION_EMAIL = "email";
    String OC_SESSION_CLIENT_ID = "id";
    String OC_SESSION_CLIENT_SECRET = "secret";
    String OC_SESSION_PARTNER_ID = "partner_id";
    String OC_SESSION_ACCESS_TOKEN = "access_token";
    String OC_NEED_OTP = "need_otp";
    String OC_FORGOT_PIN = "forgot_pin";
    String OC_SESSION_OTP = "session_otp";
    String OC_SESSION_PIN = "session_pin";


    //KEY PAYMENT RECEIPT
    String KEY_REFERENCE_NUMBER_P2P = "reference_number_P2P";
    String KEY_DATE_P2P = "date_p2p";
    String KEY_ACCOUNT_NAME_TUJUAN = "account_number_tujuan";
    String KEY_PAYMENT_QR = "thisIsQRPayment";

    //KEY DATA TRANSFER FRIEND
    String KEY_NUMBER_CONTACT = "number_contact";
    String KEY_NAME_CONTACT = "name_contact";
    String KEY_NOMINAL_TRANSFER_TO_FRIEND = "nominal";
    String DATE_TRANSACTION = "date_transaction";
    String SERVICE_TYPE_TRANSACTION = "service_type_transaction";
    String NOMINAL_TRANSACTION = "nominal_transaction";
    String DESTINATION_ACCOUNT_NUMBER_TRANSACTION = "destination_account_number_transaction";
    String DESCRIPTION_TRANSACTION = "description_transaction";
    String REFERENCE_NUMBER_TRANSACTION = "reference_number_transaction";
    String STATUS_TRANSACTION = "status_transaction";

    //callback payment data
    String PAYMENT_TOTAL = "payment_total";
    String PAYMENT_SERVICES_FEE = "payment_service_fee";
    String PAYMENT_PRODUCT_NAME = "product_name";
    String PAYMENT_BILLER_ID = "biller_id";
    String PAYMENT_CUSTOMER_RN = "customer_reference_number";
    String PAYMENT_PRODUCT_CODE = "product_code";
    String PAYMENT_PARTNER_CODE = "partner_code";


    String OTTOCASH_PAYMENT_DATA_STATUS = "data_transaction_status";
    String OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER = "data_reference_number";
    String OTTOCASH_PAYMENT_DATA_TRANSACTION_DATE = "data_transaction_date";


    //KEY REVIEW CHECKOUT
    String TOTAL_BILL_PAYMENT = "total_bill_checkout";

    //KEY PIN PAYMENT
    String KEY_PIN_TRANSFER_TO_FRIEND = "pin_transfer_to_friend";
    String KEY_PIN_CHECKOUT = "pin_checkout";

    String OC_SESSION_CHECK_PHONE_NUMBER = "is_session_check_user";
    boolean SESSION_IS_LOGIN = false;

    //API 11 - ?
    int KEY_API_REGISTER = 11;
    int KEY_API_LOGIN = 12;
    int KEY_API_INQUIRY = 13;
    int KEY_API_OTP_VERIFICATION = 14;
    int KEY_API_OTP_VERIFICATION_REGISTER = 24;
    int KEY_API_OTP_REQUEST = 15;
    int KEY_API_OTP_REQUEST_REGISTER = 25;
    int KEY_API_REVIEW_CHECK_OUT = 16;
    int KEY_API_PAYMENT_VALIDATE = 17;
    int KEY_API_CHECK_PHONE_NUMBER = 18;
    int KEY_API_TOKEN = 19;
    int KEY_API_HISTORIES = 20;
    int KEY_API_TRANSFER_TO_FRIEND = 21;
    int KEY_API_UPGRADE = 22;
    int KEY_API_FORGOT_PIN = 23;
    int KEY_API_FORGOT_PIN_INQUIRY = 26;

    //Document File foto
    String KEY_DATA = "data";

    // folder file
    String FOLDER_APP = "ottocash";
    String FOLDER_FOTO = FOLDER_APP + File.separator + "Foto";

    // file foto configuration
    String EXTENSION_FILE_FOTO = ".jpg";
    String FILE_SEPARATOR = "_";

    String KEY_BASE64_KTP = "value_base64_ktp";
    String FILE_NAME_MAIN_FOTO_KTP = "foto_ktp_sign";
    String FILE_NAME_FOTO_KTP = FOLDER_FOTO + File.separator + FILE_NAME_MAIN_FOTO_KTP + IConfig.FILE_SEPARATOR;


    String KEY_BASE64_SELFIE = "value_base64_selfie";
    String FILE_NAME_MAIN_FOTO_SELFIE = "foto_selfie";
    String FILE_NAME_FOTO_SELFIE = FOLDER_FOTO + File.separator + FILE_NAME_MAIN_FOTO_SELFIE + IConfig.FILE_SEPARATOR;

    String FILE_NAME_MAIN_FOTO_RECEIPT = "foto_receipt";
    String FILE_NAME_FOTO_RECEIPT = FOLDER_FOTO + File.separator + FILE_NAME_MAIN_FOTO_RECEIPT + IConfig.FILE_SEPARATOR;

}
