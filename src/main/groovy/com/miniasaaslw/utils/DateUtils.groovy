package com.miniasaaslw.utils

import java.text.SimpleDateFormat

class DateUtils {

    public static final DATE_PARSER_FORMAT = new SimpleDateFormat("dd 'de' MMM 'de' yyyy")
    public static final String DATE_FORMAT = "dd/MM/yyyy"

    public static Date parseDate(String date){
        return new SimpleDateFormat(DATE_FORMAT).parse(date)
    }
}
