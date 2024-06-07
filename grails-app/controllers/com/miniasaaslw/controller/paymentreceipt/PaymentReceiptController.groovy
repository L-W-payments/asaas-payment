package com.miniasaaslw.controller.paymentreceipt

class PaymentReceiptController {

    def paymentReceiptService

    def index() {}

    def receipt() {
        try {
            String publicId = params.id

            return [receipt: paymentReceiptService.find(publicId)]
        } catch (Exception exception) {
            redirect(uri: "/")
        }
    }
}
