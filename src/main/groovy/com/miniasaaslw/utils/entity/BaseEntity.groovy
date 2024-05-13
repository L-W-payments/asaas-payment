package com.miniasaaslw.utils.entity

abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    boolean deleted = false

    static mapping = {
        tablePerHierarchy = false
    }
}
