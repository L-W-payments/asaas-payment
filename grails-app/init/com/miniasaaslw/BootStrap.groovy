package com.miniasaaslw

class BootStrap {

    def init = { servletContext ->
        grailsApplication.config.grails.dataSource.smtp_email = env.SMTP_EMAIL.value
        grailsApplication.config.grails.dataSource.smtp_password = env.SMTP_PASSWORD.value
    }
    def destroy = {
    }
}
