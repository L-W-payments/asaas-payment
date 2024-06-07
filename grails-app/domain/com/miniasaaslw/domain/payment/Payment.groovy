package com.miniasaaslw.domain.payment

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.BaseEntity
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.entity.enums.payment.BillingType

class Payment extends BaseEntity {

    String publicId

    Customer customer

    Payer payer

    BillingType billingType

    PaymentStatus paymentStatus

    String description

    BigDecimal value

    Date dueDate

    public static final BigDecimal MIN_VALUE = new BigDecimal("10.00")

    public static final BigDecimal MAX_VALUE = new BigDecimal("10000.00")

    static constraints = {
        publicId nullable: false, unique: true
        customer nullable: false
        payer nullable: false
        billingType nullable: false
        paymentStatus nullable: false
        description blank: true, nullable: true
        value nullable: false
        dueDate nullable: false
    }
}
