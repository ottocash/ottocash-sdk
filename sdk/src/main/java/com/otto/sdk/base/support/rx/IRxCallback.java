package com.otto.sdk.base.support.rx;

interface IRxCallback {
    public void onNext();
    public void onError();
    public void onComplete();
}
