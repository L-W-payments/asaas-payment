package com.miniasaaslw.service

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.utils.adapters.payer.PayerAdapter
import com.miniasaaslw.utils.entity.enums.PersonType
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class PayerService {

    public List<Payer> list() {
        return Payer.notDeleted.list()
    }

    public Payer find(long id) {
        Payer payer = Payer.findById([id: id]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        return payer
    }

    public void delete(long id) {
        Payer payer = find(id)

        if (payer.deleted) {
            throw new RuntimeException("O pagador já foi deletado")
        }

        payer.deleted = true

        payer.save(failOnError: true)
    }

    public Payer update(long id, PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = definePayerProperties(find(id), payerAdapter)
        payer.save(failOnError: true)

        return payer
    }

    public Payer save(PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = definePayerProperties(new Payer(), payerAdapter)
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

        if (payerAdapter.personType != PersonType.NATURAL && payerAdapter.personType != PersonType.LEGAL) {
            payer.errors.reject("personType", null, "O tipo de pessoa do pagador não é válido")
        }

        if (!payerAdapter.cep) {
            payer.errors.reject("cep", null, "O CEP do pagador é obrigatório")
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

        return payer
    }

    private Payer definePayerProperties(Payer payer, PayerAdapter payerAdapter) {
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

        return payer
    }
}