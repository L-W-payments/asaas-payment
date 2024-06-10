package com.miniasaaslw.domain.paymentReceipt

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.BaseEntity

class PaymentReceipt extends BaseEntity {

    String publicId

    Payment payment

}