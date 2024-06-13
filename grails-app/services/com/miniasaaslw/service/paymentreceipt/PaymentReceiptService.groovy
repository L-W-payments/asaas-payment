package com.miniasaaslw.service.paymentreceipt

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.domain.paymentreceipt.PaymentReceipt
import com.miniasaaslw.repository.paymentreceipt.PaymentReceiptRepository
import com.miniasaaslw.utils.MessageUtils
import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

@GrailsCompileStatic
class PaymentReceiptService {

    public PaymentReceipt find(String publicId) {
        PaymentReceipt paymentReceipt = PaymentReceiptRepository.query([publicId: publicId]).get()

        if (!paymentReceipt) throw new RuntimeException(MessageUtils.getMessage("paymentReceipt.errors.notFound"))

        return paymentReceipt
    }

    public void save(Payment payment) {
        Payment paymentData = validatePayment(payment)

        if (paymentData.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), paymentData.errors)
        }

        PaymentReceipt paymentReceipt = new PaymentReceipt()

        paymentReceipt.publicId = UUID.randomUUID().toString().toUpperCase()
        paymentReceipt.payment = payment

        paymentReceipt.save(failOnError: true)
    }

    public Payment validatePayment(Payment payment) {
        Payment validatePayment = new Payment()

        if (!payment) {
            validatePayment.errors.reject("payment", null, MessageUtils.getMessage("payment.errors.notFound"))
        }

        if (payment.deleted) {
            validatePayment.errors.reject("deleted", null, MessageUtils.getMessage("payment.errors.notFound"))
        }

        if (payment.paymentStatus.isReceivedInCash()) {
            validatePayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.receivedInCash"))
        }

        if (!payment.paymentStatus.isReceived()) {
            validatePayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.notReceived"))
        }

        return validatePayment
    }
}
