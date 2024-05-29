package com.miniasaaslw.controller.customer

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.adapters.customer.CustomerAdapter
import grails.validation.ValidationException


class CustomerController {

    def customerService

    def index(){
        def alertInfo = flash.alertInfo
        if(alertInfo){
            return [alertInfo: alertInfo]
        }
    }

    def save() {
        try{
            Customer customer = customerService.save(new CustomerAdapter(params))
            redirect(action: 'show', params : [id: customer.id])
        } catch (ValidationException validationException){
            flash.alertInfo = [alerts: validationException.errors.allErrors.collect { it.defaultMessage } , alertType: "error"]
            redirect(uri: '/customer')
        }catch (Exception exception){
            flash.alertInfo = [alerts: ["Erro ao criar sua conta"], alertType: "error"]
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
        } catch (RuntimeException runtimeException){
            redirect(uri: "/customer")
        } catch (Exception exception){
            flash.alertInfo = [alerts: ["Erro ao buscar sua conta"], alertType: "error"]
            redirect(uri: '/customer')
        }
    }

    def update(){
        long id = params.long("id")

        try{
            Customer customer = customerService.update(id, new CustomerAdapter(params))
            redirect(action: 'show', params : [id: customer.id])
        }catch (ValidationException validationException){
            flash.alertInfo = [alerts: validationException.errors.allErrors.collect { it.defaultMessage } , alertType: "error"]
            redirect(uri: ('/customer/show/' + id))
        }catch (Exception exception){
            flash.alertInfo = [alerts: ["Erro ao atualizar sua conta"], alertType: "error"]
            redirect(uri: '/customer')
        }
    }

    def delete(){
        long id = params.long("id")

        try{
            customerService.delete(id)
            redirect(uri: "/customer")
        }catch (RuntimeException runtimeException){
            runtimeException.printStackTrace()
            redirect(uri: "/customer")
        }catch (Exception exception){
            flash.errors = ["Erro ao deletar sua conta"]
            redirect(uri: "/customer")
        }
    }
}