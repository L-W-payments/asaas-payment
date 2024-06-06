package com.miniasaaslw.controller.payer

import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.utils.LoggedCustomer
import grails.converters.JSON
import grails.validation.ValidationException

class PayerController extends BaseController {

    def payerService

    def index() {
        def messageInfo = flash.messageInfo

        List<Customer> customers = CustomerRepository.query([:]).list()

        if (messageInfo) {
            return [customers: customers, messageInfo: messageInfo]
        }

        return [customers: customers]
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter(params))

            redirect(action: "show", params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "show", params: [id: id])
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.save.unknown")], messageType: "error"]
            redirect(action: "show", params: [id: id])
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            redirect(action: "show", params: [id: payer.id])
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def restore() {
        try {
            Long id = params.long("id")

            payerService.restore(LoggedCustomer.CUSTOMER.id, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.restore.unknown")], messageType: "error"]
            render([success: false] as JSON)
        }
    }

    def show() {
        try {
            Long id = params.long("id")

            return [payer: payerService.find(id)]
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.search.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def delete() {
        try {
            Long id = params.long("id")

            payerService.delete(id)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payer.errors.delete.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def list() {
        return [payerList: payerService.list(getLimitPerPage(), getOffset(), [:])]
    }

    def loadTableContent() {
        Map search = [:]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
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
