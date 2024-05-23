package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository
import grails.validation.ValidationException

class PayerController {

    def payerService

    def customerService

    def index() {
        def errors = flash.errors

        List<Customer> customers = CustomerRepository.query([:]).list()

        if (errors) {
            return [errors: errors, customers : customers]
        }

        return [customers: customers]
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
            redirect(action: 'show', params: [id: id])
        } catch (Exception exception) {
            flash.errors = ["Erro ao atualizar o pagador"]
            redirect(action: 'show', params: [id: id])
        }
    }

    def save() {
        Long customerId = params.long("customerId")

        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
            redirect(uri: "/payer")
        } catch (Exception exception) {
            flash.errors = ["Erro ao salvar o pagador"]
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
            flash.errors = ["Erro ao buscar o pagador"]
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
            flash.errors = ["Erro ao deletar o pagador"]
        }

        redirect(uri: "/payer")
    }
}
