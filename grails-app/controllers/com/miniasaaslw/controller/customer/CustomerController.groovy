package com.miniasaaslw.controller.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter

import grails.validation.ValidationException

class CustomerController {

    def customerService

    def index() {
        def messageInfo = flash.messageInfo

        if (messageInfo) {
            return [messageInfo: messageInfo]
        }
    }

    def save() {
        try {
            Customer customer = customerService.save(new CustomerAdapter(params))

            redirect(action: "show", id: customer.id)
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "customer.errors.save.unknown")], messageType: "error"]
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
            flash.messageInfo = [messages: [message(code: "customer.errors.search.unknown")], messageType: "error"]
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
            flash.messageInfo = [messages: [message(code: "customer.errors.delete.unknown")], messageType: "error"]
        }
        redirect(action: "index")
    }
}