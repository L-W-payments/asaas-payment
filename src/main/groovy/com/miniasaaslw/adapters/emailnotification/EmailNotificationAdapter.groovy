package com.miniasaaslw.adapters.emailnotification

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.utils.DateUtils
import com.miniasaaslw.utils.MessageUtils

class EmailNotificationAdapter {

    String recipientEmail

    String subject

    String url

    String body

    Boolean sent

    public static EmailNotificationAdapter buildPayerEmailPaymentCreated(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.created")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.created", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildPayerEmailPaymentPaid(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.paid")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.paid", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildPayerEmailPaymentPaidInCash(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.paidInCash")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.paidInCash", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildPayerEmailPaymentOverdue(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.overdue")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.overdue", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildPayerEmailPaymentDeleted(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.deleted")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.deleted", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildPayerEmailPaymentRestored(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.payer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.restored")
        emailNotificationAdapter.url = "/payment/checkout/${payment.publicId}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.payer.body.payment.restored", [
                payment.customer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentCreated(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.created")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.created", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentPaid(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.paid")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.paid", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentPaidInCash(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.paidInCash")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.paidInCash", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentOverdue(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.overdue")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.overdue", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentDeleted(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.deleted")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.deleted", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

    public static EmailNotificationAdapter buildCustomerEmailPaymentRestored(Payment payment) {
        EmailNotificationAdapter emailNotificationAdapter = new EmailNotificationAdapter()

        emailNotificationAdapter.recipientEmail = payment.customer.email
        emailNotificationAdapter.subject = MessageUtils.getMessage("emailNotification.subject.payment.restored")
        emailNotificationAdapter.url = "/payment/show/${payment.id}"
        emailNotificationAdapter.body = MessageUtils.getMessage("emailNotification.customer.body.payment.restored", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatDate(payment.dueDate)
        ])
        emailNotificationAdapter.sent = false

        return emailNotificationAdapter
    }

}
