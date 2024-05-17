package com.miniasaaslw.repository.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.repository.base.BaseEntityRepository
import grails.gorm.DetachedCriteria

class PayerRepository implements BaseEntityRepository {

    public static DetachedCriteria<Payer> query(Map search) {
        DetachedCriteria<Payer> query = Payer.where(defaultQuery(search))

        return query
    }
}
