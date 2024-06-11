package com.miniasaaslw.adapters.user

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.Role

class UserAdapter {

    Customer customer

    Role role

    String email

    String password

    String confirmPassword

    public UserAdapter(Customer customer, Role role, Map params) {
        this.customer = customer
        this.role = role
        this.email = params.email
        this.password = params.password
        this.confirmPassword = params.confirmPassword
    }
}
