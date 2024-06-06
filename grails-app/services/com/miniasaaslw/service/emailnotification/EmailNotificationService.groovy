package com.miniasaaslw.service.emailnotification

import com.miniasaaslw.adapters.emailnotification.EmailNotificationAdapter
import com.miniasaaslw.domain.emailnotification.EmailNotification
import com.miniasaaslw.repository.emailnotification.EmailNotificationRepository

import grails.gorm.transactions.Transactional

@Transactional
class EmailNotificationService {

    def mailService

    public void save(EmailNotificationAdapter emailNotificationAdapter) {
        EmailNotification emailNotification = new EmailNotification()

        emailNotification.recipientEmail = emailNotificationAdapter.recipientEmail
        emailNotification.subject = emailNotificationAdapter.subject
        emailNotification.url = emailNotificationAdapter.url
        emailNotification.body = emailNotificationAdapter.body
        emailNotification.sent = emailNotificationAdapter.isSent

        emailNotification.save(failOnError: true)
    }

    public void processEmailNotification() {
        List<Long> notSentEmailList = EmailNotificationRepository.query([:]).column("id").list(max: 500) as List<Long>

        for (Long emailNotificationId : notSentEmailList) {
            EmailNotification.withNewTransaction { status ->
                try {
                    sendEmail(emailNotificationId)
                } catch (Exception exception) {
                    log.error("Erro ao enviar e-mail de notificação de pagamento: ${exception.message}")
                    status.setRollbackOnly()
                }
            }
        }
    }

    private void updateToSent(EmailNotification emailNotification) {
        emailNotification.sent = true
        emailNotification.save(failOnError: true)
    }

    private void sendEmail(long emailNotificationId) {
        EmailNotification emailNotification = EmailNotificationRepository.query([id: emailNotificationId]).get()

        mailService.sendMail {
            to emailNotification.recipientEmail
            subject emailNotification.subject
            text emailNotification.body
        }

        updateToSent(emailNotification)
    }
}