package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class PaymentRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payment> query(Map search) {
        DetachedCriteria<Payment> query = Payment.where(defaultQuery(search))

        if (joinWithPayer(search)) {
            query.createAlias("payer", "payer")
        }

        query = query.where {
            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }

            if (search.containsKey("payerName[like]")) {
                like("payer.name", search."payerName[like]" + "%")
            }
        }

        return query
    }

    private static Boolean joinWithPayer(Map search) {
        return search.containsKey("payerName[like]")
    }

}
