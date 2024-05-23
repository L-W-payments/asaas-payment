package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {

    def payerService
    def customerService

    Payment save(PaymentAdapter paymentAdapter) {
        Payer payer = payerService.find(paymentAdapter.payerId)
        Customer customer = customerService.find(paymentAdapter.customerId)

        Payment payment = new Payment(
                payer: payer,
                customer: customer,
                value: paymentAdapter.value,
                dueDate: paymentAdapter.dueDate,
                paymentStatus: paymentAdapter.paymentStatus,
                paymentType: paymentAdapter.paymentType
        )

        payment.save(failOnError: true)

        return payment
    }
}
