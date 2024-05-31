package com.miniasaaslw.jobs

import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.repository.payment.PaymentRepository

class ExpirePaymentJob {

    def paymentService

    static triggers = {
        cron name: "expirePaymentJobTrigger", cronExpression: "0 0/10 0-13 ? * *"
    }

    def execute() {
        List<Long> paymentIdList = PaymentRepository.query([
                paymentStatus: PaymentStatus.PENDING,
                "dueDate[lt]": new Date(),
                "column"     : "id"
        ]).list() as List<Long>

        paymentIdList.each { Long id ->
            try {
                paymentService.updateToExpired(id)
            } catch (Exception exception) {
                log.error("Erro ao atualizar a cobrança para expirado", exception)
            }
        }
    }
}
