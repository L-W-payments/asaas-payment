package com.miniasaaslw.repository.user

import com.miniasaaslw.domain.security.User
import com.miniasaaslw.repository.Repository

import grails.compiler.GrailsCompileStatic

import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class UserRepository implements Repository<User, UserRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.customerId) {
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
        return User.createCriteria()
    }
}
