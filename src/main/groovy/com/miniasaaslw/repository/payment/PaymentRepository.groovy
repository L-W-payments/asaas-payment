package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.entity.enums.payment.PaymentStatus
import com.miniasaaslw.repository.base.BaseEntityRepository

import grails.gorm.DetachedCriteria

class PaymentRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payment> query(Map search) {
        DetachedCriteria<Payment> query = Payment.where(defaultQuery(search))

        query = query.where {

            if (search.containsKey("paymentStatus")) {
                eq("paymentStatus", PaymentStatus.valueOf(search.paymentStatus.toString()))
            }

            if (search.containsKey("dueDate[lt]")) {
                lt("dueDate", search."dueDate[lt]")
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)

            }
        }

        return query
    }
}
