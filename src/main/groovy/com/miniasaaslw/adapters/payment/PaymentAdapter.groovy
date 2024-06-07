package com.miniasaaslw.adapters.payment

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.enums.payment.BillingType
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.DateUtils
import com.miniasaaslw.utils.LoggedCustomer

class PaymentAdapter {

    Customer customer

    Payer payer

    BillingType billingType

    String description

    BigDecimal value

    Date dueDate

    public PaymentAdapter(Map params) {
        this.customer = LoggedCustomer.CUSTOMER
        this.payer = PayerRepository.query([id: params.payerId as Long]).get()
        this.billingType = BillingType.valueOf(params.billingType.toString().toUpperCase())
        this.description = params.description?.toString()?.trim()
        this.value = new BigDecimal((params.value as String).replace(",", "."))
        this.dueDate = DateUtils.parseDate(params.dueDate as String)
    }
}
