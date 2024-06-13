package com.miniasaaslw.controller.payer

import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.exception.GenericException
import com.miniasaaslw.service.payer.PayerService
import com.miniasaaslw.utils.MessageUtils

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

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
            payerService.update(id, new PayerAdapter((getAuthenticatedUser() as User).customer, params))

            addMessageCode("payer.update.success", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payer.errors.save.unknown", MessageType.ERROR)
        }

        redirect(action: "show", id: id)
    }

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter((getAuthenticatedUser() as User).customer, params))

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

            payerService.restore((getAuthenticatedUser() as User).customerId, id)
            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payer.errors.restore.unknown")] as JSON)
        }
    }

    def show() {
        try {
            Long id = params.long("id")

            Payer payer = payerService.find((getAuthenticatedUser() as User).customerId, id)

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

            payerService.delete((getAuthenticatedUser() as User).customerId, id)
            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
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
