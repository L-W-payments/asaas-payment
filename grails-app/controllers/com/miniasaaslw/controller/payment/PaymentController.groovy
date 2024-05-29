package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer
import grails.validation.ValidationException

class PaymentController {

    def paymentService

    def index() {
        def alertInfo = flash.alertInfo

        List<Payer> payers = PayerRepository.query([:]).list()

        if (alertInfo) {
            return [payers: payers, alertInfo: alertInfo]
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

        redirect(uri: "/payment")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            flash.alertInfo = [alerts: ["Cobrança criada com sucesso"], alertType: "success"]
            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.alertInfo = [alerts: validationException.errors.allErrors.collect { it.defaultMessage } , alertType: "error"]
            redirect(uri: "/payment")
        } catch (Exception exception) {
            flash.alertInfo = [alerts: ["Erro ao salvar a cobrança"], alertType: "error"]
            redirect(uri: "/payment")
        }
    }

    def show() {
        Long id = params.long("id")

        try {
            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.errors = ["Pagamento não encontrado!"]
            redirect(uri: "/payment")
        }
    }
}
