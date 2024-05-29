package com.miniasaaslw.jobs

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.repository.payment.PaymentRepository

class PaymentExpirationJob {

    def paymentService

    static triggers = {
        String everyDayAtMidnightExpression = "0 0 0 ? * *"

        cron name: "paymentExpirationJobTrigger", cronExpression: everyDayAtMidnightExpression
    }

    def execute() {
        List<Payment> expiredPayments = PaymentRepository.query([
                paymentStatus: PaymentStatus.PENDING,
                "dueDate[lt]": new Date()
        ]).list()

        expiredPayments.each { Payment payment ->
            paymentService.updateToExpired(payment.id)
        }
    }
}
