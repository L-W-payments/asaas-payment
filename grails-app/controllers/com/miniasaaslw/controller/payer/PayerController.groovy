package com.miniasaaslw.controller.payer

import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository

import grails.converters.JSON
import grails.validation.ValidationException

class PayerController extends BaseController {

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
        Long customerId = params.long("customerId")

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
        return [payerList: payerService.list(getLimitPerPage(), getOffset(), [:])]
    }

    def loadTableContent() {
        Map search = [:]
        def filters = params.filters

        if (filters) {
            if (filters.contains("includeDeleted")) search.includeDeleted = true
        }

        if (params.name) search."name[like]" = params.name

        List<Payer> payerList = payerService.list(getLimitPerPage(), getOffset(), search)
        Integer totalRecords = payerList.totalCount
        String content = g.render(template: "/payer/templates/tableContent", model: [payerList: payerList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }

    def fetchDelete() {
        try {
            Long id = params.long("id")

            payerService.delete(id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: "Erro ao deletar o pagador"] as JSON)
        }
    }


}
