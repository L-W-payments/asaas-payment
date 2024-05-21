package com.miniasaaslw.domain.customer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.BasePerson

class Customer extends BasePerson {

    static hasMany = [payers: Payer]

}
