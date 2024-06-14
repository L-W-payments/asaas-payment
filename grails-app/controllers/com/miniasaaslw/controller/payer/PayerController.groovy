package com.miniasaaslw.controller.payer

import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.exception.BusinessException
import com.miniasaaslw.service.payer.PayerService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['isAuthenticated()'])
class PayerController extends BaseController {

    PayerService payerService

    def index() {
        if (hasMessages()) {
            return [messageInfo: getMessagesObject()]
        }
    }

    def update() {
        Long id = params.long("id")

        try {
            payerService.update(id, new PayerAdapter(getCurrentCustomer(), params))

            addMessageCode("payer.update.success", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payer.errors.save.unknown", MessageType.ERROR)
        }

        redirect(action: "show", id: id)
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(getCurrentCustomer(), params))

            addMessageCode("payer.save.success", MessageType.SUCCESS)

            redirect(action: "show", id: payer.id)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payer.errors.save.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }

    def restore() {
        try {
            Long id = params.long("id")

            payerService.restore(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (BusinessException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payer.errors.restore.unknown")] as JSON)
        }
    }

    def show() {
        try {
            Long id = params.long("id")

            Payer payer = payerService.find(getCurrentCustomerId(), id)

            if (hasMessages()) {
                return [payer: payer, messageInfo: getMessagesObject()]
            }

            return [payer: payer]
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payer.errors.search.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }

    def delete() {
        try {
            Long id = params.long("id")

            payerService.delete(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (BusinessException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payer.errors.delete.unknown")] as JSON)
        }
    }

    def list() {
        return [payerList: payerService.list(getLimitPerPage(), getOffset(), [customerId: getCurrentCustomerId()])]
    }

    @CompileDynamic
    def loadTableContent() {
        Map search = [customerId: getCurrentCustomerId()]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.name) search."name[like]" = params.name

        List<Payer> payerList = payerService.list(getLimitPerPage(), getOffset(), search)
        Integer totalRecords = payerList.totalCount
        String content = g.render(template: "/payer/templates/tableContent", model: [payerList: payerList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
