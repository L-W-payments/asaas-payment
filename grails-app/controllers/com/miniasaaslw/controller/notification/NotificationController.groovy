package com.miniasaaslw.controller.notification

import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.utils.LoggedCustomer

import grails.converters.JSON

class NotificationController {

    def notificationService

    def index() {}

    def list() {
        try {
            List<Notification> notifications = notificationService.list(LoggedCustomer.CUSTOMER.id, 50, 0)
            List<String> templates = new ArrayList<>()

            for (Notification notification : notifications) {
                String template = g.render(template: "/templates/notificationCard", model: [
                        notification: [
                                title   : notification.title,
                                message : notification.message,
                                url     : notification.url,
                                priority: notification.priority
                        ]
                ])

                templates.add(template)
            }

            render([success: true, templates: templates] as JSON)
        } catch (Exception exception) {
            render([sucess: false] as JSON)
        }
    }
}
