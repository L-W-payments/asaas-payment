package com.miniasaaslw.service.customer

import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.entity.enums.PersonType
import com.miniasaaslw.exception.BusinessException
import com.miniasaaslw.repository.customer.CustomerRepository
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.*

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class CustomerService {

    UserService userService

    public Customer save(CustomerAdapter customerAdapter, Map userParams) {
        Customer customer = validateCustomer(customerAdapter)

        if (customer.hasErrors()) throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), customer.errors)

        customer = buildCustomerProperties(new Customer(), customerAdapter)

        customer.save(failOnError: true)
        userService.save(new UserAdapter(customer, Role.findByAuthority("ROLE_ADMIN"), userParams))

        return customer
    }

    public Customer update(User user, CustomerAdapter customerAdapter) {
        Customer customer = validateCustomer(customerAdapter, true)

        if (customer.hasErrors()) throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), customer.errors)

        customer = this.find(user.customer.id)

        Boolean isSameEmail = customer.email == customerAdapter.email

        customer = buildCustomerProperties(customer, customerAdapter)

        customer.save(failOnError: true)

        if (!isSameEmail) {
            userService.updateEmail(new UserAdapter(customer, [email: customerAdapter.email, id: user.id]))
        }

        return customer
    }

    public void delete(Long id) {
        Customer customer = find(id)

        customer.deleted = true
        customer.save(failOnError: true)
    }

    public Customer find(Long id) {
        Customer customer = CustomerRepository.query([id: id]).get()

        if (!customer) throw new BusinessException(MessageUtils.getMessage("customer.errors.notFound"))

        return customer
    }

    private Customer validateCustomer(CustomerAdapter customerAdapter, boolean isUpdate = false) {
        Customer validationCustomer = new Customer()

        if (!customerAdapter.name) {
            validationCustomer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.required"))
        }

        if (!customerAdapter.email) {
            validationCustomer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.required"))
        }

        if (!customerAdapter.phone) {
            validationCustomer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.required"))
        }

        if (!customerAdapter.cpfCnpj) {
            validationCustomer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.required"))
        }

        if (!customerAdapter.personType) {
            validationCustomer.errors.reject("personType", null, MessageUtils.getMessage("general.errors.personType.required"))
        }

        if (!customerAdapter.number) {
            validationCustomer.errors.reject("number", null, MessageUtils.getMessage("general.errors.number.required"))
        }

        if (!customerAdapter.country) {
            validationCustomer.errors.reject("country", null, MessageUtils.getMessage("general.errors.country.required"))
        }

        if (!customerAdapter.city) {
            validationCustomer.errors.reject("city", null, MessageUtils.getMessage("general.errors.city.required"))
        }

        if (!customerAdapter.state) {
            validationCustomer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.required"))
        }

        if (!customerAdapter.district) {
            validationCustomer.errors.reject("district", null, MessageUtils.getMessage("general.errors.district.required"))
        }

        if (!customerAdapter.street) {
            validationCustomer.errors.reject("street", null, MessageUtils.getMessage("general.errors.street.required"))
        }

        if (!NameUtils.validateName(customerAdapter.name)) {
            validationCustomer.errors.reject("name", null, MessageUtils.getMessage("general.errors.name.invalid"))
        }

        if (!EmailUtils.validateEmail(customerAdapter.email)) {
            validationCustomer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.invalid"))
        }

        if (!PhoneUtils.validatePhone(customerAdapter.phone)) {
            validationCustomer.errors.reject("phone", null, MessageUtils.getMessage("general.errors.phone.invalid"))
        }

        if ((customerAdapter.personType == PersonType.LEGAL) && (!CpfCnpjUtils.validateCnpj(customerAdapter.cpfCnpj))) {
            validationCustomer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cnpj.invalid"))
        }

        if ((customerAdapter.personType == PersonType.NATURAL) && (!CpfCnpjUtils.validateCpf(customerAdapter.cpfCnpj))) {
            validationCustomer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cnpj.invalid"))
        }

        if (!StateUtils.validateState(customerAdapter.state)) {
            validationCustomer.errors.reject("state", null, MessageUtils.getMessage("general.errors.state.invalid"))
        }

        if (!PostalCodeUtils.validatePostalCode(customerAdapter.postalCode)) {
            validationCustomer.errors.reject("postalCode", null, MessageUtils.getMessage("general.errors.postalCode.invalid"))
        }

        if (!isUpdate) {
            if (CustomerRepository.query([cpfCnpj: customerAdapter.cpfCnpj]).exists()) {
                validationCustomer.errors.reject("cpfCnpj", null, MessageUtils.getMessage("general.errors.cpfCnpj.duplicated"))
            }

            if (CustomerRepository.query([email: customerAdapter.email]).exists()) {
                validationCustomer.errors.reject("email", null, MessageUtils.getMessage("general.errors.email.duplicated"))
            }
        }

        return validationCustomer
    }

    private Customer buildCustomerProperties(Customer customer, CustomerAdapter customerAdapter) {
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
