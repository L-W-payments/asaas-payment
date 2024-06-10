package com.miniasaaslw.domain.paymentreceipt

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.BaseEntity

class PaymentReceipt extends BaseEntity {

    String publicId

    Payment payment

    static constraints = {
        publicId unique: true
        payment unique: true
    }
}
