package com.otto.sdk;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arindamnath on 27/12/15.
 */
public class AppPreferences {

    private SharedPreferences mAppPrefs;

    public AppPreferences(Context context) {
        mAppPrefs = context.getSharedPreferences("AppPreferences", 0);
    }


//    public AppPreferences(LoanProductAdapter loanProductAdapter) {
//    }


    public void saveUserInfo(String name, String email, String phone, String gender,
                             String maritalStatus, String workStatus, String residentialStatus,
                             long dob, long id, String fatherName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        String[] userName = name.split("\\s+");
        if (userName.length == 1) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", "");
            edit.putString("userLastName", "");
        } else if (userName.length == 2) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", "");
            edit.putString("userLastName", userName[1]);
        } else if (userName.length > 2) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", userName[1]);
            edit.putString("userLastName", userName[2]);
        }
        edit.putString("userEmail", email);
        edit.putString("userPhone", phone);
        edit.putString("userGender", gender);
        edit.putString("userMaritalStatus", maritalStatus);
        edit.putString("userWorkStatus", workStatus);
        edit.putString("userResidentialStatus", residentialStatus);
        edit.putLong("userDOB", dob);
        edit.putLong("userId", id);
        edit.putString("userFather", fatherName);
        edit.commit();
    }

    public void updateUserInfo(String name, String gender, String maritalStatus, String workStatus,
                               String residentialStatus, long dob, String fatherName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        String[] userName = name.split("\\s+");
        if (userName.length == 1) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", "");
            edit.putString("userLastName", "");
        } else if (userName.length == 2) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", "");
            edit.putString("userLastName", userName[1]);
        } else if (userName.length > 2) {
            edit.putString("userFirstName", userName[0]);
            edit.putString("userMiddleName", userName[1]);
            edit.putString("userLastName", userName[2]);
        }
        edit.putString("userGender", gender);
        edit.putString("userMaritalStatus", maritalStatus);
        edit.putString("userWorkStatus", workStatus);
        edit.putString("userResidentialStatus", residentialStatus);
        edit.putLong("userDOB", dob);
        edit.putString("userFather", fatherName);
        edit.commit();
    }

    //Added by Fino
    public void saveUserData(String id, String first_name, String last_name, String gender, String title, String mobile,
                             String phone, String email, String unique_number, String business_name, String working_status,
                             String username, String active, String blacklisted, String branch_id, String country_id) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("id", id);
        edit.putString("first_name", first_name);
        edit.putString("last_name", last_name);
        edit.putString("gender", gender);
        edit.putString("title", title);
        edit.putString("mobile", mobile);
        edit.putString("phone", phone);
        edit.putString("email", email);
        edit.putString("unique_number", unique_number);
        edit.putString("business_name", business_name);
        edit.putString("working_status", working_status);
        edit.putString("username", username);
        edit.putString("active", active);
        edit.putString("blacklisted", blacklisted);
        edit.putString("branch_id", branch_id);
        edit.putString("country_id", country_id);
        edit.commit();
    }

    public void saveHomeAddress(Long id, String address, String state, String city, String country,
                                String pincode, String type) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putLong("homeAddressId", id);
        edit.putString("homeAddressStreet", address);
        edit.putString("homeAddressState", state);
        edit.putString("homeAddressCity", city);
        edit.putString("homeAddressCountry", country);
        edit.putString("homeAddressPincode", pincode);
        edit.putString("homeAddressType", type);
        edit.commit();
    }

    public String[] getHomeAddress() {
        if (mAppPrefs.getLong("homeAddressId", -1l) != -1l) {
            String[] data = new String[7];
            data[0] = mAppPrefs.getString("homeAddressStreet", "");
            data[1] = mAppPrefs.getString("homeAddressState", "");
            data[2] = mAppPrefs.getString("homeAddressCity", "");
            data[3] = mAppPrefs.getString("homeAddressCountry", "");
            data[4] = mAppPrefs.getString("homeAddressType", "");
            data[5] = mAppPrefs.getString("homeAddressPincode", "");
            return data;
        } else {
            return null;
        }
    }

    public void saveCurrentAddress(Long id, String address, String state, String city, String country,
                                   String pincode, String type) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putLong("currentAddressId", id);
        edit.putString("currentAddressStreet", address);
        edit.putString("currentAddressState", state);
        edit.putString("currentAddressCity", city);
        edit.putString("currentAddressCountry", country);
        edit.putString("currentAddressPincode", pincode);
        edit.putString("currentAddressType", type);
        edit.commit();
    }

    public String[] getCurrentAddress() {
        if (mAppPrefs.getLong("currentAddressId", -1l) != -1l) {
            String[] data = new String[7];
            data[0] = mAppPrefs.getString("currentAddressStreet", "");
            data[1] = mAppPrefs.getString("currentAddressState", "");
            data[2] = mAppPrefs.getString("currentAddressCity", "");
            data[3] = mAppPrefs.getString("currentAddressCountry", "");
            data[4] = mAppPrefs.getString("currentAddressType", "");
            data[5] = mAppPrefs.getString("currentAddressPincode", "");
            return data;
        } else {
            return null;
        }
    }

    public void saveUserImage(String imageURL) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("userImage", imageURL);
        edit.commit();
    }


    public void setFirstName(String firstName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("first_name", firstName);
        edit.commit();
    }


    public void setDocument_id(String document_id) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("document_id", document_id);
        edit.commit();
    }


    public void setLastName(String lastName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("last_name", lastName);
        edit.commit();
    }

    public void setGender(String gender) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("gender", gender);
        edit.commit();
    }


    public void setToken(String token) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString(IConfig.SESSION_ACCESS_TOKEN, token);
        edit.commit();
    }


    public void setEmail(String email) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("email", email);
        edit.commit();
    }

    public void setUniqueNumber(String uniqueNumber) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("unique_number", uniqueNumber);
        edit.commit();
    }


    public void setMobile(String mobile) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("mobile", mobile);
        edit.commit();
    }

    public void setPhone(String phone) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("phone", phone);
        edit.commit();
    }

    public void setInfo(String info) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("info", info);
        edit.commit();
    }

    public void setProvince(String province) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("province", province);
        edit.commit();
    }

    public void setCity(String city) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("city", city);
        edit.commit();
    }


    public void setAddress(String address) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("address", address);
        edit.commit();
    }

    public void setZipcode(String zipCode) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("zip", zipCode);
        edit.commit();
    }


    public void setBussiness(String bussiness) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("business_name", bussiness);
        edit.commit();
    }


    public void setWorkingStatus(String workingStatus) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("business_name", workingStatus);
        edit.commit();
    }


    public void setUserID(String userID) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("id", userID);
        edit.commit();
    }


    public void setUserRole(String role) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("userRole", role);
        edit.commit();
    }

    public void setUserToken(String token) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("userToken", token);
        edit.commit();
    }

    public void setRegId(String regId) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("regId", regId);
        edit.commit();
    }


    public void setWalletId(Long id) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putLong("setWalletId", id);
        edit.commit();
    }

    public void setUser_first_name(String first_name) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("first_name", first_name);
        edit.commit();
    }

    public void setUser_last_name(String last_name) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("last_name", last_name);
        edit.commit();
    }

    public void setUser_gender(String gender) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("gender", gender);
        edit.commit();
    }

    public void setUser_title(String title) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("title", title);
        edit.commit();
    }

    public void setUser_mobile(String mobile) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("mobile", mobile);
        edit.commit();
    }

    public void setUser_phone(String phone) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("phone", phone);
        edit.commit();
    }

    public void setUser_email(String email) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("email", email);
        edit.commit();
    }

    public void setUser_unique_number(String uniqueNumber) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("unique_number", uniqueNumber);
        edit.commit();
    }

    public void setUser_business_name(String businessName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("business_name", businessName);
        edit.commit();
    }

    public void setUser_working_status(String workingStatus) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("working_status", workingStatus);
        edit.commit();
    }

    public void setUser_username(String userName) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("username", userName);
        edit.commit();
    }

    public void setUser_active(String active) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("active", active);
        edit.commit();
    }

    public void setUser_blacklisted(String blackListed) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("blacklisted", blackListed);
        edit.commit();
    }

    public void setUser_branch_id(String branchId) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("branch_id", branchId);
        edit.commit();
    }

    public void setUser_country_id(String countryId) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("country_id", countryId);
        edit.commit();
    }


