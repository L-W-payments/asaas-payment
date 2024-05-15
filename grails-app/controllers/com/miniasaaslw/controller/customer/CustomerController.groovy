package com.miniasaaslw.controller.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.utils.adapters.customer.CustomerAdapter
import grails.validation.ValidationException


class CustomerController {

    def customerService

    def index(){
        def errors = flash.errors
        if(errors){
            return [errors: errors]
        }
    }

    def save() {
        try{
            Customer customer = customerService.save(new CustomerAdapter(params))
            redirect(action: 'show', params : [id: customer.id])
        } catch (ValidationException exception){
            flash.errors = exception.errors.allErrors.collect { it.defaultMessage }
            redirect(uri: '/customer')
        }
    }

    def show(){
        def errors = flash.errors
        try{
            Customer customer = customerService.find(params.long("id"))
            if(customer){
                if(errors){
                    return [customer: customer, errors: errors]
                }
                return [customer: customer]
            }
        } catch (RuntimeException e){
            redirect(uri: "/customer")
        }
    }

    def update(){
        long id = params.long("id")

        try{
            Customer customer = customerService.update(id, new CustomerAdapter(params))
            redirect(action: 'show', params : [id: customer.id])
        }catch (ValidationException exception){
            flash.errors = exception.errors.allErrors.collect { it.defaultMessage }
            redirect(uri: ('/customer/show/' + id))
        }
    }

    def delete(){
        long id = params.long("id")

        try{
            customerService.delete(id)
            redirect(uri: "/customer")
        }catch (RuntimeException e){
            e.printStackTrace()
            redirect(uri: "/customer")
        }
    }
}