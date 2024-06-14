package com.miniasaaslw.entity.enums

enum MessageType {

    SUCCESS,
    ERROR

    public String toString() {
        return name().toLowerCase()
    }
}