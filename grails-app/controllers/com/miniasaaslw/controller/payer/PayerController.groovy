package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository
import grails.validation.ValidationException

class PayerController {

    def payerService

    def index() {
        def messageInfo = flash.messageInfo

        List<Customer> customers = CustomerRepository.query([:]).list()

        if (messageInfo) {
            return [customers : customers, messageInfo: messageInfo]
        }

        return [customers: customers]
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage } , messageType: "error"]
            redirect(action: 'show', params: [id: id])
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.save.unknown")], messageType: "error"]
            redirect(action: 'show', params: [id: id])
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage } , messageType: "error"]
            redirect(uri: "/payer")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [messages: ["Erro ao salvar o pagador"]], messageType: "error"]
            redirect(uri: "/payer")
        }
    }

    def show() {
        try {
            Long id = params.long("id")

            return [payer: payerService.find(id)]
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.search.unknown")]
        }

        redirect(uri: "/payer")
    }

    def delete() {
        try {
            Long id = params.long("id")

            payerService.delete(id)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.delete.unknown")]
        }

        redirect(uri: "/payer")
    }

    def list() {
        return [payers: payerService.list()]
    }
}
