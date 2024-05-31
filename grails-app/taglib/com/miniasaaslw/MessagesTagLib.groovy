package com.miniasaaslw

class MessagesTagLib {
    static namespace = "messageTagLib"

    def showAlerts = { attrs, body ->
        def messageInfo = attrs.messageInfo

        out << render(
                template: "/utils/messages",
                model: messageInfo
        )
    }
}
