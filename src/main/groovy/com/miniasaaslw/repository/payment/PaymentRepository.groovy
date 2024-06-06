package com.miniasaaslw.repository.payment

import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.repository.Repository
import com.miniasaaslw.entity.enums.payment.PaymentStatus

import grails.compiler.GrailsCompileStatic

import groovy.transform.CompileDynamic

import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class PaymentRepository implements Repository<Payment, PaymentRepository> {

    @CompileDynamic
    @Override
    void buildCriteria() {
        addCriteria {
            if (joinWithCustomer(search)) {
                createAlias("customer", "customer")
            }

            if (joinWithPayer(search)) {
                createAlias("payer", "payer")
            }

            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("payerId")) {
                eq("payer.id", search.payerId)
            }

            if (search.containsKey("paymentStatus")) {
                eq("paymentStatus", PaymentStatus.valueOf(search.paymentStatus.toString()))
            }

            if (search.containsKey("paymentStatus[in]")) {
                inList("paymentStatus", search."paymentStatus[in]")
            }

            if (search.containsKey("dueDate[lt]")) {
                lt("dueDate", search."dueDate[lt]")
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }

            if (search.containsKey("payerName[like]")) {
                like("payer.name", "%" + search."payerName[like]" + "%")
            }
        }
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return Payment.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "customerId",
                "payerId",
                "paymentStatus",
                "paymentStatus[in]",
                "dueDate[lt]",
                "publicId",
                "payerName[like]"
        ]
    }

    private static Boolean joinWithCustomer(Map search) {
        return search.containsKey("customerId")
    }

    private static Boolean joinWithPayer(Map search) {
        return search.containsKey("payerName[like]")
    }

}
