package com.miniasaaslw.utils

class StateUtils {

    public static boolean isStateValid(String state) {
        return state != null && state.matches('^[a-zA-Z]{2}$')
    }
}
