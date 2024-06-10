package com.miniasaaslw.service.paymentreceipt

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.domain.paymentReceipt.PaymentReceipt
import com.miniasaaslw.utils.MessageUtils

class PaymentReceiptService {

    public void save(Payment payment) {
        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        if (payment.deleted) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        if (!payment.paymentStatus.isReceived() && !payment.paymentStatus.isReceivedInCash()) {
            throw new RuntimeException(MessageUtils.getMessage("paymentReceipt.errors.notReceived"))
        }

        PaymentReceipt paymentReceipt = new PaymentReceipt()

        paymentReceipt.publicId = UUID.randomUUID().toString().toUpperCase()
        paymentReceipt.payment = payment

        paymentReceipt.save(failOnError: true)
    }
}
