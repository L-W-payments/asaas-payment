package com.miniasaaslw.adapters.payment

import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.entity.enums.payment.PaymentType

class PaymentAdapter {

    Long customerId

    Long payerId

    PaymentType paymentType

    PaymentStatus paymentStatus

    String description

    BigDecimal value

    Date dueDate

    public PaymentAdapter(Map params) {
        this.customerId = params.customerId as Long
        this.payerId = params.payerId as Long
        this.paymentType = PaymentType.valueOf(params.paymentType.toString().toUpperCase())
        this.paymentStatus = PaymentStatus.valueOf(params.paymentStatus.toString().toUpperCase())
        this.description = params.description?.toString()?.trim()
        this.value = new BigDecimal(params.value as String)
        this.dueDate = new Date(params.dueDate as String)
    }
}
