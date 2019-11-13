package com.otto.sdk.model.api;

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
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.ClientsResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.api.response.SecurityQuestionResponse;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;

import java.util.Map;

import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/auth/register")
    Call<RegisterResponse> callApiRegister(@HeaderMap Map<String, String> headers,
                                           @Body RegisterRequest registerRequest);

    @POST("v1/auth/login")
    Call<LoginResponse> callApiLogin(@HeaderMap Map<String, String> headers,
                                     @Body LoginRequest loginRequest);

    @POST("v1/account/upgrade")
    Call<UpgradeAccountResponse> callApiUpgrade(@HeaderMap Map<String, Object> headers,
                                              @Body UpgradeAccountRequest upgradeAccountRequest);



    @POST("v1/account")
    Call<InquiryResponse> callApiInquiry(@HeaderMap Map<String, Object> headers,
                                         @Body InquiryRequest inquiryRequest);

    @POST("v1/auth/otp-verification")
    Call<OtpVerificationResponse> callApiOtpVerification(@HeaderMap Map<String, String> headers,
                                                         @Body OtpVerificationRequest otpVerificationRequest);

    @POST("v1/auth/otp-request")
    Call<OtpResponse> callApiOtpRequest(@HeaderMap Map<String, String> headers,
                                        @Body OtpRequest otpRequest);

    @POST("v1/payment/journal")
    Call<ReviewCheckOutResponse> callApiReviewCheckOut(@HeaderMap Map<String, Object> headers,
                                                       @Body ReviewCheckOutRequest reviewCheckOutRequest);

    @POST("v1/payment/validate")
    Call<PaymentValidateResponse> callApiPaymentValidate(@HeaderMap Map<String, Object> headers,
                                                         @Body PaymentValidateRequest paymentValidateRequest);


    @GET("v1/security-questions")
    Call<SecurityQuestionResponse> callSecurityQuestion();

//    @POST("v1/clients")
//    Call<ClientsResponse> callApiClients(@HeaderMap Map<String, String> headers,
//                                         @Body ClientsRequest clientsRequest);

    @POST("oauth/token")
    Call<CreateTokenResponse> callApiCreateToken(@HeaderMap Map<String, String> headers,
                                                 @Body CreateTokenRequest createTokenRequest);


    @POST("v1/auth/check-phone")
    Call<CheckPhoneNumberResponse> callApiCheckPhoneNumber(@HeaderMap Map<String, String> headers,
                                                           @Body CheckPhoneNumberRequest checkPhoneNumberRequest);

    @POST("v1/account/history")
    Call<TransactionHistoryResponse> callApiGetHistories(@HeaderMap Map<String, Object> headers,
                                                         @Body TransactionHistoryRequest model);

    @POST("v1/transfer")
    Call<TransferToFriendResponse> callApiTransferToFriend(@HeaderMap Map<String, Object> headers,
                                                           @Body TransferToFriendRequest transferToFriendRequest);

}
