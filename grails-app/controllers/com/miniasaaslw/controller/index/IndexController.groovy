package com.miniasaaslw.controller.index

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.domain.payment.Payment
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.repository.payment.PaymentRepository
import com.miniasaaslw.repository.paymentreceipt.PaymentReceiptRepository
import com.miniasaaslw.utils.DateUtils
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured(["isAuthenticated()"])
class IndexController {

    def index() {
        Customer loggedCustomer = (getAuthenticatedUser() as User).customer

        List<Payment> paymentReceivedList = getReceivedPaymentList(loggedCustomer.id)
        List<Payment> paymentPendingList = getPaymentList(loggedCustomer.id, "PENDING")
        List<Payment> paymentOverdueList = getPaymentList(loggedCustomer.id, "OVERDUE")
        List<Payment> paymentReceivedInCashList = getPaymentList(loggedCustomer.id, "RECEIVED_IN_CASH")

        def cardData = [
                [
                        title        : "Recebidas",
                        theme        : "success",
                        total        : paymentReceivedList.sum { it.value },
                        totalPayers  : (paymentReceivedList.collect { it.payer } as Set<Payer>).size(),
                        totalPayments: paymentReceivedList.size()
                ],
                [
                        title        : "Recebidas em dinheiro",
                        theme        : "info",
                        total        : paymentReceivedInCashList.sum { it.value },
                        totalPayers  : (paymentReceivedInCashList.collect { it.payer } as Set<Payer>).size(),
                        totalPayments: paymentReceivedInCashList.size()
                ],
                [
                        title        : "Pendentes",
                        theme        : "warning",
                        total        : paymentPendingList.sum { it.value },
                        totalPayers  : (paymentPendingList.collect { it.payer } as Set<Payer>).size(),
                        totalPayments: paymentPendingList.size()
                ],
                [
                        title        : "Vencidas",
                        theme        : "danger",
                        total        : paymentOverdueList.sum { it.value },
                        totalPayers  : (paymentOverdueList.collect { it.payer } as Set<Payer>).size(),
                        totalPayments: paymentOverdueList.size()
                ],
        ]
        return [cardData: cardData]
    }

    private List<Payment> getReceivedPaymentList(Long customerId) {
        return PaymentReceiptRepository.query([customerId       : customerId,
                                               "dateCreated[lt]": DateUtils.lastDayOfCurrentMonth(),
                                               "dateCreated[gt]": DateUtils.firstDayOfCurrentMonth()]).column("payment").list() as List<Payment>
    }

    private List<Payment> getPaymentList(Long customerId, String paymentStatus) {
        return PaymentRepository.query([customerId: customerId,
                                        paymentStatus: paymentStatus,
                                        "dueDate[lt]": DateUtils.lastDayOfCurrentMonth(),
                                        "dueDate[gt]": DateUtils.firstDayOfCurrentMonth()]).list()
    }
}
