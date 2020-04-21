package com.otto.sdk.model.api;

import android.content.Context;

import com.otto.sdk.BuildConfig;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
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
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.api.response.SecurityQuestionResponse;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;

import java.util.HashMap;
import java.util.Map;

import app.beelabs.com.codebase.base.BaseApi;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Callback;

public class Api extends BaseApi {


    private static Map<String, String> initHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("Cache-Control", "no-store");
        map.put("Content-Type", "application/json");

        return map;
    }

    private static Map<String, Object> initHeaderForRequest(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, context));
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json");

        return map;
    }

//    private static Map<String, String> initHeaderPartner(Context context) {
//        Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, context));
//        map.put("Cache-Control", "no-store");
//        map.put("Content-Type", "application/json");
//        map.put("Institution-ID", "OTTOCASH");
//        map.put("Partner-ID", "MRMONTIR");
//
//        return map;
//    }

//    private static ApiService initApiDomain() {
//        getInstance().setApiDomain(IConfig.API_BASE_URL);
//        return (ApiService) getInstance().setupApi(OttoCashSdk.getAppComponent(), ApiService.class, true, 60);
//    }



    synchronized private static ApiService initApiDomain() {
        return (ApiService) getInstance()
                .setupApiDomain(IConfig.API_BASE_URL, OttoCashSdk.getAppComponent(),
                        ApiService.class,
                        true,
                        60, BuildConfig.DEBUG);
    }


    synchronized public static void onRegister(RegisterRequest registerRequest, Callback callback) {
        initApiDomain().callApiRegister(initHeader(), registerRequest).enqueue((Callback<RegisterResponse>) callback);
    }


    synchronized public static void onInquiry(InquiryRequest inquiryRequest, Context context, Callback callback) {
        initApiDomain().callApiInquiry(initHeaderForRequest(context), inquiryRequest).enqueue((Callback<InquiryResponse>) callback);
    }

    synchronized public static void onLogin(LoginRequest loginRequest, Callback callback) {
        initApiDomain().callApiLogin(initHeader(), loginRequest).enqueue((Callback<LoginResponse>) callback);
    }

    synchronized public static void onUpgrade (UpgradeAccountRequest upgradeAccountRequest, Context context, Callback callback) {
        initApiDomain().callApiUpgrade(initHeaderForRequest(context), upgradeAccountRequest).enqueue((Callback<UpgradeAccountResponse>) callback);
    }

    //OTP VERIFICATION
    synchronized public static void onOtpVerification(OtpVerificationRequest otpVerificationRequest, Callback callback) {
        initApiDomain().callApiOtpVerification(initHeader(), otpVerificationRequest).enqueue((Callback<VerifyOtpResponse>) callback);
    }

    //OTP REQUEST
    synchronized public static void onOtpRequest(OtpRequest otpRequest, Callback callback) {
        initApiDomain().callApiOtpRequest(initHeader(), otpRequest).enqueue((Callback<RequestOtpResponse>) callback);
    }

    synchronized public static void onReviewCheckOut(ReviewCheckOutRequest reviewCheckOutRequest, Context context, Callback callback) {
        initApiDomain().callApiReviewCheckOut(initHeaderForRequest(context), reviewCheckOutRequest).enqueue((Callback<ReviewCheckOutResponse>) callback);
    }

    synchronized public static void onPaymentValidate(PaymentValidateRequest paymentValidateRequest, Context context, Callback callback) {
        initApiDomain().callApiPaymentValidate(initHeaderForRequest(context), paymentValidateRequest).enqueue((Callback<PaymentValidateResponse>) callback);
    }

    synchronized public static void onCheckPhoneNumber(CheckPhoneNumberRequest checkPhoneNumberRequest, Callback callback) {
        initApiDomain().callApiCheckPhoneNumber(initHeader(), checkPhoneNumberRequest).enqueue((Callback<CheckPhoneNumberResponse>) callback);
    }

//    synchronized public static void onClients(ClientsRequest clientsRequest, Context context, Callback callback) {
//        initApiDomain(context).callApiClients(initHeader(context), clientsRequest).enqueue((Callback<ClientsResponse>) callback);
//    }

    synchronized public static void onCreateToken(CreateTokenRequest createTokenRequest, Callback callback) {
        initApiDomain().callApiCreateToken(initHeader(), createTokenRequest).enqueue((Callback<CreateTokenResponse>) callback);
    }

    synchronized public static void onSecurityQuestion(Context context, Callback callback) {
        initApiDomain().callSecurityQuestion().enqueue((Callback<SecurityQuestionResponse>) callback);
    }

    synchronized public static void onGetHistories(Context context, TransactionHistoryRequest request, Callback callback) {
        initApiDomain().callApiGetHistories(initHeaderForRequest(context), request).enqueue((Callback<TransactionHistoryResponse>) callback);
    }

    synchronized public static void onTransferToFriend(TransferToFriendRequest request, Context context, Callback callback) {
        initApiDomain().callApiTransferToFriend(initHeaderForRequest(context), request).enqueue((Callback<TransferToFriendResponse>) callback);
    }


}
