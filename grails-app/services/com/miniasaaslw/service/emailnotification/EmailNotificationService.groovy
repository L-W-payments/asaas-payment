package com.miniasaaslw.service.emailnotification

import com.miniasaaslw.adapters.emailNotification.EmailNotificationAdapter
import com.miniasaaslw.domain.emailNotification.EmailNotification
import grails.gorm.transactions.Transactional

@Transactional
class EmailNotificationService {

    public void save(EmailNotificationAdapter emailNotificationAdapter) {
        EmailNotification emailNotification = new EmailNotification()

        emailNotification.recipientEmail = emailNotificationAdapter.recipientEmail
        emailNotification.subject = emailNotificationAdapter.subject
        emailNotification.url = emailNotificationAdapter.url
        emailNotification.body = emailNotificationAdapter.body
        emailNotification.sent = emailNotificationAdapter.isSent

        emailNotification.save(failOnError: true)
    }
}