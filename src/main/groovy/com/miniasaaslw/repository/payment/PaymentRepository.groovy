package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class PaymentRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payment> query(Map search) {
        DetachedCriteria<Payment> query = Payment.where(defaultQuery(search))

        if (joinWithCustomer(search)) {
            query.createAlias("customer", "customer")
        }

        query = query.where {
            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }
        }

        return query
    }

    private static Boolean joinWithCustomer(Map search) {
        return search.containsKey("customerId")
    }
}
