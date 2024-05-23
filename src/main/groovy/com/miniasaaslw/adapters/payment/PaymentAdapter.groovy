package com.miniasaaslw.adapters.payment

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.entity.enums.payment.PaymentType
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.repository.payer.PayerRepository

class PaymentAdapter {

    Customer customer

    Payer payer

    PaymentType paymentType

    PaymentStatus paymentStatus

    String description

    BigDecimal value

    Date dueDate

    public PaymentAdapter(Map params) {
        this.customer = CustomerRepository.query([id: params.customerId as Long]).get()
        this.payer = PayerRepository.query([id: params.payerId as Long]).get()
        this.paymentType = PaymentType.valueOf(params.paymentType.toString().toUpperCase())
        this.paymentStatus = PaymentStatus.valueOf(params.paymentStatus.toString().toUpperCase())
        this.description = params.description?.toString()?.trim()
        this.value = new BigDecimal(params.value as String)
        this.dueDate = new Date(params.dueDate as String)
    }
}
