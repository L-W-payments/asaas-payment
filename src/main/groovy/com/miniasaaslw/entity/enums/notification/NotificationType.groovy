package com.miniasaaslw.entity.enums.notification

enum NotificationType {

    INFO,
    WARNING,
    ERROR

    public boolean isInfo() {
        return this == INFO
    }

    public boolean isWarning() {
        return this == WARNING
    }

    public boolean isError() {
        return this == ERROR
    }
}
