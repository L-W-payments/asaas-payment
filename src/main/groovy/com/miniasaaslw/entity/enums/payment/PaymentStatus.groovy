package com.miniasaaslw.entity.enums.payment

enum PaymentStatus {

    PENDING,
    RECEIVED,
    OVERDUE

    public boolean isReceived() {
        return this == RECEIVED
    }

    public boolean isPending() {
        return this == PENDING
    }

    public boolean isOverdue() {
        return this == OVERDUE
    }
}
