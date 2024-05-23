package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
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

    def list() {
        return [payers: payerService.list()]
    }
}
