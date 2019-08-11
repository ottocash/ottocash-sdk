package com.otto.sdk.model.api;

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

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiServiceSDK {

    @POST("v1/auth/register")
    Call<RegisterResponseSDK> callApiRegister(@HeaderMap Map<String, String> headers,
                                              @Body RegisterRequest registerRequest);

    @POST("v1/auth/login")
    Call<LoginResponseSDK> callApiLogin(@HeaderMap Map<String, String> headers,
                                        @Body LoginRequest loginRequest);


    @POST("v1/account")
    Call<InquiryResponseSDK> callApiInquiry(@HeaderMap Map<String, Object> headers,
                                            @Body InquiryRequest inquiryRequest);

    @POST("v1/auth/otp-verification")
    Call<OtpVerificationResponseSDK> callApiOtpVerification(@HeaderMap Map<String, String> headers,
                                                            @Body OtpVerificationRequest otpVerificationRequest);

    @POST("v1/auth/otp-request")
    Call<OtpResponseSDK> callApiOtpRequest(@HeaderMap Map<String, String> headers,
                                           @Body OtpRequest otpRequest);

    @POST("v1/payment/journal")
    Call<ReviewCheckOutResponseSDK> callApiReviewCheckOut(@HeaderMap Map<String, Object> headers,
                                                          @Body ReviewCheckOutRequest reviewCheckOutRequest);

    @POST("v1/payment/validate")
    Call<PaymentValidateResponseSDK> callApiPaymentValidate(@HeaderMap Map<String, Object> headers,
                                                            @Body PaymentValidateRequest paymentValidateRequest);


    @GET("v1/security-questions")
    Call<SecurityQuestionResponseSDK> callSecurityQuestion();

//    @POST("v1/clients")
//    Call<ClientsResponseSDK> callApiClients(@HeaderMap Map<String, String> headers,
//                                         @Body ClientsRequest clientsRequest);

    @POST("oauth/token")
    Call<CreateTokenResponseSDK> callApiCreateToken(@HeaderMap Map<String, String> headers,
                                                    @Body CreateTokenRequest createTokenRequest);


    @POST("v1/auth/check-phone")
    Call<CheckPhoneNumberResponseSDK> callApiCheckPhoneNumber(@HeaderMap Map<String, String> headers,
                                                              @Body CheckPhoneNumberRequest checkPhoneNumberRequest);

    @POST("v1/account/history")
    Call<TransactionHistoryResponseSDK> callApiGetHistories(@HeaderMap Map<String,Object> headers,
                                                            @Body TransactionHistoryRequest model);
}
