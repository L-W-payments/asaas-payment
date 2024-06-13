package com.miniasaaslw.controller.notification

import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.service.notification.NotificationService

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['isAuthenticated()'])
class NotificationController extends BaseController {

    NotificationService notificationService

    def index() {}

    @CompileDynamic
    def list() {
        try {
            List<Notification> notifications = notificationService.list(getCurrentCustomerId())
            String template = g.render(template: "/templates/notifications", model: [notifications: notifications])

            render([success: true, template: template] as JSON)
        } catch (Exception exception) {
            render([sucess: false] as JSON)
        }
    }
}
