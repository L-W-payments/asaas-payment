package com.miniasaaslw.utils

class CepUtils {

    public static boolean validateCep(String cep){
        if(cep == null) return true

        if(cep.length() != 8) return false

        if(!allDigitsAreNumbers(cep)) return false

        return true
    }

    private static boolean allDigitsAreNumbers(String cep){
        return cep.matches('\\d{8}')
    }

}
