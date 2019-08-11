package com.otto.sdk.base;
import com.otto.sdk.base.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseDao implements IDao {
    private static BaseDao base;
    private int callbackKey;
    private IDao dao;
    private BasePresenter bp;
    private String key;
    public Callback callback;

    public BaseDao() {
        this.callback = new NamelessClass_1();
    }

    public BaseDao(IDao dao) {
        this.callback = new NamelessClass_1();
        this.dao = dao;
    }

    private BaseDao(IDao dao, BasePresenter bp, int keyCallback) {
        class NamelessClass_1 implements Callback {
            NamelessClass_1() {
            }

            public void onResponse(Call call, Response response) {
                if (BaseDao.this.dao != null) {
                    if (BaseDao.this.dao instanceof BaseDao) {
                        BaseDao.base.onResponseCallback(response, BaseDao.this.dao, BaseDao.this.callbackKey);
                    }

                }
            }

            public void onFailure(Call call, Throwable t) {
                if (BaseDao.this.dao != null && BaseDao.this.dao instanceof BaseDao) {
                    BaseDao.base.onFailureCallback(t, BaseDao.this.dao);
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

    public static BaseDao getInstance(IDao current, BasePresenter bp, int key) {
        if (base == null || base.getKey() == null || !base.getKey().equals(key)) {
            base = new BaseDao(current, bp, key);
        }

        return base;
    }

    public BasePresenter getPresenter() {
        return null;
    }

    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
    }

    public void onApiFailureCallback(String message) {
    }

    public void onResponseCallback(Response response, IDao dao, int responseCode) {
        this.bp.done();
        dao.onApiResponseCallback((BaseResponse) response.body(), responseCode, response);
    }

    public void onFailureCallback(Throwable t, IDao dao) {
        this.bp.fail(t.getMessage());
        dao.onApiFailureCallback(t.getMessage());
    }

    private class NamelessClass_1 implements Callback {
        @Override
        public void onResponse(Call call, Response response) {
            if (BaseDao.this.dao != null) {
                if (BaseDao.this.dao instanceof BaseDao) {
                    BaseDao.base.onResponseCallback(response, BaseDao.this.dao, BaseDao.this.callbackKey);
                }

            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            if (BaseDao.this.dao != null && BaseDao.this.dao instanceof BaseDao) {
                BaseDao.base.onFailureCallback(t, BaseDao.this.dao);
            }
        }
    }


}