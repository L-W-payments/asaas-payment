package com.miniasaaslw

import com.miniasaaslw.utils.DateUtils

class FormatterTagLib {
    static namespace = "formatterTagLib"

    def dateTime = { attrs ->
        String format = DateUtils.SIMPLE_DATE_FORMAT
        out << g.formatDate(format: format, date: attrs.date)
    }
}
