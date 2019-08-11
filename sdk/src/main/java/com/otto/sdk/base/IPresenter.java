package com.otto.sdk.base;

public interface IPresenter {
    void call();

    void done();

    void fail(String message);

}
