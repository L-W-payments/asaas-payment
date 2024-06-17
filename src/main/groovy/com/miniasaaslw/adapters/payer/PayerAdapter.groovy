package com.miniasaaslw.adapters.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.entity.enums.PersonType

import com.miniasaaslw.utils.StringUtils

class PayerAdapter {

    String name

    String email

    String phone

    String cpfCnpj

    PersonType personType

    String postalCode

    String number

    String complement

    String country

    String city

    String state

    String district

    String street

    Customer customer

    public PayerAdapter(Customer customer, Map params) {
        this.customer = customer
        this.name = params.name
        this.email = params.email
        this.phone = StringUtils.removeNonNumeric(params.phone as String)
        this.cpfCnpj = StringUtils.removeNonNumeric(params.cpfCnpj as String)
        this.personType = PersonType.valueOf(params.personType.toString().toUpperCase())
        this.postalCode = StringUtils.removeNonNumeric(params.postalCode as String)
        this.number = params.number
        this.complement = params.complement
        this.country = params.country
        this.city = params.city
        this.state = params.state
        this.district = params.district
        this.street = params.street
    }
}
