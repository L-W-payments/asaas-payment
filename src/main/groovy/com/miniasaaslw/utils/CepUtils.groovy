package com.miniasaaslw.utils

class CepUtils {

    public static boolean validadeCep(String cep){
        if(cep.length() != 8){
            return false
        }

        return true
    }
}
