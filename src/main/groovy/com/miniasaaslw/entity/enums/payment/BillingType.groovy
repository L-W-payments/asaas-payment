package com.miniasaaslw.entity.enums.payment

enum BillingType {

    BANK_SLIP,
    PIX,
    CREDIT_CARD,
    DEBIT_CARD

    public Boolean isBankSlip() {
        return this == BANK_SLIP
    }

    public Boolean isPix() {
        return this == PIX
    }

    public Boolean isCreditCard() {
        return this == CREDIT_CARD
    }

    public Boolean isDebitCard() {
        return this == DEBIT_CARD
    }
}