//package com.otto.sdk;
//
//import com.otto.sdk.ui.activities.SdkActivity;
//
//import java.util.Calendar;
//
//public class OttoSDK {
//
//    private static AccountSDK accountSDK;
//
//    public static class AccountSDK {
//        private long id;
//        private String phoneNumber;
//
//        public AccountSDK() {
//        }
//
//        public AccountSDK(long id, String phoneNumber) {
//            this.id = id;
//            this.phoneNumber = phoneNumber;
//        }
//
//        public long getId() {
//            return id;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//        public String getPhoneNumber() {
//            return phoneNumber;
//        }
//
//        public void setPhoneNumber(String phoneNumber) {
//            this.phoneNumber = phoneNumber;
//        }
//    }
//
//    public static AccountSDK getInstance() {
//        if (accountSDK == null) accountSDK = new AccountSDK();
//        return accountSDK;
//    }
//
//    public static AccountSDK init(SdkActivity activity, String phoneNumber) {
//        getInstance().setId(Calendar.getInstance().getTimeInMillis());
//        getInstance().setPhoneNumber(phoneNumber);
//
//        return accountSDK;
//    }
//}
