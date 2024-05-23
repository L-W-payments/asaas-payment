package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payment.Payment
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import groovy.time.TimeCategory

@Transactional
class PaymentService {


    public Payment save(PaymentAdapter paymentAdapter) {

        Payment paymentValues = validatePayment(paymentAdapter)

        if (paymentValues.hasErrors()){
            throw new ValidationException("Erro ao validar os parâmetros do pagamento", paymentValues.errors)
        }

        Payment payment = new Payment(
                payer: paymentAdapter.payer,
                customer: paymentAdapter.customer,
                value: paymentAdapter.value,
                dueDate: paymentAdapter.dueDate,
                paymentStatus: paymentAdapter.paymentStatus,
                paymentType: paymentAdapter.paymentType
        )

        payment.save(failOnError: true)

        return payment
    }

    private Payment validatePayment(PaymentAdapter paymentAdapter) {
        Payment payment = new Payment()

        if (!paymentAdapter.customer) {
            payment.errors.reject("Cliente não encontrado")
        }

        if (!paymentAdapter.payer) {
            payment.errors.reject("Pagador não encontrado")
        }

        if (!paymentAdapter.paymentType) {
            payment.errors.reject("O tipo de pagamento é obrigatório")
        }

        if (!paymentAdapter.paymentStatus) {
            payment.errors.reject("O Status do pagamento é obrigatório")
        }

        if (!paymentAdapter.value) {
            payment.errors.reject("O Valor do pagamento é obrigatório")
        }

        if (!paymentAdapter.dueDate) {
            payment.errors.reject("A Data de vencimento é obrigatória")
        }

        if (paymentAdapter.description != null && !validateDescription(paymentAdapter.description)) {
            payment.errors.reject("A descrição do pagamento deve ter no máximo 500 caracteres")
        }

        if (!validateValue(paymentAdapter.value)) {
            payment.errors.reject("O Valor do pagamento deve ser entre 10 e 10.000")
        }

        return payment
    }

    private Boolean validateDescription(String description) {
        if (description.length() > 500) return false

        return true
    }

    private Boolean validateValue(BigDecimal value) {
        if (value < new BigDecimal("10")) return false

        if (value > new BigDecimal("10.000")) return false

        return true
    }
}
