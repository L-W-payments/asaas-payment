package com.miniasaaslw

class AlertTagLib {
    static namespace = "alertTagLib"

    def showAlerts = { attrs, body ->
        def alertInfo = attrs.alertInfo

        out << render(
                template: "/utils/alerts",
                model: [alertInfo: alertInfo]
        )
    }
}
