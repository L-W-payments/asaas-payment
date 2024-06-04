package com.miniasaaslw.service.notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.entity.enums.notification.NotificationType
import com.miniasaaslw.repository.notification.NotificationRepository

import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
        return NotificationRepository.query([customerId: customerId]).list(max: max, offset: offset)
    }

    public void save(Customer customer, Map notificationInfo) {
        Notification notification = new Notification()

        notification.customer = customer
        notification.title = notificationInfo.title
        notification.message = notificationInfo.message
        notification.url = notificationInfo.url
        notification.type = notificationInfo.type as NotificationType

        notification.save(failOnError: true)
    }
}
