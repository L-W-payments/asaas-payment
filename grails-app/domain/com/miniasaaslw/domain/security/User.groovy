package com.miniasaaslw.domain.security

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.entity.BaseEntity
import grails.compiler.GrailsCompileStatic

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='email')
@ToString(includes='email', includeNames=true, includePackage=false)
class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1

    Customer customer

    String email
    String password
    Boolean enabled = true
    Boolean accountExpired = false
    Boolean accountLocked = false
    Boolean passwordExpired = false

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        email nullable: false, blank: false, unique: true, email: true
        customer nullable: false
    }

    static mapping = {
        password column: '`password`'
    }
}
