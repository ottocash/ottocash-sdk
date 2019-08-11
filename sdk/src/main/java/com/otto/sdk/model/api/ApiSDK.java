package com.otto.sdk.model.api;

import android.content.Context;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.base.BaseApiSDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponseSDK;
import com.otto.sdk.model.api.response.CreateTokenResponseSDK;
import com.otto.sdk.model.api.response.InquiryResponseSDK;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.OtpResponseSDK;
import com.otto.sdk.model.api.response.OtpVerificationResponseSDK;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;
import com.otto.sdk.model.api.response.SecurityQuestionResponseSDK;
import com.otto.sdk.model.api.response.TransactionHistoryResponseSDK;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

public class ApiSDK extends BaseApiSDK {


    private static Map<String, String> initHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("Cache-Control", "no-store");
        map.put("Content-Type", "application/json");

        return map;
    }

    private static Map<String, Object> initHeaderForRequest(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfigSDK.SESSION_ACCESS_TOKEN, context));
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json");

        return map;
    }

//    private static Map<String, String> initHeaderPartner(Context context) {
//        Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfigSDK.SESSION_ACCESS_TOKEN, context));
//        map.put("Cache-Control", "no-store");
//        map.put("Content-Type", "application/json");
//        map.put("Institution-ID", "OTTOCASH");
//        map.put("Partner-ID", "MRMONTIR");
//
//        return map;
//    }

//    private static ApiServiceSDK initApiDomain() {
//        getInstance().setApiDomain(IConfigSDK.API_BASE_URL);
//        return (ApiServiceSDK) getInstance().setupApi(OttoCashSDK.getAppComponent(), ApiServiceSDK.class, true, 60);
//    }

    synchronized private static ApiServiceSDK initApiDomain() {
        return (ApiServiceSDK) getInstance()
                .setupApiDomain(IConfigSDK.API_BASE_URL, OttoCashSDK.getAppComponent(),
                        ApiServiceSDK.class,
                        true,
                        IConfigSDK.TIMEOUT_SHORT_INSECOND);

    }


    synchronized public static void onRegister(RegisterRequest registerRequest, Callback callback) {
        initApiDomain().callApiRegister(initHeader(), registerRequest).enqueue((Callback<RegisterResponseSDK>) callback);
    }


    synchronized public static void onInquiry(InquiryRequest inquiryRequest, Context context, Callback callback) {
        initApiDomain().callApiInquiry(initHeaderForRequest(context), inquiryRequest).enqueue((Callback<InquiryResponseSDK>) callback);
    }

    synchronized public static void onLogin(LoginRequest loginRequest, Callback callback) {
        initApiDomain().callApiLogin(initHeader(), loginRequest).enqueue((Callback<LoginResponseSDK>) callback);
    }

    //OTP VERIFICATION
    synchronized public static void onOtpVerification(OtpVerificationRequest otpVerificationRequest, Callback callback) {
        initApiDomain().callApiOtpVerification(initHeader(), otpVerificationRequest).enqueue((Callback<OtpVerificationResponseSDK>) callback);
    }

    //OTP REQUEST
    synchronized public static void onOtpRequest(OtpRequest otpRequest, Callback callback) {
        initApiDomain().callApiOtpRequest(initHeader(), otpRequest).enqueue((Callback<OtpResponseSDK>) callback);
    }

    synchronized public static void onReviewCheckOut(ReviewCheckOutRequest reviewCheckOutRequest, Context context, Callback callback) {
        initApiDomain().callApiReviewCheckOut(initHeaderForRequest(context), reviewCheckOutRequest).enqueue((Callback<ReviewCheckOutResponseSDK>) callback);
    }

    synchronized public static void onPaymentValidate(PaymentValidateRequest paymentValidateRequest, Context context, Callback callback) {
        initApiDomain().callApiPaymentValidate(initHeaderForRequest(context), paymentValidateRequest).enqueue((Callback<PaymentValidateResponseSDK>) callback);
    }

    synchronized public static void onCheckPhoneNumber(CheckPhoneNumberRequest checkPhoneNumberRequest, Callback callback) {
        initApiDomain().callApiCheckPhoneNumber(initHeader(), checkPhoneNumberRequest).enqueue((Callback<CheckPhoneNumberResponseSDK>) callback);
    }

//    synchronized public static void onClients(ClientsRequest clientsRequest, Context context, Callback callback) {
//        initApiDomain(context).callApiClients(initHeader(context), clientsRequest).enqueue((Callback<ClientsResponseSDK>) callback);
//    }

    synchronized public static void onCreateToken(CreateTokenRequest createTokenRequest, Callback callback) {
        initApiDomain().callApiCreateToken(initHeader(), createTokenRequest).enqueue((Callback<CreateTokenResponseSDK>) callback);
    }

    synchronized public static void onSecurityQuestion(Context context, Callback callback) {
        initApiDomain().callSecurityQuestion().enqueue((Callback<SecurityQuestionResponseSDK>) callback);
    }

    synchronized public static void onGetHistories(Context context, TransactionHistoryRequest request, Callback callback) {
        initApiDomain().callApiGetHistories(initHeaderForRequest(context), request).enqueue((Callback<TransactionHistoryResponseSDK>) callback);
    }


}
