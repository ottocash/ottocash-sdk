package com.otto.sdk.model.api;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.ClientsResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.api.response.SecurityQuestionResponse;

import java.util.HashMap;
import java.util.Map;

import app.beelabs.com.codebase.base.BaseApi;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Callback;

public class Api extends BaseApi {

    private static Map<String, String> initHeader(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("Cache-Control", "no-store");
        map.put("Content-Type", "application/json");

        return map;
    }

    private static Map<String, Object> initHeaderForRequest(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfig.SESSION_ACCESS_TOKEN, context));
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json");

        return map;
    }

//    private static Map<String, String> initHeaderPartner(Context context) {
//        Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Bearer " + CacheUtil.getPreferenceString(IConfig.SESSION_ACCESS_TOKEN, context));
//        map.put("Cache-Control", "no-store");
//        map.put("Content-Type", "application/json");
//        map.put("Institution-ID", "OTTOCASH");
//        map.put("Partner-ID", "MRMONTIR");
//
//        return map;
//    }

    private static ApiService initApiDomain(Context context) {
        getInstance().setApiDomain(IConfig.API_BASE_URL);
        return (ApiService) getInstance().setupApi(OttoCashSdk.getAppComponent(), ApiService.class, true, 60);
    }

    synchronized public static void onRegister(RegisterRequest registerRequest, Context context, Callback callback) {
        initApiDomain(context).callApiRegister(initHeader(context), registerRequest).enqueue((Callback<RegisterResponse>) callback);
    }


    synchronized public static void onInquiry(InquiryRequest inquiryRequest, Context context, Callback callback) {
        initApiDomain(context).callApiInquiry(initHeaderForRequest(context), inquiryRequest).enqueue((Callback<InquiryResponse>) callback);
    }

    synchronized public static void onLogin(LoginRequest loginRequest, Context context, Callback callback) {
        initApiDomain(context).callApiLogin(initHeader(context), loginRequest).enqueue((Callback<LoginResponse>) callback);
    }

    //OTP VERIFICATION
    synchronized public static void onOtpVerification(OtpVerificationRequest otpVerificationRequest, Context context, Callback callback) {
        initApiDomain(context).callApiOtpVerification(initHeader(context), otpVerificationRequest).enqueue((Callback<BaseResponse>) callback);
    }

    //OTP REQUEST
    synchronized public static void onOtpRequest(OtpRequest otpRequest, Context context, Callback callback) {
        initApiDomain(context).callApiOtpRequest(initHeader(context), otpRequest).enqueue((Callback<BaseResponse>) callback);
    }

    synchronized public static void onReviewCheckOut(ReviewCheckOutRequest reviewCheckOutRequest, Context context, Callback callback) {
        initApiDomain(context).callApiReviewCheckOut(initHeaderForRequest(context), reviewCheckOutRequest).enqueue((Callback<ReviewCheckOutResponse>) callback);
    }

    synchronized public static void onPaymentValidate(PaymentValidateRequest paymentValidateRequest, Context context, Callback callback) {
        initApiDomain(context).callApiPaymentValidate(initHeaderForRequest(context), paymentValidateRequest).enqueue((Callback<PaymentValidateResponse>) callback);
    }

    synchronized public static void onCheckPhoneNumber(CheckPhoneNumberRequest checkPhoneNumberRequest, Context context, Callback callback) {
        initApiDomain(context).callApiCheckPhoneNumber(initHeader(context), checkPhoneNumberRequest).enqueue((Callback<CheckPhoneNumberResponse>) callback);
    }

//    synchronized public static void onClients(ClientsRequest clientsRequest, Context context, Callback callback) {
//        initApiDomain(context).callApiClients(initHeader(context), clientsRequest).enqueue((Callback<ClientsResponse>) callback);
//    }

    synchronized public static void onCreateToken(CreateTokenRequest createTokenRequest, Context context, Callback callback) {
        initApiDomain(context).callApiCreateToken(initHeader(context), createTokenRequest).enqueue((Callback<CreateTokenResponse>) callback);
    }

    synchronized public static void onSecurityQuestion(Context context, Callback callback) {
        initApiDomain(context).callSecurityQuestion().enqueue((Callback<SecurityQuestionResponse>) callback);
    }


}
