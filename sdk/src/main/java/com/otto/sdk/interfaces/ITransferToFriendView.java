package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.TransferToFriendResponse;

import app.beelabs.com.codebase.base.IView;

public interface ITransferToFriendView extends IView {

    void handleResponseTransferToFriend(TransferToFriendResponse model);
}
