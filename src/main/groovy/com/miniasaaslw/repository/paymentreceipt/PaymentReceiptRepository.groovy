package com.miniasaaslw.repository.paymentreceipt

import com.miniasaaslw.domain.paymentreceipt.PaymentReceipt
import com.miniasaaslw.repository.Repository
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class PaymentReceiptRepository implements Repository<PaymentReceipt, PaymentReceiptRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }
        }
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "publicId"
        ]
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return PaymentReceipt.createCriteria()
    }
}