//    public void setUser_Is_applied(String is_applied) {
//        SharedPreferences.Editor edit = mAppPrefs.edit();
//        edit.putString("is_applied", is_applied);
//        edit.commit();
//    }


    public void setUser_is_applied(boolean is_applied) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putBoolean("is_applied", is_applied);
        edit.commit();


    }

    public void setStatus(boolean is_applied) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putBoolean("status", is_applied);
        edit.commit();


    }


    public void setUser_Credit_score(String score) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("credit_score", score);
        edit.commit();
    }


//    public void setUser_applied(String applied) {
//        SharedPreferences.Editor edit = mAppPrefs.edit();
//        edit.putString("apply", applied);
//        edit.commit();
//    }


    //Added by Fino
    public String getUser_id() {
        return mAppPrefs.getString("id", null);
    }

    public String getUser_first_name() {
        return mAppPrefs.getString("first_name", null);
    }

    public String getUser_last_name() {
        return mAppPrefs.getString("last_name", null);
    }

    public String getUser_gender() {
        return mAppPrefs.getString("gender", null);
    }

    public String getUser_date() {
        return mAppPrefs.getString("month", null);
    }

    public String getUser_uniqueNumber() {
        return mAppPrefs.getString("unique", null);
    }

    public String getUser_title() {
        return mAppPrefs.getString("title", null);
    }

    public String getUser_mobile() {
        return mAppPrefs.getString("mobile", null);
    }

    public String getDocument_id() {
        return mAppPrefs.getString("document_id", null);
    }


    public String getUser_phone() {
        return mAppPrefs.getString("phone", null);

    }

    public String getUser_Province() {
        return mAppPrefs.getString("province", null);
    }

    public String getUser_City() {
        return mAppPrefs.getString("city", null);
    }

    public String getUser_email() {
        return mAppPrefs.getString("email", null);
    }

    public String getUser_Addres() {
        return mAppPrefs.getString("address", null);
    }

    public String getUser_Zip() {
        return mAppPrefs.getString("zip", null);
    }

    public String getUser_BussinessName() {
        return mAppPrefs.getString("bussiness_name", null);
    }

    public String getUser_WorkingStatus() {
        return mAppPrefs.getString("working_status", null);
    }

    public String getUser_unique_number() {
        return mAppPrefs.getString("unique_number", null);
    }

    public String getUser_business_name() {
        return mAppPrefs.getString("business_name", null);
    }

    public String getInfo() {
        return mAppPrefs.getString("info", null);
    }

    public String getUser_working_status() {
        return mAppPrefs.getString("working_status", null);
    }

    public String getUser_username() {
        return mAppPrefs.getString("username", null);
    }

    public String getUser_active() {
        return mAppPrefs.getString("active", null);
    }

    public String getUser_blacklisted() {
        return mAppPrefs.getString("blacklisted", null);
    }

    public String getUser_branch_id() {
        return mAppPrefs.getString("branch_id", null);
    }

    public String getUser_country_id() {
        return mAppPrefs.getString("country_id", null);
    }

    public String getUserImage() {
        return mAppPrefs.getString("image", null);
    }


    public boolean getUser_is_applied() {
        return mAppPrefs.getBoolean("is_applied", false);
    }

    public boolean getStatus() {
        return mAppPrefs.getBoolean("status", false);
    }

    public String getUserFirstName() {
        return mAppPrefs.getString("userFirstName", null);
    }

    public String getUserMiddleName() {
        return mAppPrefs.getString("userMiddleName", null);
    }

    public String getUserLastName() {
        return mAppPrefs.getString("userLastName", null);
    }

    public String getUserEmail() {
        return mAppPrefs.getString("userEmail", null);
    }

    public String getUserPhone() {
        return mAppPrefs.getString("userPhone", null);
    }

    public String getUserFatherName() {
        return mAppPrefs.getString("userFather", null);
    }

    public String getUserGender() {
        return mAppPrefs.getString("userGender", null);
    }

    public String getUserMaritalStatus() {
        return mAppPrefs.getString("userMaritalStatus", null);
    }

    public String getUserWorkStatus() {
        return mAppPrefs.getString("userWorkStatus", null);
    }

    public String getUserResidientialStatus() {
        return mAppPrefs.getString("userResidentialStatus", null);
    }

    public String getUserToken() {
        return mAppPrefs.getString(IConfig.SESSION_ACCESS_TOKEN, null);
    }

    public String getTitle() {
        return mAppPrefs.getString("title", null);
    }


    public String getMessage() {
        return mAppPrefs.getString("message", null);
    }


    public String getRegId() {
        return mAppPrefs.getString("regId", null);
    }


    public String getborowerr() {
        return mAppPrefs.getString("borrower_id", null);
    }


    public Long getUserId() {
        return mAppPrefs.getLong("userId", -1l);
    }

    public Long getWalletId() {
        return mAppPrefs.getLong("setWalletId", -1l);
    }

    public Long getUserDOB() {
        return mAppPrefs.getLong("userDOB", -1l);
    }

    public Long getHomeLocationId() {
        return mAppPrefs.getLong("homeAddressId", -1l);
    }

    public Long getCurrentLocationId() {
        return mAppPrefs.getLong("currentAddressId", -1l);
    }

    public void setLoggedIn() {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putBoolean("appLoggedIn", true);
        edit.commit();
    }

    public void setLoggedOut() {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.clear();
        edit.commit();
    }

    public void setSignUpActive() {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putBoolean("appSignUp", true);
        edit.commit();
    }

    public void setSignUpComplete() {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putBoolean("appSignUp", false);
        edit.commit();
    }

    public void setSignUpStep(int i) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putInt("appSignUpStep", i);
        edit.commit();
    }

    public void setGCMId(String id) {
        SharedPreferences.Editor edit = mAppPrefs.edit();
        edit.putString("appGCMId", id);
        edit.commit();
    }

    public String getGCMId() {
        return mAppPrefs.getString("appGCMId", null);
    }

    public boolean isSignUpActive() {
        return mAppPrefs.getBoolean("appSignUp", false);
    }

    public int getSignUpStep() {
        return mAppPrefs.getInt("appSignUpStep", -1);
    }

    public boolean isUserLoggedIn() {
        return mAppPrefs.getBoolean("appLoggedIn", false);
    }

}
