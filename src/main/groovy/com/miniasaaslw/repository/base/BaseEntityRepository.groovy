package com.miniasaaslw.repository.base

trait BaseEntityRepository {

    public static Closure defaultQuery(Map search) {
        return {
            if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("id")) {
                eq("id", Long.valueOf(search.id))
            }

            if (Boolean.valueOf("search.exists")) {
                projections {
                    property("id")
                }
            } else if (!Boolean.valueOf(search.disableSort)) {
                order(search.sort ?: "id", search.order ?: "desc")
            }
        }
    }
}
