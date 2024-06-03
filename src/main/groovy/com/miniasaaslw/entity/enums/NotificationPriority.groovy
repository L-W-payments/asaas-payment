package com.miniasaaslw.entity.enums

enum NotificationPriority {

    LOW,
    MEDIUM,
    HIGH

    public Boolean isHigh() {
        return this == HIGH
    }

    public Boolean isMedium() {
        return this == MEDIUM
    }

    public Boolean isLow() {
        return this == LOW
    }
}