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

    def save(){
        try{
            paymentService.save(new PaymentAdapter(params))
        }catch (ValidationException validationException){
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
        }catch (Exception exception){
            flash.errors = ["Erro ao salvar a cobrança"]
        }

        redirect(uri: "/payment")
    }
}
