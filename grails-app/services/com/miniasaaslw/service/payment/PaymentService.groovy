package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import grails.gorm.transactions.Transactional
import com.miniasaaslw.repository.payment.PaymentRepository
import grails.validation.ValidationException
import groovy.time.TimeCategory

@Transactional
class PaymentService {

    public Payment save(PaymentAdapter paymentAdapter) {

        Payment paymentData = validatePayment(paymentAdapter)

        if (paymentData.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros da cobrança", paymentData.errors)
        }

        Payment payment = buildPaymentProperties(new Payment(), paymentAdapter)

        payment.save(failOnError: true)

        return payment
    }

    public Payment find(Long id) {
        Payment payment = PaymentRepository.query([id: id]).get()

        if (!payment) throw new RuntimeException("Pagamento não encontrado!")

        return payment
    }

    public void delete(Customer customer, Long paymentId) {
        Payment payment = PaymentRepository.query([id: paymentId, customer: customer]).get()

        if (!payment) throw new RuntimeException("Pagamento não encontrado!")

        if (payment.deleted) return

        payment.deleted = true
        payment.save(failOnError: true)
    }

    public List<Payment> list(Integer page, Map search){
        return PaymentRepository.query(search).list(max: 10, offset: page * 10)
    }

    private Payment buildPaymentProperties(Payment payment, PaymentAdapter paymentAdapter){
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
            payment.errors.reject("Cliente não encontrado")
        }

        if (!paymentAdapter.payer) {
            payment.errors.reject("Pagador não encontrado")
        }

        if (!paymentAdapter.paymentType) {
            payment.errors.reject("O tipo da cobrança é obrigatório")
        }

        if (!paymentAdapter.value) {
            payment.errors.reject("O Valor da cobrança é obrigatório")
        }

        if (!paymentAdapter.dueDate) {
            payment.errors.reject("A Data de vencimento é obrigatória")
        }

        if (!validateDescription(paymentAdapter.description)) {
            payment.errors.reject("A descrição da cobrança deve ter no máximo 500 caracteres")
        }

        if (!validateValue(paymentAdapter.value)) {
            payment.errors.reject("O Valor da cobrança deve ser entre 10 e 10.000")
        }

        if (!validateDueDate(paymentAdapter.dueDate)) {
            payment.errors.reject("A Data de vencimento deve ser no futuro e no máximo 6 meses")
        }

        return payment
    }

    private Boolean validateDescription(String description) {
        if(!description) return true

        if (description.length() > 500) return false

        return true
    }

    private Boolean validateValue(BigDecimal value) {
        if (value < new BigDecimal("10.00")) return false

        if (value > new BigDecimal("10000.00")) return false

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
