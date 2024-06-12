package com.miniasaaslw.controller.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['ROLE_ADMIN'])
class UserController extends BaseController {

    UserService userService

    def index() {
        def messageInfo = flash.messageInfo

        if (messageInfo) {
            return [messageInfo: messageInfo]
        }
    }

    @CompileDynamic
    @Secured(['isAuthenticated()'])
    def show() {
        try {
            User loggedUser = getAuthenticatedUser() as User
            Boolean isAdmin = loggedUser.getAuthorities().stream().any { it.authority == 'ROLE_ADMIN' }

            if (isAdmin) {
                if (params.id) {
                    return [user: userService.find(loggedUser.customerId, params.long("id"))]
                }
            }

            return [user: loggedUser]
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.save.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            User loggedCustomer = getAuthenticatedUser() as User

            userService.save(new UserAdapter(loggedCustomer.customer, Role.findByAuthority('ROLE_MEMBER'), params))

            flash.messageInfo = [messages: [MessageUtils.getMessage("user.success.save")], messageType: "success"]
            redirect(action: "index")
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
            redirect(action: "index")
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.save.unknown")], messageType: "error"]
            redirect(action: "index")
        }
    }

    def list() {
        User loggedCustomer = getAuthenticatedUser() as User

        List<User> list = userService.list([customerId: loggedCustomer.customer.id], getLimitPerPage(), getOffset())

        return [userList: list]
    }

    @CompileDynamic
    def loadTableContent() {
        User loggedCustomer = getAuthenticatedUser() as User

        Map search = [customerId: loggedCustomer.customer.id]

        if (params.email) search."email[like]" = params.email

        List<User> userList = userService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = userList.totalCount
        String content = g.render(template: "/user/templates/tableContent", model: [userList: userList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
