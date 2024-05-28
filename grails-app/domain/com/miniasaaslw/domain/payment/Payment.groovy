package com.miniasaaslw.domain.payment

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.BaseEntity
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.entity.enums.payment.PaymentType

class Payment extends BaseEntity {

    String publicId

    Customer customer

    Payer payer

    PaymentType paymentType

    PaymentStatus paymentStatus

    String description

    BigDecimal value

    Date dueDate

    static constraints = {
        publicId nullable: false, unique: true
        customer nullable: false
        payer nullable: false
        paymentType nullable: false
        paymentStatus nullable: false
        description blank: true, nullable: true
        value nullable: false
        dueDate nullable: false
    }
}
