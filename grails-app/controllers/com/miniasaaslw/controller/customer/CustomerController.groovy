package com.miniasaaslw.controller.customer

import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.service.customer.CustomerService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(["ROLE_ADMIN"])
class CustomerController {

    CustomerService customerService

    @Secured(["permitAll"])
    def index() {
        def messageInfo = flash.messageInfo

        if (messageInfo) {
            return [messageInfo: messageInfo]
        }
    }

    @Secured(["permitAll"])
    def save() {
        try {
            customerService.save(new CustomerAdapter(params), params)
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.save.unknown")], messageType: "error"]
        }

        redirect(uri: "/index")
    }

    @CompileDynamic
    def show() {
        def messageInfo = flash.messageInfo

        try {
            Customer customer = customerService.find((getAuthenticatedUser() as User).customerId)

            if (customer) {
                if (messageInfo) {
                    return [customer: customer, messageInfo: messageInfo]
                }

                return [customer: customer]
            }
        } catch (RuntimeException runtimeException) {
            redirect(uri: "/index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.search.unknown")], messageType: "error"]
            redirect(uri: "/index")
        }
    }

    @CompileDynamic
    def update() {
        Long customerId = (getAuthenticatedUser() as User).customerId

        try {
            customerService.update(customerId, new CustomerAdapter(params))
            redirect(action: "show")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "show")
        } catch (Exception exception) {
            flash.messageInfo = [messages: ["Erro ao atualizar sua conta"], messageType: "error"]
            redirect(uri: "/index")
        }
    }

    @CompileDynamic
    def delete() {
        try {
            customerService.delete((getAuthenticatedUser() as User).customerId)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.delete.unknown")], messageType: "error"]
            render([success: false, alert: MessageUtils.getMessage("customer.errors.delete.unknown")] as JSON)
        }
        redirect(uri: "/index")
    }
}