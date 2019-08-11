package com.otto.sdk.interfaces;


import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.TransactionHistoryResponseSDK;

public interface IHistoryViewSDK extends IViewSDK {

    void handleTransacionHistory(TransactionHistoryResponseSDK model);
}
