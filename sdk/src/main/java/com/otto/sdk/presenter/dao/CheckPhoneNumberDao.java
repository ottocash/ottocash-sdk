package com.otto.sdk.presenter.dao;

import android.content.Context;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.db.User;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

import static com.otto.sdk.presenter.dao.AppDao.Database.setupRealm;

public class CheckPhoneNumberDao extends BaseDao {
    public CheckPhoneNumberDao(Object obj) {
        super(obj);
    }

    public void onCheckPhoneNumber(CheckPhoneNumberRequest model, BaseActivity ac, Callback callback) {
        Api.onCheckPhoneNumber(model, ac, callback);
    }

    public static User save(User user, Context context) {

        setupRealm(context);
        AppDao.Database.deleteRealm(User.class);

        AppDao.Database.saveToRealm(user);

        return user;

    }

    public static User getUserExisting(Context context) {
        setupRealm(context);
        User user;
        try {
            user = (User) AppDao.Database.getCollectionRealm(User.class).get(0);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

}
