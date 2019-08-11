package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;


public interface IHistoryView extends IView {

    void handleTransacionHistory(TransactionHistoryResponse model);
}
