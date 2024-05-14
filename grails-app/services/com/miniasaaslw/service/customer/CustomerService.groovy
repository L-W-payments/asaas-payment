package com.miniasaaslw.service.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.utils.adapters.customer.CustomerAdapter
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    public Customer save(CustomerAdapter customerAdapter) {

        Customer customer = validateCustomer(customerAdapter)
        if(customer.hasErrors()){
            throw new ValidationException("Erro ao salvar sua conta", customer.errors)
        }

        customer = new Customer(name: customerAdapter.name,
                email: customerAdapter.email,
                phone: customerAdapter.phone,
                cpfCnpj: customerAdapter.cpfCnpj,
                personType: customerAdapter.personType,
                cep: customerAdapter.cep,
                number: customerAdapter.number,
                complement: customerAdapter.complement,
                country: customerAdapter.country,
                city: customerAdapter.city,
                state: customerAdapter.state,
                district: customerAdapter.district,
                street: customerAdapter.street)

        return customer.save(failOnError: true)
    }

    public Customer find(Long id){
        Customer customer = Customer.get(id)

        if(!customer){
            throw new Exception("Cliente não encontrado")
        }
        return customer
    }

    private static Customer validateCustomer(CustomerAdapter customerAdapter){
        Customer customer = new Customer()

        if(!customerAdapter.name){
            customer.errors.reject("name", null, "Nome é obrigatório!")
        }

        if(!customerAdapter.email){
            customer.errors.reject("email", null, "Email é obrigatório!")
        }

        if(!customerAdapter.phone){
            customer.errors.reject("phone", null, "Telefone é obrigatório!")
        }

        if(!customerAdapter.cpfCnpj){
            customer.errors.reject("cpfCnpj", null, "CPF/CNPJ é obrigatório!")
        }

        if(!customerAdapter.personType){
            customer.errors.reject("personType", null, "Tipo de pessoa é obrigatório!")
        }

        if(!customerAdapter.cep){
            customer.errors.reject("cep", null, "CEP é obrigatório!")
        }

        if(!customerAdapter.number){
            customer.errors.reject("number", null, "Número é obrigatório!")
        }

        if(!customerAdapter.country){
            customer.errors.reject("country", null, "País é obrigatório!")
        }

        if(!customerAdapter.city){
            customer.errors.reject("city", null, "Cidade é obrigatória!")
        }

        if(!customerAdapter.state){
            customer.errors.reject("state", null, "Estado é obrigatório!")
        }

        if(!customerAdapter.district){
            customer.errors.reject("district", null, "Bairro é obrigatório!")
        }

        if(!customerAdapter.street){
            customer.errors.reject("street", null, "Rua é obrigatória!")
        }

        return customer
    }


}
