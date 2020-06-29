//package com.otto.sdk;
//
//import android.content.Context;
//import android.content.Intent;
//import android.widget.Toast;
//
//import com.otto.sdk.interfaces.ISdkView;
//import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
//import com.otto.sdk.model.api.request.CreateTokenRequest;
//import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
//import com.otto.sdk.model.api.response.CreateTokenResponse;
//import com.otto.sdk.presenter.SdkResourcePresenter;
//import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
//import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
//import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
//
//import app.beelabs.com.codebase.base.BaseActivity;
//import app.beelabs.com.codebase.support.util.CacheUtil;
//
//public class OttoCash extends BaseActivity implements ISdkView {
//
//    private static final String BILL_PAYMENT = "bill_payment";
//    public static final String OTTOCASH_PAYMENT_DATA = "paymentData";
//    public static final int REQ_OTTOCASH_PAYMENT = 101;
//
//    /**
//     * Function Get Saldo OttoCash
//     */
//    public static String getBalance(Context context) {
//
//        String saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, context);
//
//        return saldo_ottocash;
//    }
//
//
////    /**
////     * Function CheckOut Payment
////     */
////    public static void onCallPayment(Activity activity, String phoneNumber, int amount) {
////        if (onCheckIsActive(activity)) {
////            Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
////            intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
////            activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
////        } else {
////            onCheckPhoneNumber(activity, phoneNumber);
////        }
////    }
//
//
//    /**
//     * Function Log Out
//     */
//    public static void onLogoutOttoCash(Context context) {
//        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, false, context);
//        CacheUtil.clearPreference(context);
//    }
//
//    /**
//     * Call Api Check Account Number
//     */
//    public static void onCreateToken(final Context context) {
//        /*String client_id = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_ID, context);
//        String client_secret = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, context);
//
//        final CreateTokenRequest token = new CreateTokenRequest();
//        token.setGrant_type("client_credentials");
//        token.setClient_id(client_id);
//        token.setClient_secret(client_secret);
//
//        new SdkResourcePresenter(new ISdkView() {
//            @Override
//            public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
//
//            }
//
//            @Override
//            public void handleToken(CreateTokenResponse model) {
//                if (model.getMeta().getCode() == 200) {
//                    String accessToken = model.getData().getClient().getAccessToken();
//                    CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, context);
//
//                    onCheckPhoneNumber(context);
//                } else {
//                    Toast.makeText(context, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void handleFail(String message) {
//
//            }
//
//            @Override
//            public BaseActivity getBaseActivity() {
//                return null;
//            }
//
//        }).doCreateToken(token);*/
//    }
//
//    /**
//     * Call Api Check Account Number
//     */
//    private static void onCheckPhoneNumber(final Context context) {
//        /*final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
//        model.setPhone(CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, context));
//
//        new SdkResourcePresenter(new ISdkView() {
//
//            @Override
//            public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
//                if (model.getMeta().getCode() == 200) {
//                    boolean is_existing = model.getData().isIs_existing();
//                    boolean is_need_otp = model.getData().isNeed_otp();
//                    CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.OC_SESSION_CHECK_PHONE_NUMBER), is_existing, context);
//                    CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_NEED_OTP, is_need_otp, context);
//                } else {
//                    Toast.makeText(context, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void handleToken(CreateTokenResponse model) {
//
//            }
//
//            @Override
//            public void handleFail(String message) {
//
//            }
//
//            @Override
//            public BaseActivity getBaseActivity() {
//                return null;
//            }
//        }).getCheckPhone(model);*/
//    }
//
//
////    /**
////     * Create Token
////     */
////    public static void onCreateToken(final Context context) {
////        String client_id = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_ID, context);
////        String client_secret = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, context);
////
////        final CreateTokenRequest token = new CreateTokenRequest();
////        token.setGrant_type("client_credentials");
////        token.setClient_id(client_id);
////        token.setClient_secret(client_secret);
////
////        sdkResourcePresenter = ((SdkResourcePresenter) BasePresenter.getInstance("", new SdkResourcePresenter(context)));
////        sdkResourcePresenter.doCreateToken(token);
////    }
////
////
////    /**
////     * Check Phone Number
////     */
////    public static void checkIsExistingPhoneNumber(final Context context) {
////        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
////        model.setPhone(CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, context));
////
////        sdkResourcePresenter = ((SdkResourcePresenter) BasePresenter.getInstance((ISdkView) context, new SdkResourcePresenter(context)));
////        sdkResourcePresenter.getCheckPhone(model);
////    }
////
////    @Override
////    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
////        if (model.getMeta().getCode() == 200) {
////            boolean is_existing = model.getData().isIs_existing();
////            boolean is_need_otp = model.getData().isNeed_otp();
////            CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER), is_existing, this);
////            CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_NEED_OTP, is_need_otp, this);
////        } else {
////            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    @Override
////    public void handleToken(CreateTokenResponse model) {
////        if (model.getMeta().getCode() == 200) {
////            String accessToken = model.getData().getClient().getAccessToken();
////            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, this);
////
////            checkIsExistingPhoneNumber(this);
////        } else {
////            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
//
//    public static void goDashboardSDK(final Context context) {
//        Intent intent = new Intent(context, DashboardSDKActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//    }
//
//    public static void goActivation(final Context context) {
//        Intent intent = new Intent(context, ActivationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//    }
//
//    public static void goRegistration(final Context context) {
//        Intent intent = new Intent(context, RegistrationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
//
//    }
//
//    @Override
//    public void handleToken(CreateTokenResponse model) {
//
//    }
//
////    @Override
////    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
////        if (model.getMeta().getCode() == 200) {
////            boolean is_existing = model.getData().isIs_existing();
////            boolean is_need_otp = model.getData().isNeed_otp();
////            CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.OC_SESSION_CHECK_PHONE_NUMBER), is_existing, this);
////            CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_NEED_OTP, is_need_otp, this);
////        } else {
////            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    @Override
////    public void handleToken(CreateTokenResponse model) {
////        if (model.getMeta().getCode() == 200) {
////            String accessToken = model.getData().getClient().getAccessToken();
////            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, this);
////
////            onCheckPhoneNumber(this);
////        } else {
////            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
//}
