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

    public static NotificationAdapter buildPaymentCreated(Payment payment) {
        NotificationAdapter notificationAdapter = new NotificationAdapter()

        notificationAdapter.payment = payment
        notificationAdapter.title = MessageUtils.getMessage("notification.payment.saved.title")
        notificationAdapter.message = MessageUtils.getMessage("notification.payment.saved.message", [
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        notificationAdapter.url = "/payment/show/${payment.id}"
        notificationAdapter.type = NotificationType.INFO

        return notificationAdapter
    }

    public static NotificationAdapter buildPaymentRestored(Payment payment) {
        NotificationAdapter notificationAdapter = new NotificationAdapter()

        notificationAdapter.payment = payment
        notificationAdapter.title = MessageUtils.getMessage("notification.payment.restored.title")
        notificationAdapter.message = MessageUtils.getMessage("notification.payment.restored.message", [payment.value.toString()])
        notificationAdapter.url = "/payment/show/${payment.id}"
        notificationAdapter.type = NotificationType.INFO

        return notificationAdapter
    }

    public static NotificationAdapter buildPaymentDeleted(Payment payment) {
        NotificationAdapter notificationAdapter = new NotificationAdapter()

        notificationAdapter.payment = payment
        notificationAdapter.title = MessageUtils.getMessage("notification.payment.deleted.title")
        notificationAdapter.message = MessageUtils.getMessage("notification.payment.deleted.message", [
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        notificationAdapter.url = "/payment/show/${payment.id}"
        notificationAdapter.type = NotificationType.INFO

        return notificationAdapter
    }

    public static NotificationAdapter buildPaymentReceived(Payment payment) {
        NotificationAdapter notificationAdapter = new NotificationAdapter()

        notificationAdapter.payment = payment
        notificationAdapter.title = MessageUtils.getMessage("notification.payment.received.title")
        notificationAdapter.message = MessageUtils.getMessage("notification.payment.received.message", [
                payment.payer.name,
                payment.value.toString(),
                DateUtils.formatLongDate(payment.lastUpdated)
        ])
        notificationAdapter.url = "/payment/show/${payment.id}"
        notificationAdapter.type = NotificationType.INFO

        return notificationAdapter
    }

    public static NotificationAdapter buildPaymentOverdue(Payment payment) {
        NotificationAdapter notificationAdapter = new NotificationAdapter()

        notificationAdapter.payment = payment
        notificationAdapter.title = MessageUtils.getMessage("notification.payment.overdue.title")
        notificationAdapter.message = MessageUtils.getMessage("notification.payment.overdue.message", [
                payment.payer.name,
                DateUtils.formatLongDate(payment.dueDate)
        ])
        notificationAdapter.url = "/payment/show/${payment.id}"
        notificationAdapter.type = NotificationType.INFO

        return notificationAdapter
    }
}
