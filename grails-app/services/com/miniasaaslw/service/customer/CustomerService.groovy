package com.miniasaaslw.service.customer

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.exception.GenericException
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.PostalCodeUtils
import com.miniasaaslw.utils.CpfCnpjUtils
import com.miniasaaslw.utils.EmailUtils
import com.miniasaaslw.utils.MessageUtils
import com.miniasaaslw.utils.NameUtils
import com.miniasaaslw.utils.PhoneUtils
import com.miniasaaslw.utils.StateUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class CustomerService {

    UserService userService

    public Customer save(CustomerAdapter customerAdapter, Map userParams) {
        Customer customer = validateCustomer(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), customer.errors)
        }

        customer = buildCustomerProperties(customerAdapter, new Customer())

        customer.save(failOnError: true)
        userService.save(new UserAdapter(customer, Role.findByAuthority("ROLE_ADMIN"), userParams))

        return customer
    }

    public Customer update(long id, CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter, true)
        if (customer.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), customer.errors)
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

        if (!customer) throw new GenericException(MessageUtils.getMessage("customer.errors.notFound"))

        return customer
    }

    private Customer validateCustomer(CustomerAdapter customerAdapter, boolean isUpdate = false) {
        Customer customer = new Customer()

        if (!customerAdapter.name) {
            customer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.required"))
        }

        if (!customerAdapter.email) {
            customer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.required"))
        }

        if (!customerAdapter.phone) {
            customer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.required"))
        }

        if (!customerAdapter.cpfCnpj) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.required"))
        }

        if (!customerAdapter.personType) {
            customer.errors.reject("personType", null, MessageUtils.getMessage("general.errors.personType.required"))
        }

        if (!customerAdapter.number) {
            customer.errors.reject("number", null, MessageUtils.getMessage("general.errors.number.required"))
        }

        if (!customerAdapter.country) {
            customer.errors.reject("country", null, MessageUtils.getMessage("general.errors.country.required"))
        }

        if (!customerAdapter.city) {
            customer.errors.reject("city", null, MessageUtils.getMessage("general.errors.city.required"))
        }

        if (!customerAdapter.state) {
            customer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.required"))
        }

        if (!customerAdapter.district) {
            customer.errors.reject("district", null, MessageUtils.getMessage("general.errors.district.required"))
        }

        if (!customerAdapter.street) {
            customer.errors.reject("street", null, MessageUtils.getMessage("general.errors.street.required"))
        }

        if (!NameUtils.validateName(customerAdapter.name)) {
            customer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.invalid"))
        }

        if (!EmailUtils.validateEmail(customerAdapter.email)) {
            customer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.invalid"))
        }

        if (!PhoneUtils.validatePhone(customerAdapter.phone)) {
            customer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.invalid"))
        }

        if ((customerAdapter.personType == PersonType.LEGAL) && (!CpfCnpjUtils.validateCnpj(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cnpj.invalid"))
        }

        if ((customerAdapter.personType == PersonType.NATURAL) && (!CpfCnpjUtils.validateCpf(customerAdapter.cpfCnpj))) {
            customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cnpj.invalid"))
        }

        if (!StateUtils.validateState(customerAdapter.state)) {
            customer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.invalid"))
        }

        if (!PostalCodeUtils.validatePostalCode(customerAdapter.postalCode)) {
            customer.errors.reject("postalCode", null, MessageUtils.getMessage("general.errors.postalCode.invalid"))
        }

        if (!isUpdate) {
            if (CustomerRepository.query([cpfCnpj: customerAdapter.cpfCnpj]).exists()) {
                customer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.duplicated"))
            }

            if (CustomerRepository.query([email: customerAdapter.email]).exists()) {
                customer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.duplicated"))
            }
        }

        return customer
    }

    private Customer buildCustomerProperties(CustomerAdapter customerAdapter, Customer customer) {
        customer.name = customerAdapter.name
        customer.email = customerAdapter.email
        customer.phone = customerAdapter.phone
        customer.cpfCnpj = customerAdapter.cpfCnpj
        customer.personType = customerAdapter.personType
        customer.postalCode = customerAdapter.postalCode
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
