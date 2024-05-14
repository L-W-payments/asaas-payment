package com.miniasaaslw.controller.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.utils.adapters.payer.PayerAdapter

class PayerController {

    def payerService

    def index() {}

    def save() {
        try {
            Payer payer = payerService.save(new PayerAdapter(params))

            println("Pagador salvo com sucesso!")

            redirect(action: 'show', params: [id: payer.id])
        } catch (Exception exception) {
            exception.printStackTrace()
        }

    }

    def show() {
        try {
            long id = params.long("id")
            Payer payer = payerService.find(id)

            println("Pagador encontrado: ${payer.name}")
            return [payer: payer]
        } catch (Exception ignored) {
            println("Pagador não encontrado")
            redirect(uri: "/")
        }
    }
}
