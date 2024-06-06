package com.miniasaaslw.adapters.emailNotification

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.utils.DateUtils
import com.miniasaaslw.utils.MessageUtils

class EmailNotificationAdapter {

    String recipientEmail

    String subject

    String url

    String body

    Boolean isSent

    public EmailNotificationAdapter buildPayerEmailPaymentCreated(Payment payment) {
        this.recipientEmail = payment.payer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.created")
        this.url = "/payment/checkout/${payment.publicId}"
        this.body = MessageUtils.getMessage("emailNotification.payer.body.payment.created", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildPayerEmailPaymentPaid(Payment payment) {
        this.recipientEmail = payment.payer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.paid")
        this.url = "/payment/checkout/${payment.publicId}"
        this.body = MessageUtils.getMessage("emailNotification.payer.body.payment.paid", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildPayerEmailPaymentPaidInCash(Payment payment) {
        this.recipientEmail = payment.payer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.paidInCash")
        this.url = "/payment/checkout/${payment.publicId}"
        this.body = MessageUtils.getMessage("emailNotification.payer.body.payment.paidInCash", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildPayerEmailPaymentOverdue(Payment payment) {
        this.recipientEmail = payment.payer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.overdue")
        this.url = "/payment/checkout/${payment.publicId}"
        this.body = MessageUtils.getMessage("emailNotification.payer.body.payment.overdue", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildCustomerEmailPaymentCreated(Payment payment) {
        this.recipientEmail = payment.customer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.created")
        this.url = "/payment/show/${payment.id}"
        this.body = MessageUtils.getMessage("emailNotification.customer.body.payment.created", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildCustomerEmailPaymentPaid(Payment payment) {
        this.recipientEmail = payment.customer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.paid")
        this.url = "/payment/show/${payment.id}"
        this.body = MessageUtils.getMessage("emailNotification.customer.body.payment.paid", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildCustomerEmailPaymentPaidInCash(Payment payment) {
        this.recipientEmail = payment.customer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.paidInCash")
        this.url = "/payment/show/${payment.id}"
        this.body = MessageUtils.getMessage("emailNotification.customer.body.payment.paidInCash", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }

    public EmailNotificationAdapter buildCustomerEmailPaymentOverdue(Payment payment) {
        this.recipientEmail = payment.customer.email
        this.subject = MessageUtils.getMessage("emailNotification.subject.payment.overdue")
        this.url = "/payment/show/${payment.id}"
        this.body = MessageUtils.getMessage("emailNotification.customer.body.payment.overdue", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        this.isSent = false

        return this
    }


}
