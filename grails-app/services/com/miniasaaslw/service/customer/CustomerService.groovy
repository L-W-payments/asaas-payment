package com.miniasaaslw.service.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.repository.customer.CustomerRepository
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
class CustomerService {

    public Customer save(CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage('general.errors.validation'), customer.errors)
        }

        customer = buildCustomerProperties(customerAdapter, new Customer())

        return customer.save(failOnError: true)
    }

    public Customer update(long id, CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage('general.errors.validation'), customer.errors)
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
            throw new RuntimeException(MessageUtils.getMessage('general.errors.notFound', ['Cliente']))
        }

        return customer
    }

    private Customer validateCustomer(CustomerAdapter customerAdapter) {
        Customer customer = new Customer()

        if (!customerAdapter.name) {
            customer.errors.reject("name", null, MessageUtils.getMessage('general.errors.required', ['O nome']))
        }

        if (!customerAdapter.email) {
            customer.errors.reject("email", null, MessageUtils.getMessage('general.errors.required', ['O e-mail']))
        }

        if (!customerAdapter.phone) {
            customer.errors.reject("phone", null, MessageUtils.getMessage('general.errors.required', ['O número de celular']))
        }

        if (!customerAdapter.cpfCnpj) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.required', ['O cpf/cnpj']))
        }

        if (!customerAdapter.personType) {
            customer.errors.reject("personType", null, MessageUtils.getMessage('general.errors.required', ['O tipo de pessoa']))
        }

        if (!customerAdapter.number) {
            customer.errors.reject("number", null, MessageUtils.getMessage('general.errors.required', ['O número da residência']))
        }

        if (!customerAdapter.country) {
            customer.errors.reject("country", null, MessageUtils.getMessage('general.errors.required', ['O país']))
        }

        if (!customerAdapter.city) {
            customer.errors.reject("city", null, MessageUtils.getMessage('general.errors.required', ['A cidade']))
        }

        if (!customerAdapter.state) {
            customer.errors.reject("state", null, MessageUtils.getMessage('general.errors.required', ['O estádo']))
        }

        if (!customerAdapter.district) {
            customer.errors.reject("district", null, MessageUtils.getMessage('general.errors.required', ['O bairro']))
        }

        if (!customerAdapter.street) {
            customer.errors.reject("street", null, MessageUtils.getMessage('general.errors.required', ['A rua']))
        }

        if (!NameUtils.validateName(customerAdapter.name)) {
            customer.errors.reject("name", null, MessageUtils.getMessage('general.errors.invalid', ['O nome']))
        }

        if (!EmailUtils.validateEmail(customerAdapter.email)) {
            customer.errors.reject("email", null, MessageUtils.getMessage('general.errors.invalid', ['O e-mail']))
        }

        if (!PhoneUtils.validatePhone(customerAdapter.phone)) {
            customer.errors.reject("phone", null, MessageUtils.getMessage('general.errors.invalid', ['O telefone']))
        }

        if ((customerAdapter.personType == PersonType.LEGAL) && (!CpfCnpjUtils.validateCnpj(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.invalid', ['O CNPJ']))
        }

        if ((customerAdapter.personType == PersonType.NATURAL) && (!CpfCnpjUtils.validateCpf(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.invalid', ['O CPF']))
        }

        if (!StateUtils.validateState(customerAdapter.state)) {
            customer.errors.reject("state", null, MessageUtils.getMessage('general.errors.invalid', ['O estado']))
        }

        if (customerAdapter.cep != null && !CepUtils.validateCep(customerAdapter.cep)) {
            customer.errors.reject("cep", null, MessageUtils.getMessage('general.errors.invalid', ['O cep']))
        }

        if (CustomerRepository.exists([cpfCnpj: customerAdapter.cpfCnpj])) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage('general.errors.duplicated', ['O CPF/CNPJ']))
        }

        if (CustomerRepository.exists([email: customerAdapter.email])) {
            customer.errors.reject("email", null, MessageUtils.getMessage('general.errors.duplicated', ['O e-mail']))
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
