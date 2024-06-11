package com.miniasaaslw.utils

class PasswordUtils {

    private static final int MIN_PASSWORD_SIZE = 8

    public static Boolean matches(String password, String confirmPassword) {
        return password == confirmPassword
    }

    public static Boolean isStrong(String password) {
        if (password.size() < MIN_PASSWORD_SIZE) return false

        if (!hasUppercase(password)) return false

        if (!hasLowercase(password)) return false

        return true
    }

    private static Boolean hasUppercase(String password) {
        return password.find(/[A-Z]/)
    }

    private static Boolean hasLowercase(String password) {
        return password.find(/[a-z]/)
    }
}
