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
            redirect(uri: '/customer')
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "customer.errors.save.unknown")], messageType: "error"]
            redirect(uri: '/customer')
        }
    }

    def show(){
        def errors = flash.errors
        try{
            Customer customer = customerService.find(params.long("id"))
            if(customer){
                if(errors){
                    return [customer: customer, errors: errors]
                }
                return [customer: customer]
            }
        } catch (RuntimeException runtimeException){
            redirect(uri: "/customer")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "customer.errors.search.unknown")], messageType: "error"]
            redirect(uri: '/customer')
        }
    }

    def update() {
        long id = params.long("id")

        try {
            Customer customer = customerService.update(id, new CustomerAdapter(params))
            redirect(action: 'show', params: [id: customer.id])
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(uri: ('/customer/show/' + id))
        } catch (Exception exception) {
            flash.messageInfo = [messages: ["Erro ao atualizar sua conta"], messageType: "error"]
            redirect(uri: '/customer')
        }
    }

    def delete() {
        long id = params.long("id")

        try {
            customerService.delete(id)
            redirect(uri: "/customer")
        } catch (RuntimeException runtimeException) {
            redirect(uri: "/customer")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "customer.errors.delete.unknown")], messageType: "error"]
            redirect(uri: "/customer")
        }
    }
}