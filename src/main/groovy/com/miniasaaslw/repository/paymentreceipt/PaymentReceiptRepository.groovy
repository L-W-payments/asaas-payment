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

            if (joinWithPayment(search)) {
                createAlias("payment", "payment")
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }

            if (search.containsKey("customerId")) {
                eq("payment.customer.id", search.customerId)
            }

            if (search.containsKey("dateCreated[lt]")) {
                lt("dateCreated", search."dateCreated[lt]")
            }

            if (search.containsKey("dateCreated[gt]")) {
                gt("dateCreated", search."dateCreated[gt]")
            }

            if (search.containsKey("paymentId")) {
                eq("payment.id", search.paymentId)
            }

        }
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "publicId",
                "dateCreated[lt]",
                "dateCreated[gt]",
                "paymentId",
                "customerId"
        ]
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return PaymentReceipt.createCriteria()
    }

    private static Boolean joinWithPayment(Map search) {
        return search.containsKey("customerId")
    }
}
