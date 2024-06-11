package com.miniasaaslw.repository.user

import com.miniasaaslw.domain.security.User
import com.miniasaaslw.repository.Repository

import grails.compiler.GrailsCompileStatic

import groovy.transform.CompileDynamic

import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class UserRepository implements Repository<User, UserRepository> {

    @Override
    @CompileDynamic
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("email[like]")) {
                ilike("email", "%" + search."email[like]" + "%")
            }
        }
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "customerId",
                "email[like]"
        ]
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return User.createCriteria()
    }
}
