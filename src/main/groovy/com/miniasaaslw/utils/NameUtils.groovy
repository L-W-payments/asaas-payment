package com.miniasaaslw.utils

class NameUtils {

    public static boolean validateName(String name) {
        if (name == null) return false

        if (name.startsWith(" ")) return false

        if (name.endsWith(" ")) return false

        if (!StringUtils.removeAccents(name).matches('^[a-zA-Z0-9 ]+$')) return false

        if (name.split(" ").length < 2) return false

        return true
    }
}
