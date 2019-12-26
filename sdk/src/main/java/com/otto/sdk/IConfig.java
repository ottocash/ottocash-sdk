package com.otto.sdk;

public interface IConfig {

    //Development
    String API_BASE_URL = "https://ottocash-sdk.clappingape.com/";

    //Production
    //String API_BASE_URL = "https://sdkmw.ottocash.id/";


    //Session
    String SESSION_LOGIN_KEY = "session_login";
    String SESSION_USER_ID = "user_id";
    String SESSION_ACCOUNT_NUMBER = "account_number";
    String SESSION_EMONEY_BALANCE = "emoney_balance";
    String SESSION_TOTAL = "total";
    String SESSION_PHONE = "phone";
    String SESSION_PACKAGE_NAME = "package_name";
    String SESSION_NAME = "name";
    String SESSION_EMAIL = "email";
    String SESSION_ID = "id";
    String SESSION_SECRET = "secret";
    String SESSION_ACCESS_TOKEN = "access_token";
    String SESSION_ID_CARD = "id_card";
    String SESSION_PASSPORT_PHOTO = "passport_photo";

    String KEY_NUMBER_CONTACT = "number_contact";
    String KEY_NAME_CONTACT = "name_contact";
    String KEY_NOMINAL_TRANSFER_TO_FRIEND = "nominal";
    String KEY_DESTINATION = "destination_number";

    //KEY PAYMENT RECEIPT
    String KEY_REFERENCE_NUMBER_P2P = "reference_number_P2P";
    String KEY_DATE_P2P = "date_p2p";
    String KEY_ACCOUNT_NAME_TUJUAN = "account_number_tujuan";

    //KEY DATA TRANSFER FRIEND
    String DATE_TRANSACTION = "date_transaction";
    String SERVICE_TYPE_TRANSACTION = "service_type_transaction";
    String NOMINAL_TRANSACTION = "nominal_transaction";
    String DESTINATION_ACCOUNT_NUMBER_TRANSACTION = "destination_account_number_transaction";
    String DESCRIPTION_TRANSACTION = "description_transaction";
    String REFERENCE_NUMBER_TRANSACTION = "reference_number_transaction";
    String STATUS_TRANSACTION = "status_transaction";

    //KEY REVIEW CHECKOUT
    String TOTAL_BILL_PAYMENT = "total_bill_checkout";

    //KEY PIN PAYMENT
    String KEY_PIN_TRANSFER_TO_FRIEND = "pin_transfer_to_friend";
    String KEY_PIN_CHECKOUT = "pin_checkout";
    String KEY_BASE64_SELFIE_CAMERA_KTP = "selfie_camera_ktp_base64";
    String BASE64_CAMERA_PERSONA_KEY = "camera_persona64";
    boolean REMOVE_CAMERA_PHOTO_TAKEN_CONFIG = true;

    boolean SESSION_CHECK_PHONE_NUMBER = false;
    boolean SESSION_IS_LOGIN = false;
    int CAMERA_KTP_SELFIE_TYPE = 110;
    int CAMERA_KTP_TYPE = 100;

    //API 11 - ?
    int KEY_API_REGISTER = 11;
    int KEY_API_LOGIN = 12;
    int KEY_API_INQUIRY = 13;
    int KEY_API_OTP_VERIFICATION = 14;
    int KEY_API_OTP_REQUEST = 15;
    int KEY_API_REVIEW_CHECK_OUT = 16;
    int KEY_API_PAYMENT_VALIDATE = 17;
    int KEY_API_CHECK_PHONE_NUMBER = 18;
    int KEY_API_TOKEN = 19;
    int KEY_API_HISTORIES = 20;
    int KEY_API_TRANSFER_TO_FRIEND = 21;
    int KEY_API_UPGRADE = 22;


}
