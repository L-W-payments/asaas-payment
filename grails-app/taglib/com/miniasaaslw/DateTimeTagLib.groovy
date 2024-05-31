package com.miniasaaslw

import com.miniasaaslw.utils.DateUtils

class DateTimeTagLib {
    static namespace = "dateTimeTagLib"

    def datetime = { attrs ->
        String format = DateUtils.DATE_FORMAT
        out << g.formatDate(format: format, date: attrs.date)
    }
}
