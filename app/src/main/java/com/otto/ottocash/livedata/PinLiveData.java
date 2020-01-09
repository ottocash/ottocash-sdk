package com.otto.ottocash.livedata;

import android.arch.lifecycle.LiveData;

public class PinLiveData<T> extends LiveData<T> {

    public void setData(T data) {
        setValue(data);
    }

}
