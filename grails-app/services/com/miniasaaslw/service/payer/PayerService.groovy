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

    public List<Payer> list() {
        return PayerRepository.query([:]).list()
    }

    public Payer find(Long id) {
        Payer payer = PayerRepository.query([id: id]).get()

        if (!payer) throw new RuntimeException(MessageUtils.getMessage("general.errors.notFound", ["Pagador"]))

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
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), payerValues.errors)
        }

        Payer payer = find(id)

        payer = buildPayerProperties(payer, payerAdapter, payer.customer)
        payer.save(failOnError: true)

        return payer
    }

    public Payer save(PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), payerValues.errors)
        }

        Payer payer = buildPayerProperties(new Payer(), payerAdapter, payerAdapter.customer)
        payer.save(failOnError: true)

        return payer
    }

    private Payer validatePayerParams(PayerAdapter payerAdapter) {
        Payer payer = new Payer()

        if (!payerAdapter.name) {
            payer.errors.reject("name", null, MessageUtils.getMessage('general.errors.required', ['O nome do pagador']))
        }

        if (!payerAdapter.email) {
            payer.errors.reject("email", null, MessageUtils.getMessage('general.errors.required', ['O e-mail do pagador']))
        }

        if (!payerAdapter.phone) {
            payer.errors.reject("phone", null, MessageUtils.getMessage('general.errors.required', ['O número de celular do pagador']))
        }

        if (!payerAdapter.cpfCnpj) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.required', ['O CPF/CNPJ do pagador']))
        }

        if (!payerAdapter.personType) {
            payer.errors.reject("personType", null, MessageUtils.getMessage('general.errors.required', ['O CPF/CNPJ do pagador']))
        }

        if (!payerAdapter.number) {
            payer.errors.reject("number", null, MessageUtils.getMessage('general.errors.required', ['O número da residência do pagador']))
        }

        if (!payerAdapter.country) {
            payer.errors.reject("country", null, MessageUtils.getMessage('general.errors.required', ['O país do pagador']))
        }

        if (!payerAdapter.city) {
            payer.errors.reject("city", null, MessageUtils.getMessage('general.errors.required', ['A cidade do pagador']))
        }

        if (!payerAdapter.state) {
            payer.errors.reject("state", null, MessageUtils.getMessage('general.errors.required', ['O estado']))
        }

        if (!payerAdapter.district) {
            payer.errors.reject("district", null, MessageUtils.getMessage('general.errors.required', ['O bairro do pagador']))
        }

        if (!payerAdapter.street) {
            payer.errors.reject("street", null, MessageUtils.getMessage('general.errors.required', ['A rua do pagador']))
        }

        if (!payerAdapter.customer) {
            payer.errors.reject("customer", null, MessageUtils.getMessage('general.errors.invalid', ['O cliente responsável pelo pagador']))
        }

        if (!NameUtils.validateName(payerAdapter.name)) {
            payer.errors.reject("name", null, MessageUtils.getMessage('general.errors.invalid', ['O nome do pagador']))
        }

        if (!EmailUtils.validateEmail(payerAdapter.email)) {
            payer.errors.reject("email", null, MessageUtils.getMessage('general.errors.invalid', ['O e-mail do pagador']))
        }

        if (!PhoneUtils.validatePhone(payerAdapter.phone)) {
            payer.errors.reject("phone", null, MessageUtils.getMessage('general.errors.invalid', ['O número de celular do pagador']))
        }

        if (payerAdapter.personType == PersonType.LEGAL && !CpfCnpjUtils.validateCnpj(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.invalid', ['O CNPJ do pagador']))
        }

        if (payerAdapter.personType == PersonType.NATURAL && !CpfCnpjUtils.validateCpf(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.invalid', ['O CPF do pagador']))
        }

        if (!StateUtils.validateState(payerAdapter.state)) {
            payer.errors.reject("state", null, MessageUtils.getMessage('general.errors.invalid', ['O estado do pagador']))
        }

        if (payerAdapter.cep != null && CepUtils.validateCep(payerAdapter.cep)) {
            payer.errors.reject("cep", null, MessageUtils.getMessage('general.errors.invalid', ['O cep do pagador']))
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