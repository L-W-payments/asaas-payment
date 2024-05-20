package com.miniasaaslw.utils

class CepUtils {

    public static boolean validadeCep(String cep){
        if(cep.length() != 8){
            return false
        }

        if(!allDigitsAreNumbers(cep)){
            return false
        }

        return true
    }

    private static boolean allDigitsAreNumbers(String cep){
        return cep.matches('\\d{8}')
    }

}
