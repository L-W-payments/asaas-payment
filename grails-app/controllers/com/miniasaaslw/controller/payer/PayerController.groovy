package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository

import grails.converters.JSON
import grails.validation.ValidationException

class PayerController {

    def payerService

    def index() {
        def errors = flash.errors

        List<Customer> customers = CustomerRepository.query([:]).list()

        if (errors) {
            return [errors: errors, customers: customers]
        }

        return [customers: customers]
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: "show", params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
            redirect(action: "show", params: [id: id])
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.save.unknown")]
            redirect(action: "show", params: [id: id])
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: "show", params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.errors = validationException.errors.allErrors.collect { it.defaultMessage }
            redirect(uri: "/payer")
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.save.unknown")]
            redirect(uri: "/payer")
        }
    }

    def restore() {
        try {
            Long id = params.long("id")

            payerService.restore(id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
            render([success: false] as JSON)
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.restore.unknown")]
            render([success: false] as JSON)
        }
    }

    def show() {
        try {
            Long id = params.long("id")

            return [payer: payerService.find(id)]
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
        } catch (Exception exception) {
            flash.errors = [message(code: "payer.errors.search.unknown")]
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
            flash.errors = [message(code: "payer.errors.delete.unknown")]
        }

        redirect(uri: "/payer")
    }

    def list() {
        return [payers: payerService.list()]
    }
}
