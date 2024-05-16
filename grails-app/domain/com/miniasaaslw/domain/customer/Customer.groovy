package com.miniasaaslw.domain.customer

import com.miniasaaslw.entity.BasePerson

class Customer extends BasePerson {

    static namedQueries = {
        findById { Map search ->
            boolean deleted = search.containsKey("deleted") ? Boolean.valueOf(search.deleted as String) : false

            eq("id", Long.valueOf(search.id as String))
            eq("deleted", deleted)
        }
    }

}
