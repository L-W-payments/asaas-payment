package com.miniasaaslw.repository.payer

import com.miniasaaslw.domain.payer.Payer
import com.miniasaaslw.repository.Repository
import grails.compiler.GrailsCompileStatic
import groovy.transform.CompileDynamic
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class PayerRepository implements Repository<Payer, PayerRepository> {

    @Override
    @CompileDynamic
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("name[like]")) {
                like("name", "%" + search."name[like]" + "%")
            }
        }
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "customerId",
                "name[like]"
        ]
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return Payer.createCriteria()
    }

}
