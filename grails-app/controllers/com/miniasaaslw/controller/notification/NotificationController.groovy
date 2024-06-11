package com.miniasaaslw.controller.notification

import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.utils.LoggedCustomer

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(['isAuthenticated()'])
class NotificationController {

    def notificationService

    def index() {}

    def list() {
        try {
            List<Notification> notifications = notificationService.list(LoggedCustomer.CUSTOMER.id)
            String template = g.render(template: "/templates/notifications", model: [notifications: notifications])

            render([success: true, template: template] as JSON)
        } catch (Exception exception) {
            render([sucess: false] as JSON)
        }
    }
}
