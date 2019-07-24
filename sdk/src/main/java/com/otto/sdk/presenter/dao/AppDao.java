package com.otto.sdk.presenter.dao;

import app.beelabs.com.codebase.base.BaseDao;

public class AppDao extends BaseDao {
    public AppDao(Object obj) {
        super(obj);
    }

//    public static class Database {
//        // --- realm db config ---
//        protected static Realm realm;
//
//        public static Realm getRealm(Context context) {
//            if (realm == null) {
//                realm = setupRealm(context);
//            }
//            return realm;
//        }
//
//        public static void setRealm(Realm realm) {
//            Database.realm = realm;
//        }
//
//        public static Realm setupRealm(Context context) {
//            try {
//                if (context == null) return null;
//                Realm.init(context);
//                RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                        .deleteRealmIfMigrationNeeded()
//                        .name("ottocash.realm")
//                        .encryptionKey(DeviceUtil.encryptedKey64())
//                        .build();
//                // Get a Realm instance for this thread
//                realm = Realm.getInstance(realmConfig);
//                return realm;
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//
//        public static RealmObject saveToRealm(RealmObject object) {
//            realm.beginTransaction();
//            RealmObject obj = realm.copyToRealm(object);
//            realm.commitTransaction();
//            return obj;
//        }
//
//        protected static RealmResults getCollectionRealm(Class clazz) {
//            RealmResults objects = realm.where(clazz).findAll();
//            return objects;
//        }
//
//        protected static RealmResults getCollectionByKeyRealm(String key, int value, Class clazz) {
//            RealmResults items = realm.where(clazz)
//                    .beginGroup()
//                    .equalTo(key, value)
//                    .endGroup()
//                    .findAll();
//            return items;
//        }
//
//        protected static RealmResults getCollectionByKeyRealm(String key, long value, Class clazz) {
//            RealmResults items = realm.where(clazz)
//                    .beginGroup()
//                    .equalTo(key, value)
//                    .endGroup()
//                    .findAll();
//            return items;
//        }
//
//        protected static RealmResults getCollectionByKeyRealm(String key, boolean value, Class clazz) {
//            RealmResults items = realm.where(clazz)
//                    .beginGroup()
//                    .equalTo(key, value)
//                    .endGroup()
//                    .findAll();
//            return items;
//        }
//
//        protected static RealmResults getCollectionByKeyRealm(String key, String value, Class clazz) {
//            RealmResults items = realm.where(clazz)
//                    .beginGroup()
//                    .equalTo(key, value)
//                    .endGroup()
//                    .findAll();
//            return items;
//        }
//
//        protected static void closeRealm() {
//            if (realm != null)
//                realm.close();
//        }
//
//        public static void delete(RealmObject object) {
//            try {
//                realm.beginTransaction();
//                object.deleteFromRealm();
//                realm.commitTransaction();
//            } catch (Exception e) {
//            }
//        }
//
//        public static void deleteRealm(Class clazz) {
//            try {
//                realm.beginTransaction();
//                realm.delete(clazz);
//                realm.commitTransaction();
//            } catch (Exception e) {
//            }
//        }
//    }
}
