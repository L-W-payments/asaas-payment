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

        redirect(uri: "/payment")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            redirect(uri: "/payment", params: [success: "Cobrança criada com sucesso"])
        } catch (ValidationException validationException) {
            redirect(uri: "/payment")
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        } catch (Exception exception) {
            redirect(uri: "/payment")
            flash.errors = ["Erro ao salvar a cobrança"]
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

    def list() {
        return [paymentList: paymentService.list(0, [:])]
    }

    def loadTableContent() {

        Map search = [:]
        if (params.payerName) search.payerName = params.payerName

        List<Payment> paymentList = paymentService.list((params.page as Integer) - 1, search)
        Integer totalRecords = paymentList.totalCount
        String content = g.render(template: "/payment/templates/tableContent", model: [paymentList: paymentList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }

    def fetchDelete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: "Erro ao deletar a cobrança"] as JSON)
        }
    }
}
