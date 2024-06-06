package com.miniasaaslw.adapters.customer

import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.utils.StringUtils

class CustomerAdapter {

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


    public CustomerAdapter(Map params) {
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
