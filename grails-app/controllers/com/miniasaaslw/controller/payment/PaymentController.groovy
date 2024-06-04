package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.LoggedCustomer

import grails.converters.JSON
import grails.validation.ValidationException

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

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER, id)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.delete.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(params))
            flash.messageInfo = [messages: ["Cobrança criada com sucesso"], messageType: "success"]
            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(uri: "/payment")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def checkout() {
        try {
            Long id = params.long("id")

            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [message(code: "payment.errors.delete.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def list() {
        return [paymentList: paymentService.list([:], getLimitPerPage(), getOffset())]
    }

    def loadTableContent() {
        Map search = [:]
        if (params.payerName) search."payerName[like]" = params.payerName

        List<Payment> paymentList = paymentService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = paymentList.totalCount
        String content = g.render(template: "/payment/templates/tableContent", model: [paymentList: paymentList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }

    def fetchDelete() {
        try {
            Long id = params.long("id")

            paymentService.delete(LoggedCustomer.CUSTOMER, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: "Erro ao deletar a cobrança"] as JSON)
        }
    }

    def updateToReceived() {
        try {
            Long publicId = params.long("id")

            paymentService.updateToReceived(publicId)

            redirect(action: "show", id: publicId)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [message(code: "payment.errors.pay")]
            redirect(action: "index")
        }
    }

    def updateToReceivedInCash() {
        try {
            Long id = params.long("id")

            paymentService.updateToReceivedInCash(LoggedCustomer.CUSTOMER, id)

            redirect(action: "show", id: id)
        } catch (RuntimeException runtimeException) {
            flash.errors = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.errors = [message(code: "payment.errors.pay")]
            redirect(action: "index")
        }
    }

    def show() {
        try {
            Long id = params.long("id")
            return [payment: paymentService.find(LoggedCustomer.CUSTOMER, id)]
        } catch (Exception exception) {
            flash.errors = ["Pagamento não encontrado!"]
            redirect(uri: "/payment")
        }
    }

}
