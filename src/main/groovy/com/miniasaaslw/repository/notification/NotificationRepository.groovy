package com.miniasaaslw.repository.notification

import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class NotificationRepository implements BaseEntityRepository {

    public static DetachedCriteria<Notification> query(Map search) {
        DetachedCriteria<Notification> query = Notification.where(defaultQuery(search))

        query = query.where {
            if (search.containsKey("customerId")) {
                eq("customer.id", Long.valueOf(search.customerId))
            }
        }

        return query
    }
}