package com.miniasaaslw.adapters.notification

import com.miniasaaslw.entity.enums.notification.NotificationType

class NotificationAdapter {

    String title

    String message

    String url

    NotificationType type

    public NotificationAdapter(String title, String message, String url, NotificationType type) {
        this.title = title
        this.message = message
        this.url = url
        this.type = type
    }
}
