package com.miniasaaslw.repository.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class CustomerRepository implements BaseEntityRepository {

    public static DetachedCriteria<Customer> query(Map search) {
        DetachedCriteria<Customer> query = Customer.where(defaultQuery(search))

        return query
    }
}
