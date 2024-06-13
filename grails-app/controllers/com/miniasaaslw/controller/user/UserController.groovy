package com.miniasaaslw.controller.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.MessageUtils

import grails.converters.JSON
import grails.compiler.GrailsCompileStatic
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
            User user

            Boolean isAdmin = getCurrentUser().getAuthorities().stream().any { it.authority == 'ROLE_ADMIN' }

            if (isAdmin && params.id) {
                user = userService.find(getCurrentCustomerId(), params.long("id"))
            } else {
                user = getCurrentUser()
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
            userService.save(new UserAdapter(getCurrentCustomer(), Role.findByAuthority('ROLE_MEMBER'), params))

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


            userService.delete(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("user.errors.delete.unknown")] as JSON)
        }
    }

    @Secured(['ROLE_ADMIN'])
    def restore() {
        try {
            Long id = params.long("id")


            userService.restore(getCurrentCustomerId(), id)
            render([success: true] as JSON)
        } catch (RuntimeException runtimeException) {
            flash.messageInfo = [messages: [runtimeException.getMessage()], messageType: "error"]
            render([success: false, alert: runtimeException.getMessage()] as JSON)
        } catch (Exception exception) {
            flash.messageInfo = [messages: [MessageUtils.getMessage("user.errors.restore.unknown")], messageType: "error"]
            render([success: false, alert: MessageUtils.getMessage("user.errors.restore.unknown")] as JSON)
        }
    }

    @Secured(['isAuthenticated()'])
    def updatePassword() {
        try {

            userService.updatePassword(new UserAdapter(getCurrentCustomer(), params))

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
        return [userList: userService.list([customerId: getCurrentCustomerId()], getLimitPerPage(), getOffset())]
    }

    @CompileDynamic
    def loadTableContent() {

        Map search = [customerId: getCurrentCustomerId()]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.email) search."email[like]" = params.email

        List<User> userList = userService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = userList.totalCount
        String content = g.render(template: "/user/templates/tableContent", model: [userList: userList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
