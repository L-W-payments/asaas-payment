package com.miniasaaslw.service.emailnotification

import com.miniasaaslw.adapters.emailnotification.EmailNotificationAdapter
import com.miniasaaslw.domain.emailnotification.EmailNotification
import grails.gorm.transactions.Transactional

@Transactional
class EmailNotificationService {

    public void save(EmailNotificationAdapter adapter) {
        EmailNotification emailNotification = new EmailNotification()

        emailNotification.recipientEmail = adapter.recipientEmail
        emailNotification.subject = adapter.subject
        emailNotification.url = adapter.url
        emailNotification.body = adapter.body
        emailNotification.sent = adapter.sent

        emailNotification.save(failOnError: true)
    }
}