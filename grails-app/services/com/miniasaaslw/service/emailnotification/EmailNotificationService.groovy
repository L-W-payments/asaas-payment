package com.miniasaaslw.service.emailnotification

import com.miniasaaslw.adapters.emailnotification.EmailNotificationAdapter
import com.miniasaaslw.domain.emailnotification.EmailNotification
import com.miniasaaslw.repository.emailnotification.EmailNotificationRepository

import grails.gorm.transactions.Transactional

@Transactional
class EmailNotificationService {

    private static final String BASE_URL = "localhost:8080"

    def mailService

    public void save(EmailNotificationAdapter adapter) {
        EmailNotification emailNotification = new EmailNotification()

        emailNotification.recipientEmail = adapter.recipientEmail
        emailNotification.subject = adapter.subject
        emailNotification.url = adapter.url
        emailNotification.body = adapter.body
        emailNotification.sent = adapter.sent

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
            text emailNotification.body + " " + BASE_URL + emailNotification.url
        }

        updateToSent(emailNotification)
    }
}