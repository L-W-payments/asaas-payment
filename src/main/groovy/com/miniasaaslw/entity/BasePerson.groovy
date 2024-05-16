package com.miniasaaslw.entity

import com.miniasaaslw.entity.enums.PersonType

class BasePerson extends BaseEntity {

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

    static constraints = {
        email email: true
        phone size: 10..11
        cpfCnpj size: 11..14
        cep blank: true, nullable: true, size: 8..8
        complement blank: true, nullable: true
        state size: 2..2
    }
}