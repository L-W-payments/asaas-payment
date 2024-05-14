package com.miniasaaslw.service

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.utils.adapters.payer.PayerAdapter
import com.miniasaaslw.utils.entity.enums.PersonType
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class PayerService {

    public Payer find(long id) {
        Payer payer = Payer.get(id)

        if (!payer) throw new Exception("Pagador não encontrado")

        return payer
    }

    public Payer save(PayerAdapter payerAdapter) {
        Payer payerValues = validatePayerParams(payerAdapter)

        if (payerValues.hasErrors()) {
            throw new ValidationException("Erro ao validar os parâmetros do pagador", payerValues.errors)
        }

        Payer payer = new Payer(
                name: payerAdapter.name,
                email: payerAdapter.email,
                phone: payerAdapter.phone,
                cpfCnpj: payerAdapter.cpfCnpj,
                personType: payerAdapter.personType,
                cep: payerAdapter.cep,
                number: payerAdapter.number,
                complement: payerAdapter.complement,
                country: payerAdapter.country,
                city: payerAdapter.city,
                state: payerAdapter.state,
                district: payerAdapter.district,
                street: payerAdapter.street
        )

        return payer.save(failOnError: true)
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
}
