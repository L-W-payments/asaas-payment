package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer

import grails.converters.JSON
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

    def restore() {
        try {
            Long id = params.long("id")

            paymentService.restore(id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [runtimeException.getMessage(), messageType="error"]
            render([success: false] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [message(code: "payment.errors.restore.unknown"), messageType="error"]
            render([success: false] as JSON)
        }
    }

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER, id)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.delete.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            flash.messageInfo = [messages: ["Cobrança criada com sucesso"], messageType: "success"]
            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(uri: "/payment")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.save.unknown")], messageType: "error"]
            redirect(uri: "/payment")
        }
    }

    def checkout() {
        try {
            Long id = params.long("id")

            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.delete.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def updateToReceived() {
        try {
            Long publicId = params.long("id")

            paymentService.updateToReceived(publicId)

            redirect(action: "show", id: publicId)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [runtimeException.getMessage(), messageType="error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [message(code: "payment.errors.pay"), messageType="error"]
            redirect(action: "index")
        }
    }

}
