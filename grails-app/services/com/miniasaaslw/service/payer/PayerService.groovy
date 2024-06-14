package com.miniasaaslw.service.payer

import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.exception.BusinessException
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.repository.payment.PaymentRepository
import com.miniasaaslw.utils.*

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class PayerService {

    public List<Payer> list(Integer max, Integer offset, Map search) {
        return PayerRepository.query(search).list(max: max, offset: offset)
    }

    public Payer find(Long customerId, Long id) {
        Payer payer = PayerRepository.query([customerId: customerId, id: id]).get()

        if (!payer) throw new BusinessException(MessageUtils.getMessage("payer.errors.notFound"))

        return payer
    }

    public void restore(Long customerId, Long id) {
        Payer payer = PayerRepository.query([customerId: customerId, id: id, includeDeleted: true]).get()

        if (!payer) throw new BusinessException(MessageUtils.getMessage("payer.errors.notFound"))

        if (!payer.deleted) throw new BusinessException(MessageUtils.getMessage("payer.errors.restore.notDeleted"))

        payer.deleted = false

        payer.save(failOnError: true)
    }

    public void delete(Long customerId, Long id) {
        Payer payer = find(customerId, id)

        if (payer.deleted) throw new BusinessException(MessageUtils.getMessage("payer.errors.delete.unknown"))

        Boolean hasActivePayments = PaymentRepository.query([payerId: id, "paymentStatus[in]": [PaymentStatus.PENDING, PaymentStatus.OVERDUE]]).exists()

        if (hasActivePayments) throw new BusinessException(MessageUtils.getMessage("payer.errors.delete.pendingPayments"))

        payer.deleted = true

        payer.save(failOnError: true)
    }

    public Payer update(Long id, PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = find(payerAdapter.customer.id, id)

        payer = buildPayerProperties(payer, payerAdapter, payer.customer)
        payer.save(failOnError: true)

        return payer
    }

    public Payer save(PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = buildPayerProperties(new Payer(), payerAdapter, payerAdapter.customer)
        payer.save(failOnError: true)

        return payer
    }

    private Payer validatePayerParams(PayerAdapter payerAdapter) {
        Payer payer = new Payer()

        if (!payerAdapter.name) {
            payer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.required"))
        }

        if (!payerAdapter.email) {
            payer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.required"))
        }

        if (!payerAdapter.phone) {
            payer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.required"))
        }

        if (!payerAdapter.cpfCnpj) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.required"))
        }

        if (!payerAdapter.personType) {
            payer.errors.reject("personType", null, MessageUtils.getMessage("general.errors.personType.required"))
        }

        if (!payerAdapter.number) {
            payer.errors.reject("number", null, MessageUtils.getMessage("general.errors.number.required"))
        }

        if (!payerAdapter.country) {
            payer.errors.reject("country", null, MessageUtils.getMessage("general.errors.country.required"))
        }

        if (!payerAdapter.city) {
            payer.errors.reject("city", null, MessageUtils.getMessage("general.errors.city.required"))
        }

        if (!payerAdapter.state) {
            payer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.required"))
        }

        if (!payerAdapter.district) {
            payer.errors.reject("district", null, MessageUtils.getMessage("general.errors.district.required"))
        }

        if (!payerAdapter.street) {
            payer.errors.reject("street", null, MessageUtils.getMessage("general.errors.street.required"))
        }

        if (!payerAdapter.customer) {
            payer.errors.reject("customer", null, MessageUtils.getMessage("general.errors.customer.invalid"))
        }

        if (!NameUtils.validateName(payerAdapter.name)) {
            payer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.invalid"))
        }

        if (!EmailUtils.validateEmail(payerAdapter.email)) {
            payer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.invalid"))
        }

        if (!PhoneUtils.validatePhone(payerAdapter.phone)) {
            payer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.invalid"))
        }

        if (payerAdapter.personType == PersonType.LEGAL && !CpfCnpjUtils.validateCnpj(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.invalid"))
        }

        if (payerAdapter.personType == PersonType.NATURAL && !CpfCnpjUtils.validateCpf(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.invalid"))
        }

        if (!StateUtils.validateState(payerAdapter.state)) {
            payer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.invalid"))
        }

        if (!PostalCodeUtils.validatePostalCode(payerAdapter.postalCode)) {
            payer.errors.reject("postalCode", null, MessageUtils.getMessage("general.errors.postalCode.invalid"))
        }

        return payer
    }

    private Payer buildPayerProperties(Payer payer, PayerAdapter payerAdapter, Customer customer) {
        payer.name = payerAdapter.name
        payer.email = payerAdapter.email
        payer.phone = payerAdapter.phone
        payer.cpfCnpj = payerAdapter.cpfCnpj
        payer.personType = payerAdapter.personType
        payer.postalCode = payerAdapter.postalCode
        payer.number = payerAdapter.number
        payer.complement = payerAdapter.complement
        payer.country = payerAdapter.country
        payer.city = payerAdapter.city
        payer.state = payerAdapter.state
        payer.district = payerAdapter.district
        payer.street = payerAdapter.street
        payer.customer = customer

        return payer
    }
}