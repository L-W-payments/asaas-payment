package com.miniasaaslw.repository.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class PayerRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payer> query(Map search) {
        DetachedCriteria<Payer> query = Payer.where(defaultQuery(search))

        query = query.where {
            if (search.containsKey("customerId")) {
                eq('customer.id', Long.valueOf(search.customerId))
            }

            if (search.containsKey("name")){
                like('name', search.name + "%")
            }
        }

        return query
    }
}
