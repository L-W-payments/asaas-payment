package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.base.BaseEntityRepository
import com.miniasaaslw.entity.enums.payment.PaymentStatus

import grails.gorm.DetachedCriteria

class PaymentRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payment> query(Map search) {
        DetachedCriteria<Payment> query = Payment.where(defaultQuery(search))

        if (joinWithCustomer(search)) {
            query.createAlias("customer", "customer")
        }

        if (joinWithPayer(search)) {
            query.createAlias("payer", "payer")
        }

        query = query.where {

            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("paymentStatus")) {
                eq("paymentStatus", PaymentStatus.valueOf(search.paymentStatus.toString()))
            }

            if (search.containsKey("dueDate[lt]")) {
                lt("dueDate", search."dueDate[lt]")
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }

            if (search.containsKey("payerName[like]")) {
                like("payer.name", search."payerName[like]" + "%")
            }
        }

        return query
    }

    private static Boolean joinWithCustomer(Map search) {
        return search.containsKey("customerId")
    }

    private static Boolean joinWithPayer(Map search) {
        return search.containsKey("payerName[like]")
    }

}
