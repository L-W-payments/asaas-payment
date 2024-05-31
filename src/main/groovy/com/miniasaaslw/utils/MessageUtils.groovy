package com.miniasaaslw.utils

import grails.util.Holders

class MessageUtils {

    public static String getMessage(String code, List<String> args = []) {
        return Holders.applicationContext.getBean("messageSource").getMessage(code, args as Object[], new Locale("pt", "BR"))
    }
}
