package com.miniasaaslw

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.domain.security.UserRole
import com.miniasaaslw.repository.customer.CustomerRepository

import grails.gorm.transactions.Transactional


class BootStrap {

    def init = { servletContext ->
        addRoles()
        addTestUser()
    }

    @Transactional
    void addRoles() {
        Role.findOrCreateWhere(authority: 'ROLE_ADMIN').save()
        Role.findOrCreateWhere(authority: 'ROLE_MEMBER').save()
    }

    @Transactional
    void addTestUser() {
        Customer customer = CustomerRepository.query().get()

        def testUser = new User(email: 'johndoe@example.com', password: '123', customer: customer).save()

        UserRole.create(testUser, Role.findByAuthority('ROLE_ADMIN'), true)

        UserRole.withSession {
            it.flush()
            it.clear()
        }
    }

    def destroy = {
    }
}
