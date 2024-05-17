package com.miniasaaslw.utils;

class StringUtils {

    public static String removeNonNumeric(String source) {
        if (source == null) return null;

        return source.replaceAll("[^0-9]", "");
    }
}