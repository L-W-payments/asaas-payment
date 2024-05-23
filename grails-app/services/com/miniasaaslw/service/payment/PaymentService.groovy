package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payment.PaymentRepository
import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {

    def payerService
    def customerService

    public void delete(Customer customer, Long paymentId) {
        Payment payment = PaymentRepository.query([id: paymentId, customer: customer]).get()

        if (!payment) throw new RuntimeException("Pagamento não encontrado!")

        if (payment.deleted) return

        payment.deleted = true
        payment.save(failOnError: true)
    }

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
