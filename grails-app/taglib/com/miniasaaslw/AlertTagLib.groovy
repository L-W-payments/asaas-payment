package com.miniasaaslw

class AlertTagLib {
    static namespace = "alertTagLib"

    def showAlerts = { attrs, body ->
        String alertType = attrs.type
        def alerts = attrs.errors

        out << render(template: "utils/alerts", model: [alerts: alerts, alertType: alertType])
    }
}
