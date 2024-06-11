package com.miniasaaslw.controller.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.LoggedCustomer
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
@Secured(['ROLE_ADMIN'])
class UserController {

    UserService userService

    def index() {}

    def save() {
        try {
            userService.save(new UserAdapter(LoggedCustomer.CUSTOMER, Role.findByAuthority('ROLE_MEMBER'), params))

            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }
}
