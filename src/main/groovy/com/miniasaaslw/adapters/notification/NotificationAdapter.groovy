package com.miniasaaslw.adapters.notification

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.notification.NotificationType
import com.miniasaaslw.utils.DateUtils
import com.miniasaaslw.utils.MessageUtils

class NotificationAdapter {

    Payment payment

    String title

    String message

    String url

    NotificationType type

    public NotificationAdapter buildPaymentCreated(Payment payment) {
        this.payment = payment
        this.title = MessageUtils.getMessage("notification.payment.saved.title")
        this.message = MessageUtils.getMessage("notification.payment.saved.message", [
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        this.url = "/payment/show/${payment.id}"
        this.type = NotificationType.INFO

        return this
    }

    public NotificationAdapter buildPaymentRestored(Payment payment) {
        this.payment = payment
        this.title = MessageUtils.getMessage("notification.payment.restored.title")
        this.message = MessageUtils.getMessage("notification.payment.restored.message", [payment.value.toString()])
        this.url = "/payment/show/${payment.id}"
        this.type = NotificationType.INFO

        return this
    }

    public NotificationAdapter buildPaymentDeleted(Payment payment) {
        this.payment = payment
        this.title = MessageUtils.getMessage("notification.payment.deleted.title")
        this.message = MessageUtils.getMessage("notification.payment.deleted.message", [
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        this.url = "/payment/show/${payment.id}"
        this.type = NotificationType.INFO

        return this
    }

    public NotificationAdapter buildPaymentReceived(Payment payment) {
        this.payment = payment
        this.title = MessageUtils.getMessage("notification.payment.received.title")
        this.message = MessageUtils.getMessage("notification.payment.received.message", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        this.url = "/payment/show/${payment.id}"
        this.type = NotificationType.INFO

        return this
    }

    public NotificationAdapter buildPaymentOverdue(Payment payment) {
        this.payment = payment
        this.title = MessageUtils.getMessage("notification.payment.overdue.title")
        this.message = MessageUtils.getMessage("notification.payment.overdue.message", [
                payment.payer.name,
                DateUtils.formatLongDate(payment.dueDate)
        ])
        this.url = "/payment/show/${payment.id}"
        this.type = NotificationType.INFO

        return this
    }
}
