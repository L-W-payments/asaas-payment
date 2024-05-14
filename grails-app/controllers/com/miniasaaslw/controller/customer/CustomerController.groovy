package com.miniasaaslw.controller.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.utils.adapters.customer.CustomerAdapter
import grails.validation.ValidationException


class CustomerController {

    def customerService

    def index(){
    }

    def save() {
        try{
            customerService.save(new CustomerAdapter(params))
            redirect(action: 'index')
        } catch (ValidationException exception){
            render exception.errors.allErrors.defaultMessage.join(",  <br>")
        }
    }
}