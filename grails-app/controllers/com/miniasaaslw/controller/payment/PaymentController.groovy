package com.miniasaaslw.controller.payment

import com.miniasaaslw.utils.LoggedCustomer

class PaymentController {

    def paymentService

    def index() {
        def errors = flash.errors

        if (errors) {
            return [errors: errors]
        }
    }

    def delete() {
        Long id = params.long("id")

        try {
            paymentService.delete(LoggedCustomer.CUSTOMER, id)
        } catch (Exception exception) {
            flash.errors = [message(code: 'payment.errors.delete.unknown')]
        }

        redirect(uri: "/payment")
    }

    def show() {
        Long id = params.long("id")

        try {
            return [payment: paymentService.find(id)]
        } catch (Exception exception) {
            flash.errors = [message(code: 'general.errors.notFound', args: ['Pagador'])]
            redirect(uri: "/payment")
        }
    }
}
