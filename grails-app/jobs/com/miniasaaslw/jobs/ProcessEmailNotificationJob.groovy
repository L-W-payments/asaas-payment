package com.miniasaaslw.jobs

class ProcessEmailNotificationJob {

    def emailNotificationService

    static triggers = {
        simple name: "processEmailNotificationJobTrigger", repeatInterval: 1000 * 60
    }

    def execute() {
        try {
            emailNotificationService.processEmailNotification()
        } catch (Exception exception) {
            log.error("Erro ao tentar enviar o email", exception)
        }
    }
}
