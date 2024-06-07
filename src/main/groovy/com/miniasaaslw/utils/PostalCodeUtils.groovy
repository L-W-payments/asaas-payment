package com.miniasaaslw.utils

class PostalCodeUtils {

    public static boolean validatePostalCode(String postalCode){
        if(postalCode == null) return false

        if(postalCode.length() != 8) return false

        if(!allDigitsAreNumbers(postalCode)) return false

        return true
    }

    private static boolean allDigitsAreNumbers(String postalCode){
        return postalCode.matches('\\d{8}')
    }

}
