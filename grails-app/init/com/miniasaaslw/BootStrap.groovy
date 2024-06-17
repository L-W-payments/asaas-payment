package com.miniasaaslw

import com.miniasaaslw.domain.security.Role

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        addRoles()
    }

    @Transactional
    void addRoles() {
        Role.findOrCreateWhere(authority: 'ROLE_ADMIN').save()
        Role.findOrCreateWhere(authority: 'ROLE_MEMBER').save()
    }

    def destroy = {
    }
}
