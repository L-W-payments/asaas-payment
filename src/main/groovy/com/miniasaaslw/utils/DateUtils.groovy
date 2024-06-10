package com.miniasaaslw.utils

import java.text.SimpleDateFormat

class DateUtils {

    public static final String LONG_DATE_FORMAT = "dd 'de' MMMM 'de' yyyy"
    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy"

    public static Date parseDate(String date) {
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT).parse(date)
    }

    public static String formatLongDate(Date date) {
        return new SimpleDateFormat(LONG_DATE_FORMAT).format(date)
    }
}
