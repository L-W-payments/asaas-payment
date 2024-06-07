package com.miniasaaslw

import com.miniasaaslw.utils.DateUtils

class DateTimeTagLib {
    static namespace = "dateTimeTagLib"

    def dateTime = { attrs ->
        String format = DateUtils.SIMPLE_DATE_FORMAT
        out << g.formatDate(format: format, date: attrs.date)
    }
}
