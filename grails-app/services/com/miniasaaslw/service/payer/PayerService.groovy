package com.miniasaaslw.service.payer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.adapters.payer.PayerAdapter
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.repository.payer.PayerRepository
import com.miniasaaslw.utils.CepUtils
import com.miniasaaslw.utils.CpfCnpjUtils
import com.miniasaaslw.utils.EmailUtils
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

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        return payer
    }

    public void delete(Long id) {
        Payer payer = find(id)

        if (payer.deleted) {
            throw new RuntimeException("O pagador já foi deletado")
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
            payer.errors.reject("name", null, "O nome do pagador é obrigatório")
        }

        if (!payerAdapter.email) {
            payer.errors.reject("email", null, "O e-mail do pagador é obrigatório")
        }

        if (!payerAdapter.phone) {
            payer.errors.reject("phone", null, "O telefone do pagador é obrigatório")
        }

        if (!payerAdapter.cpfCnpj) {
            payer.errors.reject("cpfCnpj", null, "O CPF/CNPJ do pagador é obrigatório")
        }

        if (!payerAdapter.personType) {
            payer.errors.reject("personType", null, "O tipo de pessoa do pagador é obrigatório")
        }

        if (!payerAdapter.number) {
            payer.errors.reject("number", null, "O número do pagador é obrigatório")
        }

        if (!payerAdapter.country) {
            payer.errors.reject("country", null, "O país do pagador é obrigatório")
        }

        if (!payerAdapter.city) {
            payer.errors.reject("city", null, "A cidade do pagador é obrigatória")
        }

        if (!payerAdapter.state) {
            payer.errors.reject("state", null, "O estado do pagador é obrigatório")
        }

        if (!payerAdapter.district) {
            payer.errors.reject("district", null, "O bairro do pagador é obrigatório")
        }

        if (!payerAdapter.street) {
            payer.errors.reject("street", null, "A rua do pagador é obrigatória")
        }

        if(!payerAdapter.customer){
            payer.errors.reject("customer", null, "Cliente Inválido")
        }
      
        if (!NameUtils.validateName(payerAdapter.name)) {
            payer.errors.reject("name", null, "O nome não é válido!")
        }

        if (!EmailUtils.validateEmail(payerAdapter.email)) {
            payer.errors.reject("email", null, "O e-mail não é válido!")
        }

        if (!PhoneUtils.validatePhone(payerAdapter.phone)) {
            payer.errors.reject("phone", null, "O telefone não é válido!")
        }

        if (payerAdapter.personType == PersonType.LEGAL && !CpfCnpjUtils.validateCnpj(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, "O CNPJ não é válido!")
        }

        if (payerAdapter.personType == PersonType.NATURAL && !CpfCnpjUtils.validateCpf(payerAdapter.cpfCnpj)) {
            payer.errors.reject("cpfCnpj", null, "O CPF não é válido!")
        }

        if (!StateUtils.validateState(payerAdapter.state)) {
            payer.errors.reject("state", null, "O estado não é válido!")
        }

        if (payerAdapter.cep != null && CepUtils.validateCep(payerAdapter.cep)) {
            payer.errors.reject("cep", null, "O CEP não é válido!")
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