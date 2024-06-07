package com.miniasaaslw.service.notification

import com.miniasaaslw.adapters.notification.NotificationAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.repository.notification.NotificationRepository

import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
        return NotificationRepository.query([customerId: customerId]).list(max: max, offset: offset)
    }

    public void save(Customer customer, NotificationAdapter notificationAdapter) {
        Notification notification = new Notification()

        notification.customer = customer
        notification.title = notificationAdapter.title
        notification.message = notificationAdapter.message
        notification.url = notificationAdapter.url
        notification.type = notificationAdapter.type

        notification.save(failOnError: true)
    }
}
