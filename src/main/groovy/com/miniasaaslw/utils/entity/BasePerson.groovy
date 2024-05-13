package com.miniasaaslw.utils.entity

import com.miniasaaslw.utils.entity.enums.PersonType

class BasePerson extends BaseEntity {

    String name
    String email
    String phone
    String cpfCnpj
    PersonType personType

    static constraints = {
        email email: true
        phone size: 10..11
        cpfCnpj size: 11..14
    }
}
