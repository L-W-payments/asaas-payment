package com.miniasaaslw.controller.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.service.customer.CustomerService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
@Secured(["isAuthenticated()"])
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

            redirect(uri: "/index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def show() {
        def messageInfo = flash.messageInfo

        try {
            Customer customer = customerService.find(params.long("id"))

            if (customer) {
                if (messageInfo) {
                    return [customer: customer, messageInfo: messageInfo]
                }
                return [customer: customer]
            }
        } catch (RuntimeException runtimeException) {
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.search.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def update() {
        long id = params.long("id")

        try {
            Customer customer = customerService.update(id, new CustomerAdapter(params))

            redirect(action: "show", id: customer.id)
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "show", id: id)
        } catch (Exception exception) {
            flash.messageInfo = [messages: ["Erro ao atualizar sua conta"], messageType: "error"]
            redirect(action: "index")
        }
    }

    def delete() {
        try {
            customerService.delete(params.long("id"))
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("customer.errors.delete.unknown")], messageType: "error"]
        }
        redirect(action: "index")
    }
}