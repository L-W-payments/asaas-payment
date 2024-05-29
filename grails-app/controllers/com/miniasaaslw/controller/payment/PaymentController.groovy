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
            flash.errors = ["Erro ao deletar pagamento!"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            redirect(action: "index", params: [success: "Cobrança criada com sucesso"])
        } catch (ValidationException validationException) {
            redirect(action: "index")
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        } catch (Exception exception) {
            redirect(action: "index")
            flash.errors = ["Erro ao salvar a cobrança"]
        }
    }

    def checkout() {
        Long id = params.long("id")

        try {
            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.errors = ["Pagamento não encontrado!"]
            redirect(action: "index")
        }
    }

    def pay() {
        Long id = params.long("id")

        try {
            paymentService.updateToReceived(id)

            redirect(action: "show", id: id)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.errors = ["Erro ao efetuar o pagamento"]
            redirect(action: "index")
        }
    }
}
