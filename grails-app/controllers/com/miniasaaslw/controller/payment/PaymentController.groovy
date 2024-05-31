package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer

import grails.validation.ValidationException

class PaymentController {

    def paymentService

    def index() {
        def messageInfo = flash.messageInfo

        List<Payer> payers = PayerRepository.query([:]).list()

        if (messageInfo) {
            return [payers: payers, messageInfo: messageInfo]
        }

        return [payers: payers]
    }

    def delete() {
        Long id = params.long("id")

        try {
            paymentService.delete(LoggedCustomer.CUSTOMER, id)
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.delete.unknown")]
        }

        redirect(uri: "/payment")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            flash.messageInfo = [messages: ["Cobrança criada com sucesso"], messageType: "success"]
            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage } , messageType: "error"]
            redirect(uri: "/payment")
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.save.unknown")], messageType: "error"]
            redirect(uri: "/payment")
        }
    }

    def checkout() {
        Long id = params.long("id")

        try {
            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.notFound")]
            redirect(uri: "/payment")
        }
    }
}
