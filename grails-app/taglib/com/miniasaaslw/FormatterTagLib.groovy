package com.miniasaaslw

import com.miniasaaslw.utils.DateUtils

class FormatterTagLib {
    static namespace = "formatterTagLib"

    def dateTime = { attrs ->
        String format = DateUtils.SIMPLE_DATE_FORMAT
        out << g.formatDate(format: format, date: attrs.date)
    }

    def cpf = { attrs ->
        String cpf = attrs.cpf
        out << cpf[0..2] + "." + cpf[3..5] + "." + cpf[6..8] + "-" + cpf[9..10]
    }

}
