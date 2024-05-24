package com.miniasaaslw

class AlertTagLib {
    static namespace = "alertTagLib"

    def showAlerts = { attrs, body ->
        String alertType = attrs.alertType
        def alerts = attrs.alerts

        out << render(
                template: "/utils/alerts",
                model: [alerts: alerts, alertType: alertType]
        )
    }
}
