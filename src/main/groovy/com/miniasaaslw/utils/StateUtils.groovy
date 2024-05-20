package com.miniasaaslw.utils

class StateUtils {

    public static boolean isStateValid(String state) {
        if (state == null) return false

        if (!state.matches('^[a-zA-Z]{2}$')) return false

        return true
    }
}
