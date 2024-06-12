package com.miniasaaslw.controller.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.LoggedCustomer
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
            def messageInfo = flash.messageInfo

            User loggedUser = getAuthenticatedUser() as User
            User user

            Boolean isAdmin = loggedUser.getAuthorities().stream().any { it.authority == 'ROLE_ADMIN' }

            if (isAdmin && params.id) {
                user = userService.find(loggedUser.customerId, params.long("id"))
            } else {
                user = loggedUser
            }

            if (messageInfo) {
                return [user: user, messageInfo: messageInfo]
            }

            return [user: user]
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.save.unknown")], messageType: "error"]
        }

        redirect(action: "show")
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

    @Secured(['isAuthenticated()'])
    def updatePassword() {
        try {
            userService.updatePassword(new UserAdapter((getAuthenticatedUser() as User).customer, params))

            flash.messageInfo = [messages: [MessageUtils.getMessage("user.success.update")], messageType: "success"]
        } catch (ValidationException validationException) {
            flash.messageInfo = [messages: validationException.errors.allErrors.collect { it.defaultMessage }, messageType: "error"]
        } catch (Exception exception) {
            exception.printStackTrace()
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.update.unknown")], messageType: "error"]
        }
        redirect(action: "show")
    }

    def list() {
        return [userList: userService.list([customerId: (getAuthenticatedUser() as User).customerId], getLimitPerPage(), getOffset())]
    }

    @CompileDynamic
    def loadTableContent() {
        Map search = [customerId: LoggedCustomer.CUSTOMER.id]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.name) search."name[like]" = params.name

        List<User> userList = userService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = userList.totalCount
        String content = g.render(template: "/user/templates/tableContent", model: [userList: userList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
