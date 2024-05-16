package com.miniasaaslw.domain.payer

import com.miniasaaslw.entity.BasePerson

class Payer extends BasePerson {

    static namedQueries = {
        findById { Map search ->
            Boolean deleted = search.containsKey("deleted") ? Boolean.valueOf(search.deleted as String) : false

            eq("id", Long.valueOf(search.id as String))
                    .eq("deleted", deleted)
        }

        notDeleted {
            eq("deleted", false)
        }
    }
}
