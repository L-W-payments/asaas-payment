package com.miniasaaslw.repository.notification

import com.miniasaaslw.domain.notification.Notification
import com.miniasaaslw.repository.Repository

import grails.compiler.GrailsCompileStatic

import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class NotificationRepository implements Repository<Notification, NotificationRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }
        }
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "customerId"
        ]
    }


    @Override
    BuildableCriteria getBuildableCriteria() {
        return Notification.createCriteria()
    }
}
