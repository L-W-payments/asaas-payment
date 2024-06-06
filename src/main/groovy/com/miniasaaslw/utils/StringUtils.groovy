package com.miniasaaslw.utils

import java.text.Normalizer

class StringUtils {

    public static String removeAccents(String source) {
        if (source == null) return null

        String nfdNormalizedString = Normalizer.normalize(source, Normalizer.Form.NFD)

        return nfdNormalizedString.replaceAll("[^\\p{ASCII}]", "")
    }

    public static String removeNonNumeric(String source) {
        if (source == null) return null

        return source.replaceAll("[^0-9]", "")
    }

    public static String camelCaseToSnakeCase(String source) {
        if (source == null) return null

        StringBuilder result = new StringBuilder()
        char[] charArray = source.toCharArray()

        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c))
            } else {
                result.append(c)
            }
        }

        return result.toString()
    }
}