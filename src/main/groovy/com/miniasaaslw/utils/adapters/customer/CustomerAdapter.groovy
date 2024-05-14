package com.miniasaaslw.utils.adapters.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.utils.entity.enums.PersonType

class CustomerAdapter {

    String name

    String email

    String phone

    String cpfCnpj

    PersonType personType

    String cep

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
        this.phone = params.phone
        this.cpfCnpj = params.cpfCnpj
        this.personType = PersonType.valueOf(params.personType.toString().toUpperCase())
        this.cep = params.cep
        this.number = params.number
        this.complement = params.complement
        this.country = params.country
        this.city = params.city
        this.state = params.state
        this.district = params.district
        this.street = params.street
    }

}
