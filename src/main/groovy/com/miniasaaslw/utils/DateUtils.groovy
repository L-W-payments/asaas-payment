package com.miniasaaslw.utils

import java.text.SimpleDateFormat

class DateUtils {
    private static SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy")

    public static Date parseDate(String date){
        return inputFormat.parse(date)
    }
}
