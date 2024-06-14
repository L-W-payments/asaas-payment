package com.miniasaaslw.utils

class EmailUtils {

    public static Boolean validateEmail(String email) {
        if (email == null) return false

        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")
    }
}
