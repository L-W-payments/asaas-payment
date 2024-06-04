package com.miniasaaslw.service.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.CepUtils
import com.miniasaaslw.utils.CpfCnpjUtils
import com.miniasaaslw.utils.EmailUtils
import com.miniasaaslw.utils.MessageUtils
import com.miniasaaslw.utils.NameUtils
import com.miniasaaslw.utils.PhoneUtils
import com.miniasaaslw.utils.StateUtils

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class PayerService {

    public List<Payer> list(Integer max, Integer offset, Map search) {
        return PayerRepository.query(search).list(max: max, offset: offset)
    }

    public Payer find(Long id) {
        Payer payer = PayerRepository.query([id: id]).get()

        if (!payer) throw new RuntimeException(MessageUtils.getMessage("payer.errors.notFound"))

        return payer
    }

    public void delete(Long id) {
        Payer payer = find(id)

        if (payer.deleted) {
            throw new RuntimeException(MessageUtils.getMessage("payer.errors.delete.unknown"))
        }

        payer.deleted = true

        payer.save(failOnError: true)
    }

    public Payer update(Long id, PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = find(id)

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

        if (!CepUtils.validateCep(payerAdapter.cep)) {
            payer.errors.reject("cep", null, MessageUtils.getMessage("general.errors.zipCode.invalid"))
        }

        return payer
    }

    private Payer buildPayerProperties(Payer payer, PayerAdapter payerAdapter, Customer customer) {
        payer.name = payerAdapter.name
        payer.email = payerAdapter.email
        payer.phone = payerAdapter.phone
        payer.cpfCnpj = payerAdapter.cpfCnpj
        payer.personType = payerAdapter.personType
        payer.cep = payerAdapter.cep
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