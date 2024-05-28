package com.miniasaaslw.service.notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.entity.enums.NotificationPriority

import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    public void save(Customer customer, Map params) {
        Notification notification = new Notification()

        notification.customer = customer
        notification.title = params.title
        notification.message = params.message
        notification.url = params.url
        notification.priority = NotificationPriority.valueOf(params.priority.toString().toUpperCase())

        notification.save(failOnError: true)
    }

}
