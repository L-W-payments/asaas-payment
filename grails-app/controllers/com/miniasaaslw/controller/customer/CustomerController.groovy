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
            render exception.errors.allErrors.defaultMessage.join(",  <br>")
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