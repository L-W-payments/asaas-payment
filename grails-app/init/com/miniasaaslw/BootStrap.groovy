package com.miniasaaslw

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.domain.security.UserRole
import com.miniasaaslw.repository.customer.CustomerRepository

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        addTestUser()
    }

    @Transactional
    void addTestUser() {
        new Role(authority: 'ROLE_ADMIN').save()

        def adminRole = new Role(authority: 'ROLE_MEMBER').save()

        Customer customer = CustomerRepository.query().get()

        def testUser = new User(email: 'johndoe@example.com', password: '123', customer: customer).save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 1
        assert Role.count() == 2
        assert UserRole.count() == 1
    }

    def destroy = {
    }
}
