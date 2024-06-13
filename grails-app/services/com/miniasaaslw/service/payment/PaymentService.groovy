package com.miniasaaslw.service.payment

import com.miniasaaslw.adapters.emailnotification.EmailNotificationAdapter
import com.miniasaaslw.adapters.notification.NotificationAdapter
import com.miniasaaslw.adapters.payment.PaymentAdapter
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.repository.payment.PaymentRepository
import com.miniasaaslw.utils.DateUtils
import com.miniasaaslw.utils.LoggedCustomer
import com.miniasaaslw.utils.MessageUtils

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

import groovy.time.TimeCategory

@Transactional
class PaymentService {

    def emailNotificationService

    def notificationService

    def paymentReceiptService

    public Payment save(PaymentAdapter paymentAdapter) {
        Payment paymentData = validatePayment(paymentAdapter)

        if (paymentData.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), paymentData.errors)
        }

        Payment payment = buildPaymentProperties(new Payment(), paymentAdapter)

        payment.save(failOnError: true)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentCreated(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentCreated(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentCreated(payment))

        return payment
    }

    public Payment find(String publicId) {
        Payment payment = PaymentRepository.query([publicId: publicId]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        return payment
    }

    public Payment find(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([id: id, customerId: customerId]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        return payment
    }

    public void restore(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([customerId: customerId, id: id, includeDeleted: true]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        if (!payment.deleted) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notDeleted"))

        payment.deleted = false
        payment.save(failOnError: true)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentRestored(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentRestored(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentRestored(payment))

    }

    public void delete(Long customerId, Long paymentId) {
        Payment payment = find(customerId, paymentId)

        if (payment.deleted) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notDeleted"))

        payment.deleted = true
        payment.save(failOnError: true)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentDeleted(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentDeleted(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentDeleted(payment))

    }

    public List<Payment> list(Map search, Integer max, Integer offset) {
        return PaymentRepository.query(search).list(max: max, offset: offset)
    }

    public void updateToReceived(Long id) {
        Payment payment = PaymentRepository.query([id: id]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        Payment validatedPayment = validateUpdateToReceived(payment)
        if (validatedPayment.hasErrors()) throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), validatedPayment.errors)

        payment.paymentStatus = PaymentStatus.RECEIVED
        payment.save(failOnError: true)

        paymentReceiptService.save(payment)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentReceived(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentPaid(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentPaid(payment))

    }

    public void updateToReceivedInCash(Long customerId, Long paymentId) {
        Payment payment = find(customerId, paymentId)

        Payment validatedPayment = validateUpdateToReceivedInCash(payment)
        if (validatedPayment.hasErrors()) throw new ValidationException(MessageUtils.getMessage("payment.errors.update.unknown"), validatedPayment.errors)

        payment.paymentStatus = PaymentStatus.RECEIVED_IN_CASH
        payment.save(failOnError: true)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentReceived(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentPaidInCash(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentPaidInCash(payment))
    }

    public void updateToOverdue(Long id) {
        Payment payment = PaymentRepository.query([id: id]).get()

        if (!payment) throw new RuntimeException(MessageUtils.getMessage("payment.errors.notFound"))

        if (!payment.paymentStatus.isPending()) throw new RuntimeException(MessageUtils.getMessage("payment.errors.status.update.pending"))

        payment.paymentStatus = PaymentStatus.OVERDUE
        payment.save(failOnError: true)

        notificationService.save(LoggedCustomer.CUSTOMER, NotificationAdapter.buildPaymentOverdue(payment))

        emailNotificationService.save(EmailNotificationAdapter.buildCustomerEmailPaymentOverdue(payment))
        emailNotificationService.save(EmailNotificationAdapter.buildPayerEmailPaymentOverdue(payment))
    }

    public void processOverduePayment() {
        List<Long> overduePendingPaymentsIdList = PaymentRepository.query([
                paymentStatus: PaymentStatus.PENDING,
                "dueDate[lt]": new Date()
        ]).column("id").list(max: 500) as List<Long>

        for (Long paymentId : overduePendingPaymentsIdList) {
            Payment.withNewTransaction { status ->
                try {
                    updateToOverdue(paymentId)
                } catch (Exception exception) {
                    log.error("updatePendingPaymentStatus >> Erro ao atualizar status da cobrança de id: [${paymentId}] [Mensagem de erro]: ${exception.message}")
                    status.setRollbackOnly()
                }
            }
        }
    }

    private Payment buildPaymentProperties(Payment payment, PaymentAdapter paymentAdapter) {
        payment.publicId = payment.publicId ?: UUID.randomUUID().toString().toUpperCase()
        payment.customer = paymentAdapter.customer
        payment.payer = paymentAdapter.payer
        payment.value = paymentAdapter.value
        payment.dueDate = paymentAdapter.dueDate
        payment.paymentStatus = PaymentStatus.PENDING
        payment.billingType = paymentAdapter.billingType
        payment.description = paymentAdapter.description

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

        if (!paymentAdapter.billingType) {
            payment.errors.reject("billingType", null, MessageUtils.getMessage("payment.errors.billingType.invalid"))
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
            validationPayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.received"))
        }

        if (payment.paymentStatus.isReceivedInCash()) {
            validationPayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.receivedInCash"))
        }

        return validationPayment
    }

    private Payment validateUpdateToReceivedInCash(Payment payment) {
        Payment validationPayment = new Payment()

        if (payment.paymentStatus.isReceived()) {
            validationPayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.received"))
        }

        if (payment.paymentStatus.isReceivedInCash()) {
            validationPayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.receivedInCash"))
        }

        if (payment.paymentStatus.isOverdue()) {
            validationPayment.errors.reject("paymentStatus", null, MessageUtils.getMessage("payment.errors.overdue"))
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
        if (dueDate.before(DateUtils.today())) return false

        Date dateLimit = new Date()

        use(TimeCategory) {
            dateLimit += 6.months
        }

        if (dueDate.after(dateLimit)) return false

        return true
    }
}
