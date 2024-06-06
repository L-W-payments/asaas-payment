package com.miniasaaslw.repository.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.repository.Repository

import grails.compiler.GrailsCompileStatic

import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class CustomerRepository implements Repository<Customer, CustomerRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("cpfCnpj")) {
                eq("cpfCnpj", search.cpfCnpj)
            }

            if (search.containsKey("email")) {
                eq("email", search.email)
            }
        }
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return Customer.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "cpfCnpj",
                "email"
        ]
    }

    public static Boolean exists(Map search) {
        return query(search).get().asBoolean()
    }
}
