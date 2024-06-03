package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.payment.PaymentRepository
import com.miniasaaslw.utils.MessageUtils
import com.miniasaaslw.entity.enums.payment.PaymentStatus

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

import groovy.time.TimeCategory

@Transactional
class PaymentService {

    public Payment save(PaymentAdapter paymentAdapter) {
        Payment paymentData = validatePayment(paymentAdapter)

        if (paymentData.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), paymentData.errors)
        }

        Payment payment = buildPaymentProperties(new Payment(), paymentAdapter)

        payment.save(failOnError: true)

        return payment
    }

    public Payment find(Long id) {
        Payment payment = PaymentRepository.query([id: id]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        return payment
    }

    public Payment find(Customer customer, Long id) {
        Payment payment = PaymentRepository.query([id: id, customer: customer]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        return payment
    }

    public void delete(Customer customer, Long paymentId) {
        Payment payment = find(customer, paymentId)

        if (payment.deleted) return

        payment.deleted = true
        payment.save(failOnError: true)
    }

    public void updateToReceived(Long id) {
        Payment payment = PaymentRepository.query([id: id]).get()

        if (!payment) throw new RuntimeException("Cobrança não encontrada!")

        Payment validatedPayment = validateUpdateToReceived(payment)
        if (validatedPayment.hasErrors()) throw new ValidationException("Erro ao validar parâmetros da cobrança", validatedPayment.errors)

        payment.paymentStatus = PaymentStatus.RECEIVED
        payment.save(failOnError: true)
    }

    private Payment buildPaymentProperties(Payment payment, PaymentAdapter paymentAdapter) {
        payment.customer = paymentAdapter.customer
        payment.payer = paymentAdapter.payer
        payment.value = paymentAdapter.value
        payment.dueDate = paymentAdapter.dueDate
        payment.paymentStatus = PaymentStatus.PENDING
        payment.paymentType = paymentAdapter.paymentType

        return payment
    }

    private Payment validatePayment(PaymentAdapter paymentAdapter) {
        Payment payment = new Payment()

        if (!paymentAdapter.customer) {
            payment.errors.reject("customer", null, MessageUtils.getMessage("payment.errors.customer.notFound"))
        }

        if (!paymentAdapter.payer) {
            payment.errors.reject("payer", null, MessageUtils.getMessage("payment.errors.payer.notFound"))
        }

        if (!paymentAdapter.paymentType) {
            payment.errors.reject("paymentType", null, MessageUtils.getMessage("payment.errors.paymentType.invalid"))
        }

        if (!paymentAdapter.value) {
            payment.errors.reject("value", null, MessageUtils.getMessage("payment.errors.value.invalid"))
        }

        if (!paymentAdapter.dueDate) {
            payment.errors.reject("dueDate", null, MessageUtils.getMessage("payment.errors.dueDate.invalid"))
        }

        if (!validateDescription(paymentAdapter.description)) {
            payment.errors.reject("description", null, MessageUtils.getMessage("payment.errors.description.length"))
        }

        if (!validateValue(paymentAdapter.value)) {
            payment.errors.reject("value", null, MessageUtils.getMessage("payment.errors.value.range"))
        }

        if (!validateDueDate(paymentAdapter.dueDate)) {
            payment.errors.reject("dueDate", null, MessageUtils.getMessage("payment.errors.dueDate.range"))
        }

        return payment
    }

    private Payment validateUpdateToReceived(Payment payment) {
        Payment validationPayment = new Payment()

        if (payment.paymentStatus.isReceived()) {
            validationPayment.errors.reject("O pagamento já foi efetuado!")
        }

        if (new Date().after(payment.dueDate)) {
            validationPayment.errors.reject("A data de vencimento já passou!")
        }

        return validationPayment
    }

    private Boolean validateDescription(String description) {
        if (!description) return true

        if (description.length() > 500) return false

        return true
    }

    private Boolean validateValue(BigDecimal value) {
        if (value < Payment.MIN_VALUE) return false

        if (value > Payment.MAX_VALUE) return false

        return true
    }

    private Boolean validateDueDate(Date dueDate) {
        if (dueDate.before(new Date())) return false

        Date dateLimit = new Date()

        use(TimeCategory) {
            dateLimit += 6.months
        }

        if (dueDate.after(dateLimit)) return false

        return true
    }
}
