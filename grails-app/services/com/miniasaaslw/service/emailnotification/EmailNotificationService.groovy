package com.miniasaaslw.service.emailnotification

import com.miniasaaslw.adapters.emailnotification.EmailNotificationAdapter
import com.miniasaaslw.domain.emailnotification.EmailNotification
import com.miniasaaslw.repository.emailnotification.EmailNotificationRepository

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService

import org.springframework.transaction.TransactionStatus

@GrailsCompileStatic
@Transactional
class EmailNotificationService {

    private static final String BASE_URL = "http://localhost:8080"

    MailService mailService

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
        List<Long> notSentEmailList = EmailNotificationRepository.query([sent: false]).column("id").list(max: 500) as List<Long>

        for (Long emailNotificationId : notSentEmailList) {
            EmailNotification.withNewTransaction { TransactionStatus status ->
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

    private void sendEmail(Long emailNotificationId) {
        EmailNotification emailNotification = EmailNotificationRepository.query([id: emailNotificationId]).get()

        Map model = [
            emailNotification: emailNotification,
            url: BASE_URL + emailNotification.url
        ]

        mailService.sendMail {
            to emailNotification.recipientEmail
            subject emailNotification.subject
            html(view: "/templates/email/baseEmail", model: model)
        }

        updateToSent(emailNotification)
    }
}