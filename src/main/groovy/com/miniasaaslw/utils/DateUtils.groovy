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

    public static Date firstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.getTime()
    }

    public static Date lastDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.getTime()
    }

    public static Date today() {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.getTime()
    }
}
