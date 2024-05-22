package com.miniasaaslw.utils;

class DescriptionUtils {

    public static Boolean validateDescription(String description) {
        if (description == null) return false

        if (description.length() > 500) return false

        return true
    }
}
