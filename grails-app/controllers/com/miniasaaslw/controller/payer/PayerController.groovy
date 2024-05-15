package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.utils.adapters.payer.PayerAdapter
import grails.validation.ValidationException

class PayerController {

    def payerService

    def index() {
        def errors = flash.errors

        if (errors) {
            return [errors: errors]
        }
    }

    def update() {
        long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException exception) {
            flash.errors = exception.errors.allErrors.collect { it.defaultMessage }
            redirect(action: 'show', params: [id: id])
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: 'show', params: [id: payer.id])
        } catch (ValidationException exception) {
            flash.errors = exception.errors.allErrors.collect { it.defaultMessage }
            redirect(uri: "/payer")
        }
    }

    def show() {
        try {
            long id = params.long("id")

            return [payer: payerService.find(id)]
        } catch (RuntimeException ignored) {
            redirect(uri: "/payer")
        }
    }

    def delete() {
        try {
            long id = params.long("id")

            payerService.delete(id)
        } catch (RuntimeException ignored) {
            println("Pagador não encontrado")
        }

        redirect(uri: "/payer")
    }
}
