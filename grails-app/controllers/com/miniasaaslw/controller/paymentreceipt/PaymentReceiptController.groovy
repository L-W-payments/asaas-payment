package com.miniasaaslw.controller.paymentreceipt

import com.miniasaaslw.service.paymentreceipt.PaymentReceiptService
import com.miniasaaslw.utils.MessageUtils
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured(["permitAll"])
class PaymentReceiptController {

    PaymentReceiptService paymentReceiptService

    def index() {}

    def show() {
        try {
            String publicId = params.id
            throw new Exception("Sla")
            return [receipt: paymentReceiptService.find(publicId)]
        } catch (RuntimeException runtimeException) {
            return [message: MessageUtils.getMessage("paymentReceipt.errors.notFound"), messageType: "notFound"]
        } catch (Exception exception) {
            return [message: MessageUtils.getMessage("paymentReceipt.errors.show.unknown"), messageType: "unknown"]
        }
    }
}
