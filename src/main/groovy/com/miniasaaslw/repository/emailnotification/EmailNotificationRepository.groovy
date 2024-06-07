package com.miniasaaslw.repository.emailnotification

import com.miniasaaslw.domain.emailnotification.EmailNotification
import com.miniasaaslw.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

class EmailNotificationRepository implements Repository<EmailNotification, EmailNotificationRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("sent")){
                eq("sent", search.sent)
            }
        }
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return EmailNotification.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "sent"
        ]
    }
}
