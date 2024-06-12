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
                    return [user: userService.find((getAuthenticatedUser() as User).customerId, params.long("id"))]
                }
            }

            return [user: (getAuthenticatedUser() as User)]
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.save.unknown")], messageType: "error"]
        }

        redirect(action: "index")
    }

    def save() {
        try {
            userService.save(new UserAdapter((getAuthenticatedUser() as User).customer, Role.findByAuthority('ROLE_MEMBER'), params))

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

    @Secured(['ROLE_ADMIN'])
    def delete() {
        try {
            Long id = params.long("id")

            User loggedUser = getAuthenticatedUser() as User

            userService.delete(loggedUser.customer.id, id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("user.errors.delete.unknown")] as JSON)
        }
    }

    def list() {
        return [userList: userService.list([customerId: (getAuthenticatedUser() as User).customerId], getLimitPerPage(), getOffset())]
    }

    @CompileDynamic
    def loadTableContent() {
        User loggedUser = getAuthenticatedUser() as User

        Map search = [customerId: loggedUser.customer.id]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.email) search."email[like]" = params.email

        List<User> userList = userService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = userList.totalCount
        String content = g.render(template: "/user/templates/tableContent", model: [userList: userList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
