package com.miniasaaslw.jobs

class ProcessOverduePaymentJob {

    def paymentService

    static triggers = {
        cron name: "expirePaymentJobTrigger", cronExpression: "0 0/10 0-13 ? * *"
    }

    def execute() {
        paymentService.processOverduePayment()
    }
}
