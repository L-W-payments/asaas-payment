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

    public Customer toDomain(){
        return new Customer(name: this.name, email: this.email, phone: this.phone, cpfCnpj: this.cpfCnpj, personType: this.personType, cep: this.cep, number: this.number, complement: this.complement, country: this.country, city: this.city, state: this.state, district: this.district, street: this.street)
    }
}
