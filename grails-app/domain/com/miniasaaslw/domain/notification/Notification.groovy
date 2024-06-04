package com.miniasaaslw.domain.notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.entity.BaseEntity
import com.miniasaaslw.entity.enums.notification.NotificationPriority
import com.miniasaaslw.entity.enums.notification.NotificationType

class Notification extends BaseEntity {

    Customer customer

    String title

    String message

    String url

    NotificationType type

    NotificationPriority priority
}
