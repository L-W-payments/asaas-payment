package com.miniasaaslw.utils

import java.text.SimpleDateFormat

class DateUtils {

    public static final String DATE_FORMAT = "dd/MM/yyyy"

    private static SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT)

    public static Date parseDate(String date){
        return inputFormat.parse(date)
    }
}
