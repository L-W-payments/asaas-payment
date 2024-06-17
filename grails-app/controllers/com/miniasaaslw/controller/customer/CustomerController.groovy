package com.miniasaaslw.controller.customer

import com.miniasaaslw.adapters.customer.CustomerAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.service.customer.CustomerService

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(["ROLE_ADMIN"])
class CustomerController extends BaseController {

    CustomerService customerService

    @Secured(["permitAll"])
    def index() {
        if (hasMessages()) {
            return [messageInfo: getMessagesObject()]
        }
    }

    @Secured(["permitAll"])
    def save() {
        try {
            customerService.save(new CustomerAdapter(params), params)

            redirect(uri: "/login/auth", params: [registered: true])
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("customer.errors.save.unknown", MessageType.ERROR)

            redirect(action: "index")
        }
    }

    @CompileDynamic
    def show() {
        try {
            Customer customer = customerService.find(getCurrentCustomerId())

            if (hasMessages()) {
                return [customer: customer, messageInfo: getMessagesObject()]
            }

            return [customer: customer]
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("customer.errors.search.unknown", MessageType.ERROR)

            redirect(uri: "/index")
        }
    }

    @CompileDynamic
    def update() {
        try {
            customerService.update(getCurrentUser(), new CustomerAdapter(params))

            addMessageCode("customer.success.update", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("customer.errors.update.unknown", MessageType.ERROR)
        }

        redirect(action: "show")
    }
}