package com.miniasaaslw.service.notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.entity.enums.NotificationPriority
import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    public void save(Customer customer, String title, String message, String url, NotificationPriority priority) {
        Notification notification = buildNotificationProperties(new Notification(), customer, title, message, url, priority)
        notification.save(failOnError: true)
    }

    private Notification buildNotificationProperties(Notification notification, Customer customer, String title,
                                                     String message, String url, NotificationPriority priority) {
        notification.customer = customer
        notification.title = title
        notification.message = message
        notification.url = url
        notification.priority = priority

        return notification
    }
}
