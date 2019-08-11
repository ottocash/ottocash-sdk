package com.otto.sdk.base;
import com.otto.sdk.base.response.BaseResponseSDK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseDaoSDKSDK implements IDaoSDK {
    private static BaseDaoSDKSDK base;
    private int callbackKey;
    private IDaoSDK dao;
    private BasePresenterSDKSDK bp;
    private String key;
    public Callback callback;

    public BaseDaoSDKSDK() {
        this.callback = new NamelessClass_1();
    }

    public BaseDaoSDKSDK(IDaoSDK dao) {
        this.callback = new NamelessClass_1();
        this.dao = dao;
    }

    private BaseDaoSDKSDK(IDaoSDK dao, BasePresenterSDKSDK bp, int keyCallback) {
        class NamelessClass_1 implements Callback {
            NamelessClass_1() {
            }

            public void onResponse(Call call, Response response) {
                if (BaseDaoSDKSDK.this.dao != null) {
                    if (BaseDaoSDKSDK.this.dao instanceof BaseDaoSDKSDK) {
                        BaseDaoSDKSDK.base.onResponseCallback(response, BaseDaoSDKSDK.this.dao, BaseDaoSDKSDK.this.callbackKey);
                    }

                }
            }

            public void onFailure(Call call, Throwable t) {
                if (BaseDaoSDKSDK.this.dao != null && BaseDaoSDKSDK.this.dao instanceof BaseDaoSDKSDK) {
                    BaseDaoSDKSDK.base.onFailureCallback(t, BaseDaoSDKSDK.this.dao);
                }

            }
        }

        this.callback = new NamelessClass_1();
        this.dao = dao;
        this.bp = bp;
        this.callbackKey = keyCallback;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static BaseDaoSDKSDK getInstance(IDaoSDK current, BasePresenterSDKSDK bp, int key) {
        if (base == null || base.getKey() == null || !base.getKey().equals(key)) {
            base = new BaseDaoSDKSDK(current, bp, key);
        }

        return base;
    }

    public BasePresenterSDKSDK getPresenter() {
        return null;
    }

    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
    }

    public void onApiFailureCallback(String message) {
    }

    public void onResponseCallback(Response response, IDaoSDK dao, int responseCode) {
        this.bp.done();
        dao.onApiResponseCallback((BaseResponseSDK) response.body(), responseCode, response);
    }

    public void onFailureCallback(Throwable t, IDaoSDK dao) {
        this.bp.fail(t.getMessage());
        dao.onApiFailureCallback(t.getMessage());
    }

    private class NamelessClass_1 implements Callback {
        @Override
        public void onResponse(Call call, Response response) {
            if (BaseDaoSDKSDK.this.dao != null) {
                if (BaseDaoSDKSDK.this.dao instanceof BaseDaoSDKSDK) {
                    BaseDaoSDKSDK.base.onResponseCallback(response, BaseDaoSDKSDK.this.dao, BaseDaoSDKSDK.this.callbackKey);
                }

            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            if (BaseDaoSDKSDK.this.dao != null && BaseDaoSDKSDK.this.dao instanceof BaseDaoSDKSDK) {
                BaseDaoSDKSDK.base.onFailureCallback(t, BaseDaoSDKSDK.this.dao);
            }
        }
    }


}