package com.miniasaaslw.entity.enums.payment

enum PaymentStatus {

    PENDING,
    RECEIVED,
    OVERDUE,
    RECEIVED_IN_CASH

    public boolean isReceived() {
        return this == RECEIVED
    }

    public boolean isPending() {
        return this == PENDING
    }

    public boolean isOverdue() {
        return this == OVERDUE
    }

    public boolean isReceivedInCash() {
        return this == RECEIVED_IN_CASH
    }

}
