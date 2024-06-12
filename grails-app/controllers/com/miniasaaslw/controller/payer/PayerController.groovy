package com.miniasaaslw.controller.payer

import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.service.payer.PayerService
import com.miniasaaslw.utils.MessageUtils

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured(['isAuthenticated()'])
class PayerController extends BaseController {

    PayerService payerService

    def index() {
        def messageInfo = flash.messageInfo

        if (messageInfo) {
            return [messageInfo: messageInfo]
        }
    }

    def update() {
        Long id = params.long("id")

        try {
            Payer payer = payerService.update(id, new PayerAdapter((getAuthenticatedUser() as User).customer, params))

            redirect(action: "show", id: payer.id)
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "show", id: id)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payer.errors.save.unknown")], messageType: "error"]
            redirect(action: "show", id: id)
        }
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter((getAuthenticatedUser() as User).customer, params))

            flash.messageInfo = [messages: [MessageUtils.getMessage("payer.save.success")], messageType: "success"]
            redirect(action: "show", id: payer.id)
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payer.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def restore() {
        try {
            Long id = params.long("id")

            payerService.restore((getAuthenticatedUser() as User).customerId, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payer.errors.restore.unknown")], messageType: "error"]
            render([success: false, alert: MessageUtils.getMessage("payer.errors.restore.unknown")] as JSON)
        }
    }

    def show() {
        try {
            def messageInfo = flash.messageInfo

            Long id = params.long("id")

            if (messageInfo) {
                return [payer: payerService.find((getAuthenticatedUser() as User).customerId, id), messageInfo: messageInfo]
            }

            return [payer: payerService.find((getAuthenticatedUser() as User).customerId, id)]
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payer.errors.search.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def delete() {
        try {
            Long id = params.long("id")

            payerService.delete((getAuthenticatedUser() as User).customerId, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payer.errors.delete.unknown")] as JSON)
        }
    }

    def list() {
        return [payerList: payerService.list(getLimitPerPage(), getOffset(), [customerId: (getAuthenticatedUser() as User).customerId])]
    }

    def loadTableContent() {
        Map search = [customerId: (getAuthenticatedUser() as User).customerId]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.name) search."name[like]" = params.name

        List<Payer> payerList = payerService.list(getLimitPerPage(), getOffset(), search)
        Integer totalRecords = payerList.totalCount
        String content = g.render(template: "/payer/templates/tableContent", model: [payerList: payerList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
