package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer

import grails.validation.ValidationException

class PaymentController {

    def paymentService

    def index() {
        def errors = flash.errors
        List<Payer> payers = PayerRepository.query([:]).list()

        if (errors) {
            return [payers: payers, errors: errors]
        }

        return [payers: payers]
    }

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER, id)
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.delete.unknown")]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            redirect(action: "index", params: [success: message(code: "payment.save.success")])
        } catch (ValidationException validationException) {
            redirect(action: "index")
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        } catch (Exception exception) {
            redirect(action: "index")
            flash.errors = [message(code: "payment.errors.save.unknown")]
        }
    }

    def checkout() {
        try {
            Long id = params.long("id")
            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.notFound")]
            redirect(action: "index")
        }
    }

    def updateToReceived() {
        try {
            Long publicId = params.long("id")

            paymentService.updateToReceived(publicId)

            redirect(action: "show", id: publicId)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.pay")]
            redirect(action: "index")
        }
    }

    def updateToReceivedInCash() {
        try {
            Long id = params.long("id")

            paymentService.updateToReceivedInCash(LoggedCustomer.CUSTOMER, id)

            redirect(action: "show", id: id)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.pay")]
            redirect(action: "index")
        }
    }

    def show() {
        try {
            Long id = params.long("id")
            return [payment: paymentService.find(LoggedCustomer.CUSTOMER, id)]
        } catch (Exception exception) {
            flash.errors = ["Pagamento não encontrado!"]
            redirect(uri: "/payment")
        }
    }
}
