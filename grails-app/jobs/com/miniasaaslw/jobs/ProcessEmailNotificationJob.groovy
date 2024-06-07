package com.miniasaaslw.jobs

import java.time.Duration

class ProcessEmailNotificationJob {

    def emailNotificationService

    static triggers = {
        simple name: "processEmailNotificationJobTrigger", repeatInterval: Duration.ofSeconds(60).toMillis()
    }

    def execute() {
        try {
            emailNotificationService.processEmailNotification()
        } catch (Exception exception) {
            log.error("ProcessEmailNotificationJob.execute >> Erro ao tentar enviar o email", exception)
        }
    }
}
