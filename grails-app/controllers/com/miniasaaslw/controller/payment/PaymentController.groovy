package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.domain.paymentreceipt.PaymentReceipt
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.repository.paymentreceipt.PaymentReceiptRepository
import com.miniasaaslw.service.payment.PaymentService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['isAuthenticated()'])
class PaymentController extends BaseController {

    PaymentService paymentService

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

            paymentService.restore(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.restore.unknown")], messageType: "error"]
            render([success: false, alert: MessageUtils.getMessage("payment.errors.restore.unknown")] as JSON)
        }
    }

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payment.errors.delete.unknown")] as JSON)
        }
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(getCurrentCustomer(), params))

            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.save.success")], messageType: "success"]
        } catch (ValidationException validationException) {
            println("VALIDAÇÃO: " + validationException)
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
        } catch (Exception exception) {
            println("ERRO: " + exception)
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.save.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    @Secured(["permitAll"])
    def checkout() {
        try {
            String publicId = params.id

            Payment payment = paymentService.find(publicId)

            PaymentReceipt paymentReceipt = PaymentReceiptRepository.query([paymentId:payment.id]).get()

            return [payment: payment, paymentReceiptId: paymentReceipt?.publicId]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.notFound")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def list() {
        return [paymentList: paymentService.list([customerId: getCurrentCustomerId()], getLimitPerPage(), getOffset())]
    }

    @CompileDynamic
    def loadTableContent() {
        Map search = [customerId: getCurrentCustomerId()]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.payerName) search."payerName[like]" = params.payerName

        List<Payment> paymentList = paymentService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = paymentList.totalCount
        String content = g.render(template: "/payment/templates/tableContent", model: [paymentList: paymentList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }

    @Secured(['permitAll'])
    def updateToReceived() {
        try {
            Long id = params.long("id")
            String publicId = params.publicId

            paymentService.updateToReceived(id)

            redirect(action: "checkout", id: publicId)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.pay")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def updateToReceivedInCash() {
        try {
            Long id = params.long("id")

            paymentService.updateToReceivedInCash(getCurrentCustomerId(), id)

            redirect(action: "show", id: id)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [runtimeException.getMessage()]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.pay")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def show() {
        try {
            return [payment: paymentService.find(getCurrentCustomerId(), params.long("id"))]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("payment.errors.notFound")], messageType: "error"]
            redirect(action: "index")
        }
    }
}
