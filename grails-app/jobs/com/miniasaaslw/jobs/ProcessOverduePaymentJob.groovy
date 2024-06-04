package com.miniasaaslw.jobs

class ProcessOverduePaymentJob {

    def paymentService

    static triggers = {
        cron name: "processOverduePaymentJobTrigger", cronExpression: "0 0/10 0-13 ? * *"
    }

    def execute() {
        try {
            paymentService.processOverduePayment()
        } catch (Exception exception) {
            log.error("Erro ao tentar atualizar status do pagamento", exception)
        }
    }
}
