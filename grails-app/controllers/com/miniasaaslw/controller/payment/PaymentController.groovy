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
            redirect(uri: "/payment", params: [success: message(code: "payment.save.success")])
        } catch (ValidationException validationException) {
            redirect(uri: "/payment")
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        } catch (Exception exception) {
            redirect(uri: "/payment")
            flash.errors = [message(code: "payment.errors.save.unknown")]
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

    def show() {
        Long id = params.long("id")

        try {
            return [payment: paymentService.find(LoggedCustomer.CUSTOMER, id)]
        } catch (Exception exception) {
            flash.errors = ["Pagamento não encontrado!"]
            redirect(uri: "/payment")
        }
    }
}
