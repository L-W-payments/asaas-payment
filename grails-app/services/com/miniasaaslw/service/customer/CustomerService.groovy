package com.miniasaaslw.service.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.utils.CepUtils
import com.miniasaaslw.utils.CpfCnpjUtils
import com.miniasaaslw.utils.EmailUtils
import com.miniasaaslw.utils.NameUtils
import com.miniasaaslw.utils.PhoneUtils
import com.miniasaaslw.utils.StateUtils
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    public Customer save(CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException("Erro ao salvar sua conta", customer.errors)
        }

        customer = buildCustomerProperties(customerAdapter, new Customer())

        return customer.save(failOnError: true)
    }

    public Customer update(long id, CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException("Erro ao salvar sua conta", customer.errors)
        }

        customer = buildCustomerProperties(customerAdapter, this.find(id))

        customer.save(failOnError: true)
        return customer
    }

    public void delete(Long id) {
        Customer customer = find(id)

        customer.deleted = true
        customer.save(failOnError: true)
    }

    public Customer find(Long id) {
        Customer customer = CustomerRepository.query([id: id]).get()

        if (!customer) {
            throw new RuntimeException("Cliente não encontrado")
        }

        return customer
    }

    private Customer validateCustomer(CustomerAdapter customerAdapter) {
        Customer customer = new Customer()

        if (!customerAdapter.name) {
            customer.errors.reject("name", null, "Nome é obrigatório!")
        }

        if (!customerAdapter.email) {
            customer.errors.reject("email", null, "Email é obrigatório!")
        }

        if (!customerAdapter.phone) {
            customer.errors.reject("phone", null, "Telefone é obrigatório!")
        }

        if (!customerAdapter.cpfCnpj) {
            customer.errors.reject("cpfCnpj", null, "CPF/CNPJ é obrigatório!")
        }

        if (!customerAdapter.personType) {
            customer.errors.reject("personType", null, "Tipo de pessoa é obrigatório!")
        }

        if (!customerAdapter.number) {
            customer.errors.reject("number", null, "Número é obrigatório!")
        }

        if (!customerAdapter.country) {
            customer.errors.reject("country", null, "País é obrigatório!")
        }

        if (!customerAdapter.city) {
            customer.errors.reject("city", null, "Cidade é obrigatória!")
        }

        if (!customerAdapter.state) {
            customer.errors.reject("state", null, "Estado é obrigatório!")
        }

        if (!customerAdapter.district) {
            customer.errors.reject("district", null, "Bairro é obrigatório!")
        }

        if (!customerAdapter.street) {
            customer.errors.reject("street", null, "Rua é obrigatória!")
        }

        if (!NameUtils.validateName(customerAdapter.name)) {
            customer.errors.reject("name", null, "Nome inválido!")
        }

        if (!EmailUtils.validateEmail(customerAdapter.email)) {
            customer.errors.reject("email", null, "Email inválido!")
        }

        if (!PhoneUtils.validatePhone(customerAdapter.phone)) {
            customer.errors.reject("phone", null, "Telefone inválido!")
        }

        if ((customerAdapter.personType == PersonType.LEGAL) && (!CpfCnpjUtils.validateCnpj(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, "CNPJ inválido!")
        }

        if ((customerAdapter.personType == PersonType.NATURAL) && (!CpfCnpjUtils.validateCpf(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, "CPF inválido!")
        }

        if (!StateUtils.isStateValid(customerAdapter.state)) {
            customer.errors.reject("state", null, "Estado inválido!")
        }

        if (customerAdapter.cep != null && !CepUtils.validateCep(customerAdapter.cep)) {
            customer.errors.reject("cep", null, "CEP inválido!")
        }

        return customer
    }

    private Customer buildCustomerProperties(CustomerAdapter customerAdapter, Customer customer) {
        customer.name = customerAdapter.name
        customer.email = customerAdapter.email
        customer.phone = customerAdapter.phone
        customer.cpfCnpj = customerAdapter.cpfCnpj
        customer.personType = customerAdapter.personType
        customer.cep = customerAdapter.cep
        customer.number = customerAdapter.number
        customer.complement = customerAdapter.complement
        customer.country = customerAdapter.country
        customer.city = customerAdapter.city
        customer.state = customerAdapter.state
        customer.district = customerAdapter.district
        customer.street = customerAdapter.street
        return customer
    }

}
