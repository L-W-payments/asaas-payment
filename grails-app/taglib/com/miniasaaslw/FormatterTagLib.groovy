package com.miniasaaslw

import com.miniasaaslw.utils.DateUtils

class FormatterTagLib {
    static namespace = "formatterTagLib"

    def dateTime = { attrs ->
        Boolean longDate = attrs.longDate ?: false
        String format = longDate ? DateUtils.LONG_DATE_FORMAT : DateUtils.SIMPLE_DATE_FORMAT

        out << g.formatDate(format: format, date: attrs.date)
    }

    def cpf = { attrs ->
        String cpf = attrs.cpf
        out << cpf[0..2] + "." + cpf[3..5] + "." + cpf[6..8] + "-" + cpf[9..10]
    }

    def cnpj = { attrs ->
        String cnpj = attrs.cnpj
        out << cnpj[0..1] + "." + cnpj[2..4] + "." + cnpj[5..7] + "/" + cnpj[8..11] + "-" + cnpj[12..13]
    }

    def phone = { attrs ->
        String phone = attrs.phone
        out << "(" + phone[0..1] + ") " + phone[2..6] + "-" + phone[7..10]
    }

}
