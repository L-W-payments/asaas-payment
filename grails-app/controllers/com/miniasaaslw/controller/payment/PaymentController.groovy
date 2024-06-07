package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer

import grails.converters.JSON
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_MEMBER'])
class PaymentController extends BaseController {

    def paymentService

    def index() {
        def messageInfo = flash.messageInfo

        List<Payer> payers = PayerRepository.query([:]).list()

        if (messageInfo) {
            return [payers: payers, messageInfo: messageInfo]
        }

        return [payers: payers]
    }

    def restore() {
        try {
            Long id = params.long("id")

            paymentService.restore(LoggedCustomer.CUSTOMER.id, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.restore.unknown")], messageType: "error"]
            render([success: false, alert: message(code: "payment.errors.restore.unknown")] as JSON)
        }
    }

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER.id, id)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.delete.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))

            flash.messageInfo = [messages: [message(code: "payment.save.success")], messageType: "success"]
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.save.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    @Secured(["permitAll"])
    def checkout() {
        try {
            String publicId = params.id

            return [payment: paymentService.find(publicId)]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.notFound")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def list() {
        return [paymentList: paymentService.list([customerId: LoggedCustomer.CUSTOMER.id], getLimitPerPage(), getOffset())]
    }

    def loadTableContent() {
        Map search = [customerId: LoggedCustomer.CUSTOMER.id]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.payerName) search."payerName[like]" = params.payerName

        List<Payment> paymentList = paymentService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = paymentList.totalCount
        String content = g.render(template: "/payment/templates/tableContent", model: [paymentList: paymentList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }

    def fetchDelete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER.id, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: message(code: "payment.errors.delete.unknown")] as JSON)
        }
    }

    @Secured(['permitAll'])
    def updateToReceived() {
        try {
            Long publicId = params.long("id")

            paymentService.updateToReceived(publicId)

            redirect(action: "show", id: publicId)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.pay")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def updateToReceivedInCash() {
        try {
            Long id = params.long("id")

            paymentService.updateToReceivedInCash(LoggedCustomer.CUSTOMER.id, id)

            redirect(action: "show", id: id)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.pay")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def show() {
        try {
            return [payment: paymentService.find(LoggedCustomer.CUSTOMER.id, params.long("id"))]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.notFound")], messageType: "error"]
            redirect(action: "index")
        }
    }

}
