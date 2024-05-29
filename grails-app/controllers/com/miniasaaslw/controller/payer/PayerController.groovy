package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.utils.LoggedCustomer
import grails.validation.ValidationException

class PayerController {

    def payerService

    def index() {
        def alertInfo = flash.alertInfo

        List<Customer> customers = CustomerRepository.query([:]).list()

        if (alertInfo) {
            return [customers : customers, alertInfo: alertInfo]
        }

        return [customers: customers]
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.alertInfo = [alerts: validationException.errors.allErrors.collect { it.defaultMessage } , alertType: "error"]
            redirect(action: 'show', params: [id: id])
        } catch (Exception exception) {
            flash.alertInfo = [alerts: ["Erro ao atualizar o pagador"], alertType: "error"]
            redirect(action: 'show', params: [id: id])
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.alertInfo = [alerts: validationException.errors.allErrors.collect { it.defaultMessage } , alertType: "error"]
            redirect(uri: "/payer")
        } catch (Exception exception) {
            flash.alertInfo = [alerts: ["Erro ao salvar o pagador"], alertType: "error"]
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

    def list() {
        return [payers: payerService.list()]
    }
}
