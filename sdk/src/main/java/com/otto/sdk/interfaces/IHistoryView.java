package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.TransactionHistoryResponse;

import app.beelabs.com.codebase.base.IView;

public interface IHistoryView extends IView {

    void handleTransacionHistory(TransactionHistoryResponse model);
}
