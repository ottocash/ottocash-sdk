package com.otto.sdk.ui.component.support;


public class FormValidation {

    public static boolean required(String value) {
        if (value.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean validEmail(String email) {
        boolean validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (validEmail) {
            return true;
        }
        return false;
    }

    public static boolean validPhone(String phone) {
        if (phone.trim().length() > 9) {
            return true;
        }
        return false;
    }


    public static boolean validPin(String pin) {
        if (pin.trim().length() == 6) {
            return true;
        }
        return false;
    }

    public static boolean validUsername(String username) {
        if (username.trim().length() > 3) {
            return true;
        }
        return false;
    }


    public static boolean validName(String name) {
        String result = name.replaceAll("[^a-zA-Z\\s]", "");

        int originalLeng = name.length();
        int newLeng = result.length();

        if (originalLeng == newLeng) {
            return true;
        }
        return false;
    }
}
