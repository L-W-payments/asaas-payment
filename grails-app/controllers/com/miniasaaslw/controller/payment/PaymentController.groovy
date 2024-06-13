package com.miniasaaslw.controller.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.exception.GenericException
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.service.payment.PaymentService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['isAuthenticated()'])
class PaymentController extends BaseController {

    PaymentService paymentService

    def index() {
        List<Payer> payers = PayerRepository.query([:]).list()

        if (hasMessages()) {
            return [payers: payers, messageInfo: getMessagesObject()]
        }

        return [payers: payers]
    }

    def restore() {
        try {
            Long id = params.long("id")

            paymentService.restore(getCurrentCustomerId(), id)

            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payment.errors.restore.unknown")] as JSON)
        }
    }

    def delete() {
        try {
            Long id = params.long("id")

            paymentService.delete(getCurrentCustomerId(), id)

            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("payment.errors.delete.unknown")] as JSON)
        }
    }

    def save() {
        try {
            paymentService.save(new PaymentAdapter(getCurrentCustomer(), params))

            addMessageCode("payment.save.success", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payment.errors.save.unknown", MessageType.ERROR)
        }

        redirect(action: "index")
    }

    @Secured(["permitAll"])
    def checkout() {
        try {
            String publicId = params.id

            return [payment: paymentService.find(publicId)]
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payment.errors.notFound", MessageType.ERROR)

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
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payment.errors.pay.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }

    def updateToReceivedInCash() {
        try {
            Long id = params.long("id")

            paymentService.updateToReceivedInCash(getCurrentCustomerId(), id)

            addMessageCode("payment.updateToReceivedInCash.success", MessageType.SUCCESS)

            redirect(action: "show", id: id)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payment.errors.pay.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }

    def show() {
        try {
            Payment payment = paymentService.find(getCurrentCustomerId(), params.long("id"))

            if (hasMessages()) {
                return [payment: payment, messageInfo: getMessagesObject()]
            }

            return [payment: payment]
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("payment.errors.find.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }
}
