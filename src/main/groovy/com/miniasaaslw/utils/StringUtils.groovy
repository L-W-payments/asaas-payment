package com.miniasaaslw.utils;

class StringUtils {

    public static String removeNonNumeric(String source) {
        if (source == null) return null;

        return source.replaceAll("[^0-9]", "");
    }

    public static String camelCaseToSnakeCase(String source) {
        if (source == null) return null;

        StringBuilder result = new StringBuilder();
        char[] charArray = source.toCharArray();

        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}