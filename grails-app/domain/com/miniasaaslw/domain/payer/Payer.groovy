package com.miniasaaslw.domain.payer

import com.miniasaaslw.utils.entity.BasePerson

class Payer extends BasePerson {

    static namedQueries = {
        findById { Map search ->
            boolean deleted = search.containsKey("deleted") ? Boolean.valueOf(search.deleted as String) : false

            eq("id", Long.valueOf(search.id as String))
                    .eq("deleted", deleted)
        }

        notDeleted {
            eq("deleted", false)
        }
    }
}
