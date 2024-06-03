package com.miniasaaslw.service.notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.entity.enums.NotificationPriority

import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    public void save(Customer customer, Map notificationInfo) {
        Notification notification = new Notification()

        notification.customer = customer
        notification.title = notificationInfo.title
        notification.message = notificationInfo.message
        notification.url = notificationInfo.url
        notification.priority = notificationInfo.priority as NotificationPriority

        notification.save(failOnError: true)
    }
}
