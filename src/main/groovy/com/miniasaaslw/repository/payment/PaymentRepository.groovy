package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class PaymentRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payment> query(Map search) {
        DetachedCriteria<Payment> query = Payment.where(defaultQuery(search))

        query = query.where {
            if (search.containsKey("paymentStatus")) {
                eq("paymentStatus", search.paymentStatus)
            }

            if (search.containsKey("dueDate[lt]")) {
                lt("dueDate", search."dueDate[lt]")
            }
        }

        return query
    }
}
