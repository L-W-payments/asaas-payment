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
            Customer customer = customerService.save(new CustomerAdapter(params))
            redirect(action: 'show', params : [id: customer.id])
        } catch (ValidationException exception){
            render exception.errors.allErrors.defaultMessage.join(",  <br>")
        }
    }

    def show(){
        try{
            Customer customer = customerService.find(params.long("id"))
            if(customer){
                return [customer: customer]
            }
        } catch (Exception e){
            e.printStackTrace()
            redirect(uri: "/customer")
        }
    }
}